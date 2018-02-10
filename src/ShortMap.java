import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShortMap {
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

	public ShortMap(short[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}
	
	public ShortMap(int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new short[xSize][ySize];
	}

	public ShortMap(String filelocation)
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

	public void blur(int radius)
	{
		short[][] nmap = new short[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				double av = 0;
				double count = 0;
				for(int x1 = -radius; x1 <= radius; x1++)
				{
					for(int y1 = -radius; y1 <= radius; y1++)
					{
						int rx = x+x1, ry = y+y1;
						if(rx>-1&&rx<xSize&&ry>-1&&ry<ySize)
						{
							count++;
							av+=map[rx][ry];
						}
					}
				}
				av = av/count;
				nmap[x][y] = (short)av;
			}
		}
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				map[x][y] = nmap[x][y];
			}
		}
	}
	
	public static void blur(ShortMap in, ShortMap out, int radius)
	{
		
	}
	
	
	public ShortMap scale(int newxsize, int newysize)
	{
		return new ShortMap(getValues(scale(getImage(map), newxsize, newysize)));
	}

	

	//	public short[][] getMap()
	{

	}

	public void Export(String filelocation)
	{
		try
		{
			File f = new File(filelocation);
			ImageIO.write(getImage(map), "png", f);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	

	//65536 is the numbers in a short
	public static short DoubleToShort(double doub)
	{
		//return (short)(doub*Short.MAX_VALUE);
		return (short)(doub*65536 + Short.MIN_VALUE);
	}

	public static double ShortToDouble(short shor)
	{
		//return ((double)shor)/Short.MAX_VALUE;
		return (((double)shor)-Short.MIN_VALUE)/(65536);
	}

	public static long Offset(int x, int y, int xsize, int ysize)
	{
		return (x*ysize + y);
	}

	public static long ByteOffset(int x, int y, int xsize, int ysize)
	{
		return Offset(x,y,xsize,ysize)*2;
	}

	public static short BytesToShort(byte b1, byte b2)
	{
		return (short) (b1*255 + b2);
		//(short) ((b1 << 8) | b2);
	}

	public static byte[] ShortToBytes(short s)
	{
		byte[] b = new byte[2];
		b[1] = (byte) (s & 0xff);
		b[0] = (byte) ((s >> 8) & 0xff);
		return b;
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
				r.setPixel(x, y, new int[] {map[x][y]-Short.MAX_VALUE});
			}
		}
		return img;
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