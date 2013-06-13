import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class HTTPTest {

  /**
   * @param args
 * @throws UnsupportedEncodingException 
   */
  public static void main(String[] args) throws UnsupportedEncodingException {
      HttpURLConnection connection = null;
      BufferedReader rd  = null;
      StringBuilder sb = null;
      String line = null;
    
      URL serverAddress = null;
      String charset = "UTF-8";
      
      String login = ""; // voeg naam toe
      String password = ""; // voeg ww toe
      String query = String.format("login=%s&password=%s", 
           URLEncoder.encode(login, charset), 
           URLEncoder.encode(password, charset));
    
      try {
          serverAddress = new URL("https://www.uscki.nl/~kruit/zebra/?pagina=Smobo&api");
          //set up out communications stuff
          connection = null;
        
          //Set up the initial connection
          connection = (HttpURLConnection)serverAddress.openConnection();
          connection.setRequestMethod("GET");
          connection.setDoOutput(true);
          connection.setReadTimeout(10000);
          connection.setRequestProperty("Accept-Charset", charset);
          connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
          
          connection.connect();

          OutputStream output = null;
          try {
               output = connection.getOutputStream();
               output.write(query.getBytes(charset));
          } finally {
               if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
          }
        
          //read the result from the server
          rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          sb = new StringBuilder();
        
          while ((line = rd.readLine()) != null)
          {
              sb.append(line + '\n');
          }
        
          System.out.println(sb.toString());
                    
      } catch (MalformedURLException e) {
          e.printStackTrace();
      } catch (ProtocolException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      finally
      {
          //close the connection, set all objects to null
          connection.disconnect();
          rd = null;
          sb = null;
          connection = null;
      }
  }
}
