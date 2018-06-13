package worldUtils;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.Serializable;
import java.lang.reflect.Array;
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

public class ValueMap2D<E> 
{
	private static final long serialVersionUID = 1L;
	private final int xSize;
	private final int ySize;
	private final E[][] map;

	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}

	public ValueMap2D(E[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}

	public ValueMap2D(ArrayList<Value2D<E>> heights)
	{
		this(heights.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public ValueMap2D(int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = (E[][]) new Object[xsize][ysize];
	}

	@SuppressWarnings("unchecked")
	public ValueMap2D(Object[] heights)
	{
		int xMax = 0;
		int yMax = 0;
		for(Object h : heights)
		{
			xMax = Math.max(xMax, ((Value2D<E>)h).getX());
			yMax = Math.max(yMax, ((Value2D<E>)h).getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = (E[][]) new Object[xSize][ySize];
		for(Object h: heights)
		{
			setHeight((Value2D<E>)h);
		}
	}
	
	public ValueMap2D(Stream<Value2D<E>> stream)
	{
		this(stream.toArray());
	}
	
	public E[][] getMap()
	{
		return map;
	}

	public E get(int x, int y)
	{
		return map[x][y];
	}
	
	public void set(int x, int y, E val)
	{
		map[x][y] = val;
	}

	public Value2D<E> getHeight(int x, int y)
	{
		return new Value2D<E>(x,y,get(x,y));
	}
	
	public void setHeight(Value2D<E> h)
	{
		map[h.getX()][h.getY()] = h.getVal();
	}
	
	public ValueMap2D<E> clone()
	{
		@SuppressWarnings("unchecked")
		E [][] newMap = (E[][])new Object[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new ValueMap2D<E>(newMap);
	}
	
	@SuppressWarnings("unchecked")
	public Stream<Value2D<E>> stream()
	{
		Value2D<E>[] heightArr = (Value2D<E>[]) new Object[xSize*ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				heightArr[x*ySize + y] = getHeight(x,y);
			}
		}
		return Arrays.stream(heightArr);
	}
	
	
	public static HeightMapCollector getCollector()
	{
		return new HeightMapCollector();
	}
}

class HeightMapCollector<E> implements Collector<Value2D<E>, ArrayList<Value2D<E>>, ValueMap2D<E>>{

	@Override
	public BiConsumer<ArrayList<Value2D<E>>, Value2D<E>> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<ArrayList<Value2D<E>>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<ArrayList<Value2D<E>>, ValueMap2D<E>> finisher() {
		return (list) -> new ValueMap2D<E>(list);
	}

	@Override
	public Supplier<ArrayList<Value2D<E>>> supplier() {
		return () -> new ArrayList<Value2D<E>>();
	}


}