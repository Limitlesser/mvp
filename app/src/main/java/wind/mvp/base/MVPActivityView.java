package wind.mvp.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wind on 2016/1/12.
 */
public class MVPActivityView<M extends IModel<P>, V extends IView<P>, P extends Presenter<V, M>> extends AppCompatActivity implements IView<P> {

    protected MVP<M, V, P> mMVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMVP = new MVP<>();
        onCreateView();
        onCreatePresenter();
        mMVP.onCreate(savedInstanceState);
    }

    @Override
    public P getPresenter() {
        return mMVP.getPresenter();
    }

    @Override
    public <T extends Presenter> T getPresenter(Class<T> cls) {
        return mMVP.getPresenter(cls);
    }

    @Override
    public void onCreateView() {
        addView(mMVP.getViewClass(), this);
    }

    @Override
    public void onCreatePresenter() {
        addPresenter(mMVP.getPresenterClass());
    }

    @Override
    public void addPresenter(Class<? extends Presenter> cls) {
        mMVP.addPresenter(cls);
    }

    @Override
    public void addView(Class<? extends IView> cls, IView<P> view) {
        mMVP.addView(cls, view);
    }

    @Override
    public MVP getMVP() {
        return mMVP;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mMVP.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMVP.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMVP.onDestroy();
        mMVP.onDetach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMVP.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMVP.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMVP.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMVP.onStart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMVP.onRestoreInstanceState(savedInstanceState);
    }

}
