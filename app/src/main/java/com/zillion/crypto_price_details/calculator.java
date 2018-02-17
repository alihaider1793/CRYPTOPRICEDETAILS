package com.zillion.crypto_price_details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class calculator extends AppCompatActivity implements View.OnClickListener {

    TextView tv_topDetails,tv_cryptoName;
    TextView et_USD, et_cryptoPrice;
    ImageView close;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,b00,bClear;
    int value_etUSD = 1;

    String extraPrice = "";
    String extraSymbol = "";

    double extraPrc = 0;
    double calcPrc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        extraPrice = getIntent().getStringExtra("cryptoPrice");
        extraSymbol = getIntent().getStringExtra("cryptoSymbol");
        extraPrc = Double.parseDouble(extraPrice);

        tv_topDetails = (TextView) findViewById(R.id.topDetail_tv);
        tv_cryptoName = (TextView) findViewById(R.id.cryptoName_tv);

        tv_topDetails.setText("1 "+extraSymbol+" = "+extraPrc+" USD");
        tv_cryptoName.setText(""+extraSymbol);

        close = (ImageView) findViewById(R.id.close_calc);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        b1 = (Button)findViewById(R.id.num1);
        b1.setOnClickListener(this);

        b2 = (Button)findViewById(R.id.num2);
        b2.setOnClickListener(this);

        b3 = (Button)findViewById(R.id.num3);
        b3.setOnClickListener(this);

        b4 = (Button)findViewById(R.id.num4);
        b4.setOnClickListener(this);

        b5 = (Button)findViewById(R.id.num5);
        b5.setOnClickListener(this);

        b6 = (Button)findViewById(R.id.num6);
        b6.setOnClickListener(this);

        b7 = (Button)findViewById(R.id.num7);
        b7.setOnClickListener(this);

        b8 = (Button)findViewById(R.id.num8);
        b8.setOnClickListener(this);

        b9 = (Button)findViewById(R.id.num9);
        b9.setOnClickListener(this);

        b0 = (Button)findViewById(R.id.num0);
        b0.setOnClickListener(this);

        b00 = (Button)findViewById(R.id.num00);
        b00.setOnClickListener(this);

        bClear = (Button)findViewById(R.id.clearText);
        bClear.setOnClickListener(this);


        et_USD = (TextView)findViewById(R.id.USD_et);
        et_cryptoPrice = (TextView)findViewById(R.id.cryptoPrice_et);

        initialize();
    }

    private void initialize() {
        et_cryptoPrice.setText("1");
        calcPrc = extraPrc * value_etUSD;
        et_USD.setText(""+calcPrc);
    }

    private void appendET(int num) {
        if(num == -1){
            et_cryptoPrice.setText("1");
            value_etUSD = 1;
            et_USD.setText(""+extraPrc);
        }

        else if((value_etUSD == 1) && num<10){
            if(num == 1){
                value_etUSD = (value_etUSD*10)+num;
            }
            else{
                value_etUSD = num;
            }
            et_cryptoPrice.setText(""+value_etUSD);
            calcPrc = value_etUSD * extraPrc;
            et_USD.setText(""+calcPrc);
        }

        else if(num == 10){
            value_etUSD = value_etUSD*10;
            et_cryptoPrice.setText(""+value_etUSD);
            calcPrc = value_etUSD * extraPrc;
            et_USD.setText(""+calcPrc);

        }
        else if(num == 100){
            value_etUSD = value_etUSD *100;
            et_cryptoPrice.setText(""+value_etUSD);
            calcPrc = value_etUSD * extraPrc;
            et_USD.setText(""+calcPrc);
        }
        else {
            value_etUSD =  (value_etUSD*10)+num;
            et_cryptoPrice.setText(""+value_etUSD);
            calcPrc = value_etUSD * extraPrc;
            et_USD.setText(""+calcPrc);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.num1:
                appendET(1);
                break;
            case R.id.num2:
                appendET(2);
                break;
            case R.id.num3:
                appendET(3);
                break;
            case R.id.num4:
                appendET(4);
                break;
            case R.id.num5:
                appendET(5);
                break;
            case R.id.num6:
                appendET(6);
                break;
            case R.id.num7:
                appendET(7);
                break;
            case R.id.num8:
                appendET(8);
                break;
            case R.id.num9:
                appendET(9);
                break;
            case R.id.num0:
                appendET(10);
                break;
            case R.id.num00:
                appendET(100);
                break;
            case R.id.clearText:
                appendET(-1);
                break;
        }
    }
}
