package com.soerdev.trackerroute;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class halpilih extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;

    EditText pilihtanggal;
    Button cari;

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(i, i1, i2);
                pilihtanggal.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halpilih);
        pilihtanggal = (EditText) findViewById(R.id.pilihtanggal);
        cari = (Button) findViewById(R.id.cari);


            tanggalan();

            cari.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String key = pilihtanggal.getText().toString();
                    Intent sc = new Intent(halpilih.this,RekapAbsenActivity.class);
                    sc.putExtra("cari",key);
                    startActivity(sc);

                }
            });

    }

    private void tanggalan() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        pilihtanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDateDialog();
                }
            }
        });
    }
}
