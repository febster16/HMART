package com.huawei.grocery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.grocery.model.Cart;
import com.huawei.grocery.model.Product;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureFactory;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult;

import java.io.Serializable;
import java.util.List;

public class ProductDetails extends AppCompatActivity {
    ImageView imageViewProduct, imageViewBack, btnCart, shoppingCart;
    TextView txtName, txtDesc, txtPrice, txtQuantity;
    Button btnBuyNow, quantity_minus, quantity_plus;
    List<Cart> cartList;
    List<Product> productList;
    Product product;
    int id, qty;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent i =getIntent();
        product = (Product) i.getSerializableExtra("item");
        id = product.getId();
        String name = product.getName();
        String desc = product.getDescription();
        String price = String.valueOf(product.getPrice());
        int quantity = i.getIntExtra("quantity",1);
        int image = product.getBgImageUrl();
        cartList = (List)i.getSerializableExtra("cart");
        productList = (List)i.getSerializableExtra("product");
        txtName = findViewById(R.id.lblPName);
        txtDesc = findViewById(R.id.lblPDesc);
        txtPrice = findViewById(R.id.lblPPrice);
        txtQuantity = findViewById(R.id.quantity);
        imageViewProduct= findViewById(R.id.imgProduct);
        imageViewBack = findViewById(R.id.imgBack);
        txtName.setText(name);
        txtPrice.setText(price);
        txtDesc.setText(desc);
        txtQuantity.setText(String.valueOf(quantity));
        imageViewProduct.setImageResource(image);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ProductDetails.this,MainActivity.class);
                intent.putExtra("cart", (Serializable)cartList);
                startActivity(intent);
                finish();
            }
        });

        shoppingCart = findViewById(R.id.cart);
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductDetails.this, CartActivity.class);
                i.putExtra("product", (Serializable)productList);
                i.putExtra("cart", (Serializable)cartList);
                i.putExtra("from", "product_details");
                startActivity(i);
            }
        });

        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                startCaptureActivity(callback);
            }
        });

        btnCart = findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(txtQuantity.getText().toString());
                if(qty==0){
                    Toast.makeText(ProductDetails.this ,"Amount must be at least 1", Toast.LENGTH_SHORT).show();
                }else{
                    int flag =0;
                    for(Cart cart : cartList){
                        if(cart.getProduct().getId() == id){
                            cart.setQuantity(cart.getQuantity()+qty);
                            flag=1;
                            break;
                        }
                    }

                    if(flag==0){
                        Cart cart = new Cart(product, qty);
                        cartList.add(cart);
                    }

                    Toast.makeText(ProductDetails.this, "Product added to cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        quantity_plus = findViewById(R.id.quantity_plus);
        quantity_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(txtQuantity.getText().toString());
                if (qty >= 1) {
                    int total_item = Integer.parseInt(txtQuantity.getText().toString());
                    total_item++;
                    txtQuantity.setText(total_item + "");
                }

            }
        });

        quantity_minus = findViewById(R.id.quantity_minus);
        quantity_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(txtQuantity.getText().toString());
                if (qty != 1) {
                    int total_item = Integer.parseInt(txtQuantity.getText().toString());
                    total_item--;
                    txtQuantity.setText(total_item + "");
                }

            }
        });
    }

    private void startCaptureActivity(MLBcrCapture.Callback callback) {
        MLBcrCaptureConfig config = new MLBcrCaptureConfig.Factory()
                .setResultType(MLBcrCaptureConfig.RESULT_ALL)
                .setOrientation(MLBcrCaptureConfig.ORIENTATION_AUTO)
                .create();
        MLBcrCapture bankCapture = MLBcrCaptureFactory.getInstance().getBcrCapture(config);
        bankCapture.captureFrame(this, callback);
    }

    private void requestPermission() {
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 111);
        }
    }

    private final MLBcrCapture.Callback callback = new MLBcrCapture.Callback() {
        @Override
        public void onSuccess(MLBcrCaptureResult mlBcrCaptureResult) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Organization Name: " + mlBcrCaptureResult.getOrganization());
            stringBuffer.append("\n");

            String cardNumber = "";
            for(int i = 0; i < mlBcrCaptureResult.getNumber().length(); i ++ ){
                if(i < 10) {
                    cardNumber += "*";
                } else {
                    cardNumber += mlBcrCaptureResult.getNumber().charAt(i);
                }
            }
            stringBuffer.append("Card Number: " + cardNumber);
            stringBuffer.append("\n");
            stringBuffer.append("Expiry Date: " + mlBcrCaptureResult.getExpire());
            stringBuffer.append("\n");
//            cardImg.setImageBitmap(mlBcrCaptureResult.getOriginalBitmap());

            AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
            builder.setMessage(stringBuffer);
            builder.setCancelable(true);
            builder.setTitle("Bank Information");
            builder.show();
        }

        @Override
        public void onCanceled() {
            Toast.makeText(ProductDetails.this, "Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int i, Bitmap bitmap) {
            Toast.makeText(ProductDetails.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDenied() {
            Toast.makeText(ProductDetails.this, "Denied", Toast.LENGTH_SHORT).show();
        }
    };

    private void createDialog(StringBuffer retrivedText) {
//        WindowManager wm = (WindowManager) MainActivity.this.getSystemService(Context.WINDOW_SERVICE);
//        final Dialog dialog = new Dialog(MainActivity.this, R.style);
//        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog);
//        Toast.makeText(ProductDetails.this, retrivedText, Toast.LENGTH_SHORT).show();

    }
}