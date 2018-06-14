package tester;
import java.awt.Canvas;
import java.io.File;
import java.io.PrintStream;
import javax.swing.JFrame;

import worldBuilder.JavaScript;
import worldBuilder.TextFile;
import worldBuilder.WorldScript;
import worldBuilder.WorldTemplate;
import worldUtils.OtherUtils;
import worldUtils.Double2D;

@SuppressWarnings("serial")
public class Main
{

	public static Canvas c;
	
	public static void main(String[] args) throws InterruptedException 
	{
//		ValueMap2D<Double> vmap = new ValueMap2D<Double>(200,200);
//	FastNoise mnoise = new FastNoise(seed+1); mnoise.SetNoiseType(NoiseType.SimplexFractal); 
//	FastNoise cnoise = new FastNoise(seed+2); cnoise.SetNoiseType(NoiseType.SimplexFractal); 
//	FastNoise rnoise = new FastNoise(seed+3); rnoise.SetNoiseType(NoiseType.SimplexFractal); 
//	//set scales for continent noise and mountain noise
//
//double cscale = Math.pow(2, -2);
//	double mscale = Math.pow(2, -2);
//	
//	//weights and sizes for 
//	double[] sizes   = [Math.pow(2, -1), Math.pow(2,  0), Math.pow(2,  1), Math.pow(2,  2), Math.pow(2,  3), Math.pow(2,  4), Math.pow(2, 5)];
//	double[] weights = [17,              15,              13,              10,              7,               5,               3             ]; 
//	int weightsum = 30;//weights.reduce(function(a, b) { return a + b; }, 0);
//	int seed;	
//		vmap.stream()
//			.map(h -> {
//				int x = h.getX();
//				int y = h.getY();
//				double cheight = cnoise.GetNoise(x*cscale, y*cscale);
//				double mheight = Math.pow(1-2*Math.abs(mnoise.GetNoise(x*mscale, y*mscale)),3)-0.2;
//			
//				//fractal noise...
//				double rheight = 0;
//				for(int i = 0; i < sizes.length; i++) {
//					rnoise.SetSeed(seed+2+i);
//					rheight += weights[i]*rnoise.GetNoise(x*sizes[i], y*sizes[i]);
//				}
//				rheight = rheight/weightsum;
//				
//				double noiseSum = mheight*0.3 + rheight*0.4 + cheight*0.3;
//
//				return new Value2D<Double>(x,y,OtherUtils.clamp(noiseSum, -1, 1));
//			})
//			.collect(vmap.getCollector());
		
		int size = 700;
		JFrame frame = new JFrame("test");
		frame.setSize(size, size);
		Canvas canvas = new Canvas();
		canvas.setSize(size, size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.setVisible(true);
		c = canvas;
		
		PrintStream p = new PrintStream(System.out);
		
		WorldTemplate wt = new WorldTemplate(new File("./Resources/Templates/2PoleContinental"));
		WorldScript ws = new WorldScript(
				new JavaScript(new TextFile(wt.getMain()).getContent(), System.out),
				canvas, 
				1);
		
		ws.run();
	}

}

