package com.zillion.crypto_price_details;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class design_preview extends AppCompatActivity {

    private List<cryptoCurrency> cryptoCurrencyList = new ArrayList<cryptoCurrency>();
    private recyclerAdapter mAdapter;

    private String top20_url = "https://chasing-coins.com/api/v1/top-coins/20";
    private String imageURI = "https://chasing-coins.com/api/v1/std/logo/";
    private String convertURI = "https://chasing-coins.com/api/v1/convert/BTC/PKR";
    private String details = "";

    ProgressDialog pg;

    TextView tv_day,tv_hour, tv_cap, tv_heat, tv_price;
    ImageView iv_logo;
    ImageView refresh_btn, menu_btn,iv_calc;

    int currPosition = 0;
    //to get the current position of the recyclerview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_preview);


        tv_cap = (TextView)findViewById(R.id.cap_tv);
        tv_day = (TextView)findViewById(R.id.day_tv);
        tv_hour = (TextView)findViewById(R.id.hour_tv);
        tv_heat = (TextView)findViewById(R.id.heat_tv);
        tv_price = (TextView)findViewById(R.id.price_tv);
        iv_logo = (ImageView)findViewById(R.id.cryptoIcon);

        pg = new ProgressDialog(design_preview.this);
        pg.setMessage("LOADING DATA...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        menu_btn = (ImageView)findViewById(R.id.vertMenu);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMenu();
            }
        });

        refresh_btn = (ImageView) findViewById(R.id.refresh);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cryptoCurrencyList.clear();
                pg = new ProgressDialog(design_preview.this);
                pg.setMessage("UPDATING DATA...");
                pg.setCanceledOnTouchOutside(false);
                pg.show();
                design_preview.AsyncDataClass asyncDataClass1 = new design_preview.AsyncDataClass();
                asyncDataClass1.execute(top20_url);
            }
            });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.allCryptoCurrencies);
        mAdapter = new recyclerAdapter(cryptoCurrencyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        currPosition = position;
                        showDetails(currPosition);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        design_preview.AsyncDataClass asyncRequestObject = new design_preview.AsyncDataClass();
        asyncRequestObject.execute(top20_url);

        iv_calc = (ImageView)findViewById(R.id.calc);
        iv_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),calculator.class);
                if(!cryptoCurrencyList.isEmpty())
                {
                    intent.putExtra("cryptoSymbol",cryptoCurrencyList.get(currPosition).getSymbol());
                    intent.putExtra("cryptoPrice",cryptoCurrencyList.get(currPosition).getPrice());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(design_preview.this, "INTERNET IS REQUIRED TO USE THIS FEATURE", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void showDetails(int i){
        cryptoCurrency temp = cryptoCurrencyList.get(i);
        Picasso.with(design_preview.this).load(imageURI+""+temp.getSymbol()).into(iv_logo);
        tv_price.setText("$"+temp.getPrice());
        tv_heat.setText(temp.getHeat());
        if(temp.getHour().contains("-")){
            tv_hour.setTextColor(getResources().getColor(R.color.colorRed));
            tv_hour.setText(temp.getHour()+"%");
        }
        else{
            tv_hour.setTextColor(getResources().getColor(R.color.colorGreen));
            tv_hour.setText(temp.getHour()+"%");
        }
        if(temp.getDay().contains("-")){
            tv_day.setTextColor(getResources().getColor(R.color.colorRed));
            tv_day.setText(temp.getDay()+"%");
        }
        else{
            tv_day.setTextColor(getResources().getColor(R.color.colorGreen));
            tv_day.setText(temp.getDay()+"%");
        }
        tv_cap.setText("$"+temp.getCap());
    }

    //decodeJSON will decode the JSON and store the result in arrayList
    private void decodeJSON(){
        try {
            JSONObject jsonObject = new JSONObject(details);
            for(int i = 1; i<=20;i++){
                cryptoCurrency temp = new cryptoCurrency();
                temp.setSymbol(jsonObject.getJSONObject(""+i).get("symbol").toString());
                temp.setCap(jsonObject.getJSONObject(""+i).get("cap").toString());
                temp.setHour(jsonObject.getJSONObject(""+i).getJSONObject("change").get("hour").toString());
                temp.setDay(jsonObject.getJSONObject(""+i).getJSONObject("change").get("day").toString());
                temp.setPrice(jsonObject.getJSONObject(""+i).get("price").toString());
                temp.setHeat(jsonObject.getJSONObject(""+i).get("coinheat").toString());
                cryptoCurrencyList.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
        pg.dismiss();
        if(!cryptoCurrencyList.isEmpty())
            showDetails(currPosition);
    }



    //extra features of the app
    private void createMenu(){
        PopupMenu popupMenu = new PopupMenu(design_preview.this, menu_btn);
        popupMenu.getMenuInflater().inflate(R.menu.more_menu_popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.privacy_policy){
                    Toast.makeText(design_preview.this, "PLEASE WAIT", Toast.LENGTH_SHORT).show();
                } else if(id == R.id.rateus){
//                    Uri maketUri = Uri.parse("market://details?id=" + getPackageName());
//                    Log.i(TAG, "onMenuItemClick: URI FOR APP: "+maketUri);
//                    try {
//                        Uri marketUri = Uri.parse("market://details?id=" + getPackageName());
//                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, marketUri);
//                        startActivity(goToMarket);
//
//                    }catch (ActivityNotFoundException e){
//
//                        Uri playstoreUri = Uri.parse("https://play.google.com/apps/testing/com.zillion.cameraview.PSL(Pakistan Selfie League)");
//                        Intent goTOPlaystore = new Intent(Intent.ACTION_VIEW, playstoreUri);
//                        startActivity(goTOPlaystore);
//                    }
                    Toast.makeText(design_preview.this, "PLEASE WAIT", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.terms_of_use){
                    Toast.makeText(design_preview.this, "PLEASE WAIT", Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.shareApp){
                    Toast.makeText(design_preview.this, "PLEASE WAIT", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        popupMenu.show();
    }


    //Generate an HTTP request and fetch the data from the API
    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,5000);
            HttpConnectionParams.setSoTimeout(httpParameters,5000);
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);
            String jsonResult = "";

            try{
                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
            }catch (ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return jsonResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("") || result == null) {
                Toast.makeText(getApplicationContext(), "CONNECTION ERROR! PLEASE TRY AGAIN", Toast.LENGTH_LONG).show();
            }

            //josn result is stored in details
            details = result;
            decodeJSON();
        }

        private StringBuilder inputStreamToString(InputStream is){
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try{
                while((rLine = br.readLine())!= null){
                    answer.append(rLine);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return answer;
        }
    }
}