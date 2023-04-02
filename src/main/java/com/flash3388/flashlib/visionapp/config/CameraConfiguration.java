package com.flash3388.flashlib.visionapp.config;

import com.typesafe.config.Config;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CameraConfiguration extends ConfigurationBase {

    private final int mDev;
    private final int mWidth;
    private final int mHeight;
    private final int mFps;
    private final double mFovRadians;
    private final KnownCameraBackend mBackend;
    private final KnownCameraFormat mFormat;

    CameraConfiguration(Config config) {
        super(config);

        mDev = config.getInt("dev");
        assert mDev >= 0;

        mWidth = config.getInt("width");
        assert mWidth > 0;

        mHeight = config.getInt("height");
        assert mHeight > 0;

        mFps = config.getInt("fps");
        assert mFps > 0;

        mFovRadians = config.getDouble("fovRadians");
        assert mFovRadians > 0;

        if (config.hasPath("backend")) {
            String backendName = config.getString("backend");
            mBackend = KnownCameraBackend.fromConfigName(backendName);
        } else {
            mBackend = KnownCameraBackend.ANY;
        }

        if (config.hasPath("format")) {
            String formatName = config.getString("format");
            mFormat = KnownCameraFormat.fromConfigName(formatName);
        } else {
            mFormat = null;
        }
    }

    public int getDev() {
        return mDev;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getFps() {
        return mFps;
    }

    public double getFovRadians() {
        return mFovRadians;
    }

    public KnownCameraBackend getBackend() {
        return mBackend;
    }

    public KnownCameraFormat getFormat() {
        return mFormat;
    }
}
