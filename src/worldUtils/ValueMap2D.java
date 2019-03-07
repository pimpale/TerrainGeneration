package worldUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class ValueMap2D
{
	private final int xSize;
	private final int ySize;
	private final double[][] map;

	public ValueMap2D(Collection<Value2D> values)
	{
		int xMax = 0;
		int yMax = 0;
		for(Value2D h : values)
		{
			xMax = Math.max(xMax, h.getX());
			yMax = Math.max(yMax, h.getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = new double[xSize][ySize];
		for(Value2D h: values)
		{
			setHeight(h);
		}
	}
	
	public ValueMap2D(int xsize, int ysize)
	{
		this(new double[xsize][ysize]);
	}

	public ValueMap2D(double[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}

	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
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
		return new Value2D(x,y,get(x,y));
	}
	
	public void setHeight(Value2D h)
	{
		map[h.getX()][h.getY()] = h.get();
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
	
	
	public static DoubleMap2DCollector COLLECTOR = new DoubleMap2DCollector();
}

class DoubleMap2DCollector implements Collector<Value2D, Collection<Value2D>, ValueMap2D>{

	@Override
	public BiConsumer<Collection<Value2D>, Value2D> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<Collection<Value2D>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<Collection<Value2D>, ValueMap2D> finisher() {
		return (list) -> new ValueMap2D(list);
	}

	@Override
	public Supplier<Collection<Value2D>> supplier() {
		return () -> Collections.synchronizedCollection(new ArrayList<Value2D>());
	}
}