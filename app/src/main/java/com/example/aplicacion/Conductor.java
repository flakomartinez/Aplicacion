package com.example.aplicacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Conductor extends AppCompatActivity {

    private DatabaseReference bdApp;

    TextView mail, rolin,daFinal;
    private String prueba;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor);
        /*prueba = "emailadmi@hoy.co";
        mail= (TextView) findViewById(R.id.correo);
        rolin = (TextView) findViewById(R.id.info2);
        daFinal = (TextView) findViewById(R.id.rol);

        bdApp = FirebaseDatabase.getInstance().getReference();*/


    }
}
