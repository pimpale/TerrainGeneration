package tester;
import java.awt.Canvas;
import java.io.File;
import java.io.PrintStream;
import javax.swing.JFrame;

import worldBuilder.JavaScript;
import worldBuilder.TextFile;
import worldBuilder.WorldScript;
import worldBuilder.WorldTemplate;
import worldUtils.HeightMap;

@SuppressWarnings("serial")
public class Main
{

	public static Canvas c;
	
	public static void main(String[] args) throws InterruptedException 
	{
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
				(int)(Math.random()*200));
		
		
		ws.run();
	}

}

