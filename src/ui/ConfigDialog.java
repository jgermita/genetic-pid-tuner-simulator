package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jfree.ui.ApplicationFrame;

public class ConfigDialog extends ApplicationFrame {

	public int generations = (int) ConfigFile.getInstance("config.txt")
			.getValue("genField");
	public int population = (int) ConfigFile.getInstance("config.txt")
			.getValue("popField");
	public double loopTime = ConfigFile.getInstance("config.txt").getValue(
			"loopField");
	public double kP = ConfigFile.getInstance("config.txt").getValue("pField");
	public double kI = ConfigFile.getInstance("config.txt").getValue("iField");
	public double kD = ConfigFile.getInstance("config.txt").getValue("dField");

	public double speed = ConfigFile.getInstance("config.txt")
			.getValue("speed");
	public double force = ConfigFile.getInstance("config.txt")
			.getValue("force");
	public double eff = ConfigFile.getInstance("config.txt").getValue(
			"efficiency");
	public double load = ConfigFile.getInstance("config.txt").getValue("load");

	public int mode = 0;

	JTextField genField = new JTextField(5);
	JTextField popField = new JTextField(5);
	JTextField loopField = new JTextField(5);

	JTextField pField = new JTextField(5);
	JTextField iField = new JTextField(5);
	JTextField dField = new JTextField(5);

	JTextField speedField = new JTextField(5);
	JTextField forceField = new JTextField(5);
	JTextField effField = new JTextField(5);
	JTextField loadField = new JTextField(5);

	JButton loadDefaultsButton = new JButton("Load Defaults");


	public ConfigDialog() {
		super("Genetic PID Tuner");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();

		mainPanel.setSize(new Dimension(500, 500));


		JTabbedPane tabbedPane = new JTabbedPane();


		tabbedPane.addTab("Genetic Tuner", getGenConfigPanel());

		tabbedPane.addTab("Gain Simulator", getSimConfigPanel());

		tabbedPane.addTab("System Configuration", getSysConfigPanel());

		mainPanel.add(tabbedPane);


		this.setContentPane(mainPanel);


		int result = JOptionPane.showConfirmDialog(mainPanel, tabbedPane,
				"Genetic PID Tuner", JOptionPane.OK_CANCEL_OPTION);

		generations = (int) getText(genField, generations);
		loopTime = getText(loopField, loopTime);
		population = (int) getText(popField, population);

		kP = getText(pField, kP);
		kI = getText(iField, kI);
		kD = getText(dField, kD);

		speed = getText(speedField, speed);
		force = getText(forceField, force);
		eff = getText(effField, eff);
		load = getText(loadField, load);

		if (result == JOptionPane.OK_OPTION) {
			mode = tabbedPane.getSelectedIndex();

			if (mode == 0) {
				System.out.println("Mode: Genetic simulator");
				loopTime = getText(loopField, loopTime);
			} else if (mode == 1) {
				System.out.println("Mode: Single gain simulator");
				getText(loopField, loopTime);
			} else {
				System.out.println("Mode: " + mode);
			}

		} else if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		} else if (result == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}

	}

	private double getText(JTextField field, double def) {
		double answer = def;
		try {
			answer = Double.parseDouble(field.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

	private JPanel getGenConfigPanel() {
		JPanel gcr = new JPanel(new BorderLayout());
		popField.setText(ConfigFile.getInstance("config.txt").getString(
				"popField"));
		configureTextField(popField);

		genField.setText(ConfigFile.getInstance("config.txt").getString(
				"genField"));
		configureTextField(genField);



		gcr.setLayout(new BoxLayout(gcr,
				BoxLayout.PAGE_AXIS));
		gcr.add(new JLabel("Genetic PID Tuner Configuration"));
		gcr.add(Box.createVerticalStrut(15));

		gcr.add(new JLabel("Population size per generation:"));
		gcr.add(popField);
		gcr.add(Box.createVerticalStrut(15));

		gcr.add(new JLabel("Number of Generations:"));
		gcr.add(genField);
		gcr.add(Box.createVerticalStrut(15));

		gcr.add(Box.createVerticalStrut(15));

		return gcr;

	}

	public JPanel getSimConfigPanel() {
		JPanel scp = new JPanel(new BorderLayout());

		pField.setText(ConfigFile.getInstance("config.txt").getString("pField"));
		configureTextField(pField);

		iField.setText(ConfigFile.getInstance("config.txt").getString("iField"));
		configureTextField(iField);

		dField.setText(ConfigFile.getInstance("config.txt").getString("dField"));
		configureTextField(dField);

		scp.setLayout(new BoxLayout(scp,
				BoxLayout.PAGE_AXIS));
		scp.add(new JLabel("Single Gain set simulator"));
		scp.add(Box.createVerticalStrut(15));
		
		scp.add(new JLabel("kP:"));
		scp.add(pField);
		scp.add(Box.createVerticalStrut(15));

		scp.add(new JLabel("kI:"));
		scp.add(iField);
		scp.add(Box.createVerticalStrut(15));

		scp.add(new JLabel("kD:"));
		scp.add(dField);
		scp.add(Box.createVerticalStrut(15));


		return scp;
	}

	public JPanel getSysConfigPanel() {
		JPanel scp = new JPanel(new BorderLayout());
		speedField.setText(ConfigFile.getInstance("config.txt").getString(
				"speed"));
		configureTextField(speedField);

		forceField.setText(ConfigFile.getInstance("config.txt").getString(
				"force"));
		configureTextField(forceField);

		effField.setText(ConfigFile.getInstance("config.txt").getString(
				"efficiency"));
		configureTextField(effField);

		loadField.setText(ConfigFile.getInstance("config.txt")
				.getString("load"));
		configureTextField(loadField);


		loopField.setText(ConfigFile.getInstance("config.txt").getString(
				"loopField"));
		configureTextField(loopField);

		loadDefaultsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speedField.setText(ConfigFile.getInstance("config.txt")
						.getString("speed"));
				forceField.setText(ConfigFile.getInstance("config.txt")
						.getString("force"));
				effField.setText(ConfigFile.getInstance("config.txt")
						.getString("efficiency"));
				loadField.setText(ConfigFile.getInstance("config.txt")
						.getString("load"));

			}
		});

		scp.setLayout(new BoxLayout(scp, BoxLayout.PAGE_AXIS));
		scp.add(new JLabel("System configuration"));
		scp.add(Box.createVerticalStrut(15));

		scp.add(new JLabel("Speed(ft/s):"));
		scp.add(speedField);
		scp.add(Box.createVerticalStrut(15));

		scp.add(new JLabel("Force:(lbf)"));
		scp.add(forceField);
		scp.add(Box.createVerticalStrut(15));

		scp.add(new JLabel("Efficiency:(0.0-1.0)"));
		scp.add(effField);
		scp.add(Box.createVerticalStrut(15));

		scp.add(new JLabel("Loop execution time:(s)"));
		scp.add(loopField);
		scp.add(Box.createVerticalStrut(15));

		scp.add(loadDefaultsButton);

		return scp;
	}

	private void configureTextField(JTextField field) {
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field
				.getPreferredSize().height));
		field.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				field.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});
	}

}

