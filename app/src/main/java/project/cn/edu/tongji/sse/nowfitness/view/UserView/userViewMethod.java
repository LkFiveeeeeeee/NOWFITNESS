package project.cn.edu.tongji.sse.nowfitness.view.UserView;

public interface userViewMethod {
    void initUserView();
    void setBMINum();
    void setLisenter();

    void jumpToStars();
    void jumpToFans();
    void jumpToMyMoments();
    void queryError(Throwable e);

    void applyForImageChange();
    void applyForBodyInfoChange();
    void applyForMomentsInfoChange();
}
