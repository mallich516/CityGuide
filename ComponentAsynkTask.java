package com.example.loginpage.components;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

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

public class ComponentAsynkTask extends AsyncTask<String, Void, String> {

    private String url = "https://cityguidecom.000webhostapp.com/json(1).json";

    ArrayList<ComponentModel> arrayList = new ArrayList<>();

    ProgressDialog pd;
    Context context;

    private String rating;
    private String desc;
    private String image;
    private String title;

    private RecyclerView rv;

    public ComponentAsynkTask(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;
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
        //pd = new ProgressDialog(context);
       // pd.setMessage("Loading...");
     //   pd.setCancelable(false);
      //  pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //if (pd.isShowing()) {
          //  pd.dismiss();
    //}
        try {

            JSONObject rootobject = new JSONObject(s);
            JSONArray cityguide = rootobject.getJSONArray("cityguide");

            int i = Topcitys.getdistindex();

            JSONObject districts = cityguide.getJSONObject(i);
            JSONArray cities = districts.getJSONArray("cities");

            int j = City.getTopcitiesindex();
            int c = ServiceActivity.getcityindex();
            int h = ComponentActivity.getIndex();
            JSONObject city = cities.getJSONObject(j);


            if (c == 0) {
                JSONArray hotels = city.getJSONArray("hotels");

                JSONObject hotel = hotels.getJSONObject(h);
                title = hotel.getString("title");
                image = hotel.optString("images");
                desc = hotel.getString("description");
                rating = hotel.getString("rating");
               // img.getImage(image);
                //tit.getTitle(title);
                //des.getDesc(desc);
              //  rat.getRating(rating);
                ComponentModel Cmodel = new ComponentModel();
                Cmodel.setImage(image);
                Cmodel.setTitle(title);
                Cmodel.setDesc(desc);
                Cmodel.setRating(rating);
                arrayList.add(Cmodel);

            } else if (c == 1) {
                JSONArray restarents = city.getJSONArray("restaurents");

                JSONObject restarent = restarents.getJSONObject(h);
                title = restarent.getString("title");
                image = restarent.optString("images");
                desc = restarent.getString("description");
                rating = restarent.getString("rating");
                //img.getImage(image);
      //          tit.getTitle(title);
    //            des.getDesc(desc);
  //              rat.getRating(rating);
                ComponentModel Cmodel = new ComponentModel();
                Cmodel.setImage(image);
                Cmodel.setTitle(title);
                Cmodel.setDesc(desc);
                Cmodel.setRating(rating);
                arrayList.add(Cmodel);

            } else if (c == 2) {
                JSONArray touristplaces = city.getJSONArray("touristplaces");

                JSONObject touristplace = touristplaces.getJSONObject(h);
                title = touristplace.getString("title");
                image = touristplace.optString("images");
                desc = touristplace.getString("description");
                rating = touristplace.getString("rating");
                //img.getImage(image);
      //          tit.getTitle(title);
    //            des.getDesc(desc);
  //              rat.getRating(rating);
                ComponentModel Cmodel = new ComponentModel();
                Cmodel.setImage(image);
                Cmodel.setTitle(title);
                Cmodel.setDesc(desc);
                Cmodel.setRating(rating);
                arrayList.add(Cmodel);

            } else if (c == 3) {
                JSONArray theaters = city.getJSONArray("theaters");

                JSONObject theater = theaters.getJSONObject(h);
                title = theater.getString("title");
                image = theater.optString("images");
                desc = theater.getString("description");
                rating = theater.getString("rating");
                //img.getImage(image);
      //          tit.getTitle(title);
    //            des.getDesc(desc);
  //              rat.getRating(rating);
                ComponentModel Cmodel = new ComponentModel();
                Cmodel.setImage(image);
                Cmodel.setTitle(title);
                Cmodel.setDesc(desc);
                Cmodel.setRating(rating);
                arrayList.add(Cmodel);
            }

            //  ServiceAdaptar serviceAdaptar = new ServiceAdaptar(arrayList,context);
            ComponentAdaptar adaptar = new ComponentAdaptar(context,arrayList);
            rv.setAdapter(adaptar);
            //    ComponentActivity activity = new ComponentActivity(arrayList,context);
            //   activity.arrayList.toArray();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
