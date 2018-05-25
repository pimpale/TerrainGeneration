package worldUtils;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class HeightMap {
	private final int xSize;
	private final int ySize;
	private final short[][] map;
	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}

	public HeightMap(short[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}

	public HeightMap(int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new short[xSize][ySize];
	}

	public HeightMap(BufferedImage img)
	{
		map = getValues(img);
		xSize = map.length;
		ySize = map[0].length;
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

	public short[][] getMap()
	{
		return map;
	}

	public short get(int x, int y)
	{
		return map[x][y];
	}

	public void set(int x, int y, short val)
	{
		map[x][y] = val;
	}

	private static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
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

	public static long Offset(int x, int y, int xsize, int ysize)
	{
		return (x*ysize + y);
	}

	public static long ByteOffset(int x, int y, int xsize, int ysize)
	{
		return Offset(x,y,xsize,ysize)*2;
	}

	

	public static BufferedImage getImage(short[][] map)
	{
		int xSize = map.length;
		int ySize = map[0].length;
		
		BufferedImage img = new BufferedImage(xSize,ySize, BufferedImage.TYPE_USHORT_GRAY);
		WritableRaster r = img.getRaster();
		for (int y = 0; y < ySize; y++) 
		{
			for (int x = 0; x < xSize; x++) 
			{
				r.setPixel(x, y, new int[] {map[x][y] + Short.MIN_VALUE});
			}
		}
		return img;
	}

	public BufferedImage getImage()
	{
		return getImage(map);
	}

	public static short[][] getValues(BufferedImage img)
	{
		int ySize = img.getWidth();
		int xSize = img.getHeight();
		DataBufferUShort buffer = (DataBufferUShort) img.getRaster().getDataBuffer(); 
		// Safe cast as img is of type TYPE_USHORT_GRAY 
		short[][] map = new short[xSize][ySize];
		for (int y = 0; y < ySize; y++) 
		{
			for (int x = 0; x < xSize; x++) 
			{
				map[x][y] = (short)(Short.MAX_VALUE+buffer.getElem(x + y * xSize));
			}
		}
		return map;
	}
	
}