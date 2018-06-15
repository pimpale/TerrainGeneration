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

public class IntegerMap2D
{
	private static final long serialVersionUID = 1L;
	private final int xSize;
	private final int ySize;
	private final int[][] map;

	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}


	public IntegerMap2D(Stream<Integer2D> stream)
	{
		this(stream.toArray(size -> new Integer2D[size]));
	}

	public IntegerMap2D(Collection<Integer2D> values)
	{
		this(values.toArray(new Integer2D[values.size()]));
	}
	
	public IntegerMap2D(int xsize, int ysize)
	{
		this(new int[xsize][ysize]);
	}
	
	public IntegerMap2D(int[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
		
	}


	public IntegerMap2D(Integer2D[] values)
	{
		int xMax = 0;
		int yMax = 0;
		for(Integer2D h : values)
		{
			xMax = Math.max(xMax, h.getX());
			yMax = Math.max(yMax, h.getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = new int[xSize][ySize];
		for(Integer2D h: values)
		{
			setHeight(h);
		}
	}
	
	
	public int[][] getMap()
	{
		return map;
	}

	public int get(int x, int y)
	{
		return map[x][y];
	}
	
	public void set(int x, int y, int val)
	{
		map[x][y] = val;
	}

	public Integer2D getHeight(int x, int y)
	{
		return new Integer2D(x,y,get(x,y));
	}
	
	public void setHeight(Integer2D h)
	{
		map[h.getX()][h.getY()] = h.get();
	}
	
	public IntegerMap2D clone()
	{
		@SuppressWarnings("unchecked")
		int[][] newMap = new int[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new IntegerMap2D(newMap);
	}
	
	public Stream<Integer2D> stream()
	{
		Integer2D[] heightArr = new Integer2D[xSize*ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				heightArr[x*ySize + y] = getHeight(x,y);
			}
		}
		return Arrays.stream(heightArr);
	}
	
	
	public static IntegerMap2DCollector COLLECTOR = new IntegerMap2DCollector();
}

class IntegerMap2DCollector implements Collector<Integer2D, ArrayList<Integer2D>, IntegerMap2D>{

	@Override
	public BiConsumer<ArrayList<Integer2D>, Integer2D> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<ArrayList<Integer2D>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<ArrayList<Integer2D>, IntegerMap2D> finisher() {
		return (list) -> new IntegerMap2D(list);
	}

	@Override
	public Supplier<ArrayList<Integer2D>> supplier() {
		return () -> new ArrayList<Integer2D>();
	}


}