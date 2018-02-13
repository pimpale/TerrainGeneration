import java.util.Random;
import com.flowpowered.noise.*;
import com.flowpowered.noise.module.source.Perlin;


public class WorldUtils {
	//inshortform
	public static double makecontintentmappoint(int x, int y,
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
	
	public static ShortMap absSimplexNoise(OpenSimplexNoise noise, double scale, int xSize, int ySize)
	{
		short[][] map = new short[xSize][ySize]; 
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				map[x][y] = ShortMap.DoubleToShort(Math.abs(noise.eval(x/scale, x/scale)));
			}
		}
		return new ShortMap(map);	
	}
	
	/**
	 * 
	 * @param map the map to be processed
	 * @return returns a map that has been flipped. All 1s are 0s, all 0.7s are 0.3s, and so on
	 */
	public static ShortMap flip(ShortMap map)
	{
		int xSize = map.getXSize();
		int ySize = map.getYSize();
		short[][] oldmap = map.getMap();
		short[][] newmap = new short[xSize][ySize];
		double value;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				value = ShortMap.ShortToDouble(oldmap[x][y]);
				newmap[x][y] = ShortMap.DoubleToShort(1 -value);
			}
		}
		return new ShortMap(oldmap);
	}
	
	/**
	 * 
	 * @param value the level of the surface
	 * @param xSize the x size of the ShortMap
	 * @param ySize the y size of the ShortMap
	 * @return a ShortMap which is flat
	 */
	public static ShortMap constantValue(double value, int xSize, int ySize)
	{
		Perlin n;
		n.
		
		short[][] map = new short[xSize][ySize]; 
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				map[x][y] = ShortMap.DoubleToShort(value);
			}
		}
		return new ShortMap(map);
	}
	
	/**
	 * Raise all values on the map by a certain amount;
	 * @param map The map to perform the operation on
	 * @param pow
	 * @return
	 */
	public static ShortMap pow(ShortMap map, double pow)
	{ 
		int xSize = map.getXSize();
		int ySize = map.getYSize();
		short[][] oldmap = map.getMap();
		short[][] newmap = new short[xSize][ySize]; 
		double value;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				value = ShortMap.ShortToDouble(oldmap[x][y]);
				newmap[x][y] = ShortMap.DoubleToShort(Math.pow(value, pow));
			}
		}
		return new ShortMap(oldmap);
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
	
	public static ShortMap add(ShortMap map1, ShortMap map2)
	{
		return add(new ShortMap[] {map1,map2});
	}
	
	public static ShortMap subtract(ShortMap map1, ShortMap map2)
	{
		return subtract(map1, map2, 0, 0);
	}
	
	public static ShortMap subtract(ShortMap map1, ShortMap map2, int xOffset, int yOffset)
	{
		int xSize = map1.getXSize();
		int ySize = map1.getYSize();
		
		short[][] m1 = map1.getMap();
		short[][] m2 = map2.getMap();
		
		short[][] mapSum = new short[xSize][ySize];
		double sum = 0;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				sum = ShortMap.ShortToDouble(m1[x][y]) - ShortMap.ShortToDouble(m2[x][y]);
				mapSum[x][y] = ShortMap.DoubleToShort(sum);
			}
		}
		return new ShortMap(mapSum);
	}
	
	/**
	 * 
	 * @param maps the maps to add (must be same size) 
	 * @return The sum of the maps (will be clamped between 1 and 0)
	 */
	public static ShortMap add(ShortMap[] maps)
	{
		int xSize = maps[0].getXSize();
		int ySize = maps[0].getYSize();
		
		short[][] mapSum = new short[xSize][ySize];
		int sum = 0;
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				sum = 0;
				for(int i = 0; i < maps.length; i++)
				{
					sum += ShortMap.ShortToDouble(maps[i].get(x, y));
				}
				mapSum[x][y] = ShortMap.DoubleToShort(sum);
			}
		}
		return new ShortMap(mapSum);
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

