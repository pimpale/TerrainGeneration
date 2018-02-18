import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class WorldHistoryDisplay {
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	private JFrame frame;
	private ImageIcon image;
	private JTextArea status;
	private JScrollPane statusScroll;
	private JButton startButton;
	private JSplitPane leftSplitPane;
	private JSplitPane splitPane;
	
	private WorldTemplate template;
	private BufferedImage window;
	
	
	public WorldHistoryDisplay(WorldTemplate t)
	{
			template = t;
			window = new BufferedImage
	}
	
}
