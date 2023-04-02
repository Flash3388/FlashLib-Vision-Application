package com.flash3388.flashlib.visionapp.vision;

public class InstanceData {

    private final InstanceInfo mInstanceInfo;
    private final VisionData mVisionData;

    public InstanceData(InstanceInfo instanceInfo, VisionData visionData) {
        mInstanceInfo = instanceInfo;
        mVisionData = visionData;
    }

    public InstanceInfo getInstanceInfo() {
        return mInstanceInfo;
    }

    public VisionData getVisionData() {
        return mVisionData;
    }
}
