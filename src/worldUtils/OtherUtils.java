package worldUtils;

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
		return (short)(c*10);
	}
	
	public static double temperatureToCelsius(short s)
	{
		return ((double)s)*(10.0);
	}
	public static double clamp(double val, double min, double max)
	{
		return Math.max(min, Math.min(max, val));
	}
}
