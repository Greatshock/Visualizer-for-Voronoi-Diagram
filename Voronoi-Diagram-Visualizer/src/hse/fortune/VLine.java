package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.Graphics;

class VLine implements VPaintable
{
	private VPoint P1, P2;

	VLine(VPoint vPoint1, VPoint vPoint2)
	{
		P1 = vPoint1;
		P2 = vPoint2;
	}

	public void paint(Graphics g)
	{
		g.drawLine((int)P1.x, (int)P1.y, (int)P2.x, (int)P2.y);
	}
}
