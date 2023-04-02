package com.flash3388.flashlib.visionapp.vision.pipelines.detectors;

import java.util.Collection;
import java.util.Map;

public interface ObjectTracker {

    Map<Integer, ? extends ScorableTarget> updateTracked(Collection<? extends ScorableTarget> objects);
}
