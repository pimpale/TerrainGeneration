package worldUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import fastnoise.FastNoise;


public class WorldUtils {
	/*//inshortform
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
	}*/
}
