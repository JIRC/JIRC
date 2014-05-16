/**
 *
 * @author Daniel Atkinson
 */
import java.util.Hashtable;
import java.io.*;
public class OutboundParser 
{
    final Hashtable<String, Integer> commandMap = new Hashtable<String, Integer>();
    String channelName;
    String prefix;
    public OutboundParser(String ch)
    {
        channelName = ch;
        prefix = null;
        commandMap.put("JOIN", 0);
        commandMap.put("PRIVMSG", 1);
    }
    
    public OutboundParser(String ch, String pf)
    {
        channelName = ch;
        prefix = pf;
    }
    
    public String parseString(String msg)
    {
        byte[] receiver;
        String finalMsg = "";
        try
            {
                // Check that the input isn't empty
                if(msg.equals(""))
                {
                    throw new IOException("empty string");
                }
                
                if(prefix!=null)
                {
                    finalMsg += ":" + prefix;
                }
                
                // Check for command
                String msgCommand;
                if(msg.charAt(0) == '/')
                {
                    int commandEnd = msg.indexOf(" ");
                    msgCommand = msg.substring(1, commandEnd);
                    msg = msg.substring(commandEnd+1);
                    finalMsg = finalMsg + msgCommand + " ";
                    if(!checkCommands(msgCommand)) 
                    { 
                        throw new IOException("invalid command"); 
                    }
                }
                else
                {
                    msgCommand = "PRIVMSG";
                    finalMsg += msgCommand + " ";
                    msg = channelName + " " + msg;
                }
                
                // Attach all parameters before message
                int paramNum = commandMap.get(msgCommand.toUpperCase());
                int currentNum = 0;
                System.out.println(paramNum + " " + currentNum);
                while (currentNum < paramNum)
                {
                    System.out.println("run");
                    int paramEnd = msg.indexOf(" ");
                    if(paramEnd == -1)
                    {
                        paramEnd = msg.length();
                    }
                    finalMsg += msg.substring(0, paramEnd);
                    msg = msg.substring(paramEnd+1);
                    currentNum++;
                }
                finalMsg += ":" + msg;
                System.out.println(finalMsg);
                
                // Convert to bytes
                receiver = finalMsg.getBytes("UTF-8");
                finalMsg = convertToHex(receiver);
                finalMsg += "0d0a";
                System.out.println(finalMsg);
            }
            catch(IOException e)
            {
                receiver = null;
                System.out.println(e);
            }
            return finalMsg;
    }
    public byte[] convertToByte(String s)
    {
        byte[] processedText = new byte[s.length()];
        for (int i = 0; i < s.length(); i++)
        {
            // exceptions
            if (s.charAt(i) == ' ')
            {
                
            }
        }
        return (processedText);
    }
    
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    
    public static String convertToHex(byte[] b)
    {
        char[] chars = new char[2 * b.length];
        for (int i = 0; i < b.length; i++)
        {
            chars[2 * i] = HEX_CHARS[(b[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[b[i] & 0x0F];
        }
        return new String(chars);
    }
    
    private boolean checkCommands(String command)
    {
        command = command.toUpperCase();
        return commandMap.containsKey(command);
    }
}