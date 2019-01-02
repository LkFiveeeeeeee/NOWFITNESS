package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.github.chrisbanes.photoview.PhotoView;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;

import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModelList;


import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;



import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;


import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.MomentsView.MyQzoneShare;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.ToPersonPageView;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;


public class MomentsDetailView extends AppCompatActivity implements CommentsMethod,MyQzoneShare,ToPersonPageView {
    private MomentsModel momentsModel;

    public static final int COMMENT_TARGET_MOMENTS=1001;
    public static final int COMMENT_TARGET_COMMENTS=1002;
    public static final int COMMENT_TARGET_REPLYS=1003;
    private ExpandableListView expandableListView;
    private CircleImageView momentsUserImage;
    private TextView momentsUserNameText;
    private TextView momentsTimeText;
    private TextView momentsContentText;
    private PhotoView momentsImage;
    private ImageView shareImage;
    private int originPos;
    private Toolbar toolbar;
    private Tencent mTencent;

    private SwipeRefreshLayout commentsRefreshLayout;
    private BottomSheetDialog dialog;
    private TextView commentTextView;
    private MomentsDetailPresenter momentsDetailPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moments_detail_view);
        Intent intent = getIntent();
        originPos = intent.getIntExtra("position",0);
        momentsModel = (MomentsModel) intent.getSerializableExtra("moments");
        momentsDetailPresenter = new MomentsDetailPresenter(this,momentsModel,this);
        momentsDetailPresenter.initView();
        momentsDetailPresenter.setExpandableListView(expandableListView);
        momentsDetailPresenter.initExpandableList();
        momentsDetailPresenter.queryForComments(momentsModel.getMomentsId());
        mTencent = Tencent.createInstance(Constant.APP_ID, this.getApplicationContext());
    }

    @Override
    public void makeCommentsSuccess(ResponseModel responseModel) {

        if(responseModel.getStatus() == 200 || responseModel.getStatus() == 201)
            momentsDetailPresenter.queryForComments(momentsModel.getMomentsId());
        else{
            Toast.makeText(MomentsDetailView.this,responseModel.getError(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void makeCommentsError(Throwable e) {
        Log.d("getComment", "querySuccess: ");
        e.printStackTrace();
    }

    @Override
    public void querySuccess(ResponseModel<CommentsDetailModelList> commentsDetailModelsList) {
        Log.d("getComment", "querySuccess: ");
        if(commentsDetailModelsList.getStatus() >= 200 && commentsDetailModelsList.getStatus() < 300){
            Log.d("getComment", "querySuccess: " + commentsDetailModelsList.getData().getCommentsDetailModels().size());
            if(commentsDetailModelsList.getData().getCommentsDetailModels().size()>0){
                momentsDetailPresenter.resetCommentsList(commentsDetailModelsList.getData().getCommentsDetailModels());
            }
        }else{
            ConstantMethod.toastShort(MomentsDetailView.this,commentsDetailModelsList.getError());
        }
        commentsRefreshLayout.setRefreshing(false);
    }

    @Override
    public void queryError(Throwable e) {
        Toast.makeText(this,"网络出错了...",Toast.LENGTH_SHORT).show();
        e.printStackTrace();
        commentsRefreshLayout.setRefreshing(false);
    }

    public void initView(List<CommentsDetailModel> commentsList){
        momentsImage =(PhotoView) findViewById(R.id.moments_detail_image);
        momentsUserImage = (CircleImageView)findViewById(R.id.moments_detail_userPicture);
        momentsUserNameText = (TextView)findViewById(R.id.moments_detail_userName);
        momentsTimeText =(TextView)findViewById(R.id.moments_detail_time);
        momentsContentText = (TextView)findViewById(R.id.moments_detail_detail);
        expandableListView = ( ExpandableListView) findViewById(R.id.moments_detail_comments_listView);
        commentTextView = (TextView) findViewById(R.id.moments_detail_do_comment);
        shareImage = (ImageView)findViewById(R.id.detail_share_image);
        commentsRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.comments_refresh);
        toolbar = (Toolbar) findViewById(R.id.moments_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initEvent();
    }

    public void initEvent(){
        expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = expandableListView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0)
                        commentsRefreshLayout.setEnabled(true);
                    else
                        commentsRefreshLayout.setEnabled(false);
                }
                else
                    commentsRefreshLayout.setEnabled(false);
            }
        });
        commentsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                momentsDetailPresenter.queryForComments(momentsModel.getMomentsId());
            }
        });
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                momentsDetailPresenter.shareToQzone(momentsModel.getNickName(),momentsModel.getContent(),momentsModel.getImage(),momentsModel.getUserPhoto());
            }
        });
    }


    public void initExpandableListView(final List<CommentsDetailModel> commentList){
        commentTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showCommentDialog(commentList,COMMENT_TARGET_MOMENTS,-1,-1);
            }
        });
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        for(int i = 1; i<commentList.size(); i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final long packedPosition = expandableListView.getExpandableListPosition(i);
                final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
                //长按的是group的时候，childPosition = -1
                if (childPosition!= -1) {
                    if(momentsDetailPresenter.isReplyDeletable(groupPosition,childPosition))
                        showPopWindows(view,childPosition,groupPosition);
                }
                return true;
            }

        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                if(groupPosition!=0) {
                    showCommentDialog(commentList, COMMENT_TARGET_COMMENTS, groupPosition, -1);
                    return true;
                }
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
        if(!isCommentable(commentsList, commentType, groupPosition, childPosition)) {
            return;
        }
        dialog = new BottomSheetDialog(this,R.style.BottomSheetEdit);
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
                           momentsDetailPresenter.addReplyData(childPosition,groupPosition-0,commentContent);

                            expandableListView.expandGroup(groupPosition);
                            //Toast.makeText(MainActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                            break;
                        case COMMENT_TARGET_REPLYS:
                            momentsDetailPresenter.addReplyData(childPosition,groupPosition-0,commentContent);
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

    private boolean isCommentable(List<CommentsDetailModel> commentsList,final int commentType, final int groupPosition, final int childPosition){
        switch (commentType){
            case COMMENT_TARGET_MOMENTS :
                return true;
            case COMMENT_TARGET_COMMENTS:
                if(commentsList.get(groupPosition).getCommentUserName().equals(UserInfoLab.get().getUserInfoModel().getUserName())){
                    return false;
                 }else
                    return true;
            case COMMENT_TARGET_REPLYS:
                if(commentsList.get(groupPosition).getRepliesList().get(childPosition).getFromUserName()
                        .equals(UserInfoLab.get().getUserInfoModel().getUserName()))
                    return false;
                else
                    return true;
            default:
                return true;
        }
    }

    private void showPopWindows(View v,int childPos,int groupPos) {
        View mPopView = LayoutInflater.from(this).inflate(R.layout.menu_popup, null);
        final PopupWindow mPopWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //这个很重要 ,获取弹窗的长宽度
        mPopView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = mPopView.getMeasuredWidth();
        int popupHeight = mPopView.getMeasuredHeight();
        //获取父控件的位置
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        //显示位置
        mPopWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1]
                +popupHeight/2);
        mPopWindow.update();

        final String copyTxt = (String) v.getTag();
        mPopView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                momentsDetailPresenter.deleteReply(groupPos,childPos);
                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        resultBack();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            resultBack();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void resultBack(){
        Intent intent=new Intent();
        intent.putExtra("position",originPos);
        intent.putExtra("commentsNum",momentsDetailPresenter.pMomentsModel.getCommentsNum());
        setResult(Activity.RESULT_OK,intent);
    }

    @Override
    public void shareToQZone(Bundle params) {
        mTencent.shareToQzone(MomentsDetailView.this, params,null);
    }

    @Override
    public void jumpToPersonPage(int id, String userName, String nickName, String personPhoto) {
        Intent intent = new Intent();
        intent.putExtra("userId",id);
        intent.putExtra("nickName",nickName);
        intent.putExtra("photo",personPhoto);
        intent.putExtra("userName",userName);
        intent.setClass(this, PersonPageView.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                Toast.makeText(getApplicationContext(),"分享成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        momentsDetailPresenter.onViewDestroyed();
        super.onDestroy();
    }
}
