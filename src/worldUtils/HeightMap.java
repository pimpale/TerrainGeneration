package worldUtils;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class HeightMap {
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

	public HeightMap(String filelocation)
	{
		BufferedImage img = null;
		try {
			File f = new File(filelocation);
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map = getValues(img);
		xSize = map.length;
		ySize = map[0].length;
	}

	public HeightMap(Stream<Height> stream, int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new double[xSize][ySize];
		
		Height[] heights = (Height[]) stream.toArray();
		
		for(Height h : heights)
		{
			map[h.x][h.y] = h.val;
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
		short[][] newMap = new short[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new HeightMap(newMap);
	}
	
	public HeightMap scale(int newxsize, int newysize)
	{
		return new HeightMap(getValues(scale(getImage(), newxsize, newysize)));
	}

	public HeightMap truncate(int x, int y, int width, int height)
	{
		return new HeightMap(getImage().getSubimage(x, y, width, height));
	}

	public void Export(String filelocation)
	{
		try
		{
			File f = new File(filelocation);
			ImageIO.write(getImage(), "png", f);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
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
		return getImage(map);
	}

	
}

class Height {
	public final int x ,y;
	public double val;
	public Height(int x, int y, double val) {
		this.x = x;
		this.y = y;
		this.val = val;
	}
}
