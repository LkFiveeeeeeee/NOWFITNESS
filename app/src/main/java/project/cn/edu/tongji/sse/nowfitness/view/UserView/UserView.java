package project.cn.edu.tongji.sse.nowfitness.view.UserView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.CalendarControlMethod;

public class UserView extends AppCompatActivity implements CalendarControlMethod {

    /*Calendar Para*/
    private TextView monthDay;
    private TextView yearDay;
    private TextView lunarDay;
    private TextView currentDay;
    private CalendarView calendarView;
    /*Calendar Para*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userview);
        initView();
    }

    void initView(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initCalendarView();
    }

    /*Calendar Method*/

    @Override
    public void initCalendarView() {
        monthDay = findViewById(R.id.tv_month_day);
        yearDay = findViewById(R.id.tv_year);
        lunarDay = findViewById(R.id.tv_lunar);
        currentDay = findViewById(R.id.tv_current_day);
        calendarView = findViewById(R.id.calendarView);
        setInitView();
    }

    @Override
    public void setInitView() {
        yearDay.setText(String.valueOf(calendarView.getCurYear()));
        monthDay.setText(calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        lunarDay.setText("今日");
        currentDay.setText(String.valueOf(calendarView.getCurDay()));
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
    }

    /*Calendar Method*/
}
