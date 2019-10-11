package com.example.loginpage.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.example.loginpage.Home.DistrictAdopter;
import com.example.loginpage.Top5cities.Top5Model;
import com.example.loginpage.Top5cities.Topcitys;
import com.example.loginpage.city.City;
import com.example.loginpage.service.ServiceActivity;
import com.example.loginpage.service.ServiceAdaptar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HotelTask extends AsyncTask<String, Void, String> {
    private String url = "https://cityguidecom.000webhostapp.com/json(1).json";
    ArrayList<Top5Model> arrayList = new ArrayList<Top5Model>();
    Context context;
    RecyclerView recyclerView;
    private String title;
    private String image;
    private ProgressDialog pd;

    public HotelTask(Context context, RecyclerView recyclerView) {
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
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pd.isShowing()) {
            pd.dismiss();
        }
        if (s != null) {
            try {

                JSONObject rootobject = new JSONObject(s);
                JSONArray cityguide = rootobject.getJSONArray("cityguide");

                int i = Topcitys.getdistindex();

                JSONObject districts = cityguide.getJSONObject(i);
                JSONArray cities = districts.getJSONArray("cities");

                int j = City.getTopcitiesindex();
                int c = ServiceActivity.getcityindex();
                JSONObject city = cities.getJSONObject(j);


                if (c == 0) {
                    JSONArray hotels = city.getJSONArray("hotels");

                    for (int k = 0; k < hotels.length(); k++) {

                        JSONObject hotel = hotels.getJSONObject(k);
                        title = hotel.getString("title");
                        image = hotel.optString("images");
                        Top5Model top5Model = new Top5Model();
                        top5Model.setImage(image);
                        top5Model.setTitle(title);
                        arrayList.add(top5Model);
                    }
                } else if (c == 1) {
                    JSONArray restarents = city.getJSONArray("restaurents");
                    for (int k = 0; k < restarents.length(); k++) {
                        JSONObject restarent = restarents.getJSONObject(k);
                        title = restarent.getString("title");
                        image = restarent.optString("images");
                        Top5Model top5Model = new Top5Model();
                        top5Model.setImage(image);
                        top5Model.setTitle(title);
                        arrayList.add(top5Model);
                    }
                } else if (c == 2) {
                    JSONArray touristplaces = city.getJSONArray("touristplaces");
                    for (int k = 0; k < touristplaces.length(); k++) {
                        JSONObject touristplace = touristplaces.getJSONObject(k);
                        title = touristplace.getString("title");
                        image = touristplace.optString("images");
                        Top5Model top5Model = new Top5Model();
                        top5Model.setImage(image);
                        top5Model.setTitle(title);
                        arrayList.add(top5Model);
                    }
                } else if (c == 3) {
                    JSONArray theaters = city.getJSONArray("theaters");
                    for (int k = 0; k < theaters.length(); k++) {
                        JSONObject theater = theaters.getJSONObject(k);
                        title = theater.getString("title");
                        image = theater.optString("images");
                        Top5Model top5Model = new Top5Model();
                        top5Model.setImage(image);
                        top5Model.setTitle(title);
                        arrayList.add(top5Model);
                    }
                }
                ServiceAdaptar serviceAdaptar = new ServiceAdaptar(context, arrayList);
                recyclerView.setAdapter(serviceAdaptar);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
