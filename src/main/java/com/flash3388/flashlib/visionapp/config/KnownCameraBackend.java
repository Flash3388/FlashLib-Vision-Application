package com.flash3388.flashlib.visionapp.config;

import org.opencv.videoio.Videoio;

public enum KnownCameraBackend {
    ANY("any", Videoio.CAP_ANY),
    V4L2("v4l2", Videoio.CAP_V4L2),
    V4L("v4l", Videoio.CAP_V4L),
    OPENCV_MJPEG("opencv-mjpeg", Videoio.CAP_OPENCV_MJPEG),
    FFMPEG("ffmpeg", Videoio.CAP_FFMPEG),
    GSTREAMER("gstreamer", Videoio.CAP_GSTREAMER)
    ;

    private final String mConfigName;
    private final int mOpencvBackendCode;

    KnownCameraBackend(String configName, int opencvBackendCode) {
        mConfigName = configName;
        mOpencvBackendCode = opencvBackendCode;
    }

    public int getOpencvBackendCode() {
        return mOpencvBackendCode;
    }

    public static KnownCameraBackend fromConfigName(String typeName) {
        for (KnownCameraBackend type : values()) {
            if (type.mConfigName.equals(typeName)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(KnownCameraBackend.class, typeName);
    }
}
