package com.glassbyte.neurobranch.Services.HTTP;

import android.content.Context;
import android.os.AsyncTask;

import com.glassbyte.neurobranch.Services.DataObjects.Attributes;
import com.glassbyte.neurobranch.Services.DataObjects.Response;
import com.glassbyte.neurobranch.Services.Enums.RequestType;
import com.glassbyte.neurobranch.Services.Globals;
import com.glassbyte.neurobranch.Services.Interfaces.GetDetailsCallback;
import com.glassbyte.neurobranch.Services.Interfaces.JSONCallback;
import com.glassbyte.neurobranch.Services.Interfaces.LoginCallback;

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
import java.net.URL;

/**
 * Created by ed on 25/06/16
 */
public class HTTPRequest {

    //debug force post trial
    public static class JoinTrial extends AsyncTask<String, Void, String> {
        JSONObject details;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        public JoinTrial(String userId, String trialId) {
            details = new JSONObject();
            try {
                details.put("userid", userId);
                details.put("trialid", trialId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(Globals.ADD_TO_REQUESTED_LIST);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setConnectTimeout(30 * 1000);
                httpURLConnection.setReadTimeout(30 * 1000);

                //headers
                Writer writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                writer.write(details.toString());
                writer.close();

                System.out.println("Requesting to join with details " + details.toString());

                //response
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    buffer.append(inputLine).append("\n");
                }

                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert httpURLConnection!=null;
                    httpURLConnection.disconnect();
                    assert bufferedReader != null;
                    bufferedReader.close();
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

    public static class ReceiveJSON extends AsyncTask<Object, Object, JSONArray> {
        Context context;
        URL url;
        JSONCallback jsonCallback;
        String trialId, candidateId, questionId;

        public ReceiveJSON(Context context, URL url) {
            this(context, url, null, null, null, null);
        }

        public ReceiveJSON(Context context, URL url, JSONCallback jsonCallback) {
            this(context, url, jsonCallback, null, null, null);
        }

        public ReceiveJSON(Context context, URL url, JSONCallback jsonCallback,
                           String trialId, String questionId, String candidateId) {
            this.context = context;
            this.url = url;
            this.jsonCallback = jsonCallback;
            this.trialId = trialId;
            this.questionId = questionId;
            this.candidateId = candidateId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONArray doInBackground(Object... params) {
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) getUrl().openConnection();
                connection.setRequestMethod(RequestType.GET.name());
                connection.setRequestProperty("Content-length", "0");
                connection.setUseCaches(false);
                connection.setAllowUserInteraction(false);
                connection.setConnectTimeout(30 * 1000);
                connection.setReadTimeout(30 * 1000); // 30 seconds
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

            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            if(jsonCallback != null)
                jsonCallback.onLoadCompleted(jsonArray);
        }

        public URL getUrl() {
            return url;
        }

        public String getTrialId() {
            return trialId;
        }

        public String getQuestionId() {
            return questionId;
        }

        public String getCandidateId() {
            return candidateId;
        }
    }

    public static class GetCandidateDetails extends AsyncTask<JSONObject, Void, JSONObject> {
        GetDetailsCallback detailsCallback;
        String candidateId;

        public GetCandidateDetails(String candidateId, GetDetailsCallback detailsCallback) {
            this.candidateId = candidateId;
            this.detailsCallback = detailsCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(JSONObject... jsonObjects) {
            HttpURLConnection connection;
            try {
                URL url = new URL(Globals.getCandidateInfo(candidateId));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-length", "0");
                connection.setUseCaches(false);
                connection.setAllowUserInteraction(false);
                connection.setConnectTimeout(30 * 1000);
                connection.setReadTimeout(30 * 1000); // 30 seconds
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
                        System.out.println(writer.toString());
                        return new JSONObject(writer.toString());
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            if(s != null)
                detailsCallback.onRetrieved(s);
            else
                detailsCallback.onFail();
            super.onPostExecute(s);
        }
    }

    public static class CandidateLogin extends AsyncTask<String, Void, String> {

        JSONObject loginDetails;
        LoginCallback loginCallback;

        public CandidateLogin(String email, String password, LoginCallback loginCallback) {
            try {
                loginDetails = new JSONObject();
                loginDetails.put("email", email);
                loginDetails.put("password", password);
                this.loginCallback = loginCallback;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(Globals.CANDIDATE_LOGIN_ADDRESS);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setConnectTimeout(30 * 1000);
                httpURLConnection.setReadTimeout(30 * 1000); // 30 seconds
                httpURLConnection.connect();

                //body
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(loginDetails.toString());
                wr.flush();
                wr.close();

                //response
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    buffer.append(inputLine).append("\n");
                }

                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert httpURLConnection != null;
                    httpURLConnection.disconnect();
                    assert bufferedReader != null;
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.get("isMatch").equals(true)) {
                    loginCallback.onLoggedIn(jsonObject.get("id").toString());
                } else {
                    loginCallback.onLoginFailed();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                super.onPostExecute(s);
            }
        }
    }

    public static class CreateCandidateAccount extends AsyncTask<String, Void, String> {

        JSONObject candidateSignUpDetails;

        public CreateCandidateAccount(String email, String password){
            try {
                candidateSignUpDetails = new JSONObject();
                candidateSignUpDetails.put("email", email);
                candidateSignUpDetails.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(Globals.CANDIDATE_SIGNUP_ADDRESS);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setConnectTimeout(30 * 1000);
                httpURLConnection.setReadTimeout(30 * 1000); // 30 seconds
                httpURLConnection.connect();

                //body
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(candidateSignUpDetails.toString());
                wr.flush();
                wr.close();

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
                    assert httpURLConnection != null;
                    httpURLConnection.disconnect();
                    assert bufferedReader != null;
                    bufferedReader.close();
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

    public static class PostTrialResponse extends AsyncTask<String, Void, String> {
        Response response;

        public PostTrialResponse(Response response) {
            this.response = response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader bufferedReader = null;

            try {
                URL url = new URL(Globals.POST_RESPONSE_ADDRESS);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setConnectTimeout(30 * 1000);
                httpURLConnection.setReadTimeout(30 * 1000); // 30 seconds
                httpURLConnection.connect();

                //body
                OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
                wr.write(response.getQuestionResponse().toString());
                wr.flush();
                wr.close();

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
