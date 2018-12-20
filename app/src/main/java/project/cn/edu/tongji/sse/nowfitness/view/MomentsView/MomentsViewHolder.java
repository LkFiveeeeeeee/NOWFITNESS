package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.BaseMomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.PersonPagePresenter;
import project.cn.edu.tongji.sse.nowfitness.view.PersonPageView.PersonPageView;

/**
 * Created by a on 2018/11/28.
 */

public class MomentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private int DIALOG_NULL = 0;
    private int DIALOG_DELETE = 1;
    private int DIALOG_NOT_FOLLOWING = 2;


    private CircleImageView userPhotoImage;
    private ImageView contentPicImage;
    private TextView userNameTextView;
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

    private String title,message;
    private int type=DIALOG_NULL;

    public MomentsViewHolder(View itemView, BaseMomentsPresenter baseMomentsPresenter) {
        super(itemView);
        userPhotoImage = (CircleImageView) itemView.findViewById(R.id.moments_user_photo);
        userNameTextView =(TextView)itemView.findViewById(R.id.moments_user_name);
        timeTextView =(TextView)itemView.findViewById(R.id.moments_release_time);
        contentTextView =(TextView)itemView.findViewById(R.id.moments_content);
        contentPicImage =(ImageView)itemView.findViewById(R.id.content_image);
        likeNum=(TextView)itemView.findViewById(R.id.like_num);
        commentsNum =(TextView)itemView.findViewById(R.id.comment_num);
        like = (ImageView) itemView.findViewById(R.id.image_like);
        btMenu = (ImageView) itemView.findViewById(R.id.btnMenus);
        this.baseMomentsPresenter = baseMomentsPresenter;
    }
    public void onBindMomentsData(MomentsModel moments,int pos){
        mMoments = moments;
        myPosition = pos;
        if(moments!=null) {
            userId = moments.getUserId();
            String time = moments.getReleaseTime();
            time = time.substring(0,19);
            time=time.replace("T"," ");
            userNameTextView.setText(moments.getUserName());
            likeNum.setText(String.valueOf(moments.getLikes())+"人点赞");
            contentTextView.setText(moments.getContent());
            commentsNum.setText(String.valueOf(moments.getCommentsNum())+"人评论");
            timeTextView.setText(time);
            like.setSelected(moments.isLiked());
            if (moments.getUserPhoto()!=null)
                Glide.with(itemView).load("http://47.107.167.12:8080/api/image/get?imageName="+moments.getUserPhoto()).into(userPhotoImage);
            if(moments.getImage()!=null)
                Glide.with(itemView).load("http://47.107.167.12:8080/api/image/get?imageName="+moments.getImage()).into(contentPicImage);
            else
                contentPicImage.setVisibility(View.GONE);
        }
        if((baseMomentsPresenter instanceof PersonPagePresenter && userId!=0 )||baseMomentsPresenter instanceof MomentsPresenter)
            btMenu.setOnClickListener(this);
        else
            btMenu.setVisibility(View.GONE);
        like.setOnClickListener(this);
        userPhotoImage.setOnClickListener(this);
        userNameTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {;
        switch (view.getId()) {
            case R.id.image_like :
                if (view.isSelected() == false) {
                    view.setSelected(true);
                    mMoments.setLikes(mMoments.getLikes()+1);
                    likeNum.setText(String.valueOf(mMoments.getLikes())+"人点赞");
                    //发送点赞请求
                } else {
                    view.setSelected(false);
                    mMoments.setLikes(mMoments.getLikes()-1);
                    likeNum.setText(String.valueOf(mMoments.getLikes())+"人点赞");
                    //发送点赞请求
                }
                break;
            case R.id.moments_user_photo :
                baseMomentsPresenter.jumpToPersonPage(userId,mMoments.getUserName(),mMoments.getUserPhoto());
                break;
            case R.id.moments_user_name:
                baseMomentsPresenter.jumpToPersonPage(userId,mMoments.getUserName(),mMoments.getUserPhoto());
                break;
            case R.id.btnMenus:
                PopupMenu popup = new PopupMenu(baseMomentsPresenter.getContext(),btMenu);
                if(baseMomentsPresenter instanceof PersonPagePresenter){
                if(userId!=0) {//USERINFORid
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

                                    break;
                            }
                            return true;
                        }
                    });
                }
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
                //momentsPresenter.likeOrDislike();
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

