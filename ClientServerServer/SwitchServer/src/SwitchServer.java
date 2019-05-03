import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.ArrayList;

public class SwitchServer implements Runnable{
    public static final String IP = "127.0.0.1";
    
    private static List<Socket> SocketListOpbase = new ArrayList<Socket>();
    private static List<Socket> SocketListOpspec = new ArrayList<Socket>();
    
    public Socket cliente;

    public SwitchServer(Socket cliente){
        this.cliente = cliente;
    }

    public static void main(String[] args) throws IOException, SocketException{       
        try {
            ServerSocket switchserver = new ServerSocket(6666);
            System.out.println("Aguardando conexao do cliente...");
            
            while (true) {
                Socket cliente = switchserver.accept();
                SwitchServer tratamento = new SwitchServer(cliente);
                Thread t = new Thread(tratamento);
                t.start();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

    public void run() {
        String str;        
        try {
            InputStream iCliente = this.cliente.getInputStream();
            OutputStream oCliente = this.cliente.getOutputStream();
            System.out.println("Cliente: " + cliente.getInetAddress() + " conectou no servidor.");
            
            Socket serveropbase = new Socket(IP, 1234);
            SocketListOpbase.add(serveropbase);
            
            Socket serveropspec = new Socket(IP, 1235);
            SocketListOpspec.add(serveropspec);
//            InputStream iServerOpbase = serveropbase.getInputStream();
//            OutputStream oServerOpbase = serveropbase.getOutputStream();

            do {
                byte[] line = new byte[100];
                iCliente.read(line);
                str = new String(line);
                String[] mensagem = new String[100];                
                
                                
                mensagem = str.split("\\+"); //soma
                if (mensagem.length == 2) {
                    
                }
                mensagem = str.split("\\-"); //subtracao
                mensagem = str.split("\\*"); //multiplicacao
                mensagem = str.split("\\/"); //divisao
                if (mensagem.length == 2){                    
                    InputStream iServerOpbase = serveropbase.getInputStream();
                    OutputStream oServerOpbase = serveropbase.getOutputStream();

                    oServerOpbase.write(mensagem[0].getBytes());
                    oServerOpbase.write("+".getBytes());
                    oServerOpbase.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serveropbase.getInetAddress());
                    iServerOpbase.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serveropbase.getInetAddress());
                    oCliente.write(line);
                    serveropbase.close();                        
                }              

                mensagem = str.split("\\^"); //potenciacao
                mensagem = str.split("\\%"); //porcentagem
                if (mensagem.length == 2) {
                    Socket serverpot = new Socket(IP, 1238);
                    InputStream iServerPot = serverpot.getInputStream();
                    OutputStream oServerPot = serverpot.getOutputStream();

                    oServerPot.write(mensagem[0].getBytes());
                    oServerPot.write("^".getBytes());
                    oServerPot.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serverpot.getInetAddress());
                    iServerPot.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serverpot.getInetAddress());
                    oCliente.write(line);
                    serverpot.close();
                }
                
                if (str.contains("#")) { //raiz quadrada
                    Socket serverrq = new Socket(IP, 1240);
                    InputStream iServerRq = serverrq.getInputStream();
                    OutputStream oServerRq = serverrq.getOutputStream();

                    oServerRq.write(str.getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serverrq.getInetAddress());
                    iServerRq.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serverrq.getInetAddress());
                    oCliente.write(line);
                    serverrq.close();
                }

                if (mensagem == null){
                    throw new IllegalArgumentException("Falta do operador");
                }
                str = new String(line);
            } while ( !str.trim().equals("bye") );           
            this.cliente.close();
        } catch (IOException e) {
            System.err.println("Cliente desconectado. Mensagem: " + e);
        }
    }
}
