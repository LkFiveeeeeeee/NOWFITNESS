package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserSettingView.UserSettingMethod;
import project.cn.edu.tongji.sse.nowfitness.view.UserSettingView.UserSettingView;

public class UserSettingPresenter extends BasePresenter {
    private UserSettingView userSettingView;
    private UserSettingMethod userSettingMethod;
    public UserSettingPresenter(UserSettingView userSettingView,UserSettingMethod userSettingMethod){
        this.userSettingView = userSettingView;
        this.userSettingMethod = userSettingMethod;

    }

    public void putUserInfo(UserInfoModel userInfoModel){

      //  JSONObject object = new JSONObject();
   /*     try{
            object.put("userName",userInfoModel.getUserName());
            object.put("password",userInfoModel.getPassword());
            object.put("sex",userInfoModel.getSex());
            object.put("nickName",userInfoModel.getNickName());
            object.put("age",userInfoModel.getAge());
        }catch (JSONException e){
            e.printStackTrace();
            Log.d("PUT UserInfo", "putUserInfo: error!!" );
            return;
        }*/

        subscriptions.add(apiRepository.putUserInfo(userInfoModel)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userSettingMethod::putSuccess,userSettingMethod::putError)
        );

    }
}
