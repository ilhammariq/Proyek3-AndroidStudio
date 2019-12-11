package com.ilhammuhammadariq.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DataPengukuran extends AppCompatActivity {
TextView panjang,lebar,tinggi,volume,beratd;
Button isi;
DatabaseReference reff;
SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengukuran);

        swipe = findViewById(R.id.swipe);
        panjang = findViewById(R.id.tvpanjang);
        lebar   = findViewById(R.id.tvlebar);
        tinggi  = findViewById(R.id.tvtinggi);
        volume  = findViewById(R.id.tvvolume);
        beratd  = findViewById(R.id.tvberatD);
        isi     = findViewById(R.id.btnisidata);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });

        reff = FirebaseDatabase.getInstance().getReference();
        Query lastdata = reff.child("Pengukuran").orderByKey().limitToLast(1);
        lastdata.addListenerForSingleValueEvent(   new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    String pjg = data.child("panjang").getValue().toString();
                    String lbr = data.child("lebar").getValue().toString();
                    String tgi = data.child("tinggi").getValue().toString();
                    String vlm = data.child("volume").getValue().toString();
                    String brtdmns = data.child("berat dimensi").getValue().toString();


                    panjang.setText(pjg +"\n cm");
                    lebar.setText(lbr + "\n cm");
                    tinggi.setText(tgi + "\n cm");
                    volume.setText(vlm + "\n cm^3");
                    beratd.setText(brtdmns + "\n gram");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        isi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isi = new Intent(DataPengukuran.this,MainActivity.class);
                startActivity(isi);
            }
        });




    }
}
