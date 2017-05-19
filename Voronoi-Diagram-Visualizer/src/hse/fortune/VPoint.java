package hse.fortune;/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.Graphics;
import java.awt.Point;

class VPoint implements VPaintable
{
    // Thread safe coordinates
    volatile double x, y;

    /* Various constructors */
	VPoint(double X, double Y)
	{
		x = X;
		y = Y;
	}

	VPoint(VPoint other)
	{
		x = other.x;
		y = other.y;
	}

	VPoint(Point other)
	{
		x = other.x;
		y = other.y;
	}

	public void paint(Graphics g)
	{
		g.fillOval((int)(x - 3.0), (int)(y - 3.0), 5, 5);
	}

	/**
	 * Method to calculate the distance
	 * between current VPoint other one
	 * @param other - second fortuneurn the distance (in double format) between these two points
	 */
	double getDistance(VPoint other)
	{
		double s1 = other.x - x;
		double s2 = other.y - y;
		return Math.sqrt(s1 * s1 + s2 * s2);
	}
}
