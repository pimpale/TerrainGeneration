package worldUtils;

import java.io.Serializable;

public class Value2D implements Cloneable, Serializable, Comparable<Value2D> {
	private static final long serialVersionUID = 3242L;
	private final int x ,y;
	private double val;
	
	
	public Value2D(Value2D h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.getVal();
	}
	
	public Value2D(int x, int y, double val) {
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
	public Value2D clone()
	{
		return new Value2D(x,y,val);
	}
	
	@Override
	public boolean equals(Object other)
	{
		Value2D h = (Value2D)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.getVal()==getVal());
	}

	@Override
	public int compareTo(Value2D arg0) {
		return (int) Math.rint(getVal() - arg0.getVal());
	}
}
