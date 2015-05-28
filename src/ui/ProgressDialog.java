package ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class ProgressDialog extends JPanel implements PropertyChangeListener {
	private JProgressBar progressBar;
	private JTextArea taskOutput;
	private Task task;
	private JButton startButton;
	private static int prog = 0;

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {

			int progress = 0;
			// Initialize progress property.
			setProgress(0);
			while (progress < 100) {

				progress = prog;
				setProgress(Math.min(progress, 100));
			}
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			taskOutput.append("Done!\n");
		}
	}

	public ProgressDialog() {
		super(new BorderLayout());

//		// Create the demo's UI.
//		startButton = new JButton("Start");
//		startButton.setActionCommand("start");
//		startButton.addActionListener(this);

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		JPanel panel = new JPanel();
		// panel.add(startButton);
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
	}

	/**
	 * Invoked when the user presses the start button.
	 */
//	public void actionPerformed(ActionEvent evt) {
//		//startButton.setEnabled(false);
//		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		// Instances of javax.swing.SwingWorker are not reusuable, so
//		// we create new instances as needed.
//		task = new Task();
//		task.addPropertyChangeListener(this);
//		task.execute();
//	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			taskOutput.append(String.format("%d%% Complete.\n",
					task.getProgress()));
		}
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	public static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ProgressBarDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JComponent newContentPane = new ProgressDialog();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void setProgress(int progress) {
		prog = progress;
	}

}
