package wind.mvp.demo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import wind.mvp.R;
import wind.mvp.base.MVPActivityView;
import wind.mvp.demo.model.MModel;
import wind.mvp.demo.presenter.MPresenter;

/**
 * Created by pc on 2016/1/13.
 */
public class MVPActivity extends MVPActivityView<MModel, MPresenter.MView, MPresenter> implements MPresenter.MView {


    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getPresenter().getHelloFromPresenter();
    }

    private void initView() {
        mTextView = new TextView(this);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(22);
        mTextView.setTextColor(Color.BLACK);
        setContentView(mTextView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void onGetHello(String hello) {
        mTextView.setText(hello + "  from presenter");
    }
}
