/*************************************
 * Filename:  SMTPInteraction.java
 * Student-ID: 201353343
 * Date: 02/10/20
 *************************************/
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to mailserver and send one mail.
 *
 */
public class SMTPInteraction {
    /* Socket to the server */
    private Socket connection;

    /* Streams for reading from and writing to socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private static final String CRLF = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPInteraction object. Create the socket and the
       associated streams. Initialise SMTP connection. */
    public SMTPInteraction(EmailMessage mailmessage) throws IOException {
        // Open a TCP client socket with hostname and portnumber specified in
        // mailmessage.DestHost and  mailmessage.DestHostPort, respectively.
	       connection = new Socket("35.246.110.174",1025);

        // attach the BufferedReader fromServer to read from the socket and
        // the DataOutputStream toServer to write to the socket
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      toServer =   new DataOutputStream(connection.getOutputStream());
        //fromServer = new BufferedReader(new InputStreamReader(System.in));
        //toServer = System.out;

	      /* Read one line from server and check that the reply code is 220.
	      If not, throw an IOException. */
        String response = fromServer.readLine();
        System.out.println(response);
        if (!response.startsWith("220")){
          throw new Exception("220 reply not received from server.");
        }

	      /* SMTP handshake. We need the name of the local machine.
	      Send the appropriate SMTP handshake command. */
	       String localhost = InetAddress.getLocalHost().getHostName();
	       sendCommand(toServer.writeBytes());

	       isConnected = true;
      }

    /* Send message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(EmailMessage mailmessage) throws IOException {
	/* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */
	/* Fill in */

  //HELO
  String helo = "HElO user\r\n";
  //sendCommand(helo);
  toServer.writeBytes(helo);

  //MAIL FROM
  String mailFrom = "MAIL FROM\r\n";
  //sendCommand(mailFrom);
  toServer.writeBytes(mailFrom);

  //RCPT TO
  String rcptTo = "RCPT TO\r\n";
  //sendCommand(rcptTO);
  toServer.writeBytes(rcptTo);

  //DATA
  String data = "DATA\r\n";
  //sendCommand(data);
  toServer.writeBytes(data);

  }

    /* Close SMTP connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
	isConnected = false;
	try {
	    sendCommand("QUIT");
	    connection.close();
	} catch (IOException e) {
	    System.out.println("Unable to close connection: " + e);
	    isConnected = true;
	}
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
	/* Write command to server and read reply from server. */
	/* Fill in */

	/* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
	/* Fill in */
    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
	if(isConnected) {
	    close();
	}
	super.finalize();
    }
}
