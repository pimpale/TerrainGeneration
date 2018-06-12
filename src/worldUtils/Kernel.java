package worldUtils;

public class Kernel {
	
	final double[][] kernel;
	
	final int xSize;
	final int ySize;

	final int xOff;
	final int yOff;
	
	public Kernel(double[][] kern)
	{
		kernel = kern;
		xSize = kernel.length;
		ySize = kernel[0].length;
		
		xOff = -xSize/2;
		yOff = -ySize/2;
	}
	
	
	public int getElementNum()
	{
		return xSize*ySize;
	}
}
