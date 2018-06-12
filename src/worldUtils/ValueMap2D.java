package worldUtils;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class ValueMap2D implements Cloneable, Serializable
{
	private static final long serialVersionUID = 1L;
	private final int xSize;
	private final int ySize;
	private final double[][] map;

	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}

	public ValueMap2D(double[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}

	public ValueMap2D(ArrayList<Value2D> heights)
	{
		this(heights.toArray());
	}
	
	public ValueMap2D(int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new double[xSize][ySize];
	}

	public ValueMap2D(Object[] heights)
	{
		int xMax = 0;
		int yMax = 0;
		for(Object h : heights)
		{
			xMax = Math.max(xMax, ((Value2D)h).getX());
			yMax = Math.max(yMax, ((Value2D)h).getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = new double[xSize][ySize];
		for(Object h: heights)
		{
			setHeight((Value2D)h);
		}
	}
	
	public ValueMap2D(Stream<Value2D> stream)
	{
		this(stream.toArray());
	}
	
	public double[][] getMap()
	{
		return map;
	}

	public double get(int x, int y)
	{
		return map[x][y];
	}
	
	public void set(int x, int y, double val)
	{
		map[x][y] = val;
	}

	public Value2D getHeight(int x, int y)
	{
		return new Value2D(x,y,map[x][y]);
	}
	
	public void setHeight(Value2D h)
	{
		map[h.getX()][h.getY()] = h.getVal();
	}
	
	public ValueMap2D clone()
	{
		double[][] newMap = new double[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new ValueMap2D(newMap);
	}
	
	public Stream<Value2D> stream()
	{
		Value2D[] heightArr = new Value2D[xSize*ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				heightArr[x*ySize + y] = getHeight(x,y);
			}
		}
		return Arrays.stream(heightArr);
	}
	
	public BufferedImage getImage()
	{
		BufferedImage bimg = new BufferedImage(xSize, ySize, BufferedImage.TYPE_USHORT_GRAY);
		WritableRaster raster = bimg.getRaster();
		int[] r = new int[1];
		for(int x = 0; x < xSize; x++) 
		{
			for(int y = 0; y < ySize; y++)
			{
				r[0] = (short)(map[x][y]*Short.MAX_VALUE + Short.MIN_VALUE);
				raster.setPixel(x, y, r);
			}
		}
		return bimg;
	}
	
	public static HeightMapCollector getCollector()
	{
		return new HeightMapCollector();
	}
}

class HeightMapCollector implements Collector<Value2D, ArrayList<Value2D>, ValueMap2D>{

	@Override
	public BiConsumer<ArrayList<Value2D>, Value2D> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<ArrayList<Value2D>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<ArrayList<Value2D>, ValueMap2D> finisher() {
		return (list) -> new ValueMap2D(list);
	}

	@Override
	public Supplier<ArrayList<Value2D>> supplier() {
		return () -> new ArrayList<Value2D>();
	}


}