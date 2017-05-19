package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

class ParabolaPoint extends VPoint
{
    double a, b, c;

	ParabolaPoint(VPoint vPoint)
	{
		super(vPoint);
	}

	@SuppressWarnings("SuspiciousNameCombination")
    double realX()
	{
		return y;
	}

	private double realY(double d)
	{
		return d - x;
	}

	CirclePoint calculateCenter(VPoint vPoint1, ArcNode arcnode, VPoint vPoint2)
	{
		CirclePoint circlepoint = null;
		VPoint vPoint3 = new VPoint(arcnode.x - vPoint1.x, arcnode.y - vPoint1.y);
		VPoint vPoint4 = new VPoint(vPoint2.x - arcnode.x, vPoint2.y - arcnode.y);
		if(vPoint4.y * vPoint3.x > vPoint4.x * vPoint3.y)
		{
			double d = -vPoint3.x / vPoint3.y;
			double d1 = (vPoint1.y + vPoint3.y / 2D) - d * (vPoint1.x + vPoint3.x / 2D);
			double d2 = -vPoint4.x / vPoint4.y;
			double d3 = (arcnode.y + vPoint4.y / 2D) - d2 * (arcnode.x + vPoint4.x / 2D);
			double d4;
			double d5;
			if(vPoint3.y == 0.0D)
			{
				d4 = vPoint1.x + vPoint3.x / 2D;
				d5 = d2 * d4 + d3;
			} else
			if(vPoint4.y == 0.0D)
			{
				d4 = arcnode.x + vPoint4.x / 2D;
				d5 = d * d4 + d1;
			} else
			{
				d4 = (d3 - d1) / (d - d2);
				d5 = d * d4 + d1;
			}
			circlepoint = new CirclePoint(d4, d5, arcnode);
		}
		return circlepoint;
	}

	void init(double d)
	{
		double d1 = realX();
		double d2 = realY(d);
		a = 1.0D / (2D * d2);
		b = -d1 / d2;
		c = (d1 * d1) / (2D * d2) + d2 / 2D;
	}

	double F(double d)
	{
		return (a * d + b) * d + c;
	}

	double[] solveQuadratic(double d, double d1, double d2) throws Throwable
	{
		double ad[] = new double[2];
		double d3 = d1 * d1 - 4D * d * d2;

		if(d3 < 0.0D)
			throw new Throwable();
		if(d == 0.0D)
		{
			if(d1 != 0.0D)
				ad[0] = -d2 / d1;
			else
				throw new Throwable();
		}
		else
		{
			double d4 = Math.sqrt(d3);
			double d5 = -d1;
			double d6 = 2D * d;
			ad[0] = (d5 + d4) / d6;
			ad[1] = (d5 - d4) / d6;
		}
		return ad;
	}
}
