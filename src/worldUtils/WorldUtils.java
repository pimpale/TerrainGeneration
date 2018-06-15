package worldUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import fastnoise.FastNoise;
import tester.Main;


public class WorldUtils {
	
	public static DoubleMap2D blur(DoubleMap2D input, int radius)
	{
		class BlurLine implements Runnable {
			private final int radius;
			private final boolean horizontal;
			private double[][] in;
			private double[][] out;
			private final int start;
			private final int end;
			private final int xSize;
			private final int ySize;
			public BlurLine(double[][] in, double[][] out, int radius, boolean horizontal, int start, int end)
			{
				this.in = in;
				this.out = out;
				this.radius = radius;
				this.horizontal = horizontal;
				this.start = start;
				this.end = end;
				this.xSize = in.length;
				this.ySize = in[0].length;
			}
			
			@Override
			public void run()
			{
				if(horizontal)
				{
					//means that certain rows will be blurred
					for(int y = start; y < end; y++)
					{
						for(int x = 0; x < xSize; x++)
						{
							double sum = 0;
							for(int sx = -radius; sx <= radius; sx++)
							{
								int rx = (int)OtherUtils.clamp(x+sx, 0, xSize-1);
								sum += in[rx][y];
							}
							out[x][y] = sum/(radius*2);
						}
					}
				}
				else //vertical
				{
					//means that certain columns will be blurred
					for(int x = start; x < end; x++)
					{
						for(int y = 0; y < ySize; y++)
						{
							double sum = 0;
							for(int sy = -radius; sy <= radius; sy++)
							{
								int ry = (int)OtherUtils.clamp(y+sy, 0, ySize-1);
								sum += in[x][ry];
							}
							out[x][y] = sum/(radius*2);
						}
					}
				}
			}
		}
		int xSize = input.getXSize();
		int ySize = input.getYSize();
		
		double[][] in = input.getMap();
		double[][] horizontalBlur = new double[xSize][ySize];
		double[][] out = new double[xSize][ySize];
		
		int threadCount = OtherUtils.getThreadCount();
		
		
		ExecutorService horizontalExec = Executors.newFixedThreadPool(threadCount);
		//first blur horizontally
		for(int i = 0; i < threadCount; i++)
		{
			horizontalExec.execute(new BlurLine(in, horizontalBlur, radius, true, i*ySize/threadCount, (i+1)*ySize/threadCount));
		}
		horizontalExec.shutdown();
		while(!horizontalExec.isTerminated())
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ExecutorService verticalExec = Executors.newFixedThreadPool(threadCount);
		//first blur vertically
		for(int i = 0; i < threadCount; i++)
		{
			verticalExec.execute(new BlurLine(horizontalBlur, out, radius, false, i*xSize/threadCount, (i+1)*xSize/threadCount));
		}
		verticalExec.shutdown();
		while(!verticalExec.isTerminated())
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new DoubleMap2D(out);
	}
	

