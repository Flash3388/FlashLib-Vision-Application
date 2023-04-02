package com.flash3388.flashlib.visionapp.vision;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.visionapp.Globals;
import com.flash3388.flashlib.visionapp.vision.pipelines.VisionPipeline;
import com.flash3388.flashlib.visionapp.vision.sources.InstanceSource;
import com.flash3388.flashlib.visionapp.vision.sources.VisionSource;
import org.slf4j.Logger;

public class VisionInstance {

    private final InstanceInfo mInfo;
    private final Clock mClock;
    private final StoredObject mObject;

    private final VisionSource mSource;
    private final VisionPipeline mPipeline;

    private final Logger mLogger;

    public VisionInstance(InstanceInfo info,
                          Clock clock,
                          StoredObject object,
                          VisionSource source,
                          VisionPipeline pipeline) {
        mInfo = info;
        mClock = clock;
        mObject = object.getChild(info.getName());
        mSource = source;
        mPipeline = pipeline;

        mLogger = Globals.getVisionInstanceLogger(info.getName());
    }

    public Runnable createRunTask() {
        return new VisionRunner(
                new InstanceSource(mInfo, mSource),
                mPipeline,
                mClock,
                mLogger,
                mObject.getChild("run")
        );
    }
}
