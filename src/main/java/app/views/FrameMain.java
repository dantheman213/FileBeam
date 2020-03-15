package app.views;

import javax.swing.*;
import java.awt.*;

public class FrameMain {
    private JFrame frame;

    public void show() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        frame = new JFrame("FileBeam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null); // center frame to screen

        frame.setLayout(new BorderLayout());

        var tree = new JTree();
        frame.add(tree, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
