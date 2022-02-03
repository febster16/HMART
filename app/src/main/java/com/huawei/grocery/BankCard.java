package com.huawei.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BankCard extends AppCompatActivity {

    ImageView cardImg;
    TextView cardInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);

        cardImg = findViewById(R.id.cardImg);
        cardInfoTv = findViewById(R.id.cardInfoTv);

        Intent intent = getIntent();

        Bitmap bmp;
        if(intent.hasExtra("cardInfo")) {
            cardInfoTv.setText(intent.getStringExtra("cardInfo"));
        }

        if(intent.hasExtra("cardImg")) {
            byte[] byteArray = intent.getByteArrayExtra("cardImg");
            bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            cardImg.setImageBitmap(bmp);
        }
    }
}