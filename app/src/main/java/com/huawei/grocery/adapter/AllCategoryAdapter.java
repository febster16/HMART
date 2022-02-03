package com.huawei.grocery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.grocery.ProductDetails;
import com.huawei.grocery.R;
import com.huawei.grocery.ViewAllProducts;
import com.huawei.grocery.model.AllCategoryModel;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Product;

import java.io.Serializable;
import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.AllCategoryViewHolder> {
    Context context;
    List<AllCategoryModel> categoryList;
    List<Product> productList;
    List<Cart> cartList;
    int catId = 0;
    String _quantity, _price, _attribute, _subtotal;

    public AllCategoryAdapter(List<Product> productList, List<Cart> cartList, List<AllCategoryModel> categoryList, Context context) {
        this.productList = productList;
        this.cartList = cartList;
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_category_row_items,parent,false);
        return new AllCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryViewHolder holder, int position) {
        holder.allCategoryImage.setImageResource(categoryList.get(position).getImageUrl());

        holder.allCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, ViewAllProducts.class);
                i.putExtra("prev", "category");
                i.putExtra("catId", categoryList.get(position).getId());
                i.putExtra("product", (Serializable) productList);
                i.putExtra("cart", (Serializable)cartList);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class AllCategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView allCategoryImage;

        public AllCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            allCategoryImage =itemView.findViewById(R.id.allCategoryImage);
        }
    }
}
