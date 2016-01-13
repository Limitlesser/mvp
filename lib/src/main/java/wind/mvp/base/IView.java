package wind.mvp.base;

/**
 * Created by wind on 2016/1/12.
 */
public interface IView<P extends Presenter> {

    P getPresenter();

    <T extends Presenter> T getPresenter(Class<T> cls);

    void onCreatePresenter();

    void addPresenter(Class<? extends Presenter> cls);

}
