package project.cn.edu.tongji.sse.nowfitness.view.MomentsView;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;


import project.cn.edu.tongji.sse.nowfitness.model.MomentsModel;
import project.cn.edu.tongji.sse.nowfitness.model.UserInfoLab;
import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsView extends Fragment implements MomentsMethod{
    private View myView;
    private String type;
    private RecyclerView momentsRecyclerView;
    private MomentsPresenter momentsPresenter;

    public static MomentsView newInstance(String type){
        Bundle args = new Bundle();
        args.putString("TYPE",type);
        MomentsView fragment = new MomentsView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=(String )getArguments().getString("TYPE");
        momentsPresenter = new MomentsPresenter(this,this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.moments_page,container,false);
        momentsPresenter.queryForInfo((int) UserInfoLab.get().getUserInfoModel().getId());
        momentsPresenter.initView();
        return myView;
    }

    public void initView(){
        momentsRecyclerView = (RecyclerView) myView.findViewById(R.id.news_recyclerView);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(myView.getContext(), LinearLayout.VERTICAL,false));
        //momentsRecyclerView.addItemDecoration(new MomentsItemDecoration());
        momentsPresenter.setMomentsRecyerView(momentsRecyclerView);

    }

    @Override
    public void querySuccess(List<MomentsModel> models) {
        Log.d("TestQuerry", "querySuccess: " + models.size());
        Log.d("TestQuerry", "querySuccess: " + models.get(0).getUserName());
    }

    @Override
    public void queryError(Throwable e) {
        e.printStackTrace();
    }


    public class MomentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView userPhotoImage;
        private ImageView contentPicImage;
        private TextView userNameTextView;
        private TextView contentTextView;
        private TextView timeTextView;
        private ImageView like;
        private TextView likeNum;
        private TextView commentsNum;
        private String mMoments;
        public MomentsViewHolder(View itemView) {
            super(itemView);
            userPhotoImage = (CircleImageView) itemView.findViewById(R.id.moments_user_photo);
            userNameTextView =(TextView)itemView.findViewById(R.id.moments_user_name);
            timeTextView =(TextView)itemView.findViewById(R.id.moments_release_time);
            contentTextView =(TextView)itemView.findViewById(R.id.moments_content);
            contentPicImage =(ImageView)itemView.findViewById(R.id.content_image);
            likeNum=(TextView)itemView.findViewById(R.id.like_num);
            commentsNum =(TextView)myView.findViewById(R.id.comment_num);
            like = (ImageView) itemView.findViewById(R.id.image_like);
        }
        public void onBindMomentsData(final String moments){
            mMoments = moments;
            userNameTextView.setText(moments);
            if(moments.length()<=6)
                contentPicImage.setVisibility(View.GONE);
            like.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
                if(view.getId()==R.id.image_like) {
                    if (view.isSelected() == false) {
                        view.setSelected(true);
                    } else
                        view.setSelected(false);
                    momentsPresenter.likeOrDislike();
                }
        }
    }
    public class MomentsItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
       //如果不是第一个，则设置top的值。
            if (parent.getChildAdapterPosition(view) != 0){
                //这里直接硬编码为10px
                outRect.top = 2;
            }
        }
    }
}
