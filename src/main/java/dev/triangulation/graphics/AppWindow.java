package dev.triangulation.graphics;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.SortedSet;
import javax.swing.*;
import javax.swing.border.Border;

public class AppWindow extends JFrame {

    public AppWindow(int width, int height, SortedSet<DrawablePoint> points) {
        this.setPreferredSize(new Dimension(width, height));

        WindowAdapter windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        };

        DisplayPanel displayPanel = new DisplayPanel(points);
        displayPanel.setBackground(Color.WHITE);

        GUIPanel guiPanel = new GUIPanel(points);
        guiPanel.setPreferredSize(new Dimension(200, height));
        guiPanel.setMaximumSize(new Dimension(200, height));
        guiPanel.setBackground(Color.PINK);

        JPanel mainPanel = new JPanel();
        BoxLayout borderLayout = new BoxLayout(mainPanel, BoxLayout.X_AXIS);
        mainPanel.setLayout(borderLayout);

        mainPanel.add(displayPanel, BorderLayout.EAST);
        mainPanel.add(guiPanel, BorderLayout.WEST);

        this.setMinimumSize(new Dimension(400, 300));
        this.addWindowListener(windowAdapter);
        this.add(mainPanel);
        this.pack();
        this.setVisible(true);
    }
}
