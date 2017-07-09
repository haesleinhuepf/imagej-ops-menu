package net.imagej.ops.menu.core;

import net.imglib2.algorithm.neighborhood.CenteredRectangleShape;
import net.imglib2.algorithm.neighborhood.Shape;

/**
 * Created by haase on 7/9/17.
 */
public class OpsMenuUtilities {
    public static Shape rectangleShape(int d, int radius, boolean skipCenter) {
        int[] span = new int[d];
        for (int i = 0; i < span.length; i++) {
            span[i] = radius;
        }
        return new CenteredRectangleShape(span, skipCenter);
    }

    public static String rectangleShapeCommandString(int d, int radius, boolean skipCenter) {
        String command = "import net.imglib2.algorithm.neighborhood.CenteredRectangleShape;\n";

        command = command + "span = new int[] {";
        for (int i = 0; i < d; i++) {
            command = command + radius;
            if (i < d - 1) {
                command = command + ",";
            }
        }
        command = command + "};\n";
        command = command + "shape = new CenteredRectangleShape(span, " + (skipCenter?"true":"false") + ");\n";

        return command;
    }
}
