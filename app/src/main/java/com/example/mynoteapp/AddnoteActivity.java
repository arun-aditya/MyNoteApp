package com.example.mynoteapp;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddnoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText head,desc;
    Button Addbtn;
    FirebaseFirestore db;
    ProgressBar pbar;
    FirebaseAuth fauth;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private Spinner customspinner;
    private String ccode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        head=findViewById(R.id.heading_addnote);
        desc=findViewById(R.id.description_addnote);
        Addbtn=findViewById(R.id.addbutton_addnote);
        pbar=findViewById(R.id.add_probar);

        db= FirebaseFirestore.getInstance();

        customspinner=findViewById(R.id.spinner_add);

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



        Addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbar.setVisibility(View.VISIBLE);

                calendar = Calendar.getInstance();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                date = dateFormat.format(calendar.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
                Date d = new Date();
                String dayOfTheWeek = sdf.format(d);


                modelclass pmodel=new modelclass();
                String userid= fauth.getInstance().getCurrentUser().getUid();

                DocumentReference newnoteref= db.collection("users").document(
                        ""+userid.trim()).collection("notes").document();


                pmodel.setHead(head.getText().toString());
                pmodel.setDesc(desc.getText().toString());
                pmodel.setDate(date.toString());
                pmodel.setDay(dayOfTheWeek.toString());
                pmodel.setCol(ccode);
                pmodel.setUid(newnoteref.getId());


                newnoteref.set(pmodel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddnoteActivity.this, "New note added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddnoteActivity.this,MainActivity.class));
                        }
                        else{
                            Toast.makeText(AddnoteActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        customitem item=(customitem) adapterView.getSelectedItem();
        Toast.makeText(this, item.getSpinnerText(), Toast.LENGTH_SHORT).show();
        ccode=item.getColorcode();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
