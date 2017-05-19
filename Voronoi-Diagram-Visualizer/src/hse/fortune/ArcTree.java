package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.Graphics;

class ArcTree
{
    private ArcNode Arcs;

	void insert (VPoint mypoint, double d, VEventQueue eventqueue)
	{
		if(Arcs == null)
		{
			Arcs = new ArcNode(mypoint);
			return;
		}

		try
		{
			ParabolaPoint parabolapoint = new ParabolaPoint(mypoint);
			parabolapoint.init(d);
			Arcs.init(d);
			Arcs.insert(parabolapoint, d, eventqueue);
		}
		catch(Throwable _ex)
		{
			System.out.println("*** error: No parabola intersection during fortune)");
		}
	}

	void checkBounds (VCanvas vCanvas, double d)
	{
		if(Arcs != null)
		{
			Arcs.init(d);
			Arcs.checkBounds(vCanvas, d);
		}
	}

	void paint (Graphics g, double d, boolean flag, boolean drawBeach)
	{
		if(Arcs != null)
		{
			Arcs.init(d);
			Arcs.paint(g, d, 0.0D, flag, drawBeach);
		}
	}
}
