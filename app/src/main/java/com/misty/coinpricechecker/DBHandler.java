package com.misty.coinpricechecker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.InetAddress;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "coindatadb";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "cachedcoins";

    private static final String ID_COL = "id";

    private static final String COIN_SYMBOL = "symbol";

    private static final String COIN_NAME = "name";

    private static final String COIN_CONVERSION_RATE = "rate";

    private static final String COIN_CONVERSION_TIMESTAMP = "timestamp";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COIN_SYMBOL + " TEXT,"
                + COIN_NAME + " TEXT,"
                + COIN_CONVERSION_RATE + " REAL,"
                + COIN_CONVERSION_TIMESTAMP + " TEXT)";

        db.execSQL(query);
    }

    public void addCoinData(String coinSymbol, String coinName, double coinConversionRate, String coinConversionTimestamp) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COIN_SYMBOL, coinSymbol);
        values.put(COIN_NAME, coinName);
        values.put(COIN_CONVERSION_RATE, coinConversionRate);
        values.put(COIN_CONVERSION_TIMESTAMP, coinConversionTimestamp);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @SuppressLint("Range")
    public ArrayList<CoinCardModel> fetchCachedCoins() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<CoinCardModel> cachedCoinsList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                cachedCoinsList.add(new CoinCardModel(cursor.getString(cursor.getColumnIndexOrThrow("COIN_SYMBOL")),
                        cursor.getString(cursor.getColumnIndexOrThrow("COIN_NAME")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("COIN_CONVERSION_RATE")),
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("COIN_CONVERSION_TIMESTAMP")))));
            } while (cursor.moveToNext());

        }

        cursor.close();
        return cachedCoinsList;
    }
}
