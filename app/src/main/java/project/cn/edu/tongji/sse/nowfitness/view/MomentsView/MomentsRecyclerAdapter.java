package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.BaseMomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsViewHolder;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.EmptyViewHolder;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.LoadingViewHolder;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.OfflineViewHolder;

/**
 * Created by a on 2018/11/28.
 */

public class MomentsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NORMAL = 0;//正常的动态布局
    public static final int NO_CONTENT= 1;//无内容的布局
    public static final int NO_NETWORK =2;//断网的布局
    public static final int LOADING =3;//加载中布局


    private List<MomentsModel> momentsLab;
    private BaseMomentsPresenter baseMomentsPresenter;
    private int viewStates;

    public MomentsRecyclerAdapter(List<MomentsModel> momentsModelsList,BaseMomentsPresenter baseMomentsPresenter){
        this.baseMomentsPresenter = baseMomentsPresenter;
        this.momentsLab = momentsModelsList;
        this.viewStates =LOADING;
    }
    public void resetMomentsModelsList(List<MomentsModel> momentsModelsList){
        this.momentsLab = momentsModelsList;
    }
    public void setViewStatus(int type){
        viewStates = type;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        switch (viewType) {
            case NORMAL:
                itemView = inflater.inflate(R.layout.moments_item, parent, false);
                return new MomentsViewHolder(itemView, baseMomentsPresenter);
            case NO_CONTENT:
                itemView = inflater.inflate(R.layout.display_no_item, parent, false);
                return new EmptyViewHolder(itemView);
            case NO_NETWORK:
                itemView = inflater.inflate(R.layout.network_error, parent, false);
                return new OfflineViewHolder(itemView);
            case LOADING:
                itemView = inflater.inflate(R.layout.loading_view, parent, false);
                return new LoadingViewHolder(itemView);
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MomentsViewHolder&&momentsLab.size()>0) {
            if(momentsLab.size()>position) {
                ((MomentsViewHolder) holder).onBindMomentsData(momentsLab.get(position), position);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baseMomentsPresenter.jumpToMomentsDetail(momentsLab.get(position),position);
                }
            });
        }else if(holder instanceof OfflineViewHolder){
            ((OfflineViewHolder) holder).setTextView("网络开小差了。。");
        }else{
            //holder instanceof EmptyViewHolder NOTHING TO DO
        }
    }
    @Override
    public int getItemCount() {
        if(momentsLab.size()==0){
            return 1;
        }else{
            return momentsLab.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
            return viewStates;
    }

    /**
     * @Author: omf
     * @Description: 在recyclerview中的view被回收时的处理
     * @Param holder
     * @Return: void
     */
    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if(holder!=null&&holder instanceof MomentsViewHolder){
            Glide.with(holder.itemView).clear(((MomentsViewHolder)holder).contentPicImage);//取消该view中glide加载图片
            ((MomentsViewHolder)holder).contentPicImage.setVisibility(View.VISIBLE);//设置view中图片可见
        }
        super.onViewRecycled(holder);
    }
}