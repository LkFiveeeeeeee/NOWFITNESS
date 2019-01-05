package project.cn.edu.tongji.sse.nowfitness.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.dto.CommentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.dto.IndividualDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.dto.MomentsListDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.dto.StepDataListDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.NetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModelList;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.FollowingRelation;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualsList;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.SaltModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public class APIRepositoryImpl
        implements APIRepository {

    private final ApiInterface api = NetWorkUtils.makeRetrofit().create(ApiInterface.class);

    @Override
    public Single<ResponseModel<Token>> verifyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel<Token> responseModel = new ResponseModel<>();
        return api.verifyInfo(userName,passWord)
                .map(tokenDTOResponseDTO -> {
                    responseModel.setError(tokenDTOResponseDTO.getError());
                    responseModel.setStatus(tokenDTOResponseDTO.getStatus());
                    Token token = new Token(tokenDTOResponseDTO.getData());
                    responseModel.setData(token);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<Token>> applyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel<Token> responseModel = new ResponseModel<>();
        return api.applyRegister(userName,passWord)
                .map(tokenDTOResponseDTO -> {
                    responseModel.setError(tokenDTOResponseDTO.getError());
                    responseModel.setStatus(tokenDTOResponseDTO.getStatus());
                    Token token = new Token(tokenDTOResponseDTO.getData());
                    responseModel.setData(token);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<UserInfoModel>> queryUserInfo(String userName) {
        ResponseModel<UserInfoModel> responseModel = new ResponseModel<>();
        return api.queryUserInfo(userName)
                .map(userInfoDTOResponseDTO -> {
                    responseModel.setStatus(userInfoDTOResponseDTO.getStatus());
                    responseModel.setError(userInfoDTOResponseDTO.getError());
                    UserInfoModel userInfoModel = new UserInfoModel(userInfoDTOResponseDTO.getData());
                    responseModel.setData(userInfoModel);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<MomentsModelList>> getStarsMoments(int userId, int pageNum) {
        ResponseModel<MomentsModelList> responseModel = new ResponseModel<>();

        return api.getStarsAllMoments(userId,pageNum)
                .map(momentsDTOResponseDTO -> {
                    List<MomentsModel> modelList = new ArrayList<>();
                    MomentsModelList momentsModelList = new MomentsModelList(momentsDTOResponseDTO.getData());
                    if(momentsDTOResponseDTO.getData() != null){
                        for(MomentsListDTO bean:momentsDTOResponseDTO.getData().getList()){
                            modelList.add(new MomentsModel(bean));
                        }
                        momentsModelList.setList(modelList);
                    }
                    responseModel.setStatus(momentsDTOResponseDTO.getStatus());
                    responseModel.setError(momentsDTOResponseDTO.getError());
                    responseModel.setData(momentsModelList);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<MomentsModelList>> getNeighborMoments(int userId, int pageNum) {
        ResponseModel<MomentsModelList> responseModel = new ResponseModel<>();
        return api.getNeighborMoments(userId,pageNum)
                .map(momentsDTOResponseDTO -> {
                    List<MomentsModel> modelList = new ArrayList<>();
                    MomentsModelList momentsModelList = new MomentsModelList(momentsDTOResponseDTO.getData());
                    if(momentsDTOResponseDTO.getData() != null){
                        for(MomentsListDTO bean:momentsDTOResponseDTO.getData().getList()){
                            modelList.add(new MomentsModel(bean));
                        }
                        momentsModelList.setList(modelList);
                    }
                    responseModel.setStatus(momentsDTOResponseDTO.getStatus());
                    responseModel.setError(momentsDTOResponseDTO.getError());
                    responseModel.setData(momentsModelList);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<MomentsModelList>> getUserMoments(int userId, int pageNum) {
        ResponseModel<MomentsModelList> responseModel = new ResponseModel<>();
        return api.getUserMoments(userId,pageNum)
                .map(momentsDTOResponseDTO -> {
                    List<MomentsModel> modelList = new ArrayList<>();
                    MomentsModelList momentsModelList = new MomentsModelList(momentsDTOResponseDTO.getData());
                    if(momentsDTOResponseDTO.getData() != null){
                        for(MomentsListDTO bean:momentsDTOResponseDTO.getData().getList()){
                            modelList.add(new MomentsModel(bean));
                        }
                        momentsModelList.setList(modelList);
                    }
                    responseModel.setStatus(momentsDTOResponseDTO.getStatus());
                    responseModel.setError(momentsDTOResponseDTO.getError());
                    responseModel.setData(momentsModelList);
                    return responseModel;
                });
    }

    //omf
  @Override
    public  Single<ResponseModel<CommentsDetailModelList>> getCommentsInfo(int momentsId){
      ResponseModel<CommentsDetailModelList> responseModel = new ResponseModel<>();
        List<CommentsDetailModel> commentsDetailModelList = new ArrayList<>();
        return api.getAllComments(momentsId)
                .map(commentsDTOResponseDTO -> {
                    for(CommentsDTO.CommentsListBean bean:commentsDTOResponseDTO.getData().getCommentsList()){
                        commentsDetailModelList.add(new CommentsDetailModel(bean));
                    }
                    responseModel.setError(commentsDTOResponseDTO.getError());
                    responseModel.setStatus(commentsDTOResponseDTO.getStatus());
                    CommentsDetailModelList modelList = new CommentsDetailModelList();
                    modelList.setCommentsDetailModels(commentsDetailModelList);
                    responseModel.setData(modelList);
                    return responseModel;
                });
  }
    @Override
    public Single<ResponseModel> postUserAvatar(MultipartBody.Part file, RequestBody body) {
        ResponseModel responseModel = new ResponseModel();
        return api.postUserAvatar(file,body)
                .map(responseDTO -> {
                          responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }


  //omf
  @Override
    public Single<ResponseModel> makeNewCommentInfo(CommentsDetailModel body){
      ResponseModel responseModel = new ResponseModel();
        return api.makeNewComments(body)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
  }


    @Override
    public Single<ResponseModel> makeReply(CommentsReplyModel body) {
        ResponseModel responseModel = new ResponseModel();
        return api.postReply(body)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single deleteReply(int id) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteReply(id)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> putUserInfo(UserInfoModel userInfoObject) {
        ResponseModel responseModel = new ResponseModel();
        return api.putUserInfo(userInfoObject)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> postMoment(RequestBody userId,RequestBody content,MultipartBody.Part file) {
        ResponseModel responseModel = new ResponseModel();
        return api.postMoment(userId,content,file)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> postMomentWithoutFile(RequestBody userId, RequestBody content) {
        ResponseModel responseModel = new ResponseModel();
        return api.postMomentWithoutFile(userId,content)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }


    @Override
    public Single<ResponseModel> deleteComment(int commentsId) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteComment(commentsId)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> postLikeInfo(int momentsId, int likesId) {
        ResponseModel responseModel = new ResponseModel();
        return api.postLikeInfo(momentsId,likesId)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> delLikeInfo(int momentsId, int likesId) {
        ResponseModel responseModel = new ResponseModel();
        return api.delLikeInfo(momentsId,likesId)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> postFollowInfo(int userId, int followId) {
        ResponseModel responseModel = new ResponseModel();
        return api.postFollowInfo(userId,followId)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single deleteFollowInfo(int userId, int followId) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteFollowInfo(userId,followId)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<FollowingRelation>> getUserRelation(int userID, int anotherUserId) {
        ResponseModel<FollowingRelation> responseModel = new ResponseModel<>();
        return api.getUserRelation(userID, anotherUserId)
                .map(relationDTOResponseDTO -> {
                    responseModel.setError(relationDTOResponseDTO.getError());
                    responseModel.setStatus(relationDTOResponseDTO.getStatus());
                    responseModel.setData(new FollowingRelation(relationDTOResponseDTO.getData()));
                    return responseModel;
                });
    }
    @Override
    public Single<ResponseModel<IndividualsList>> getFansInfo(int userId) {
        ResponseModel<IndividualsList> responseModel = new ResponseModel<>();
        List<IndividualModel>  individualModels= new ArrayList<>();
        return api.getFansInfo(userId)
                .map(individualsDTOResponseDTO -> {
                    if(individualsDTOResponseDTO.getData().getTotalNum() != 0){
                        for(IndividualDTO individualDTO:individualsDTOResponseDTO.getData().getUsers()){
                            individualModels.add(new IndividualModel(individualDTO));
                        }
                    }
                    IndividualsList list = new IndividualsList();
                    list.setTotalNum(individualsDTOResponseDTO.getData().getTotalNum());
                    list.setIndividualModels(individualModels);
                    responseModel.setStatus(individualsDTOResponseDTO.getStatus());
                    responseModel.setError(individualsDTOResponseDTO.getError());
                    responseModel.setData(list);
                    return responseModel;
                });
    }

    @Override
        public Single<ResponseModel<IndividualsList>> getFollowingInfo(int userId) {
        ResponseModel<IndividualsList> responseModel = new ResponseModel<>();
        List<IndividualModel>  individualModels= new ArrayList<>();
        return api.getFollowingInfo(userId)
                .map(individualsDTOResponseDTO -> {
                    if(individualsDTOResponseDTO.getData().getTotalNum() != 0){
                        for(IndividualDTO individualDTO:individualsDTOResponseDTO.getData().getUsers()){
                            individualModels.add(new IndividualModel(individualDTO));
                        }
                    }
                    IndividualsList list = new IndividualsList();
                    list.setTotalNum(individualsDTOResponseDTO.getData().getTotalNum());
                    list.setIndividualModels(individualModels);
                    responseModel.setStatus(individualsDTOResponseDTO.getStatus());
                    responseModel.setError(individualsDTOResponseDTO.getError());
                    responseModel.setData(list);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> putTodayStep(Map<String,RequestBody> bodyMap) {
        ResponseModel responseModel = new ResponseModel();
        return api.putTodayStepsData(bodyMap)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<StepModelList>> getStepsData(int userId, int days) {
        ResponseModel<StepModelList> responseModel = new ResponseModel<>();
        List<StepModel> stepModels = new ArrayList<>();
        return api.getStepsData(userId,days)
                .map(stepDataDTOResponseDTO -> {
                    if(stepDataDTOResponseDTO.getData().getDays() > 0){
                        for(StepDataListDTO
                                bean:stepDataDTOResponseDTO.getData().getStepsDataModelList()){
                            stepModels.add(new StepModel(bean));
                        }
                    }
                    StepModelList stepModelList = new StepModelList();
                    stepModelList.setStepModels(stepModels);
                    stepModelList.setDays(stepDataDTOResponseDTO.getData().getDays());
                    responseModel.setStatus(stepDataDTOResponseDTO.getStatus());
                    responseModel.setError(stepDataDTOResponseDTO.getError());
                    responseModel.setData(stepModelList);
                    return responseModel;
                });
    }


    @Override
    public Single<ResponseModel> postDailyCheck(int userId) {
        ResponseModel responseModel = new ResponseModel();
        return api.postDailyCheck(userId)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel<SaltModel>> changePassword(RequestBody userId, RequestBody password) {
        ResponseModel<SaltModel> responseModel = new ResponseModel<>();
        return api.changePassword(userId,password)
                .map(saltDTOResponseDTO -> {
                    responseModel.setStatus(saltDTOResponseDTO.getStatus());
                    responseModel.setError(saltDTOResponseDTO.getError());
                    SaltModel saltModel = new SaltModel();
                    saltModel.setSalt(saltDTOResponseDTO.getData().getSalt());
                    responseModel.setData(saltModel);
                    return responseModel;
                });
    }

    @Override
    public Single<ResponseModel> deleteMoment(int momentID) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteMoment(momentID)
                .map(responseDTO -> {
                    responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;
                });
    }
}
