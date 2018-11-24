package project.cn.edu.tongji.sse.nowfitness.data;

import io.reactivex.Single;
import project.cn.edu.tongji.sse.nowfitness.data.network.ApiInterface;
import retrofit2.http.GET;

public interface APIRepositary {

    Single vertifyInfo(String userName,String passWord);

    Single applyInfo(String userName,String passWord);
}
