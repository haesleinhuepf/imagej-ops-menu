package net.imagej.legacy;

import ij.IJ;
import ij.plugin.frame.Recorder;
import org.scijava.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by haase on 7/8/17.
 */
public class RecorderWrapper {
    Recorder recorder;
    TextArea textArea;
    Timer timer;

    private final String placeHolder = "##MARKER##";

    private RecorderWrapper() {
        initialize();
    }

    private void initialize() {
        if (timer == null) {
            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (textArea == null) {
                        return;
                    }
                    // the following is necessary to remove anything ImageJ records in the following
                    // otherwise, something like IJ.run... would be recorded.
                    String texts[] = textArea.getText().split(placeHolder);
                    textArea.setText(texts[0]);
                    timer.stop();
                }
            });
        }

        if (recorder == Recorder.getInstance()) {
            return;
        }
        recorder = Recorder.getInstance();

        if (recorder == null) {
            return;
        }

        Frame frame = (Frame)recorder;
        for (Component component : frame.getComponents()) {
            if (component instanceof TextArea) {
                textArea = (TextArea)component;
            }
        }
    }

    private static RecorderWrapper instance;
    public static RecorderWrapper getInstance() {
        if (instance == null) {
            instance = new RecorderWrapper();
            instance.initialize();
        }
        return instance;
    }

    public void record(String command) {
        if (recorder == null || recorder.recordInMacros) {
            return;
        }

        recorder.record(command + "\n" + placeHolder);



        timer.start();

    }

}
