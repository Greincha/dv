package com.example.job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NouveauBIActivity extends AppCompatActivity {

    Button button;
    Bitmap bmp, scaledBitmap, signBitmap;
    Spinner Arrivéespinner, Départspinner, Typespinner, Villespinner;
    EditText Nom, Mail, Adresse, Com, Inter, Inter2, Inter3, Temps;
    Date dateObj;
    DateFormat dateFormat;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_biactivity);

        button = findViewById(R.id.button);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter( this,listGroup,listItem);

        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        scaledBitmap = Bitmap.createScaledBitmap(bmp, 230, 230,false);

        Arrivéespinner = findViewById(R.id.Arrivéespinner);
        Départspinner = findViewById(R.id.Départspinner);
        Typespinner = findViewById(R.id.Typespinner);
        Villespinner = findViewById(R.id.Villespinner);
        Nom = findViewById(R.id.editTextNom);
        Adresse = findViewById(R.id.editTextAdresse);
        Mail = findViewById(R.id.editTextMail);
        Com = findViewById(R.id.editTextCom);
        Inter = findViewById(R.id.editTextInter);
        Inter2 = findViewById(R.id.editTextInter2);
        Inter3 = findViewById(R.id.editTextInter3);
        Temps = findViewById(R.id.editTempspassé);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPDF();

    }

    private void createPDF() {

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                dateObj = new Date();

                if(Nom.getText().toString().length()==0 ||
                        Mail.getText().toString().length()==0 ||
                        Adresse.getText().toString().length()==0){
                    {
                        Toast.makeText(NouveauBIActivity.this, "Il manque des éléments", Toast.LENGTH_LONG).show();
                    }
                }else {


                    PdfDocument myPdfDocument = new PdfDocument();
                    Paint myPaint = new Paint();

                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

                    Canvas canvas = myPage1.getCanvas();

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(80f);
                    canvas.drawText("BON D'INTERVENTION", 300, 100, myPaint);

                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2);
                    canvas.drawRect(10, 200, myPageInfo1.getPageWidth() - 10, 480, myPaint);

                    canvas.drawBitmap(scaledBitmap, 10, 10, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    myPaint.setStyle(Paint.Style.FILL);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("Nom : "+Nom.getText(), 50, 300, myPaint);
                    canvas.drawText("Adresse :" +Adresse.getText(), 50, 380, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("Mail :" +Mail.getText(), 600, 300, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("" +Villespinner.getSelectedItem().toString(), 650, 380, myPaint);

                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2);
                    canvas.drawRect(10, 540, 1190, 770, myPaint);

                    myPaint.setStyle(Paint.Style.FILL);
                    myPaint.setColor(Color.BLACK);
                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("Type d'Intervention : " +Typespinner.getSelectedItem().toString(), 50, 580, myPaint);

                    dateFormat = new SimpleDateFormat( "dd.MM.yy");
                    myPaint.setTextSize(40f);
                    canvas.drawText( "Date :" +dateFormat.format(dateObj), 740, 580, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("Heure d'arrivée : " +Arrivéespinner.getSelectedItem().toString(), 50, 660, myPaint);
                    canvas.drawText("Heure de départ : " +Départspinner.getSelectedItem().toString(), 650, 660, myPaint);
                    canvas.drawText("Durée de l'intervention : " +Temps.getText()+" Heure", 50, 740, myPaint);

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(50f);
                    canvas.drawText("RAPPORT", myPageInfo1.getPageWidth() / 2, 880, myPaint);


                    myPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText( ""+Inter.getText(), 50, 970, myPaint);
                    canvas.drawText( ""+Inter2.getText(), 50, 1070, myPaint);
                    canvas.drawText( ""+Inter3.getText(), 50, 1170, myPaint);

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(50f);
                    canvas.drawText("COMMENTAIRE", myPageInfo1.getPageWidth() / 2, 1320, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText( ""+Com.getText(), 50, 1410, myPaint);

                    canvas.drawText("Signature PFC", 100, 1800, myPaint);
                    canvas.drawText("Signature Client", 700, 1800, myPaint);


                    SignaturePad signaturePad = findViewById(R.id.signature_pad);
                    ImageView imageView = findViewById(R.id.imageView);

                    Bitmap bitmap = signaturePad.getSignatureBitmap();
                    imageView.setImageBitmap(bitmap);

                    signBitmap = Bitmap.createScaledBitmap(imageView, 230, 230,false);

                    canvas.drawBitmap(signBitmap, 10, 10, myPaint);


                    myPdfDocument.finishPage(myPage1);


                    File file = new File(Environment.getExternalStorageDirectory(), "BI"+Nom.getText()+Typespinner.getSelectedItem()+dateFormat.format(dateObj)+".pdf");

                    Toast.makeText(NouveauBIActivity.this, "PDF Généré", Toast.LENGTH_LONG).show();


                    try {
                        myPdfDocument.writeTo(new FileOutputStream(file));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    myPdfDocument.close();

                }
            }
        });


                }
}