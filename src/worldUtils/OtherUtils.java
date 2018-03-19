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
}
