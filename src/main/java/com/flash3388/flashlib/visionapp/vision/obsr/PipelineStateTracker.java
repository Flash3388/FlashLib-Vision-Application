package com.flash3388.flashlib.visionapp.vision.obsr;

import com.flash3388.flashlib.net.obsr.StoredEntry;
import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.time.Time;

public class PipelineStateTracker {

    private final StoredEntry mRunTime;
    private final StoredEntry mLastError;

    public PipelineStateTracker(StoredObject object) {
        mRunTime = object.getEntry("RunTime");
        mLastError = object.getEntry("LastError");
    }

    public void setPipelineIterationFinished(Time runTime, Throwable error) {
        mRunTime.setLong(runTime.valueAsMillis());
        if (error != null) {
            mLastError.setString(error.getClass().getName());
        } else {
            mLastError.clearValue();
        }
    }
}
