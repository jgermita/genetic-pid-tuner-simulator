/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import geneticpidtuner.Gains;
import geneticpidtuner.Setpoint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYStepRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Graph extends ApplicationFrame {

	Gains gains = null;
	public Graph(final String title, ArrayList<Setpoint> data,
			ArrayList<Setpoint> traj, Gains gains) {
		super(title);
		this.gains = gains;

		JFreeChart chart = createChart(data, traj);

		File imageFile = new File(ConfigFile.getInstance("config.txt")
				.getString("outputPng"));
		int width = 1280;
		int height = 960;

		try {
			ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
		} catch (IOException ex) {
			System.err.println(ex);
		}
		final ChartPanel panel = new ChartPanel(chart);
		panel.setPreferredSize(new java.awt.Dimension(1500, 800));
		this.setContentPane(panel);
	}

	ArrayList<Setpoint> trajectory = new ArrayList();

	/**
	 * Creates a chart.
	 *
	 * @param dataset
	 *            the data for the chart.
	 *
	 * @return a chart.
	 */
	private JFreeChart createChart(ArrayList<Setpoint> setpoints,
			ArrayList<Setpoint> traj) {
		trajectory = traj;

		XYSeries posSeries = new XYSeries("Position");
		XYSeries trajSeries = new XYSeries("Trajectory");
		XYSeries velSeries = new XYSeries("Velocity");
		for (int i = 0; i < setpoints.size(); i++) {

			Setpoint p = setpoints.get(i);
			posSeries.add(p.time, p.position);
			velSeries.add(p.time, p.velocity);
		}

		for (int i = 0; i < trajectory.size(); i++) {

			Setpoint p = trajectory.get(i);
			trajSeries.add(p.time, p.position);

		}

		XYSeriesCollection posDataset = new XYSeriesCollection();
		XYSeriesCollection trajDataset = new XYSeriesCollection();
		XYSeriesCollection velDataset = new XYSeriesCollection();

		posDataset.addSeries(posSeries);
		velDataset.addSeries(velSeries);
		trajDataset.addSeries(trajSeries);
		// create the chart...
		final JFreeChart chart = ChartFactory.createScatterPlot(
				"System output", // chart title
				"X", // x axis label
				"Y", // y axis label
				posDataset, // data
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

		plot.setDataset(0, posDataset);
		plot.setDataset(1, trajDataset);
		plot.setDataset(2, velDataset);
		plot.setBackgroundPaint(Color.white);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);

		

		XYLineAndShapeRenderer posRenderer = new XYLineAndShapeRenderer();
		// renderer.setSeriesShape(0, new Ellipse2D.Float(1.0f, 1.0f, 1.0f,
		// 1.0f));
		posRenderer.setSeriesPaint(0, Color.BLUE);
		posRenderer.setSeriesLinesVisible(0, true);
		posRenderer.setSeriesShapesVisible(0, false);
		XYStepRenderer trajRenderer = new XYStepRenderer();
		trajRenderer.setSeriesPaint(1, Color.RED);
		trajRenderer.setSeriesStroke(1, new BasicStroke(10));
		trajRenderer.setSeriesLinesVisible(1, true);
		trajRenderer.setSeriesShapesVisible(1, false);

		XYLineAndShapeRenderer velRenderer = new XYLineAndShapeRenderer();
		velRenderer.setSeriesPaint(0, Color.MAGENTA);
		velRenderer.setSeriesLinesVisible(0, true);
		velRenderer.setSeriesShapesVisible(0, false);
		// renderer.setSeriesStroke(1, new BasicStroke(0.01f));
		plot.setRenderer(0, posRenderer);
		plot.setRenderer(1, trajRenderer);
		plot.setRenderer(2, velRenderer);

		for (Setpoint s : trajectory) {
			Marker marker = new ValueMarker(s.time);
			marker.setPaint(Color.DARK_GRAY);
			marker.setLabel(Float.toString((float) s.position));
			marker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
			marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
			plot.addDomainMarker(marker);
		}

		XYTextAnnotation p = new XYTextAnnotation("kP = " + gains.kP, plot
				.getDomainAxis().getUpperBound() * 0.125, plot.getRangeAxis()
				.getUpperBound() * .75);
		p.setFont(new Font("Dialog", Font.PLAIN, 12));
		plot.addAnnotation(p);
		XYTextAnnotation i = new XYTextAnnotation("kI = " + gains.kI, plot
				.getDomainAxis().getUpperBound() * 0.125, plot.getRangeAxis()
				.getUpperBound() * .7);
		i.setFont(new Font("Dialog", Font.PLAIN, 12));
		plot.addAnnotation(i);
		XYTextAnnotation d = new XYTextAnnotation("kD = " + gains.kD, plot
				.getDomainAxis().getUpperBound() * 0.125, plot.getRangeAxis()
				.getUpperBound() * .65);
		d.setFont(new Font("Dialog", Font.PLAIN, 12));
		plot.addAnnotation(d);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setAutoRange(true);
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}
}
