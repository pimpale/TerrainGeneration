package tester;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.JFrame;

import com.flowpowered.noise.model.Plane;
import com.flowpowered.noise.module.source.Perlin;

import worldUtils.ShortMap;
import worldUtils.WorldUtils;

@SuppressWarnings("serial")
public class Main
{
	
	public static void main(String[] args) throws InterruptedException 
	{		
		Perlin p = new Perlin();
		Plane pl = new Plane(p);
		ShortMap sh = WorldUtils.noise(pl, 300, 300);
		
		WorldUtils.scale(sh, 50, 0, 0, sh.getXSize(), sh.getYSize());
		
		JFrame frame = new JFrame("test");
		Canvas canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);
		frame.add(canvas);
		frame.setVisible(true);
		
		Graphics2D g2d = (Graphics2D) canvas.getGraphics();
		while(true)
		{
			g2d.drawImage(sh.getImage(), 0, 0, null);
			Thread.sleep(100);
		}
	}

}

