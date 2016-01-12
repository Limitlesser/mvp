package wind.mvp.base;

/**
 * Created by wind on 2016/1/12.
 */
public interface IView<P extends Presenter> {

    P getPresenter();

    <T extends Presenter> T getPresenter(Class<T> cls);

    void onCreateView();

    void onCreatePresenter();

    void addPresenter(Class<? extends Presenter> cls);

    void addView(Class<? extends IView> cls, IView<P> view);

    MVP getMVP();

}
