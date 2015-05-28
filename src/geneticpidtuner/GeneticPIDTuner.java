/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpidtuner;

import java.util.ArrayList;

import ui.ConfigDialog;
import ui.FitnessGraph;
import ui.PlotFile;
import ui.PositionGraph;

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

		// while (true) {

		ConfigDialog dia = new ConfigDialog();



        System.out.println("Genetic PID TUNER");

		double timeStep = dia.loopTime;
		SystemModel sys = new SystemModel(1, 300.0, 1.0, 25.0, timeStep, true);

        ArrayList<Setpoint> traj = new ArrayList();

		// for (int i = 1; i < 5; i++) {
		// traj.add(new Setpoint((double) i * 5, (Math.random() * 50)));
		// }

		traj.add(new Setpoint(0.0, 5));
		traj.add(new Setpoint(5.0, 10));
		traj.add(new Setpoint(10.0, 15));
		traj.add(new Setpoint(15.0, 0.0));
		traj.add(new Setpoint(20.0, 0.0));

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
				+ sim.getFitness());
		ArrayList<Setpoint> plot = sim.getOutputTrajectory();

        System.out.println("Creating plot...");
		try {
			PlotFile out = new PlotFile(plot, "out.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (gen != 0) {
			FitnessGraph fg = new FitnessGraph("fitness v time", fitnesses);
			fg.pack();
			fg.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			fg.setVisible(true);
		}
			final PositionGraph graph = new PositionGraph("position v time",
					plot, traj, mostFit);

		graph.pack();
		// RefineryUtilities.centerFrameOnScreen(graph);
		graph.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		graph.setVisible(true);

		// final VelocityGraph vg = new VelocityGraph("vel v time", plot);
		// vg.pack();
		// vg.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		// vg.setVisible(true);

		System.gc();


    }
	// }

}

