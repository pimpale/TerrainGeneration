package worldUtils;

import java.io.Serializable;

class Height implements Cloneable, Serializable {
	private static final long serialVersionUID = 3242L;
	public final int x ,y;
	public double val;
	public Height(int x, int y, double val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
	
	@Override
	public Height clone()
	{
		return new Height(x,y,val);
	}
}
