package project.cn.edu.tongji.sse.nowfitness.view.UserView.CalendarView;

import com.haibin.calendarview.Calendar;

import java.util.ArrayList;
import java.util.Date;

public interface CalendarControlMethod {

    public void initCalendarView();

    public void setInitView();

    public Calendar getSchemeCalendar(int year, int month, int day, String text);

    public Date StringConvertToDate(String dateString);
}
