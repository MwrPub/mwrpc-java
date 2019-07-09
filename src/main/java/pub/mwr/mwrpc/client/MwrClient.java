package pub.mwr.mwrpc.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;

public class MwrClient {

    private String urlString;

    public MwrClient(String host, long port, boolean isHttps) {
        String scheme = isHttps ? "https://" : "http://";
        this.urlString = scheme + host + ":" + port + "/";
    }

    public Object init(final Class endpoint) {
        String urlStr = this.urlString + endpoint.getSimpleName().toLowerCase() + "/";
        return Proxy.newProxyInstance(endpoint.getClassLoader(), new Class[]{endpoint}, (proxy, method, args) -> {
            URL url = new URL(urlStr + method.getName().toLowerCase());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream out = new DataOutputStream(connection.getOutputStream());

            JSONObject object = new JSONObject();
            String requestBody;
            if (args != null) {
                object.put("param", args);
                requestBody = object.toString();
            } else {
                requestBody = "{\"param\":[]}";
            }

            out.write(requestBody.getBytes());
            out.flush();
            out.close();

            int responseCode = connection.getResponseCode();
            String responseBody = null;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                responseBody = response.toString();
            }
            if (responseBody != null) {
                JSONObject result = new JSONObject(responseBody);
                return result.get("result");
            }
            return 0;
        });
    }
}
