package com.example.emad.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

/**
 * Created by EMAD on 3/2/2018.
 */

public class Details extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMITION_TO_READ_EXTERNAL_STORAGE = 1;
    private static final int PICK_IMAGE_REQUEST = 0;
    Uri currentUri = null;
    EditText editTextName;
    EditText editTextprice;
    EditText password;

    boolean r = false;
    long recievedId;
    ProgressDialog pr;
    ImageButton decreasingQuantity;
    ImageButton increasingQuantity;
    Button selectPhotoBt;
    ImageView imageView;
    DatabaseReference dref;
    private StorageReference stref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Add new product");
        pr = new ProgressDialog(this);
        stref = FirebaseStorage.getInstance().getReference();
        dref = FirebaseDatabase.getInstance().getReference().child("Blog");

        editTextName = (EditText) findViewById(R.id.product_name);
        editTextprice = (EditText) findViewById(R.id.product_price);
        password = (EditText) findViewById(R.id.passowrd);


        imageView = (ImageView) findViewById(R.id.imagePlace);
       // selectPhotoBt.setOnClickListener(this);
        imageView.setOnClickListener(this);


    }

    public void posting() {
        pr.setMessage("loading....");
        pr.show();
        final String name = editTextName.getText().toString().toLowerCase().trim();
        final String price = editTextprice.getText().toString().toLowerCase().trim();
        final String pass = password.getText().toString().toLowerCase().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(pass)) {
            if (pass.contentEquals("2222") == true) {
                r = true;
                StorageReference fillpath = stref.child("Blog_Images").child(currentUri.getLastPathSegment());
                fillpath.putFile(currentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri doun = taskSnapshot.getDownloadUrl();

                        DatabaseReference newPost = dref.push();
                        newPost.child("nameCh").setValue(name);
                        newPost.child("priceCh").setValue(price);
                        newPost.child("image").setValue(doun.toString());
                        newPost.child("passCh").setValue(pass);
                        startActivity(new Intent(Details.this, NewsAcc.class));
                        pr.dismiss();
                    }
                });
            } else {
                password.setError("you shoud enter valid password or pay for order one");
            }

        } else {
            Toast.makeText(Details.this, "You must set all required fields", Toast.LENGTH_LONG).show();
            pr.dismiss();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        posting();

        return true;
    }


    private void openingImageSelector() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecting Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMITION_TO_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openingImageSelector();

                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            if (resultData != null) {
                currentUri = resultData.getData();
                imageView.setImageURI(currentUri);
                imageView.invalidate();
            }
        }
    }

    @Override
    public void onClick(View v) {
        openingImageSelector();
    }

    @Override
    public void onBackPressed() {
        if (r) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardBtClick =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        // Showing dialog  to inform that there are unsaved changes
        makesureOnSavingDataDialog(discardBtClick);
    }

    private void makesureOnSavingDataDialog(
            DialogInterface.OnClickListener discardBtClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("you didnot save the product");
        builder.setPositiveButton("discard", discardBtClick);
        builder.setNegativeButton("keep_editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
