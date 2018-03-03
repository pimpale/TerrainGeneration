package deprecatedButIStillWantToKeep;


import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.awt.image.BufferedImage;

public class Screen
{	

	public static short WindDoubleToShort(double doub)
	{
		return WorldUtils.DoubleToShort(0.5+16*doub);
	}

	public static double WindShortToDouble(short shor)
	{
		return (WorldUtils.ShortToDouble(shor)-0.5)/16;
	}

	public static void drawWinds(int xsizem, int ysizem)
	{
		short[][] xvec = new short[xsizem][ysizem];
		short[][] yvec = new short[xsizem][ysizem];
		
		for(int x = 0; x < xsizem; x++)
		{
			for(int y = 0; y < ysizem; y++)
			{
				if(y < ysizem/6)
				{
					xvec[x][y] += WindDoubleToShort(+0.005);
					yvec[x][y] += WindDoubleToShort(+0.000);
				}
				else if(y < 2*ysizem/6)
				{
					xvec[x][y] += WindDoubleToShort(-0.005);
					yvec[x][y] += WindDoubleToShort(-0.000);
				}
				else if(y < 3*ysizem/6)
				{
					xvec[x][y] += WindDoubleToShort(+0.005);
					yvec[x][y] += WindDoubleToShort(+0.000);
				}
				else if(y < 4*ysizem/6)
				{
					xvec[x][y] += WindDoubleToShort(+0.005);
					yvec[x][y] += WindDoubleToShort(-0.000);
				}
				else if(y < 5*ysizem/6)
				{
					xvec[x][y] += WindDoubleToShort(-0.005);
					yvec[x][y] += WindDoubleToShort(+0.000);
				}
				else if(y < 6*ysizem/6)
				{
					xvec[x][y] += WindDoubleToShort(+0.005);
					yvec[x][y] += WindDoubleToShort(-0.000);
				}		
			}
		}
		ShortMap xm = new ShortMap(xvec);
		ShortMap ym = new ShortMap(yvec);
		String s = "./Resources/Templates/2PoleContinental/";
		xm.blur(8);
		ym.blur(8);
		try {
			xm.Export(s+"WindX.png");
			ym.Export(s+"WindY.png");
			System.out.println("nice");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	static volatile ArrayList<Object> EntityList = new ArrayList<Object>();//the type of the Entity. Can be 
	static volatile double[][] gravXvector;
	static volatile double[][] gravYvector;

	static volatile double[][] rain;

	static int ThreadCount = Runtime.getRuntime().availableProcessors();

	static int xSize;
	static int ySize;

	static double[][] hpm = null;


	public static void wind()
	{	
		ShortMap hmap = new ShortMap("./WorldSave/Elevation.png");
		xSize = hmap.getXSize();
		ySize = hmap.getYSize();
		gravXvector = new double[xSize][ySize];
		gravYvector = new double[xSize][ySize];
		rain = new double[xSize][ySize];
		String s = "./Resources/Templates/2PoleContinental/";
		ShortMap hx = new ShortMap(s+"WindX.png");
		ShortMap hy = new ShortMap(s+"WindY.png");
		hx = hx.scale(xSize, ySize);
		hy = hy.scale(xSize, ySize);
		for(int x = 0; x < xSize; x ++)
		{
			for(int y = 0; y < ySize; y++)
			{
				gravXvector[x][y] = (float) WindShortToDouble(hx.get(x,y));
				gravYvector[x][y] = (float) WindShortToDouble(hy.get(x,y));
			}
		}
		ExecutorService executor = Executors.newFixedThreadPool(ThreadCount);
		class DoSegment implements Runnable
		{		
			final double startX;
			final double startY;
			final double Val;
			public DoSegment(double startx, double starty, double val)
			{
				startX = startx;
				startY = starty;
				Val = val;
			}

			@Override
			public void run() {
				int x1;
				int y1;
				int rx;
				int ry;
				double dir;
				double dist;
				double mag;
				int sqrradius = 128;
				for(int nx = 0; nx < sqrradius*2; nx++)
				{
					for(int ny = 0; ny < sqrradius*2; ny++)
					{
						x1 = nx-sqrradius;
						y1 = ny-sqrradius;
						rx = (int) (startX+x1);
						ry = (int) (startY+y1);
						if(rx > -1 && rx < xSize && ry > -1 && ry < ySize)
						{
							dir = Math.atan2(y1, x1);
							dist = Math.sqrt(x1*x1+y1*y1);
							mag = Val*Math.pow(64+dist,-2);
							gravXvector[rx][ry] += mag*Math.cos(dir);
							gravYvector[rx][ry] += mag*Math.sin(dir);
						}				
					}
				}
			}
		}
		double val;
		for(int x = 0; x < xSize; x+=16)
		{
			for(int y = 0; y < ySize; y+=16)
			{
				val = WorldUtils.ShortToDouble(hmap.get(x, y));
				if(val > 0.2)
				{
					System.out.println(x);
					executor.execute(new DoSegment(x,y,val*16));
				}
			}
		}
		executor.shutdown();  
		while (!executor.isTerminated()) {}  
		ShortMap wxm = new ShortMap(xSize, ySize);
		ShortMap wym = new ShortMap(xSize, ySize);
		double maxval = WindShortToDouble((short)(Short.MAX_VALUE-1));
		double minval = WindShortToDouble((short)(Short.MIN_VALUE+1));
		double xwind;
		double ywind;
		for(int x = 0; x < xSize; x+=1)
		{
			for(int y = 0; y < ySize; y+=1)
			{
				xwind = gravXvector[x][y];
				ywind = gravYvector[x][y];
				if(xwind > maxval){xwind = maxval;}
				else if(xwind < minval){xwind = minval;}
				
				if(ywind > maxval){ywind = maxval;}
				else if(ywind < minval){ywind = minval;}
			
				wxm.set(x, y, WindDoubleToShort(xwind));
				wym.set(x, y, WindDoubleToShort(ywind));
			}
		}
		wxm.blur(8);
		wxm.Export("./WorldSave/WindX.png");
		wym.Export("./WorldSave/WindY.png");
		System.out.println("wind generated");
		run();
	}

	static public int nicre()
	{
		return (int)(Math.random()*xSize);
	}

	static int timer = 0;
	public static int run()
	{
		ShortMap hmap = new ShortMap("./WorldSave/Elevation.png");
		ShortMap wxm  = new ShortMap("./WorldSave/WindX.png");
		ShortMap wym  = new ShortMap("./WorldSave/WindY.png");

		xSize = hmap.getXSize();
		ySize = hmap.getYSize();
		gravXvector = new double[xSize][ySize];
		gravYvector = new double[xSize][ySize];
		rain = new double[xSize][ySize];
		hpm =  new double[xSize][ySize];
		for(int x = 0; x < xSize; x+=1)
		{
			for(int y = 0; y < ySize; y+=1)
			{
			//	gravXvector[x][y] = WindShortToDouble((short)(wxm.get(x, y)+xnoise.eval(x/500f, y/500f)*4096));
			//	gravYvector[x][y] = WindShortToDouble((short)(wym.get(x, y)+ynoise.eval(x/500f, y/500f)*4096));
				gravXvector[x][y] = WindShortToDouble(wxm.get(x, y));
				gravYvector[x][y] = WindShortToDouble(wym.get(x, y));
			}
		}
		double value;
		for(int x = 0; x < hpm.length; x++)
		{
			for(int y = 0; y < hpm[0].length; y++)
			{
				value = WorldUtils.ShortToDouble(hmap.get(x, y));
				hpm[x][y] = value;
				if(value < 0.2)
				{
					hpm[x][y] = 0.19999;
				}
			}
		}
		
		class Entity
		{
			public double X;
			public double Y;
			public double YMomentum = 0;
			public double XMomentum = 0;
			public double Curl = 0;
			public Entity(double x, double y)
			{
				X = x;
				Y = y;
			}
		}
		
		for(int x = 0; x < xSize; x+=8)
		{
			for(int y = 0; y < ySize; y+=8)
			{
				Entity e = new Entity(x, y);
//				e.Mass = Math.random()*2;
				e.Curl = (Math.random()*2-1)/512;
				EntityList.add(e);
			}
		}
		System.out.println(EntityList.size());
		AtomicInteger threadcount = new AtomicInteger(ThreadCount);
		Executor exe = Executors.newFixedThreadPool(ThreadCount);
		ArrayList<Entity> replacementList = new ArrayList<Entity>();
		//Main.g2d.setPaint(new Color(0,0,255,10));
		class AffectEntities implements Runnable
		{
			final int begin, end;
			public AffectEntities(int Begin, int End)
			{
				begin = Begin;	end = End;
			}
			
			@Override
			public void run()
			{
				synchronized(EntityList)
				{
					for(int i = begin; i < end; i++)
					{
						Entity e = (Entity) EntityList.get(i);
						int x, y;
						x = (int) e.X;
						y = (int) e.Y;
						if(x < xSize && y < ySize && x > -1 && y> -1)
						{
							e.XMomentum+=gravXvector[x][y];
							e.YMomentum+=gravYvector[x][y];
							double mag = Math.sqrt(e.XMomentum*e.XMomentum + e.YMomentum*e.YMomentum);
							double dir = Math.atan2(e.YMomentum, e.XMomentum);
						//	Main.g2d.fillRect(x, y, 1, 1);
							e.XMomentum = Math.cos(dir+e.Curl)*mag;
							e.YMomentum = Math.sin(dir+e.Curl)*mag;
							rain[x][y] += mag;
							e.X+=e.XMomentum;
							e.Y+=e.YMomentum;
							replacementList.add(e);
						}
						else if(x < xSize + 100 && y < ySize+100 && x > -100 && y > -100)
						{
							replacementList.add(e);
							e.X+=e.XMomentum;
							e.Y+=e.YMomentum;
						}
					}
				}
				threadcount.decrementAndGet();
			}
		}

		int timer = 0;		
		while (true) 
		{
			timer++;
			if(timer > 1000)
			{
				short[][] rainmap = new short[xSize][ySize];
				for(int x= 0; x < xSize; x++)
				{
					for(int y = 0; y < xSize; y++)
					{
						double val = rain[x][y]*hpm[x][y]*hpm[x][y]*0.8;
						val = Math.pow(val, 0.5);
						if(val > 1)
						{
							val = 0.99;
						}
						else if(val < 0)
						{
							val = 0;
						}
						rainmap[x][y] = WorldUtils.DoubleToShort(val);
					}
				}
				ShortMap m = new ShortMap(rainmap);
				System.out.println("ok... plz wait");
				m.blur(16);
				m.Export("./WorldSave/RainMap.png");
				System.out.println("rain complete");
				return 0;
			}
			else
			{
				for(int i =0; i < ThreadCount; i++)
				{
					exe.execute(new AffectEntities(
							i*EntityList.size()/ThreadCount,
							(i+1)*EntityList.size()/ThreadCount));
					//System.out.println(i*EntityList.size()/ThreadCount);
				}
				while(threadcount.get() != 0) {	}
				EntityList.clear();
				for(Entity e:replacementList)
				{
					EntityList.add(e);
				}
				replacementList.clear();
				threadcount.set(ThreadCount);
			}
		}
	}
}
