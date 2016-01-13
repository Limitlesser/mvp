package wind.mvp.base;

/**
 * Created by wind on 2016/1/12.
 */
public interface IPresenter<V extends IView, M extends IModel> {

    V getView();

    <T extends IView> T getView(Class<T> cls);

    boolean hasView();

    <T extends IView> boolean hasView(Class<T> cls);

    M getModel();

    <T extends IModel> T getModel(Class<T> cls);

    void addModel(Class<? extends IModel> cls);

    MVP getMVP();

}
