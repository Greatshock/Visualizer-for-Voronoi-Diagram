package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.Graphics;

class CirclePoint extends VEventPoint
{
	private double radius;
	private ArcNode arc;

	CirclePoint(double X, double Y, ArcNode arcnode)
	{
		super(X, Y);
		arc = arcnode;
		radius = getDistance(arcnode);
		x += radius;
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		double d = radius;
		g.drawOval((int)(x - 2D * d), (int)(y - d), (int)(2D * d), (int)(2D * d));
	}

	public void performAction(VCanvas vCanvas)
	{
		ArcNode arcNode1 = arc.Prev;
		ArcNode arcNode2 = arc.Next;
		VPoint vPoint = new VPoint(x - radius, y);

		arc.completeTrace(vCanvas, vPoint);
		arcNode1.completeTrace(vCanvas, vPoint);
		arcNode1.startOfTrace = vPoint;
		arcNode1.Next = arcNode2;
		arcNode2.Prev = arcNode1;

		if(arcNode1.circlePoint != null)
		{
			vCanvas.Events.remove(arcNode1.circlePoint);
			arcNode1.circlePoint = null;
		}
		if(arcNode2.circlePoint != null)
		{
			vCanvas.Events.remove(arcNode2.circlePoint);
			arcNode2.circlePoint = null;
		}
		arcNode1.checkCircle(vCanvas.Events);
		arcNode2.checkCircle(vCanvas.Events);
	}
}
