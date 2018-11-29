package project.cn.edu.tongji.sse.nowfitness.presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.view.CommentsView.MomentsDetailView;

/**
 * Created by a on 2018/11/25.
 */

public class CommentsListViewAdapter extends BaseExpandableListAdapter {
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;

    private static final String TAG = "CommentExpandAdapter";
    private List<CommentsDetailModel> commentsList;
    private List<CommentsReplyModel> replyList;
    private MomentsDetailView context;
    private MomentsModel dMomentsModel;
    private int pageIndex = 1;

    public CommentsListViewAdapter(MomentsDetailView context, List<CommentsDetailModel> commentsList, MomentsModel momentsModel) {
        this.context = context;
        this.commentsList = commentsList;
        dMomentsModel = momentsModel;
    }
    public void resetCommentsList(List<CommentsDetailModel>commentsDetailModelList){
        commentsList = commentsDetailModelList;
    }

    @Override
    public int getGroupCount() {
        return commentsList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(commentsList.get(i).getRepliesList() == null){
            return 0;
        }else {
            return commentsList.get(i).getRepliesList().size()>0 ? commentsList.get(i).getRepliesList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentsList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentsList.get(i).getRepliesList().get(i1);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        int type = getItemViewType(groupPosition);
        if(type==TYPE_2&&commentsList.size()>1) {
            final MomentsDetailView.GroupHolder groupHolder;
            if (groupPosition==1||convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_item, viewGroup, false);
                groupHolder = context.new GroupHolder(convertView);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (MomentsDetailView.GroupHolder) convertView.getTag();
            }
            groupHolder.setTopDivider(groupPosition);
            Glide.with(context).load(R.drawable.test);//errorrrrr
            groupHolder.onBindView(commentsList.get(groupPosition));
            return convertView;
        }else{
            final MomentsDetailView.GroupMomentsHolder groupMomentsHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.moments_detail_moments_view, viewGroup, false);
                groupMomentsHolder = context.new GroupMomentsHolder(convertView);
                convertView.setTag(groupMomentsHolder);
            } else {
                groupMomentsHolder = (MomentsDetailView.GroupMomentsHolder) convertView.getTag();
            }
            //Glide.with(context).load(R.drawable.test);//errorrrrr
            groupMomentsHolder.onBindView(dMomentsModel);
            return convertView;
        }
    }
    public int getItemViewType(int groupPosition) {
        int p = groupPosition;
        if (p == 0)
            return TYPE_1;
        else
            return TYPE_2;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final MomentsDetailView.ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item,viewGroup, false);
            childHolder = context.new ChildHolder(convertView);
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (MomentsDetailView.ChildHolder) convertView.getTag();
        }
        if(groupPosition!=0)
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

    public void addTheReplyData(CommentsReplyModel replyDetailBean, int groupPosition){
        if(replyDetailBean!=null){
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
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


    private void addReplyList(List<CommentsReplyModel> replyList, int groupPosition){
        if(commentsList.get(groupPosition).getRepliesList() != null ){
            commentsList.get(groupPosition).getRepliesList().clear();
            commentsList.get(groupPosition).getRepliesList().addAll(replyList);
        }else {

            commentsList.get(groupPosition).setRepliesList(replyList);
        }

        notifyDataSetChanged();
    }

}
