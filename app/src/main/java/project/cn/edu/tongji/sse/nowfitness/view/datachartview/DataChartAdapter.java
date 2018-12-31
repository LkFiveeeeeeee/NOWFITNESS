package project.cn.edu.tongji.sse.nowfitness.view.datachartview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataChartAdapter extends FragmentPagerAdapter {
    private List<Fragment> dataChartFragments = new ArrayList<>();
    public DataChartAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
        dataChartFragments.add(new DayFragment());
        dataChartFragments.add(DataChartFragment.newInstance(7));
        dataChartFragments.add(DataChartFragment.newInstance(30));
    }

    @Override
    public Fragment getItem(int position) {
        return dataChartFragments.get(position);
    }

    @Override
    public int getCount() {
        return dataChartFragments.size();
    }
}
