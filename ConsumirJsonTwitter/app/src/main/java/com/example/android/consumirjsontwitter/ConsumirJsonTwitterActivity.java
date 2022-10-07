    package com.example.android.consumirjsontwitter;

    import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ConsumirJsonTwitterActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sTrend;
        sTrend = getTimelineForSearchTerm();
        //new DownloadJsonAsyncTask().execute("https://api.twitter.com/1" +
        //        ".1/trends/place.json?id=1");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Trend trend = (Trend) l.getAdapter().getItem(position);

        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(trend.url));
        startActivity(it);
    }

    class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<Trend>> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(ConsumirJsonTwitterActivity.this, "Aguarde",
                    "Baixando JSON, Por Favor Aguarde...");
        }

        @Override
        protected List<Trend> doInBackground(String... params) {
            String urlString = params[0];

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(urlString);

            try {
                HttpResponse response = httpclient.execute(httpget);

                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream instream = entity.getContent();

                    String json = toString(instream);
                    instream.close();

                    List<Trend> trends = getTrends(json);

                    return trends;
                }
            } catch (Exception e) {
                Log.e("DEVMEDIA", "Falha ao acessar Web service", e);
            }
            return null;
        }


        private List<Trend> getTrends(String jsonString) {

            List<Trend> trends = new ArrayList<Trend>();
            String json = "https://api.twitter.com/1.1/trends/place.json?id=1";
            try {
                //JSONArray trendLists = new JSONArray(jsonString);
                //JSONObject trendList = trendLists.getJSONObject(0);
                JSONObject parentObject = new JSONObject(json);
                JSONObject trendList = parentObject.getJSONObject(json);
                JSONArray trendsArray = trendList.getJSONArray("trends");

                JSONObject trend;

                for (int i = 0; i < trendsArray.length(); i++) {
                    trend = new JSONObject(trendsArray.getString(i));

                    Log.i("DEVMEDIA", "nome=" + trendList.getString("name"));

                    Trend objetoTrend = new Trend();
                    objetoTrend.name = trendList.getString("name");
                    objetoTrend.url = trendList.getString("url");

                    trends.add(objetoTrend);
                }
            } catch (JSONException e) {
                Log.e("DEVMEDIA", "Erro no parsing do JSON", e);
            }

            return trends;
        }
        @Override
        protected void onPostExecute(List<Trend> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.size() > 0) {
                ArrayAdapter<Trend> adapter = new ArrayAdapter<Trend>(
                        ConsumirJsonTwitterActivity.this,
                        android.R.layout.simple_list_item_1, result);
                setListAdapter(adapter);

            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        ConsumirJsonTwitterActivity.this).setTitle("Atenção")
                        .setMessage("Não foi possivel acessar essas informções...")
                        .setPositiveButton("OK", null);
                builder.create().show();
            }
        }

        private String toString(InputStream is) throws IOException {

            byte[] bytes = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int lidos;
            while ((lidos = is.read(bytes)) > 0) {
                baos.write(bytes, 0, lidos);
            }
            return new String(baos.toByteArray());
        }
    }

    class Trend {
        String name;
        String url;

        @Override
        public String toString() {
            return name;
        }
    }

    // Accesing twitterapi needs a Authorization as a header
    public class ConstantsUtils {

        public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
        public static final String URL_SEARCH = URL_ROOT_TWITTER_API + "/1.1/search/tweets.json?q=";
        public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";

        public static final String URL_INDIA_TRENDING ="https://api.twitter.com/1.1/trends/place.json?id=23424977";


        public static final String CONSUMER_KEY = "pEKsMXQUzwG7R4bT8MsWI5eRS";
        public static final String CONSUMER_SECRET = "5ZuwxkhKMZUjpXAh0rqMx2Mcoc0wQ3CvnLGCTgacxaRs9WRNfw";


    }

    // Getting Authorization Token
    public static final String TAG = "TwitterUtils";

    public static String appAuthentication() {

        HttpURLConnection httpConnection = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            URL url = new URL(ConstantsUtils.URL_AUTHENTICATION);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);

            String accessCredential = ConstantsUtils.CONSUMER_KEY + ":"
                    + ConstantsUtils.CONSUMER_SECRET;
            String authorization = "Basic "
                    + Base64.encodeToString(accessCredential.getBytes(),
                    Base64.NO_WRAP);
            String param = "grant_type=client_credentials";

            httpConnection.addRequestProperty("Authorization", authorization);
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            httpConnection.connect();

            outputStream = httpConnection.getOutputStream();
            outputStream.write(param.getBytes());
            outputStream.flush();
            outputStream.close();
            // int statusCode = httpConnection.getResponseCode();
            // String reason =httpConnection.getResponseMessage();

            bufferedReader = new BufferedReader(new InputStreamReader(
                    httpConnection.getInputStream()));
            String line;
            response = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            Log.d(TAG,
                    "POST response code: "
                            + String.valueOf(httpConnection.getResponseCode()));
            Log.d(TAG, "JSON response: " + response.toString());

        } catch (Exception e) {
            Log.e(TAG, "POST error: " + Log.getStackTraceString(e));

        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return response.toString();
    }

    public static String getTimelineForSearchTerm()
    {
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(ConstantsUtils.URL_INDIA_TRENDING);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");

            String jsonString = appAuthentication();
            JSONObject jsonObjectDocument = new JSONObject(jsonString);
            String token = jsonObjectDocument.getString("token_type") + " "
                    + jsonObjectDocument.getString("access_token");
            httpConnection.setRequestProperty("Authorization", token);

            httpConnection.setRequestProperty("Authorization", token);
            httpConnection.setRequestProperty("Content-Type",
                    "application/json");
            httpConnection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(
                    httpConnection.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }

            Log.d(TAG,
                    "GET response code: "
                            + String.valueOf(httpConnection
                            .getResponseCode()));
            Log.d(TAG, "JSON response: " + response.toString());

        } catch (Exception e) {
            Log.e(TAG, "GET error: " + Log.getStackTraceString(e));

        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();

            }
        }
        return response.toString();
    }
}