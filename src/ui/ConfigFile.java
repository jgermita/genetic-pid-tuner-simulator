package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ConfigFile {
	private static ConfigFile instance = null;

	ArrayList<Property> properties = new ArrayList();

	public static ConfigFile getInstance(String filename) {
		if (instance == null) {
			instance = new ConfigFile(filename);
		}
		return instance;
	}

	private ConfigFile(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.contains("=")) {
					properties.add(new Property(line.split("=")[0], (line
							.split("=")[1])));
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getValue(String name) {
		double answer = 0.0;
		for (Property p : properties) {
			if (p.name.equals(name)) {
				answer = Double.parseDouble(p.value);
			}
		}

		return answer;
	}

	public String getString(String name) {
		String answer = null;
		for (Property p : properties) {
			if (p.name.equals(name)) {
				answer = p.value;
			}
		}

		return answer;
	}

	public class Property {
		String name = null;
		String value = null;

		public Property(String name, String value) {
			this.name = name;
			this.value = value;
		}
	}

}
