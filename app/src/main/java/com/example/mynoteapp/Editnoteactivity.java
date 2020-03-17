package com.example.mynoteapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Editnoteactivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText h,des;
    Button del,edit;
    ProgressBar epbar;
    FirebaseAuth fauth;

    public static final String extraheading="com.example.mynoteapp.extraheading";
    public static final String extradescription="com.example.mynoteapp.extradescription";
    public static final String extrauid="com.example.mynoteapp.extrauid";


    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private Spinner customspinner;
    private String ccode;
    private String edituid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnoteactivity);

        h=findViewById(R.id.heading_editnote);
        des=findViewById(R.id.description_editnote);
        edit=findViewById(R.id.editbutton_editnote);
        epbar=findViewById(R.id.edit_probar);
        del=findViewById(R.id.delnote_edit);

        h.setText(getIntent().getStringExtra(extraheading));
        des.setText(getIntent().getStringExtra(extradescription));
        edituid=getIntent().getStringExtra(extrauid);
        //Toast.makeText(this, "uid:"+edituid, Toast.LENGTH_SHORT).show();

        customspinner=findViewById(R.id.spinner_edit);

        ArrayList<customitem> customList = new ArrayList<>();
        customList.add(new customitem("Very Important", R.drawable.ic_remove_circle_black_24dp,"#FF021B"));
        customList.add(new customitem("Important", R.drawable.ic_orange_circle_black_24dp,"#FF8E1B"));
        customList.add(new customitem("Least", R.drawable.ic_blue_circle_black_24dp,"#8FE0FF"));
        customList.add(new customitem("I dont give a shit", R.drawable.ic_green_circle_black_24dp,"#9DFF8B"));

        customadapter customadapter= new customadapter(this,customList);
        if(customspinner!=null){
            customspinner.setAdapter(customadapter);
            customspinner.setOnItemSelectedListener(this);
        }


        //customspinner.setOnItemSelectedListener();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epbar.setVisibility(View.VISIBLE);

                calendar = Calendar.getInstance();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date = dateFormat.format(calendar.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);

                modelclass mc = new modelclass();
                String userid = fauth.getInstance().getCurrentUser().getUid();



                mc.setHead(h.getText().toString());
                mc.setDesc(des.getText().toString());
                mc.setDate(date.toString());
                mc.setDay(dayOfTheWeek.toString());
                mc.setCol(ccode);
                FirebaseFirestore.getInstance().collection("users").document(
                        "" + userid.trim()).collection("notes").document("" + edituid.trim()).update("head",mc.getHead());
                FirebaseFirestore.getInstance().collection("users").document(
                        "" + userid.trim()).collection("notes").document("" + edituid.trim()).update("desc",mc.getDesc());
                FirebaseFirestore.getInstance().collection("users").document(
                        "" + userid.trim()).collection("notes").document("" + edituid.trim()).update("date",mc.getDate());
                FirebaseFirestore.getInstance().collection("users").document(
                        "" + userid.trim()).collection("notes").document("" + edituid.trim()).update("day",mc.getDay());
                FirebaseFirestore.getInstance().collection("users").document(
                        "" + userid.trim()).collection("notes").document("" + edituid.trim()).update("col",mc.getCol());

                Toast.makeText(Editnoteactivity.this, "updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Editnoteactivity.this,MainActivity.class));
                finish();


            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                epbar.setVisibility(View.VISIBLE);

                String userid= fauth.getInstance().getCurrentUser().getUid();

                FirebaseFirestore.getInstance().collection("users").document
                        (""+userid.trim()).collection("notes").
                        document(""+edituid.trim()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Editnoteactivity.this, "deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Editnoteactivity.this,MainActivity.class));
                        finish();
                    }
                });
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        customitem item=(customitem) adapterView.getSelectedItem();
        //Toast.makeText(this, item.getSpinnerText(), Toast.LENGTH_SHORT).show();
        ccode=item.getColorcode();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
