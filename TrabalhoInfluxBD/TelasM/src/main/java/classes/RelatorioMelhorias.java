/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Micro
 */
public class RelatorioMelhorias {
    
    
    private double RAM;
    private double CPU;
    private double HD;
    private int RAMtotal;
    private double HDtotal;

    public RelatorioMelhorias(ListaRegistros lr) {
        
        double mediaCPU =0;
        double mediaRAM=0;
        double mediaHD=0;
        
        
        List<List<Double>> lista = lr.getRegistro();
        
        for(List<Double> l:lista){
            mediaCPU += l.get(0);
            mediaRAM += l.get(1);
            mediaHD += l.get(2);
            
        }
        
        mediaCPU = mediaCPU/lista.size();
        mediaRAM = mediaRAM/lista.size();
        mediaHD = mediaHD/lista.size();
        
        mediaCPU = BigDecimal.valueOf(mediaCPU)
        .setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
        
        mediaRAM = BigDecimal.valueOf(mediaRAM)
        .setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
        
        mediaHD = BigDecimal.valueOf(mediaHD)
        .setScale(2, RoundingMode.HALF_UP)
        .doubleValue();
        

        this.RAM = mediaRAM;
        this.CPU = mediaCPU;
        this.HD = mediaHD;
        
        try {
            BufferedReader bf = new BufferedReader(new FileReader("bin\\ConfiguracoesPC.txt"));
            RAMtotal = (int)Double.parseDouble(bf.readLine().split(":")[1]);
            HDtotal  = Double.parseDouble(bf.readLine().split(":")[1]);
        } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, "Arquivo: ConfiguracoesPC.txt não encontrado!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro de leitura!");
        }
    }
    
    

    
    
    public String getRelatorio(){
        String str = "";
        
        str+= verificarCPU()+ "\n";
        str+= verificarRAM()+ "\n";
        str+= verificarHD();
        
        return str;
        
    }
    
    private String verificarCPU(){
        if(this.CPU > 75){
            return "O seu processador está sendo utilizado demais. Com uma Média de: "+ CPU +" % de uso." + 
                    "\nÉ altamente recomendado que você atualize seu processador o mais rapido possivel";
        }else if (this.CPU > 50){
            return "O seu processador está sendo utilizado bastante. Com uma Média de: "+ CPU +" % de uso."   
                    +"\nNão há uma grande necessidade de trocar seu processador";
        }else{
            return "Seu processador está sendo utilizado em uma frequencia baixa. Com uma Média de: "+ CPU +" % de uso."
                    +"\nNão é necessário atualizar seu processador";
        }
    }
    
    private String verificarHD(){
        double usagemHD = ((double)this.HD/(double)this.HDtotal);
        usagemHD = usagemHD * 100d;
        if(usagemHD > 90){
            return "\nSeu HD está "+ usagemHD +"% cheio, é recomendado realizar uma limpeza de disco, ou adquirir um adicional."
                    + "\n Quantidade usada: " + HD + " GB.";
        }else if(usagemHD > 75){
            return "\nSeu HD está "+ usagemHD + "% cheio, é recomendado uma limpeza de disco.  Quantidade usada: " + HD + " GB.";
        }else{
            return "\nSeu HD ainda não precisa de upgrade. Quantidade usada: " + HD + " GB.";
        }
    }
    
    private String verificarRAM(){
        
        double usagemRAM = ((double)this.RAM/(double)this.RAMtotal);
        usagemRAM = usagemRAM * 100d;
        if(usagemRAM > 80){
            return "\nO seu uso de RAM está muito alto, o uso médio é cerca de"
                    + "\n"+ usagemRAM + "% do seu total de "+ RAMtotal + " GB. " +
                    "\nÉ extremamente recomendado que atualize para "+getProximaRAM()+"GB";
        }else if(usagemRAM >50){
            return "\nO seu uso de RAM está um pouco alto, o uso médio é cerca de"
                    + "\n"+ usagemRAM + "% do seu total de "+ RAMtotal + " GB. " +
                    "\nÉ recomendado adquirir uma memória RAM de "+getProximaRAM()+
                    "GB em um futuro proximo";
        }else{
            return "\nO seu uso de RAM está baixo, cerca de "+ usagemRAM + "%. "+ "não é necessario nenhum upgrade";
        }
    }
    
    private int getProximaRAM(){
        int resultado = 0;
        switch(this.RAMtotal){
                case (2): resultado = 4;
                        break;
                case(4): resultado = 8;
                        break;
                case(6): resultado = 10;
                        break;
                case(8): resultado = 12;
                        break;
        }
        return resultado;
    }
    
    
    
}
