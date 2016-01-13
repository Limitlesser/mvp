package wind.mvp.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import wind.mvp.utils.ReflectUtils;
import wind.mvp.utils.Stream;

/**
 * Created by wind on 2016/1/12.
 */
public class MVP<M extends IModel, V extends IView, P extends Presenter> {

    private static final String TAG = "MVP";

    private boolean mAttached = false;


    private V mView;
    private Map<Class<? extends IModel>, IModel> mModels = new HashMap<>();
    private Map<Class<? extends Presenter>, Presenter> mPresenters = new HashMap<>();

    private Class<M> mModelClass;
    private Class<V> mViewClass;
    private Class<P> mPresenterClass;

    public Class<M> getModelClass() {
        return mModelClass;
    }

    public Class<V> getViewClass() {
        return mViewClass;
    }

    public Class<P> getPresenterClass() {
        return mPresenterClass;
    }

    public void attach(V view) {
        mView = view;
        mViewClass = ReflectUtils.getClassGenericType(view.getClass(), 0);
        mPresenterClass = ReflectUtils.getClassGenericType(view.getClass(), 1);
        if (!mViewClass.isInstance(view)) {
            throw new IllegalArgumentException("view must implement " + mViewClass.getName());
        }
        mAttached = true;
    }

    private void checkClasses() {
        if (!IModel.class.isAssignableFrom(mModelClass)) {
            throw new RuntimeException("can't detect the type of model, have you declare the generic type of presenter");
        }
        if (!IView.class.isAssignableFrom(mViewClass)) {
            throw new RuntimeException("can't detect the type of view, have you declare the generic type of view");
        }
        if (!Presenter.class.isAssignableFrom(mPresenterClass)) {
            throw new RuntimeException("can't detect the type of presenter, have you declare the generic type of view");
        }
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
        T t = ReflectUtils.newInstance(cls);
        t.onInit(this);
        return t;
    }

    public <T extends IModel> T createModel(Class<T> cls) {
        return ReflectUtils.newInstance(cls);
    }

    private <T> void foreach(Map<?, T> map, Stream.Each<T> each) {
        Stream.from(map.values())
                .foreach(each);
    }


    public void onCreate(final Bundle savedInstanceState) {
        mModelClass = ReflectUtils.getClassGenericType(getPresenter().getClass(), 1);
        checkClasses();
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
        if (mView != null && cls.isInstance(mView)) {
            return (T) mView;
        }
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

    public boolean hasView() {
        return mView != null;
    }

    public <T extends IView> boolean hasView(Class<T> cls) {
        return mView != null && cls.isInstance(mView);
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
        mView = null;
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
