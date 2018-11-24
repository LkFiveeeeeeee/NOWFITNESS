package project.cn.edu.tongji.sse.nowfitness.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;


import project.cn.edu.tongji.sse.nowfitness.presenter.MomentsPresenter;

/**
 * Created by a on 2018/11/23.
 */

public class MomentsView extends Fragment {
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
        momentsPresenter = new MomentsPresenter(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.moments_page,container,false);
        momentsPresenter.initView();
        return myView;
    }

    public void initView(){
        momentsRecyclerView = (RecyclerView) myView.findViewById(R.id.news_recyclerView);
        momentsRecyclerView.setLayoutManager(new LinearLayoutManager(myView.getContext(), LinearLayout.VERTICAL,false));
        momentsPresenter.setMomentsRecyerView(momentsRecyclerView);
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
            switch (view.getId()){
                case R.id.image_like:
                    if(view.isSelected()==false) {
                        view.setSelected(true);
                    }else
                        view.setSelected(false);
                    momentsPresenter.likeOrDislike();
                    break;
                default:
                    //调用presenter跳转到动态详情页
            }
        }
    }

}