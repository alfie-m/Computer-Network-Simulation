import java.io.*;
import java.net.*;

public class EmailSender
{
   public static void main(String[] args) throws Exception
   {
      // Establish a TCP connection with the mail server.
      Socket socket = new Socket("35.246.110.174",1025);

      // Create a BufferedReader to read a line at a time.
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);

      // Read greeting from the server.
      String response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("220")) {
         throw new Exception("220 reply not received from server.");
      }

      // Get a reference to the socket's output stream.
      DataOutputStream os = new DataOutputStream(socket.getOutputStream());

      // Send HELO command and get server response.
      String command = "HELO alice\r\n";
      System.out.print(command);
      os.writeBytes(command);
      response = br.readLine();
      System.out.println(response);
      if (!response.startsWith("250")) {
         throw new Exception("250 reply not received from server.");
      }

      // Send MAIL FROM command.



      // Send RCPT TO command.



      // Send DATA command.



      // Send message data.
      // End with line with a single period.



      // Send QUIT command.



   }
}
