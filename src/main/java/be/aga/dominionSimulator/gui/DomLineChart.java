package be.aga.dominionSimulator.gui;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;

public class DomLineChart {
	private ArrayList<DomPlayer> players;
    private ChartPanel myChartPanel;
    private String myType;

    public DomLineChart (ArrayList<DomPlayer> aPlayers, String aType) {
      myType = aType;
   	  players = aPlayers;
      XYDataset dataset = createDataset();
      JFreeChart chart = createChart(dataset);
      myChartPanel = new ChartPanel(chart);
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            myType.equals("VP")? "Average Victory Points gained/turn" : "Average $ generated/turn",      // chart title
            "turns",                      // x axis label
            myType.equals("VP")? "VP gained" : "available money",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
//        chart.setBackgroundPaint(Color.darkGray);
//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
//        plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0,Color.black);
        renderer.setSeriesPaint(1,Color.blue);
        renderer.setSeriesPaint(2,Color.red);
        renderer.setSeriesPaint(3,Color.cyan);
        renderer.setBaseShapesVisible(true);
        plot.setRenderer(renderer);
        // change the auto tick unit selection to integer units only...
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        domainAxis.setTickUnit( new NumberTickUnit( 1 ) );
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        rangeAxis.setTickUnit( new NumberTickUnit( 1 ) );
                
        return chart;
	}

	private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
		for (DomPlayer thePlayer : players) {
			XYSeries theSeries = new XYSeries(thePlayer.toString());
			for (int i=0;i<thePlayer.getSumTurns()/DomEngine.NUMBER_OF_GAMES;i++){
               if (myType.equals( "VP" ))
                 theSeries.add(i+1,thePlayer.getVPCurve(i)/DomEngine.NUMBER_OF_GAMES);
               if (myType.equals( "Money" ))
                 theSeries.add(i+1,thePlayer.getMoneyCurve(i)/DomEngine.NUMBER_OF_GAMES);
			}
			dataset.addSeries(theSeries);
        }
		return dataset;
	}

    public ChartPanel getChartPanel() {
       int theHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
       int theWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
//       myChartPanel.setMinimumSize( new Dimension(theWidth*10/27,theHeight*10/27) );
//       myChartPanel.setPreferredSize( new Dimension(theWidth*10/27,theHeight*10/27) );
       return myChartPanel;
    }
}