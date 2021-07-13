import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ShortMap {
  private final int xSize;
  private final int ySize;
  private final short[][] map;

  public int getXSize() {
    return xSize;
  }

  public int getYSize() {
    return ySize;
  }

  public ShortMap(short[][] newmap) {
    map = newmap;
    xSize = map.length;
    ySize = map[0].length;
  }

  public ShortMap(int xsize, int ysize) {
    xSize = xsize;
    ySize = ysize;
    map = new short[xSize][ySize];
  }

  public ShortMap(BufferedImage img) {
    map = getValues(img);
    xSize = map.length;
    ySize = map[0].length;
  }

  public ShortMap(String filelocation) {
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

  public short[][] getMap() {
    return map;
  }

  public short get(int x, int y) {
    return map[x][y];
  }

  public void set(int x, int y, short val) {
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

  public ShortMap blur(int radius) {
    short[][] newMap = new short[this.xSize][this.ySize];
    for (int x = 0; x < this.xSize; x++) {
      for (int y = 0; y < this.ySize; y++) {
        // TODO
        newMap[x][y] = (short) (this.map[x][y]);
      }
    }

    return new ShortMap(newMap);
  }

  public ShortMap scale(int newxsize, int newysize) {
    return new ShortMap(getValues(scale(getImage(), newxsize, newysize)));
  }

  public void Export(String filelocation) {
    try {
      File f = new File(filelocation);
      ImageIO.write(getImage(), "png", f);
    } catch (Throwable e) {
      e.printStackTrace();
    }
  }

  // Maps 0-1 to a short
  public static short DoubleToShort(double doub) {
    // return (short)(doub*Short.MAX_VALUE);
  // 65536 is the numbers in a short
    return (short) (doub * 65536 + Short.MIN_VALUE);
  }

  // Maps a short to 0-1
  public static double ShortToDouble(short shor) {
    // return ((double)shor)/Short.MAX_VALUE;
    return (((double) shor) - Short.MIN_VALUE) / (65536);
  }

  public static long Offset(int x, int y, int xsize, int ysize) {
    return (x * ysize + y);
  }

  public static long ByteOffset(int x, int y, int xsize, int ysize) {
    return Offset(x, y, xsize, ysize) * 2;
  }

  public static short BytesToShort(byte b1, byte b2) {
    return (short) (b1 * 255 + b2);
  }

  public static byte[] ShortToBytes(short s) {
    byte[] b = new byte[2];
    b[1] = (byte) (s & 0xff);
    b[0] = (byte) ((s >> 8) & 0xff);
    return b;
  }

  public static BufferedImage getImage(short[][] map) {
    int xSize = map.length;
    int ySize = map[0].length;
    BufferedImage img = new BufferedImage(xSize, ySize, BufferedImage.TYPE_USHORT_GRAY);
    WritableRaster r = img.getRaster();
    for (int y = 0; y < ySize; y++) {
      for (int x = 0; x < xSize; x++) {
        r.setPixel(x, y, new int[] { map[x][y] - Short.MAX_VALUE });
      }
    }
    return img;
  }

  public BufferedImage getImage() {
    return getImage(map);
  }

  public static short[][] getValues(BufferedImage img) {
    int ySize = img.getWidth();
    int xSize = img.getHeight();
    DataBufferUShort buffer = (DataBufferUShort) img.getRaster().getDataBuffer();
    // Safe cast as img is of type TYPE_USHORT_GRAY
    short[][] map = new short[xSize][ySize];
    for (int y = 0; y < ySize; y++) {
      for (int x = 0; x < xSize; x++) {
        map[x][y] = (short) (Short.MAX_VALUE + buffer.getElem(x + y * xSize));
      }
    }
    return map;
  }

}
