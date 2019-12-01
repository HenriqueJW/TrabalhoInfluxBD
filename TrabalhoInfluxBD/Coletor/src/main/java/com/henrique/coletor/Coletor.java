package com.henrique.coletor;


import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class Coletor {

    static double[] dados;

    public static void main(String[] args) {

        try {
            File f = new File("bin\\ConfiguracoesPC.txt");
            
                f.createNewFile();
                PrintWriter writer;
                writer = new PrintWriter(f, "UTF-8");
                BigDecimal bd = BigDecimal.valueOf(Explorador.getTotalPhysicalMemorySize());
                bd = bd.setScale(0, RoundingMode.DOWN);
                
                BigDecimal bd2 = BigDecimal.valueOf(Explorador.getTotalSpace());
                bd2 = bd2.setScale(0, RoundingMode.DOWN);
                
                writer.write("RAM:" + bd.doubleValue()+System.getProperty("line.separator"));
                
                
                writer.write("HD:" + bd2.doubleValue());
                writer.close();
            

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo: ConfiguracoesBanco.txt não encontrado!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro de leitura!");
        }

        IntegradorInflux integrador = new IntegradorInflux(args[0], args[1], args[2], args[3]); //Precisa passar argumentos ao iniciar (dados da base, user, etc.)
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                dados = Explorador.getDados();
                integrador.inserir(dados[0], dados[1], dados[2]);
            }
        }, 0, 1000);

    }

}
