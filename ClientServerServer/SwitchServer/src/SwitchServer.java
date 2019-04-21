import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SwitchServer {

    public static void main(String[] args) 	{
        final String IP = "127.0.0.1";
        try {
            ServerSocket switchserver = new ServerSocket(6666);
            String str;
            while (true) {
                Socket cliente = switchserver.accept();
                InputStream iCliente = cliente.getInputStream();
                OutputStream oCliente = cliente.getOutputStream();
                
                do {
                    byte[] line = new byte[100];
                    iCliente.read(line);
                    str = new String(line);
                    String[] mensagem = new String[100];
                    
                    mensagem = str.split("\\+");
                    if (mensagem.length == 2){
                        Socket serveradd = new Socket(IP, 1234);
                        InputStream iServerAdd = serveradd.getInputStream();
                        OutputStream oServerAdd = serveradd.getOutputStream();
                        
                        oServerAdd.write(line);
                        iServerAdd.read(line);
                        oCliente.write(line.toString().getBytes());
                        serveradd.close();                        
                    }
                    
                    mensagem = str.split("\\-");
                    if (mensagem.length == 2){
                        Socket serversub = new Socket(IP, 1235);
                        InputStream iServerSub = serversub.getInputStream();
                        OutputStream oServerSub = serversub.getOutputStream();
                        
                        oServerSub.write(line);
                        iServerSub.read(line);
                        oCliente.write(line.toString().getBytes());
                        serversub.close();
                    }
                    
                    mensagem = str.split("\\*");
                    if (mensagem.length == 2){
                        Socket servermult = new Socket(IP, 1236);
                        InputStream iServerMult = servermult.getInputStream();
                        OutputStream oServerMult = servermult.getOutputStream();
                        
                        oServerMult.write(line);
                        iServerMult.read(line);
                        oCliente.write(line.toString().getBytes());
                        servermult.close();
                    }
                    
                    mensagem = str.split("\\/");
                    if (mensagem.length == 2){
                        Socket serverdiv = new Socket(IP, 1237);
                        InputStream iServerDiv = serverdiv.getInputStream();
                        OutputStream oServerDiv = serverdiv.getOutputStream();
                        
                        oServerDiv.write(line);
                        iServerDiv.read(line);
                        oCliente.write(line.toString().getBytes());
                        serverdiv.close();
                    }
                    
                    if (mensagem == null){
                        throw new IllegalArgumentException("Falta do operador");
                    }
                    str = new String(line);
                } while ( !str.trim().equals("bye") );
                cliente.close();                
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }
}
