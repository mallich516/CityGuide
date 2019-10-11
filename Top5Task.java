package com.example.loginpage.Top5cities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.loginpage.Home.DistrictModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class Top5Task extends AsyncTask<String, Void, String> {
    ProgressDialog progressDialog;

    Context context;
    RecyclerView rv;
    private static final String url = "https://cityguidecom.000webhostapp.com/json(1).json";
    private String title;
    private String image;
    private AbstractList<Top5Model> arrayList = new ArrayList<>();


    public Top5Task(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;
    }

    @Override
    protected String doInBackground(String... strings) {


        try {
            URL u = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream stream = connection.getInputStream();

            if (stream != null) {

                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line = null;
                StringBuilder sb = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
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

                int i = Topcitys.getdistindex();

                JSONObject districts = cityguide.getJSONObject(i);
                JSONArray cities = districts.getJSONArray("cities");

                for (int j = 0; j < cities.length(); j++) {

                    JSONObject city = cities.getJSONObject(j);

                    title = city.getString("names");
                    image = city.optString("images");

                    Top5Model top5Model = new Top5Model();
                    top5Model.setImage(image);
                    top5Model.setTitle(title);
                    arrayList.add(top5Model);
                }


                TopcitiesAdaptar adaptar = new TopcitiesAdaptar(context, arrayList);
                rv.setAdapter(adaptar);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
