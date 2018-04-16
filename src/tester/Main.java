package tester;
import java.awt.Canvas;
import java.io.File;
import java.io.PrintStream;

import javax.swing.JFrame;

import WorldBuilder.WorldScript;
import WorldBuilder.WorldTemplate;

@SuppressWarnings("serial")
public class Main
{
	
	public static void main(String[] args) throws InterruptedException 
	{
		JFrame frame = new JFrame("test");
		Canvas canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.add(canvas);
		frame.setVisible(true);
		
		PrintStream p = new PrintStream(System.out);
		
		WorldTemplate wt = new WorldTemplate(new File("./Resources/Templates/2PoleContinental"));
		WorldScript ws = new WorldScript(wt.getMain(), canvas, p);
		
		ws.run();
	}

}

