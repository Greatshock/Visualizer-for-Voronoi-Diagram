package hse.visualizer;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import hse.fortune.VCanvas;
import hse.utilities.Utilities;

import javax.swing.*;
import java.awt.*;


public class VDFrame extends JFrame implements Runnable {

    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 720;
    private static final String aboutText = "Voronoi Diagram Visualizer app allows you to watch how Voronoi Diagram\n" +
                                            "is being built step by step with Fortune algorithm to the defined set of "
                                            + "points\n"
                                            + "Designed by: Nikita Marinosyan\n"
                                            + "Email: nikita.marinosyan@gmail.com\n"
                                            + "Higher School of Economics, Faculty of Computer Science\n"
                                            + "2017";

    private VCanvas canvas;
    private Controls controls;
    private Thread thread;

    private VDFrame()
    {
        init();

        start();
        controls.buttons[5].doClick();
        controls.buttons[0].doClick();
    }

    /**
     * Method to init the frame
     */
    private void init()
    {
        // Set default properties
        onPaint();

        // Create menu
        createMenu();

        // Create the canvas, controls and import settings
        setLayout(new BorderLayout());

        canvas = new VCanvas(this.getWidth(), this.getHeight() - 115, 32);

        add(BorderLayout.NORTH, new Settings(canvas));
        add(BorderLayout.SOUTH, controls = new Controls(canvas));
        add(BorderLayout.CENTER, canvas);
    }

    /**
     * Method that starts the thread
     */
    private void start()
    {
        if(thread == null)
        {
            controls.thread = new Thread(this);
            thread = controls.thread;
            thread.start();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run()
    {
        if(thread != null)
        {
            while(true)
            {
                canvas.init();
                while(canvas.singleStep())
                {
                    try
                    {
                        Thread.sleep(25L);
                    }
                    catch(InterruptedException _ex)
                    {
                        System.out.print("Program has been interrupted");
                    }
                }
                controls.threadRunning(false);
            }
        }
    }

    /**
     * Method to set the default values to the frame's properties
     */
    private void onPaint()
    {

        setTitle("Voronoi Diagram Visualizer (Fortune Algorithm)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
    }

    /**
     * Method which creates the menu bar
     */
    private void createMenu()
    {

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        /*--------------------------------------------- Create separator ---------------------------------------------*/
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setMaximumSize(new Dimension(10, 50));

        /*------------------------------------------- Create help menu -----------------------------------------------*/
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem manual = new JMenuItem("Manual");
        JFrame manualWindow = new JFrame("Manual");
        JTextPane manualText = new JTextPane();
        manualText.setEditable(false);
        manualText.setContentType("text/html");
        manualText.setText(Utilities.readAllTextFromResource("manual.html"));
        manualText.setCaretPosition(0);
        manualWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        manualWindow.setSize(700, 700);
        manualWindow.setResizable(false);
        JScrollPane jsp = new JScrollPane(manualText);

        manualWindow.add(jsp);
        manualWindow.setLocation(this.getX() + 380, this.getY());
        manual.addActionListener(e -> openManual(manualWindow));
        helpMenu.add(manual);

        JMenuItem about = new JMenuItem("About the program");
        helpMenu.add(about).addActionListener(e -> openAbout());

        menuBar.add(separator);
    }

    /**
     * Method to handle manual menu button pressing
     * Opens new window with complete manual on the app
     */
    private void openManual(JFrame manualWindow)
    {
        controls.buttons[0].doClick();
        manualWindow.setVisible(true);
    }

    /**
     * Method to handle aboutMenu button pressing
     * Opens new window with brief info about the app
     */
    private void openAbout()
    {
        // Pause visualization
        controls.buttons[0].doClick();

        // Show about frame
        JOptionPane.showMessageDialog(this, aboutText);
    }

    public static void main(String[] args)
    {
        VDFrame vdFrame = new VDFrame();
        vdFrame.setVisible(true);
    }
}
