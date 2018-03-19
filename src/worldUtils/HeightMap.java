package worldUtils;

import java.awt.Rectangle;

//TODO this whole thing is todo at a low priority, everything should work for now
/**
 * implements a heightmap between 1 and 0.
 * @author fidgetsinner
 *
 */
public interface HeightMap {

	public int getWidth();
	public int getHeight();
	
	public double get(int x, int y);
	public void set(int x, int y, double val);
	
	public boolean isSameSize(HeightMap h);
	
	public HeightMap scale(int newX, int newY);
	
	
}
