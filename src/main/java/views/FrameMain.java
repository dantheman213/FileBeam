package views;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        var btnFileMenuExit = new JMenuItem("Exit");
        btnFileMenuExit.addActionListener(btnFileMenuExit_Clicked());
        menuFile.add(btnFileMenuExit);

        menu.add(menuFile);
        frame.add(menu, BorderLayout.NORTH);

        var rootNode = new DefaultMutableTreeNode("Contacts");
        treeContactList = new JTree(rootNode);
        treeContactList.setRootVisible(false);
        frame.add(treeContactList, BorderLayout.CENTER);

        chkAllowDiscovery = new JCheckBox("Allow Discovery");
        chkAllowDiscovery.addActionListener(chkAllowDiscovery_Checked());
        frame.add(chkAllowDiscovery, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private ActionListener btnFileMenuExit_Clicked() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        };
    }

    private ActionListener chkAllowDiscovery_Checked() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (chkAllowDiscovery.isSelected()) {

                } else {

                }

                JOptionPane.showMessageDialog(null, String.format("Discovery set to %b", chkAllowDiscovery.isSelected())); // TODO: remove
            }
        };
    }
}
