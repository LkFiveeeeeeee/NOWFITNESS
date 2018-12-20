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
    private static final int TYPE_3 = 1;
    private int order ;

    private List<CommentsDetailModel> commentsList;
    private List<CommentsReplyModel> replyList;
    private MomentsDetailView context;
    private MomentsModel dMomentsModel;
    private int pageIndex = 1;

    private MomentsDetailPresenter presenter;

    public CommentsListViewAdapter(MomentsDetailView context, List<CommentsDetailModel> commentsList, MomentsModel momentsModel,MomentsDetailPresenter momentsDetailPresenter) {
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
        Log.d("wwwww", "getGroupTypeCount: "+order++);
        return 2;
    }

    @Override
    public int getGroupCount() {
        Log.d("wwwww", "getGroupCount: "+order++);
        return commentsList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        Log.d("wwww", "getChildrenCount: "+order++);
        if(commentsList.get(i).getRepliesList() == null){
            return 0;
        }else {
            return commentsList.get(i).getRepliesList().size()>0 ? commentsList.get(i).getRepliesList().size():0;
        }

    }

    @Override
    public Object getGroup(int i) {
        Log.d("wwwww", "getGroup: "+order++);
        return commentsList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        if(commentsList.get(i).getRepliesList()!=null&&commentsList.get(i).getRepliesList().size()>0)
            return commentsList.get(i).getRepliesList().get(i1);
        else
            return null;
    }

    @Override
    public long getGroupId(int groupPosition) {

        Log.d("wwww", "getGroupId: "+order++);
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        Log.d("wwww", "getChildId: "+order++);
        return childPosition;//getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getGroupType(int groupPosition) {
        Log.d("wwww", "getGroupType: "+order++);
        if(groupPosition==0){
            return TYPE_1;
        }else{
            return TYPE_2;
        }
    }

    /*@Override
    public int getChildTypeCount() {
        return 1;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return TYPE_3;
    }*/

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        Log.d("wwww", "getGroupView: "+order++);
        int type = getGroupType(groupPosition);
        if(type==TYPE_2) {//&&commentsList.size()>=1
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
                    convertView = LayoutInflater.from(context).inflate(R.layout.moments_detail_moments_view, viewGroup, false);
                    groupMomentsHolder = new GroupMomentsHolder(convertView,context,presenter);
                    groupMomentsHolder.setGroupPosition(groupPosition);
                    convertView.setTag(groupMomentsHolder);
                } else {
                    groupMomentsHolder = (GroupMomentsHolder) convertView.getTag();
                }
                //Glide.with(context).load(R.drawable.test);//errorrrrr
                groupMomentsHolder.onBindView(dMomentsModel);
                return convertView;

        }
    }
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        Log.d("wwww", "getChildView: "+order++);
        // type = getChildType(groupPosition,childPosition);
        final ChildHolder childHolder;
        if(true) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item, viewGroup, false);
                childHolder = new ChildHolder(convertView,context,presenter);
                convertView.setTag(childHolder);
            } else {
                childHolder = (ChildHolder) convertView.getTag();
            }
            childHolder.onBindView(commentsList.get(groupPosition).getRepliesList().get(childPosition));
            return convertView;
        }else
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
            Log.e("wwww", "addTheReplyData: >>>>该刷新回复列表了:"+replyDetailBean.toString() );
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

    public boolean deleteReply(int groupPos,int childPos){
        if(commentsList.get(groupPos).getRepliesList() != null ){
            commentsList.get(groupPos).getRepliesList().remove(childPos);
            notifyDataSetChanged();
            return true;
        }else {
            return false;
        }
    }

    public boolean deleteComments(int groupPos){
        if(commentsList.get(groupPos)!=null){
            commentsList.remove(groupPos);
            notifyDataSetChanged();
            return true;
        }else
            return false;
    }


}
