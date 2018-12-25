package project.cn.edu.tongji.sse.nowfitness.view.LeftView;

import project.cn.edu.tongji.sse.nowfitness.model.BookResponseModel;

/**
 * Created by a on 2018/12/25.
 */

public interface BookMethod {
    void queryError(Throwable e);
    void querySuccess(BookResponseModel bookResponseModel);
}
