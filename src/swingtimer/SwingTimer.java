package swingtimer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SwingTimer extends JFrame {
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	private static JComboBox<LocalTime> timerField = new JComboBox<LocalTime>();
	private static Timer timer;
	private JFileChooser chooser = new JFileChooser();
	private static JButton startTimerButton = new JButton();
	private static JButton stopButton = new JButton();

	public SwingTimer() {

		setLocationByPlatform(true);

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JMenu fileMenu = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Set sound");
		fileMenu.add(openItem);
		openItem.addActionListener(event -> {
			chooser.setCurrentDirectory(new File("C:\\Windows\\Media\\"));
			int result = chooser.showOpenDialog(SwingTimer.this); 
			if (result == JFileChooser.APPROVE_OPTION) {
				String name = chooser.getSelectedFile().getPath();
				SwingPlay.setPath(name); // задаём имя звукового файла.
				pack();
			}
		});

		fileMenu.add(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		JMenu stateMenu = new JMenu("Options");
		JCheckBoxMenuItem onTop = new JCheckBoxMenuItem("Up to other windows");
		onTop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (onTop.isSelected()) {
					EventQueue.invokeLater(() -> {
						setAlwaysOnTop(true);
					});
				}
				if (!(onTop.isSelected())) {
					EventQueue.invokeLater(() -> {
						setAlwaysOnTop(false);
					});
				}

			}
		});
		stateMenu.add(onTop);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuBar.add(fileMenu);
		menuBar.add(stateMenu);
		// фильтр wav файлов
		FileFilter filter = new FileNameExtensionFilter(
				"wave", "wav");
		chooser.setFileFilter(filter);

		timerField.addItem(LocalTime.of(0, 31));
		timerField.addItem(LocalTime.of(0, 46));
		timerField.addItem(LocalTime.of(0, 1));
		timerField.setEditable(true);

		startTimerButton = new JButton("Start timer");

		stopButton = new JButton("Stop timer");
		stopButton.setEnabled(false);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 2));
		northPanel.add(new JLabel("Timer minutes: ", SwingConstants.RIGHT));
		northPanel.add(timerField);
		northPanel.add(startTimerButton);
		northPanel.add(stopButton);
		add(northPanel, BorderLayout.NORTH);
		startTimerButton.addActionListener(event -> {
			startTimerButton.setEnabled(false);
			stopButton.setEnabled(true);
			if (timer == null || !(timer.isRunning())) {
				Timer timer = new Timer(1000, taskPerformer);
				this.timer = timer;
				timer.setRepeats(true);
				timer.start();

				timerField.setEnabled(false);
			}
		}

		);
		stopButton.addActionListener(event -> {
			timer.stop();
			startTimerButton.setEnabled(true);
			stopButton.setEnabled(false);
			timerField.setEnabled(true);
		});
		pack();
	}

	ActionListener taskPerformer = new ActionListener() {

		public void actionPerformed(ActionEvent evt) {
			if (timerField.getSelectedItem() instanceof LocalTime) {
				startandparseTimer();

			} else {
				parsTime();
				startandparseTimer();
			}
		}
	};

	static void parsTime() {
		String parstime = (String) timerField.getSelectedItem();
		String parsehour = parstime.substring(0, 2);
		int parsehourint = Integer.parseInt(parsehour);
		String parseminute = parstime.substring(3, 5);
		int parseminuteint = Integer.parseInt(parseminute);
		int parsesecondsint = 0;
		if (parstime.length() > 5) {
			String parseseconds = parstime.substring(6, 8);
			parsesecondsint = Integer.parseInt(parseseconds);
		}
		timerField.setSelectedItem(LocalTime.of(parsehourint, parseminuteint, parsesecondsint));
	}

	static void startandparseTimer() {

		LocalTime timerLocalTime = (LocalTime) timerField.getSelectedItem();

		if (timerLocalTime.compareTo(LocalTime.of(0, 0, 0)) > 0) {

			timerLocalTime = timerLocalTime.minusSeconds(1);
			timerField.setEnabled(true);

			timerField.setSelectedItem(timerLocalTime);

			timerField.setEnabled(false);
		} else {
			startTimerButton.setEnabled(true);
			try {
				SwingPlay.PlaySound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException
					| InterruptedException e) {

				e.printStackTrace();
			}
			timer.stop();

			timerField.setEnabled(true);
			startTimerButton.setEnabled(true);
			stopButton.setEnabled(false);
		}

	}
}