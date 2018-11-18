package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.RegisterView;

public class RegisterPresenter {
    private RegisterView registerView;
    public RegisterPresenter(RegisterView registerView){
        this.registerView = registerView;
    }

    public void showAnimate(){
        registerView.showEnterAnimaton();
    }

    public void initView(){
        registerView.initView();
    }
}
