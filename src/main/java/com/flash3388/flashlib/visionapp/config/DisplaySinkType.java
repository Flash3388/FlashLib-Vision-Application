package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.app.FlashLibInstance;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.vision.jpeg.server.MjpegServer;
import com.flash3388.flashlib.visionapp.vision.InstanceInfo;
import com.flash3388.flashlib.visionapp.vision.pipelines.PipelineDisplay;
import com.flash3388.flashlib.visionapp.vision.pipelines.PipelineImageSink;
import com.flash3388.flashlib.visionapp.vision.pipelines.ServerPipelineSink;
import com.typesafe.config.Config;

import java.io.IOException;
import java.net.InetSocketAddress;

public enum DisplaySinkType {
    MJPEG("mjpeg") {
        @Override
        public PipelineImageSink createFromConfig(InstanceInfo instanceInfo,
                                                  Config config,
                                                  StoredObject object) throws IOException {
            int port = config.getInt("port");

            MjpegServer server = MjpegServer.create(
                    new InetSocketAddress(port),
                    FlashLibInstance.getControl().getClock()
            );
            FlashLibInstance.getControl().getServiceRegistry().register(server);

            return new ServerPipelineSink(instanceInfo.getName(), server);
        }
    },
    SWING("swing") {
        @Override
        public PipelineImageSink createFromConfig(InstanceInfo instanceInfo, Config config, StoredObject object) throws IOException {
            return new PipelineDisplay(instanceInfo.getName());
        }
    }
    ;

    private final String mConfigName;

    DisplaySinkType(String configName) {
        mConfigName = configName;
    }

    public abstract PipelineImageSink createFromConfig(InstanceInfo instanceInfo,
                                              Config config,
                                              StoredObject object) throws IOException;

    public static DisplaySinkType fromConfigName(String typeName) {
        for (DisplaySinkType type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(DisplaySinkType.class, typeName);
    }
}
