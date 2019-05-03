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
    
    private static List<Socket> SocketList = new ArrayList<Socket>();    
    
    public Socket cliente;

    public SwitchServer(Socket cliente){
        this.cliente = cliente;
    }

    public static void main(String[] args) throws IOException, SocketException{
        int n = 7000;
        try {
            ServerSocket switchserver = new ServerSocket(6666);
            System.out.println("Aguardando conexao do cliente...");
            
            try {
                while (true) {                    
                    Socket serveropbase = new Socket(IP, n);
                    SocketList.add(serveropbase);
                    n++;
                }
            } catch (IOException e){}
            n = 6999;
            try {
                while (true) {                    
                    Socket serveropspec = new Socket(IP, n);
                    SocketList.add(serveropspec);
                    n--;
                }
            } catch (IOException e){}
            
            if (SocketList.isEmpty()) {
                throw new IOException("Nenhum servidor escravo conectado!");
            }
            
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

            do {
                byte[] line = new byte[100];
                iCliente.read(line);
                str = new String(line);
                String[] mensagem = new String[100];                
                
                                
                mensagem = str.split("\\+"); //soma
                if (mensagem.length == 2) {
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() >= 7000) {
                            oCliente.write(SendToServer(mensagem, line, "+"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }
                mensagem = str.split("\\-"); //subtracao
                if (mensagem.length == 2) {
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() >= 7000) {
                            oCliente.write(SendToServer(mensagem, line, "-"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }
                mensagem = str.split("\\*"); //multiplicacao
                if (mensagem.length == 2) {
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() >= 7000) {
                            oCliente.write(SendToServer(mensagem, line, "*"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }
                mensagem = str.split("\\/"); //divisao
                if (mensagem.length == 2) {
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() >= 7000) {
                            oCliente.write(SendToServer(mensagem, line, "/"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }
                
                mensagem = str.split("\\^"); //potenciacao
                if (mensagem.length == 2) {
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() < 7000) {
                            oCliente.write(SendToServer(mensagem, line, "^"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }
                mensagem = str.split("\\%"); //porcentagem
                if (mensagem.length == 2) {
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() < 7000) {
                            oCliente.write(SendToServer(mensagem, line, "%"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }
                
                if (str.contains("#")) { //raiz quadrada
                    mensagem = str.split("\\#");
                    boolean done = false;
                    while (!done) {
                        if (SocketList.get(0).getPort() < 7000) {
                            oCliente.write(SendToServer(mensagem, line, "#"));
                            ShiftToLast();
                            done = true;
                        }else{
                            ShiftToLast();
                        }
                    }
                }

                if (mensagem == null){
                    throw new IllegalArgumentException("Falta do operador");
                }
                str = new String(line);
            } while ( !str.trim().equals("bye") );           
            this.cliente.close();
//            SocketList.forEach(SocketList.get(0).close());
        } catch (IOException e) {
            System.err.println("Cliente desconectado. Mensagem: " + e);
        }
    }

    private void ShiftToLast() {        
        SocketList.add(SocketList.remove(0));
    }

    private byte[] SendToServer(String[] mensagem, byte[] line, String op) throws IOException {
        InputStream iServerOpbase = SocketList.get(0).getInputStream();
        OutputStream oServerOpbase = SocketList.get(0).getOutputStream();
        
        oServerOpbase.write(mensagem[0].getBytes());
        oServerOpbase.write(op.getBytes());
        if (!op.equals("#")) {
            oServerOpbase.write(mensagem[1].getBytes());
        }
        System.out.println("Mensagem enviada ao servidor:" + SocketList.get(0).getInetAddress());
        iServerOpbase.read(line);
        System.out.println("Mensagem retornada pelo servidor:" + SocketList.get(0).getInetAddress());
        return line;
    }
}
