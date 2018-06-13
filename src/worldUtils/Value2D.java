package worldUtils;

import java.io.Serializable;

public class Value2D<E> implements Cloneable, Serializable  {
	private static final long serialVersionUID = 3242L;
	private final int x ,y;
	private E val;
	
	
	public Value2D(Value2D<E> h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.getVal();
	}
	
	public Value2D(int x, int y, E val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
	
	public E getVal()
	{
		return val;
	}
	
	public void setVal(E val)
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
	public Value2D<E> clone()
	{
		return new Value2D<E>(x,y,val);
	}
	
	@Override
	public boolean equals(Object other)
	{
		Value2D h = (Value2D)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.getVal().equals(getVal()));
	}
}
