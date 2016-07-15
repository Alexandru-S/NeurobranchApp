package com.glassbyte.neurobranch.Services.HTTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.Helpers.Connectivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by ed on 25/06/16.
 */
public class HTTPRequest {

    public enum RequestType {
        POST, GET
    }

    public enum UpdateType {
        POST_DATA, LOGIN, UPDATE_TRIAL_LIST, UPDATE_QS_AVAILABLE
    }

    public HTTPRequest() {}

    public static class GetImageResource extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... strings) {
            try {
                InputStream is = (InputStream) new URL(strings[0]).getContent();
                return Drawable.createFromStream(is, null);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static class JoinTrial extends AsyncTask<String, Void, String> {
        String userID;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        public JoinTrial(String userID) {
            this.userID = userID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(Globals.POST_JOIN_TRIALS_ADDRESS);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application-json");
                httpURLConnection.setRequestProperty("Accept", "application/json");

                //headers
                Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                writer.write(userID);
                writer.close();

                //response
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    buffer.append(inputLine).append("\n");
                    if (buffer.length() == 0) {
                        return null;
                    }
                }

                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public static class ReceiveJSON extends AsyncTask<Void, Void, JSONArray> {

        Context context;
        ProgressDialog progressDialog;
        URL url;
        boolean isNetworkConnected;

        public ReceiveJSON(Context context, URL url) {
            this.context = context;
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isNetworkConnected = Connectivity.isNetworkConnected(context);
            if(isNetworkConnected) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading...");
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        }

        @Override
        protected JSONArray doInBackground(Void...voids) {
            if(isNetworkConnected) {
                HttpURLConnection connection;
                try {
                    connection = (HttpURLConnection) getUrl().openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-length", "0");
                    connection.setUseCaches(false);
                    connection.setAllowUserInteraction(false);
                    connection.connect();

                    int status = connection.getResponseCode();
                    Writer writer = new StringWriter();

                    switch (status) {
                        case 200:
                        case 201:
                            char[] buffer = new char[1024];
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(connection.getInputStream()));
                            int n;
                            while ((n = br.read(buffer)) != -1) {
                                writer.write(buffer, 0, n);
                            }
                            br.close();
                            return new JSONArray(writer.toString());
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            progressDialog.dismiss();
        }

        public URL getUrl() {
            return url;
        }
    }

    public class AreNewQsAvailable extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    public class UserLogin extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }

    public static class PostTrialResponse extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String JSONData = strings[0];
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(Globals.POST_TRIALS_ADDRESS);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application-json");
                httpURLConnection.setRequestProperty("Accept", "application/json");

                //headers
                Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                writer.write(JSONData);
                writer.close();

                //response
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    buffer.append(inputLine).append("\n");
                    if (buffer.length() == 0) {
                        return null;
                    }
                }

                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
