package com.mobica.map.serverConnection;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectorHttp {

    public String sendHttpGet(String url) {

        String ret = "";
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                ret = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ret;
        }
        return ret;
    }

    public String sendHttpPost(String url, String text) {

        String responseStr = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("value", text));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            HttpResponse response;

            response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                responseStr = EntityUtils.toString(resEntity);
            }
        } catch (ClientProtocolException e) {
            Log.e("ConnectorHttp", "unsupported exception", e);
        } catch (IOException e) {
            Log.e("ConnectorHttp", "unsupported exception", e);
        }

        return responseStr;
    }
}
