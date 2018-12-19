package project.cn.edu.tongji.sse.nowfitness.data.network;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.CommentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.RepliesDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.TokenDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //TODO API接口函数声明


    /**
     * POST 登录请求
     * @param userName
     * @param password
     * @return token?
     */
    @POST("user/login")
    @Multipart
    Single<ResponseDTO<TokenDTO>> vertifyInfo(@Part("userName") RequestBody userName, @Part("password") RequestBody password);

    /**
     * POST 注册请求
     * @param userName
     * @param passWord
     * @return token?
     */
    @POST("user/register")
    @Multipart
    Single<ResponseDTO<TokenDTO>> applyRegister(@Part("userName") RequestBody userName, @Part("password") RequestBody passWord);

    /**
     * 获取用户信息
     * @param userName
     * @return
     */
    @GET("user/information/{userName}")
    Single<ResponseDTO<UserInfoDTO>> queryUserInfo(@Path("userName") String userName);


    /**
     * Post 用户头像
     * @param file
     * @param id
     * @return
     */
    @POST("user/uploadPhoto")
    @Multipart
    Single<ResponseDTO> postUserAvatar(@Part MultipartBody.Part file,@Part("id") RequestBody id);

    @GET("user/{userId}/following/moments/{pageNum}")
    Single<ResponseDTO<MomentsDTO>> getStarsAllMoments(@Path("userId") int userID,@Path("pageNum") int pageNum);

    //omf
    @GET("comment")
    Single<CommentsDTO> getAllComments(@Query("momentsId")int momentsId);
    //omf
    @POST("/comment/make")
    @Headers({"Content-Type:application/json"})
    Single<ResponseDTO> makeNewComments(@Body RequestBody commentsBody);


    /**
     * post user 对于某一条评论的user 的回复
     * @param commentsBody which includes json like this
     * {           "commentId": 3,
     *             "fromUserId": 5,
     *             "toUserId": 4,
     *             "replyType": "reply",
     *             "content": "hafgdfsghadfasfdwwwwwwa"}
     * @return
     */
    @POST("/reply")
    @Headers({"Content-Type:application/json"})
    Single<ResponseDTO> postReply(@Body RequestBody commentsBody);


    /**
     * post user发布的动态
     * @param body
     * @param file
     * @return
     */
    @POST("/moments")
    @Multipart
    Single<ResponseDTO> postMoment(@Body RequestBody body,@Part MultipartBody.Part file);


}
