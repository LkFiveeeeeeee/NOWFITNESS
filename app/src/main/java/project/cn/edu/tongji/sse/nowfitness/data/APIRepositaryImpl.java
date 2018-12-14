package project.cn.edu.tongji.sse.nowfitness.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.MomentsDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.ResponseDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.NetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public class APIRepositaryImpl implements APIRepositary {

    ApiInterface api = NetWorkUtils.makeRetrofit().create(ApiInterface.class);

    @Override
    public Single<ResponseModel> vertifyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel responseModel = new ResponseModel();
        return api.vertifyInfo(userName,passWord)
                .map(new Function<ResponseDTO<LoginDTO>, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO<LoginDTO> loginDTOResponseDTO) throws Exception {
                        responseModel.setStatus(loginDTOResponseDTO.getStatus());
                        responseModel.setError(loginDTOResponseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<ResponseModel> applyInfo(RequestBody userName, RequestBody passWord) {
        ResponseModel responseModel = new ResponseModel();
        return api.applyRegister(userName,passWord)
                .map(new Function<ResponseDTO<LoginDTO>, ResponseModel>() {
                    @Override
                    public ResponseModel apply(ResponseDTO<LoginDTO> loginDTOResponseDTO) throws Exception {
                        responseModel.setStatus(loginDTOResponseDTO.getStatus());
                        responseModel.setError(loginDTOResponseDTO.getError());
                        return responseModel;
                    }
                });
    }

    @Override
    public Single<UserInfoModel> queryUserInfo(String userName, String passWord) {
        return api.queryUserInfo(userName,passWord)
                .map(new Function<ResponseDTO<UserInfoDTO>, UserInfoModel>() {
                    @Override
                    public UserInfoModel apply(ResponseDTO<UserInfoDTO> userInfoDTOResponseDTO) throws Exception {
                        return new UserInfoModel(userInfoDTOResponseDTO.getData());
                    }
                });
    }

    @Override
    public Single<List<MomentsModel>> getStarsInfo(int userId) {
        List<MomentsModel> modelList = new ArrayList<>();
        return api.getStarsAllMoments(userId)
                .map(new Function<ResponseDTO<MomentsDTO>, List<MomentsModel>>() {
                    @Override
                    public List<MomentsModel> apply(ResponseDTO<MomentsDTO> momentsDTOResponseDTO) throws Exception {
                        for(MomentsDTO.MomentsModelsListBean bean:momentsDTOResponseDTO.getData().getMomentsModelsList()){
                            modelList.add(new MomentsModel(bean));
                        }
                        return modelList;
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
                        responseDTO.setError(responseDTO.getError());
                        return responseModel;
                    }
                });
    }


}
