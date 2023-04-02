package com.flash3388.flashlib.visionapp.vision.sources;

import com.flash3388.flashlib.vision.Source;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.visionapp.vision.InstanceData;
import com.flash3388.flashlib.visionapp.vision.InstanceInfo;
import com.flash3388.flashlib.visionapp.vision.VisionData;

public class InstanceSource implements Source<InstanceData> {

    private final InstanceInfo mInfo;
    private final VisionSource mSource;

    public InstanceSource(InstanceInfo info, VisionSource source) {
        mInfo = info;
        mSource = source;
    }

    @Override
    public InstanceData get() throws VisionException {
        VisionData data = mSource.get();
        return new InstanceData(mInfo, data);
    }
}
