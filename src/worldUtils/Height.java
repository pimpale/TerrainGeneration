package worldUtils;

import java.io.Serializable;

public class Height implements Cloneable, Serializable, Comparable<Height> {
	private static final long serialVersionUID = 3242L;
	private final int x ,y;
	private double val;
	
	
	public Height(Height h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.getVal();
	}
	
	public Height(int x, int y, double val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
	
	public double getVal()
	{
		return val;
	}
	
	public void setVal(double val)
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
	public Height clone()
	{
		return new Height(x,y,val);
	}
	
	@Override
	public boolean equals(Object other)
	{
		Height h = (Height)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.getVal()==getVal());
	}

	@Override
	public int compareTo(Height arg0) {
		return (int) Math.rint(getVal() - arg0.getVal());
	}
}
