package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

class VEventPoint extends VPoint
{
    VEventPoint Prev, Next;

	VEventPoint(VPoint vPoint)
	{
		super(vPoint);
	}

	VEventPoint(double x, double y)
	{
		super(x, y);
	}

	void insert(VEventPoint vEventPoint)
	{
		if(vEventPoint.x > x || vEventPoint.x == x && vEventPoint.y > y)
		{
			if(Next != null)
			{
				Next.insert(vEventPoint);
				return;
			} else
			{
				Next = vEventPoint;
				vEventPoint.Prev = this;
				return;
			}
		}
		if(vEventPoint.x != x || vEventPoint.y != y || (vEventPoint instanceof CirclePoint))
		{
			vEventPoint.Prev = Prev;
			vEventPoint.Next = this;
			if(Prev != null)
				Prev.Next = vEventPoint;
			Prev = vEventPoint;
		}
		else
		{
			vEventPoint.Prev = vEventPoint;
			System.out.println("Double point ignored: " + vEventPoint.toString());
		}
	}

	public void performAction(VCanvas mycanvas)
	{
		mycanvas.Arcs.insert(this, mycanvas.XPos, mycanvas.Events);
	}
}
