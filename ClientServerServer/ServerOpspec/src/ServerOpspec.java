import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServerOpspec {

    public static void main(String[] args) 	{
        int port = 6999;
        
        try {
            port = Integer.parseInt(JOptionPane.showInputDialog("Informe a porta que deseja usar (Padrão <= 6999)"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Porta inválida, Porta 6999 escolhida.");
        }
        
        try {
            ServerSocket server = new ServerSocket(port);
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
                Double resultado = 0.0;
                    
                try {
                    mensagem = str.split("\\^");
                    if (mensagem.length == 2) {
                        resultado = potencia(mensagem[0], mensagem[1]);
                        oSwitchServer.write(resultado.toString().getBytes());
                    }
                    mensagem = str.split("\\%");
                    if (mensagem.length == 2) {
                        resultado = porcentagem(mensagem[0], mensagem[1]);
                        oSwitchServer.write(resultado.toString().getBytes());
                    }
                    mensagem = str.split("\\#");
                    if (mensagem.length == 2) {
                        resultado = raizq(mensagem[0]);
                        oSwitchServer.write(resultado.toString().getBytes());
                    }                    
                    if (mensagem == null){
                        oSwitchServer.write("Esperado uma operacao numerica!".getBytes());
                    }
                } catch (NumberFormatException e) {
                    oSwitchServer.write("Esperado uma operacao numerica!".getBytes());
                }
            }                                                              
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

    private static Double potencia(String string, String string0) {
        Double op1;
        Double op2;

        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return Math.pow(op1,op2);
    }
    
    private static Double porcentagem(String string, String string0) {
        Double op1;
        Double op2;
        
        op1 = Double.parseDouble(string);
        op2 = Double.parseDouble(string0);
        
        return (op1 * op2) / 100;
    }
    
    private static Double raizq(String string) {
        Double op1;        
        
        op1 = Double.parseDouble(string);        
        
        return Math.sqrt(op1);
    }
}
