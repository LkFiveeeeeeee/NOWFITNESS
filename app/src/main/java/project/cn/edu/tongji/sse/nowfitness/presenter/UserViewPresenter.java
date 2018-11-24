package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserView;

public class UserViewPresenter {
    private UserView userView;
    public UserViewPresenter(UserView userView){
        this.userView = userView;
    }
    public void initView(){
        userView.initView();
    }
}
