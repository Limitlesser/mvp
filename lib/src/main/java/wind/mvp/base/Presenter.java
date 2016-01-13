package wind.mvp.base;

import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by wind on 2016/1/12.
 */
public class Presenter<V extends IView, M extends IModel> implements IPresenter<V, M> {

    protected MVP<M, V, ? extends Presenter> mMVP;

    protected void onInit(MVP<M, V, ? extends Presenter> mvp) {
        mMVP = mvp;
    }

    @Override
    public V getView() {
        return mMVP.getView();
    }

    @Override
    public <T extends IView> T getView(Class<T> cls) {
        return mMVP.getView(cls);
    }

    @Override
    public boolean hasView() {
        return mMVP.hasView();
    }

    @Override
    public <T extends IView> boolean hasView(Class<T> cls) {
        return mMVP.hasView(cls);
    }

    @Override
    public M getModel() {
        return mMVP.getModel();
    }

    @Override
    public <T extends IModel> T getModel(Class<T> cls) {
        return mMVP.getModel(cls);
    }

    public void onCreate(Bundle savedInstanceState) {
        onCreateModel();
    }

    protected void onCreateModel() {
        addModel(mMVP.getModelClass());
    }

    @Override
    public void addModel(Class<? extends IModel> cls) {
        mMVP.addModel(cls);
    }

    @Override
    public MVP getMVP() {
        return mMVP;
    }

    public void onConfigurationChanged(Configuration newConfig) {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onStart() {

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }
}
