package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.Graphics;
import java.util.Vector;

class VoronoiClass extends Vector<VPaintable>
{
	VoronoiClass (int width, int height, int numberOfSites)
	{
		int count = 0;
		if(numberOfSites > 0)
		{
			int x = 540;
			int y = 300;

			int r = 50;
			for (int a = 0; a < 1000; a++) {
				for (int b = 0; b < 1000; b++) {
					if ((Math.pow(x-a,2) + Math.pow(y-b,2)) == Math.pow(r, 2))
						addElement(new VPoint(a, b));
				}
			}

			r = 100;
			for (int a = 0; a < 1000; a++) {
				for (int b = 0; b < 1000; b++) {
					if ((Math.pow(x-a,2) + Math.pow(y-b,2)) == Math.pow(r, 2))
						addElement(new VPoint(a, b));
				}
			}

			r = 150;
			for (int a = 0; a < 1000; a++) {
				for (int b = 0; b < 1000; b++) {
					if ((Math.pow(x-a,2) + Math.pow(y-b,2)) == Math.pow(r, 2))
						addElement(new VPoint(a, b));
				}
			}

			r = 200;
			//(x-a)^2+(y-b)^2=r^2
			for (int a = 0; a < 1000; a++) {
				for (int b = 0; b < 1000; b++) {
					if ((Math.pow(x-a,2) + Math.pow(y-b,2)) == Math.pow(r, 2))
						addElement(new VPoint(a, b));
				}
			}

			r = 250;
			for (int a = 0; a < 1000; a++) {
				for (int b = 0; b < 1000; b++) {
					if ((Math.pow(x-a,2) + Math.pow(y-b,2)) == Math.pow(r, 2))
						addElement(new VPoint(a, b));
				}
			}
		}
		System.out.println(count);
		checkDegenerate();
	}

	void checkDegenerate ()
	{
		if(size() > 1)
		{
			VPoint min = (VPoint)elementAt(0), next = min;
			for(int i = 1; i < size(); i++)
			{
				Object element = elementAt(i);
				if(element instanceof VPoint)
				{
					if(((VPoint)element).x <= min.x)
					{
						next = min;
						min = (VPoint)element;
					}
					else if(((VPoint)element).x <= min.x)
					{
						next = (VPoint)element;
					}
				}
			}

			if(min.x == next.x && min != next)
			{
				min.x--;
				System.out.println("Moved point: " + next.x + " -> " + min.x);
			}
		}
	}

	void paint (Graphics g, boolean flag)
	{
		for(int i = 0; i < size(); i++)
		{
			if(flag || !(elementAt(i) instanceof VLine))
				(elementAt(i)).paint(g);
		}
	}

	public void clear()
	{
		for(int i = 0; i < size(); i++)
		{
			if(elementAt(i) instanceof VLine)
				removeElementAt(i--);
		}
	}
}
