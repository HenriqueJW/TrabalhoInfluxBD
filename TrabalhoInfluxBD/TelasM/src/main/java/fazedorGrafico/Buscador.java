/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fazedorGrafico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;

/**
 *
 * @author Micro
 */
public class Buscador {

    InfluxDB banco;
    public static String base;

    public Buscador() {
        String porta = "";
        String usuario = "";
        String senha = "";

        try {

//            BufferedReader bf = new BufferedReader(new FileReader("bin\\ConfiguracoesBanco.txt"));
            BufferedReader bf = new BufferedReader(new FileReader("bin\\ConfiguracoesBanco.txt"));
            porta = bf.readLine();
            usuario = bf.readLine();
            senha = bf.readLine();
            base = bf.readLine();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Arquivo: ConfiguracoesBanco.txt não encontrado!");
//            JOptionPane.showConfirmDialog(null, System.getProperty("user.dir"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro de leitura!");
        }

        banco = InfluxDBFactory.connect("http://localhost:" + porta, usuario, senha);
    }


    public Instant queryComeco() {

        QueryResult resultado = banco.query(new Query("select * from DadosPerformance order by time limit 1", base));

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<registroPc> listaRegistros = resultMapper
                .toPOJO(resultado, registroPc.class);

        return listaRegistros.get(0).getTime();
    }

    public Instant queryFim() {

        QueryResult resultado = banco.query(new Query("select * from DadosPerformance order by time desc limit 1", base));

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<registroPc> listaRegistros = resultMapper
                .toPOJO(resultado, registroPc.class);

        return listaRegistros.get(0).getTime();
    }

    public List<List<Double>> queryDias() {
       
        long tempo = System.currentTimeMillis();
         System.out.println(tempo);
         
        Date comeco = Date.from(queryComeco());

        Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c1.setTime(comeco);
        c1.setTimeZone(TimeZone.getTimeZone("BRT"));

        Date fim = Date.from(queryFim());
        Calendar c2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c2.setTime(fim);
        c2.setTimeZone(TimeZone.getTimeZone("BRT"));

        Calendar c3 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c3.setTime(comeco);
        c3.setTimeZone(TimeZone.getTimeZone("BRT"));

        Calendar c4 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        c4.setTime(comeco);
        c4.setTimeZone(TimeZone.getTimeZone("BRT"));

        long dias = TimeUnit.DAYS.convert(c2.getTimeInMillis() - c1.getTimeInMillis(), TimeUnit.MILLISECONDS);

        LinkedList<List<Double>> listaSemana = new LinkedList<>();

        int i = 0;
        int controle = 0;
        do {
            c3.add(Calendar.DAY_OF_MONTH, controle);
            c4.add(Calendar.DAY_OF_MONTH, 1);

            //select * from DadosPerformance where time > 1574626607863000000 and time < 
            QueryResult resultado = banco.query(new Query("select * from DadosPerformance where time > " + c3.getTimeInMillis() + "000000" + " and time < "
                    + c4.getTimeInMillis() + "000000", base));

            double somaCPU = 0;
            double somaRAM = 0;
            double somaDisco = 0;

            List<List<Object>> listaRegistros = resultado.getResults().get(0).getSeries().get(0).getValues();
            for (List<Object> l : listaRegistros) {
                somaCPU += (double) l.get(1);
                somaRAM += (double) l.get(4);
                somaDisco += (double) l.get(2);
            }
            LinkedList<Double> listaDias = new LinkedList<>();
            listaDias.add(somaCPU / (double) listaRegistros.size());
            listaDias.add(somaRAM / (double) listaRegistros.size());
            listaDias.add(somaDisco / (double) listaRegistros.size());

            listaSemana.add(listaDias);
            
            if (i == 0) {
                controle = 1;
            }
            i++;

        } while (i <= dias);
        
        tempo = System.currentTimeMillis() - tempo;
                System.out.println(tempo);
        return listaSemana;

    }

   
}
