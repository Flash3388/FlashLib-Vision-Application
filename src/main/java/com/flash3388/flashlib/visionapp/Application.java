package com.flash3388.flashlib.visionapp;

import com.flash3388.flashlib.app.FlashLibControl;
import com.flash3388.flashlib.app.SimpleApp;
import com.flash3388.flashlib.app.StartupException;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.visionapp.config.InstanceConfiguration;
import com.flash3388.flashlib.visionapp.config.PipelineConfiguration;
import com.flash3388.flashlib.visionapp.config.RootConfiguration;
import com.flash3388.flashlib.visionapp.config.VisionOptionConfiguration;
import com.flash3388.flashlib.visionapp.vision.InstanceInfo;
import com.flash3388.flashlib.visionapp.vision.InstanceManager;
import com.flash3388.flashlib.visionapp.vision.VisionInstance;
import com.flash3388.flashlib.visionapp.vision.pipelines.PipelineImageSink;
import com.flash3388.flashlib.visionapp.vision.sources.ComplexSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Application implements SimpleApp {

    private final FlashLibControl mControl;
    private final InstanceManager mInstanceManager;

    public Application(FlashLibControl control, RootConfiguration configuration, ProgramOptions programOptions)
            throws StartupException {
        mControl = control;

        StoredObject obsrRoot = control.getNetworkInterface().getObjectStorage().getInstanceRoot();
        StoredObject instancesRoot = obsrRoot.getChild("instances");

        // parse config
        Collection<VisionInstance> instances = new ArrayList<>();
        for (InstanceConfiguration instanceConfiguration : configuration.getInstances().getAll().values()) {
            StoredObject object = instancesRoot.getChild(instanceConfiguration.getName());

            Collection<PipelineImageSink> imageSinks;
            try {
                imageSinks = instanceConfiguration.getDisplaySinks().getAll();
            } catch (IOException e) {
                imageSinks = new ArrayList<>();
            }

            StoredObject pipelineObject = object.getChild("pipeline");
            PipelineConfiguration pipelineConfiguration = instanceConfiguration.getPipeline();
            VisionOptionConfiguration visionOptionConfiguration = instanceConfiguration.getVisionOptions();

            VisionInstance instance = new VisionInstance(
                    new InstanceInfo(instanceConfiguration.getName()),
                    control.getClock(),
                    object,
                    new ComplexSource(instanceConfiguration.getSource().getOpener()),
                    pipelineConfiguration.getProcessors(pipelineObject),
                    pipelineConfiguration.getDetector(pipelineObject),
                    pipelineConfiguration.getAnalyser(pipelineObject),
                    pipelineConfiguration.getSink(pipelineObject),
                    imageSinks,
                    visionOptionConfiguration
            );
            instances.add(instance);
        }

        mInstanceManager = new InstanceManager(instances);
        mControl.registerCloseables(mInstanceManager);

        if (programOptions.shouldStartPipelines()) {
            mInstanceManager.startAll();
        }
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
