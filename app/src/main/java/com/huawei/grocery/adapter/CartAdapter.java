package com.huawei.grocery.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.grocery.R;
import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    String Tag;
    int pQuantity = 1;
    List<Cart> cartList;
    List<Product> productList;
    String _quantity, _price, _attribute, _subtotal;

    public CartAdapter(List<Cart> cartList, List<Product> productList, Context context) {
        this.cartList = cartList;
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_row_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final Cart cart = cartList.get(position);
        holder.title.setText(cart.getProduct().getName());

        holder.attribute.setText(cart.getProduct().getUnit());
        holder.price.setText(String.valueOf(cart.getProduct().getPrice()));
        pQuantity = cart.getQuantity();
        holder.quantity.setText(String.valueOf(pQuantity));
        holder.subTotal.setText(String.valueOf(cart.getProduct().getPrice()*pQuantity));
        holder.imageView.setImageResource(cart.getProduct().getBgImageUrl());

//        Picasso.get()
//                .load(Utils.ProductImage + product.getBgImageUrl())
//                .into(holder.imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        holder.progressBar.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Log.d("Error : ", e.getMessage());
//                    }
//                });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pQuantity = Integer.parseInt(holder.quantity.getText().toString());
                if (pQuantity >= 1) {
                    int total_item = Integer.parseInt(holder.quantity.getText().toString());
                    total_item++;
                    holder.quantity.setText(total_item + "");
                    holder.subTotal.setText(String.valueOf(cart.getProduct().getPrice()*total_item));
                    cart.setQuantity(total_item);
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
                    holder.subTotal.setText(String.valueOf(cart.getProduct().getPrice()*total_item));
                    cart.setQuantity(total_item);
                }

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartList.size());
            }
        });


//
//        holder.addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                holder.addToCart.setVisibility(View.GONE);
//                holder.subTotal.setVisibility(View.VISIBLE);
//
//                _price = String.valueOf(product.getPrice());
//                _quantity = holder.quantity.getText().toString();
//                _attribute = product.getUnit();
//
//                if (Integer.parseInt(_quantity) != 0) {
//                    _subtotal = String.valueOf(Double.parseDouble(_price) * Integer.parseInt(_quantity));
//                    holder.subTotal.setText(_quantity + "X" + _price + "= $ " + _subtotal);
////                    if (context instanceof ViewAllProducts) {
////                        Cart cart = new Cart(product.getId(), product.getName(), product.getImage(), product.getCurrency(), _price, _attribute, _quantity, _subtotal);
////                        cartList = ((BaseActivity) context).getCartList();
////                        cartList.add(cart);
////                        String cartStr = gson.toJson(cartList);
////                        //Log.d("CART", cartStr);
////                        localStorage.setCart(cartStr);
////                        ((AddorRemoveCallbacks) context).onAddProduct();
////                        notifyItemChanged(position);
////                    }
//
//                    int flag =0;
//                    for(Cart cart : cartList){
//                        if(cart.getProduct().getId() == product.getId()){
//                            cart.setQuantity(cart.getQuantity()+Integer.parseInt(_quantity));
//                            flag=1;
//                            break;
//                        }
//                    }
//
//                    if(flag==0){
//                        Cart cart = new Cart(product, Integer.parseInt(_quantity));
//                    }
//
//                    Toast.makeText(context, "Product added to cart!", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(context, "Please Add Quantity", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {

        return cartList.size();
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
        Button plus, minus, remove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            quantity = itemView.findViewById(R.id.quantity);
            currency = itemView.findViewById(R.id.product_currency);
            attribute = itemView.findViewById(R.id.product_attribute);
            plus = itemView.findViewById(R.id.quantity_plus);
            minus = itemView.findViewById(R.id.quantity_minus);
            remove = itemView.findViewById(R.id.cart_remove);
            subTotal = itemView.findViewById(R.id.sub_total);
            price = itemView.findViewById(R.id.product_price);
        }
    }
}
