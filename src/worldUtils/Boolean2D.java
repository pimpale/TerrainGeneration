package worldUtils;

import java.io.Serializable;

public class Boolean2D implements Value2D, Serializable {
	private static final long serialVersionUID = 32212L;
	private final int x ,y;
	private boolean val;
	
	
	public Boolean2D(Boolean2D h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.get();
	}
	
	public Boolean2D(int x, int y, boolean val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
	
	public boolean get()
	{
		return val;
	}
	
	public void set(boolean val)
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
		Boolean2D h = (Boolean2D)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.get()==(get()));
	}
}
