package project.cn.edu.tongji.sse.nowfitness.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.CommentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.IndividualDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.IndividualsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.RelationDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.StepDataDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.TokenDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.NetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModelList;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;

import project.cn.edu.tongji.sse.nowfitness.model.FollowingRelation;
import project.cn.edu.tongji.sse.nowfitness.model.IndiInfoModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndiRelationModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualsList;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.Response;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModel;
import project.cn.edu.tongji.sse.nowfitness.model.StepModelList;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public class APIRepositoryImpl implements APIRepository {

    private ApiInterface api = NetWorkUtils.makeRetrofit().create(ApiInterface.class);

    @Override
    public Single<ResponseModel<Token>> verifyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel responseModel = new ResponseModel();
        return api.verifyInfo(userName,passWord)
                .map(new Function<ResponseDTO<TokenDTO>, ResponseModel<Token>>() {
                    @Override
                    public ResponseModel<Token> apply(ResponseDTO<TokenDTO> tokenDTOResponseDTO) throws Exception {
                        responseModel.setError(tokenDTOResponseDTO.getError());
                        responseModel.setStatus(tokenDTOResponseDTO.getStatus());
                        Token token = new Token(tokenDTOResponseDTO.getData());
                        responseModel.setData(token);
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> applyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel responseModel = new ResponseModel();
        return api.applyRegister(userName,passWord)
                .map(new Function<ResponseDTO<TokenDTO>, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO<TokenDTO> tokenDTOResponseDTO) throws Exception {
                        responseModel.setError(tokenDTOResponseDTO.getError());
                        responseModel.setStatus(tokenDTOResponseDTO.getStatus());
                        Token token = new Token(tokenDTOResponseDTO.getData());
                        responseModel.setData(token);
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel<UserInfoModel>> queryUserInfo(String userName) {
        ResponseModel responseModel = new ResponseModel();
        return api.queryUserInfo(userName)
                .map(new Function<ResponseDTO<UserInfoDTO>, ResponseModel<UserInfoModel>>() {
                    @Override
                    public ResponseModel<UserInfoModel> apply(ResponseDTO<UserInfoDTO> userInfoDTOResponseDTO) throws Exception {
                        responseModel.setStatus(userInfoDTOResponseDTO.getStatus());
                        responseModel.setError(userInfoDTOResponseDTO.getError());
                        UserInfoModel userInfoModel = new UserInfoModel(userInfoDTOResponseDTO.getData());
                        responseModel.setData(userInfoModel);
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel<MomentsModelList>> getStarsMoments(int userId, int pageNum) {
        ResponseModel responseModel = new ResponseModel();

        return api.getStarsAllMoments(userId,pageNum)
                .map(new Function<ResponseDTO<MomentsDTO>, ResponseModel<MomentsModelList>>() {
                    @Override
                    public ResponseModel<MomentsModelList> apply(ResponseDTO<MomentsDTO> momentsDTOResponseDTO) throws Exception {
                        List<MomentsModel> modelList = new ArrayList<>();
                        MomentsModelList momentsModelList = new MomentsModelList(momentsDTOResponseDTO.getData());
                        if(momentsDTOResponseDTO.getData() != null){
                            for(MomentsDTO.ListBean bean:momentsDTOResponseDTO.getData().getList()){
                                modelList.add(new MomentsModel(bean));
                            }
                            momentsModelList.setList(modelList);
                        }
                        responseModel.setStatus(momentsDTOResponseDTO.getStatus());
                        responseModel.setError(momentsDTOResponseDTO.getError());
                        responseModel.setData(momentsModelList);
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel<MomentsModelList>> getNeighborMoments(int userId, int pageNum) {
        ResponseModel responseModel = new ResponseModel();
        return api.getNeighborMoments(userId,pageNum)
                .map(new Function<ResponseDTO<MomentsDTO>, ResponseModel<MomentsModelList>>() {
                    @Override
                    public ResponseModel<MomentsModelList> apply(ResponseDTO<MomentsDTO> momentsDTOResponseDTO) throws Exception {
                        List<MomentsModel> modelList = new ArrayList<>();
                        MomentsModelList momentsModelList = new MomentsModelList(momentsDTOResponseDTO.getData());
                        if(momentsDTOResponseDTO.getData() != null){
                            for(MomentsDTO.ListBean bean:momentsDTOResponseDTO.getData().getList()){
                                modelList.add(new MomentsModel(bean));
                            }
                            momentsModelList.setList(modelList);
                        }
                        responseModel.setStatus(momentsDTOResponseDTO.getStatus());
                        responseModel.setError(momentsDTOResponseDTO.getError());
                        responseModel.setData(momentsModelList);
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel<MomentsModelList>> getUserMoments(int userId, int pageNum) {
        ResponseModel responseModel = new ResponseModel();
        return api.getUserMoments(userId,pageNum)
                .map(new Function<ResponseDTO<MomentsDTO>, ResponseModel<MomentsModelList>>() {
                    @Override
                    public ResponseModel<MomentsModelList> apply(ResponseDTO<MomentsDTO> momentsDTOResponseDTO) throws Exception {
                        List<MomentsModel> modelList = new ArrayList<>();
                        MomentsModelList momentsModelList = new MomentsModelList(momentsDTOResponseDTO.getData());
                        if(momentsDTOResponseDTO.getData() != null){
                            for(MomentsDTO.ListBean bean:momentsDTOResponseDTO.getData().getList()){
                                modelList.add(new MomentsModel(bean));
                            }
                            momentsModelList.setList(modelList);
                        }
                        responseModel.setStatus(momentsDTOResponseDTO.getStatus());
                        responseModel.setError(momentsDTOResponseDTO.getError());
                        responseModel.setData(momentsModelList);
                        return responseModel;
                    }
                });
    }

    //omf
  @Override
    public  Single<ResponseModel<CommentsDetailModelList>> getCommentsInfo(int momentsId){
        ResponseModel responseModel = new ResponseModel();
        List<CommentsDetailModel> commentsDetailModelList = new ArrayList<>();
        return api.getAllComments(momentsId)
                .map(new Function<ResponseDTO<CommentsDTO>, ResponseModel<CommentsDetailModelList>>() {
                    @Override
                    public ResponseModel<CommentsDetailModelList> apply(ResponseDTO<CommentsDTO> commentsDTOResponseDTO) throws Exception {
                        for(CommentsDTO.CommentsListBean bean:commentsDTOResponseDTO.getData().getCommentsList()){
                            commentsDetailModelList.add(new CommentsDetailModel(bean));
                        }
                        responseModel.setError(commentsDTOResponseDTO.getError());
                        responseModel.setStatus(commentsDTOResponseDTO.getStatus());
                        CommentsDetailModelList modelList = new CommentsDetailModelList();
                        modelList.setCommentsDetailModels(commentsDetailModelList);
                        responseModel.setData(modelList);
                        return responseModel;
                    }
                });
  }
    @Override
    public Single<ResponseModel> postUserAvatar(MultipartBody.Part file, RequestBody body) {
        ResponseModel responseModel = new ResponseModel();
        return api.postUserAvatar(file,body)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        Log.d("AAAAAAAAA", "apply: ResponseModel");
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }


  //omf
  @Override
    public Single<ResponseModel> makeNewCommentInfo(CommentsDetailModel body){
      ResponseModel responseModel = new ResponseModel();
        return api.makeNewComments(body)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
  }


    @Override
    public Single<ResponseModel> makeReply(CommentsReplyModel body) {
        ResponseModel responseModel = new ResponseModel();
        return api.postReply(body)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single deleteReply(int id) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteReply(id)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> putUserInfo(UserInfoModel userInfoObject) {
        ResponseModel responseModel = new ResponseModel();
        return api.putUserInfo(userInfoObject)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> postMoment(RequestBody userId,RequestBody content,MultipartBody.Part file) {
        ResponseModel responseModel = new ResponseModel();
        return api.postMoment(userId,content,file)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> postMomentWithoutFile(RequestBody userId, RequestBody content) {
        ResponseModel responseModel = new ResponseModel();
        return api.postMomentWithoutFile(userId,content)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }


    @Override
    public Single<ResponseModel> deleteComment(int commentsId) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteComment(commentsId)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> postLikeInfo(int momentsId, int likesId) {
        ResponseModel responseModel = new ResponseModel();
        return api.postLikeInfo(momentsId,likesId)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> delLikeInfo(int momentsId, int likesId) {
        ResponseModel responseModel = new ResponseModel();
        return api.delLikeInfo(momentsId,likesId)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> postFollowInfo(int userId, int followId) {
        ResponseModel responseModel = new ResponseModel();
        return api.postFollowInfo(userId,followId)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single deleteFollowInfo(int userId, int followId) {
        ResponseModel responseModel = new ResponseModel();
        return api.deleteFollowInfo(userId,followId)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel<FollowingRelation>> getUserRelation(int userID, int anotherUserId) {
        ResponseModel responseModel = new ResponseModel();
        return api.getUserRelation(userID, anotherUserId)
                .map(new Function<ResponseDTO<RelationDTO>, ResponseModel<FollowingRelation>>() {
                    @Override
                    public ResponseModel<FollowingRelation> apply(ResponseDTO<RelationDTO> relationDTOResponseDTO) throws Exception {
                        responseModel.setError(relationDTOResponseDTO.getError());
                        responseModel.setStatus(relationDTOResponseDTO.getStatus());
                        responseModel.setData(new FollowingRelation(relationDTOResponseDTO.getData()));
                        return responseModel;
                    }
                });
    }
    @Override
    public Single<ResponseModel<IndividualsList>> getFansInfo(int userId) {
        ResponseModel responseModel = new ResponseModel();
        List<IndividualModel>  individualModels= new ArrayList<>();
        return api.getFansInfo(userId)
                .map(new Function<ResponseDTO<IndividualsDTO>, ResponseModel<IndividualsList>>() {
                    @Override
                    public ResponseModel<IndividualsList> apply(ResponseDTO<IndividualsDTO> individualsDTOResponseDTO) throws Exception {
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
                    }
                });
    }

    @Override
        public Single<ResponseModel<IndividualsList>> getFollowingInfo(int userId) {
        ResponseModel responseModel = new ResponseModel();
        List<IndividualModel>  individualModels= new ArrayList<>();
        return api.getFollowingInfo(userId)
                .map(new Function<ResponseDTO<IndividualsDTO>, ResponseModel<IndividualsList>>() {
                    @Override
                    public ResponseModel<IndividualsList> apply(ResponseDTO<IndividualsDTO> individualsDTOResponseDTO) throws Exception {
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
                    }
                });
    }

    @Override
    public Single<ResponseModel> putTodayStep(Map<String,RequestBody> bodyMap) {
        ResponseModel responseModel = new ResponseModel();
        return api.putTodayStepsData(bodyMap)
                .map(new Function<ResponseDTO, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO responseDTO) throws Exception {
                        responseModel.setStatus(responseDTO.getStatus());
                        responseModel.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel<StepModelList>> getStepsData(int userId, int days) {
        ResponseModel responseModel = new ResponseModel();
        List<StepModel> stepModels = new ArrayList<>();
        return api.getStepsData(userId,days)
                .map(new Function<ResponseDTO<StepDataDTO>, ResponseModel<StepModelList>>() {
                    @Override
                    public ResponseModel<StepModelList> apply(ResponseDTO<StepDataDTO> stepDataDTOResponseDTO) throws Exception {
                        if(stepDataDTOResponseDTO.getData().getDays() > 0){
                            for(StepDataDTO.StepsDataModelListBean bean:stepDataDTOResponseDTO.getData().getStepsDataModelList()){
                                stepModels.add(new StepModel(bean));
                            }
                        }
                        Collections.reverse(stepModels);
                        StepModelList stepModelList = new StepModelList();
                        stepModelList.setStepModels(stepModels);
                        stepModelList.setDays(stepDataDTOResponseDTO.getData().getDays());
                        responseModel.setStatus(stepDataDTOResponseDTO.getStatus());
                        responseModel.setError(stepDataDTOResponseDTO.getError());
                        responseModel.setData(stepModelList);
                        return responseModel;
                    }
                });
    }
}
