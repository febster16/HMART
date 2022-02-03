package com.huawei.grocery.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.grocery.ProductDetails;
import com.huawei.grocery.R;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.MyViewHolder> {

    List<Product> productList;
    List<Product> productByCatId = new ArrayList<>();
    Context context;
    String Tag;
    int catId=-1;
    int pQuantity = 1;
    List<Cart> cartList;
    String _quantity, _price, _attribute, _subtotal;

    public AllProductsAdapter(List<Product> productList, List<Cart> cartList, Context context) {
        this.productList = productList;
        this.cartList = cartList;
        this.context = context;
        for(Product product : productList){
            productByCatId.add(product);
        }
    }

    public AllProductsAdapter(List<Product> productByCatId, List<Product> productList, List<Cart> cartList, Context context) {
        this.productByCatId = productByCatId;
        this.productList = productList;
        this.cartList = cartList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_grid_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Product product = productByCatId.get(position);
        holder.title.setText(product.getName());
        holder.attribute.setText(product.getUnit());
        holder.price.setText(String.valueOf(product.getPrice()));
        holder.imageView.setImageResource(product.getBgImageUrl());

        holder.quantity.setText("1");

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pQuantity = Integer.parseInt(holder.quantity.getText().toString());
                if (pQuantity >= 1) {
                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
                    total_item++;
                    holder.quantity.setText(total_item + "");
                }

            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pQuantity = Integer.parseInt(holder.quantity.getText().toString());
                if (pQuantity != 1) {
                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
                    total_item--;
                    holder.quantity.setText(total_item + "");

                }

            }
        });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, ProductDetails.class);
                i.putExtra("item", product);
                i.putExtra("cart", (Serializable) cartList);
                i.putExtra("product", (Serializable) productList);
                i.putExtra("quantity", holder.quantity.getText().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _price = String.valueOf(product.getPrice());
                _quantity = holder.quantity.getText().toString();
                _attribute = product.getUnit();

                if (Integer.parseInt(_quantity) != 0) {
                    int flag =0;
                    for(Cart cart : cartList){
                        if(cart.getProduct().getId() == product.getId()){
                            cart.setQuantity(cart.getQuantity()+Integer.parseInt(_quantity));
                            flag=1;
                            break;
                        }
                    }

                    if(flag==0){
                        Cart cart = new Cart(product, Integer.parseInt(_quantity));
                        cartList.add(cart);
                    }

                    Toast.makeText(context, "Product added to cart!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Please Add Quantity", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    @Override
    public int getItemCount() {

        return productByCatId.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        ProgressBar progressBar;
        CardView cardView;
        TextView offer, currency, price, quantity, attribute, addToCart, subTotal;
        Button plus, minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            cardView = itemView.findViewById(R.id.card_view);
            currency = itemView.findViewById(R.id.product_currency);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.quantity);
            addToCart = itemView.findViewById(R.id.add_to_cart);
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            subTotal = itemView.findViewById(R.id.sub_total);
        }
    }
}
