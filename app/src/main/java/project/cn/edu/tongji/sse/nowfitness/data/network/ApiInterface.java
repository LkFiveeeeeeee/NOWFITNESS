package project.cn.edu.tongji.sse.nowfitness.data.network;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.BookDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.CommentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.TokenDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //TODO API接口函数声明


    /**
     * POST 登录请求
     * @param userName 用户名
     * @param password 密码
     * @return ResponseDTO ( T data is defined to be TokenDTO)
     */
    @POST("user/login")
    @Multipart
    Single<ResponseDTO<TokenDTO>> verifyInfo(@Part("userName") RequestBody userName, @Part("password") RequestBody password);

    /**
     * POST 注册请求
     * @param userName 用户名
     * @param passWord 密码
     * @return ResponseDTO ( T data is defined to be TokenDTO)
     */
    @POST("user/register")
    @Multipart
    Single<ResponseDTO<TokenDTO>> applyRegister(@Part("userName") RequestBody userName, @Part("password") RequestBody passWord);

    /**
     * 获取用户信息
     * @param userName 用户名
     * @return ResponseDTO ( T data is defined to be UserInfoDTO )
     */
    @GET("user/information/{userName}")
    Single<ResponseDTO<UserInfoDTO>> queryUserInfo(@Path("userName") String userName);


    /**
     * POST 用户头像
     * @param file 照片文件
     * @param id 用户id
     * @return ResponseDTO without
     */
    @POST("user/uploadPhoto")
    @Multipart
    Single<ResponseDTO> postUserAvatar(@Part MultipartBody.Part file,@Part("id") RequestBody id);

    /**
     * POST 打卡信息
     * @param userId 用户id
     * @return ResponseDTO without data
     */
    @POST("dailycheck/user/{userId}")
    Single<ResponseDTO> postDailyCheck(@Path("userId") int userId);

    /**
     * GET 关注的人的动态
     * @param userID 用户id
     * @param pageNum 页码信息
     * @return ResponseDTO ( T data is defined to be MomentsDTO )
     */
    @GET("user/{userId}/following/moments/{pageNum}")
    Single<ResponseDTO<MomentsDTO>> getStarsAllMoments(@Path("userId") int userID,@Path("pageNum") int pageNum);

    /**
     * GET 获得周围的人的动态
     * @param userID 用户id
     * @param pageNum 页码信息
     * @return ResponseDTO ( T data is defined to be MomentsDTO )
     */
    @GET("user/{userId}/nearBy/moments/{pageNum}")
    Single<ResponseDTO<MomentsDTO>> getNeighborMoments(@Path("userId") int userID, @Path("pageNum") int pageNum);


    /**
     * GET 某个特定用户的动态
     * @param userID 用户id
     * @param pageNum 页码信息
     * @return ResponseDTO ( T data is defined to be MomentsDTO )
     */
    @GET("user/{userId}/moments/{pageNum}")
    Single<ResponseDTO<MomentsDTO>> getUserMoments(@Path("userId") int userID,@Path("pageNum") int pageNum);


    /**
     * GET 某条动态的评论信息
     * @param momentsId 动态Id
     * @return ResponseDTO ( T data is defined to be CommentsDTO )
     */
    //omf
    @GET("moments/{momentsId}/comments")
    Single<ResponseDTO<CommentsDTO>> getAllComments(@Path("momentsId") int momentsId);

    /**
     * POST 针对某条动态的评论
     * @param commentsBody 包含类似如此的信息 ===》  {"momentsId":9,"content":"fjooowwoof","commentUserId":3}
     * @return ResponseDTO without data
     */
    //omf
    @POST("comment")
    @Headers({"Content-Type:application/json"})
    Single<ResponseDTO> makeNewComments(@Body CommentsDetailModel commentsBody);


    /**
     * POST 用户对于某一条评论下的回复
     * @param commentsBody which includes json like this
     * {           "commentId": 3,
     *             "fromUserId": 5,
     *             "toUserId": 4,
     *             "replyType": "reply",
     *             "content": "hafgdfsghadfasfdwwwwwwa"}
     * @return ResponseDTO without data
     */
    @POST("reply")
    @Headers({"Content-Type:application/json"})
    Single<ResponseDTO> postReply(@Body CommentsReplyModel commentsBody);

    @DELETE("reply/{id}")
    Single<ResponseDTO> deleteReply(@Path("id") int id);



    @POST("moments")
    @Multipart
    Single<ResponseDTO> postMoment(@Part("userId") RequestBody userId,@Part("content") RequestBody content,@Part MultipartBody.Part file);

    @POST("moments")
    @Multipart
    Single<ResponseDTO> postMomentWithoutFile(@Part("userId") RequestBody userId,@Part("content") RequestBody content);

    /**
     * POST 对于某一条动态的点赞信息
     * @param momentsId 动态id
     * @param likesId 用户id
     * @return ResponseDTO without data
     */
    @POST("moments/{momentsId}/likes/{likesId}")
    Single<ResponseDTO> postLikeInfo(@Path("momentsId") int momentsId,@Path("likesId") int likesId);

    /**
     * DELETE 对于某一条动态的点赞信息
     * @param momentsId  动态id
     * @param likesId  用户id
     * @return ResponseDTO without data
     */
    @DELETE("moments/{momentsId}/likes/{likesId}")
    Single<ResponseDTO> delLikeInfo(@Path("momentsId") int momentsId,@Path("likesId") int likesId);


    /**
     * DELETE 评论信息
     * @param commentsId  所要删除评论的id
     * @return ResponseDTO without data
     */
    @DELETE ("comments/{commentsId}")
    Single<ResponseDTO> deleteComment(@Path("commentsId") int commentsId);


    /**
     * POST 用户关注信息
     * @param userId  用户id
     * @param followId 关注者id
     * @return ResponseDTO without data
     */
    @POST("user/{userId}/following/{followId}")
    Single<ResponseDTO> postFollowInfo(@Path("userId") int userId,@Path("followId") int followId);


    /**
     * DELETE 关注信息
     * @param userId 用户id
     * @param followId 关注者id
     * @return ResponseDTO without data
     */
    @DELETE("user/{userId}/following/{followId}")
    Single<ResponseDTO> deleteFollowInfo(@Path("userId") int userId, @Path("followId") int followId);


    /**
     * PUT 今日步数
     * @param bodyMap 请求体  ===》{id -> 用户id , step -> 步数 , calories -> 卡路里}
     * @return ResponseDTO without data
     */
    @PUT("user/stepsData")
    @Multipart
    Single<ResponseDTO> putTodayStepsData(@PartMap Map<String,RequestBody> bodyMap);


    @PUT("user/update")
    @Headers({"Content-Type:application/json"})
    Single<ResponseDTO> putUserInfo(@Body UserInfoModel jsonObject);

    @GET("search")
    Single<BookDTO> getBookInfo(@Query("tag")String tag,@Query("start")int start,@Query("count")int count);
}
