package worldUtils;

import java.io.Serializable;

public class Integer2D implements Value2D, Serializable  {
	private static final long serialVersionUID = 3242L;
	private final int x ,y;
	private int val;
	
	
	public Integer2D(Integer2D h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.get();
	}
	
	public Integer2D(int x, int y, int val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
	
	public int get()
	{
		return val;
	}
	
	public void set(int val)
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
		Integer2D h = (Integer2D)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.get()==(get()));
	}
}
