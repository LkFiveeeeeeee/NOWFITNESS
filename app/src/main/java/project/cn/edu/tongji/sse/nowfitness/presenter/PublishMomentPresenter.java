package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.net.Uri;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.publishMomentView.PublishMomentMethod;
import project.cn.edu.tongji.sse.nowfitness.view.publishMomentView.PublishMomentView;

import static project.cn.edu.tongji.sse.nowfitness.view.NOWFITNESSApplication.getContext;

public class PublishMomentPresenter extends BasePresenter {
    private PublishMomentView publishMomentView;
    private PublishMomentMethod publishMomentMethod;

    public PublishMomentPresenter(PublishMomentView publishMomentView,PublishMomentMethod publishMomentMethod){
        this.publishMomentView = publishMomentView;
        this.publishMomentMethod = publishMomentMethod;
    }

    public void postMoment(String content,Uri uri){
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(userInfoModel.getId()));
        RequestBody requestContent = RequestBody.create(MediaType.parse("text/plain"),content);

        if(uri != null){
            String url = FileHelper.getFilePath(getContext(),uri);
            assert url != null;
            File file = new File(url);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestFile);
            postMomentWithFile(userId,requestContent,part);
        }else{
            postMomentWithoutFile(userId,requestContent);
        }

    }

    private void postMomentWithFile(RequestBody userId, RequestBody requestContent, MultipartBody.Part part){
        subscriptions.add(apiRepository.postMoment(userId,requestContent,part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(publishMomentMethod::postSuccess,publishMomentMethod::postError));
    }

    private void postMomentWithoutFile(RequestBody userId, RequestBody requestContent){
        subscriptions.add(apiRepository.postMomentWithoutFile(userId,requestContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(publishMomentMethod::postSuccess,publishMomentMethod::postError));
    }
}
