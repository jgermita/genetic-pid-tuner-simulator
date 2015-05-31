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
public class Gains {

    public double kP = 0.0;
    public double kI = 0.0;
    public double kD = 0.0;

    public Gains(double p, double i, double d) {
        this.kP = p;
		this.kI = 0;
		this.kD = d;
    }

    public String toString() {
        return "kP: " + kP + "\tkI: " + kI + "\tkD: " + kD;
    }

}
