package hse.visualizer;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import hse.fortune.VCanvas;

class Controls extends Panel implements ActionListener
{

    private VCanvas canvas;
    Thread thread;
	private boolean running;
    JButton buttons[];

	Controls(VCanvas vCanvas)
	{
		running = true;
		canvas = vCanvas;
		String as[] = { "Pause", "Start/Resume", "Next event", "Step forward",
                "Clear", "Restart" };

		buttons = new JButton[as.length];
		for(int i = 0; i < as.length; i++)
		{
			buttons[i] = new JButton(as[i]);
			if ((i % 2 == 0 && i != 0))
                add(Box.createRigidArea(new Dimension(70, 0)));
			buttons[i].addActionListener(this);
			add(buttons[i]);
			buttons[i].setFocusPainted(false);
		}

        buttons[1].setEnabled(false);
		buttons[3].setEnabled(false);
	}

	public void actionPerformed(ActionEvent actionevent) {

	    String s = actionevent.getActionCommand();

        switch (s)
        {
            case "Pause":
                threadRunning(false);
                break;
            case "Start/Resume":
            	threadRunning(true);
                break;
            case "Next event":
                canvas.step();
                return;
            case "Step forward":
                canvas.singleStep();
                return;
            case "Clear":
                threadRunning(false);
                canvas.clear();
                return;
            case "Restart":
                canvas.restart();
                threadRunning(true);
                break;
        }
    }

	@SuppressWarnings("deprecation")
    void threadRunning(boolean flag)
	{
		if(flag != running)
		{
			if(running = flag)
			{
				buttons[0].setEnabled(true);
				buttons[1].setEnabled(false);
				buttons[3].setEnabled(false);
				thread.resume();
				return;
			}
			buttons[0].setEnabled(false);
			buttons[1].setEnabled(true);
			buttons[3].setEnabled(true);
			thread.suspend();
		}
	}
}
