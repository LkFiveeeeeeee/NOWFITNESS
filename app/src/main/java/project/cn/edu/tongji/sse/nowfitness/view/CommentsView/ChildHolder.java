package project.cn.edu.tongji.sse.nowfitness.view.CommentsView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsDetailModel;
import project.cn.edu.tongji.sse.nowfitness.model.CommentsReplyModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsDetailPresenter;

/**
 * Created by a on 2018/12/2.
 */

public class ChildHolder extends  BaseExHolder implements View.OnClickListener{
    private TextView tv_fromName, tv_content,tv_toName;
    private CommentsReplyModel commentsReplyModel;
    public ChildHolder(View view, Context context, MomentsDetailPresenter momentsDetailPresenter) {
        super(context,momentsDetailPresenter);
        tv_fromName = (TextView) view.findViewById(R.id.reply_item_user);
        tv_toName = (TextView) view.findViewById(R.id.to_item_user);
        tv_content = (TextView) view.findViewById(R.id.reply_item_content);
    }
    public void onBindView(CommentsReplyModel commentsReplyModel){
        this.commentsReplyModel = commentsReplyModel;
        String replyUser = commentsReplyModel.getFromUserNickName();
        String toUserName =commentsReplyModel.getToUserNickName();
        if(!TextUtils.isEmpty(replyUser)){
            tv_toName.setText(toUserName );
            tv_fromName.setText(replyUser);

        }else {
            tv_toName.setText("无名"+":");
        }
        tv_content.setText(commentsReplyModel.getContent());
        if(commentsReplyModel.getFromUserId()!= UserInfoLab.get().getUserInfoModel().getId()) {
            tv_fromName.setOnClickListener(this);
        }
        if(commentsReplyModel.getToUserId()!=UserInfoLab.get().getUserInfoModel().getId()){
            tv_toName.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reply_item_user:
                momentsDetailPresenter.jumpToPersonPage(commentsReplyModel.getFromUserId(),commentsReplyModel.getFromUserName(),
                        commentsReplyModel.getFromUserNickName(),null);
                break;
            case R.id.to_item_user:
                momentsDetailPresenter.jumpToPersonPage(commentsReplyModel.getToUserId(),commentsReplyModel.getToUserName(),
                        commentsReplyModel.getToUserNickName(),null);
                break;
        }
    }


}
