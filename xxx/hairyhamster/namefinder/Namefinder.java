package xxx.hairyhamster.namefinder;
import java.io.*;
import java.net.*;

public class Namefinder {

  public static String response(boolean male) throws Exception {
    final String url = "https://thingnames.com/hamster-names";
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("User-Agent", "HALLO");
    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    con.setDoOutput(true);

    final String urlParameters = String.format("male=%b&requestType=newHamsterName", male);

    try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
      wr.writeBytes(urlParameters);
      wr.flush();
    }

    final int responseCode = con.getResponseCode();
    if (responseCode != 200) {
      return null;
    }

    final StringBuffer response = new StringBuffer();
    try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      String inputLine;
      while((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
    }
    return response.toString();
  }

  private static String name(boolean male) throws Exception {
    final String response = response(male);
    if (response == null) return null;
    final String search = "<p>Your Hamster Name is...</p>";
    final int index = response.indexOf(search) + search.length();
    if (index == -1) {
      System.out.println("Can't find name");
      return null;
    }
    final int start = response.indexOf('>', index) + 1;
    final int end = response.indexOf('<', start);
    final String name = response.substring(start, end);
    return name;
  }

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      System.out.println("Usage: Namefinder count <male/female>");
      return;
    }
    boolean male = true;
    if (args.length == 2) {
      switch(args[1]) {
        case "male":
          male = true;
          break;

        case "female":
          male = false;
          break;
      }
    }
    int count = Integer.parseInt(args[0]);
    for (int i = 0; i < count; i++) {
      final String name = name(male);
      if (name == null) return;
      System.out.println(name);
    }
  }
}
