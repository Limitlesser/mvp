package wind.mvp.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import wind.mvp.utils.ReflectUtils;
import wind.mvp.utils.Stream;

/**
 * Created by wind on 2016/1/12.
 */
public class MVP<M extends IModel, V extends IView, P extends Presenter> {

    private static final String TAG = "MVP";

    private boolean mAttached = false;


    private Map<Class<? extends IModel>, IModel> mModels = new HashMap<>();

    private Map<Class<? extends IView>, IView> mViews = new HashMap<>();

    private Map<Class<? extends Presenter>, Presenter> mPresenters = new HashMap<>();


    public Class<M> getModelClass() {
        return ReflectUtils.getClassGenericType(getClass(), 0);
    }

    public Class<V> getViewClass() {
        return ReflectUtils.getClassGenericType(getClass(), 1);
    }

    public Class<P> getPresenterClass() {
        return ReflectUtils.getClassGenericType(getClass(), 2);
    }


    public void addView(Class<? extends IView> cls, IView view) {
        if (!cls.isInstance(view)) {
            throw new IllegalArgumentException("view not instance of class " + cls.getName());
        }
        mViews.put(cls, view);
    }

    public void addPresenter(Class<? extends Presenter> cls) {
        if (mPresenters.containsKey(cls)) {
            Log.w(TAG, "presenter " + cls.getSimpleName() + " has already added");
            return;
        }
        mPresenters.put(cls, createPresenter(cls));
    }

    public void addModel(Class<? extends IModel> cls) {
        if (mModels.containsKey(cls)) {
            Log.w(TAG, "model " + cls.getSimpleName() + " has already added");
            return;
        }
        mModels.put(cls, createModel(cls));
    }

    public <T extends Presenter> T createPresenter(Class<T> cls) {
        return (T) ReflectUtils.newInstance(cls, new Class[]{getClass()}, new Object[]{this});
    }

    public <T extends IModel> T createModel(Class<T> cls) {
        return (T) ReflectUtils.newInstance(cls, new Class[]{getClass()}, new Object[]{this});
    }

    private <T> void foreach(Map<?, T> map, Stream.Each<T> each) {
        Stream.from(map.values())
                .foreach(each);
    }


    public void onCreate(final Bundle savedInstanceState) {
        mAttached = true;
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onCreate(savedInstanceState);
            }
        });
    }

    public boolean attached() {
        return mAttached;
    }

    public P getPresenter() {
        return getPresenter(getPresenterClass());
    }

    public <T extends Presenter> T getPresenter(Class<T> cls) {
        return (T) mPresenters.get(cls);
    }

    public V getView() {
        return getView(getViewClass());
    }

    public <T extends IView> T getView(Class<T> cls) {
        T view = (T) mViews.get(cls);
        if (view != null) {
            return view;
        }
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

    public boolean hasView() {
        return getView() != null;
    }

    public <T extends IView> boolean hasView(Class<T> cls) {
        return mViews.containsKey(cls);
    }

    public M getModel() {
        return getModel(getModelClass());
    }

    public <T extends IModel> T getModel(Class<T> cls) {
        return (T) mModels.get(cls);
    }

    public void onConfigurationChanged(final Configuration newConfig) {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onConfigurationChanged(newConfig);
            }
        });
    }

    public void onStop() {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onStop();
            }
        });
    }

    public void onDestroy() {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onDestroy();
            }
        });
    }

    public void onDetach(IView view) {
        mAttached = false;
        for (Map.Entry<Class<? extends IView>, IView> entry : mViews.entrySet()) {
            if (entry.getKey().isInstance(view)) {
                mViews.remove(entry.getKey());
            }
        }
    }

    public void onPause() {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onPause();
            }
        });
    }

    public void onResume() {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onResume();
            }
        });
    }

    public void onSaveInstanceState(final Bundle outState) {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onSaveInstanceState(outState);
            }
        });
    }

    public void onStart() {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onStart();
            }
        });
    }

    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        foreach(mPresenters, new Stream.Each<Presenter>() {
            @Override
            public void onEach(Presenter presenter) {
                presenter.onRestoreInstanceState(savedInstanceState);
            }
        });
    }
}
