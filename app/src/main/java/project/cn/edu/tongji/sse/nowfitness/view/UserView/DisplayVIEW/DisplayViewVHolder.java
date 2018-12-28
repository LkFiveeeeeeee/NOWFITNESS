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
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.DisplayPresenter;
import project.cn.edu.tongji.sse.nowfitness.presenter.FollowPresenter;

public class DisplayViewVHolder extends RecyclerView.ViewHolder {
    private CircleImageView avatar;
    private TextView nickName;
    private TextView sex;
    private IndividualModel individualModel;
    private AppCompatButton switchButton;
    private FollowPresenter followPresenter = new FollowPresenter();

    public DisplayViewVHolder(LayoutInflater inflater, ViewGroup parent){
        super(inflater.inflate(R.layout.individual_item,parent,false));

        avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
        nickName = (TextView) itemView.findViewById(R.id.username);
        sex = (TextView) itemView.findViewById(R.id.sex);
        switchButton = (AppCompatButton) itemView.findViewById(R.id.follow_button);

    }

    public void setSwitchButton(boolean judge){
        if(judge){
            Drawable img = itemView.getContext().getResources().getDrawable(R.drawable.righticon);
            img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
            switchButton.setCompoundDrawables(img,null,null,null);
            switchButton.setText(R.string.isFollowed);
        }else{
            Drawable img = itemView.getContext().getResources().getDrawable(R.drawable.add_mini);
            img.setBounds(0,0,img.getMinimumWidth(),img.getMinimumHeight());
            switchButton.setCompoundDrawables(img,null,null,null);
            switchButton.setText(R.string.unFollowed);
        }
    }

    void bind(final IndividualModel individual){
        this.individualModel = individual;
        nickName.setText(individualModel.getNickName());
        sex.setText(individualModel.getSex());
        Glide.with(itemView).load(individualModel.getPicture()).into(avatar);
        setSwitchButton(individualModel.isStated());
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                individualModel.setStated(!individualModel.isStated());
                setSwitchButton(individualModel.isStated());
                UserInfoModel userInfoModel = UserInfoLab.get().getUserInfoModel();
                if(individualModel.isStated()){
                    followPresenter.postFollowInfo((int)UserInfoLab.get().getUserInfoModel().getId(),individualModel.getId());
           //         userInfoModel.setFollowingNum(userInfoModel.getFollowingNum() + 1);
                }else{
                    followPresenter.deleteFollowInfo((int)UserInfoLab.get().getUserInfoModel().getId(),individualModel.getId());
           //         userInfoModel.setFollowingNum(userInfoModel.getFollowingNum() - 1);
                }
                //TODO 更改网络方面数据
                //TODO Toast 成功or失败信息
            }
        });
    }



}
