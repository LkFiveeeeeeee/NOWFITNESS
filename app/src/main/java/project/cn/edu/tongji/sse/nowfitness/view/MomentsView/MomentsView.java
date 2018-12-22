package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;


import android.graphics.Rect;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;


import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModelList;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.LeftView.LeftFragment;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsView extends Fragment implements MomentsMethod{
    private View myView;
    private String type;
    private RecyclerView momentsRecyclerView;
    private MomentsPresenter momentsPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SmartRefreshLayout refreshLayout;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.moments_page,container,false);
        if(type== LeftFragment.TAB_TYPE_1)
            momentsPresenter.queryForInfo((int) UserInfoLab.get().getUserInfoModel().getId(),1);
        momentsPresenter.initView();
        return myView;
    }

    public void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.news_swipe_refresh);
        momentsRecyclerView = (RecyclerView) myView.findViewById(R.id.news_recyclerView);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(myView.getContext(), LinearLayout.VERTICAL,false));
        //momentsRecyclerView.addItemDecoration(new MomentsItemDecoration());
        momentsPresenter.setMomentsRecyerView(momentsRecyclerView);
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
                momentsPresenter.queryForInfo((int) UserInfoLab.get().getUserInfoModel().getId(),1);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                    momentsPresenter.queryForInfo((int) UserInfoLab.get().getUserInfoModel().getId(),momentsPresenter.getNextPage());
                    //finishLoadMore(delayed);
            }
        });
    }


    @Override
    public void querySuccess(ResponseModel<MomentsModelList> models) {
        if(models.getStatus() >= 200 || models.getStatus() < 300){
           if(models.getData().getTotal()==0)
               momentsPresenter.setAdapterStates(MomentsRecyclerAdapter.NO_CONTENT);
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
    public void queryError(Throwable e) {
        if(momentsPresenter.getTotal()==0)
            momentsPresenter.setAdapterStates(MomentsRecyclerAdapter.NO_NETWORK);
        else
            refreshLayout.finishLoadMore(false);
        swipeRefreshLayout.setRefreshing(false);
        e.printStackTrace();
    }


    public class MomentsItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //如果不是第一个，则设置top的值。
            if (parent.getChildAdapterPosition(view) != 0){
                //这里直接硬编码为10px
                outRect.top = 2;
            }
        }
    }
}
