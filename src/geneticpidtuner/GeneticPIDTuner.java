/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpidtuner;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ui.ConfigDialog;
import ui.ConfigFile;
import ui.FitnessGraph;
import ui.Graph;
import ui.PlotFile;
import ui.TrajectoryFile;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class GeneticPIDTuner {
	public static int progress = 0;

	static class ProgressThread extends Thread {
		int prevProg = 0;
		public void run() {
			while(this.isAlive()) {
				
				if (progress < 100 && prevProg != progress) {
					System.out.println(progress + "% done...");
				}
				prevProg = progress;
			}
		}
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

		ConfigFile cfg = ConfigFile.getInstance("config.txt");
		ConfigDialog dia = new ConfigDialog();

		double timeStep = dia.loopTime;

		SystemModel sys = new SystemModel(dia.speed, dia.force, dia.eff,
				dia.load, timeStep, false);

		ArrayList<Setpoint> traj = TrajectoryFile.getInstance("trajectory.csv")
				.getTrajectory();

		int population = dia.population;

		ArrayList<Gains> pop = PopulationGenerator.generate(population);
        System.out.println("Initial Population generated");



		Gains mostFit = new Gains(dia.kP, dia.kI, dia.kD);
		Gains prevMostFit = new Gains(dia.kP, dia.kI, dia.kD);

		System.out.println("Running simulations...");

		ArrayList<Double> fitnesses = new ArrayList();
		int gen = dia.mode == 0 ? dia.generations : 0;

		Simulator sim = new Simulator(sys, mostFit, traj, timeStep);

		double maxFit = 0;

			(new ProgressThread()).start();
		for (int j = 0; j < gen; j++) {
			// System.out.println(((double) j / (double) gen) + "% done...");
			progress = (int) (((double) (j + 1) / (double) gen) * 100.0);


			for (int i = 0; i < pop.size(); i++) {
				sys.reset();
				sim.setGains(pop.get(i));
				sim.simulate();
				double fitness = sim.getFitness();
		  
				if (fitness > maxFit) {
					maxFit = fitness;
					prevMostFit = mostFit;
					mostFit = pop.get(i);
				}

			}

			fitnesses.add(new Double(maxFit));
			pop = PopulationGenerator.generate(population, mostFit,
					pop.get(((int) Math.random() * (pop.size() - 1))));
		  
		  }
		progress = 100;
		System.out.println("Simulations done!");
		
		sys.reset();
		sim.setGains(mostFit);
		sim.simulate();
		 
		System.out.println("Best gains: "
				+ mostFit.toString()
				+ "\tFitness: "
				+ new DecimalFormat("#.####").format(sim.getFitness()));
		ArrayList<Setpoint> plot = sim.getOutputTrajectory();

        System.out.println("Creating plot...");
		try {
			PlotFile out = new PlotFile(plot, cfg.getString("outputCsv"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (gen != 0) {
			FitnessGraph fg = new FitnessGraph("fitness v time", fitnesses);
			fg.pack();
			fg.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			fg.setVisible(true);
		}
			final Graph graph = new Graph("position v time",
					plot, traj, mostFit);

		graph.pack();
		// RefineryUtilities.centerFrameOnScreen(graph);
		graph.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		graph.setVisible(true);

		// final VelocityGraph vg = new VelocityGraph("accelFilter v time", plot);
		// vg.pack();
		// vg.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// vg.setVisible(true);

		System.gc();


    }


}

