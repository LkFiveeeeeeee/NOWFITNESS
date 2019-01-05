package project.cn.edu.tongji.sse.nowfitness.presenter;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayView;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW.DisplayViewMethod;

/**
 * Created by LK on 2018/11/24.
 */

public class DisplayPresenter extends BasePresenter{
    private DisplayView displayView;
    private DisplayViewMethod displayViewMethod;

    public DisplayPresenter(DisplayView displayView, DisplayViewMethod displayViewMethod){
        this.displayView = displayView;
        this.displayViewMethod = displayViewMethod;
    }

    public void initView(){
        displayView.initView();
    }

    public void queryForFollowingOrFansInfo(int type){
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        if(type == Constant.STARS_TYPE){
            subscriptions.add(apiRepository.getFollowingInfo((int) userInfoModel.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(displayViewMethod::queryForSuccess,displayViewMethod::queryError)
            );
        }else{
            subscriptions.add(apiRepository.getFansInfo((int) userInfoModel.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(displayViewMethod::queryForSuccess,displayViewMethod::queryError)
            );
        }
    }

    public void jumpToPersonPage(int id, String userName, String nickName, String personPhoto) {
        displayView.jumpToPersonPage(id, userName, nickName, personPhoto);
    }


}
