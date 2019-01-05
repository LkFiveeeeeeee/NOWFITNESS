package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.Constant;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.model.ResponseModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.FollowPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

/**
 * Created by LK on 2018/11/24.
 */

public class DisplayViewVHolder extends RecyclerView.ViewHolder implements FollowMethod{
    private final CircleImageView avatar;
    private TextView nickName;
    private TextView sex;
    private IndividualModel individualModel;
    private AppCompatButton switchButton;
    private FollowPresenter followPresenter = new FollowPresenter(this);

    public DisplayViewVHolder(LayoutInflater inflater, ViewGroup parent){
        super(inflater.inflate(R.layout.individual_item,parent,false));

        avatar = itemView.findViewById(R.id.avatar);
        nickName = itemView.findViewById(R.id.username);
        sex = itemView.findViewById(R.id.sex);
        switchButton = itemView.findViewById(R.id.follow_button);

    }

    private void setSwitchButton(boolean judge){
        if(judge){
            setFollow();
        }else{
            setUnFollow();
        }
    }

    private void setFollow(){
        Drawable img = itemView.getContext().getResources().getDrawable(R.drawable.righticon);
        img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
        switchButton.setCompoundDrawables(img,null,null,null);
        switchButton.setText(R.string.isFollowed);
    }

    private void setUnFollow(){
        Drawable img = itemView.getContext().getResources().getDrawable(R.drawable.add_mini);
        img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
        switchButton.setCompoundDrawables(img,null,null,null);
        switchButton.setText(R.string.unFollowed);
    }

    void bind(final IndividualModel individual){
        this.individualModel = individual;
        nickName.setText(individualModel.getNickName());
        sex.setText(individualModel.getSex());
        Glide.with(itemView).load(individualModel.getPicture()).into(avatar);
        setSwitchButton(individualModel.isStated());
        //设置监听,当点击之后
        switchButton.setOnClickListener((View view) -> {
            individualModel.setStated(!individualModel.isStated());
            setSwitchButton(individualModel.isStated());
            UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
            //当关注之后,设置一些信息的动态变化,并将该动作传递到后台
            if(individualModel.isStated()){
                followPresenter.postFollowInfo((int)UserInfoLab.get().
                        getUserInfoModel().getId(),individualModel.getId());

            }else{
                followPresenter.deleteFollowInfo((int)UserInfoLab.get().
                        getUserInfoModel().getId(),individualModel.getId());

            }
        });
    }


    @Override
    public void postSuccess(ResponseModel responseModel) {
        if(responseModel.getStatus() >= Constant.NET_CODE_200 && responseModel.getStatus() < Constant.NET_CODE_300){
            UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
            userInfoModel.setFollowingNum(userInfoModel.getFollowingNum() + 1);
        }else{
            ConstantMethod.toastShort(itemView.getContext(),"请求错误");
        }
    }

    @Override
    public void deleteSuccess(ResponseModel responseModel) {
        if(responseModel.getStatus() >= Constant.NET_CODE_200 && responseModel.getStatus() < Constant.NET_CODE_300){
            UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
            userInfoModel.setFollowingNum(userInfoModel.getFollowingNum() - 1);
        }else{
            ConstantMethod.toastShort(itemView.getContext(),"请求错误");
        }
    }

    @Override
    public void sendError(Throwable e) {
        ConstantMethod.toastShort(itemView.getContext(),"网络连接错误");
    }
}
