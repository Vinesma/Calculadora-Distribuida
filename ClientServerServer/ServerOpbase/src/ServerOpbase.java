import java.awt.HeadlessException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServerOpbase {

    public static void main(String[] args) 	{
        int port = 7000;
        
        try {
            port = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta que deseja usar (Padrão >= 7000)"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Porta inválida, Porta 7000 escolhida.");
        }
        
        try {
            ServerSocket server = new ServerSocket(7000);
            String str;
            
            Socket switchserver = server.accept();
            InputStream iSwitchServer = switchserver.getInputStream();
            OutputStream oSwitchServer = switchserver.getOutputStream();
            System.out.println(switchserver.getInetAddress() + " conectou no servidor.");
            
            while (true) {
                byte[] line = new byte[100];
                String[] mensagem = new String[100];
                iSwitchServer.read(line);
                str = new String(line);

                mensagem = str.split("\\+");
                Double resultado = 0.0;

                try {
                    resultado = somar(mensagem[0], mensagem[1]);
                    oSwitchServer.write(resultado.toString().getBytes());
                } catch (NumberFormatException e) {
                    oSwitchServer.write("Esperado uma operacao numerica!".getBytes());
                }
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

    private static Double somar(String string, String string0) {
        Double op1;
        Double op2;
        
        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return op1 + op2;
    }
    
    private static Double subtrair(String string, String string0) {
        Double op1;
        Double op2;
        
        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return op1 - op2;
    }
    
    private static Double multiplicar(String string, String string0) {
        Double op1;
        Double op2;
        
        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return op1 * op2;
    }
    
    private static Double dividir(String string, String string0) {
        Double op1;
        Double op2;
        
        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return op1 / op2;
    }
}
