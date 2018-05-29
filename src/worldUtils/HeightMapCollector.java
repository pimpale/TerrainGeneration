package worldUtils;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class HeightMapCollector implements Collector<Height, ArrayList<Height>, HeightMap>{

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
