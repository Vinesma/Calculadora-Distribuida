import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSub {

    public static void main(String[] args) 	{
        try {
            ServerSocket server = new ServerSocket(1235);
            String str;
            while (true) {
                Socket switchserver = server.accept();
                InputStream iSwitchServer = switchserver.getInputStream();
                OutputStream oSwitchServer = switchserver.getOutputStream();
                    
                System.out.println(switchserver.getInetAddress() + " conectou no servidor.");
                byte[] line = new byte[100];
                String[] mensagem = new String[100];
                iSwitchServer.read(line);
                str = new String(line);
                    
                mensagem = str.split("\\-");
                Double resultado = 0.0;

                resultado = subtrair(mensagem[0], mensagem[1]);
                    
                oSwitchServer.write(resultado.toString().getBytes());
                    
                switchserver.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

    private static Double subtrair(String string, String string0) {
        Double op1;
        Double op2;
        
        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return op1 - op2;
    }
}
