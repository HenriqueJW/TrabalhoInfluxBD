/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fazedorGrafico;

import classes.registroPc;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Micro
 */
public class fazedorHD {
        private double maiorHD;
        private double menorHD;
    
       public XYDataset createDataSet(List<List<Double>> listaRegistro) {

        XYSeries series = new XYSeries("Uso de HD em GigaBytes");

        int tempo = 1;
        maiorHD=0;
        menorHD=Double.MAX_VALUE;
        
        for (List l : listaRegistro) {
            double valor = (double) l.get(2);
            series.add(tempo, valor);
            tempo++;
            if(valor >= maiorHD){
                maiorHD =valor;
            }
            if(valor <= menorHD){
                menorHD = valor;
            }
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
    
    public JFreeChart createChart(XYDataset dataSet) {

        JFreeChart grafico = ChartFactory.createXYLineChart("Espaço ocupado no HD ao longo do tempo",
                "Dias",
                "Espaço Ocupado no HD (GB)",
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);

        XYPlot plot = grafico.getXYPlot();
        XYLineAndShapeRenderer renderizador = new XYLineAndShapeRenderer();
        renderizador.setSeriesPaint(0, Color.RED);
        renderizador.setSeriesStroke(0, new BasicStroke(2.0f));
        plot.setRenderer(renderizador);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        NumberAxis axis = (NumberAxis) plot.getDomainAxis();
        axis.setTickUnit(new NumberTickUnit(1.0));
        NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
        double tick = maiorHD - menorHD + 0.1;
        axisY.setRange(menorHD - 2*tick,maiorHD + 2*tick);
//        axisY.setTickUnit(new NumberTickUnit());
        return grafico;
    }
    
    public ChartPanel criarGrafico(List<List<Double>> listaRegistro) {

        XYDataset dataSet = createDataSet(listaRegistro);

        JFreeChart grafico = createChart(dataSet);

        ChartPanel painel = new ChartPanel(grafico);
        painel.setBackground(Color.white);
        //painel.setPreferredSize(new Dimension(400,400));

        return painel;
    }
    
    
    
}
