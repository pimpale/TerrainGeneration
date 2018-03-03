package deprecatedButIStillWantToKeep;
import java.util.ArrayList;

public class River {
	public static final byte RIVER_END_FEED_INTO_ANOTHER = 5;
	public static final byte RIVER_END_REACH_OCEAN = 4;
	public static final byte RIVER_START_SPRING = 3;
	public static final byte RIVER_NORMAL = 0;
	
	
	ArrayList<RiverNode> r = new ArrayList<RiverNode>();
	public River()
	{
		
	}
	
	public void appendNewPoint(int x, int y, double value, byte code)
	{
		RiverNode rn = new RiverNode(code, value, x,y);
		r.add(rn);
	}
	
	public void removePoint(int loc)
	{
		r.remove(loc);
	}
	
	
	public int getLocationPoint(int x, int y)
	{
		for(int i = 0; i < r.size(); i++)
		{
			RiverNode rivn = r.get(i);
			if(rivn.X == x && rivn.Y == y)
			{
				return i;
			}
		}
		return -1;
	}
	
	public double getPower(int loc)
	{
		return r.get(loc).Value;
	}
	
	public int getX(int loc)
	{
		return r.get(loc).X;
	}
	public int getY(int loc)
	{
		return r.get(loc).Y;
	}
	
	
	public int length()
	{
		return r.size();
	}
	
	
	public void mergeAtPoint(int mergeloc)
	{
	//	System.out.println("   " + mergeloc);
		ArrayList<RiverNode> newRiverList = new ArrayList<RiverNode>(10);
		double rivPow = 1;
		r.get(r.size()-1);
		RiverNode feedintonew = new RiverNode(River.RIVER_END_FEED_INTO_ANOTHER,
				rivPow,
				r.get(mergeloc).X, r.get(mergeloc).Y);
		newRiverList.add(feedintonew);
		r = newRiverList;
	}
	
}

class RiverNode
{
	double Code;
	double Value;
	int X;
	int Y;
	
	public RiverNode(byte code, double value, int x, int y)
	{
		Code = code;
		Value = value;
		X = x;
		Y = y;
	}
}