package worldUtils;

public class Value2D {
	private final int x ,y;
	private double val;
	
	
	public Value2D(Value2D h)
	{
		this.x = h.getX();
		this.y = h.getY();
		this.val = h.get();
	}
	
	public Value2D(int x, int y, double val) {
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
		Value2D h = (Value2D)other;
		return (h.getX()==getX()) && (h.getY()==getY()) && (h.get()==(get()));
	}
}
