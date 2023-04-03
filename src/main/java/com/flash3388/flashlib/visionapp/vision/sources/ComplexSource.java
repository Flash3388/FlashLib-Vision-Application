package com.flash3388.flashlib.visionapp.vision.sources;

import com.castle.util.closeables.Closeables;
import com.castle.util.function.ThrowingSupplier;
import com.flash3388.flashlib.vision.VisionException;

public class ComplexSource implements VisionSource {

    private final ThrowingSupplier<VisionSource, VisionException> mOpener;
    private VisionSource mOpenSource;

    public ComplexSource(ThrowingSupplier<VisionSource, VisionException> opener) {
        mOpener = opener;
        mOpenSource = null;
    }

    @Override
    public synchronized VisionImage get() throws VisionException {
        VisionSource source = openSource();
        try {
            return source.get();
        } catch (VisionSourceException e) {
            close();
            throw e;
        }
    }

    @Override
    public synchronized void close() {
        if (mOpenSource != null) {
            Closeables.silentClose(mOpenSource);
            mOpenSource = null;
        }
    }

    private synchronized VisionSource openSource() throws VisionException {
        if (mOpenSource != null) {
            return mOpenSource;
        }

        mOpenSource = mOpener.get();
        return mOpenSource;
    }
}
