package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;

/**
 * Created by a on 2018/12/2.
 */

public class GroupMomentsHolder extends BaseExHolder implements View.OnClickListener{
    private View myView;
    private CircleImageView userPhoto;
    private TextView userName, content,releaseTime;
    private PhotoView momentsImage;
    private MomentsModel momentsModel;
    public GroupMomentsHolder(View view, Context context, MomentsDetailPresenter momentsDetailPresenter) {
        super(context,momentsDetailPresenter);
        userPhoto = (CircleImageView) view.findViewById(R.id.moments_detail_userPicture);
        content = (TextView) view.findViewById(R.id.moments_detail_detail);
        userName = (TextView) view.findViewById(R.id.moments_detail_userName);
        releaseTime = (TextView) view.findViewById(R.id.moments_detail_time);
        momentsImage = (PhotoView)view.findViewById(R.id.moments_detail_image);
        myView =view;
    }
    public void onBindView(MomentsModel momentsModel){
        this.momentsModel = momentsModel;
        if(momentsModel!=null){
            content.setText(momentsModel.getContent());
            userName.setText(momentsModel.getNickName());
            String time = momentsModel.getReleaseTime();
            time = time.substring(0,19);
            time = time.replace("T"," ");
            releaseTime.setText(time);
            Glide.with(myView).load(momentsModel.getUserPhoto()).into(userPhoto);
            if(!momentsModel.getImage().substring(momentsModel.getImage().length()-4).equals("null")) {
                Glide.with(myView).load(momentsModel.getImage()).into(momentsImage);
            }
            else {
                momentsImage.setVisibility(View.GONE);
            }
            userName.setOnClickListener(this);
            userPhoto.setOnClickListener(this);
        }
    }

    /**
     * @Author: omf
     * @Description: 设置点击的响应事件，跳转到个人社交主页
     * @Param view
     * @Return: void
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.moments_detail_userPicture:
                momentsDetailPresenter.jumpToPersonPage(momentsModel.getUserId(),
                        momentsModel.getUserName(),momentsModel.getNickName(),momentsModel.getUserPhoto());
                break;
            case R.id.moments_detail_userName:
                momentsDetailPresenter.jumpToPersonPage(momentsModel.getUserId(),
                        momentsModel.getUserName(),momentsModel.getNickName(),momentsModel.getUserPhoto());
                break;
            default:
                break;
        }
    }
}
