package app.views;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.KeyEvent;

public class FrameMain {
    private JFrame frame;
    private JMenuBar menu;
    private JTree treeContactList;
    private JCheckBox chkAllowDiscovery;

    public void show() throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        frame = new JFrame("FileBeam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null); // center frame to screen

        frame.setLayout(new BorderLayout());

        menu = new JMenuBar();
        var menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuFile.add(new JMenuItem("Exit"));
        menu.add(menuFile);
        frame.add(menu, BorderLayout.NORTH);

        var rootNode = new DefaultMutableTreeNode("Contacts");
        treeContactList = new JTree(rootNode);
        treeContactList.setRootVisible(false);
        frame.add(treeContactList, BorderLayout.CENTER);

        chkAllowDiscovery = new JCheckBox("Allow Discovery");
        frame.add(chkAllowDiscovery, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
