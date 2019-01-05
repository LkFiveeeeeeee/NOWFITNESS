package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.ChildHolder;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.GroupHolder;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.GroupMomentsHolder;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;

/**
 * Created by a on 2018/11/25.
 */

public class CommentsListViewAdapter extends BaseExpandableListAdapter {
    private static final int TYPE_1 = 0;
    private static final int TYPE_2 = 1;
    private int order ;

    private List<CommentsDetailModel> commentsList;
    private MomentsDetailView context;
    private MomentsModel dMomentsModel;

    private MomentsDetailPresenter presenter;

    public CommentsListViewAdapter(MomentsDetailView context, List<CommentsDetailModel> commentsList,
                                   MomentsModel momentsModel,MomentsDetailPresenter momentsDetailPresenter) {
        this.context = context;
        this.commentsList = commentsList;
        dMomentsModel = momentsModel;
        this.presenter = momentsDetailPresenter;
        order = 0;
    }
    public void resetCommentsList(List<CommentsDetailModel>commentsDetailModelList){
        commentsList = commentsDetailModelList;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupCount() {
        return commentsList.size();
    }

    /**
     * @Author: omf
     * @Description: 获得一级list中位置为i的二级list中view的数量
     * @Param i view的位置
     * @Return: int 数量
     */
    @Override
    public int getChildrenCount(int i) {
        if(commentsList.get(i).getRepliesList() == null){
            return 0;
        }else {
            if(commentsList.get(i).getRepliesList().size()>0) {
                return commentsList.get(i).getRepliesList().size();
            }else{
                return 0;
            }
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentsList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        if(commentsList.get(i).getRepliesList()!=null&&commentsList.get(i).getRepliesList().size()>0) {
            return commentsList.get(i).getRepliesList().get(i1);
        }
        else {
            return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * @Author: omf
     * @Description: 获得view的类型
     * @Param groupPosition
     * @Return: int
     */
    @Override
    public int getGroupType(int groupPosition) {

        if(groupPosition==0){
            return TYPE_1;//动态布局
        }else{
            return TYPE_2;//评论布局
        }
    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        int type = getGroupType(groupPosition);
        if(type==TYPE_2) {
            final GroupHolder groupHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, viewGroup, false);
                groupHolder = new GroupHolder(convertView,context,presenter);
                groupHolder.setGroupPosition(groupPosition);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.setTopDivider(groupPosition);
            Glide.with(context).load(R.drawable.test);//errorrrrr
            groupHolder.onBindView(commentsList.get(groupPosition));
            return convertView;
        }else{
                final GroupMomentsHolder groupMomentsHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.moments_detail_moments_view,
                            viewGroup, false);
                    groupMomentsHolder = new GroupMomentsHolder(convertView,context,presenter);
                    groupMomentsHolder.setGroupPosition(groupPosition);
                    convertView.setTag(groupMomentsHolder);
                } else {
                    groupMomentsHolder = (GroupMomentsHolder) convertView.getTag();
                }

                groupMomentsHolder.onBindView(dMomentsModel);
                return convertView;

        }
    }
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b,
                             View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item, viewGroup, false);
                childHolder = new ChildHolder(convertView,context,presenter);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }
            childHolder.onBindView(commentsList.get(groupPosition).getRepliesList().get(childPosition));
            return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public void addTheCommentData(CommentsDetailModel commentDetailBean){
        if(commentDetailBean!=null){
            commentsList.add(commentDetailBean);
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * @Author: omf
     * @Description: 初始化
     * @Param replyDetailBean
     * @Param groupPosition
     * @Return: void
     */
    public void addTheReplyData(CommentsReplyModel replyDetailBean, int groupPosition){
        if(replyDetailBean!=null){
            if(commentsList.get(groupPosition).getRepliesList() != null ){
                commentsList.get(groupPosition).getRepliesList().add(replyDetailBean);
            }else {
                List<CommentsReplyModel> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentsList.get(groupPosition).setRepliesList(replyList);
            }
            notifyDataSetChanged();
        }else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    public List<CommentsDetailModel> getCommentsList(){
        return commentsList;
    }

    private void addReplyList(List<CommentsReplyModel> replyList, int groupPosition){
        if(commentsList.get(groupPosition).getRepliesList() != null ){
            commentsList.get(groupPosition).getRepliesList().clear();
            commentsList.get(groupPosition).getRepliesList().addAll(replyList);
        }else {

            commentsList.get(groupPosition).setRepliesList(replyList);
        }
        notifyDataSetChanged();
    }

    /**
     * @Author: omf
     * @Description: 移除list中的reply的数据
     * @Param groupPos 在一级list中的位置
     * @Param childPos 在二级list中的位置
     * @Return: boolean
     */
    public boolean deleteReply(int groupPos,int childPos){
        if(commentsList.get(groupPos).getRepliesList() != null ){
            commentsList.get(groupPos).getRepliesList().remove(childPos);
            notifyDataSetChanged();
            return true;
        }else {
            return false;
        }
    }

    /**
     * @Author: omf
     * @Description: 移除list中评论数据
     * @Param groupPos 在一级list中的位置
     * @Return: boolean
     */
    public boolean deleteComments(int groupPos){
        if(commentsList.get(groupPos)!=null){
            commentsList.remove(groupPos);
            notifyDataSetChanged();
            return true;
        }else {
            return false;
        }
    }


}
