package com.example.loginpage.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

class MyTask extends AsyncTask<String, Void, String> {

    ArrayList<DistrictModel> arrayList = new ArrayList<>();

    Context context;
    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    private String title;
    private String image;

    private static final String url = "https://cityguidecom.000webhostapp.com/json(1).json";

    public MyTask(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            URL u = new URL(url);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) u.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            InputStream stream = httpsURLConnection.getInputStream();

            if (stream != null) {

                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (s != null) {

            try {

                JSONObject rootobject = new JSONObject(s);
                JSONArray cityguide = rootobject.getJSONArray("cityguide");

                for (int i = 0; i < cityguide.length(); i++) {
                    JSONObject district = cityguide.getJSONObject(i);

                    title = district.getString("title");
                    image = district.optString("images");

                    DistrictModel districtModel = new DistrictModel();
                    districtModel.setDist_title(title);
                    districtModel.setImage(image);
                    arrayList.add(districtModel);
                }

                DistrictAdopter adaptar = new DistrictAdopter(context, arrayList);
                recyclerView.setAdapter(adaptar);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
