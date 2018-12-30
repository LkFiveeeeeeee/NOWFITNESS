package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.BaseMomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.PersonPagePresenter;
import project.cn.edu.tongji.sse.nowfitness.view.LeftView.LeftFragment;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;

/**
 * Created by a on 2018/11/28.
 */

public class MomentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private int DIALOG_DELETE = 1;
    private int DIALOG_NOT_FOLLOWING = 2;
    private int PERSONPAGE = 0;
    private int LEFTFRAGMENT = 1;

    private int type ;
    private CircleImageView userPhotoImage;
    private ImageView contentPicImage;
    private TextView nickNameTextView;
    private TextView contentTextView;
    private TextView timeTextView;
    private ImageView like;
    private TextView likeNum;
    private TextView commentsNum;
    private ImageView btMenu;
    private MomentsModel mMoments;
    private BaseMomentsPresenter baseMomentsPresenter;
    private int userId;
    private int myPosition;


    public MomentsViewHolder(View itemView, BaseMomentsPresenter baseMomentsPresenter) {
        super(itemView);
        userPhotoImage = (CircleImageView) itemView.findViewById(R.id.moments_user_photo);
        nickNameTextView =(TextView)itemView.findViewById(R.id.moments_user_name);
        timeTextView =(TextView)itemView.findViewById(R.id.moments_release_time);
        contentTextView =(TextView)itemView.findViewById(R.id.moments_content);
        contentPicImage =(ImageView)itemView.findViewById(R.id.content_image);
        likeNum=(TextView)itemView.findViewById(R.id.like_num);
        commentsNum =(TextView)itemView.findViewById(R.id.comment_num);
        like = (ImageView) itemView.findViewById(R.id.image_like);
        btMenu = (ImageView) itemView.findViewById(R.id.btnMenus);
        this.baseMomentsPresenter = baseMomentsPresenter;
        if(baseMomentsPresenter instanceof PersonPagePresenter){
            type = PERSONPAGE;
        }else{
            type = LEFTFRAGMENT;
        }

    }
    public void onBindMomentsData(MomentsModel moments,int pos){
        mMoments = moments;
        myPosition = pos;
        if(moments!=null) {
            userId = moments.getUserId();
            String time = moments.getReleaseTime();
            time = time.substring(0,19);
            time=time.replace("T"," ");
            nickNameTextView.setText(moments.getNickName());
            likeNum.setText(String.valueOf(moments.getLikes())+"人点赞");
            contentTextView.setText(moments.getContent());
            commentsNum.setText(String.valueOf(moments.getCommentsNum())+"人评论");
            timeTextView.setText(time);
            like.setSelected(moments.isLiked());
            Log.d("sssssss", "onBindMomentsData: "+moments.getUserPhoto());
            if (moments.getUserPhoto()!=null)
                Glide.with(itemView).load(moments.getUserPhoto()).into(userPhotoImage);
            if(!moments.getImage().substring(moments.getImage().length()-4).equals("null"))
                Glide.with(itemView).load(moments.getImage()).into(contentPicImage);
            else
                contentPicImage.setVisibility(View.GONE);
        }
        if((type == PERSONPAGE&& userId==(int)UserInfoLab.get().getUserInfoModel().getId() )
                ||type == LEFTFRAGMENT && ((MomentsPresenter) baseMomentsPresenter).getMomentsType().equals(LeftFragment.TAB_TYPE_1))
            btMenu.setOnClickListener(this);
        else
            btMenu.setVisibility(View.GONE);
        like.setOnClickListener(this);
        userPhotoImage.setOnClickListener(this);
        nickNameTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
        switch (view.getId()) {
            case R.id.image_like :
                if (!view.isSelected()) {
                    view.setSelected(true);
                    mMoments.setLikes(mMoments.getLikes()+1);
                    likeNum.setText(String.valueOf(mMoments.getLikes())+"人点赞");
                    baseMomentsPresenter.postLikeInfo(mMoments.getMomentsId(),(int)userInfoModel.getId());
                    //发送点赞请求
                } else {
                    view.setSelected(false);
                    mMoments.setLikes(mMoments.getLikes()-1);
                    likeNum.setText(String.valueOf(mMoments.getLikes())+"人点赞");
                    baseMomentsPresenter.deleteLikeInfo(mMoments.getMomentsId(),(int) userInfoModel.getId());
                    //删除点赞请求
                }
                break;
            case R.id.moments_user_photo :
                if(type == LEFTFRAGMENT) {
                    baseMomentsPresenter.jumpToPersonPage(userId, mMoments.getUserName(), mMoments.getNickName(), mMoments.getUserPhoto());
                }
                break;
            case R.id.moments_user_name:
                if(type == LEFTFRAGMENT) {
                    baseMomentsPresenter.jumpToPersonPage(userId, mMoments.getUserName(), mMoments.getNickName(), mMoments.getUserPhoto());
                }
                break;
            case R.id.btnMenus:
                PopupMenu popup = new PopupMenu(baseMomentsPresenter.getContext(),btMenu);
                if(type == PERSONPAGE){
                    popup.getMenuInflater().inflate(R.menu.moments_pop_menu_u, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete:
                                    showDialog("确认删除这条动态？","你将会永远失去一条美好的回忆！！",
                                            DIALOG_DELETE );
                                    break;
                                case R.id.share:
                                    baseMomentsPresenter.shareToQzone(mMoments.getNickName(),mMoments.getContent(),mMoments.getImage(),mMoments.getUserPhoto());
                                    break;
                            }
                            return true;
                        }
                    });
                }else{
                    popup.getMenuInflater().inflate(R.menu.moments_pop_menu_f, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.not_following:
                                    showDialog("确认不再关注该用户？","取关后该用户的所有动态将不会出现在你的好友动态中"
                                    ,DIALOG_NOT_FOLLOWING);
                                    break;
                                case R.id.share:
                                    baseMomentsPresenter.shareToQzone(mMoments.getNickName(),mMoments.getContent(),mMoments.getImage(),mMoments.getUserPhoto());
                                    break;
                            }
                            return true;
                        }
                    });
                }
                popup.show();
                break;
            default:
                break;
        }
    }

    private void showDialog(String title,String message,int type){
        AlertDialog.Builder dialog = new AlertDialog.Builder(baseMomentsPresenter.getContext());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(type==DIALOG_NOT_FOLLOWING) {
                    UserInfoLab.get().getUserInfoModel().setFollowingNum(UserInfoLab.get().getUserInfoModel().getFollowingNum()-1);
                    baseMomentsPresenter.deleteFollowingInfo((int)UserInfoLab.get().getUserInfoModel().getId(),mMoments.getUserId());
                }else if(type==DIALOG_DELETE){
                    ((PersonPagePresenter) baseMomentsPresenter).deleteMoments(myPosition);
                }
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialog.show();
    }

}

