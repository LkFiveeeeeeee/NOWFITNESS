package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.content.Intent;
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
import project.cn.edu.tongji.sse.nowfitness.view.method.ConstantMethod;

public class DisplayView extends AppCompatActivity implements DisplayViewMethod{
    private final int stars_type = 1;
    private final int fans_type = 0;

    private RecyclerView displayRecyclerView;
    private List<IndividualModel> individualModelList = new ArrayList<>();
    private DisplayPresenter displayPresenter;
    private TextView displayText;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_view);
        Intent intent = getIntent();
 /*       String typeString = intent.getStringExtra(ConstantMethod.type_Key);
        if(typeString.equals(ConstantMethod.fans_Type)){
            type = fans_type;
        }else{
            type = stars_type;
        }*/
        displayPresenter = new DisplayPresenter(this);
        //TODO 本地数据库信息获取
        displayPresenter.initView();
    }


    public void initView(){
        initModel();
        displayRecyclerView = findViewById(R.id.individual_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getApplication());
        displayRecyclerView.setLayoutManager(manager);
        queryForSuccess(individualModelList);
        setDisplayText();
    }

    private void setDisplayText(){
        displayText = findViewById(R.id.display_text);
        if(type == stars_type){
            displayText.setText("粉丝列表");
        }else{
            displayText.setText("关注列表");
        }
    }

    public void initModel(){
        /*individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",true));
        individualModelList.add(new IndividualModel("","小林","男",false));*/
    }

    @Override
    public void queryForSuccess(List<IndividualModel> modelList) {
        //TODO 本地数据库信息更新
        if(modelList.size() == 0){

            DisplayNoItemAdapter displayNoItemAdapter = new DisplayNoItemAdapter();
            displayRecyclerView.setAdapter(displayNoItemAdapter);
            displayNoItemAdapter.notifyDataSetChanged();
            return;
        }
        DisplayViewAdapter displayViewAdapter = new DisplayViewAdapter(modelList);
        displayRecyclerView.setAdapter(displayViewAdapter);
        displayViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
