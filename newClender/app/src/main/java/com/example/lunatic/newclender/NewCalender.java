package com.example.lunatic.newclender;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by liufeng on 2017/7/14.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class NewCalender extends LinearLayout {
    private ImageView btnPre,btnNext;
    private TextView  txtDate;
    private GridView  Grid;
    private Calendar  curdate=Calendar.getInstance();

    public NewCalender(Context context) {
        super(context);
    }

    public NewCalender(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public NewCalender(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }
    private void initControl(Context context){
        bindContrl(context);
        bindControlEvent();
        renderCalendar();
    }

    private void bindContrl(Context context) {
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.calender_view,this);

        btnPre= (ImageView) findViewById(R.id.btnPre);
        btnNext= (ImageView) findViewById(R.id.btnNext);
        txtDate= (TextView) findViewById(R.id.txtDare);
        Grid= (GridView) findViewById(R.id.calender_Grid);
    }
    private void bindControlEvent() {
        btnPre.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                curdate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                curdate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });

    }

    private void renderCalendar() {
        SimpleDateFormat sdf=new SimpleDateFormat("MMM yyy");
        txtDate.setText(sdf.format(curdate.getTime()));

        ArrayList<Date> cell=new ArrayList<Date>();
        Calendar calender= (Calendar) curdate.clone();

        calender.set(calender.DAY_OF_MONTH,1);
        int preDays=calender.get(Calendar.DAY_OF_WEEK)-1;
        calender.add(Calendar.DAY_OF_MONTH,-preDays);

        int maxCellCount=6*7;
        while (cell.size()<maxCellCount){
            cell.add(calender.getTime());
            calender.add(Calendar.DAY_OF_MONTH,1);
        }
        Grid.setAdapter(new CalendarAdapter(getContext(),cell));
    }
    private  class CalendarAdapter extends ArrayAdapter<Date>{
         LayoutInflater inflater;

        /**
         * Constructor
         *
         * @param context  The current context.
         * @param dates The resource ID for a layout file containing a TextView to use when
         */
        public CalendarAdapter(Context context,ArrayList<Date> dates) {
            super(context, R.layout.calender_text_day,dates);
            inflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date=getItem(position);
            if(convertView ==null){
                convertView=inflater.inflate(R.layout.calender_text_day,parent,false);
            }
            int Day=date.getDay();
            ((TextView)convertView).setText(String.valueOf(Day));
            return convertView;
        }
    }

}
