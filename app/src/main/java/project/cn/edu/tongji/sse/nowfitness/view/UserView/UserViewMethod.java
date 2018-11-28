package project.cn.edu.tongji.sse.nowfitness.view.UserView;

import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.model.SignModel;

public interface UserViewMethod {
    void initUserView();
    void setBMINum();
    void setLisenter();

    void jumpToStars();
    void jumpToFans();
    void jumpToMyMoments();
    void queryError(Throwable e);

    void applyForImageChange(SignModel responseDTO);
    void applyForBodyInfoChange();
    void applyForMomentsInfoChange();
}
