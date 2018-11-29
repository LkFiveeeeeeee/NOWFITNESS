package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;

/**
 * Created by a on 2018/11/28.
 */

public class MomentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private CircleImageView userPhotoImage;
    private ImageView contentPicImage;
    private TextView userNameTextView;
    private TextView contentTextView;
    private TextView timeTextView;
    private ImageView like;
    private TextView likeNum;
    private TextView commentsNum;
    private MomentsModel mMoments;
    public MomentsViewHolder(View itemView) {
        super(itemView);
        userPhotoImage = (CircleImageView) itemView.findViewById(R.id.moments_user_photo);
        userNameTextView =(TextView)itemView.findViewById(R.id.moments_user_name);
        timeTextView =(TextView)itemView.findViewById(R.id.moments_release_time);
        contentTextView =(TextView)itemView.findViewById(R.id.moments_content);
        contentPicImage =(ImageView)itemView.findViewById(R.id.content_image);
        likeNum=(TextView)itemView.findViewById(R.id.like_num);
        commentsNum =(TextView)itemView.findViewById(R.id.comment_num);
        like = (ImageView) itemView.findViewById(R.id.image_like);
    }
    public void onBindMomentsData(MomentsModel moments){
        mMoments = moments;
        if(moments!=null) {
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
        like.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.image_like) {
            if (view.isSelected() == false) {
                view.setSelected(true);
            } else
                view.setSelected(false);
            //momentsPresenter.likeOrDislike();
        }
    }
}

