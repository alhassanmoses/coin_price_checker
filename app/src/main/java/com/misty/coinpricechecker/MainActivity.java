package com.misty.coinpricechecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView coinCardsRV;
    private ProgressBar progressBar;
    private String fiatCurrencySelected = "USD";
    private TextView amountToConvert;
    private DBHandler dbHandler;

    private ArrayList<CoinCardModel> coinCardsDataList;
    private CoinCardAdapter coinCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountToConvert = findViewById(R.id.idAmountInput);
        coinCardsRV = findViewById(R.id.idCoinCards);
        progressBar = findViewById(R.id.idPBar);
        Spinner spinner = findViewById(R.id.idCurrencySpinner);

        dbHandler = new DBHandler(MainActivity.this);

        coinCardsDataList = new ArrayList<>();
        coinCardAdapter = new CoinCardAdapter(coinCardsDataList, this);

        coinCardsRV.setLayoutManager(new LinearLayoutManager(this));
        coinCardsRV.setAdapter(coinCardAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        amountToConvert.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                getConversionRates(editable.toString(), fiatCurrencySelected);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fiatCurrencySelected = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), fiatCurrencySelected, Toast.LENGTH_SHORT).show();
        getConversionRates(amountToConvert.getText().toString(), fiatCurrencySelected);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getConversionRates(String amount, String fiat) {
        progressBar.setVisibility(View.VISIBLE);

        if (!ValidAmountChecker.isValidAmount(amount)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "Kindly input a valid amount. Eg: 10", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!InternetConnectivity.hasInternetConnection(this)) {

          coinCardsDataList =  dbHandler.fetchCachedCoins();
          coinCardAdapter.notifyDataSetChanged();

          return;
        }

        //Cannot convert more than one coin on free tier
        String url = "https://pro-api.coinmarketcap.com/v1/tools/price-conversion?amount=" + amount + "&symbol=" + fiat + "&convert=BTC";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressBar.setVisibility(View.GONE);
                coinCardsDataList.clear();

                try {

                    JSONObject responseData = response.getJSONObject("data");
                    JSONObject quotes = responseData.getJSONObject("quote");

                    for (Iterator<String> it = quotes.keys(); it.hasNext(); ) {

                        String symbol = it.next();

                        JSONObject data = quotes.getJSONObject(symbol);

                        String coinSymbol = symbol;

                        //Would have looked-up an ENUM of symbols if it were an array
                        String coinName = "Bitcoin";

                        double coinConversionRate = data.getDouble("price");
                        String coinConversionTimestamp = data.getString("last_updated");

                        coinCardsDataList.add(new CoinCardModel(symbol, coinName, coinConversionRate, coinConversionTimestamp));
                        dbHandler.addCoinData(symbol, coinName, coinConversionRate, coinConversionTimestamp);
                    }

                    coinCardAdapter.notifyDataSetChanged();


                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Sorry, we're unable to process your conversion, please try again.", Toast.LENGTH_SHORT).show();

                }

            }
        }, error -> {

            progressBar.setVisibility(View.GONE);

            Toast.makeText(MainActivity.this, "Sorry, we're unable to process your conversion, please try again.", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();

                //key will be deleted shortly
                headers.put("X-CMC_PRO_API_KEY", "a18f0f76-dec6-4b2b-9c2a-d11328a8a345");

                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
}