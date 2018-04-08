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
	public static short DoubleToShort(double doub)
	{
		return (short)(doub*Short.MAX_VALUE);
		//return (short)(doub*65536 + Short.MIN_VALUE);
	}

	public static double ShortToDouble(short shor)
	{
		return ((double)shor)/Short.MAX_VALUE;
		//return (((double)shor)-Short.MIN_VALUE)/(65536);
	}

	public static double clamp(double val, double min, double max)
	{
		return Math.max(min, Math.min(max, val));
	}
}
