package dev.triangulation.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;

public class GUIPanel extends JPanel {
    public GUIPanel(final DisplayPanel displayPanel) {

        Color color = Color.PINK;

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel infoPanel = new JPanel();
        JPanel randomizePanel = new JPanel();
        randomizePanel.setLayout(new BoxLayout(randomizePanel, BoxLayout.PAGE_AXIS));
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

        JPanel setNumberPointsPanel = new JPanel();
        setNumberPointsPanel.setBackground(color);
        JLabel labelRandomize = new JLabel("Nombre de points :");
        labelRandomize.setBackground(color);

        final SpinnerNumberModel numberOfPointsRandomModel = new SpinnerNumberModel(3, 3, 420, 1);
        final JSpinner numberOfPointsInput = new JSpinner(numberOfPointsRandomModel);
        numberOfPointsInput.setBackground(color);
        setNumberPointsPanel.add(labelRandomize);
        setNumberPointsPanel.add(numberOfPointsInput);

        JButton buttonRandomize = new JButton("Generer");
        buttonRandomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.newPoints(PointsGenerator.generate(numberOfPointsRandomModel.getNumber().intValue(), displayPanel.getWidth(), displayPanel.getHeight()));
            }
        });
        randomizePanel.add(setNumberPointsPanel);
        randomizePanel.add(buttonRandomize);

        JButton buttonLaunch = new JButton("Calculer");
        launchPanel.add(buttonClear);
        launchPanel.add(buttonLaunch);

        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);

        infoPanel.setBackground(color);
        randomizePanel.setBackground(color);
        launchPanel.setBackground(color);
        separator1.setBackground(color);
        separator2.setBackground(color);
        mainPanel.setBackground(color);
        this.setBackground(color);

        mainPanel.add(infoPanel);
        mainPanel.add(separator1, BorderLayout.CENTER);
        mainPanel.add(randomizePanel);
        mainPanel.add(separator2, BorderLayout.CENTER);
        mainPanel.add(launchPanel);

        this.add(mainPanel);
    }
}
