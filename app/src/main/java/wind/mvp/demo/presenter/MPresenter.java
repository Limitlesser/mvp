package wind.mvp.demo.presenter;

import wind.mvp.base.IView;
import wind.mvp.base.Presenter;
import wind.mvp.demo.model.MModel;

/**
 * Created by wind on 2016/1/13.
 */
public class MPresenter extends Presenter<MPresenter.MView, MModel> {


    public interface MView extends IView<MPresenter> {
        void onGetHello(String hello);
    }

    public void getHelloFromPresenter() {
        String hello = getModel().getHello();
        if (hasView()) {
            getView().onGetHello(hello);
        }
    }

}
