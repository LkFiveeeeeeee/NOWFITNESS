package project.cn.edu.tongji.sse.nowfitness.presenter;

import org.greenrobot.greendao.rx.RxDao;

import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.UserInfoModelDao;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewMethod;

public class UserViewPresenter extends BasePresenter{
    private boolean ableRead = false;
    private boolean ableWrite = false;
    private UserViewFragment userView;
    private UserViewMethod userViewMethod;
    private UserInfoModel userInfoModel;
    private UserInfoModelDao userInfoModelDao;

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

    public UserViewPresenter(UserViewFragment userView, UserViewMethod userViewMethod){
        this.userView = userView;
        this.userViewMethod = userViewMethod;
        this.userInfoModel = UserInfoLab.get().getUserInfoModel();
        this.userInfoModelDao = DaoManager.getDaoInstance().
                getDaoSession().getUserInfoModelDao();
    }
    public void initView(){
        userView.initView();
    }
    public void setWeight(String weight){
        if(weight.equals("")){
            userInfoModel.setWeight(0);
        }else {
            userInfoModel.setWeight(Double.valueOf(weight));
        }
        userInfoModelDao.insertOrReplace(userInfoModel);
        userViewMethod.setBMINum();
    }
    public void setHeight(String height){
        if (height.equals(""))
        {
            userInfoModel.setHeight(0);
        }else{
            userInfoModel.setHeight(Double.valueOf(height));
        }
        userInfoModelDao.insertOrReplace(userInfoModel);
        userViewMethod.setBMINum();
    }

    public double getBMINum(){
        if(userInfoModel.getWeight() == 0 || userInfoModel.getHeight() == 0){
            return 0.0;
        }
        return userInfoModel.getWeight() / (userInfoModel.getHeight()/100)
                /(userInfoModel.getHeight()/100);
    }

    public void setAvatar(String imageUrl){
        userInfoModel.setPictureUrl(imageUrl);
        userInfoModelDao.insertOrReplace(userInfoModel);
    }

    public void queryMyMoments() {

    }

    public void queryMyFans(){

    }

    public void queryMyStars(){

    }
}
