package be.aga.dominionSimulator.gui;


import java.awt.*;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import be.aga.dominionSimulator.DomEngine;
import be.aga.dominionSimulator.DomPlayer;

/**
 */
public class DomBarChart {
	private ArrayList<DomPlayer> players;
    private ChartPanel myChartPanel;
    private DefaultCategoryDataset dataset;

    /**
     */
    public DomBarChart (ArrayList<DomPlayer> aPlayers) {
    	players = aPlayers;
        dataset = createBarDataset();
        JFreeChart chart = createChart(dataset);
        myChartPanel = new ChartPanel(chart);
    }

    private JFreeChart createChart(DefaultCategoryDataset defaultCategoryDataset) {
        String theTitle = "";
        if (!players.isEmpty())            
          theTitle = "Results (Average #turns = " + (players.get(0).getSumTurns()/DomEngine.NUMBER_OF_GAMES) + ")"; 

        final JFreeChart chart = ChartFactory.createBarChart3D(
             theTitle,  // chart title
             "",
             "",
             defaultCategoryDataset,           // data
             PlotOrientation.VERTICAL,
             true,             // include legend
             true,
             false
         );
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer barRenderer;
        barRenderer = (BarRenderer)plot.getRenderer();
        barRenderer.setSeriesPaint(0,Color.black);
        barRenderer.setSeriesPaint(1,Color.blue);
        barRenderer.setSeriesPaint(2,Color.red);
        barRenderer.setSeriesPaint(3,Color.cyan);

        return chart;
   }

	/**
     * @return
     */
    private DefaultCategoryDataset createBarDataset() {
        DefaultCategoryDataset theDataset = new DefaultCategoryDataset();
        double theTotalTies = 0;
        for (DomPlayer thePlayer : players) {
          String theLabel = thePlayer.toString() + " ("+ (thePlayer.getWins()*100/DomEngine.NUMBER_OF_GAMES) + "%)";
          theDataset.addValue( thePlayer.getWins()/DomEngine.NUMBER_OF_GAMES, theLabel, "");
          theTotalTies += thePlayer.getTies();
        }
        if (!players.isEmpty()) {
          String theLabel = "Ties ("+ (theTotalTies*100/DomEngine.NUMBER_OF_GAMES) + "%)";
          theDataset.addValue( theTotalTies/DomEngine.NUMBER_OF_GAMES, theLabel, "");
        }
        return theDataset;
    }

	/**
     * @return
     */
    public ChartPanel getChartPanel() {
       int theHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
       int theWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
//       myChartPanel.setPreferredSize( new Dimension(theWidth*10/28,theHeight*10/32) );
       return myChartPanel;
    }
}