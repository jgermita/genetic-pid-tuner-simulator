/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpidtuner;

/**
 * Class representing a linear mechanical system. Elevator, drivetrain, etc
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class SystemModel {

	private double maxSpeed = 0.0;
	private double maxForce = 0.0;
    private double gearRatio = 0.0;
    private double efficiency = 0.0;
	private double load = 0.0;
	private double timeStep = 0.002;
	private boolean gravity = false;

	private int filter_size = 10;


	public SystemModel(double maxSpeed, double maxForce, double efficiency,
			double load, double timeStep, boolean grav) {
		this.maxSpeed = maxSpeed;
		this.maxForce = maxForce;
        this.gearRatio = gearRatio;
        this.efficiency = efficiency;
		this.load = load;
		this.timeStep = timeStep;
		this.gravity = grav;
    }

	MovingAverage vel = new MovingAverage(filter_size);
	double velocity = 0.0;
	double filteredVel = 0.0;

	public void calculateVelocity(double out) {
		double outRaw = Math.min(1.0, (Math.max(-1.0, out)));
		out = outRaw * (maxSpeed * efficiency);

		
		filteredVel = vel.calculate(out);
		
		//
		

		// Attempted physics:
		// double accel = ((maxForce / load) * 3.2808399)
		// - (gravity ? 32.1850394 : 0);
		//
		// velocity += (accel * outRaw * timeStep);
		// filteredVel = vel.calculate(velocity);
		//
    }
    
	public double getVelocity() {
		return filteredVel;
	}

	public void reset() {
		velocity = 0;
		vel = new MovingAverage(filter_size);
	}

}
