package project.cn.edu.tongji.sse.nowfitness.data;



import java.util.Map;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public interface APIRepository {

    Single verifyInfo(RequestBody userName, RequestBody passWord);

    Single applyInfo(RequestBody userName,RequestBody passWord);

    Single queryUserInfo(String userName);

    Single postUserAvatar(MultipartBody.Part file,RequestBody body);

    Single getStarsMoments(int userId, int pageNum);

    Single getNeighborMoments(int userId, int pageNum);

    Single getUserMoments(int userId, int pageNum);

    //omf
    Single getCommentsInfo(int momentsId);
    //omf
    Single makeNewCommentInfo(CommentsDetailModel body);

    Single makeReply(CommentsReplyModel body);

    Single putUserInfo(UserInfoModel userInfoObject);

    Single postMoment(RequestBody userId,RequestBody content,MultipartBody.Part file);

    Single postMomentWithoutFile(RequestBody userId,RequestBody content);

    Single deleteComment(int commentsId);

    Single postLikeInfo(int momentsId,int likesId);

    Single delLikeInfo(int momentsId,int likesId);

    Single postFollowInfo(int userId,int followId);

    Single deleteFollowInfo(int userId,int followId);

    Single deleteReply(int id);

    Single getFansInfo(int userId);

    Single getFollowingInfo(int userId);

    Single putTodayStep(Map<String,RequestBody> bodyMap);

    Single getStepsData(int userId,int days);


    Single postDailyCheck(int userId);

    Single changePassword(RequestBody userId,RequestBody password);

    Single getUserRelation(int userID, int anotherUserId);

}
