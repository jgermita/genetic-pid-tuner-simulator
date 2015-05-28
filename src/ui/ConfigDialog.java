package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jfree.ui.ApplicationFrame;

public class ConfigDialog extends ApplicationFrame {

	public int generations = 10;
	public int population = 100;
	public double loopTime = 0.001;
	public double kP = 0.0;
	public double kI = 0.0;
	public double kD = 0.0;

	public int mode = 0;

	JTextField genField = new JTextField(5);
	JTextField popField = new JTextField(5);
	JTextField loopField = new JTextField(5);
	JTextField loopField2 = new JTextField(5);
	JTextField pField = new JTextField(5);
	JTextField iField = new JTextField(5);
	JTextField dField = new JTextField(5);

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

		if (result == JOptionPane.OK_OPTION) {
			mode = tabbedPane.getSelectedIndex();

			if (mode == 0) {
				System.out.println("Mode: Genetic simulator");
			} else if (mode == 1) {
				System.out.println("Mode: Single gain simulator");
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
		popField.setText(Integer.toString(this.population));
		popField.setMaximumSize(new Dimension(Integer.MAX_VALUE, popField
				.getPreferredSize().height));
		popField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				popField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});

		genField.setText(Integer.toString(this.generations));
		genField.setMaximumSize(new Dimension(Integer.MAX_VALUE, genField
				.getPreferredSize().height));
		genField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				genField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});
		loopField.setText("0.001");
		loopField.setMaximumSize(new Dimension(Integer.MAX_VALUE, loopField
				.getPreferredSize().height));
		loopField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				loopField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});


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

		gcr.add(new JLabel("Loop execution time:"));
		gcr.add(loopField);
		gcr.add(Box.createVerticalStrut(15));

		return gcr;

	}

	public JPanel getSimConfigPanel() {
		JPanel scp = new JPanel(new BorderLayout());

		pField.setText("0.0");
		pField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				pField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});

		iField.setText("0.0");
		iField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				iField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});

		dField.setText("0.0");
		dField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				dField.setText("");
			}

			public void focusLost(FocusEvent e) {

			}
		});
		loopField2.setText("0.001");

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

		scp.add(new JLabel("Loop execution time:"));
		scp.add(loopField2);
		scp.add(Box.createVerticalStrut(15));

		return scp;
	}

	public JPanel getSysConfigPanel() {
		return new JPanel();
	}

}

