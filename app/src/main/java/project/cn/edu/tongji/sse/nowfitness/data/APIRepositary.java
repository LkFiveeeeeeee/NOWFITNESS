package project.cn.edu.tongji.sse.nowfitness.data;

import android.net.Uri;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import retrofit2.http.GET;

public interface APIRepositary {

    Single vertifyInfo(RequestBody userName,RequestBody passWord);

    Single applyInfo(RequestBody userName,RequestBody passWord);

    Single queryUserInfo(String userName);

    Single postUserAvatar(MultipartBody.Part file,RequestBody body);

    Single getStarsInfo (int userId,int pageNum);

    //omf
    Single getCommentsInfo(int momentsId);
    //omf
    Single makeNewCommentInfo(RequestBody body);

    Single makeReply(RequestBody body);
}
