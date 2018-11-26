package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.DisplayPresenter;

public class DisplayView extends AppCompatActivity {
    private RecyclerView displayRecyclerView;
    private List<IndividualModel> individualModelList = new ArrayList<>();
    private DisplayPresenter displayPresenter;
    private TextView displayText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_view);
        displayPresenter = new DisplayPresenter(this);
        displayPresenter.initView();
    }


    public void initView(){
        initModel();
        displayRecyclerView = findViewById(R.id.individual_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getApplication());
        displayRecyclerView.setLayoutManager(manager);
        DisplayViewAdapter displayViewAdapter = new DisplayViewAdapter(individualModelList);
        displayRecyclerView.setAdapter(displayViewAdapter);
        setDisplayText();
    }

    private void setDisplayText(){
        displayText = findViewById(R.id.display_text);
        if(individualModelList.get(0).isFollowed() == true){
            displayText.setText("关注列表");
        }else{
            displayText.setText("粉丝列表");
        }
    }

    public void initModel(){
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",false));
    }
}
