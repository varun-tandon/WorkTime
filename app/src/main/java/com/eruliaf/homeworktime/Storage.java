package com.eruliaf.homeworktime;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Varun on 10/29/2016.
 */

public class Storage implements Serializable {
    private static ArrayList<String> assignments = new ArrayList<String>();
    private static ArrayList<Double> times = new ArrayList<Double>();


    public static void addAssignment(String assignment, final Context context){
        assignments.add(assignment);
        saveThis(context);

    }

    public static void addTime(double time, final Context context){
        times.add(time);
        saveThis(context);

//        System.out.println(times);
    }
    public static void removeAssignment(int index, final Context context){
        assignments.remove(index);
       saveThis(context);
    }
    public static void removeTime(int time, final Context context){
        times.remove(time);
        saveThis(context);

    }

    public static ArrayList<String> getAssignments(){
        return assignments;
    }
    public static ArrayList<Double> getTimes(){
        return times;
    }
    private static void saveThis(final Context c){
        System.out.println(assignments);
        System.out.println(times);
        try{
            FileOutputStream fos = c.openFileOutput("assignsav.dat", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(assignments);
            os.close();
            fos.close();
            fos =  c.openFileOutput("timesav.dat", Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(times);
            os.close();
            fos.close();
        }catch(IOException i){
            i.printStackTrace();
        }
    }
}

