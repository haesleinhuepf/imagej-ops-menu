package net.imagej.ops.menu.math;

import ij.ImagePlus;
import net.imagej.legacy.ImageJLegacyUtilities;
import net.imagej.legacy.RecorderWrapper;
import net.imagej.ops.OpService;
import net.imagej.ops.filter.ifft.IFFTMethodsOpC;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * Created by haase on 7/9/17.
 */

@Plugin(type = Command.class, menuPath = "Ops>Filtering>Inverse Fast Fourier Transform (WIP)")
public class InverseFastFourierTransform<T extends RealType<T>> implements Command {

    @Parameter
    private ImagePlus fftImage;

    @Parameter
    private ImagePlus targetImage;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService opService;

    @Override
    public void run() {

        Img<FloatType> input = ImageJFunctions.convertFloat(fftImage);
        Img<FloatType> output = ImageJFunctions.convertFloat(targetImage);
        //Img<T> fftOutput = opService.filter().createFFTOutput(input, input.firstElement());


        opService.run(IFFTMethodsOpC.class, output, input);

        //opService.filter().ifft(output, input);

        ImagePlus imp = ImageJFunctions.wrap(output, "iFFT of " + fftImage.getTitle());

        imp = ImageJLegacyUtilities.copyImagePlusProperties(fftImage, imp);
        imp.show();

        RecorderWrapper.getInstance().record("ops.filter().ifft(output, input);");
    }
}
