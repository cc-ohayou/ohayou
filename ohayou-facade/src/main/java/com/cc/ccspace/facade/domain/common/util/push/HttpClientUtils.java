package com.cc.ccspace.facade.domain.common.util.push;

import com.cc.ccspace.facade.domain.common.constants.PushConstants;
import com.cc.ccspace.facade.domain.common.util.http.HttpClientUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * HTTPClient 工具类
 * 使用旧的http api 不推荐使用
 * @author Lynch 2014-09-15
 */
@Deprecated
public class HttpClientUtils {

    private static final JsonNodeFactory factory = new JsonNodeFactory(false);

    /**
     * Send SSL Request
     *
     * @return
     */
    public static ObjectNode sendHTTPRequest(URL url, Credential credential, Object dataBody, String method) {

//        HttpClient httpClient = getClient(true);
        CloseableHttpClient httpClient = HttpClientUtil.getCloseableClient();

        ObjectNode resObjectNode = factory.objectNode();

        CloseableHttpResponse response = null;
        try {

            if (method.equals(HttpMethod.METHOD_POST)) {
                HttpPost httpPost = new HttpPost(url.toURI());

                if (credential != null) {
                    Token.applyAuthentication(httpPost, credential);
                }
                httpPost.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));

                response = httpClient.execute(httpPost);
            } else if (method.equals(HttpMethod.METHOD_PUT)) {
                HttpPut httpPut = new HttpPut(url.toURI());
                if (credential != null) {
                    Token.applyAuthentication(httpPut, credential);
                }
                httpPut.setEntity(new StringEntity(dataBody.toString(), "UTF-8"));

                response = httpClient.execute(httpPut);
            } else if (method.equals(HttpMethod.METHOD_GET)) {

                HttpGet httpGet = new HttpGet(url.toURI());
                if (credential != null) {
                    Token.applyAuthentication(httpGet, credential);
                }

                response = httpClient.execute(httpGet);

            } else if (method.equals(HttpMethod.METHOD_DELETE)) {
                HttpDelete httpDelete = new HttpDelete(url.toURI());

                if (credential != null) {
                    Token.applyAuthentication(httpDelete, credential);
                }

                response = httpClient.execute(httpDelete);
            }

            HttpEntity entity = response.getEntity();
            if (null != entity) {
                String responseContent = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);

                ObjectMapper mapper = new ObjectMapper();
                JsonFactory factory = mapper.getJsonFactory();
                JsonParser jp = factory.createJsonParser(responseContent);

                resObjectNode = mapper.readTree(jp);
                resObjectNode.put("statusCode", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return resObjectNode;
    }


    /**
     * Create a httpClient instance
     *
     * @param isSSL
     * @return HttpClient instance
     */
    public static HttpClient getClient(boolean isSSL) {

        HttpClient httpClient = new DefaultHttpClient();
        if (isSSL) {
            X509TrustManager xtm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            try {
                SSLContext ctx = SSLContext.getInstance("TLS");

                ctx.init(null, new TrustManager[]{xtm}, null);

                SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

                httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        return httpClient;
    }

    public static URL getURL(String path) {
        URL url = null;

        try {
            url = new URL(PushConstants.API_HTTP_SCHEMA, PushConstants.API_SERVER_HOST, "/" + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Check illegal String
     *
     * @param regex
     * @param str
     * @return
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.lookingAt();
    }
}

