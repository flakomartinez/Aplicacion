package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private DatabaseReference bdApp;


    //Spinner Tipo;
    EditText Cedula;
    EditText Nombre;
    EditText Apellido1;
    EditText Apellido2;
    EditText Telefono;
    EditText Area;
    Button BtRegistrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bdApp= FirebaseDatabase.getInstance().getReference("Usuario");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Tipo=(Spinner) findViewById(R.id.tipo);
        Cedula=(EditText) findViewById(R.id.cedula);
        Nombre=(EditText) findViewById(R.id.nombre);
        Apellido1=(EditText) findViewById(R.id.apellido1);
        Apellido2=(EditText) findViewById(R.id.apellido2);
        Telefono=(EditText) findViewById(R.id.telefono);
        Area=(EditText) findViewById(R.id.area);
        BtRegistrar=(Button) findViewById(R.id.btregistrar);

        BtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registrarUsuario();
            }
        });

    }




    public void registrarUsuario(){

        //String tipos=Tipo.getSelectedItem().toString();
        String cedu=Cedula.getText().toString();
        String nom=Nombre.getText().toString();
        String apell1=Apellido1.getText().toString();
        String apell2=Apellido2.getText().toString();
        String tel=Telefono.getText().toString();
        String are=Area.getText().toString();


        if(!TextUtils.isEmpty(cedu)){

            String id =bdApp.push().getKey();
            Usuario usuarios = new Usuario(id, cedu, nom, apell1, apell2, tel, are);
            Toast.makeText( this, "Usuario registrado", Toast.LENGTH_LONG).show();
        }else{

            Toast.makeText( this, "Debe introducir cedula", Toast.LENGTH_LONG).show();

        }

    }


}


