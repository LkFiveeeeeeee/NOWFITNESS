package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.LeftView.LeftFragment;

/**
 * Created by a on 2018/11/22.
 */

public class LeftFragmentPresenter extends BasePresenter{

    private LeftFragment leftFragment;

    public LeftFragmentPresenter(LeftFragment leftFragment){
        this.leftFragment = leftFragment;

    }
    public void initView(){
        leftFragment.initView();
    }
}
