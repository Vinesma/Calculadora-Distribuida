import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleJavaClient {       

    public static void main(String[] args) 	{       
        final String IP = "127.0.0.1";
        try {
            Socket switchserver = new Socket(IP, 6666);
            InputStream iSwitchServer = switchserver.getInputStream();
            OutputStream oSwitchServer = switchserver.getOutputStream();
            String str;
            do {
                byte[] line = new byte[100];
                System.in.read(line);
                oSwitchServer.write(line);
                iSwitchServer.read(line);
                str = new String(line);
                System.out.println(str.trim());
            } while ( !str.trim().equals("bye") );
            switchserver.close();
        }
        catch (Exception err) {
            System.err.println(err);
        }
    }
}
