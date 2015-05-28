/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class FitnessGraph extends ApplicationFrame {

	public FitnessGraph(final String title, ArrayList<Double> data) {
		super(title);

		JFreeChart chart = createChart(createDataset(data));

		File imageFile = new File(System.currentTimeMillis() + ".png");
		int width = 1280;
		int height = 960;

		final ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new java.awt.Dimension(1500, 800));
		this.setContentPane(panel);
	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return a sample dataset.
	 */
	private XYDataset createDataset(ArrayList<Double> data) {

		final XYSeries series1 = new XYSeries("fitness");
		for (int i = 0; i < data.size() - 1; i++) {

			double p = data.get(i);
			series1.add(i, p);
		}


		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);

		return dataset;

	}

	/**
	 * Creates a chart.
	 *
	 * @param dataset
	 *            the data for the chart.
	 *
	 * @return a chart.
	 */
	private JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createScatterPlot(
				"Fitness v generation", // chart title
				"X", // x axis label
				"Y", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);


		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);


		

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// renderer.setSeriesShape(0, new Ellipse2D.Float(1.0f, 1.0f, 1.0f,
		// 1.0f));
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, false);
		// renderer.setSeriesStroke(1, new BasicStroke(0.01f));
		plot.setRenderer(renderer);


		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		// rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRange(true);
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}
}
