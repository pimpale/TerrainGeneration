package worldUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;

public class OtherUtils {
	public static short BytesToShort(byte b1, byte b2)
	{
		return (short) (b1*255 + b2);
	}

	public static byte[] ShortToBytes(short s)
	{
		byte[] b = new byte[2];
		b[1] = (byte) (s & 0xff);
		b[0] = (byte) ((s >> 8) & 0xff);
		return b;
	}
	
	public static int arrayOffset(int x, int y, int xsize, int ysize)
	{
		return (x*ysize + y);
	}

	//65536 is the numbers in a short
	public static short doubleToShort(double d)
	{
		return (short)(d*Short.MAX_VALUE);
	}

	public static double shortToDouble(short s)
	{
		return ((double)s)/Short.MAX_VALUE;
	}
	
	public static short celsiusToTemperature(double c)
	{
		return (short)(c*500);
	}
	
	public static double temperatureToCelsius(short s)
	{
		return ((double)s)*(10.0);
	}
	public static double clamp(double val, double min, double max)
	{
		return Math.max(min, Math.min(max, val));
	}
	
	
	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
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
