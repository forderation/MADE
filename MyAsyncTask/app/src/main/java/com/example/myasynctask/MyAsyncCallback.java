package com.example.myasynctask;

public interface MyAsyncCallback {
    void onPreExecute();
    void onPostExecute(String text);
}
