import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.net.Socket;

public class SimpleJavaClient implements Runnable{       
    public static final String IP = "127.0.0.1";
    
    private Socket cliente;
    
    public SimpleJavaClient(Socket cliente){
        this.cliente = cliente;
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException {       
        Socket socket = new Socket(IP, 6666);

        SimpleJavaClient c = new SimpleJavaClient(socket);
        Thread t = new Thread(c);
        t.start();        
    }

    public void run() {
        try {
            Socket switchserver = new Socket(IP, 6666);
            InputStream iSwitchServer = switchserver.getInputStream();
            OutputStream oSwitchServer = switchserver.getOutputStream();
            String str;
            do {
                byte[] line = new byte[100];
                System.out.print("Msg: ");
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
