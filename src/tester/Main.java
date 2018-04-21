package tester;
import java.awt.Canvas;
import java.io.File;
import java.io.PrintStream;
import javax.swing.JFrame;


import worldBuilder.WorldScript;
import worldBuilder.WorldTemplate;

@SuppressWarnings("serial")
public class Main
{
	
	public static void main(String[] args) throws InterruptedException 
	{
		JFrame frame = new JFrame("test");
		frame.setSize(1024, 1024);
		Canvas canvas = new Canvas();
		canvas.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.setVisible(true);
		
		PrintStream p = new PrintStream(System.out);
		
		WorldTemplate wt = new WorldTemplate(new File("./Resources/Templates/2PoleContinental"));
		WorldScript ws = new WorldScript(wt.getMain(), canvas, System.out);
		
		ws.run();
	}

}

