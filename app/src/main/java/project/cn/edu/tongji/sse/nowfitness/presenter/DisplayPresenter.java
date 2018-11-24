package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayView;

public class DisplayPresenter {
    DisplayView displayView;
    public DisplayPresenter(DisplayView displayView){
        this.displayView = displayView;
    }

    public void initView(){
        displayView.initView();
    }
}
