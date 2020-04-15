package is.hi.hbv601.fitnesstracker.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * This Class communicates with a Java Spring RESTful backend
 */
public class NetworkClient {

    private static final String URL = "https://hugbo2-2020.herokuapp.com/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    /**
     * Posts method Object to backend
     * @param url appended to URL String URL + url
     * @param json object that is posted to url
     * @return Call
     * @throws IOException
     */
    public Call post(String url, String json)  {
        OkHttpClient client = new OkHttpClient();
        url = URL + url;
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request);
    }

    /**
     *
     * @param url
     * @return Call
     */
    public Call get(String url) {
        final String URL = "https://hugbo2-2020.herokuapp.com/";
        OkHttpClient client = new OkHttpClient();
        url = URL + url;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return client.newCall(request);
    }

}