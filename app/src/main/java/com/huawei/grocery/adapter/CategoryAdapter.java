package com.huawei.grocery.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.huawei.grocery.R;
import com.huawei.grocery.ViewAllProducts;
import com.huawei.grocery.model.AllCategoryModel;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Category;
import com.huawei.grocery.model.Product;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<Category> categoryList;
    List<Product> productList;
    List<Cart> cartList;

    public CategoryAdapter(List<Product> productList, List<Cart> cartList, List<Category> categoryList, Context context) {
        this.productList = productList;
        this.cartList = cartList;
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_row_items, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        holder.categoryImage.setImageResource(categoryList.get(position).getImageurl());

        holder.categoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productList.isEmpty()){
                    Toast.makeText(context, "product list is empty", Toast.LENGTH_SHORT).show();
                }else{
                    Intent i= new Intent(context, ViewAllProducts.class);
                    i.putExtra("prev", "main");
                    i.putExtra("catId", categoryList.get(position).getId());
                    i.putExtra("product", (Serializable) productList);
                    i.putExtra("cart", (Serializable)cartList);
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.allCategoryImage);
        }
    }
}
