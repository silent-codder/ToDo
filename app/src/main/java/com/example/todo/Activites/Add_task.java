package com.example.todo.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
//import android.app.TimePickerDialog;
import com.allyants.notifyme.NotifyMe;
import com.example.todo.Fragments.TaskFragment;
import com.example.todo.Fragments.TimePickerFragment;
import com.example.todo.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

public class Add_task extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText title;
    EditText subPoints;

    TextView date,fromTime;

    LinearLayout reminder;

    Button save;


    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd;
    DatePickerDialog dpd;

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    private AdView adView;
    AdRequest adRequest;

   // FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        title = (EditText) findViewById(R.id.task_title);
        subPoints = (EditText) findViewById(R.id.task_sub_points);
        date = (TextView) findViewById(R.id.task_date);
        fromTime =(TextView) findViewById(R.id.from_time);
        //toTime = (TextView) findViewById(R.id.to_time);
//        time = (TextView) findViewById(R.id.t);
//        from = findViewById(R.id.from);
        //to = findViewById(R.id.to);

        save = (Button) findViewById(R.id.btn_save);
        reminder = (LinearLayout) findViewById(R.id.reminder);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseUser uid = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        adView = (AdView) findViewById(R.id.ad_view);
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

       // db = FirebaseFirestore.getInstance();


        dpd = DatePickerDialog.newInstance(
                Add_task.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        tpd = TimePickerDialog.newInstance(
                Add_task.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
               // now.get(Calendar.SECOND),
                false
        );



        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });



//        toTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Initialize a new time picker dialog fragment
//                DialogFragment dFragment1 = new TimePickerFragment();
//
//                // Show the time picker dialog fragment
//                dFragment1.show(getSupportFragmentManager(),"Time Picker");
////                isFromClicked = true;
//
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_title = title.getText().toString();
                String txt_subPoints = subPoints.getText().toString();
                String txt_date = date.getText().toString();
                String txt_time = fromTime.getText().toString();
                //String txt_toTime = toTime.getText().toString();
//                String formHour =  from.getText().toString();
//                String toHour = to.getText().toString();
//
//                int fHour = Integer.parseInt(formHour);
//                int tHour = Integer.parseInt(toHour);
//                int Hour = Math.abs(fHour - tHour);
//                int TotalHour = Hour / 60;

                if (TextUtils.isEmpty(txt_title)) {
                    Toast.makeText(Add_task.this, "Set title...", Toast.LENGTH_SHORT).show();
                } else{
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("Title", txt_title);
                    map.put("SubPoints", txt_subPoints);
                    map.put("Date", txt_date);
                    map.put("FromTime", txt_time);
                   // map.put("ToTime", txt_toTime);
                   // map.put("TotalHour" , TotalHour);

//                    db.collection("Tasks").add(map)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Toast.makeText(Add_task.this, "Task add successfully....", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Add_task.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    databaseReference.child("Users").child(user.getUid()).child("Tasks").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Task added successfully :)", Toast.LENGTH_SHORT).show();
                                 //startActivity(new Intent(Add_task.this, TaskFragment.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add_task.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    title.setText("");
                    date.setText("----");
                    fromTime.setText("----");
                    subPoints.setText("1.\n2.");
                  //  toTime.setText("click to set time");
                }
            }
        });




    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        now.set(Calendar.YEAR,year);
        now.set(Calendar.MONTH,monthOfYear);
        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);


        if (monthOfYear == 0){
            date.setText(dayOfMonth + "  " + "Jan" + "  " + year );
        }else if (monthOfYear == 1){
            date.setText(dayOfMonth + "  " + "Feb" + "  " + year );
        }else if (monthOfYear == 2){
            date.setText(dayOfMonth + "  " + "March" + "  " + year );
        }else if (monthOfYear == 3){
            date.setText(dayOfMonth + "  " + "April" + "  " + year );
        }else if (monthOfYear == 4){
            date.setText(dayOfMonth + "  " + "May" + "  " + year );
        }else if (monthOfYear == 5){
            date.setText(dayOfMonth + "  " + "June" + "  " + year );
        }else if (monthOfYear == 6){
            date.setText(dayOfMonth + "  " + "July" + "  " + year );
        }else if (monthOfYear == 7){
            date.setText(dayOfMonth + "  " + "Aug" + "  " + year );
        }else if (monthOfYear == 8){
            date.setText(dayOfMonth + "  " + "Sept" + "  " + year );
        }else if (monthOfYear == 9){
            date.setText(dayOfMonth + "  " + "Oct" + "  " + year );
        }else if (monthOfYear == 10){
            date.setText(dayOfMonth + "  " + "Nov" + "  " + year );
        }else if(monthOfYear == 11){
            date.setText(dayOfMonth + "  " + "Dec" + "  " + year );
        }

        tpd.show(getSupportFragmentManager(), "Timepickerdialog");


    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
        now.set(Calendar.MINUTE,minute);
        now.set(Calendar.SECOND,second);

      //  time.setText(hourOfDay+ "" +minute);

//        if (hourOfDay < 9 && minute < 9){
//            fromTime.setText("0"+hourOfDay + "0" + minute);
//        }else {
//              fromTime.setText(hourOfDay+ " : " +minute);
//        }

         //from.setText(hourOfDay+""+minute);

        if (hourOfDay > 12 ){
            fromTime.setText(hourOfDay+ "." +minute + " PM");
        }else {
            fromTime.setText(hourOfDay+ "." +minute + " AM");
        }
        Intent intent = new Intent(getApplicationContext(), TaskFragment.class);
        intent.putExtra("test","I am a String");
        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
                .title(title.getText().toString())
                //.content(subPoints.getText().toString())
                .color(249, 32, 47, 1)
                .led_color(249, 32, 47, 1)
                .time(now)
                //  .addAction(intent,"Reset",false)
                .key("test")
                //   .addAction(new Intent(),"Cancel",true,false)
                .addAction(intent,"Done")
                .large_icon(R.drawable.app)
                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=1")
                .build();

        Toast.makeText(getApplicationContext(),"Set Reminder !!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}