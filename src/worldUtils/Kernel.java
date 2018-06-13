package worldUtils;

public class Kernel {
	
	final double[][] kernel;
	
	final int xSize;
	final int ySize;

	//The offset of the kernel. for example a kernel of 3x3 would have an offset of -1 so that k[1+xOff][1+yOff] would be 
	final int xOff;
	final int yOff;
	
	
	public static Kernel IDENTITY = new Kernel(new double[][] {{1}});
	
	public static Kernel GAUSSIAN3 = new Kernel(new double[][] {
		{1,2,1},
		{2,4,2},
		{1,2,1}
	}).normalize();
	
	public static Kernel SHARPEN3 = new Kernel(new double[][] {
		{ 0,-1, 0},
		{-1, 5,-1},
		{ 0,-1, 0}
	}).normalize();
	
	public Kernel(double[][] kern)
	{
		kernel = kern;
		xSize = kernel.length;
		ySize = kernel[0].length;
		
		xOff = xSize/2;
		yOff = ySize/2;
	}
	
	public Kernel normalize()
	{
		double accumulator = 0;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				accumulator += kernel[x][y];
			}
		}
		
		double[][] newKernel = new double[xSize][ySize];
		
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newKernel[x][y] = kernel[x][y]/accumulator;
			}
		}
		return new Kernel(newKernel);
	}
	
	
	public int getElementNum()
	{
		return xSize*ySize;
	}
}
