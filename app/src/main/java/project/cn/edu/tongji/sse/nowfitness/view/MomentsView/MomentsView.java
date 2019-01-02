package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import project.cn.edu.tongji.sse.nowfitness.R;


import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.LeftView.LeftFragment;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsView extends Fragment implements MomentsMethod,MyQzoneShare{
    private View myView;
    private String type;
    private RecyclerView momentsRecyclerView;
    private MomentsPresenter momentsPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SmartRefreshLayout refreshLayout;
    private final int LOAD = 1;
    private final int REFRESH = 2;

    private Tencent mTencent;

    public static MomentsView newInstance(String type){
        Bundle args = new Bundle();
        args.putString("TYPE",type);
        MomentsView fragment = new MomentsView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=(String )getArguments().getString("TYPE");
        momentsPresenter = new MomentsPresenter(this,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.moments_page,container,false);
        if(type.equals(LeftFragment.TAB_TYPE_1)) {
            momentsPresenter.queryForInfo((int) UserInfoLab.get().getUserInfoModel().getId(), 1);
        }
        else{
            momentsPresenter.queryForNearByInfo((int) UserInfoLab.get().getUserInfoModel().getId(),1);
        }
        mTencent = Tencent.createInstance(Constant.APP_ID, this.getActivity().getApplicationContext());
        momentsPresenter.initView();
        return myView;
    }

    public void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.news_swipe_refresh);
        momentsRecyclerView = (RecyclerView) myView.findViewById(R.id.news_recyclerView);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(myView.getContext(), LinearLayout.VERTICAL,false));
        momentsPresenter.setMomentsRecyclerView(momentsRecyclerView);
        momentsPresenter.setAdapter();
        refreshLayout = (SmartRefreshLayout)myView.findViewById(R.id.moments_refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(this.getActivity()));
        initEvent();
    }

    public void initEvent(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryDifferentMoments(REFRESH);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                queryDifferentMoments(LOAD);
            }
        });
    }

    @Override
    public void querySuccess(ResponseModel<MomentsModelList> models) {
        if(models.getStatus() >= Constant.NET_CODE_200 || models.getStatus() < Constant.NET_CODE_300){
           if(models.getData().getTotal()==0) {
               momentsPresenter.setAdapterStates(MomentsRecyclerAdapter.NO_CONTENT);
           }
           else if(models.getData().getSize()==0) {
               refreshLayout.finishLoadMoreWithNoMoreData();
           }else {
               momentsPresenter.setPages(models.getData().getPages());
               momentsPresenter.setPageNum(models.getData().getPageNum());
               momentsPresenter.setTotalMoments(models.getData().getTotal());
               if(models.getData().getPageNum()==1) {
                   momentsPresenter.resetMomentsList(models.getData().getList());
                   momentsPresenter.setAdapterStates(MomentsRecyclerAdapter.NORMAL);
                   refreshLayout.setNoMoreData(false);
               }
               else {
                   momentsPresenter.addMomentsList(models.getData().getList());
                   refreshLayout.finishLoadMore();
               }

           }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void queryInfoSuccess(ResponseModel<UserInfoModel> userInfoModelResponseModel) {
        //there is no need to use this method in this view
    }

    @Override
    public void queryError(Throwable e) {
        if(momentsPresenter.getTotal()==0) {
            momentsPresenter.setAdapterStates(MomentsRecyclerAdapter.NO_NETWORK);
        }
        else {
            refreshLayout.finishLoadMore(false);
        }
        swipeRefreshLayout.setRefreshing(false);
        e.printStackTrace();
    }

    private void queryDifferentMoments(int queryType){
        int page = 1;
        if(queryType==LOAD){
            page = momentsPresenter.getNextPage();
        }
        if(type.equals(LeftFragment.TAB_TYPE_1)) {
            momentsPresenter.queryForInfo((int) UserInfoLab.get().getUserInfoModel().getId(), page);
        }else{
            momentsPresenter.queryForNearByInfo((int) UserInfoLab.get().getUserInfoModel().getId(),   page);
        }
    }

    public String getType(){
        return type;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==MomentsPresenter.RESULT_CODE&&resultCode== Activity.RESULT_OK){
            int position = data.getIntExtra("position",0);
            int commentsNum = data.getIntExtra("commentsNum",0);
            Log.d("momentsview", "onActivityResult: "+String.valueOf(position)+"  "+String.valueOf(commentsNum));
            momentsPresenter.notifyCommentsNumChange(position,commentsNum);
        }
        Tencent.onActivityResultData(requestCode, resultCode, data, null);
    }

    @Override
    public void shareToQZone(Bundle params) {
        mTencent.shareToQzone(this.getActivity(), params,null);
    }

    @Override
    public void onDestroy() {
        momentsPresenter.onViewDestroyed();
        super.onDestroy();
    }


}
