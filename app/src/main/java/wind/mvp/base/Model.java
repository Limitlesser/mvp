package wind.mvp.base;

/**
 * Created by wind on 2016/1/12.
 */
public class Model<P extends Presenter> implements IModel<P> {

    protected MVP<? extends Model, ? extends IView, P> mMVP;

    public Model(MVP<? extends Model, ? extends IView, P> mvp) {
        mMVP = mvp;
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
    public MVP getMVP() {
        return mMVP;
    }
}
