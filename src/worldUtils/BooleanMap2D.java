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

public class BooleanMap2D
{
	private static final long serialVersionUID = 1L;
	private final int xSize;
	private final int ySize;
	private final boolean[][] map;

	public int getXSize()
	{
		return xSize;
	}

	public int getYSize()
	{
		return ySize;
	}


	public BooleanMap2D(Stream<Boolean2D> stream)
	{
		this(stream.toArray(size -> new Boolean2D[size]));
	}

	public BooleanMap2D(Collection<Boolean2D> values)
	{
		this(values.toArray(new Boolean2D[values.size()]));
	}
	
	public BooleanMap2D(int xsize, int ysize)
	{
		this(new boolean[xsize][ysize]);
	}
	
	public BooleanMap2D(boolean[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
		
	}


	public BooleanMap2D(Boolean2D[] values)
	{
		int xMax = 0;
		int yMax = 0;
		for(Boolean2D h : values)
		{
			xMax = Math.max(xMax, h.getX());
			yMax = Math.max(yMax, h.getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = new boolean[xSize][ySize];
		for(Boolean2D h: values)
		{
			setHeight(h);
		}
	}
	
	
	public boolean[][] getMap()
	{
		return map;
	}

	public boolean get(int x, int y)
	{
		return map[x][y];
	}
	
	public void set(int x, int y, boolean val)
	{
		map[x][y] = val;
	}

	public Boolean2D getHeight(int x, int y)
	{
		return new Boolean2D(x,y,get(x,y));
	}
	
	public void setHeight(Boolean2D h)
	{
		map[h.getX()][h.getY()] = h.get();
	}
	
	public BooleanMap2D clone()
	{
		@SuppressWarnings("unchecked")
		boolean[][] newMap = new boolean[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new BooleanMap2D(newMap);
	}
	
	public Stream<Boolean2D> stream()
	{
		Boolean2D[] heightArr = new Boolean2D[xSize*ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				heightArr[x*ySize + y] = getHeight(x,y);
			}
		}
		return Arrays.stream(heightArr).parallel();
	}
	
	
	public static BooleanMap2DCollector COLLECTOR = new BooleanMap2DCollector();
}

class BooleanMap2DCollector implements Collector<Boolean2D, ArrayList<Boolean2D>, BooleanMap2D>{

	@Override
	public BiConsumer<ArrayList<Boolean2D>, Boolean2D> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<ArrayList<Boolean2D>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<ArrayList<Boolean2D>, BooleanMap2D> finisher() {
		return (list) -> new BooleanMap2D(list);
	}

	@Override
	public Supplier<ArrayList<Boolean2D>> supplier() {
		return () -> new ArrayList<Boolean2D>();
	}


}