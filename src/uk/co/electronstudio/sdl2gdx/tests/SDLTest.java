package uk.co.electronstudio.sdl2gdx.tests;

import com.badlogic.gdx.controllers.Controller;
import org.libsdl.SDL;
import uk.co.electronstudio.sdl2gdx.SDL2Controller;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;
import org.libsdl.SDL_Error;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A quick and dirty interface to check if a controller is working. I hope you like swing!
 */
public class SDLTest {
    public static int NUM_CONTROLLERS = 4;

    public static void main(String[] args) {
        SDL2ControllerManager controllerManager = new SDL2ControllerManager();

        //rumbleExample(controllerManager);
        //reflectionExample(controllerManager);

        JTabbedPane tabbedPane = new JTabbedPane();
        JFrame testFrame = new JFrame();
        SDLInfoPanel[] controllerTabs = setup(tabbedPane, testFrame);

        while (true) {
            mainLoop(controllerManager, testFrame, controllerTabs);
        }
    }

    private static void mainLoop(SDL2ControllerManager controllerManager, JFrame testFrame, SDLInfoPanel[] controllerTabs) {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            controllerManager.pollState();
        } catch (SDL_Error sdl_error) {
            sdl_error.printStackTrace();
        }
        for (int i = 0; i < controllerManager.getControllers().size; i++) {
            Controller controllerAtIndex = controllerManager.getControllers().get(i);
            controllerTabs[i].updatePanel((SDL2Controller) controllerAtIndex);
        }
        testFrame.repaint();
    }

    private static SDLInfoPanel[] setup(JTabbedPane tabbedPane, JFrame testFrame) {
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        testFrame.setLocationRelativeTo(null);
        testFrame.setMinimumSize(new Dimension(640, 350));
        testFrame.setResizable(true);
        testFrame.setVisible(true);

        SDLInfoPanel[] controllerTabs = new SDLInfoPanel[NUM_CONTROLLERS];
        for (int i = 0; i < NUM_CONTROLLERS; i++) {
            controllerTabs[i] = new SDLInfoPanel();
            tabbedPane.add("   Controller " + (i + 1) + "   ", controllerTabs[i]);

        }
        testFrame.setContentPane(tabbedPane);
        return controllerTabs;
    }

    private static void rumbleExample(SDL2ControllerManager controllerManager) {
        SDL2Controller controller = (SDL2Controller) controllerManager.getControllers().get(0);
        controller.rumble(1.0f, 1.0f, 500);
    }

    private static void reflectionExample(SDL2ControllerManager controllerManager) {
        Method method = null;
        Controller controller = controllerManager.getControllers().get(0);
        for (Method m : controller.getClass().getMethods()) {
            if (m.getName().equals("rumble")) method = m;
        }
        try {
            method.invoke(controller, 1f, 1f, 500);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * A JPanel that displays information about a given ControllerIndex.
     */
    public static class SDLInfoPanel extends JPanel {
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

}
