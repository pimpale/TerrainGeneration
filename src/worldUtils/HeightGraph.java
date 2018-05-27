package worldUtils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;

import utils.Edge;
import utils.Graph;

public class HeightGraph {
	
	private Hashtable<Point,Double> vertexes;
	private ArrayList<Edge> edges;
	
	private boolean meshIntact;
	
	
	public HeightGraph()
	{
		vertexes = new Hashtable<Point,Double>();
		edges = new ArrayList<Edge>();
		meshIntact = true;
	}
	
	public boolean hasLocation(Point location)
	{
		return vertexes.contains(location);
	}
	
	
	public void addHeight(Point location, double value)
	{
		if(!hasLocation(location))
		{
			meshIntact = false;
		}
		vertexes.put(location, value);
	}
	
	public void removeHeight(Point location)
	{
		if(hasLocation(location))
		{
			meshIntact = false;
			vertexes.remove(location);
		}
	}
	
	public double getHeight(Point location)
	{
		return vertexes.get(location);
	}
	
	public void setHeight(Point location, double value)
	{
		if(hasLocation(location))
		{
			vertexes.put(location, value);
		}
	}
}