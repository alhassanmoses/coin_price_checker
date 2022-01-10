package com.misty.coinpricechecker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CoinCardAdapter extends RecyclerView.Adapter<CoinCardAdapter.ViewHolder> {

    private ArrayList<CoinCardModel> coinCardDataList;
    private Context context;
    private static DecimalFormat df5 = new DecimalFormat("#.#####");

    public CoinCardAdapter(ArrayList<CoinCardModel> coinCardDataList, Context context) {
        this.coinCardDataList = coinCardDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoinCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.coin_card, parent,false);

        return new CoinCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinCardAdapter.ViewHolder holder, int position) {

        CoinCardModel coinData = coinCardDataList.get(position);
        holder.coinSymbol.setText(coinData.getCoinSymbol());
        holder.coinName.setText(coinData.getCoinName());
        holder.coinConversionRate.setText(df5.format(coinData.getCoinConversionRate()));
        holder.conversionTimestamp.setText(coinData.getTimeOfConversion().toString());
    }

    @Override
    public int getItemCount() {
        return coinCardDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView coinSymbol,coinName,coinConversionRate, conversionTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinSymbol = itemView.findViewById(R.id.idCoinSymbol);
            coinName = itemView.findViewById(R.id.idCoinName);
            coinConversionRate = itemView.findViewById(R.id.idConvertedCoinRate);
            conversionTimestamp = itemView.findViewById(R.id.idConversionTimestamp  );

        }
    }
}
