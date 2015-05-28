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
public class PopulationGenerator {

	private static double MAX_GAIN = 10.0;

    public static ArrayList<Gains> generate(int size) {
        ArrayList<Gains> pop = new ArrayList();

        for (int i = 0; i < size; i++) {
			double kP = Math.random() * MAX_GAIN;// ((Math.random() * 2.0)
														// -
													// 1.0) * MAX_GAIN;
			double kI = Math.random() * MAX_GAIN;// ((Math.random()
																	// * 2.0) -
													// 1.0) * MAX_GAIN;
			double kD = Math.random() * MAX_GAIN;// ((Math.random()
																	// * 2.0) -
													// 1.0) * MAX_GAIN;
            Gains member = new Gains(kP, kI, kD);
            pop.add(member);
        }
        return pop;
    }

	private static double MUTATION_PROB = 0.3;

    public static ArrayList<Gains> generate(int size, Gains parentA, Gains parentB) {
        ArrayList<Gains> pop = new ArrayList();
        double cP = 0, cI = 0, cD = 0;

        for (int i = 0; i < size; i++) {
            if (Math.random() > MUTATION_PROB) {

				double ran = Math.random();

				if (ran < 1.0 / 6.0) {
					cP = parentA.kP;
					cI = parentA.kI;
					cD = parentA.kD;
				} else if (ran < 2.0 / 6.0) {
					cP = parentB.kP;
					cI = parentA.kI;
					cD = parentA.kD;
				} else if (ran < 3.0 / 6.0) {
					cP = parentA.kP;
					cI = parentB.kI;
					cD = parentA.kD;
				} else if (ran < 4.0 / 6.0) {
					cP = parentA.kP;
					cI = parentA.kI;
					cD = parentB.kD;
				} else if (ran < 5.0 / 6.0) {
					cP = parentB.kP;
					cI = parentB.kI;
					cD = parentA.kD;
				} else {
					cP = parentB.kP;
					cI = parentB.kI;
					cD = parentB.kD;
				}

				// cP *= (Math.random() + 0.5);
				// cI *= (Math.random() + 0.5);
				// cD *= (Math.random() + 0.5);
            } else {

				if (Math.random() < 0.5) {
					cP = (parentA.kP + parentB.kP) / 2;
					cI = (parentA.kI + parentB.kI) / 2;
					cD = (parentA.kD + parentB.kD) / 2;
				} else {
					cP = Math.random() * MAX_GAIN;
					cI = Math.random() * MAX_GAIN;
					cD = Math.random() * MAX_GAIN;
				}

				// cP *= Math.random();
				// cI *= Math.random();
				// cD *= Math.random();
            }
            Gains member = new Gains(cP, cI, cD);
            pop.add(member);
        }
        return pop;
    }

}
