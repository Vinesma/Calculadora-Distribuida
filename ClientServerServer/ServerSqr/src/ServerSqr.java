import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSqr {

    public static void main(String[] args) 	{        
        try {
            ServerSocket server = new ServerSocket(1240);
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
                Double resultado = 0.0;
                
                mensagem = str.split("\\#");
                
                try {
                    resultado = raizq(mensagem[0]);                    
                    oSwitchServer.write(resultado.toString().getBytes());
                } catch (NumberFormatException e) {
                    oSwitchServer.write("Esperado uma operacao numerica!".getBytes());
                }                                                                       
                switchserver.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

    private static Double raizq(String string) {
        Double op1;        
        
        op1 = Double.parseDouble(string);        
        
        return Math.sqrt(op1);
    }
}
