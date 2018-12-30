package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;

/**
 * Created by a on 2018/12/2.
 */

public class GroupHolder extends BaseExHolder implements View.OnClickListener {
    private CircleImageView logo;
    private TextView tv_name, tv_content, tv_time;
    private ImageView menu;
    private View topDivider;
    private CommentsDetailModel commentsDetailModel;
    private View myView;
    public GroupHolder(View view, Context context, MomentsDetailPresenter momentsDetailPresenter) {
        super(context,momentsDetailPresenter);
        logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
        tv_content = (TextView) view.findViewById(R.id.comment_item_content);
        tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
        tv_time = (TextView) view.findViewById(R.id.comment_item_time);
        topDivider = (View)view.findViewById(R.id.top_divider);
        menu = (ImageView)view.findViewById(R.id.comment_menu_bt);
        myView =view;
    }
    public void onBindView(CommentsDetailModel commentsDetailModel){
        this.commentsDetailModel = commentsDetailModel;
        if(commentsDetailModel.getCommentTime()!=null) {
            String time = commentsDetailModel.getCommentTime();
            time = time.substring(0, 19);
            time = time.replace("T", " ");
            tv_time.setText(time);
        }else{
            String time = getTimeStamp();
            tv_time.setText(time);
        }
        Log.e("aaaaa", "onBindView: "+commentsDetailModel.getCommentUserPhoto());
        Glide.with(myView).load(commentsDetailModel.getCommentUserPhoto()).into(logo);
        tv_name.setText(commentsDetailModel.getCommentUserNickName());
        tv_name.setOnClickListener(this);
        tv_content.setText(commentsDetailModel.getContent());
        logo.setOnClickListener(this);
        if(commentsDetailModel.getCommentUserId()==(int) UserInfoLab.get().getUserInfoModel().getId()) {
            menu.setVisibility(View.VISIBLE);
            menu.setOnClickListener(this);
        }
        else
            menu.setVisibility(View.GONE);
    }
    public void setTopDivider(int groupPostion) {
        if (groupPostion <= 1) {
            topDivider.setVisibility(View.GONE);
        } else {
            topDivider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comment_item_logo:
                momentsDetailPresenter.jumpToPersonPage(commentsDetailModel.getCommentUserId(),commentsDetailModel.getCommentUserName(),
                        commentsDetailModel.getCommentUserNickName(),commentsDetailModel.getCommentUserPhoto());
                break;
            case R.id.comment_item_userName:
                momentsDetailPresenter.jumpToPersonPage(commentsDetailModel.getCommentUserId(),commentsDetailModel.getCommentUserName(),
                        commentsDetailModel.getCommentUserNickName(), commentsDetailModel.getCommentUserPhoto());
                break;
            case R.id.comment_menu_bt:
                PopupMenu popup = new PopupMenu(mContext,menu);
                popup.getMenuInflater().inflate(R.menu.comments_pop_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                momentsDetailPresenter.pMomentsModel.setCommentsNum( momentsDetailPresenter.pMomentsModel.getCommentsNum()-1);
                                 momentsDetailPresenter.deleteComments(groupPosition);
                        }
                        return true;
                    }
                });
                popup.show();
                break;
                default:
                    break;
        }
    }
    private String getTimeStamp(){
        long time = System.currentTimeMillis();
        String timeStamp = String.valueOf(time/1000);
        if(timeStamp == null || timeStamp.isEmpty() || timeStamp.equals("null")){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(new Date(Long.valueOf(timeStamp+"000")));
    }
}
