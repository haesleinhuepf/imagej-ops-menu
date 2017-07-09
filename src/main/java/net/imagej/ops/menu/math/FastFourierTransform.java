package net.imagej.ops.menu.math;

import ij.IJ;
import ij.ImagePlus;
import net.imagej.legacy.ImageJLegacyUtilities;
import net.imagej.legacy.RecorderWrapper;
import net.imagej.ops.OpService;
import net.imagej.ops.filter.fft.FFTMethodsOpF;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.complex.ComplexFloatType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * Created by haase on 7/9/17.
 */

@Plugin(type = Command.class, menuPath = "Ops>Filtering>Fast Fourier Transform (WIP)")
public class FastFourierTransform<T extends RealType<T>> implements Command {

    @Parameter
    private ImagePlus currentData;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService opService;

    @Override
    public void run() {

        Img<T> input = ImageJFunctions.wrapReal(currentData);
        //Img<T> fftOutput = opService.filter().createFFTOutput(input, input.firstElement());


        final RandomAccessibleInterval<ComplexFloatType> output =
                (RandomAccessibleInterval<ComplexFloatType>) opService.run(
                        FFTMethodsOpF.class, input, null, false);

        //RandomAccessibleInterval<T> output = opService.filter().fft(input);

        ImagePlus imp = ImageJFunctions.wrap(output, "FFT of " + currentData.getTitle());

        //imp = ImageJLegacyUtilities.copyImagePlusProperties(currentData, imp);
        imp.show();
        IJ.run(imp, "Enhance Contrast", "saturated=0.35");

        RecorderWrapper.getInstance().record("fftImage = ops.filter().fft(input);");
    }
}
