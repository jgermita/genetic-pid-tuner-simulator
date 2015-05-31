package ui;

import geneticpidtuner.Setpoint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TrajectoryFile {
	private static TrajectoryFile instance = null;

	ArrayList<Setpoint> trajectory = new ArrayList();

	public static TrajectoryFile getInstance(String filename) {
		if (instance == null) {
			instance = new TrajectoryFile(filename);
		}
		return instance;
	}

	private TrajectoryFile(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = null;
			while((line = br.readLine()) != null) {
				if (line.contains(",")) {
					Setpoint p = new Setpoint(Double.parseDouble(line
							.split(",")[0]),
							Double.parseDouble(line.split(",")[1]));
					trajectory.add(p);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Setpoint> getTrajectory() {
		return trajectory;
	}

}
