package dev.triangulation.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;

public class GUIPanel extends JPanel {
    public GUIPanel(SortedSet<DrawablePoint> points, final DisplayPanel displayPanel) {

        Color color = Color.PINK;

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel infoPanel = new JPanel();
        JPanel launchPanel = new JPanel();

        JLabel label1 = new JLabel("Hey ça marche");
        infoPanel.add(label1);

        JButton buttonClear = new JButton("Effacer");
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.clear();
            }
        });

        JButton buttonLaunch = new JButton("Calculer");
        launchPanel.add(buttonClear);
        launchPanel.add(buttonLaunch);

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);

        infoPanel.setBackground(color);
        launchPanel.setBackground(color);
        separator1.setBackground(color);
        mainPanel.setBackground(color);
        this.setBackground(color);

        mainPanel.add(infoPanel);
        mainPanel.add(separator1, BorderLayout.CENTER);
        mainPanel.add(launchPanel);

        this.add(mainPanel);
    }
}
