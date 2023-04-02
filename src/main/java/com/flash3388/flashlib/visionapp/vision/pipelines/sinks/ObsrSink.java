package com.flash3388.flashlib.visionapp.vision.pipelines.sinks;

import com.flash3388.flashlib.net.obsr.StoredObject;
import com.flash3388.flashlib.vision.VisionException;
import com.flash3388.flashlib.vision.analysis.Analysis;
import com.flash3388.flashlib.vision.analysis.Target;
import com.flash3388.flashlib.visionapp.vision.pipelines.AnalysisSink;

public class ObsrSink implements AnalysisSink {

    private final StoredObject mObject;

    public ObsrSink(StoredObject object) {
        mObject = object;
    }

    @Override
    public void process(Analysis input) throws VisionException {
        mObject.delete();

        for (Target target : input.getDetectedTargets()) {
            if (!target.hasProperty("id")) {
                continue;
            }

            int id = target.getProperty("id", Integer.class);
            StoredObject object = mObject.getChild(String.valueOf(id));

            if (target.hasProperty("distance")) {
                double value = target.getProperty("distance", Double.class);
                object.getEntry("distance").setDouble(value);
            }

            if (target.hasProperty("angle")) {
                double value = target.getProperty("angle", Double.class);
                object.getEntry("angle").setDouble(value);
            }

            if (target.hasProperty("centerX")) {
                double value = target.getProperty("centerX", Double.class);
                object.getEntry("centerX").setDouble(value);
            }

            if (target.hasProperty("centerY")) {
                double value = target.getProperty("centerY", Double.class);
                object.getEntry("centerY").setDouble(value);
            }
        }
    }
}
