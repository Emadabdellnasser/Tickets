package com.example.emad.myapplication;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by EMAD on 3/8/2018.
 */

public class NewsAcc extends AppCompatActivity implements View.OnClickListener {


    Button btAdd;
    DatabaseReference dref;
    private RecyclerView rec;
    TextView emptytxt;
    ProgressDialog pr;

    // private Query mQuery=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newacc);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        emptytxt = (TextView) findViewById(R.id.emptyview);
        pr = new ProgressDialog(this);
        dref = FirebaseDatabase.getInstance().getReference().child("Blog");

        rec = (RecyclerView) findViewById(R.id.mainData);

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.addOnItemTouchListener(
                new RecyclerItemClickListener(NewsAcc.this, rec, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Button buy = (Button) view.findViewById(R.id.buttonBuy);
                        buy.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
//                                Intent in = new Intent(NewsAcc.this, Buy.class);
//                                startActivity(in);
//                                return true;
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" +"146454"));
                                startActivity(intent);
                                return true;
                            }
                        });

                    }


                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewsAcc.this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                   // String s=null;

                   Query Q = dref.orderByChild("nameCh").startAt(newText).endAt("~");
                   // Query Q = dref.orderByChild("nameCh").startAt("[a-zA-Z0-9]*").endAt(newText);

                    FirebaseRecyclerAdapter<Object_Class, NewsAcc.recycleClass> fireRec = new FirebaseRecyclerAdapter<Object_Class, NewsAcc.recycleClass>(Object_Class.class, R.layout.list_item, NewsAcc.recycleClass.class, Q)

                    {
                        @Override
                        protected void populateViewHolder(NewsAcc.recycleClass viewHolder, Object_Class model, int position) {

                            viewHolder.setTitle(model.getNameCh());
                            viewHolder.setPrice(model.getPriceCh());
                            viewHolder.setImag(getApplicationContext(), model.getImage());
                            pr.dismiss();


                        }
                    };

                    rec.setAdapter(fireRec);


                    return false;
                }
            });
        }
        return true;

    }

    @Override
    protected void onStart() {
        super.onStart();
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //checking network connection
        if (networkInfo != null && networkInfo.isConnected()) {
            //loading data

            pr.setMessage("loading....");

            pr.show();
            FirebaseRecyclerAdapter<Object_Class, NewsAcc.recycleClass> fireRec = new FirebaseRecyclerAdapter<Object_Class, NewsAcc.recycleClass>(Object_Class.class, R.layout.list_item, NewsAcc.recycleClass.class, dref)

            {

                @Override
                protected void populateViewHolder(NewsAcc.recycleClass viewHolder, Object_Class model, int position) {


                    viewHolder.setTitle(model.getNameCh());
                    viewHolder.setPrice(model.getPriceCh());
                    viewHolder.setImag(getApplicationContext(), model.getImage());
                    pr.dismiss();


                }
            };

            rec.setAdapter(fireRec);


        } else {
            emptytxt.setText("check your network connection");
        }

    }


    @Override
    public void onClick(View v) {
        if (v == btAdd) {

            // Intent in=new Intent(MainActivity.this,Details.class);

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_new_post) {
            startActivity(new Intent(NewsAcc.this, Details.class));

        }


        return super.onOptionsItemSelected(item);
    }


    public static class recycleClass extends RecyclerView.ViewHolder {
        View mv;
        Context con;

        public recycleClass(View itemView) {
            super(itemView);
            mv = itemView;
        }


        public void setTitle(String title) {
            TextView txtName = (TextView) mv.findViewById(R.id.productname);
            txtName.setText(title);

        }

        public void setPrice(String price) {
            TextView txtprice = (TextView) mv.findViewById(R.id.productprice);
            txtprice.setText(price);
        }

        public void setImag(Context ctx, String imag) {
            ImageView im = (ImageView) mv.findViewById(R.id.imagespace);
            Picasso.with(ctx).load(imag).into(im);
        }


    }


}
