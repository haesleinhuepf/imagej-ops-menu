package net.imagej.legacy;

import ij.ImagePlus;
import ij.plugin.HyperStackConverter;
import ij.process.LUT;

import java.awt.*;

/**
 * Created by haase on 7/8/17.
 */
public class ImageJLegacyUtilities {
    public static ImagePlus copyImagePlusProperties(ImagePlus source, ImagePlus target) {
        target = copyCalibration(source, target);
        target = copyDimensions(source, target);
        target = copyLuts(source, target);
        target = copyDisplayMode(source, target);
        target = copyDisplayRange(source, target);
        target = copyStackPosition(source, target);

        return target;
    }

    public static ImagePlus copyCalibration(ImagePlus source, ImagePlus target) {
        target.setCalibration(source.getCalibration().copy());
        return target;
    }

    public static ImagePlus copyDimensions(ImagePlus source, ImagePlus target) {
        if ((source.getNFrames() != target.getNFrames() ||
                source.getNSlices() != target.getNSlices() ||
                source.getNChannels() != target.getNChannels()) &&
                source.getNChannels() * source.getNSlices() * source.getNFrames() == target.getNSlices()) {
            target = HyperStackConverter.toHyperStack(target, source.getNChannels(), source.getNSlices(), source.getNFrames());
        }
        return target;
    }

    public static ImagePlus copyLuts(ImagePlus source, ImagePlus target) {
        int sourceC = source.getC();
        int targetC = target.getC();
        for (int c = 0; c < source.getNChannels() && c < target.getNChannels(); c++) {
            source.setC(c);
            target.setC(c);
            target.getProcessor().setLut(source.getProcessor().getLut());
        }

        source.setC(sourceC);
        target.setC(targetC);
        return target;
    }

    public static ImagePlus copyDisplayMode(ImagePlus source, ImagePlus target) {
        target.setDisplayMode(source.getDisplayMode());
        return target;
    }

    public static ImagePlus copyDisplayRange(ImagePlus source, ImagePlus target) {
        int sourceC = source.getC();
        int targetC = target.getC();
        for (int c = 0; c < source.getNChannels() && c < target.getNChannels(); c++) {
            target.setDisplayRange(source.getDisplayRangeMin(), source.getDisplayRangeMax());
        }

        source.setC(sourceC);
        target.setC(targetC);

        return target;
    }

    public static ImagePlus copyStackPosition(ImagePlus source, ImagePlus target) {
        target.setC(source.getC());
        target.setT(source.getT());
        target.setZ(source.getZ());
        return target;
    }

}
