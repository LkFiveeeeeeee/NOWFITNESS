package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsMethod;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsView;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsPresenter extends BaseMomentsPresenter{
    public static final int RESULT_CODE = 1001;
    private MomentsView momentsView;

    public void initView(){
        momentsView.initView();
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
    public void queryForNearByInfo(int userId,int pageNum){
        subscriptions.add(apiRepository.getNeighborMoments(userId,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(momentsMethod::querySuccess,momentsMethod::queryError)
        );
    }

    public void addMomentsList(List<MomentsModel> momentsModelList){
        for(MomentsModel e:momentsModelList) {
            pMomentsLab.add(e);
        }
        momentsRecyclerAdapter.resetMomentsModelsList(pMomentsLab);
        momentsRecyclerAdapter.notifyDataSetChanged();
    }

    public String getMomentsType(){
        return momentsView.getType();
    }

    @Override
    public void jumpToMomentsDetail(MomentsModel momentsModel,int position){
        Intent intent = new Intent();
        intent.putExtra("moments",momentsModel);
        intent.putExtra("position",position);
        intent.setClass(momentsView.getActivity(), MomentsDetailView.class);
        momentsView.startActivityForResult(intent,RESULT_CODE);
    }
    @Override
    public void shareToQzone(String title,String summary,String contentUrl,String imageUrl){
        momentsView.shareToQZone(BaseMomentsPresenter.setShareContent(title,summary,contentUrl,imageUrl));
    }

}
