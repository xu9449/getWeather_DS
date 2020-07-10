

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Send_HTTP_Request {
        public static void main(String[] args) {

            // Find the text file named "test.txt"
            File file = new File("test.txt");

            // Create a new file named "result.txt"
            try {
                File myObj = new File("result.txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }




            // Scan the test.txt file and destract latitude and longtitude
            try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name())) {
                String pattern = "(.*)\\° (.), (.*)\\° (.)";
                Pattern p= Pattern.compile(pattern);

                //create the file writer
                FileWriter writer = new FileWriter("result.txt");

                // scan the test.txt line by line and write results to the result.txt
                while (sc.hasNextLine()){
                    Matcher m = p.matcher(sc.nextLine());
                    if(m.find()){
                        String lat = m.group(1);
                        String lon = m.group(3);

                        if(m.group(2).equals("S")){
                            lat = "-"+lat;
                        }
                        if(m.group(4).equals("W")){
                            lon = "-"+lon;
                        }
                        // write the temperature result to result.txt
                        String temp = Combine(lat,lon);
                        if(sc.hasNextLine()) {
                            writer.write(temp + ',');
                        } else {
                            writer.write(temp);
                        }
                    }
                }
                writer.close();
                System.out.println("Successfully wrote all the datas");
            }
            catch (IOException e) {
                e.printStackTrace();
            }



        }
        // Method to return the temperature by input latitude and longtitude
        public static String Combine(String lat, String lon){
            String res = " ";
            try {
                JSONObject js = send_request("https://api.weather.gov/points/"+lat+","+lon);
                JSONObject js2 = send_request(findURL(js));
                res = findTemperature(js2).toString();
            } catch (Exception e){
                e.printStackTrace();
            }
            return res;
        }


        // Method to send request and get Json responds
        public static JSONObject send_request(String url) throws IOException {

            URL obj = new URL(url);
            HttpsURLConnection co = (HttpsURLConnection) obj.openConnection();
            // set default request method to 'GET'
            co.setRequestMethod("GET");
            int resCode = co.getResponseCode();
            System.out.println("Sending 'GET' request to URL: " + url);
            System.out.println("Response Code : " + resCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(co.getInputStream()));
            String input;
            StringBuffer response = new StringBuffer();
            while((input = in.readLine()) != null){
                response.append(input);
            }
            in.close();
            return new JSONObject(response.toString());
        }

        public static String findURL(JSONObject jsonObject){
            return jsonObject.getJSONObject("properties").getString("forecast");
        }

        public static Integer findTemperature(JSONObject jsonObject){
            JSONObject propertise = jsonObject.getJSONObject("properties");
            JSONArray period = propertise.getJSONArray("periods");
            DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
            for(Object obj : period){
                JSONObject jsb = (JSONObject) obj;
                if(dayOfWeek == DayOfWeek.WEDNESDAY){
                    if(jsb.getString("name").equals("Tonight")){
                        return jsb.getInt("temperature");
                    }
                } else {
                    if (jsb.getString("name").equals("Wednesday Night")) {
                        return jsb.getInt("temperature");
                    }
                }
            }
            return -1;
        }


}
