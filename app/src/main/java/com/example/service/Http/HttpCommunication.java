package com.example.service.Http;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class HttpCommunication
{
    Context context;
    InputStream is=null;
    static String result=null;

    static InputStream ins = null;
    static JSONObject jObj = null;
    static String json = "";
    static String responseString;


    public HttpCommunication(Context context) {
        this.context=context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public static String getLogin(String url, String method, List<NameValuePair> params, Context context)
    {
        try
        {
            HttpClient httpClient = new DefaultHttpClient();
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url += "?" + paramString;
            Log.i("url", url);
            HttpGet httpGet = new HttpGet(url);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            Log.i("Fail 2","4");
            ins = entity.getContent();

            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(ins,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            ins.close();
             result = sb.toString().trim();

          //  result=result.replace("0Invaid email or password","invalid User");
           // result=result.replace("1Login Success","");


            Log.i("pass 2", "connection success :"+result);
           // Toast.makeText(context, result.trim(),Toast.LENGTH_LONG).show();

        }
        catch(Exception e)
        {
            Log.e("Fail 1", e.toString());
            Toast.makeText(context, "Invalid IP Address"+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }

        // return JSON String
       // return jObj;


        return result;

    }


    public static JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params, Context context) {
//this.context=context;
        // Making HTTP request
        Log.d("json class", url + "," + method + "," + params.toString());
        try {

            // check for request method
            if (method == "POST") {
                // request method is POST
                // defaultHttpClient
                Log.d("json class", "post method");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                Log.d("json class", "HttpPost" + httpPost);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                Log.d("json class", "setentity");
                HttpResponse httpResponse = httpClient.execute(httpPost);

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int responce_code = httpResponse.getStatusLine()
                        .getStatusCode();
                Log.d("responce code", "response method");
                Log.d("responce code", "" + responce_code);
                StatusLine statusLine = httpResponse.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    Log.i("RESPONSE", "6");
                    /*
                     * httpResponse.getEntity().writeTo(out); out.close();
                     * String responseString = out.toString(); Log.i("RESPONSE",
                     * ""+responseString);
                     */
                    // ..more logic
                } else {
                    Log.d("RESPONSE", "null pointer exception");
                    // Closes the connection.
                    httpResponse.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }

                HttpEntity httpEntity = httpResponse.getEntity();
                ins = httpEntity.getContent();

            } else if (method == "GET") {
                // request method is GET
                HttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                Log.i("url", url);
                HttpGet httpGet = new HttpGet(url);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int responce_code = httpResponse.getStatusLine()
                        .getStatusCode();
                Log.d("responce code", "response method");
                Log.d("responce code", "" + responce_code);

                StatusLine statusLine = httpResponse.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

                    Log.d("RESPONSE", "6");
                    httpResponse.getEntity().writeTo(out);
                    Log.d("RESPONSE", "7");
                    out.close();
                    Log.d("RESPONSE", "8");
                    responseString = out.toString();
                    Log.d("RESPONSE", "9");
                    Log.i("RESPONSE", "" + responseString);
                    // ..more logic
                } else {
                    Log.d("RESPONSE", "null pointer exception");
                    // Closes the connection.
                    httpResponse.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
                /*
                 * HttpEntity httpEntity = httpResponse.getEntity(); is =
                 * httpEntity.getContent();
                 */
            }
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(context, "Encode Exception "+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Toast.makeText(context, "Protocol Exception "+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "Io Exception "+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        try {
            /*
             * BufferedReader reader = new BufferedReader(new InputStreamReader(
             * is, "iso-8859-1"), 8); StringBuilder sb = new StringBuilder();
             * String line = null; while ((line = reader.readLine()) != null) {
             * sb.append(line + "\n"); } is.close();
             */
            json = responseString.toString();
           // Toast.makeText(context, "Res : "+responseString.toString(),Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error "+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();

            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
          //  Log.i("RespJson",jObj.get());
        } catch (JSONException e) {
            Toast.makeText(context, "JsonException "+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();

            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;
    }

}
