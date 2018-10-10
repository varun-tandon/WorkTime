package com.eruliaf.homeworktime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    LinearLayout assignments;
    LinearLayout done;
    CheckBox standardCheckbox;
    float homeworkTime;
    TextView numHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        numHours = (TextView) findViewById(R.id.textView3) ;
        fab.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                GetHoursDialog d = new GetHoursDialog(MainActivity.this);
                d.show(getFragmentManager(), "hola");



            }
        });
        try{
            FileInputStream fis = getBaseContext().openFileInput("assignsav.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            ArrayList<String> asigns = (ArrayList<String>) is.readObject();
//            System.out.println(asigns);
            is.close();
            fis.close();
            fis = getBaseContext().openFileInput("timesav.dat");
            is = new ObjectInputStream(fis);
            ArrayList<Double> times = (ArrayList<Double>) is.readObject();
//            System.out.println(times);

            is.close();
            fis.close();
            for(String a: asigns){
                Storage.addAssignment(a, getBaseContext());
            }
            for(double d: times){
                Storage.addTime(d, getBaseContext());
            }
            for(int i = 0; i < asigns.size(); i++){
                addCheckBox(asigns.get(i));
                if(i == 0){
                    increaseTotalHours(times.get(i));
                }else {
                    increaseTotalHours(getCurrenttime() + times.get(i));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            File file = new File(getBaseContext().getFilesDir(), "assignsav.dat");
            File otherFile = new File(getBaseContext().getFilesDir(), "timesav.dat");
        }
    }

    public void addCheckBox(String text){

        assignments = (LinearLayout) findViewById(R.id.checkboxes);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                Color.BLACK,
                Color.RED,
                Color.BLACK,
                Color.BLUE
        };

        final ColorStateList myList = new ColorStateList(states, colors);
        final CheckBox check = new CheckBox(this.getBaseContext());

        check.setButtonTintList(myList);
//                check.setButtonDrawable(getBaseContext().getResources().getDrawable( R.drawable.checkbox_custom));
        check.setTextColor(Color.BLACK);
        check.setChecked(false);
        check.setText(text);
        assignments.addView(check);

        System.out.println("AT THIS TIME" + Storage.getTimes());
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                assignments.removeViewInLayout(check);
//                System.out.println(Storage.getAssignments());
//                System.out.println(Storage.getTimes());
               int remove = Storage.getAssignments().indexOf(check.getText().toString());
                Storage.removeAssignment(remove, getBaseContext());
                Storage.removeTime(remove, getBaseContext());
//                System.out.println(Storage.getAssignments());
//                System.out.println(Storage.getTimes());

                addUncheckbox(check.getText().toString());
                increaseTotalHours(getCurrenttime() - getCheckboxTime(check.getText().toString()));
            }
        });
    }
    public void addUncheckbox(String text){
        done = (LinearLayout) findViewById(R.id.uncheckboxes);

        int[][] states = new int[][] {
                new int[] { android.R.attr.state_checked}, // enabled
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[] {
                Color.BLACK,
                Color.RED,
                Color.BLACK,
                Color.BLUE
        };

        final ColorStateList myList = new ColorStateList(states, colors);
        final CheckBox check = new CheckBox(this.getBaseContext());

        check.setButtonTintList(myList);
//                check.setButtonDrawable(getBaseContext().getResources().getDrawable( R.drawable.checkbox_custom));
        check.setTextColor(Color.BLACK);
        check.setPaintFlags(check.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        check.setChecked(true);
        check.setText(text);
        done.addView(check);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                done.removeViewInLayout(check);
                addCheckBox(check.getText().toString());
                Storage.addAssignment(check.getText().toString(), getBaseContext());
                Storage.addTime(getCheckboxTime(check.getText().toString()), getBaseContext());
                increaseTotalHours(getCurrenttime() + getCheckboxTime(check.getText().toString()));

            }
        });
    }
    public void increaseTotalHours(double d){
        numHours.setText(Double.toString(d));
    }
    public double getCurrenttime(){
        return Double.parseDouble(numHours.getText().toString());
    }
    public static double getCheckboxTime(String s){
        int lastdash = s.lastIndexOf('-');
        int nextSpace = s.indexOf(' ', lastdash + 2);
        return Double.parseDouble(s.substring(lastdash + 2, nextSpace));
    }
}


