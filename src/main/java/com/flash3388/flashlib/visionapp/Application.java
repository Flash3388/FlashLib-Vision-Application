package com.flash3388.flashlib.visionapp;

import com.flash3388.flashlib.app.FlashLibControl;
import com.flash3388.flashlib.app.SimpleApp;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.util.concurrent.ExecutorCloser;
import com.flash3388.flashlib.visionapp.config.InstanceConfiguration;
import com.flash3388.flashlib.visionapp.config.InstancesConfiguration;
import com.flash3388.flashlib.visionapp.config.RootConfiguration;
import com.flash3388.flashlib.visionapp.vision.InstanceInfo;
import com.flash3388.flashlib.visionapp.vision.InstanceManager;
import com.flash3388.flashlib.visionapp.vision.VisionInstance;
import com.flash3388.flashlib.visionapp.vision.pipelines.BasePipeline;
import com.flash3388.flashlib.visionapp.vision.pipelines.TestPipeline;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionPipeline;
import com.flash3388.flashlib.visionapp.vision.sources.ComplexSource;
import com.flash3388.flashlib.visionapp.vision.sources.CvCameraSource;
import com.flash3388.flashlib.visionapp.vision.sources.VisionSource;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application implements SimpleApp {

    private final FlashLibControl mControl;


    private final InstanceManager mInstanceManager;

    public Application(FlashLibControl control, RootConfiguration configuration) {
        mControl = control;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        control.registerCloseables(new ExecutorCloser(executorService));

        StoredObject obsrRoot = control.getNetworkInterface().getObjectStorage().getInstanceRoot();
        StoredObject instancesRoot = obsrRoot.getChild("instances");

        // parse config
        Collection<VisionInstance> instances = new ArrayList<>();
        for (InstanceConfiguration instanceConfiguration : configuration.getInstances().getAll().values()) {
            StoredObject object = instancesRoot.getChild(instanceConfiguration.getName());

            VisionPipeline pipeline = instanceConfiguration.getPipeline().create(object.getChild("pipeline"));
            VisionInstance instance = new VisionInstance(
                    new InstanceInfo(instanceConfiguration.getName()),
                    control.getClock(),
                    object,
                    new ComplexSource(instanceConfiguration.getSource().getOpener()),
                    pipeline
            );
            instances.add(instance);
        }

        mInstanceManager = new InstanceManager(executorService, instances);
        mControl.getServiceRegistry().register(mInstanceManager);
    }

    @Override
    public void main() throws Exception {
        mControl.getServiceRegistry().startAll();
        while (true) {
            Thread.sleep(1000);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
