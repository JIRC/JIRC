import java.io.*;
import java.net.*;

public class JIRC {

    public static void main(String[] args) throws Exception {

        // The server to connect to and the details for login (currently placeholders)
        String server = "irc.freenode.net";
        String nick = "nicktest";
        String login = "logintest";

        // The channel to join (currently placeholder)
        String channel = "#irchacks";

        // Connect directly to the IRC server.
        Socket socket = new Socket(server, 6667);
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream( )));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream( )));

        // Log on to server
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER " + login + " 8 * : JIRC \r\n");
        writer.flush( );

        // Read until connected
        String line = null;
        while ((line = reader.readLine( )) != null) {
            if (line.indexOf("004") >= 0) {
                // We are now logged in.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nickname is already in use.");
                return;
            }
        }

        // Join channel
        writer.write("JOIN " + channel + "\r\n");
        writer.flush( );

        while ((line = reader.readLine( )) != null) {
            if (line.toLowerCase( ).startsWith("PING ")) {
                // We must respond to PINGs to avoid being disconnected.
                writer.write("PONG " + line.substring(5) + "\r\n");
                writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush( );
            }
            else {
                System.out.println(line);
            }
        }
    }

}