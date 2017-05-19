package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.*;

class ArcNode extends ParabolaPoint
{

	ArcNode Next, Prev;
	CirclePoint circlePoint;
	VPoint startOfTrace;

	ArcNode (VPoint vPoint)
	{
		super(vPoint);
	}

	void checkCircle (VEventQueue vEventQueue)
	{
		if(Prev != null && Next != null)
		{
			circlePoint = calculateCenter(Next, this, Prev);
			if(circlePoint != null)
				vEventQueue.insert(circlePoint);
		}
	}

	private void removeCircle (VEventQueue vEventQueue)
	{
		if(circlePoint != null)
		{
			vEventQueue.remove(circlePoint);
			circlePoint = null;
		}
	}

	@SuppressWarnings("unchecked")
	void completeTrace (VCanvas vCanvas, VPoint mypoint)
	{
		if(startOfTrace != null)
		{
			vCanvas.Voronoi.addElement(new VLine(startOfTrace, mypoint));
			startOfTrace = null;
		}
	}

	void checkBounds (VCanvas vCanvas, double d)
	{
		if(Next != null)
		{
			Next.init(d);
			if(d > Next.x && d > x && startOfTrace != null)
			{
				try
				{
					double ad[] = solveQuadratic(a - Next.a, b - Next.b, c - Next.c);
					double d1 = ad[0];
					double d2 = d - F(d1);
					Rectangle rectangle = vCanvas.getBounds();
					if(d2 < startOfTrace.x && d2 < 0.0D || d1 < 0.0D || d2 >= (double)rectangle.width || d1 >= (double)rectangle.height)
						completeTrace(vCanvas, new VPoint(d2, d1));
				}
				catch(Throwable _ex)
				{
					System.out.println("*** exception");
				}
			}
			Next.checkBounds(vCanvas, d);
		}
	}

	void insert (ParabolaPoint parabolapoint, double sweepLine, VEventQueue vEventQueue)
		throws Throwable
	{
		boolean split = true;
		if(Next != null)
		{
			Next.init(sweepLine);
			if(sweepLine > Next.x && sweepLine > x)
			{
				double xs[] = solveQuadratic(a - Next.a, b - Next.b, c - Next.c);
				if(xs[0] <= parabolapoint.realX() && xs[0] != xs[1])
					split = false;
			}
			else
			{
				split = false;
			}
		}

		if(split)
		{
			removeCircle(vEventQueue);

			ArcNode arcnode = new ArcNode(parabolapoint);
			arcnode.Next = new ArcNode(this);
			arcnode.Prev = this;
			arcnode.Next.Next = Next;
			arcnode.Next.Prev = arcnode;

			if(Next != null)
				Next.Prev = arcnode.Next;

			Next = arcnode;

			checkCircle(vEventQueue);
			Next.Next.checkCircle(vEventQueue);

			Next.Next.startOfTrace = startOfTrace;
			startOfTrace = new VPoint(sweepLine - F(parabolapoint.y), parabolapoint.y);
			Next.startOfTrace = new VPoint(sweepLine - F(parabolapoint.y), parabolapoint.y);
		}
		else
		{
			Next.insert(parabolapoint, sweepLine, vEventQueue);
		}
	}

	void paint (Graphics g, double d, double d1, boolean flag, boolean drawBeachline)
	{
		double d2 = g.getClipBounds().height;
		ArcNode arcnode = Next;
		if(arcnode != null)
		{
			arcnode.init(d);
		}
		if(d == x)
		{
			double d3 = arcnode != null ? d - arcnode.F(y) : 0.0D;
			if(drawBeachline)
				g.drawLine((int)d3, (int)y, (int)d, (int)y);
			d2 = y;
		}
		else
		{
			if(arcnode != null)
			{
				if(d == arcnode.x)
				{
					d2 = arcnode.y;
				}
				else
				{
					try
					{
						double ad[] = solveQuadratic(a - arcnode.a, b - arcnode.b, c - arcnode.c);
						d2 = ad[0];
					}
					catch(Throwable _ex)
					{
						d2 = d1;
						System.out.println("*** error: No parabola intersection during ArcNode - SLine: " +
                                d + ", " + toString() + " " + arcnode.toString());
					}
				}
			}

			if(drawBeachline)
			{
				int i = 1;
				double d4 = 0.0D;
				for(double d5 = d1; d5 < Math.min(Math.max(0.0D, d2), g.getClipBounds().height); d5 += i)
				{
					double d6 = d - F(d5);
					if(d5 > d1 && (d4 >= 0.0D || d6 >= 0.0D))
					{
						g.drawLine((int)d4, (int)(d5 - (double)i), (int)d6, (int)d5);
					}
					d4 = d6;
				}
			}

			if(flag && startOfTrace != null)
			{
				double d7 = d - F(d2);
                g.getClipBounds();
				g.getClipBounds();
				g.drawLine((int)startOfTrace.x, (int)startOfTrace.y, (int)d7, (int)d2);
			}
		}

		if(Next != null)
			Next.paint(g, d, Math.max(0.0D, d2), flag, drawBeachline);
	}
}
