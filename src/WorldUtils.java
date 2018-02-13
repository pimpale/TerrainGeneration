import java.util.Random;

public class WorldUtils {
	//inshortform
	private static double makecontintentmappoint(int x, int y,
			OpenSimplexNoise rangeNoise, OpenSimplexNoise shapeNoise, OpenSimplexNoise baseNoise)
	{
		double scale = 2<<19;
		double imountrangesize = 2;
		double ifisrangesize = 3;
		double icontinentsize = 1;
		float mheight = (float) (rangeNoise.eval(((float)x)/(scale/imountrangesize),
				((float)y)/(scale/imountrangesize)));
		mheight = 1f-Math.abs(mheight);
		float fheight = (float) (rangeNoise.eval(((float)(x))/(scale/ifisrangesize),//same scale, different area
				((float)y)/(scale/ifisrangesize)));
		fheight =  -(float)Math.pow(1 - Math.abs(fheight),3);
		float cheight = (float) (shapeNoise.eval(((float)x)/(scale/icontinentsize),
				((float)y)/(scale/icontinentsize)));
		cheight = (cheight+1)/2;
		double[][] LayerWeightsAndiSizes = new double[][]	{
			{2<<2  , 2<<3  , 2<<4   , 2<<5   , 2<<6   , 2<<7   , 2<<8   ,},	
			{17    , 15    , 13     , 10     , 7      , 5      , 3      ,}
		};

		float layerweighttotal = 0;
		float rheight = 0;
		for(int i = 0; i < LayerWeightsAndiSizes[0].length; i++)
		{
			double layersize = scale/LayerWeightsAndiSizes[0][i];
			double layerweight =LayerWeightsAndiSizes[1][i];			
			double lheight = (baseNoise.eval(x/layersize,  (y/layersize)));
			lheight *= layerweight;
			rheight += lheight;
			layerweighttotal += LayerWeightsAndiSizes[1][i];
		}
		rheight = rheight/layerweighttotal;
		rheight = (rheight+1)/2;
		float height = mheight*0.2f  + fheight*0.1f + rheight*0.45f + cheight*0.45f;
		//height = fheight;
		return Math.pow(height, 4);
	}
	/**
	 * 
	 * @param noise
	 * @param scale the scale of the noise.
	 * @param xSize the x size of the shortMap
	 * @param ySize the y size of the shortMap
	 * @return A ShortMap with size xSize by ySize representing the noise
 	 */
	public static ShortMap simplexNoise(OpenSimplexNoise noise, double scale, int xSize, int ySize)
	{
		short[][] map = new short[xSize][ySize]; 
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				map[x][y] = ShortMap.DoubleToShort((noise.eval(x/scale, x/scale) + 1)/2);
			}
		}
		return new ShortMap(map);		
	}
	
	
	public static short[][] makeContinentDigestArray(int xsize, int ysize, int seed)
	{
		Random r = new Random(seed);
		OpenSimplexNoise Noise1 = new OpenSimplexNoise(r.nextInt()); 
		OpenSimplexNoise Noise2 = new OpenSimplexNoise(r.nextInt()); 
		OpenSimplexNoise Noise3 = new OpenSimplexNoise(r.nextInt()); 
		int digestxsize = 700;
		int digestysize = 700;
		System.out.println("In the beginning, the world was drawn forth from the ether...");
		short[][] map = new short[digestxsize][digestysize];
		for(int x = 0; x < digestxsize; x+=1)
		{
			for(int y = 0; y < digestysize; y+=1)
			{
				//long startTime = System.nanoTime();
				double rawheight  = makecontintentmappoint(
						(xsize/digestxsize)*x,
						(ysize/digestysize)*y,
						Noise1,Noise2,Noise3
						);
				short height = ShortMap.DoubleToShort(rawheight);
				map[x][y] = height;
				//	System.out.println(System.nanoTime() - startTime);
			}
			System.out.println(x);
		}
		return map;
	}	
	
	public static ShortMap scale(ShortMap map, double multiplier)
	{
		int xSize = map.getXSize();
		int ySize = map.getYSize();
		short[][] oldmap = map.getMap();
		short[][] newMap = new short[xSize][ySize];
		
		double newVal = 0;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newVal = ShortMap.ShortToDouble(oldmap[x][y]) * multiplier;
				newMap[x][y] =  ShortMap.DoubleToShort(ShortMap.clamp(newVal, 0, 1));
			}
		}
		return new ShortMap(newMap);
	}
	
	
	/**
	 * 
	 * @param maps the maps to average (must be same size)
	 * @param weights the weights of each map (Should all add up to 1, with each individual weight being in between 1 and 0)
	 * @return The average of the maps
	 */
	public static ShortMap weightedAverage(ShortMap[] maps, double[] weights)
	{
		int xSize = maps[0].getXSize();
		int ySize = maps[0].getYSize();
		
		double weightSum = 0;
		for(int i = 0; i < weights.length; i++)
		{
			weightSum += weights[i];
		}
		
		short[][] average = new short[xSize][ySize];
		
		double mapSum = 0;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				mapSum = 0;
				for(int i = 0; i < maps.length; i++)
				{
					mapSum += ShortMap.ShortToDouble(maps[i].get(x, y));
				}
				average[x][y] = ShortMap.DoubleToShort(mapSum/weightSum);
			}
		}
		return new ShortMap(average);
	}
}

