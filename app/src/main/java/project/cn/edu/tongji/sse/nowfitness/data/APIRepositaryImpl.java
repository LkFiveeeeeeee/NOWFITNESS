package project.cn.edu.tongji.sse.nowfitness.data;

import android.util.Log;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import project.cn.edu.tongji.sse.nowfitness.data.network.DTO.LoginDTO;
import project.cn.edu.tongji.sse.nowfitness.data.network.NetWorkUtils;
import project.cn.edu.tongji.sse.nowfitness.model.LoginModel;

public class APIRepositaryImpl implements APIRepositary {

    ApiInterface api = NetWorkUtils.makeRetrofit().create(ApiInterface.class);

    @Override
    public Single<LoginModel> vertifyInfo(String userName, String passWord) {
        return api.vertifyInfo(userName,passWord)
                .map(new Function<LoginDTO, LoginModel>() {
                    @Override
                    public LoginModel apply(LoginDTO loginDTO) throws Exception {
                        return new LoginModel(loginDTO.getResult());
                    }
                });
    }

    @Override
    public Single<LoginModel> applyInfo(String userName, String passWord) {
        return api.applyRegister(userName,passWord)
                .map(new Function<LoginDTO, LoginModel>() {
                    @Override
                    public LoginModel apply(LoginDTO loginDTO) throws Exception {
                        return new LoginModel(loginDTO.getResult());
                    }
                });
    }
}
