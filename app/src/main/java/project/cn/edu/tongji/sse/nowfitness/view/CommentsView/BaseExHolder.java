package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.content.Context;

import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;

/**
 * Created by a on 2018/12/18.
 */

public class BaseExHolder {
    protected Context mContext;
    protected MomentsDetailPresenter momentsDetailPresenter;
    protected int childPosition;//在expanableListView 的二级list中的位置
    protected int groupPosition;//在expanableListView 的 一级list中的位置
    public BaseExHolder(Context context,MomentsDetailPresenter momentsDetailPresenter){
        this.mContext = context;
        this.momentsDetailPresenter = momentsDetailPresenter;
    }
    public void setChildPosition(int pos){
        childPosition = pos;
    }
    public void setGroupPosition(int pos){
        groupPosition = pos;
    }


}
