package com.mobica.map.serverConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectorHttp {

    public String sendHttpGet(String url) throws IOException {

        String ret = "";
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                ret = EntityUtils.toString(resEntity);
            }
        return ret;
    }
}
