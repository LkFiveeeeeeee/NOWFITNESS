package project.cn.edu.tongji.sse.nowfitness.view.UserView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import project.cn.edu.tongji.sse.nowfitness.R;
import project.cn.edu.tongji.sse.nowfitness.presenter.UserViewPresenter;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.CalendarControlMethod;
import project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView.ConstantColor;


public class UserView extends AppCompatActivity implements CalendarControlMethod, userViewMethod{

    private UserViewPresenter userViewPresenter;

    /*Calendar Para*/
    private TextView monthDay;
    private TextView yearDay;
    private TextView lunarDay;
    private TextView currentDay;
    private CalendarView calendarView;
    /*Calendar Para*/


    /*others Para*/
    private CircleImageView avatarImageView;
    private LinearLayout momentLayout;
    private LinearLayout followLayout;
    private LinearLayout fansLayout;
    private TextView momentNum;
    private TextView fansNum;
    private TextView followersNum;
    private EditText hightNum;
    private EditText weightNum;
    private TextView BMINum;
    /*others Para*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userview);
        userViewPresenter = new UserViewPresenter(this);
        userViewPresenter.initView();
    }

    public void initView(){
        setActionBar();
        initUserView();
        initCalendarView();
    }

    /*Calendar Method*/

    private void setActionBar(){
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
    }

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
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 3, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 3, " "));
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 4, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 4, " "));
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 5, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 5, " "));
        map.put(getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 6, " ").toString(),
                getSchemeCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 6, " "));

        calendarView.setSchemeDate(map);

    }

    @Override
    public Calendar getSchemeCalendar(int year, int month, int day, String text) {
        Random random = new Random();
        int color = ConstantColor.color[random.nextInt(100) % ConstantColor.color.length];

        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        return calendar;
    }

    /*Calendar Method*/


    /*UserView Method*/
    @Override
    public void initUserView() {
        avatarImageView = findViewById(R.id.avatar);
        momentLayout = findViewById(R.id.indimoment);
        followLayout = findViewById(R.id.indifollow);
        fansLayout = findViewById(R.id.indifans);
        momentNum = findViewById(R.id.momentnum);
        fansNum = findViewById(R.id.fansnum);
        followersNum = findViewById(R.id.followersnum);
        hightNum = findViewById(R.id.height_view);
        weightNum = findViewById(R.id.weight_view);
        BMINum = findViewById(R.id.bmiview);
        setLisenter();
    }

    @Override
    public void setBMINum() {
        if(hightNum.getText().equals("")||weightNum.getText().equals("")){
            return;
        }else{
            //TODO calculate BMI number
        }
    }

    @Override
    public void setLisenter() {
        momentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Intent
            }
        });
        followLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Intent
            }
        });
        fansLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Intent
            }
        });
        hightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBMINum();
            }
        });
        weightNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBMINum();
            }
        });
    }
    /*UserView Method*/
}
