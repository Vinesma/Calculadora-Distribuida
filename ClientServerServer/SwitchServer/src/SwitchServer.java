import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SwitchServer implements Runnable{
    public static final String IP = "127.0.0.1";
    
    public Socket cliente;

    public SwitchServer(Socket cliente){
        this.cliente = cliente;
    }

    public static void main(String[] args) throws IOException{       
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
            
            do {
                byte[] line = new byte[100];
                iCliente.read(line);
                str = new String(line);
                String[] mensagem = new String[100];

                mensagem = str.split("\\+"); //soma
                if (mensagem.length == 2){
                    Socket serveradd = new Socket(IP, 1234);
                    InputStream iServerAdd = serveradd.getInputStream();
                    OutputStream oServerAdd = serveradd.getOutputStream();

                    oServerAdd.write(mensagem[0].getBytes());
                    oServerAdd.write("+".getBytes());
                    oServerAdd.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serveradd.getInetAddress());
                    iServerAdd.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serveradd.getInetAddress());
                    oCliente.write(line);
                    serveradd.close();                        
                }

                mensagem = str.split("\\-"); //subtracao
                if (mensagem.length == 2){
                    Socket serversub = new Socket(IP, 1235);
                    InputStream iServerSub = serversub.getInputStream();
                    OutputStream oServerSub = serversub.getOutputStream();

                    oServerSub.write(mensagem[0].getBytes());
                    oServerSub.write("-".getBytes());
                    oServerSub.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serversub.getInetAddress());
                    iServerSub.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serversub.getInetAddress());
                    oCliente.write(line);
                    serversub.close();
                }

                mensagem = str.split("\\*"); //multiplicacao
                if (mensagem.length == 2){
                    Socket servermult = new Socket(IP, 1236);
                    InputStream iServerMult = servermult.getInputStream();
                    OutputStream oServerMult = servermult.getOutputStream();

                    oServerMult.write(mensagem[0].getBytes());
                    oServerMult.write("*".getBytes());
                    oServerMult.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + servermult.getInetAddress());
                    iServerMult.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + servermult.getInetAddress());
                    oCliente.write(line);
                    servermult.close();
                }

                mensagem = str.split("\\/"); //divisao
                if (mensagem.length == 2){
                    Socket serverdiv = new Socket(IP, 1237);
                    InputStream iServerDiv = serverdiv.getInputStream();
                    OutputStream oServerDiv = serverdiv.getOutputStream();

                    oServerDiv.write(mensagem[0].getBytes());
                    oServerDiv.write("/".getBytes());
                    oServerDiv.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serverdiv.getInetAddress());
                    iServerDiv.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serverdiv.getInetAddress());
                    oCliente.write(line);
                    serverdiv.close();
                }

                mensagem = str.split("\\^"); //potenciacao
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

                mensagem = str.split("\\%"); //porcentagem?
                if (mensagem.length == 2) {
                    Socket serverpor = new Socket(IP, 1239);
                    InputStream iServerPor = serverpor.getInputStream();
                    OutputStream oServerPor = serverpor.getOutputStream();

                    oServerPor.write(mensagem[0].getBytes());
                    oServerPor.write("^".getBytes());
                    oServerPor.write(mensagem[1].getBytes());
                    System.out.println("Mensagem enviada ao servidor:" + serverpor.getInetAddress());
                    iServerPor.read(line);
                    System.out.println("Mensagem retornada pelo servidor:" + serverpor.getInetAddress());
                    oCliente.write(line);
                    serverpor.close();
                }

                mensagem = str.split("\\#"); //raiz quadrada?
                if (mensagem.length == 2) {
                    Socket serverrq = new Socket(IP, 1239);
                    InputStream iServerRq = serverrq.getInputStream();
                    OutputStream oServerRq = serverrq.getOutputStream();

                    oServerRq.write(mensagem[0].getBytes());
                    oServerRq.write("#".getBytes());
                    oServerRq.write(mensagem[1].getBytes());
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
            e.printStackTrace();
        }
    }
}
