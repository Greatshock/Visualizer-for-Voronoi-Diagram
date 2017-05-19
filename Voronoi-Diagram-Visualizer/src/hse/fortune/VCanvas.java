package hse.fortune;

/*
 * @author Nikita Marinosyan
 * @date 20.04.2017
 * Higher School of Economics,
 * Faculty of Computer Science,
 * Department of Software Engineering
 */

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class VCanvas extends Canvas implements MouseListener
{
    private Graphics offScreenGraphics;
    private BufferedImage offScreenImage;
    int XPos;
    VoronoiClass Voronoi;
    public boolean drawCircles, drawBeach, drawVoronoiLines;
    VEventQueue Events;
    ArcTree Arcs;
    private int width, height;

	public VCanvas(int width, int height, int numberOfSites)
	{
		drawCircles = false;
		drawBeach = true;
		drawVoronoiLines = true;
		addMouseListener(this);
		Voronoi = new VoronoiClass(width, height, numberOfSites);
		this.width = width;
		this.height = height;
	}

    public synchronized void init()
	{
		offScreenImage = new BufferedImage(width, height, 1);
		offScreenGraphics = offScreenImage.getGraphics();
		XPos = 0;
		Arcs = new ArcTree();
		Events = new VEventQueue();
		Voronoi.clear();

		for(int i = 0; i < Voronoi.size(); i++)
			Events.insert(new VEventPoint((VPoint)Voronoi.elementAt(i)));
	}

    public synchronized void paint(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getBounds().width, getBounds().height);

        g.setColor(Color.BLACK);
        ((Graphics2D)g).setStroke(new BasicStroke(3));
        Voronoi.paint(g, drawVoronoiLines);

        g.setColor(Color.RED);
        ((Graphics2D)g).setStroke(new BasicStroke(3));
        g.drawLine(XPos, 0, XPos, getBounds().height);

        if(Events != null && Arcs != null)
        {
            g.setColor(Color.BLUE);
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            Events.paint(g, drawCircles);

            g.setColor(new Color(13, 136, 0));
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            Arcs.paint(g, XPos, drawVoronoiLines, drawBeach);
        }
    }

    public void update(Graphics g)
    {
        offScreenGraphics.setClip(g.getClipBounds());
        paint(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public synchronized boolean singleStep()
    {
        if(Events.Events == null || (double)XPos < Events.Events.x)
            XPos++;

        while(Events.Events != null && (double)XPos >= Events.Events.x)
        {
            VEventPoint vEventPoint = Events.pop();
            XPos = Math.max(XPos, (int)vEventPoint.x);
            vEventPoint.performAction(this);
            Arcs.checkBounds(this, XPos);
        }

        if(XPos > getBounds().width && Events.Events == null)
            Arcs.checkBounds(this, XPos);

        repaint();
        return Events.Events != null || XPos < 1000 + getBounds().width;
    }

    public synchronized void step()
    {
        VEventPoint vEventPoint = Events.pop();
        if(vEventPoint != null)
        {
            XPos = Math.max(XPos, (int)vEventPoint.x);
            vEventPoint.performAction(this);
        } else
        if(XPos < getBounds().width)
        {
            XPos = getBounds().width;
        } else
        {
            init();
        }
        Arcs.checkBounds(this, XPos);
        repaint();
    }

    public synchronized void clear()
    {
        Voronoi = new VoronoiClass(getBounds().width, getBounds().height, 0);
        restart();
    }

    public synchronized void restart()
    {
        init();
        repaint();
    }

    @SuppressWarnings("unchecked")
    public synchronized void mousePressed(MouseEvent mouseEvent)
    {
        VPoint vPoint = new VPoint(mouseEvent.getPoint());
        if(vPoint.x > (double)XPos)
        {
            Voronoi.addElement(vPoint);
            Voronoi.checkDegenerate();
            Events.insert(new VEventPoint(vPoint));
            repaint();
        }
    }

    /**
     * Methods below are empty because we need to implement MouseListener
     */
	public void mouseClicked(MouseEvent mouseevent)
	{
	}

	public void mouseReleased(MouseEvent mouseevent)
	{
	}

	public void mouseEntered(MouseEvent mouseevent)
	{
	}

	public void mouseExited(MouseEvent mouseevent)
	{
	}
}
