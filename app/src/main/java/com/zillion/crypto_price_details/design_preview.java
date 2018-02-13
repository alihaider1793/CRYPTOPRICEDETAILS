package com.zillion.crypto_price_details;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class design_preview extends AppCompatActivity {

    private List<cryptoCurrency> cryptoCurrencyList = new ArrayList<cryptoCurrency>();
    private recyclerAdapter mAdapter;

    private String top20_url = "https://chasing-coins.com/api/v1/top-coins/20";
    private String details = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_preview);

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
                        cryptoCurrency temp = cryptoCurrencyList.get(position);
                        Toast.makeText(design_preview.this, ""+temp.getSymbol(), Toast.LENGTH_SHORT).show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        design_preview.AsyncDataClass asyncRequestObject = new design_preview.AsyncDataClass();
        asyncRequestObject.execute(top20_url);

    }

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
        for(int i = 0 ; i<cryptoCurrencyList.size();i++){
            Log.i("design", "decodeJSON: "+cryptoCurrencyList.get(i).getSymbol());
        }
        Log.i("design", "decodeJSON: "+cryptoCurrencyList);

        mAdapter.notifyDataSetChanged();
    }

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
//            showToast();
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
