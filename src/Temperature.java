
public class Temperature {

  // accepts a number between 0 and 100, returns a short, 
  public static short TemperatureDoubleToShort(double doub) {
    return ShortMap.DoubleToShort(doub/100);
  }

  // accepts a short, returns a number between 0 and 100
  public static double TemperatureShortToDouble(short shor) {
    return ShortMap.ShortToDouble(shor) * 100;
  }

  // temp is measured on a scale of double fahrenheit / 100
  // 0 is the minimum temp (== -17 C)
  // 100 is the max temp (== 37 C)
  // for illumination map, white maps to 100 and 0 maps to -100
  public static void getTemperature(short[][] heightMap, int seed, double sealevel) {
    int xsize = heightMap.length;
    int ysize = heightMap[0].length;
    OpenSimplexNoise snoise = new OpenSimplexNoise(seed);
    short[][] tempMap = new short[xsize][ysize];
    for (int x = 0; x < xsize; x++) {
      for (int y = 0; y < ysize; y++) {
        double height = ShortMap.ShortToDouble(heightMap[x][y]);
        if (height< sealevel) {
          height = sealevel;
        }

        double basetmp = 90 * Math.sin((y * Math.PI) / (ysize)) - 10;
        double randtmp = 40 * snoise.eval(x / 200.0, y / 200.0);
        double heighttmp = -80 * height;

        double tmp = Math.max(0, Math.min(100, basetmp + randtmp + heighttmp));

        tempMap[x][y] = TemperatureDoubleToShort(tmp);
      }
    }
    ShortMap temp = new ShortMap(tempMap);
    temp.Export("./WorldSave/Temperature.png");
  }

  public static void illuminance(int xsize, int ysize) {
    // TODO
    // we need to realign the scales
    short[][] imap = new short[xsize][ysize];
    OpenSimplexNoise n = new OpenSimplexNoise(1);
    for (int x = 0; x < xsize; x++) {
      for (int y = 0; y < ysize; y++) {
        imap[x][y] = (short) (0);
      }
    }
    new ShortMap(imap).Export("./Resources/Templates/2PoleContinental/Temperature.png");
    BasicHeightMap.draw(1, imap);
  }

}
/*
 * private static void illuminance(int xsize, int ysize) { short[][] imap = new
 * short[xsize][ysize]; OpenSimplexNoise n = new OpenSimplexNoise(1); for(int x
 * = 0; x < xsize; x++) { for(int y = 0; y < ysize; y++) { double ry = y+0.0;
 * imap[x][y] =
 * HeightMap.DoubleToShort(Math.pow(1.99*(Math.sqrt(Math.pow(ysize/2,
 * 2)-Math.pow(ry-ysize/2, 2))/ysize),2)); } } new
 * HeightMap(imap).Export("./Resources/IlluminanceMaps/Illuminance.png");
 * draw(imap); }
 */
