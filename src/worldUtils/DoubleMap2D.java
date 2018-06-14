package worldUtils;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class DoubleMap2D
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


	public DoubleMap2D(Stream<Double2D> stream)
	{
		this(stream.toArray(size -> new Double2D[size]));
	}

	public DoubleMap2D(Collection<Double2D> values)
	{
		this(values.toArray(new Double2D[values.size()]));
	}
	
	public DoubleMap2D(int xsize, int ysize)
	{
		this(new double[xsize][ysize]);
	}
	
	public DoubleMap2D(double[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
		
	}


	public DoubleMap2D(Double2D[] values)
	{
		int xMax = 0;
		int yMax = 0;
		for(Double2D h : values)
		{
			xMax = Math.max(xMax, h.getX());
			yMax = Math.max(yMax, h.getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = new double[xSize][ySize];
		for(Double2D h: values)
		{
			setHeight(h);
		}
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

	public Double2D getHeight(int x, int y)
	{
		return new Double2D(x,y,get(x,y));
	}
	
	public void setHeight(Double2D h)
	{
		map[h.getX()][h.getY()] = h.get();
	}
	
	public DoubleMap2D clone()
	{
		@SuppressWarnings("unchecked")
		double[][] newMap = new double[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new DoubleMap2D(newMap);
	}
	
	public Stream<Double2D> stream()
	{
		Double2D[] heightArr = new Double2D[xSize*ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				heightArr[x*ySize + y] = getHeight(x,y);
			}
		}
		return Arrays.stream(heightArr).parallel();
	}
	
	
	public static DoubleMap2DCollector COLLECTOR = new DoubleMap2DCollector();
}

class DoubleMap2DCollector implements Collector<Double2D, ArrayList<Double2D>, DoubleMap2D>{

	@Override
	public BiConsumer<ArrayList<Double2D>, Double2D> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<ArrayList<Double2D>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<ArrayList<Double2D>, DoubleMap2D> finisher() {
		return (list) -> new DoubleMap2D(list);
	}

	@Override
	public Supplier<ArrayList<Double2D>> supplier() {
		return () -> new ArrayList<Double2D>();
	}


}