package com.example.mynoteapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements rviewadapter.onNoteClick, TimePickerDialog.OnTimeSetListener {
    Toolbar toolbar;
    RecyclerView verticalrecycle;
    private FirebaseAuth mauth;
    rviewadapter vadapt;
    FirestoreRecyclerOptions<modelclass> options;
    FloatingActionButton fadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.textView4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MY NOTES");


        mauth = FirebaseAuth.getInstance();
        String uid = mauth.getInstance().getCurrentUser().getUid();
        CollectionReference cf = FirebaseFirestore.getInstance().collection("users").document("" + uid.trim()).collection("notes");
        Query query = cf;
        options = new FirestoreRecyclerOptions.Builder<modelclass>().setQuery(query, modelclass.class).build();

        fadd = findViewById(R.id.home_addbtn);
        fadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddnoteActivity.class)
                );
            }
        });
        verticalrecycle = findViewById(R.id.recyclerview1);
        verticalrecycle.setHasFixedSize(true);
        verticalrecycle.setLayoutManager(new GridLayoutManager(this, 2));
        vadapt = new rviewadapter(options, this);
        vadapt.notifyDataSetChanged();
        vadapt.startListening();
        verticalrecycle.setAdapter(vadapt);

    }

    @Override
    public void onNoteItemSelected(int position, final modelclass mclass, View view) {


        ImageView picture = (ImageView) view.findViewById(R.id.notbell);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                DialogFragment timepicker = new Timepickerfragment();
                timepicker.show(getSupportFragmentManager(), "timepicker");

            }
        });


        Intent intent = new Intent(MainActivity.this, Editnoteactivity.class);
        intent.putExtra(Editnoteactivity.extraheading, mclass.getHead());
        intent.putExtra(Editnoteactivity.extradescription, mclass.getDesc());
        intent.putExtra(Editnoteactivity.extrauid, mclass.getUid());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_menu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Toast.makeText(this, "alarm set", Toast.LENGTH_SHORT).show();
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,minute);
        c.set(Calendar.SECOND,0);

        startalarm(c);

    }

    private void startalarm(Calendar c){
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,alertreciever.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }

}


