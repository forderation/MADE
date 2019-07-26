package com.example.myasynctaskloader;
import android.content.AsyncTaskLoader;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<WeatherItems>> {
    private ArrayList<WeatherItems> mData;
    private boolean mHasResult = false;
    private String cities;

    public MyAsyncTaskLoader(Context context, String cities) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if(takeContentChanged()){
            forceLoad();
        }else if(mHasResult){
            deliverResult(mData);
        }
    }

    @Override
    public void deliverResult(ArrayList<WeatherItems> data) {
        super.onReset();
        onStopLoading();
        if(mHasResult){
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if(mHasResult){
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "ea0dd5d1d993a66b645606918182349c";

    @Override
    protected ArrayList<WeatherItems> onLoadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<WeatherItems> listItems = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id=" + cities
                + "&units=metric&appid=" + API_KEY;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");
                    for(int i=0;i<list.length();i++){
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems(weather);
                        listItems.add(weatherItems);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return listItems;
    }

    @Override
    public ArrayList<WeatherItems> loadInBackground() {
        return null;
    }
}
