package worldUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.util.ArrayList;

public class OtherUtils {

	
	
	public static int getThreadCount()
	{
		return Runtime.getRuntime().availableProcessors();
	}
	
	
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

	public static final int SHORT_RANGE=Short.MAX_VALUE-Short.MIN_VALUE;
	public static short doubleToShort(double d)
	{
		return (short)(d*SHORT_RANGE);
	}

	public static double shortToDouble(short s)
	{
		return ((double)s)/SHORT_RANGE;
	}
	
	public static double clamp(double val, double min, double max)
	{
		return Math.max(min, Math.min(max, val));
	}
	
	public static int clamp(int val, int min, int max)
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
	
	private static BufferedImage blurImage(BufferedImage image, int radius) {
		int width = image.getWidth();
		int height = image.getHeight();

		int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
		int[] changedPixels = new int[pixels.length];

		FastGaussianBlur(pixels, changedPixels, width, height, radius);

		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		newImage.setRGB(0, 0, width, height, changedPixels, 0, width);

		return newImage;
	}
	
	private static void BoxBlur(int[] source, int[] output, int width, int height, int radius) {
		System.arraycopy(source, 0, output, 0, source.length);
		BoxBlurHorizontal(output, source, width, height, radius);
		BoxBlurVertical(source, output, width, height, radius);
	}

	private static void BoxBlurHorizontal(int[] sourcePixels, int[] outputPixels, int width, int height, int radius) {
		int resultingColorPixel;
		float iarr = 1f / (radius + radius);
		for (int i = 0; i < height; i++) {
			int outputIndex = i * width;
			int li = outputIndex;
			int sourceIndex = outputIndex + radius;

			int fv = Byte.toUnsignedInt((byte) sourcePixels[outputIndex]);
			int lv = Byte.toUnsignedInt((byte) sourcePixels[outputIndex + width - 1]);
			float val = (radius) * fv;

			for (int j = 0; j < radius; j++) {
				val += Byte.toUnsignedInt((byte) (sourcePixels[outputIndex + j]));
			}

			for (int j = 0; j < radius; j++) {
				val += Byte.toUnsignedInt((byte) sourcePixels[sourceIndex++]) - fv;
				resultingColorPixel = Byte.toUnsignedInt(((Integer) Math.round(val * iarr)).byteValue());
				outputPixels[outputIndex++] = (0xFF << 24) | (resultingColorPixel << 16) | (resultingColorPixel << 8) | (resultingColorPixel);
			}

			for (int j = (radius + 1); j < (width - radius); j++) {
				val += Byte.toUnsignedInt((byte) sourcePixels[sourceIndex++]) - Byte.toUnsignedInt((byte) sourcePixels[li++]);
				resultingColorPixel = Byte.toUnsignedInt(((Integer) Math.round(val * iarr)).byteValue());
				outputPixels[outputIndex++] = (0xFF << 24) | (resultingColorPixel << 16) | (resultingColorPixel << 8) | (resultingColorPixel);
			}

			for (int j = (width - radius); j < width; j++) {
				val += lv - Byte.toUnsignedInt((byte) sourcePixels[li++]);
				resultingColorPixel = Byte.toUnsignedInt(((Integer) Math.round(val * iarr)).byteValue());
				outputPixels[outputIndex++] = (0xFF << 24) | (resultingColorPixel << 16) | (resultingColorPixel << 8) | (resultingColorPixel);
			}
		}
	}

	private static void BoxBlurVertical(int[] sourcePixels, int[] outputPixels, int width, int height, int radius) {
		int resultingColorPixel;
		float iarr = 1f / (radius + radius + 1);
		for (int i = 0; i < width; i++) {
			int outputIndex = i;
			int li = outputIndex;
			int sourceIndex = outputIndex + radius * width;

			int fv = Byte.toUnsignedInt((byte) sourcePixels[outputIndex]);
			int lv = Byte.toUnsignedInt((byte) sourcePixels[outputIndex + width * (height - 1)]);
			float val = (radius + 1) * fv;

			for (int j = 0; j < radius; j++) {
				val += Byte.toUnsignedInt((byte) sourcePixels[outputIndex + j * width]);
			}
			for (int j = 0; j <= radius; j++) {
				val += Byte.toUnsignedInt((byte) sourcePixels[sourceIndex]) - fv;
				resultingColorPixel = Byte.toUnsignedInt(((Integer) Math.round(val * iarr)).byteValue());
				outputPixels[outputIndex] = (0xFF << 24) | (resultingColorPixel << 16) | (resultingColorPixel << 8) | (resultingColorPixel);
				sourceIndex += width;
				outputIndex += width;
			}
			for (int j = radius + 1; j < (height - radius); j++) {
				val += Byte.toUnsignedInt((byte) sourcePixels[sourceIndex]) - Byte.toUnsignedInt((byte) sourcePixels[li]);
				resultingColorPixel = Byte.toUnsignedInt(((Integer) Math.round(val * iarr)).byteValue());
				outputPixels[outputIndex] = (0xFF << 24) | (resultingColorPixel << 16) | (resultingColorPixel << 8) | (resultingColorPixel);
				li += width;
				sourceIndex += width;
				outputIndex += width;
			}
			for (int j = (height - radius); j < height; j++) {
				val += lv - Byte.toUnsignedInt((byte) sourcePixels[li]);
				resultingColorPixel = Byte.toUnsignedInt(((Integer) Math.round(val * iarr)).byteValue());
				outputPixels[outputIndex] = (0xFF << 24) | (resultingColorPixel << 16) | (resultingColorPixel << 8) | (resultingColorPixel);
				li += width;
				outputIndex += width;
			}
		}
	}

	public static void FastGaussianBlur(int[] source, int[] output, int width, int height, int radius) {
		ArrayList<Integer> gaussianBoxes = CreateGausianBoxes(radius, 3);
		BoxBlur(source, output, width, height, (gaussianBoxes.get(0) - 1) / 2);
		BoxBlur(output, source, width, height, (gaussianBoxes.get(1) - 1) / 2);
		BoxBlur(source, output, width, height, (gaussianBoxes.get(2) - 1) / 2);
	}

	public static ArrayList<Integer> CreateGausianBoxes(double sigma, int n) {
		double idealFilterWidth = Math.sqrt((12 * sigma * sigma / n) + 1);

		int filterWidth = (int) Math.floor(idealFilterWidth);

		if (filterWidth % 2 == 0) {
			filterWidth--;
		}

		int filterWidthU = filterWidth + 2;

		double mIdeal = (12 * sigma * sigma - n * filterWidth * filterWidth - 4 * n * filterWidth - 3 * n) / (-4 * filterWidth - 4);
		double m = Math.round(mIdeal);

		ArrayList<Integer> result = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			result.add(i < m ? filterWidth : filterWidthU);
		}

		return result;
	}

	
}
