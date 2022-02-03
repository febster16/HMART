package com.huawei.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.huawei.grocery.adapter.AllProductsAdapter;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Category;
import com.huawei.grocery.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewAllProducts extends AppCompatActivity {
    private static int cart_count = 0;

    View progress;
    String prev;
    Category category;
    ImageView shoppingCart, back;
    List<Product> productList;
    List<Cart> cartList;
    AllProductsAdapter mAdapter;
    int catId=0;
    private RecyclerView recyclerView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_products);
//        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
//        this.changeActionBarTitle(getSupportActionBar());
//        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        final Drawable upArrow = getResources().getDrawable( R.drawable.ic_arrow_back_green, null);
//        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
//        this.getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Intent intent = getIntent();
        prev = intent.getStringExtra("prev");
        cartList = (List)intent.getSerializableExtra("cart");
        productList = (List)intent.getSerializableExtra("product");
        recyclerView = findViewById(R.id.product_rv);

        if(intent.hasExtra("catId")){
            catId = intent.getIntExtra("catId", 0);
            setUpGridRecyclerViewWithCat();
        }else{
            setUpGridRecyclerView();
        }

        shoppingCart = findViewById(R.id.shoppingCart);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewAllProducts.this, CartActivity.class);
                i.putExtra("product", (Serializable)productList);
                i.putExtra("cart", (Serializable)cartList);
                i.putExtra("from", "view_all_products");
                startActivity(i);
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (prev){
                    case "main":
                        Intent intent = new Intent(ViewAllProducts.this, MainActivity.class);
                        intent.putExtra("cart", (Serializable) cartList);
                        intent.putExtra("product", (Serializable) productList);
                        startActivity(intent);
                        break;
                    case "category":
                        Intent i = new Intent(ViewAllProducts.this, AllCategory.class);
                        i.putExtra("cart", (Serializable) cartList);
                        i.putExtra("product", (Serializable) productList);
                        startActivity(i);
                        break;
                }
            }
        });

    }

    private void hideProgressDialog() {
        progress.setVisibility(View.GONE);
    }

    private void showProgressDialog() {
        progress.setVisibility(View.VISIBLE);
    }

    private void changeActionBarTitle(ActionBar actionBar) {
        // Create a LayoutParams for TextView
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
        TextView tv = new TextView(getApplicationContext());
        // Apply the layout parameters to TextView widget
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        // Set text to display in TextView
        tv.setText("Products"); // ActionBar title text
        tv.setTextSize(20);

        // Set the text color of TextView to red
        // This line change the ActionBar title text color
        tv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        // Set the ActionBar display option
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        // Finally, set the newly created TextView as ActionBar custom view
        actionBar.setCustomView(tv);
    }


    private void setUpGridRecyclerView() {

        mAdapter = new AllProductsAdapter(productList, cartList,ViewAllProducts.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    private void setUpGridRecyclerViewWithCat() {

        List<Product> productByCatId = new ArrayList<>();

        for(Product product : productList){
            if(product.getCategory_id() == catId)
                productByCatId.add(product);
        }
        mAdapter = new AllProductsAdapter(productByCatId, productList, cartList,ViewAllProducts.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public void onToggleClicked(View view) {
        setUpGridRecyclerView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(ViewAllProducts.this, MainActivity.class);
                intent.putExtra("cart", (Serializable) cartList);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

//            case R.id.cart_action:
//                startActivity(new Intent(getApplicationContext(), CartActivity.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem menuItem = menu.findItem(R.id.cart_action);
//        menuItem.setIcon(Converter.convertLayoutToImage(ProductActivity.this, cart_count, R.drawable.ic_shopping_basket));
//        return true;
//    }


//    @Override
//    public void onAddProduct() {
//        cart_count++;
//        invalidateOptionsMenu();
//
//    }
//
//    @Override
//    public void onRemoveProduct() {
//        cart_count--;
//        invalidateOptionsMenu();
//
//    }

}
