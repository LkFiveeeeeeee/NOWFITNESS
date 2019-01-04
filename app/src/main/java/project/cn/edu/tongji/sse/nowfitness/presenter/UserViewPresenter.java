package project.cn.edu.tongji.sse.nowfitness.presenter;


import android.util.Log;

import java.io.File;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.DaoManager;
import project.cn.edu.tongji.sse.nowfitness.greendao.db.UserInfoModelDao;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewFragment;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.UserViewMethod;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

import static project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication.getContext;

public class UserViewPresenter extends BasePresenter{
    private UserViewFragment userView;
    private UserViewMethod userViewMethod;
    private UserInfoModel userInfoModel;
    private UserInfoModelDao userInfoModelDao;


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
        final int DIVISION = 100;
        if(userInfoModel.getWeight() >= -Constant.EPSILON && userInfoModel.getHeight() <= Constant.EPSILON){
            return 0.0;
        }
        return userInfoModel.getWeight() / (userInfoModel.getHeight()/DIVISION)
                /(userInfoModel.getHeight()/DIVISION);
    }

    public void setAvatar(String imageUrl){
        userInfoModel.setPictureUrl(imageUrl);
        userInfoModelDao.insertOrReplace(userInfoModel);
    }

    public void postAvatar(String uri, int userId){
        File imageFile = new File(uri);
        new Compressor(getContext())
                .compressToFileAsFlowable(imageFile)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(file -> {
                    File compressFile = file;
                    Log.d("compress", "accept: Ok");
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),compressFile);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file",compressFile.getName(),requestFile);
                    RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(userId));
                    subscriptions.add(apiRepository.postUserAvatar(part,requestId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(userViewMethod::applyForImageChange,userViewMethod::queryError));
                }, throwable -> {
                    throwable.printStackTrace();
                    ConstantMethod.toastShort(getContext(),throwable.getMessage());
                });
    }

}
