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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NouveauDevisActivity extends AppCompatActivity {

    Button button;
    Bitmap bmp, scaledBitmap;
    Spinner alimentationspinner, Besoinspinner, Coef1spinner, Villespinner, Piecespinner, Piecespinner2;
    EditText Nom, Adresse, Tel, Mail, Année, Distance, Com, Com2, Com3, Largeur1, Largeur2, Longueur1, Longueur2, HSP1, HSP2, Delta1, Coef1;
    Date dateObj;
    DateFormat dateFormat;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String,List<String>> listItem;
    MainAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_devis);

        button = findViewById(R.id.button);
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        scaledBitmap = Bitmap.createScaledBitmap(bmp, 230, 230,false);
        expandableListView = findViewById(R.id.expandablelistview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter( this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        alimentationspinner = findViewById(R.id.alimentationspinner);
        Besoinspinner = findViewById(R.id.Besoinspinner);
        Villespinner = findViewById(R.id.Villespinner);
        Piecespinner = findViewById(R.id.Piecespinner);
        Piecespinner2 = findViewById(R.id.Piecespinner2);
        Nom = findViewById(R.id.editTextNom);
        Adresse = findViewById(R.id.editTextAdresse);
        Mail = findViewById(R.id.editTextMail);
        Tel = findViewById(R.id.editTextTel);
        Année = findViewById(R.id.editTextAnnée);
        Distance = findViewById(R.id.editTextDistance);
        Com = findViewById(R.id.editTextCom);
        Com2 = findViewById(R.id.editTextCom2);
        Com3 = findViewById(R.id.editTextCom3);
        Largeur1 = findViewById(R.id.editTextLargeur1);
        Largeur2 = findViewById(R.id.editTextLargeur2);
        Longueur1 = findViewById(R.id.editTextLongueur1);
        Longueur2 = findViewById(R.id.editTextLongueur2);
        HSP1 = findViewById(R.id.editTextHSP1);
        HSP2 = findViewById(R.id.editTextHSP2);
        Delta1 = findViewById(R.id.editTextDelta1);
        Coef1 = findViewById(R.id.editTextCoef1);




        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

        createPDF();

    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));
        listGroup.add(getString(R.string.group5));
        listGroup.add(getString(R.string.group6));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);
        for (String item : array) {
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group2);
        for (String item : array) {
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group3);
        for (String item : array) {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group4);
        for (String item : array) {
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group5);
        for (String item : array) {
            list5.add(item);
        }

        List<String> list6 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group6);
        for (String item : array) {
            list6.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        listItem.put(listGroup.get(5),list6);
        adapter.notifyDataSetChanged();


    }

    private void createPDF() {

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                dateObj = new Date();

                if(Nom.getText().toString().length()==0 ||
                        Adresse.getText().toString().length()==0 ||
                        Tel.getText().toString().length()==0 ||
                        Année.getText().toString().length()==0 ||
                        Mail.getText().toString().length()==0) {
                    Toast.makeText(NouveauDevisActivity.this, "VIDE", Toast.LENGTH_LONG).show();
                }else {


                    PdfDocument myPdfDocument = new PdfDocument();
                    Paint myPaint = new Paint();

                    PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                    PdfDocument.Page myPage1 = myPdfDocument.startPage(myPageInfo1);

                    Canvas canvas = myPage1.getCanvas();

                    myPaint.setTextAlign(Paint.Align.CENTER);
                    myPaint.setTextSize(80f);
                    canvas.drawText("FICHE D'AUDIT", myPageInfo1.getPageWidth() / 2, 100, myPaint);



                    dateFormat = new SimpleDateFormat( "dd.MM.yy");
                    myPaint.setTextSize(40f);
                    canvas.drawText( "Date :" +dateFormat.format(dateObj), 740, 240, myPaint);


                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2);
                    canvas.drawRect(10, 200, myPageInfo1.getPageWidth() - 10, 480, myPaint);

                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2);
                    canvas.drawRect(10, 560, 1190, 1200, myPaint);

                    canvas.drawLine( 10, 620, 1190, 615, myPaint);


                    canvas.drawBitmap(scaledBitmap, 10, 10, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    myPaint.setStyle(Paint.Style.FILL);
                    myPaint.setColor(Color.BLACK);
                    canvas.drawText("Nom : "+Nom.getText(), 50, 300, myPaint);
                    canvas.drawText("Adresse :" +Adresse.getText(), 50, 380, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("Mail :" +Mail.getText(), 50, 460, myPaint);
                    canvas.drawText("Tel :" +Tel.getText(), 740, 300, myPaint);

                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("" +Villespinner.getSelectedItem().toString(), 650, 380, myPaint);


                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("Année de la maison :" +Année.getText(), 50, 540, myPaint);
                    canvas.drawText("Besoin :" +Besoinspinner.getSelectedItem().toString(), 620, 540, myPaint);

                    myPaint.setTextSize(40.0f);
                    canvas.drawText("Alimentation :" +alimentationspinner.getSelectedItem().toString(), 50, 1360, myPaint);
                    canvas.drawText("Distance :" +Distance.getText()+" Mètres", 600, 1360, myPaint);


                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(45f);
                    canvas.drawText("Commentaire ", 500, 1450, myPaint);
                    canvas.drawText( ""+Com.getText(), 50, 1550, myPaint);
                    canvas.drawText( ""+Com2.getText(), 50, 1650, myPaint);
                    canvas.drawText( ""+Com3.getText(), 50, 1750, myPaint);


                    myPaint.setStyle(Paint.Style.STROKE);
                    myPaint.setStrokeWidth(2);
                    canvas.drawRect( 10, 1400, 1190, 1990, myPaint);


                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setStyle(Paint.Style.FILL);
                    myPaint.setTextSize(35.0f);
                    canvas.drawText("Pièce", 20, 600, myPaint);
                    canvas.drawText( "Largeur", 120, 600, myPaint );
                    canvas.drawText( "Longueur", 260, 600, myPaint);
                    canvas.drawText( "Superficie", 420, 600, myPaint);
                    canvas.drawText( "HSP", 600, 600, myPaint);
                    canvas.drawText( "Volume", 680, 600, myPaint);
                    canvas.drawText( "Delta", 820, 600, myPaint);
                    canvas.drawText( "Coef", 920, 600, myPaint);
                    canvas.drawText( "Besoin W", 1020, 600, myPaint);


                    float Lar1 = Float.parseFloat(Largeur1.getText().toString());
                    float Lon1 = Float.parseFloat(Longueur1.getText().toString());
                    float Superficie1 = Lar1 * Lon1;
                    float H1 = Float.parseFloat(HSP1.getText().toString());
                    float Vol1 = Superficie1 * H1;
                    float D1 = Float.parseFloat(Delta1.getText().toString());
                    float C1 = Float.parseFloat(Coef1.getText().toString());
                    float B1 = D1 * C1 * Vol1;


                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("" +Piecespinner.getSelectedItem().toString(), 20, 670, myPaint);
                    canvas.drawText(Largeur1.getText().toString(), 180, 670, myPaint);
                    canvas.drawText(Longueur1.getText().toString(), 310, 670, myPaint);
                    canvas.drawText(HSP1.getText().toString(), 600, 670, myPaint);
                    canvas.drawText(String.valueOf(Superficie1), 440, 670, myPaint);
                    canvas.drawText(String.valueOf(Vol1), 710, 670, myPaint);
                    canvas.drawText(Delta1.getText().toString(), 830, 670, myPaint);
                    canvas.drawText(Coef1.getText().toString(), 940, 670, myPaint);
                    canvas.drawText(String.valueOf(B1), 1050, 670, myPaint);

                    float Lar2 = Float.parseFloat(Largeur2.getText().toString());
                    float Lon2 = Float.parseFloat(Longueur2.getText().toString());
                    float Superficie2 = Lar2 * Lon2;
                    float H2 = Float.parseFloat(HSP2.getText().toString());
                    float Vol2 = Superficie2 * H2;
                    float B2 = D1 * C1 * Vol2;


                    myPaint.setTextAlign(Paint.Align.LEFT);
                    myPaint.setTextSize(40f);
                    canvas.drawText("" +Piecespinner2.getSelectedItem().toString(), 20, 740, myPaint);
                    canvas.drawText(Largeur2.getText().toString(), 180, 740, myPaint);
                    canvas.drawText(Longueur2.getText().toString(), 310, 740, myPaint);
                    canvas.drawText(HSP2.getText().toString(), 600, 740, myPaint);
                    canvas.drawText(String.valueOf(Superficie2), 440, 740, myPaint);
                    canvas.drawText(String.valueOf(Vol2), 710, 740, myPaint);
                    canvas.drawText(Delta1.getText().toString(), 830, 740, myPaint);
                    canvas.drawText(Coef1.getText().toString(), 940, 740, myPaint);
                    canvas.drawText(String.valueOf(B2), 1050, 740, myPaint);

                    canvas.drawText(Delta1.getText().toString(), 830, 810, myPaint);
                    canvas.drawText(Coef1.getText().toString(), 940, 810, myPaint);

                    canvas.drawText(Delta1.getText().toString(), 830, 880, myPaint);
                    canvas.drawText(Coef1.getText().toString(), 940, 880, myPaint);

                    canvas.drawText(Delta1.getText().toString(), 830, 950, myPaint);
                    canvas.drawText(Coef1.getText().toString(), 940, 950, myPaint);

                    canvas.drawText(Delta1.getText().toString(), 830, 1020, myPaint);
                    canvas.drawText(Coef1.getText().toString(), 940, 1020, myPaint);









                    myPdfDocument.finishPage(myPage1);


                    File file = new File(Environment.getExternalStorageDirectory(), "audit"+Nom.getText()+dateFormat.format(dateObj)+".pdf");

                    Toast.makeText(NouveauDevisActivity.this, "PDF Généré", Toast.LENGTH_LONG).show();


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