package worldUtils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

import utils.Edge;
import utils.Graph;

public class HeightGraph {
	
	private Hashtable<Point,Height> vertexes;
	private ArrayList<Edge> edges;
	
	private boolean meshIntact;
	
	
	public HeightGraph()
	{
		vertexes = new Hashtable<Point,Height>();
		edges = new ArrayList<Edge>();
		meshIntact = true;
	}
	
	public void addHeight(Point location, double value)
	{
		Point p = new Point(h.getX(), h.getY());
		if(!vertexes.containsKey(p))
		{
			meshIntact = false;
		}
		vertexes.put(p, h);
	}
	
	public void removeHeight()
	
	
}


 class Height extends Vertex  {

	
	private final int x;
	private final int y;
	
	public Height(double value, int x, int y) {
		super(value);
		this.x = x;
		this.y = y;
		
	}
	
	public void setValue(double value)
	{
		setLabel(value);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public double getValue()
	{
		return (double)super.getLabel();
	}
	
}

