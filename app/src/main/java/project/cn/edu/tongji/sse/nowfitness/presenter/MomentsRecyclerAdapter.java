package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MomentsViewHolder;

/**
 * Created by a on 2018/11/28.
 */

public class MomentsRecyclerAdapter extends RecyclerView.Adapter<MomentsViewHolder> {

    private static final int IMAGE_TYPE = 0;
    private static final int NO_IMAGE_TYPE = 1;

    private List<MomentsModel> momentsLab;
    private Context mContext;
    private MomentsPresenter momentsPresenter;

    public MomentsRecyclerAdapter(List<MomentsModel> momentsModelsList,MomentsPresenter momentsPresenter){
        this.momentsPresenter = momentsPresenter;
        this.momentsLab = momentsModelsList;
    }
    public void resetMomentsModelsList(List<MomentsModel> momentsModelsList){
        this.momentsLab = momentsModelsList;
    }

    @NonNull
    @Override
    public MomentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.moments_item, parent, false);
        return new MomentsViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MomentsViewHolder holder, int position) {
        holder.onBindMomentsData(momentsLab.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //请求数据加载数据
                momentsPresenter.jumpToMomentsDetail(momentsLab.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return momentsLab.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {
            return IMAGE_TYPE;
        }
        return NO_IMAGE_TYPE;
    }

}