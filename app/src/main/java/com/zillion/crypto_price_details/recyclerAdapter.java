package com.zillion.crypto_price_details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haide on 2/13/2018.
 */

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {


    private List<cryptoCurrency> cryptoCurrencies;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView sym_tv;


        public MyViewHolder(View view) {
            super(view);
            sym_tv = (TextView) view.findViewById(R.id.crptoSym);
        }
    }

    public recyclerAdapter(List<cryptoCurrency> cryptoCurrencies) {
        this.cryptoCurrencies = cryptoCurrencies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        cryptoCurrency cc = cryptoCurrencies.get(position);
        holder.sym_tv.setText(cc.getSymbol());
    }

    @Override
    public int getItemCount() {
        return cryptoCurrencies.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}