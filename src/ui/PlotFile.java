package ui;

import geneticpidtuner.Setpoint;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class PlotFile {
	public PlotFile(ArrayList<Setpoint> data, String name) throws Exception {
		FileWriter fw = new FileWriter(new File(name));

		for (int i = 0; i < data.size(); i++) {
			Setpoint p = data.get(i);
			String out = p.time + "," + p.position + "," + p.velocity + "\n";
			fw.write(out);
		}

		fw.flush();
		fw.close();
	}
}