	public static DoubleMap2D mask(DoubleMap2D map1, DoubleMap2D map2, BooleanMap2D mask)
	{
		int xSize = map1.getXSize();
		int ySize = map2.getYSize();
		double[][] out = new double[xSize][ySize];
		double[][] in1 = map1.getMap();
		double[][] in2 = map2.getMap();
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0 ; y < ySize; y++)
			{
				if(mask.get(x, y))
				{
					out[x][y] = in1[x][y];
				}
				else
				{
					out[x][y] = in2[x][y];
				}
			}
		}
		return new DoubleMap2D(out);
	}
	
	public static DoubleMap2D apply(Function<Double2D, Double2D> f, DoubleMap2D i)
	{
		return i.stream().map(f).collect(DoubleMap2D.COLLECTOR);
	}
	
	
	public static DoubleMap2D convolve(Kernel k, DoubleMap2D v)
	{
		int vXSize = v.getXSize();
		int vYSize = v.getYSize();
		
		double[][] vIn = v.getMap();
		
		double[][] vOut = new double[vXSize][vYSize];
		
		
		int threadCount = OtherUtils.getThreadCount();
		ExecutorService exec = Executors.newFixedThreadPool(threadCount);
		
		//For multithreading split the ValueMap2D up into as many parts as there are threads and have a thread operate on the rows
		class ConvolveRows implements Runnable {
			private final Kernel k;
			private final double[][] vin;
			private final double[][] vout;
			private final int startX;
			private final int endX;
			private final int xSize;
			private final int ySize;
			public ConvolveRows(Kernel k, double[][] vin, double[][] vout, int startX, int endX) {
				this.k = k;
				this.vin = vin;
				this.vout = vout;
				this.startX = startX;
				this.endX = endX;
				xSize = vin.length;
				ySize = vin[0].length;
			}
			
			@Override
			public void run()
			{
				for(int x = startX; x < endX; x++)
				{
					for(int y = 0; y < ySize; y++)
					{
						double accumulator = 0;
						double[][] kArr = k.kernel;
						//iterate through all elements in the kernel
						for(int kx = 0; kx < k.xSize; kx++)
						{
							for(int ky = 0; ky < k.ySize; ky++)
							{
								//relative vx and vy, for reference to the value map
								//we clamp the values between 0 and the max size of the array to be convolved
								int rvx = (int) OtherUtils.clamp(x + kx - k.xOff, 0, xSize-1);
								int rvy = (int) OtherUtils.clamp(y + ky - k.yOff, 0, ySize-1);
								//multiply the corresponding pixel value to the n
								accumulator += vin[rvx][rvy]*kArr[kx][ky];
							}
						}
						//normalize pixel
						vout[x][y] = accumulator;
					}
				}
			}
		}
		
	
		for(int i = 0; i < threadCount; i++)
		{
			exec.execute(new ConvolveRows(k, vIn, vOut, i*vXSize/threadCount, (i+1)*vXSize/threadCount));
		}
		exec.shutdown();
		while(!exec.isTerminated())
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new DoubleMap2D(vOut);
	}
	
	public static DoubleMap2D fillBasins(DoubleMap2D h, double seaLevel)
	{
		
		//Graphics2D g2d = (Graphics2D) Main.c.getGraphics();
		final int xSize = h.getXSize();
		final int ySize = h.getYSize();
		double[][] map = h.getMap();
		//Then we fill holes and tell the water where to go
		double plevel = seaLevel;
		byte[][] exploremap = new byte[xSize][ySize];//explored, tells what has been touched, and what not
		byte[][] explorereplacementmap = new byte[xSize][ySize];//

		//wang & liu algorithm. starts from edges and spots underneath the sea 
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				//the points to start exploring from
				if(map[x][y] < plevel || x==0 ||y==0 ||x==xSize-1||y==ySize-1)
 				{
					exploremap[x][y] = 1;
					explorereplacementmap[x][y] = 1;
				}
			}
		}	
		boolean keepgoing = true;//whether to keep going or not
		boolean saturated = true;//if there are still places to be explored at this water level
		byte freeedges = 0;//unexplored edges of this tile.
		while(keepgoing)
		{
			keepgoing = false;
			//raise the water level
			//if(saturated)
			{
				plevel+=0.0001;
			}
			saturated = true;
			for(int x = 0; x < xSize; x++)
			{
				for(int y = 0; y < ySize; y++)
				{
					if(exploremap[x][y] == 1)//if this is an discovered, but unexplored tile
					{
						keepgoing = true;//keep going
						freeedges = 0;//the number of free edges at this particular point
						for(int x1 = -1; x1 <= 1; x1++)
						{
							for(int y1 = -1; y1 <= 1; y1++)
							{

								if(x1 == 0 || y1 == 0)
								{

									int rx=x+x1, ry=y+y1; 

									//if it is within range and x or y is zero and the selected target is unexplored
									if(rx>=0&&ry>=0&&rx<xSize&&ry<ySize&&exploremap[rx][ry] == 0)
									{
										freeedges+=1;
										if(map[rx][ry] < plevel)
										{
											//g2d.setPaint(Color.getHSBColor((float)plevel, 0.7f, 0.7f));
											//g2d.fillRect(rx, ry, 1, 1);
											explorereplacementmap[rx][ry] = 1;
											saturated = false;
											//The water flows from rx to x
										}
									}
								}
							}
						}
						if(freeedges < 1)
						{
							explorereplacementmap[x][y] = 2;
							map[x][y] = plevel;
						}
					}
				}
			}
			for(int x = 0; x < map.length; x++)
			{
				for(int y = 0; y < map[0].length; y++)
				{
					exploremap[x][y] = explorereplacementmap[x][y];
				}
			}
		}
		DoubleMap2D b = new DoubleMap2D(map);
		return b;
	}
	
	
	public static BufferedImage getImage(DoubleMap2D source)
	{
		double[][] map = source.getMap();
		int xSize = source.getXSize();
		int ySize = source.getYSize();
		
		BufferedImage bimg = new BufferedImage(xSize, ySize, BufferedImage.TYPE_USHORT_GRAY);
		WritableRaster raster = bimg.getRaster();
		int[] r = new int[1];
		for(int x = 0; x < xSize; x++) 
		{
			for(int y = 0; y < ySize; y++)
			{
				r[0] = (short)(map[x][y]*Short.MAX_VALUE + Short.MIN_VALUE);
				raster.setPixel(x, y, r);
			}
		}
		return bimg;
	}
	
}



