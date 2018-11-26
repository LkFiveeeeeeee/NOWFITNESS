package project.cn.edu.tongji.sse.nowfitness.model;

public class UserInfoLab  {
    private static UserInfoLab userInfoLab;

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }

    public void setUserInfoModel(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    private UserInfoModel userInfoModel;
    public static UserInfoLab get(){
        if(userInfoLab == null){
            userInfoLab = new UserInfoLab();
        }
        return userInfoLab;
    }
}
