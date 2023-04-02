package com.flash3388.flashlib.visionapp.config;

import org.opencv.videoio.VideoWriter;

public enum KnownCameraFormat {
    MJPEG("mjpeg", VideoWriter.fourcc('M', 'J', 'P', 'G')),
    ;

    private final String mConfigName;
    private final int mOpencvCode;

    KnownCameraFormat(String configName, int opencvCode) {
        mConfigName = configName;
        mOpencvCode = opencvCode;
    }

    public int getOpencvCode() {
        return mOpencvCode;
    }

    public static KnownCameraFormat fromConfigName(String typeName) {
        for (KnownCameraFormat type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(KnownCameraFormat.class, typeName);
    }
}
