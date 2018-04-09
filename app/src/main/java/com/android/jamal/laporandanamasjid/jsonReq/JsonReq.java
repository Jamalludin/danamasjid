package com.android.jamal.laporandanamasjid.jsonReq;

import com.android.jamal.laporandanamasjid.pojo.Url;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jamal on 25/11/17.
 */

public class JsonReq {


    public JsonReq() {
    }

    public String login (String username, String password){

        String url = Url.koneksi+"danamasjid/login.php";

        try {

            URL masuk = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) masuk.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("username="+username);
            stringBuilder.append("&password="+password);
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(stringBuilder.toString());
            out.flush();
            out.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) !=null){
                stringBuffer.append(line);
            }
            bufferedReader.close();
            return stringBuffer.toString();
            
        }catch (Exception e){

            return e.getMessage();
        }
    }
}
