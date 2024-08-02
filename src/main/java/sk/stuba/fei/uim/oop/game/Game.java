package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static final String RESTART = "Restart";
    public static final String CHECK = "Check";


    public Game(){

        JFrame frame = new JFrame("pIpes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720,780);
        frame.getContentPane().setBackground(Color.BLACK );
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        GameLogic logic = new GameLogic(frame);
        frame.addKeyListener(logic);

        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.PINK);

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8,12,8);
        slider.setBackground(Color.PINK);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);

        JButton resetButton = new JButton(RESTART);
        resetButton.setBackground(Color.PINK);
        resetButton.setFocusable(false);
        resetButton.addActionListener(logic);

        JButton checkButton = new JButton(CHECK);
        checkButton.setBackground(Color.PINK);
        checkButton.setFocusable(false);
        checkButton.addActionListener(logic);


        sideMenu.setLayout(new GridLayout(1,5,0,0));
        sideMenu.add(checkButton);
        sideMenu.add(resetButton);
        sideMenu.add(logic.getLevelLabel());
        sideMenu.add(slider);
        sideMenu.add(logic.getBoardSizeLabel());

        frame.add(sideMenu, BorderLayout.PAGE_END);
        frame.setVisible(true);


    }
}
