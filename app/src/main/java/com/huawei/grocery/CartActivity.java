package com.huawei.grocery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.huawei.grocery.adapter.AddressQueryAdapter;
import com.huawei.grocery.adapter.CartAdapter;
import com.huawei.grocery.adapter.ClickItemListener;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Category;
import com.huawei.grocery.model.Product;
import com.huawei.hms.framework.common.Utils;
import com.huawei.hms.site.api.SearchResultListener;
import com.huawei.hms.site.api.SearchService;
import com.huawei.hms.site.api.SearchServiceFactory;
import com.huawei.hms.site.api.model.AddressDetail;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.Site;
import com.huawei.hms.site.api.model.TextSearchRequest;
import com.huawei.hms.site.api.model.TextSearchResponse;
import com.huawei.hms.utils.Util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements ClickItemListener{
    private static int cart_count = 0;
    private static final String TAG = "CartActivity";
    private SearchService searchService;
//    TextView queryResult;
    EditText queryEt;
    private ArrayList<String> queryResults = new ArrayList<>();

    View progress;
    String from;
    Category category;
    List<Cart> cartList = new ArrayList<>();
    List<Product> productList = new ArrayList<>();
    CartAdapter mAdapter;
    ImageView back;
    private RecyclerView recyclerView;
    Button searchBtn, checkoutBtn;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Intent intent = getIntent();
        cartList = (List)intent.getSerializableExtra("cart");
        productList = (List)intent.getSerializableExtra("product");
        if(cartList.isEmpty()){
            setContentView(R.layout.cart_empty);
        }else{
            setContentView(R.layout.activity_cart);
            recyclerView = findViewById(R.id.cartView);
            checkoutBtn = findViewById(R.id.checkoutBtn);
            searchService = SearchServiceFactory.create(this);
            queryEt = findViewById(R.id.queryEt);
//            queryResult = findViewById(R.id.queryResult);
//            queryResult.setMovementMethod(ScrollingMovementMethod.getInstance());
            searchBtn = findViewById(R.id.searchBtn);

            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search();
                }

            });

            checkoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(queryEt.getText().equals("")){
                        Toast.makeText(CartActivity.this, "Please enter an address before checkout.", Toast.LENGTH_SHORT).show();
                    } else if (cartList.isEmpty()) {
                        Toast.makeText(CartActivity.this, "There is no item to checkout.", Toast.LENGTH_SHORT).show();
                    } else {
                        checkoutCart();
                    }
                }
            });

            setUpRecyclerView();

        }

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this, MainActivity.class);
                i.putExtra("cart", (Serializable)cartList);
                i.putExtra("product", (Serializable)productList);
                startActivity(i);
            }
        });

//        if(!cartList.isEmpty()) {
//            searchService = SearchServiceFactory.create(this, getApiKey());
//            input = findViewById(R.id.searchQuery);
//            result = findViewById(R.id.responseSearch);
//            search = findViewById(R.id.searchBtn);
//            search.setOnClickListener(this::search);
//        }

//        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
//        this.changeActionBarTitle(getSupportActionBar());
//        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        final Drawable upArrow = getResources().getDrawable( R.drawable.ic_arrow_back_green, null);
//        //upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
//        this.getSupportActionBar().setHomeAsUpIndicator(upArrow)

    }

