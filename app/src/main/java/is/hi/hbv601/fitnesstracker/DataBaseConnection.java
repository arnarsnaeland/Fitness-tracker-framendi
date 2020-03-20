//package is.hi.hbv601.fitnesstracker;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL; 

//import com.fasterxml.jackson.core;

public class DataBaseConnection {

    private static final String URL_STRING = "https://hugbo2-2020.herokuapp.com";

    public static void call_me() throws Exception {
        URL obj = new URL(URL_STRING + "/signup");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        //ObjectMapper mapper = new ObjectMapper();

        //String jsonString = mapper.writeValueAsString(staff);

        String jsonString = "{\"userName\": \"fokk\", \"password\": \"fuck\"}";
        con.setDoOutput(true);

		try(OutputStream os = con.getOutputStream()){
			byte[] input = jsonString.getBytes("utf-8");
			os.write(input, 0, input.length);			
		}

        int responseCode = con.getResponseCode();

        System.out.println(responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        System.out.println(response.toString());
        //Read JSON response and print
    }
    public static void main(String args[]) {
        try { 
            DataBaseConnection.call_me();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}