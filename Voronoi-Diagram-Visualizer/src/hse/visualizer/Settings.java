package hse.visualizer;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import hse.fortune.VCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class Settings extends Panel implements ItemListener
{
	private VCanvas canvas;

    Settings(VCanvas vCanvas)
	{
		canvas = vCanvas;
		String as[] = { "Circle events", "Beachline", "Completed diagram" };
        Checkbox[] boxes = new Checkbox[as.length];
		for(int i = 0; i < as.length; i++)
		{
			boxes[i] = new Checkbox(as[i]);
			boxes[i].addItemListener(this);
			add(boxes[i]);
		}

		boxes[1].setState(true);
		boxes[2].setState(true);

        ImageIcon legend1 = new ImageIcon(getClass().getResource("/legend1.png"));
        ImageIcon legend2 = new ImageIcon(getClass().getResource("/legend2.png"));
        JLabel legend1Label = new JLabel("", legend1, JLabel.CENTER);
        JLabel legend2Label = new JLabel("", legend2, JLabel.CENTER);
        add(legend1Label);
        add(legend2Label);
	}

	public void itemStateChanged (ItemEvent itemEvent)
	{
		String s = itemEvent.getItem().toString();
		boolean flag = itemEvent.getStateChange() == ItemEvent.SELECTED;
        switch (s) {
            case "Circle events":
                canvas.drawCircles = flag;
                break;
            case "Beachline":
                canvas.drawBeach = flag;
                break;
            case "Completed diagram":
                canvas.drawVoronoiLines = flag;
                break;
        }
		canvas.repaint();
	}
}
