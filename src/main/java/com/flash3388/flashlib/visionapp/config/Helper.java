package com.flash3388.flashlib.visionapp.config;

import com.flash3388.flashlib.visionapp.vision.color.ColorRange;
import com.flash3388.flashlib.visionapp.vision.color.ColorSpace;
import com.typesafe.config.Config;
import org.opencv.core.Range;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    private Helper() {}

    public static ColorRange parseColorRange(Config config) {
        String colorSpaceName = config.getString("space");
        ColorSpace colorSpace = ColorSpace.valueOf(colorSpaceName);

        List<Integer> min = config.getIntList("min");
        List<Integer> max = config.getIntList("max");

        assert min.size() != max.size();
        assert min.size() >= 1 && min.size() <= 4;

        List<Range> ranges = new ArrayList<>();
        for (int i = 0; i < min.size(); i++) {
            ranges.add(new Range(
                    min.get(i),
                    max.get(i)
            ));
        }

        return new ColorRange(colorSpace, ranges);
    }
}
