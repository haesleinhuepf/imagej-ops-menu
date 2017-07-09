package net.imagej.ops.menu.filter;

import ij.ImagePlus;
import ij.plugin.frame.Recorder;
import net.imagej.ImageJ;
import net.imagej.legacy.RecorderWrapper;
import org.jruby.RubyProcess;
import org.junit.Test;
import ij.IJ;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by haase on 7/9/17.
 */
public class GaussianBlurFilterTest {
    public static void main(String... args) throws IOException, InterruptedException {
        new GaussianBlurFilterTest().testIfPluginAndRecorderOutputDoTheSame();
    }

    @Test
    public void testIfPluginAndRecorderOutputDoTheSame() throws IOException, InterruptedException {
        ImageJ ij = new ImageJ();
        new Recorder();

        ImagePlus image = IJ.openImage("src/test/resources/blobs.gif");
        image.show();

        Object[] parameters = new Object[]{"image", image, "sigma", 5};

        ij.command().run(GaussianBlurFilter.class, true, parameters);
        Thread.sleep(1000);

        ImagePlus commandResult = IJ.getImage();



        String executedCommandScript = RecorderWrapper.getInstance().getRecordings();
        RecorderWrapper.getInstance().clear();

        String[] temp = executedCommandScript.split("=");
        System.out.println(Arrays.toString(temp));
        String resultVariableName = temp[temp.length - 2].trim();

        String script =
                "import ij.ImagePlus;\n" +
                "//@UIService ui\n" +
                "//@OpService ops\n" +
                "//@ImagePlus image\n" +
                "//@int sigma\n" +
                        executedCommandScript + "\n" +
                "ui.show(" + resultVariableName + ")\n";

        System.out.println("Script:\n\n" + script);
        ij.script().run("test.groovy", script, true, parameters);
    }

}