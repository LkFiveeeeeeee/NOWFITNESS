package project.cn.edu.tongji.sse.nowfitness.data.network;

import io.reactivex.Single;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.CommentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.RepliesDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    //TODO API接口函数声明

    @GET("user/login")
    Single<LoginDTO> vertifyInfo(@Query("userName") String userName, @Query("password") String passWord);
    @GET("user/register")
    Single<LoginDTO> applyRegister(@Query("userName") String userName, @Query("password") String passWord);
    @GET("user/information")
    Single<UserInfoDTO> queryUserInfo(@Query("userName") String userName,@Query("password") String passWord);
    @GET("moments/getFollowing")
    Single<MomentsDTO> getStarsAllMoments(@Query("userId") int userID);

    //omf
    @GET("comment")
    Single<CommentsDTO> getAllComments(@Query("momentsId")int momentsId);
    //omf
    @GET("/comment/make")
    Single<RepliesDTO> makeNewComments(@Query("commentsModel")CommentsDetailModel commentsDetailModel);


    @POST("user/information")
    Single<LoginDTO> postUserAvatar(@Part("photo")RequestBody photo);
}
