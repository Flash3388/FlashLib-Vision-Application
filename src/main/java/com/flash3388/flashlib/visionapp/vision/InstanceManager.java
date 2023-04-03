package com.flash3388.flashlib.visionapp.vision;

import java.util.Collection;

public class InstanceManager implements AutoCloseable {

    private final Collection<VisionInstance> mInstances;

    public InstanceManager(Collection<VisionInstance> instances) {
        mInstances = instances;
    }

    public void startAll() {
        for (VisionInstance instance : mInstances) {
            instance.start();
        }
    }

    @Override
    public void close() throws Exception {
        for (VisionInstance instance : mInstances) {
            instance.stop();
        }
    }
}
