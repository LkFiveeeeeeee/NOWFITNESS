package project.cn.edu.tongji.sse.nowfitness.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.CommentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.RepliesDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.TokenDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.NetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.Token;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public class APIRepositaryImpl implements APIRepositary {

    ApiInterface api = NetWorkUtils.makeRetrofit().create(ApiInterface.class);

    @Override
    public Single<ResponseModel<Token>> vertifyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel responseModel = new ResponseModel();
        return api.vertifyInfo(userName,passWord)
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
    public Single<ResponseModel<MomentsModelList>> getStarsInfo(int userId, int pageNum) {
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

    //omf
  @Override
    public  Single<List<CommentsDetailModel>> getCommentsInfo(int momentsId){
        List<CommentsDetailModel> commentsDetailModelList = new ArrayList<>();
        return api.getAllComments(momentsId)
                .map(new Function<CommentsDTO, List<CommentsDetailModel>>() {
                    @Override
                    public List<CommentsDetailModel> apply(CommentsDTO commentsDTO) throws Exception {
                        for(CommentsDTO.CommentsListBean bean:commentsDTO.getCommentsList()){
                            commentsDetailModelList.add(new CommentsDetailModel(bean));
                        }
                        return commentsDetailModelList;
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
    public Single<ResponseModel> makeNewCommentInfo(RequestBody body){
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
    public Single<ResponseModel> makeReply(RequestBody body) {
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
}
