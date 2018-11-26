package project.cn.edu.tongji.sse.nowfitness.data;

import android.net.wifi.p2p.WifiP2pManager;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.UserInfoDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.NetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.SignModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;

public class APIRepositaryImpl implements APIRepositary {

    ApiInterface api = NetWorkUtils.makeRetrofit().create(ApiInterface.class);

    @Override
    public Single<SignModel> vertifyInfo(String userName, String passWord) {
        return api.vertifyInfo(userName,passWord)
                .map(new Function<LoginDTO, SignModel>() {
                    @Override
                    public SignModel apply(LoginDTO loginDTO) throws Exception {
                        return new SignModel(loginDTO.getResult());
                    }
                });
    }

    @Override
    public Single<SignModel> applyInfo(String userName, String passWord) {
        return api.applyRegister(userName,passWord)
                .map(new Function<LoginDTO, SignModel>() {
                    @Override
                    public SignModel apply(LoginDTO loginDTO) throws Exception {
                        return new SignModel(loginDTO.getResult());
                    }
                });
    }

    @Override
    public Single<UserInfoModel> queryUserInfo(String userName, String passWord) {
        return api.queryUserInfo(userName,passWord)
                .map(new Function<UserInfoDTO, UserInfoModel>() {
                    @Override
                    public UserInfoModel apply(UserInfoDTO userInfoDTO) throws Exception {
                        return new UserInfoModel(userInfoDTO);
                    }
                });
    }


}
