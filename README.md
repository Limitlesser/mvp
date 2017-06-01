# mvp
一种吊炸天的MVP模式实现
##
    public class MVPActivity extends MVPActivityView<MPresenter.MView, MPresenter> implements MPresenter.MView {

        TextView mTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initView();
            getPresenter().getHelloFromPresenter();
        }

        @Override
        public void onGetHello(String hello) {
            mTextView.setText(hello + "  from presenter");
        }
    }
