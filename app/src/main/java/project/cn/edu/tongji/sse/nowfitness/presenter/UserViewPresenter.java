package project.cn.edu.tongji.sse.nowfitness.presenter;

import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;

public class UserViewPresenter extends BasePresenter{
    private boolean ableRead = false;

    public boolean isAbleRead() {
        return ableRead;
    }

    public void setAbleRead(boolean ableRead) {
        this.ableRead = ableRead;
    }

    public boolean isAbleWrite() {
        return ableWrite;
    }

    public void setAbleWrite(boolean ableWrite) {
        this.ableWrite = ableWrite;
    }

    private boolean ableWrite = false;

    private UserViewFragment userView;
    public UserViewPresenter(UserViewFragment userView){
        this.userView = userView;
    }
    public void initView(){
        userView.initView();
    }

    public void queryMyMoments() {

    }

    public void queryMyFans(){

    }

    public void queryMyStars(){

    }
}
