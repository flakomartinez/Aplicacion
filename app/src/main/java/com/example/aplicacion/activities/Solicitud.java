package com.example.aplicacion.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicacion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class Solicitud extends AppCompatActivity {

    private DatabaseReference bdApp;
    private FirebaseAuth autenticacion;

    public EditText txtOrigen;
    public EditText txtDestino;
    public EditText txtFechaSalida;
    public EditText txtHoraSalida;
    public EditText txtHoraLLegada;
    public EditText txtNoPasajeros;
    public Button btnSolicitar;
    public String origen, destino, fechaSalida, horaSalida, horaLlegada, noPasajeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);


        txtOrigen = (EditText) findViewById(R.id.campoOrigen);
        txtDestino = (EditText) findViewById(R.id.campoDestino);
        txtFechaSalida = (EditText) findViewById(R.id.campoFecha);
        txtHoraSalida = (EditText) findViewById(R.id.campoHoraSalida);
        txtHoraLLegada = (EditText) findViewById(R.id.campoHoraLlegada);
        txtNoPasajeros = (EditText) findViewById(R.id.campoNoPasajeros);

        btnSolicitar = (Button) findViewById(R.id.btnSolicitar);





        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origen=txtOrigen.getText().toString().trim();
                destino=txtDestino.getText().toString().trim();
                fechaSalida=txtFechaSalida.getText().toString().trim();
                horaSalida=txtHoraSalida.getText().toString().trim();
                horaLlegada=txtHoraLLegada.getText().toString().trim();
                noPasajeros=txtNoPasajeros.getText().toString().trim();




                if(!origen.isEmpty()&& !destino.isEmpty() && !fechaSalida.isEmpty() && !horaSalida.isEmpty() && !noPasajeros.isEmpty()){

                    realizarSolicitud();
                }
                else {
                    Toast.makeText(Solicitud.this,"Debe completar los campos ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }







    public void realizarSolicitud(){


        final String origen= txtOrigen.getText().toString();
        final String destino= txtDestino.getText().toString();
        final String fechaSalida= txtFechaSalida.getText().toString();
        final String horaSalida= txtHoraSalida.getText().toString();
        final String horaLlegada= txtHoraLLegada.getText().toString();
        final String noPasajeros= txtNoPasajeros.getText().toString();


                    Map<String, Object> informacion = new HashMap<>();

                    informacion.put("origen", origen);
                    informacion.put("destino", destino);
                    informacion.put("fechaSalida", fechaSalida);
                    informacion.put("horaSalida", horaSalida);
                    informacion.put("HoraLlegada", horaLlegada);
                    informacion.put("noPasajeros", noPasajeros);


                    String id=autenticacion.getCurrentUser().getUid();
                    bdApp.child("Solicitud").child(id).setValue(informacion).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task1) {
                            if(task1.isSuccessful()){
                                Toast.makeText(Solicitud.this,"Logueado y guardado en realtime", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else {
                                Toast.makeText(Solicitud.this,"No se pudieron crear lo datos en la bd", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    Toast.makeText(Solicitud.this,"Registrado correctamente", Toast.LENGTH_LONG).show();
                }

                //progreso.dismiss();
            }
