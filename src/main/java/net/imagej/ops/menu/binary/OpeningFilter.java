package net.imagej.ops.menu.binary;

import ij.ImagePlus;
import net.imagej.legacy.ImageJLegacyUtilities;
import net.imagej.legacy.RecorderWrapper;
import net.imagej.ops.OpService;
import net.imagej.ops.create.img.Imgs;
import net.imagej.ops.menu.core.OpsMenuUtilities;
import net.imglib2.algorithm.neighborhood.Shape;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.RealType;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UIService;

/**
 * Created by haase on 7/9/17.
 */

@Plugin(type = Command.class, menuPath = "Ops>Binary>Opening")
public class OpeningFilter<T extends RealType<T>> implements Command {

    @Parameter
    private ImagePlus currentData;

    @Parameter
    private UIService uiService;

    @Parameter
    private OpService opService;

    @Parameter(label = "radius in pixels")
    private int radius;

    @Override
    public void run() {

        Img<T> input = ImageJFunctions.wrapReal(currentData);

        Img<T> output = Imgs.create(input.factory(), input, input.firstElement());
        Img<T> temp = Imgs.create(input.factory(), input, input.firstElement());

        Shape shape = OpsMenuUtilities.rectangleShape(input.numDimensions(), radius, false);

        opService.filter().min(temp, input, shape);
        opService.filter().max(output, temp, shape);

        ImagePlus imp = ImageJFunctions.wrap(output, "Binary opening (radius = " + radius + ") of " + currentData.getTitle());

        imp = ImageJLegacyUtilities.copyImagePlusProperties(currentData, imp);
        imp.show();

        String command = OpsMenuUtilities.rectangleShapeCommandString(input.numDimensions(), radius, false);
        command = command + "output = ops.create().img(input);\n";
        command = command + "temp = ops.create().img(input);\n";
        command = command + "output = ops.filter().min(temp, input, shape);\n";
        command = command + "output = ops.filter().max(output, temp, shape);\n";

        RecorderWrapper.getInstance().record(command);
    }
}