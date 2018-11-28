package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.graphics.Color;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsCommentsModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.CommentsListViewAdapter;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;

public class MomentsDetailView extends AppCompatActivity {


    public static final int COMMENT_TARGET_MOMENTS=1001;
    public static final int COMMENT_TARGET_COMMENTS=1002;
    public static final int COMMENT_TARGET_REPLYS=1003;
    private CommentExpandableListView expandableListView;
    private CircleImageView momentsUserImage;
    private TextView momentsUserNameText;
    private TextView momentsTimeText;
    private TextView momentsContentText;
    private PhotoView momentsImage;

    private BottomSheetDialog dialog;
    private TextView commentTextView;
    private MomentsDetailPresenter momentsDetailPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moments_detail_view);
        momentsDetailPresenter = new MomentsDetailPresenter(this);
        momentsDetailPresenter.initView();
        momentsDetailPresenter.setExpandableListView(expandableListView);
        momentsDetailPresenter.initExpandableList();
    }

    public void initView(List<CommentsDetailModel> commentsList){
        momentsImage =(PhotoView) findViewById(R.id.moments_detail_image);
        momentsUserImage = (CircleImageView)findViewById(R.id.moments_detail_userPicture);
        momentsUserNameText = (TextView)findViewById(R.id.moments_detail_userName);
        momentsTimeText =(TextView)findViewById(R.id.moments_detail_time);
        momentsContentText = (TextView)findViewById(R.id.moments_detail_detail);
        expandableListView = ( CommentExpandableListView) findViewById(R.id.moments_detail_comments_listView);
        commentTextView = (TextView) findViewById(R.id.moments_detail_do_comment);
        commentTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showCommentDialog(commentsList,COMMENT_TARGET_MOMENTS,-1,-1);
            }
        });
    }

    public void initExpandableListView(final List<CommentsDetailModel> commentList){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复

        for(int i = 1; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e("momentsDetailView", "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getCommentUserName());
                showCommentDialog(commentList,COMMENT_TARGET_COMMENTS,groupPosition,-1);
                return true;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                showCommentDialog(commentList,COMMENT_TARGET_REPLYS,groupPosition,childPosition);
                return true;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");
            }
        });
    }

    private void showCommentDialog( List<CommentsDetailModel> commentsList,final int commentType, final int groupPosition, final int childPosition){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.moments_dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.moments_dialog_comment_bt);
        CommentsDetailModel commentsDetailModel;
        switch (commentType) {
            case COMMENT_TARGET_MOMENTS :
                commentText.setHint("请输入评论内容");
                break;
            case COMMENT_TARGET_COMMENTS:
                commentsDetailModel = commentsList.get(groupPosition);
                commentText.setHint("回复 " + commentsDetailModel.getCommentUserName() + " 的评论:");
                break;
            case COMMENT_TARGET_REPLYS:
                commentsDetailModel = commentsList.get(groupPosition);
                commentText.setHint("回复"+commentsDetailModel.getRepliesList().get(childPosition).getFromUserName() + "的评论");
                break;
            default:
                break;
        }
        dialog.setContentView(commentView);
        if(commentType==COMMENT_TARGET_COMMENTS){
            View parent = (View) commentView.getParent();
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
            commentView.measure(0,0);
            behavior.setPeekHeight(commentView.getMeasuredHeight());
        }

        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){
                    dialog.dismiss();
                    switch (commentType){
                        case COMMENT_TARGET_MOMENTS :
                           momentsDetailPresenter.addCommentData(commentContent);
                            break;
                        case COMMENT_TARGET_COMMENTS:
                           momentsDetailPresenter.addReplyData(groupPosition-0,commentContent);
                            expandableListView.expandGroup(groupPosition);
                            //Toast.makeText(MainActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                            break;
                        case COMMENT_TARGET_REPLYS:
                            momentsDetailPresenter.addReplyData(groupPosition-0,commentContent);
                            expandableListView.expandGroup(groupPosition);
                            break;
                        default:
                            break;
                    }

                }else {
                    Toast.makeText(MomentsDetailView.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        dialog.show();
    }

    public class GroupHolder{
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private View topDivider;
        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            topDivider = (View)view.findViewById(R.id.top_divider);
        }
        public void onBindView(CommentsDetailModel commentsDetailModel){
            tv_name.setText(commentsDetailModel.getCommentUserName());
            tv_time.setText(commentsDetailModel.getCommentTime());
            tv_content.setText(commentsDetailModel.getContent());
        }
        public void setTopDivider(int groupPostion) {
            if (groupPostion <= 1) {
                topDivider.setVisibility(View.GONE);
            } else {
                topDivider.setVisibility(View.VISIBLE);
            }
        }
    }

    public class GroupMomentsHolder{
        private CircleImageView userPhoto;
        private TextView userName, content,releaseTime;
        private PhotoView momentsImage;
        public GroupMomentsHolder(View view) {
            userPhoto = (CircleImageView) view.findViewById(R.id.moments_detail_userPicture);
            content = (TextView) view.findViewById(R.id.moments_detail_detail);
            userName = (TextView) view.findViewById(R.id.moments_detail_userName);
            releaseTime = (TextView) view.findViewById(R.id.moments_detail_time);
            momentsImage = (PhotoView)view.findViewById(R.id.moments_detail_image);
        }
    }
    public class ChildHolder{
        private TextView tv_name, tv_content;
        public ChildHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.reply_item_user);
            tv_content = (TextView) view.findViewById(R.id.reply_item_content);
        }
        public void onBindView(CommentsReplyModel commentsReplyModel){
            String replyUser = commentsReplyModel.getFromUserName();
            if(!TextUtils.isEmpty(replyUser)){
                tv_name.setText(replyUser + ":");
            }else {
                tv_name.setText("无名"+":");
            }
                tv_content.setText(commentsReplyModel.getContent());
        }
    }
}
