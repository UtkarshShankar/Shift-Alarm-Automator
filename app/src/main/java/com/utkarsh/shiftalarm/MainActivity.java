package com.utkarsh.shiftalarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btn,btn2;
    Spinner spinner;
    TextView textView;
    TimePickerDialog timePickerDialog;
    Calendar calendar;
    String ampm="AM";
//    String[] shifts={"A","B","C"};
    int currHr,currMin,a,b,c=0,d;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        textView=findViewById(R.id.textView1);
        btn2=findViewById(R.id.btn2);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.shift_options,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if(checkSelfPermission(Manifest.permission.SET_ALARM)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.SET_ALARM},1);
        }


        btn.setOnClickListener(view -> {
            calendar =Calendar.getInstance();
            currHr=calendar.get(Calendar.HOUR_OF_DAY);
            if(currHr>12)
            {
                currHr=currHr-12;
            }
            currMin=calendar.get(Calendar.MINUTE);
            timePickerDialog =new TimePickerDialog(MainActivity.this, (timePicker, i, i1) -> {
                if(i>=12)
                {
                    ampm="PM"; }
                if(i>12)
                {
                    i=i-12;
                }
                else if(i==0)
                {
                    i=12;
                }
                if(i1<10)
                {
                    textView.setText(i+":0"+i1+" "+ampm);

                }
                else
                {
                    textView.setText(i+":"+i1+" "+ampm);
                }
                c=i;
                d=i1;
                a=i-currHr;
                b=i1-currMin;
            },currHr,currMin,false);
            timePickerDialog.show();



            Toast.makeText(getApplicationContext(),"Alarm set for "+a+" hours "+b+" minutes" ,Toast.LENGTH_LONG).show();
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
                intent.setAction("com.utkarsh.alarm");
                intent.putExtra("My message", " hi this is from 1st activity");
                PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),0,intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY,pendingIntent);
                Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),"Selected",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}