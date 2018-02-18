import java.io.PipedOutputStream;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;


public class WorldHistoryDisplay implements Runnable{

	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	private JFrame frame;
	private ImageIcon image;
	private JLabel imageLabel;
	private JTextArea status;
	private JScrollPane statusScroll;
	private JButton startButton;
	private JSplitPane leftSplitPane;
	private JSplitPane splitPane;

	private WorldTemplate template;
	private BufferedImage window;
	private PipedOutputStream out;
	private boolean paused;

	public boolean isPaused()
	{
		return paused;
	}

	public void repaint()
	{
		image.setImage(window);
	}


	public WorldHistoryDisplay(WorldTemplate t)
	{
		template = t;
		window = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_4BYTE_ABGR);
		image = new ImageIcon(window);
		imageLabel = new JLabel(image);
		status = new JTextArea("");
		statusScroll = new JScrollPane(status);

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isPaused())
				{
					startButton.setText("Stop");
					paused = false;
				}
				else if(!isPaused())
				{
					startButton.setText("Start");
					paused = true;
				}
			}
		});
		
		leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				statusScroll, startButton);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftSplitPane, imageLabel);

	}

	@Override
	public void run() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(splitPane);
		frame.pack();
		frame.setVisible(true);
	}

}
