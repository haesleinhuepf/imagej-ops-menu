package net.imagej.ops.menu.filter;

import ij.ImagePlus;
import ij.plugin.frame.Recorder;
import net.imagej.Dataset;
import net.imagej.legacy.ImageJLegacyUtilities;
import net.imagej.legacy.RecorderWrapper;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * Created by haase on 7/8/17.
 */

@Plugin(type = Command.class, menuPath = "Ops>Filtering>Gaussian Blur")
public class GaussianBlurFilter<T extends RealType<T>> implements Command {


    @Parameter
    private ImagePlus currentData;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService opService;

    @Parameter(label = "sigma in pixels")
    private double sigma;

    @Override
    public void run() {

        Img<T> input = ImageJFunctions.wrapReal(currentData);

        RandomAccessibleInterval<T> output = opService.filter().gauss(input, sigma);

        ImagePlus imp = ImageJFunctions.wrap(output, "Gaussian blur (sigmal = " + sigma + ") of " + currentData.getTitle());

        imp = ImageJLegacyUtilities.copyImagePlusProperties(currentData, imp);
        imp.show();

        RecorderWrapper.getInstance().record("gaussianBlurredImage = ops.filter().gauss(image, sigma);");
    }
}
