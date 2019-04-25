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
                    System.out.println("Cliente: " + cliente.getInetAddress() + " conectou no servidor.");
                    
                    byte[] line = new byte[100];
                    iCliente.read(line);
                    str = new String(line);
                    String[] mensagem = new String[100];
                    
                    mensagem = str.split("\\+");
                    if (mensagem.length == 2){
                        Socket serveradd = new Socket(IP, 1234);
                        InputStream iServerAdd = serveradd.getInputStream();
                        OutputStream oServerAdd = serveradd.getOutputStream();
                        
                        oServerAdd.write(mensagem[0].getBytes());
                        oServerAdd.write("+".getBytes());
                        oServerAdd.write(mensagem[1].getBytes());
                        System.out.println("Mensagem enviada ao servidor:" + serveradd.getInetAddress());
                        iServerAdd.read(line);                        
                        oCliente.write(line);
                        serveradd.close();                        
                    }
                    
                    mensagem = str.split("\\-");
                    if (mensagem.length == 2){
                        Socket serversub = new Socket(IP, 1235);
                        InputStream iServerSub = serversub.getInputStream();
                        OutputStream oServerSub = serversub.getOutputStream();
                        
                        oServerSub.write(mensagem[0].getBytes());
                        oServerSub.write("-".getBytes());
                        oServerSub.write(mensagem[1].getBytes());
                        System.out.println("Mensagem enviada ao servidor:" + serversub.getInetAddress());
                        iServerSub.read(line);
                        oCliente.write(line);
                        serversub.close();
                    }
                    
                    mensagem = str.split("\\*");
                    if (mensagem.length == 2){
                        Socket servermult = new Socket(IP, 1236);
                        InputStream iServerMult = servermult.getInputStream();
                        OutputStream oServerMult = servermult.getOutputStream();
                        
                        oServerMult.write(mensagem[0].getBytes());
                        oServerMult.write("*".getBytes());
                        oServerMult.write(mensagem[1].getBytes());
                        System.out.println("Mensagem enviada ao servidor:" + servermult.getInetAddress());
                        iServerMult.read(line);
                        oCliente.write(line);
                        servermult.close();
                    }
                    
                    mensagem = str.split("\\/");
                    if (mensagem.length == 2){
                        Socket serverdiv = new Socket(IP, 1237);
                        InputStream iServerDiv = serverdiv.getInputStream();
                        OutputStream oServerDiv = serverdiv.getOutputStream();
                        
                        oServerDiv.write(mensagem[0].getBytes());
                        oServerDiv.write("/".getBytes());
                        oServerDiv.write(mensagem[1].getBytes());
                        System.out.println("Mensagem enviada ao servidor:" + serverdiv.getInetAddress());
                        iServerDiv.read(line);
                        oCliente.write(line);
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
