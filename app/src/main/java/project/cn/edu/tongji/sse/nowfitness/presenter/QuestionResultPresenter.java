package project.cn.edu.tongji.sse.nowfitness.presenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView.QuestionResultMethod;
import project.cn.edu.tongji.sse.nowfitness.view.PlanQuestionView.QuestionResultView;

public class QuestionResultPresenter extends BasePresenter{
    private QuestionResultView questionResultView;
    private QuestionResultMethod questionResultMethod;

    public QuestionResultPresenter(QuestionResultView questionResultView,QuestionResultMethod questionResultMethod){
        this.questionResultMethod = questionResultMethod;
        this.questionResultView = questionResultView;
    }

    public void postDailyCheck(){
        subscriptions.add(apiRepository.postDailyCheck((int) UserInfoLab.get().getUserInfoModel().getId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(questionResultMethod::postSuccess,questionResultMethod::postError)
        );
    }
}
