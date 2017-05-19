package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.Graphics;

class VEventQueue
{
	VEventPoint Events;

    /**
     * Inserts the defined event to the queue
     * @param vEventPoint - the event to be inserted
     */
	void insert (VEventPoint vEventPoint)
	{
		if(Events != null)
			Events.insert(vEventPoint);

		if(vEventPoint.Prev == null)
			Events = vEventPoint;
	}

    /**
     * Removes the event from the queue
     * @param vEventPoint - the event to be removed
     */
	void remove (VEventPoint vEventPoint)
	{
		if(vEventPoint.Next != null)
			vEventPoint.Next.Prev = vEventPoint.Prev;

		if(vEventPoint.Prev != null)
				vEventPoint.Prev.Next = vEventPoint.Next;
		else	Events = vEventPoint.Next;
	}

    /**
     * Pops the event from the queue
     * @return popped event
     */
	VEventPoint pop ()
	{
		VEventPoint vEventPoint = Events;
		if(vEventPoint != null)
		{
			Events = Events.Next;
			if(Events != null)
			{
				Events.Prev = null;
			}
		}
		return vEventPoint;
	}

	void paint(Graphics g, boolean flag)
	{
		for(VEventPoint vEventPoint = Events; vEventPoint != null; vEventPoint = vEventPoint.Next)
		{
			if(flag || !(vEventPoint instanceof CirclePoint))
				vEventPoint.paint(g);
		}

	}
}
