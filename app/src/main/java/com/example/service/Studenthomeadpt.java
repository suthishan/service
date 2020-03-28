package com.example.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;



public class Studenthomeadpt extends BaseAdapter {
    Context context;
    List<StudenthomeViewDataItem> viewall;

    LayoutInflater inflter;

    public Studenthomeadpt(Context applicationContext, List<StudenthomeViewDataItem> viewall) {
        this.context = applicationContext;
        this.viewall = viewall;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return viewall.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.studenthome, null);
        TextView college_name = (TextView) view.findViewById(R.id.college_name);
        TextView department = (TextView) view.findViewById(R.id.department);
        TextView event_name = (TextView) view.findViewById(R.id.event_name);
        TextView event_date = (TextView) view.findViewById(R.id.event_date);
        TextView registration_date = (TextView) view.findViewById(R.id.registration_date);
        TextView team_size=(TextView) view.findViewById(R.id.team_size);



        college_name.setText(viewall.get(i).college_name);
        department.setText(viewall.get(i).department);
        event_name.setText(viewall.get(i).event_name);
        event_date.setText(viewall.get(i).event_date);
        registration_date.setText(viewall.get(i).registration_date);
        team_size.setText(viewall.get(i).team_size);
        return view;


    }
}