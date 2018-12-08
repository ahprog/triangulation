package dev.triangulation.graphics;

import dev.triangulation.compute.PointsGenerator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class GUIPanel extends JPanel {

    public GUIPanel(final DisplayPanel displayPanel) {

        Color color = Color.PINK;
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 13);


        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel infoPanel = new JPanel();
        JPanel randomizePanel = new JPanel(new GridLayout(2, 1));
        JPanel dynamicPanel = new JPanel(new GridLayout(1, 1));
        JPanel choicesPanel = new JPanel(new GridLayout(3, 1));
        final JPanel launchPanel = new JPanel(new GridLayout(1, 2, 5, 0));

        JLabel label1 = new JLabel("<html><b>Triangulator</b><br/>Hey ça marche</html>");
        label1.setFont(font);
        infoPanel.add(label1);

        // Création points random
        JPanel setNumberPointsPanel = new JPanel();
        setNumberPointsPanel.setBackground(color);
        setNumberPointsPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel labelRandomize = new JLabel("Nombre de points :");
        labelRandomize.setFont(font);

        final SpinnerNumberModel numberOfPointsRandomModel = new SpinnerNumberModel(3, 3, 800, 1);
        final JSpinner numberOfPointsInput = new JSpinner(numberOfPointsRandomModel);
        setNumberPointsPanel.add(labelRandomize);
        setNumberPointsPanel.add(numberOfPointsInput);

        JButton buttonRandomize = new JButton("Generer");
        buttonRandomize.setFont(font);
        buttonRandomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.newPoints(PointsGenerator.generate(numberOfPointsRandomModel.getNumber().intValue(), displayPanel.getWidth(), displayPanel.getHeight()));
                displayPanel.computeShapesDynamic(true);
            }
        });
        randomizePanel.add(setNumberPointsPanel);
        randomizePanel.add(buttonRandomize);



        //Panel pour trianguler dynamiquement
        JCheckBox dynamicCheckBox = new JCheckBox("Triangulation dynamique");
        dynamicCheckBox.setSelected(true);
        dynamicCheckBox.setFont(font);
        dynamicCheckBox.setBackground(color);
        dynamicPanel.add(dynamicCheckBox);


        //Panel des choix d'affichages
        final JCheckBox convexHullCheckBox = new JCheckBox("Enveloppe convexe");
        convexHullCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                displayPanel.setConvexHullVisible((e.getStateChange() == ItemEvent.SELECTED));
                displayPanel.computeShapesDynamic(false);
            }
        });
        convexHullCheckBox.setFont(font);
        convexHullCheckBox.setBackground(color);

        JPanel triangulationChoicePanel = new JPanel();
        triangulationChoicePanel.setBorder(new EmptyBorder(0, 20, 0, 0));
        triangulationChoicePanel.setLayout(new BoxLayout(triangulationChoicePanel, BoxLayout.PAGE_AXIS));
        triangulationChoicePanel.setBackground(color);

        final JRadioButton simpleTriangulationRadio = new JRadioButton("simple");
        simpleTriangulationRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                displayPanel.setTriangulationVisible((e.getStateChange() == ItemEvent.SELECTED));
                displayPanel.computeShapesDynamic(false);
            }
        });
        simpleTriangulationRadio.setEnabled(false);
        simpleTriangulationRadio.setFont(font);
        simpleTriangulationRadio.setBackground(color);

        final JRadioButton delaunayTriangulationRadio = new JRadioButton("de Delaunay");
        delaunayTriangulationRadio.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                displayPanel.setTriangulationDelaunayVisible((e.getStateChange() == ItemEvent.SELECTED));
                displayPanel.computeShapesDynamic(false);
        }
        });
        delaunayTriangulationRadio.setEnabled(false);
        delaunayTriangulationRadio.setFont(font);
        delaunayTriangulationRadio.setBackground(color);

        final JCheckBox triangulationCheckBox = new JCheckBox("Triangulation :");
        triangulationCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED) {
                    displayPanel.setTriangulationVisible(false);
                    displayPanel.setTriangulationDelaunayVisible(false);
                    simpleTriangulationRadio.setEnabled(false);
                    delaunayTriangulationRadio.setEnabled(false);
                }
                else {
                    if (simpleTriangulationRadio.isSelected()) {
                        displayPanel.setTriangulationVisible(true);
                    }
                    else if (delaunayTriangulationRadio.isSelected()){
                        displayPanel.setTriangulationDelaunayVisible(true);
                    }
                    simpleTriangulationRadio.setEnabled(true);
                    delaunayTriangulationRadio.setEnabled(true);
                }
                displayPanel.computeShapesDynamic(false);
            }
        });
        triangulationCheckBox.setFont(font);
        triangulationCheckBox.setBackground(color);

        ButtonGroup triangulationChoiceGroup = new ButtonGroup();
        triangulationChoiceGroup.add(simpleTriangulationRadio);
        triangulationChoiceGroup.add(delaunayTriangulationRadio);
        triangulationChoicePanel.add(simpleTriangulationRadio);
        triangulationChoicePanel.add(delaunayTriangulationRadio);

        choicesPanel.add(convexHullCheckBox);
        choicesPanel.add(triangulationCheckBox);
        choicesPanel.add(triangulationChoicePanel);




        //Launch panel
        final JButton buttonClear = new JButton("Effacer");
        buttonClear.setFont(font);
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                triangulationCheckBox.setSelected(false);
                convexHullCheckBox.setSelected(false);
                displayPanel.clear();
            }
        });

        launchPanel.add(buttonClear);

        //Bouton Calcul triangulation quelconque

        final JButton buttonTriangulation = new JButton("Calculer");
        buttonTriangulation.setEnabled(false);
        buttonTriangulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPanel.computeShapes();
            }
        });
        buttonTriangulation.setFont(font);
        launchPanel.add(buttonTriangulation);

        dynamicCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                buttonTriangulation.setEnabled(e.getStateChange() != ItemEvent.SELECTED);
                displayPanel.switchDynamicCompute();
            }
        });



        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
        JSeparator separator4 = new JSeparator(SwingConstants.HORIZONTAL);

        infoPanel.setBackground(color);
        randomizePanel.setBackground(color);
        launchPanel.setBackground(color);
        dynamicPanel.setBackground(color);
        choicesPanel.setBackground(color);
        separator1.setBackground(color);
        separator2.setBackground(color);
        separator3.setBackground(color);
        separator4.setBackground(color);
        mainPanel.setBackground(color);
        this.setBackground(color);

        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        randomizePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        launchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        dynamicPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        choicesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        mainPanel.add(infoPanel);
        mainPanel.add(separator1, BorderLayout.CENTER);
        mainPanel.add(randomizePanel);
        mainPanel.add(separator2, BorderLayout.CENTER);
        mainPanel.add(dynamicPanel);
        mainPanel.add(separator3, BorderLayout.CENTER);
        mainPanel.add(choicesPanel);
        mainPanel.add(separator4, BorderLayout.CENTER);
        mainPanel.add(launchPanel);

        this.add(mainPanel);
    }
}
