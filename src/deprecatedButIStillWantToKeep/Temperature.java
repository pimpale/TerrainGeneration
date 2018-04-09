package deprecatedButIStillWantToKeep;

import worldUtils.OtherUtils;
import worldUtils.ShortMap;
import worldUtils.WorldUtils;

public class Temperature {
	
	public static short TemperatureDoubleToShort(double doub)
	{
		return OtherUtils.doubleToShort(doub/2);
	}
	
	public static double TemperatureShortToDouble(short shor)
	{
		return (OtherUtils.shortToDouble(shor))*2;
	}

	
	//temp is measured on a scale of short where Short.MinValue is Absolute Zero, as cold as cold can get, anw  Short.MaxValue is Absolute Heat
	//water freezes at 0 and boils at 100
	//for illumination map, white maps to 100 and 0 maps to -100
	public static void getTemperature(short[][] height, int seed, double sealevel)
	{
		int xsize = height.length;
		int ysize = height[0].length;
		ShortMap ilmn = new ShortMap("./Resources/Templates/2PoleContinental/Temperature.png").scale(xsize, ysize);
		short[][] ilmnMap = ilmn.getMap();
		//also sum up a random
		for(int x = 0; x < xsize; x++)
		{
			for(int y = 0; y < ysize; y++)
			{
				double val = OtherUtils.shortToDouble(height[x][y]);
				if(val  < sealevel)
				{
					val = sealevel;
				}
				ilmnMap[x][y] += (short)(-60*val+ 10+20);//plus random
			}
		}
		ilmn = new ShortMap(ilmnMap);
		ilmn = WorldUtils.blur(ilmn, 3);
		ilmn.Export("./WorldSave/Temperature.png");
	}
	
	public static void illuminance(int xsize, int ysize)
	{
		short[][] imap = new short[xsize][ysize];
		for(int x = 0; x < xsize; x++)
		{
			for(int y = 0; y < ysize; y++)
			{
				double ry = y+0.0;
				imap[x][y] = 
						(short)(-10+40*Math.sin((ry*Math.PI)/(ysize)));
			}
		}
		new ShortMap(imap).Export("./Resources/Templates/2PoleContinental/Temperature.png");
	}
	
}
/*
private static void illuminance(int xsize, int ysize)
{
	short[][] imap = new short[xsize][ysize];
	OpenSimplexNoise n = new OpenSimplexNoise(1);
	for(int x = 0; x < xsize; x++)
	{
		for(int y = 0; y < ysize; y++)
		{
			double ry = y+0.0;
			imap[x][y] = 
					HeightMap.DoubleToShort(Math.pow(1.99*(Math.sqrt(Math.pow(ysize/2, 2)-Math.pow(ry-ysize/2, 2))/ysize),2));
		}
	}
	new HeightMap(imap).Export("./Resources/IlluminanceMaps/Illuminance.png");
	draw(imap);
}
*/