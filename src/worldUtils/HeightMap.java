package worldUtils;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.Serializable;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class HeightMap implements Cloneable, Serializable
{
	private final int xSize;
	private final int ySize;
	private final double[][] map;
	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}

	public HeightMap(double[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}

	public HeightMap(int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new double[xSize][ySize];
	}

	public HeightMap(Stream<Height> stream, int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new double[xSize][ySize];
		
		Height[] heights = (Height[]) stream.toArray();
		
		for(Height h : heights)
		{
			map[h.x][h.y] += h.val;
		}
		
	}
	
	public double[][] getMap()
	{
		return map;
	}

	public double get(int x, int y)
	{
		return map[x][y];
	}

	public void set(int x, int y, double val)
	{
		map[x][y] = val;
	}

	
	public HeightMap clone()
	{
		double[][] newMap = new double[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new HeightMap(newMap);
	}
	
	public Stream<Height> toHeightStream()
	{
		Stream<Height> sst = Stream.empty();
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < xSize; y++)
			{
				Stream.concat(sst, Stream.of(new Height(x,y,map[x][y])));
			}
		}
		return sst;
	}
	
	public BufferedImage getImage()
	{
		BufferedImage bimg = new BufferedImage(xSize, ySize, BufferedImage.TYPE_USHORT_GRAY);
		WritableRaster raster = bimg.getRaster();
		int[] r = new int[1];
		for(int x = 0; x < xSize; x++) 
		{
			for(int y = 0; y < ySize; y++)
			{
				r[1] = OtherUtils.doubleToShort(map[x][y]);
				raster.setPixel(x, y, r);
			}
		}
		return bimg;
	}
}

class Height implements Cloneable {
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
