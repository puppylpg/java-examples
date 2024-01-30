package xyz.puppylpg;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://stackoverflow.com/a/77584226/7676237
 *
 * @author puppylpg on 2024/01/30
 */
public class CollectToList {

	/**
	 * {@link Stream#collect(Collector)}的参数是：`<R, A> R collect(Collector<? super T, A, R> collector)`
	 * 接收的collector的list里定义的是`? super T`，所以即使这个collector collect的是{@link Integer}，依然可以返回{@link Number}
	 */
	List<Number> old = Stream.of(0).collect(Collectors.toList());

	/**
	 * Required type:
	 * List<Number>
	 *
	 * Provided:
	 * List<Integer>
	 *
	 * 但是{@link Stream#toList()}的参数是：List<T> toList()，只能返回Integer
	 */
//	List<Number> error = Stream.of(0).toList();

	/**
	 * 所以要么把Integer转成Number
	 */
	List<Number> fix1 = Stream.of(0).map(Number.class::cast).toList();

	/**
	 * 要么定义为List<? extends Number>
	 */
	List<? extends Number> fix2 = Stream.of(0).toList();
}
