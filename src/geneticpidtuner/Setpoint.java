/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpidtuner;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Setpoint {

    public double position = 0.0;
    public double time = 0.0;
	public double velocity = 0;

    public Setpoint(double time, double position) {
        this.position = position;
        this.time = time;
    }

	public Setpoint(double time, double position, double velocity) {
		this.position = position;
		this.time = time;
		this.velocity = velocity;
	}

}
