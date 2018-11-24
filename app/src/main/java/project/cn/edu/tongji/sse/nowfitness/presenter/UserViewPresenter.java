package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;

public class UserViewPresenter {
    private UserViewFragment userView;
    public UserViewPresenter(UserViewFragment userView){
        this.userView = userView;
    }
    public void initView(){
        userView.initView();
    }
}
