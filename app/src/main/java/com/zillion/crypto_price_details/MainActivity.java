package com.zillion.crypto_price_details;

import android.support.v7.app.AppCompatActivity;

//
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.squareup.picasso.Picasso;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
public class MainActivity extends AppCompatActivity {
//
//    private String app_server_url = "https://blockchain.info/ticker";
//    private String top20_url = "https://chasing-coins.com/api/v1/top-coins/20";
//    private String imageURI = "https://chasing-coins.com/api/v1/std/logo/";
//
//    Button btn;
//    TextView symbol, cap, hour, day, price, heat;
//    ImageView crypto_icon;
//
//    //details for storing the json
//    String details = "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        AsyncDataClass asyncRequestObject = new AsyncDataClass();
//        asyncRequestObject.execute(top20_url);
//
//        symbol = (TextView)findViewById(R.id.symbol_tv);
//        cap = (TextView)findViewById(R.id.cap_tv);
//        hour = (TextView)findViewById(R.id.hour_tv);
//        day = (TextView)findViewById(R.id.day_tv);
//        price = (TextView)findViewById(R.id.price_tv);
//        heat = (TextView)findViewById(R.id.heat_tv);
//
//        crypto_icon = (ImageView)findViewById(R.id.imageView);
//        btn = (Button) findViewById(R.id.refresh_btn);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AsyncDataClass asyncDataClass = new AsyncDataClass();
//                asyncDataClass.execute(top20_url);
//            }
//        });
//    }
//
////    public void showToast(){
////        bitcoinDetails bd = new bitcoinDetails();
////        try {
////            JSONObject jo= new JSONObject(details);
////            bd.buy= "$ "+ jo.getJSONObject("USD").get("buy").toString();
////            bd.sell= "$ "+  jo.getJSONObject("USD").get("sell").toString();
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////        tv_buying.setText(bd.buy);
////        tv_selling.setText(bd.sell);
////    }
//
//    public void decodeJSON(){
//        Log.i("MAIN", "decodeJSON: "+details);
//        bitcoinDetails temp = new bitcoinDetails();
//        ArrayList<bitcoinDetails> arrayList= new ArrayList<bitcoinDetails>();
//        try {
//            JSONObject jsonObject = new JSONObject(details);
//            for(int i = 1; i<=20;i++){
//                temp.symbol = jsonObject.getJSONObject(""+i).get("symbol").toString();
//                temp.cap = jsonObject.getJSONObject(""+i).get("cap").toString();
//                temp.hour = jsonObject.getJSONObject(""+i).getJSONObject("change").get("hour").toString();
//                temp.day = jsonObject.getJSONObject(""+i).getJSONObject("change").get("day").toString();
//                temp.price = jsonObject.getJSONObject(""+i).get("price").toString();
//                temp.heat = jsonObject.getJSONObject(""+i).get("coinheat").toString();
//                arrayList.add(temp);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        symbol.setText(temp.symbol);
//        Picasso.with(MainActivity.this).load(imageURI+symbol).into(crypto_icon);
//        cap.setText(temp.cap);
//        day.setText(temp.day);
//        hour.setText(temp.hour);
//        heat.setText(temp.heat);
//        price.setText(temp.price);
//
//    }
//
//    private class AsyncDataClass extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            HttpParams httpParameters = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParameters,5000);
//            HttpConnectionParams.setSoTimeout(httpParameters,5000);
//            HttpClient httpClient = new DefaultHttpClient(httpParameters);
//            HttpPost httpPost = new HttpPost(params[0]);
//            String jsonResult = "";
//
//            try{
//                HttpResponse response = httpClient.execute(httpPost);
//                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
//            }catch (ClientProtocolException e){
//                e.printStackTrace();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//            return jsonResult;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//            if (result.equals("") || result == null) {
//                Toast.makeText(getApplicationContext(), "Server connection failed", Toast.LENGTH_LONG).show();
//            }
//
//            //josn result is stored in details
//            details = result;
//            decodeJSON();
////            showToast();
//        }
//
//
//        private StringBuilder inputStreamToString(InputStream is){
//            String rLine = "";
//            StringBuilder answer = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            try{
//                while((rLine = br.readLine())!= null){
//                        answer.append(rLine);
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            return answer;
//        }
//    }
//
//    private class bitcoinDetails{
//        String symbol = "";
//        String cap = "";
//        String hour = "";
//        String day = "";
//        String price = "";
//        String heat = "";
//    }
//
}
