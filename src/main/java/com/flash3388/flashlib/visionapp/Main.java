package com.flash3388.flashlib.visionapp;

import com.castle.code.Natives;
import com.castle.exceptions.CodeLoadException;
import com.castle.exceptions.FindException;
import com.flash3388.flashlib.app.AppImplementation;
import com.flash3388.flashlib.app.BasicFlashLibControl;
import com.flash3388.flashlib.app.FlashLibControl;
import com.flash3388.flashlib.app.FlashLibMain;
import com.flash3388.flashlib.app.SimpleAppRunner;
import com.flash3388.flashlib.app.net.HfcsConfiguration;
import com.flash3388.flashlib.app.net.NetworkConfiguration;
import com.flash3388.flashlib.app.net.ObsrConfiguration;
import com.flash3388.flashlib.net.util.NetInterfaces;
import com.flash3388.flashlib.util.unique.InstanceId;
import com.flash3388.flashlib.util.unique.InstanceIdGenerator;
import com.flash3388.flashlib.visionapp.config.RootConfiguration;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.opencv.core.Core;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;

import static net.sourceforge.argparse4j.impl.Arguments.*;

public class Main {

    private static final long IDENTIFIER = 0x55;
    private static final String DEFAULT_CONFIG_FILE_NAME = "application.conf";

    public static void main(String[] args) throws IOException, CodeLoadException, ArgumentParserException {
        ProgramOptions programOptions = handleArguments(args);
        loadNatives();

        Config baseConfig = ConfigFactory.parseFile(programOptions.getConfigFile()).resolve();
        RootConfiguration configuration = new RootConfiguration(baseConfig);

        NetworkInterface networkInterface = NetInterfaces.getInterface("lo");
        InetAddress multicastGroup = InetAddress.getByName("224.0.0.251");

        InstanceId instanceId = InstanceIdGenerator.generate(IDENTIFIER);
        FlashLibMain.appMain((ourId, resourceHolder)-> {
            NetworkConfiguration networkConfiguration = NetworkConfiguration.enabled(
                    ObsrConfiguration.primaryNode(),
                    HfcsConfiguration.multicastMode(
                            5005,
                            networkInterface,
                            multicastGroup,
                            5000)
            );

            FlashLibControl control = new BasicFlashLibControl(ourId, resourceHolder, networkConfiguration);
            return new AppImplementation(control, new SimpleAppRunner(
                    (cnt)-> new Application(cnt, configuration, programOptions)
            ));
        }, instanceId);
    }

    private static void loadNatives() throws CodeLoadException {
        try {
            Natives.Loader loader = Natives.newLoader();
            loader.load(Core.NATIVE_LIBRARY_NAME);
        } catch (FindException | IOException e) {
            throw new CodeLoadException(e);
        }
    }

    private static ProgramOptions handleArguments(String[] args) throws ArgumentParserException {
        ArgumentParser parser = ArgumentParsers.newFor("FlashLib Vision App")
                .build()
                .defaultHelp(true)
                .description("Vision Program for FlashLib");

        // config
        String userDir = System.getProperty("user.dir");
        parser.addArgument("-f", "--config-file")
                .dest("config-file")
                .required(false)
                .type(String.class)
                .action(store())
                .setDefault(userDir.concat("/").concat(DEFAULT_CONFIG_FILE_NAME))
                .help("Path to the configuration file of the program");

        parser.addArgument("--display-pipelines")
                .dest("display-pipelines")
                .required(false)
                .type(booleanType())
                .action(storeTrue())
                .setDefault(false)
                .help("Enables visual display of the pipelines and its images");


        Namespace namespace = parser.parseArgs(args);
        return new ProgramOptions(
                new File(namespace.getString("config-file")),
                namespace.getBoolean("display-pipelines")
        );
    }
}
