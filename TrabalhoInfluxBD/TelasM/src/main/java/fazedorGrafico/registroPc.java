package fazedorGrafico;

import java.time.Instant;

import java.util.concurrent.TimeUnit;

import org.influxdb.annotation.Column;

import org.influxdb.annotation.Measurement;

@Measurement(name = "DadosPerformance", timeUnit = TimeUnit.MILLISECONDS)

public class registroPc {

    @Column(name = "time")

    private Instant time;

    @Column(name = "IDPC")
    private String idpc;

    @Column(name = "CPU")
    private double cpu;

    @Column(name = "Memoria")
    private double ram;
    
    @Column(name = "Disco")
    private double disco;

    public Instant getTime() {

        return time;

    }

    public void setTime(Instant time) {

        this.time = time;

    }

    public String getIdpc() {
        return idpc;
    }

    public void setIdpc(String idpc) {
        this.idpc = idpc;
    }

    public double getCpu() {
        return cpu;
    }

    public void setCpu(float cpu) {
        this.cpu = cpu;
    }

    public double getRam() {
        return ram;
    }

    public void setRam(float ram) {
        this.ram = ram;
    }

    public double getDisco() {
        return disco;
    }

    public void setDisco(float disco) {
        this.disco = disco;
    }

    @Override
    public String toString() {
        return this.getIdpc() +" " +this.getCpu() +" " + this.getRam() +" " + this.getDisco() +" " + this.getTime();
    }

    

}