package tester;

import java.awt.Canvas;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.DoubleStream;

import javax.swing.JFrame;

import fastnoise.FastNoise;
import fastnoise.FastNoise.NoiseType;
import worldBuilder.JavaScript;
import worldBuilder.TextFile;
import worldBuilder.WorldScript;
import worldBuilder.WorldTemplate;
import worldUtils.Value2D;
import worldUtils.ValueMap2D;
import worldUtils.WorldUtils;
import worldUtils.OtherUtils;

@SuppressWarnings("serial")
public class Main
{
	
	public static ValueMap2D getSimplexFractal(int xSize, int ySize, int seed, double[] sizes, double[] weights) {
		ValueMap2D[] rnoises = new ValueMap2D[sizes.length];
		for(int i = 0; i < rnoises.length; i++) {
			FastNoise n = new FastNoise(seed+i);
			n.SetNoiseType(NoiseType.SimplexFractal);
			final int fi = i;
			rnoises[i] = WorldUtils.mapOf(xSize, ySize, (x,y) -> (weights[fi]*n.GetNoise((float)(x*sizes[fi]), (float)(y*sizes[fi]))));
		}
		return WorldUtils.apply(rnoises, arr -> Arrays.stream(arr).reduce((a,b) -> a+b).get());
	}
	
	public static ValueMap2D getHeightMap(int xsize, int ysize, int seed) {
		//set up the noise
		FastNoise mnoise = new FastNoise(seed+1); 
		FastNoise cnoise = new FastNoise(seed+2); 
		mnoise.SetNoiseType(NoiseType.SimplexFractal); 
		cnoise.SetNoiseType(NoiseType.SimplexFractal); 

		//set scales for continent noise and mountain noise
		float cscale = (float)Math.pow(2, -2);
		float mscale = (float)Math.pow(2, -2);
		
		//weights and sizes for the rnoise generation
		double[] sizes =   { Math.pow(2, -1), Math.pow(2,  0), Math.pow(2,  1), Math.pow(2,  2), Math.pow(2,  3), Math.pow(2,  4), Math.pow(2, 5) };
		double[] weights = { 17/70,           15/70,           13/70,           10/70,           7/70,            5/70,            3/70           }; 
		
		ValueMap2D rvals = getSimplexFractal(xsize, ysize, seed+3, sizes, weights);
		ValueMap2D cvals = WorldUtils.mapOf(xsize, ysize, (x,y) -> Double.valueOf(cnoise.GetNoise(x*cscale, y*cscale)));
		ValueMap2D mvals = WorldUtils.mapOf(xsize, ysize, (x,y) -> Double.valueOf(Math.abs(mnoise.GetNoise(x*mscale, y*mscale))));

		return cvals;
		//return WorldUtils.apply(new ValueMap2D[] {rvals, cvals, mvals}, xarr -> OtherUtils.clamp(0.4*xarr[0] + 0.3*xarr[1] + 0.3*xarr[2], 0, 1));
	}
	
	public static Canvas c;
	
	public static void main(String[] args) throws InterruptedException 
	{
		int size = 7000;
		JFrame frame = new JFrame("test");
		frame.setSize(size, size);
		Canvas canvas = new Canvas();
		canvas.setSize(size, size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.setVisible(true);
		c = canvas;
		
		ValueMap2D map = getHeightMap(size,size, 1);
		while(true) {
			c.getGraphics().drawImage(WorldUtils.toImage(map), 0, 0, null);
			Thread.sleep(20);
		}
	
		
		
		/*PrintStream p = new PrintStream(System.out);
		
		WorldTemplate wt = new WorldTemplate(new File("./Resources/Templates/2PoleContinental"));
		WorldScript ws = new WorldScript(
				new JavaScript(new TextFile(wt.getMain()).getContent(), System.out),
				canvas, 
				1);
		
		ws.run(); */
	}

}