//    private static String getApiKey() {
//        // get apiKey from AppGallery Connect
//        String apiKey = "CwEAAAAA5c7He1Sa/rd+gvtWY3gZ7aNHdfbqkxJp2ibIfyjKpgDlsn3AcKdg3EWHBZKs55qNcuCzMgfWuqFNlcvUR41vVyTQiQc=";
//
//        // need encodeURI the apiKey
//        try {
//            String encodedApiKey = URLEncoder.encode(apiKey, "utf-8");
//            return encodedApiKey;
//        } catch (UnsupportedEncodingException e) {
//            Log.e(TAG, "encode apikey error");
//            return null;
//        }
//    }
//
//    public void search(View view){
//        TextSearchRequest textSearchRequest = new TextSearchRequest();
//        textSearchRequest.setQuery(input.getText().toString());
//        searchService.textSearch(textSearchRequest, new SearchResultListener<TextSearchResponse>() {
//            @Override
//            public void onSearchResult(TextSearchResponse textSearchResponse) {
//                StringBuilder response = new StringBuilder("\n");
//                response.append("success\n");
//                int count =1;
//                AddressDetail addressDetail;
//                for(Site site :textSearchResponse.getSites()){
//                    addressDetail = site.getAddress();
//                    response.append(String.format(
//                            "[%s] name: %s, formatAddress: %s, country: %s, countryCode: %s \r\n",
//                            "" + (count++), site.getName(), site.getFormatAddress(),
//                            (addressDetail == null ? "" : addressDetail.getCountry()),
//                            (addressDetail == null? "" : addressDetail.getCountryCode())
//                    ));
//                    Log.d(TAG, "search result is : " +response);
//                    result.setText(response.toString());
//                }
//            }

//            @Override
//            public void onSearchError(SearchStatus searchStatus) {
//                Log.e(TAG, "onSearchError is: " +searchStatus.getErrorCode());
//            }
//        });
//    }

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

    private void setUpRecyclerView() {
        mAdapter = new CartAdapter(cartList, productList,CartActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void onToggleClicked(View view) {
        setUpRecyclerView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(CartActivity.this, MainActivity.class);
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

    public void search() {
        TextSearchRequest textSearchRequest = new TextSearchRequest();
        textSearchRequest.setQuery(queryEt.getText().toString());
        searchService.textSearch(textSearchRequest, new SearchResultListener<TextSearchResponse>() {
            @Override
            public void onSearchResult(TextSearchResponse textSearchResponse) {

//                StringBuilder response = new StringBuilder("\n");
//                response.append("success\n");
                int count = 1;
                AddressDetail addressDetail;
                for (Site site : textSearchResponse.getSites()) {
                    addressDetail = site.getAddress();
//                    response.append(String.format(
//                       "[%s] name: %s, formatAddress: %s, country: %s, countryCode: %s \r\n",
//                            "" + (count++), site.getName(), site.getFormatAddress(),
//                            (addressDetail == null ? "" : addressDetail.getCountry()),
//                            (addressDetail == null ? "" : addressDetail.getCountryCode())
//                    ));
                    queryResults.add(String.format(
                            "%s, %s, %s, %s \r\n", site.getName(), site.getFormatAddress(),
                            (addressDetail == null ? "" : addressDetail.getCountry()),
                            (addressDetail == null ? "" : addressDetail.getCountryCode())
                    ));
                }
//                Log.d(TAG, "search result is : " + response);
//                queryResult.setText(response.toString());
                addressQueryAdapter();

            }

            @Override
            public void onSearchError(SearchStatus searchStatus) {
                Log.e(TAG, "onSearchError is : " + searchStatus.getErrorCode());
            }
        });

    }

    public void checkoutCart() {
        StringBuffer buffer = new StringBuffer();
        int totalPrice = 0;

        for (Cart cart: cartList) {
            totalPrice += (cart.getQuantity() * cart.getProduct().getPrice());
        }
        for (Cart cart: cartList) {
            buffer.append("Item: " + cart.getProduct().getName() + ", " + cart.getQuantity() + " x " + cart.getProduct().getPrice() + "\n");
        }
        buffer.append("Address: $" + queryEt.getText() + "\n");
        buffer.append("Total Price: $" + totalPrice + "\n");

        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);

        builder.setMessage(buffer);
        builder.setCancelable(false);
        builder.setTitle("Successfully Checkout Items!");
        builder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        cartList.clear();
                        finish();
                        startActivity(getIntent());
                    }
                });
        builder.show();
    }

    private void addressQueryAdapter() {
        RecyclerView recyclerView = findViewById(R.id.queryResultRecyclerView);
        AddressQueryAdapter adapter = new AddressQueryAdapter(this, queryResults, (ClickItemListener) this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onItemClick(String selectedAddress) {
        queryEt.setText(selectedAddress);
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
