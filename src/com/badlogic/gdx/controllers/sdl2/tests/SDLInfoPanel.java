package com.badlogic.gdx.controllers.sdl2.tests;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.sdl2.SDL2Controller;
import com.studiohartman.jamepad.ControllerAxis;
import com.studiohartman.jamepad.ControllerButton;
import com.studiohartman.jamepad.ControllerIndex;
import com.studiohartman.jamepad.ControllerUnpluggedException;
import org.libsdl.SDL;
import org.libsdl.SDL_Error;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * A JPanel that displays information about a given ControllerIndex.
 */
public class SDLInfoPanel extends JPanel {
    private JPanel title;
    private JPanel axes;
    private JPanel buttons;
    private JSlider leftRumble, rightRumble;
    private JButton vibrateButton;
    private JLabel titleLabel;

    public SDLInfoPanel() {
        setLayout(new BorderLayout());

        title = new JPanel();
        axes = new JPanel();
        buttons = new JPanel();

        JPanel vibratePanel = new JPanel();
        vibrateButton = new JButton("Rumble");
        leftRumble = new JSlider(0, 100, 100);
        rightRumble = new JSlider(0, 100, 100);

        vibratePanel.add(leftRumble);
        vibratePanel.add(rightRumble);
        vibratePanel.add(vibrateButton);


        title.setLayout(new BoxLayout(title, BoxLayout.Y_AXIS));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel = new JLabel();
        title.add(titleLabel);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.add(title);
        middlePanel.add(axes);
        middlePanel.add(buttons);

        add(middlePanel);
        add(vibratePanel, BorderLayout.SOUTH);
    }

    public void updatePanel(SDL2Controller c) {
        try {
            titleLabel.setText(c.getName());

            axes.removeAll();
            for (int i = 0; i < c.joystick.numAxes(); i++) {
                JLabel label = new JLabel();
                label.setPreferredSize(new Dimension(100, 30));
                label.setText(SDL.SDL_GameControllerGetStringForAxis(i));

                JProgressBar progressBar = new JProgressBar(-100, 100);
                progressBar.setPreferredSize(new Dimension(200, 30));
                progressBar.setValue((int) (c.getAxis(i) * 100));

                JPanel axisPanel = new JPanel();
                axisPanel.setLayout(new BoxLayout(axisPanel, BoxLayout.X_AXIS));
                axisPanel.add(label);
                axisPanel.add(progressBar);
                axes.add(axisPanel);
            }

            buttons.removeAll();
            for (int i = 0; i < c.joystick.numButtons(); i++) {
                JButton button = new JButton(SDL.SDL_GameControllerGetStringForButton(i));
                button.setEnabled(c.getButton(i));
                buttons.add(button);
            }

            Arrays.stream(vibrateButton.getActionListeners()).forEach(vibrateButton::removeActionListener);
            vibrateButton.addActionListener(event -> {
                c.rumble(leftRumble.getValue() / 100f, rightRumble.getValue() / 100f, 1000);

            });

        } catch (SDL_Error e) {
            e.printStackTrace();

            titleLabel.setText("a Jamepad runtime exception occurred!");
            axes.removeAll();
            buttons.removeAll();

            axes.add(new JLabel(e.getMessage()));
        }
    }

    public void setAsDisconnected() {
        titleLabel.setText("No controller connected at this index!");
        axes.removeAll();
        buttons.removeAll();
    }
}
