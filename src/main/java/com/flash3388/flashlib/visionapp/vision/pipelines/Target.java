package com.flash3388.flashlib.visionapp.vision.pipelines;

import org.opencv.core.Point;

public interface Target {

    Point getCenter();

    int getWidthPixels();
    int getHeightPixels();
}
