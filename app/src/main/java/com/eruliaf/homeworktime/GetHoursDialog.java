package com.eruliaf.homeworktime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * Created by varun on 10/9/16.
 */


@SuppressLint("ValidFragment")
public class GetHoursDialog extends DialogFragment {
   MainActivity m;
    private int[][] states = new int[][] {
            new int[] { android.R.attr.state_checked}, // enabled
            new int[] {-android.R.attr.state_enabled}, // disabled
            new int[] {-android.R.attr.state_checked}, // unchecked
            new int[] { android.R.attr.state_pressed}  // pressed
    };

    private int[] colors = new int[] {
            Color.BLACK,
            Color.RED,
            Color.BLACK,
            Color.BLUE
    };
    @SuppressLint("ValidFragment")
    public GetHoursDialog(MainActivity m){
        this.m = m;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialogbox, null);
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        EditText activityName = (EditText) GetHoursDialog.this.getDialog().findViewById(R.id.activityName);
                        double amountTime = Double.parseDouble(((EditText) GetHoursDialog.this.getDialog().findViewById(R.id.time)).getText().toString());
                        GetHoursDialog.this.getDialog().setContentView(R.layout.activity_main);

//                        System.out.println(((TextView)GetHoursDialog.this.getDialog().findViewById(R.id.textView3)));
                        double currentAmount = m.getCurrenttime();
                        System.out.println(currentAmount);
                        m.addCheckBox(activityName.getText().toString() + " - " + amountTime + " hours");
                        Storage.addAssignment(activityName.getText().toString() + " - " + amountTime + " hours", m.getBaseContext());
                        Storage.addTime(amountTime, m.getBaseContext());
                        if(currentAmount < 0){
//3
//  System.out.println(amountTime);
                            m.increaseTotalHours(amountTime);
//                            System.out.println("HOLA");
                        }else{
                           m.increaseTotalHours(currentAmount + amountTime);
                        }

//                        final ColorStateList myList = new ColorStateList(states, colors);
//
//                        final CheckBox check = new CheckBox(getActivity());
//                        check.setButtonTintList(myList);
////                       check.setButtonDrawable(getActivity().getResources().getDrawable(R.drawable.));
//                        check.setTextColor(Color.BLACK);
//                        check.setText(activityName.toString());
//                        LinearLayout assignments = (LinearLayout) GetHoursDialog.this.getDialog().findViewById(R.id.checkboxes);
//                        assignments.addView(check);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GetHoursDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    }

