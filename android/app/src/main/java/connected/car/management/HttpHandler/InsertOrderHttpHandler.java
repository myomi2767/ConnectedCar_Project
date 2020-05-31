package connected.car.management.HttpHandler;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

// 아무것도 안붙여서 보낼 때 쓴다.
public class InsertOrderHttpHandler {
    public static String requestData(String urlstr, JSONObject jo) {
        String result = null;
        HttpURLConnection conn = null;
        InputStream is;
        OutputStream os = null;
        URL url;
        try {
            url = new URL(urlstr);
            conn = (HttpURLConnection) url.openConnection();
            Log.d("---", "url: " + url.toString());
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/String;charset=utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("---","Error between connection_InertMenu");
        }
        try {
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            out.write(jo.toString());
            out.flush();
            Log.d("---","Data Sended to Server" + jo);
            conn.getInputStream();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn.getOutputStream() != null) {
                    conn.getOutputStream().close();
                    conn.disconnect();
                }
                if (conn != null) {
                    conn.disconnect();
                    conn = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static String convertJsonToStr(InputStream is) {
        BufferedReader bi = null;
        StringBuilder sb = new StringBuilder();
        try {
            bi = new BufferedReader(new InputStreamReader(is));
            String temp = "";
            // 스트링 빌더를 이용하여 라인별로 읽고 쌓은 후 //
            while ((temp = bi.readLine()) != null) {
                sb.append(temp);
            }
            Log.d("---","sb: "+sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}