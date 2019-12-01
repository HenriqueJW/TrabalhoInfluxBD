/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fazedorGrafico;

import classes.registroPc;
import java.awt.BasicStroke;
import java.awt.Color;

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
import org.jfree.data.DomainOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Micro
 */
public class fazedorCPU {

    public XYDataset createDataSet(List<List<Double>> listaRegistro) {

        XYSeries series = new XYSeries("% do uso de CPU");

        int tempo = 1;
        for (List l : listaRegistro) {

            series.add((int) tempo, (double) l.get(0));
            tempo++;
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    public JFreeChart createChart(XYDataset dataSet) {

        JFreeChart grafico = ChartFactory.createXYLineChart("Uso da CPU ao longo do tempo",
                "Dias",
                "Uso CPU (%)",
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
