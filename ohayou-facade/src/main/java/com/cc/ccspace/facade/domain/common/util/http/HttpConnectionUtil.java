package com.cc.ccspace.facade.domain.common.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUtil {
	private static Logger log = LoggerFactory.getLogger(HttpConnectionUtil.class);
	public static String post(String urlString, String content) {
        StringBuffer stringBuffer = new StringBuffer();

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlString);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Length", String.valueOf(content.length()));
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
            OutputStreamWriter outputStreamWriter= new OutputStreamWriter(httpConnection.getOutputStream(), "UTF-8");
            outputStreamWriter.write(content);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {
            return null;
        }
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
            }
        }
        log.info("body:"+stringBuffer.toString());
        return stringBuffer.toString();
    }
	
	
}
