package net.imagej.segmentation.thresholding;

import ij.IJ;
import ij.ImagePlus;
import net.imagej.legacy.ImageJLegacyUtilities;
import net.imagej.legacy.RecorderWrapper;
import net.imagej.ops.OpService;
import net.imglib2.IterableInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * Created by haase on 7/8/17.
 */

@Plugin(type = Command.class, menuPath = "Ops>Segmentation>Thresholding>Huang")
public class HuangThresholdSegmentation<T extends RealType<T>> implements Command {
    @Parameter
    ImagePlus currentData;

    @Parameter
    OpService ops;

    @Parameter
    UIService ui;

    @Override
    public void run() {

        Img<T> img = ImageJFunctions.wrapReal(currentData);

        IterableInterval<BitType> result = ops.threshold().huang(img);

        ui.show(result);
        ImagePlus imp = IJ.getImage();

        imp.setTitle("Huang segmentation of " + currentData.getTitle());
        imp = ImageJLegacyUtilities.copyImagePlusProperties(currentData, imp);
        imp.updateAndDraw();

        RecorderWrapper.getInstance().record("huangThresholdedBinaryImage = ops.threshold().huang(image);");



    }
}
