package com.henrique.coletor;


import java.util.List;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;



public class IntegradorInflux {

    InfluxDB banco;
    String base;
    String id;
    Point point;
    String idpc = "IDPC";
    String cpu1 = "CPU";
    String memoria = "Memoria";
    String disco1 = "Disco";
    String politicaRetencao = "DUAS_SEMANAS";
    
        

    public IntegradorInflux(String base, String porta, String usuario, String senha) {
        this.base = base;
        banco = InfluxDBFactory.connect("http://localhost:" + porta, usuario, senha);
        id = System.getProperty("user.name");
        QueryResult resultado = banco.query(new Query("SHOW DATABASES"));
        
        List<QueryResult.Result> lista = resultado.getResults();
        List<List<Object>> lista1 = lista.get(0).getSeries().get(0).getValues();
        boolean tem = false;
        
        for(List l : lista1){
            if(l.get(0).toString().equals(base)){
                tem=true;
                break;
            }
        }
        if(!tem){
            banco.query(new Query("CREATE DATABASE " + base));
            banco.query(new Query("CREATE RETENTION POLICY "+ politicaRetencao +" ON "+ base + " DURATION 2w REPLICATION 1 DEFAULT"));
        }
        
            
    }
    

    public void inserir(Double cpu, Double ram, Double disco) {
        point = Point.measurement("DadosPerformance")
                .tag(idpc, id)
                .addField(cpu1, cpu)
                .addField(memoria, ram)
                .addField(disco1, disco)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        banco.write(base, politicaRetencao, point);
    }



}
