package com.huawei.grocery.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.grocery.R;

import java.util.ArrayList;

public class AddressQueryAdapter extends RecyclerView.Adapter<AddressQueryAdapter.ViewHolder>{
    private Context context;
    private ArrayList<String> queryResults = new ArrayList<>();
    private ClickItemListener clickItemListener;

    public AddressQueryAdapter(Context context, ArrayList<String> queryResults, ClickItemListener clickItemListener) {
        this.context = context;
        this.queryResults = queryResults;
        this.clickItemListener = clickItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_address_query_adapter, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressQueryAdapter.ViewHolder holder, int position) {
        holder.address.setText(queryResults.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, queryResults.get(position), Toast.LENGTH_LONG).show();
                clickItemListener.onItemClick(queryResults.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return queryResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.address);
            relativeLayout= itemView.findViewById(R.id.addressAdapterLayout);
        }
    }
}