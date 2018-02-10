

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;

import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



@SuppressWarnings("serial")
public class Main extends JPanel  implements  MouseListener, MouseMotionListener, KeyListener
{	
	public Main(JFrame frame)
	{
		addMouseListener(this);
		addMouseMotionListener(this);	 
		frame.addKeyListener(this);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {


	}

	@Override
	public void mouseClicked(MouseEvent arg0) {


	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {


	}

	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_W)
		{
			wkeydown = true;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_S)
		{
			skeydown = true;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_A)
		{
			akeydown = true;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_D)
		{
			dkeydown = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_W)
		{
			wkeydown = false;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_S)
		{
			skeydown = false;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_A)
		{
			akeydown = false;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_D)
		{
			dkeydown = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private static boolean wkeydown = false;
	private static boolean skeydown = false;
	private static boolean akeydown = false;
	private static boolean dkeydown = false;
	


	double[][] Map = new double[0][0];
	double[][] FeatureMap = new double[0][0];
	void initialize()
	{	
		
		//double[][][] maps = BasicHeightMap.getBasicHeightMap(3000000, 3000000, 0.2);
		double[][][] maps = BasicHeightMap.getBasicHeightMap(2<<20, 2<<20, 0.1);
		Map = maps[0];
		FeatureMap = maps[1];
	}

	void functions()
	{
		Update();
	}
	void Update()
	{
		if(akeydown)
		{
			centerx -= 10;
		}
		if(dkeydown)
		{
			centerx += 10;
		}
		if(wkeydown)
		{
			centery -= 10;
		}
		if(skeydown)
		{
			centery += 10;
		}
	}


	public static int centerx = 10;
	public static int centery = 10;
	
	public void newGraphics()
	{
		super.update(g2d);
		g2d = (Graphics2D)frame.getGraphics();		
	}
	
	public void fillRect(int x, int y, int width, int height, Color color)
	{
		//super.repaint(10,10, height, height, height);
		g2d.setPaint(color);
		g2d.fillRect(x, y, width, height);
	}
	
	public void drawImage(Image img, int x, int y)
	{
		//super.repaint();
		g2d.drawImage(img,x,y,null);
		
	}
	
	public static Main game;
	public static JFrame frame;
	public static Graphics2D g2d = null;
	public static void main(String[] args) throws InterruptedException 
	{
		frame = new JFrame("terrainOutput");
		game = new Main(frame);
		frame.add(game);
		frame.setSize(1024, 720);
		frame.setVisible(true);
		frame.setResizable(false);
		g2d = (Graphics2D)frame.getGraphics();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.initialize();
		
		while (true) 
		{
			game.functions();
			Thread.sleep(10);
		}
	}

}

