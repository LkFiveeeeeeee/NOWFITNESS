package project.cn.edu.tongji.sse.nowfitness.view.UserView.DisplayVIEW;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import project.cn.edu.tongji.sse.nowfitness.model.IndividualModel;
import project.cn.edu.tongji.sse.nowfitness.presenter.DisplayPresenter;

public class DisplayViewAdapter extends RecyclerView.Adapter<DisplayViewVHolder> {
    private List<IndividualModel> individualModels;
    private DisplayPresenter displayPresenter;
    DisplayViewAdapter(List<IndividualModel> list, DisplayPresenter displayPresenter){
        individualModels = list;
        this.displayPresenter = displayPresenter;
    }



    @NonNull
    @Override
    public DisplayViewVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new DisplayViewVHolder(layoutInflater,parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayViewVHolder holder, int position) {
        IndividualModel individualModel = individualModels.get(position);
        holder.bind(individualModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayPresenter.jumpToPersonPage(
                        individualModel.getId(),individualModel.getUserName(),
                        individualModel.getNickName(),individualModel.getPicture());
            }
        });
    }

    @Override
    public int getItemCount() {
        return individualModels.size();
    }
}
