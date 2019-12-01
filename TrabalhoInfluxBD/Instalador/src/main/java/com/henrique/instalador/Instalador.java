package com.henrique.instalador;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class Instalador {

    public static void criarInstalador() {
        
         String diretorio = JOptionPane.showInputDialog("Insira o diretório do banco de dados Influx, ou seja, a pasta que contém o influx.exe e o influxd.exe");
         String base = JOptionPane.showInputDialog("insira o nome da database desejada");
         String porta = JOptionPane.showInputDialog("insira a porta em que o banco opera");
         String usuario = JOptionPane.showInputDialog("insira o nome do usuario do banco");
         String senha = JOptionPane.showInputDialog("insira a senha do banco");
        
        String dir = System.getProperty("user.home");

        dir += "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";

        final File file = new File(dir + "\\AutoIniciarColetor.bat");
        final File file2 = new File(System.getProperty("user.dir") + "\\InicializadorColetor.bat");
        final File file3 = new File(System.getProperty("user.dir") + "\\bin\\ConfiguracoesBanco.txt");
        final File file4 = new File(System.getProperty("user.dir") + "\\TelaDeSugestões.bat");
        

        
        String separador = System.getProperty("line.separator");
        String separador1 = System.getProperty("file.separator");
        
        try {
            file.createNewFile();
            PrintWriter writer;
            writer = new PrintWriter(file, "UTF-8");
            writer.write("start "+diretorio+ separador1+"influxd");
            writer.write(separador);
            writer.write("timeout 10");
            writer.write(separador);
            writer.write("start javaw -Xmx200m -jar " +"\""+System.getProperty("user.dir")+"\"" + separador1 + "bin" + separador1
            + "Coletor-1.0.jar " + base + " " + porta + " " + usuario + " " + senha);
            writer.write(separador);
            writer.write("exit");
            writer.close();
            
            file2.createNewFile();
            writer = new PrintWriter(file2, "UTF-8");
            writer.write("start "+diretorio+ separador1+"influxd");
            writer.write(separador);
            writer.write("timeout 10");
            writer.write(separador);
            writer.write("start javaw -Xmx200m -jar " + "\""+System.getProperty("user.dir")+"\""+ separador1 + "bin" + separador1
            + "Coletor-1.0.jar " + base + " " + porta + " " + usuario + " " + senha);
            writer.write(separador);
            writer.write("exit");
            writer.close();
            
            file3.createNewFile();
            writer = new PrintWriter(file3, "UTF-8");
            writer.write(porta+separador+usuario+separador+senha+separador+base);
            writer.close();
            
            file4.createNewFile();
            writer = new PrintWriter(file4, "UTF-8");
            writer.write("start "+diretorio+ separador1+"influxd");
             writer.write(separador);
             writer.write("timeout 10");
             writer.write(separador);
            writer.write("start javaw -Xmx200m -jar " +"\""+System.getProperty("user.dir")+"\"" + separador1 + "bin" + separador1
            + "TelasM-1.0.jar");
            writer.write(separador);
            writer.write("exit");
            writer.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Instalador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    
    public static void main(String[] args) {
        Instalador.criarInstalador();
        
    }
    
    
}




