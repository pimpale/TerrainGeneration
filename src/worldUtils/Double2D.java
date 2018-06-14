package worldUtils;

import java.io.Serializable;

public class Double2D implements Value2D, Serializable  {
	private static final long serialVersionUID = 3242L;
	private final int x ,y;
	private double val;
	
	
	public Double2D(Double2D h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.get();
	}
	
	public Double2D(int x, int y, double val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
	
	public double get()
	{
		return val;
	}
	
	public void set(double val)
	{
		this.val = val;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public String toString()
	{
		return x + " " + y + " " + val;
	}
	
	
	@Override
	public boolean equals(Object other)
	{
		Double2D h = (Double2D)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.get()==(get()));
	}
}
