package net.imagej.ops.managedmenu;

import ij.plugin.frame.Recorder;
import net.imagej.Dataset;
import net.imagej.ops.OpService;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * Created by haase on 7/8/17.
 */

@Plugin(type = Command.class, menuPath = "Ops>Filtering>Gaussian Blur (Ops)")
public class GaussianBlurPlugin<T extends RealType<T>> implements Command {


    @Parameter
    private Dataset currentData;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService opService;

    @Parameter
    private double sigma;

    @Override
    public void run() {

        final Img<T> image = (Img<T>)currentData.getImgPlus();

        RandomAccessibleInterval<T> result = opService.filter().gauss(image, sigma);

        uiService.show(result);

        Recorder.record("ops.filter().gauss(image, sigma)");
    }
}
