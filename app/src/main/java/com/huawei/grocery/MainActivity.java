package com.huawei.grocery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huawei.grocery.adapter.CategoryAdapter;
import com.huawei.grocery.adapter.DiscountedProductAdapter;
import com.huawei.grocery.adapter.RecentlyViewedAdapter;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Category;
import com.huawei.grocery.model.DiscountedProducts;
import com.huawei.grocery.model.Product;
import com.huawei.grocery.model.RecentlyViewed;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView discountedRecyclerView, categoryRecyclerView , recentlyViewedRecycler;
    DiscountedProductAdapter discountedProductAdapter;
    RecentlyViewedAdapter recentlyViewedAdapter;
    List<DiscountedProducts> discountedProductsList;
    List<RecentlyViewed> recentlyViewedList;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    List<Product> productList;
    List<Cart> cartList;
    ImageView allCategoryImageView, shoppingCart;
    Button viewAllProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        HwAds.init(this);

        BannerView adsView = findViewById(R.id.adsView);
        AdParam adParam = new AdParam.Builder().build();
        adsView.loadAd(adParam);


//        BannerView bannerView = new BannerView(this);
//        // testw6vs28auh3 is a dedicated test ad unit ID.
//        bannerView.setAdId("testw6vs28auh3");
//        bannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_360_57);
//        // Add BannerView to the layout.
//        RelativeLayout rootView = findViewById(R.id.root_view);
//        rootView.addView(bannerView);

        Intent intent = getIntent();
        if(intent.hasExtra("cart")) {
            cartList = (List)intent.getSerializableExtra("cart");
        }else{
            cartList = new ArrayList<>();
        }

