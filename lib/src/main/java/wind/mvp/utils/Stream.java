package wind.mvp.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wind on 2016/1/12.
 */
public class Stream<T> {

    public interface Each<T> {
        void onEach(T t);
    }

    public interface Map<T, F> {
        F onMap(T t);
    }

    private Iterable<T> mIterable;

    private Stream(Iterable<T> iterable) {
        this.mIterable = iterable;
    }

    public static <T> Stream<T> from(Iterable<T> iterable) {
        return new Stream<>(iterable);
    }

    public static <T> Stream<T> from(T[] arr) {
        return from(Arrays.asList(arr));
    }

    public Stream<T> foreach(Each<T> action) {
        if (mIterable == null) {
            return this;
        }
        for (T t : mIterable) {
            action.onEach(t);
        }
        return this;
    }

    public <F> Stream<F> map(Map<T, F> map) {
        if (mIterable == null) {
            return Stream.from((Iterable<F>) null);
        }
        List<F> list = new LinkedList<>();
        for (T t : mIterable) {
            list.add(map.onMap(t));
        }
        return from(list);
    }

}
