package project.cn.edu.tongji.sse.nowfitness.presenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsView;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsPresenter extends BaseMomentsPresenter{
    private MomentsView momentsView;

    public void initView(){
        momentsView.initView();
    }
    public void likeOrDislike(){

    }
    public MomentsPresenter(MomentsView momentsView, MomentsMethod momentsMethod){
        super(momentsMethod,momentsView.getActivity());
        this.momentsView = momentsView;
        pMomentsLab = new ArrayList<>();
        this.momentsMethod = momentsMethod;
    }

    public void queryForInfo(int userId,int pageNum){
        subscriptions.add(apiRepository.getStarsMoments(userId,pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(momentsMethod::querySuccess,momentsMethod::queryError)
        );

    }

    public void resetMomentsList(List<MomentsModel> momentsModelList){
        pMomentsLab = momentsModelList;
        momentsRecyclerAdapter.resetMomentsModelsList(momentsModelList);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }

    public void addMomentsList(List<MomentsModel> momentsModelList){
        for(MomentsModel e:momentsModelList)
            pMomentsLab.add(e);
        momentsRecyclerAdapter.resetMomentsModelsList(pMomentsLab);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }
}
