/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpidtuner;

import java.util.ArrayList;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Simulator {
	
	// Inputs:
	private SystemModel sys = null;
	private Gains gains = null;
	private ArrayList<Setpoint> setpoints = null;
	private ArrayList<Setpoint> trajectory = null;
	private double timeStep = 0.0;
	
	// Outputs:
	private double fitness = 0.0;
	private ArrayList<Setpoint> outputTrajectory = null;

	public Simulator(SystemModel sys, Gains gains, ArrayList<Setpoint> traj, double timeStep) {
		this.sys = sys;
		this.gains = gains;
		this.setpoints = traj;
		this.timeStep = timeStep;


		// Extrapolate input setpoints to full trajectory with time resulolution
		// timeStep
		int point = 0;
        Setpoint curr = new Setpoint(0.0, 0.0);
		trajectory = new ArrayList();
        for (double t = 0.0; t < traj.get(traj.size() - 1).time; t += timeStep) {
            if (t >= traj.get(point).time) {
                trajectory.add(traj.get(point));
                curr = traj.get(point);
                point++;
            } else {
                trajectory.add(new Setpoint(t, curr.position));
            }
        }

		this.outputTrajectory = new ArrayList();
	}

	private double errIntegral = 0.0;
	private double position = 0.0;
	private double integral = 0.0;
	private double error = 0.0, prevErr = 0.0;

	public void simulate() {
        errIntegral = 0.0;
        position = 0.0;
        integral = 0.0;
		error = 0.0;
		prevErr = 0.0;
		this.clearOutputTrajectory();
        for (int i = 0; i < trajectory.size(); i++) {
            prevErr = error;
            error = trajectory.get(i).position - position;
			integral += error * timeStep;
			integral = Math.min(1.0, Math.max(-1.0, integral));

			if (Math.abs(error) < 0.05) {
				// integral = 0;
			}
			double out = (gains.kP * error) + (gains.kI * integral)
					+ (gains.kD * ((error - prevErr) / timeStep));

			sys.calculateVelocity(out);
			position += sys.getVelocity() * timeStep;

			outputTrajectory.add(new Setpoint(trajectory.get(i).time, position,
					sys.getVelocity()));

            errIntegral += error * error;
        }


        fitness = 1 / Math.sqrt(errIntegral);

    }

	public void setGains(Gains newGains) {
		this.gains = newGains;
	}

	public Gains getGains() {
		return this.gains;
	}

	public double getFitness() {
		return fitness;
	}

	public ArrayList<Setpoint> getOutputTrajectory() {
		return this.outputTrajectory;
	}

	public void clearOutputTrajectory() {
		this.outputTrajectory.clear();
	}

}
