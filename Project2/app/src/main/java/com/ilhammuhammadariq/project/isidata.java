package com.ilhammuhammadariq.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class isidata extends AppCompatActivity {
    EditText panjang,lebar,tinggi,volume;
    Button simpan,tampil;
    DatabaseReference reff;
    ukur ukur;
    long id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isidata);

        panjang = findViewById(R.id.tvpanjang);
        lebar   = findViewById(R.id.eTlebar);
        tinggi  = findViewById(R.id.eTtinggi);
        volume  = findViewById(R.id.eTvolume);
        simpan  = findViewById(R.id.btnSave);
        tampil  = findViewById(R.id.btndata);
        ukur = new ukur();
        reff    = FirebaseDatabase.getInstance().getReference().child("pengukuran");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    id=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(isidata.this,DataPengukuran.class);
                startActivity(data);
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pjg = Integer.parseInt(panjang.getText().toString().trim());
                int lbr = Integer.parseInt(lebar.getText().toString().trim());
                int tgi = Integer.parseInt(tinggi.getText().toString().trim());
                int vlm = Integer.parseInt(volume.getText().toString().trim());

                ukur.setPanjang(pjg);
                ukur.setLebar(lbr);
                ukur.setTinggi(tgi);
                ukur.setVolume(vlm);

                reff.child(String.valueOf(id++)).setValue(ukur);
                Toast.makeText(isidata.this, "Data telah tersimpan", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
