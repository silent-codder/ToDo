package com.example.todo.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.todo.R;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Get a Calendar instance
        final Calendar calendar = Calendar.getInstance();
        // Get the current hour and minute
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);


        // TimePickerDialog Theme : THEME_DEVICE_DEFAULT_LIGHT
        TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,this,hour,minute,false);


        // Return the TimePickerDialog
        return tpd;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String am_pm = "";

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);

//        ((TextView)getActivity().findViewById(R.id.to)).setText( hourOfDay+""+minute);


        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";

//        ((TextView)getActivity().findViewById(R.id.to_time)).setText( strHrsToShow+"."+datetime.get(Calendar.MINUTE)+" "+am_pm );

    }
}
