package project.cn.edu.tongji.sse.nowfitness.data;

import io.reactivex.Single;

/**
 * Created by a on 2018/12/25.
 */

public interface DouBanAPIRepository {
    //omf
    Single getBookInfo(String tag, int start, int count);
}
