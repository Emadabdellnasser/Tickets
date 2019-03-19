package com.example.emad.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ImageButton btNew;
    ImageButton btOld;
    int backButtonCount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btNew = (ImageButton) findViewById(R.id.newbt);
        btOld = (ImageButton) findViewById(R.id.oldbt);
        btNew.setOnClickListener(this);
        btOld.setOnClickListener(this);
        final Animation a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();
        final TextView rText = (TextView) findViewById(R.id.animatedText);
        rText.startAnimation(a);
        btNew.startAnimation(a);
        btOld.startAnimation(a);

    }

    @Override
       public void onClick(View v) {
        if(v==btNew)
        {
            Intent in=new Intent(MainActivity.this,NewsAcc.class);
            startActivity(in);

        }
        else
        {
           // Intent in=new Intent(MainActivity.this,NewsAcc.class);
            showingOrderConfirmationDialog();

        }

    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    private void showingOrderConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("you will call the support to add product");
        builder.setPositiveButton("phone", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "01152905444"));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Email", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:" + "emadnasser1515@gmail.com"));
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, " New product order");
                String Message = "Please i want to deal with u"
                        ;
                intent.putExtra(android.content.Intent.EXTRA_TEXT, Message);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
