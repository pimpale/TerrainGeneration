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

public class HeightMap implements Cloneable, Serializable
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

	public HeightMap(double[][] newmap)
	{
		map  = newmap;
		xSize = map.length;
		ySize = map[0].length;
	}

	public HeightMap(ArrayList<Height> heights)
	{
		this(heights.toArray());
	}
	
	public HeightMap(int xsize, int ysize)
	{
		xSize = xsize;
		ySize = ysize;
		map = new double[xSize][ySize];
	}

	public HeightMap(Object[] heights)
	{
		int xMax = 0;
		int yMax = 0;
		for(Object h : heights)
		{
			xMax = Math.max(xMax, ((Height)h).getX());
			yMax = Math.max(yMax, ((Height)h).getY());
		}
		xSize = xMax+1;
		ySize = yMax+1;
		map = new double[xSize][ySize];
		for(Object h: heights)
		{
			setHeight((Height)h);
		}
	}
	
	public HeightMap(Stream<Height> stream)
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

	public Height getHeight(int x, int y)
	{
		return new Height(x,y,map[x][y]);
	}
	
	public void setHeight(Height h)
	{
		map[h.getX()][h.getY()] = h.getVal();
	}
	
	public HeightMap clone()
	{
		double[][] newMap = new double[xSize][ySize];
		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				newMap[x][y] = map[x][y];
			}
		}
		return new HeightMap(newMap);
	}
	
	public Stream<Height> stream()
	{
		Height[] heightArr = new Height[xSize*ySize];
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

class HeightMapCollector implements Collector<Height, ArrayList<Height>, HeightMap>{

	@Override
	public BiConsumer<ArrayList<Height>, Height> accumulator() {
		return (list, h) -> list.add(h);
	}

	@Override
	public Set<Characteristics> characteristics() {
		 return EnumSet.of(Characteristics.UNORDERED);
	}

	@Override
	public BinaryOperator<ArrayList<Height>> combiner() {
		return (list1, list2) -> {
			list1.addAll(list2);
			return list1;
		};
	}

	@Override
	public Function<ArrayList<Height>, HeightMap> finisher() {
		return (list) -> new HeightMap(list);
	}

	@Override
	public Supplier<ArrayList<Height>> supplier() {
		return () -> new ArrayList<Height>();
	}


}