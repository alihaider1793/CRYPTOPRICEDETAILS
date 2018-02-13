package com.zillion.crypto_price_details;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
//    private String top50_url = "https://chasing-coins.com/api/v1/top-coins/50";
    private String imageURI = "https://chasing-coins.com/api/v1/std/logo/";
    private String details = "" , priceFinal = "";

    ProgressDialog pg;

    TextView tv_day,tv_hour, tv_cap, tv_heat, tv_price;
    ImageView iv_logo;
    Button refresh_btn;

    //request code = 1 for getting top20 detail and 0 for converting currency.

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

        refresh_btn = (Button)findViewById(R.id.refresh);
        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        showDetails(position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        design_preview.AsyncDataClass asyncRequestObject = new design_preview.AsyncDataClass();
        asyncRequestObject.execute(top20_url);
    }


    private void convertCurrency() {
        try {
            URL url = new URL("https://www.exchange-rates.org/converter/USD/PKR/1");

            // Get the input stream through URL Connection
            URLConnection con = url.openConnection();
            InputStream is =con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            // read each line and write to System.out
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showDetails(int i){
        cryptoCurrency temp = cryptoCurrencyList.get(i);
        Picasso.with(design_preview.this).load(imageURI+""+temp.getSymbol()).into(iv_logo);
        tv_price.setText(temp.getPrice());
        tv_heat.setText(temp.getHeat());
        tv_hour.setText(temp.getHour());
        tv_day.setText(temp.getDay());
        tv_cap.setText(temp.getCap());
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
        showDetails(0);
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
                Toast.makeText(getApplicationContext(), "Server connection failed", Toast.LENGTH_LONG).show();
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
