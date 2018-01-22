package com.ilho.jungssam;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ian.park on 2018. 1. 18..
 */

public class StudentListAdapter extends BaseAdapter {

    private LayoutInflater inflater = null;
    private Context mContext = null;
    private ViewHolder viewHolder = null;
    private ArrayList<StudentInfo> mStudentList= null;
/*
private StudentListListener mStudentListListener = null;
public interface StudentListListener {
public void onClickStudent(StudentInfo info);
}
*/

    public StudentListAdapter(@NonNull Context context, int resource, ArrayList<StudentInfo> studentList) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mStudentList = studentList;
    }

    @Override
    public int getCount() {
        return mStudentList.size();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = inflater.inflate(R.layout.list_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mName = (TextView) view.findViewById(R.id.list_name);
            viewHolder.mSchool = (TextView) view.findViewById(R.id.list_school);
            viewHolder.mYear = (TextView) view.findViewById(R.id.list_year);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        StudentInfo info = mStudentList.get(position);

        viewHolder.mName.setText(info.name);
        viewHolder.mSchool.setText(info.school);
        viewHolder.mYear.setText(info.year);

        return view;
    }

    class ViewHolder{
        public TextView mName = null;
        public TextView mSchool = null;
        public TextView mYear = null;
        public LinearLayout layout = null;
    }

    @Override
    public Object getItem(int i) {
        return mStudentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
