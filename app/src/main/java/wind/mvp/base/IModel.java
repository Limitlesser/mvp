package wind.mvp.base;

/**
 * Created by wind on 2016/1/12.
 */
public interface IModel<P extends Presenter> {

    P getPresenter();

    <T extends Presenter> T getPresenter(Class<T> cls);

    MVP getMVP();

}