//        discountedRecyclerView=findViewById(R.id.discountedRecycler);
//        //adding data to model
//        discountedProductsList =new ArrayList<>();
//        discountedProductsList.add(new DiscountedProducts(1,R.drawable.discountberry));
//        discountedProductsList.add(new DiscountedProducts(2,R.drawable.discountbrocoli));
//        discountedProductsList.add(new DiscountedProducts(3,R.drawable.discountmeat));
//        discountedProductsList.add(new DiscountedProducts(4,R.drawable.discountberry));
//        discountedProductsList.add(new DiscountedProducts(5,R.drawable.discountbrocoli));
//        discountedProductsList.add(new DiscountedProducts(6,R.drawable.discountmeat));
//        setDiscountedRecycler(discountedProductsList);

        categoryRecyclerView =findViewById(R.id.catagoryRecycler);
        //adding data to model
        categoryList =new ArrayList<>();
        categoryList.add(new Category(1,R.drawable.ic_veggies));
        categoryList.add(new Category(2,R.drawable.ic_fruits));
        categoryList.add(new Category(3,R.drawable.ic_juce));
        categoryList.add(new Category(4,R.drawable.ic_dairy));
        categoryList.add(new Category(5,R.drawable.ic_meat));
        categoryList.add(new Category(6,R.drawable.ic_fish));
        categoryList.add(new Category(7,R.drawable.ic_egg));
        categoryList.add(new Category(8,R.drawable.ic_drink));
        categoryList.add(new Category(9,R.drawable.ic_desert));
        categoryList.add(new Category(10,R.drawable.ic_salad));
        categoryList.add(new Category(11,R.drawable.ic_cookies));
        categoryList.add(new Category(12,R.drawable.ic_spices));

        allCategoryImageView =findViewById(R.id.allCategoryImage);
        allCategoryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AllCategory.class);
                intent.putExtra("cart", (Serializable) cartList);
                intent.putExtra("product", (Serializable) productList);
                startActivity(intent);
            }
        });

        productList =new ArrayList<>();
        productList.add(new Product(1,"Watermelon","Watermelon has high water content and also provide some fiber.",10,"KG",2, R.drawable.card4,R.drawable.b4));
        productList.add(new Product(2,"Papaya","Papaya has high water content and also provide some fiber.",5,"KG",2, R.drawable.card3,R.drawable.b3));
        productList.add(new Product(3,"Strawberry","Strawberry has high water content and also provide some fiber.",8,"KG",2, R.drawable.card2,R.drawable.b1));
        productList.add(new Product(4,"Kiwi","Kiwi has high water content and also provide some fiber.",7,"Pcs",2, R.drawable.card1,R.drawable.b2));
        productList.add(new Product(5,"Beef","Freshly cut lean beef high in protein and low in fat.",8,"KG",5, R.drawable.card5,R.drawable.b5));
        productList.add(new Product(6,"Salmon","Norwegian salmon fillet rich in antioxidants and omega-3.",6,"Pcs",5, R.drawable.card6,R.drawable.b6));
        productList.add(new Product(7,"Chicken","Free range organic chicken without added antibiotics",5,"KG",5, R.drawable.card7 ,R.drawable.b7));
        productList.add(new Product(8,"Purple Cabbage","Crisp and radiant in color rich in nutrients and fiber.",8,"Pcs",1, R.drawable.card8,R.drawable.b8));
        productList.add(new Product(9,"Spinach","Organic spinach packed with iron and boosts immune system.",6,"Bundle",1, R.drawable.card9,R.drawable.b9));
        productList.add(new Product(10,"Corn","Organic corn high in vitamin C content and good for eye health.",5,"Pcs",1, R.drawable.card10 ,R.drawable.b10));
        productList.add(new Product(11,"Mango Juice","Mango has high in antioxidants and may boost immunity.",8,"Pcs",3, R.drawable.card11 ,R.drawable.b11));
        productList.add(new Product(12,"Milk","Milk is a nutrient-rich liquid food produced by the mammary glands of mammals.",6,"Cup",4, R.drawable.card12 ,R.drawable.b12));
        productList.add(new Product(13,"Salmon","Salmon may support a healthy heart and brain function.",20,"KG",6, R.drawable.card13 ,R.drawable.b13));
        productList.add(new Product(14,"Chicken Eggs","Eggs contain a generous amount of vitamin A, for your skin and eye health.",2,"Pcs",7, R.drawable.card14 ,R.drawable.b14));
        productList.add(new Product(15,"Coca Cola","Soft drink.",4,"Pcs",8, R.drawable.card15 ,R.drawable.b15));
        productList.add(new Product(16,"Mango Popsicle","Ice cream!",6,"Pcs",9, R.drawable.card16 ,R.drawable.b16));
        productList.add(new Product(17,"Seafood Salad","A salad is a dish consisting of mixed pieces of food, typically with at least one raw ingredient.",15,"Pcs",10, R.drawable.card17 ,R.drawable.b17));
        productList.add(new Product(18,"The Cranberry","Soft cookies with macadamia and dried cranberry.",8,"Pcs",11, R.drawable.card18 ,R.drawable.b18));
        productList.add(new Product(19,"Black Pepper","Black pepper has high antioxidants and anti-inflammatory properties",10,"Pcs",12, R.drawable.card19 ,R.drawable.b19));
        viewAllProductsBtn = findViewById(R.id.viewAllProductsBtn);

        recentlyViewedRecycler =findViewById(R.id.recently_items);
        //adding data to model
        recentlyViewedList =new ArrayList<>();
        recentlyViewedList.add(new RecentlyViewed(productList.get(0)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(5)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(4)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(1)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(2)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(7)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(8)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(9)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(3)));
        recentlyViewedList.add(new RecentlyViewed(productList.get(6)));
        setRecentlyRecycler();
        setCategoryRecycler();


        viewAllProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllProducts.class);
                intent.putExtra("prev", "main");
                intent.putExtra("cart", (Serializable) cartList);
                intent.putExtra("product", (Serializable) productList);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        shoppingCart = findViewById(R.id.shoppingCart);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                i.putExtra("product", (Serializable)productList);
                i.putExtra("cart", (Serializable)cartList);
                startActivity(i);
            }
        });
    }

    private void setRecentlyRecycler() {
        RecyclerView.LayoutManager  layoutManager=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        recentlyViewedAdapter = new RecentlyViewedAdapter(this,recentlyViewedList, cartList);
        recentlyViewedRecycler.setAdapter(recentlyViewedAdapter);
    }

    private void setCategoryRecycler() {
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(productList, cartList, categoryList, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void setDiscountedRecycler(List<DiscountedProducts> dataList) {
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        discountedRecyclerView.setLayoutManager(layoutManager);
        discountedProductAdapter =new DiscountedProductAdapter(this,dataList);
        discountedRecyclerView.setAdapter(discountedProductAdapter);
    }

}