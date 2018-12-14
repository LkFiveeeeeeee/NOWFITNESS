package project.cn.edu.tongji.sse.nowfitness.data.network;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    //TODO API接口函数声明

    @POST("user/login")
    @Multipart
    Single<ResponseDTO<LoginDTO>> vertifyInfo(@Part("userName") RequestBody userName, @Part("password") RequestBody password);

    @POST("user/register")
    @Multipart
    Single<ResponseDTO<LoginDTO>> applyRegister(@Part("userName") RequestBody userName, @Part("password") RequestBody passWord);
    @GET("user/information")
    Single<ResponseDTO<UserInfoDTO>> queryUserInfo(@Query("userName") String userName,@Query("password") String passWord);
    @GET("moments/getFollowing")
    Single<ResponseDTO<MomentsDTO>> getStarsAllMoments(@Query("userId") int userID);


    @POST("user/uploadPhoto")
    @Multipart
    Single<ResponseDTO> postUserAvatar(@Part MultipartBody.Part file,@Part("id") RequestBody id);
}
