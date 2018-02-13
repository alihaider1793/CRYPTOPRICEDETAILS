package com.zillion.crypto_price_details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class design_preview extends AppCompatActivity {

    private List<cryptoCurrency> cryptoCurrencyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private recyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_preview);

        recyclerView = (RecyclerView) findViewById(R.id.allCryptoCurrencies);
        mAdapter = new recyclerAdapter(cryptoCurrencyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        recyclerView.setAdapter(mAdapter);

        getDataInList();

//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.allCryptoCurrencies);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
    }
    private void getDataInList(){
        cryptoCurrency ccc = new cryptoCurrency();
        ccc.setSymbol("asdf");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("xyz");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("qwer");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("poiu");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("hjkl");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("xyz");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("qwer");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("poiu");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("hjkl");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("xyz");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("qwer");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("poiu");
        cryptoCurrencyList.add(ccc);

        ccc.setSymbol("hjkl");
        cryptoCurrencyList.add(ccc);

        mAdapter.notifyDataSetChanged();

    }
}
