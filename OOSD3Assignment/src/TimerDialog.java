import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


import net.miginfocom.swing.MigLayout;


public class TimerDialog extends JDialog implements ActionListener {
	
	// Represents the numberof seconds that the countdown will be performed for.
	private long seconds;
	//hello
	//My name
	private int hours;
	private int mins;
	private int secs;
	
	
	// Menu components.
	JTextField hourField, minField, secField;
	JLabel hourLabel, minLabel, secLabel;
	JButton startButton = new JButton("START");
	

	public TimerDialog(Frame owner, long seconds, boolean modality) {
		super(owner, modality);
		this.seconds = seconds;
		setLocationRelativeTo(owner);
		initComponents();
	}
	
	public long getSeconds() {
		return (long)seconds;
	}
	
	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	public int getHours() {
		return hours;
	}

	

	public int getMins() {
		return mins;
	}

	

	public int getSecs() {
		return secs;
	}

	

	// Sets up display.
	private void initComponents() {
		setTitle("Initialise Timer");	
		setLayout(new BorderLayout());
		actionListeners();
		
		Font displayFont = new Font("Arial", Font.BOLD, 16);
		Font labelFont = new Font("Arial", Font.BOLD, 12);
		
		JPanel displayPanel = new JPanel(new GridLayout(1,3));
				
		JPanel hourPanel = new JPanel(new BorderLayout());
		hourField = new JTextField(5);
		hourField.setHorizontalAlignment(JTextField.CENTER);
		hourField.setFont(displayFont);
		hourField.setText("00");
		hourLabel = new JLabel("Hours");
		hourLabel.setHorizontalAlignment(JTextField.CENTER);
		hourLabel.setFont(labelFont);
		hourPanel.add(hourField, BorderLayout.CENTER);
		hourPanel.add(hourLabel, BorderLayout.SOUTH);
		
		displayPanel.add(hourPanel);
		
		JPanel minPanel = new JPanel(new BorderLayout());
		minField = new JTextField(5);
		minField.setHorizontalAlignment(JTextField.CENTER);
		minField.setFont(displayFont);
		minField.setText("00");
		minLabel = new JLabel("Minutes");
		minLabel.setHorizontalAlignment(JTextField.CENTER);
		minLabel.setFont(labelFont);
		minPanel.add(minField, BorderLayout.CENTER);
		minPanel.add(minLabel, BorderLayout.SOUTH);
		
		displayPanel.add(minPanel);
		
		JPanel secPanel = new JPanel(new BorderLayout());
		secField = new JTextField(5);
		secField.setHorizontalAlignment(JTextField.CENTER);
		secField.setFont(displayFont);
		secField.setText("00");
		secLabel = new JLabel("Seconds");
		secLabel.setHorizontalAlignment(JTextField.CENTER);
		secLabel.setFont(labelFont);
		secPanel.add(secField, BorderLayout.CENTER);
		secPanel.add(secLabel, BorderLayout.SOUTH);
		
		displayPanel.add(secPanel);
		
		add(displayPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.add(startButton);
		
	

		
		add(buttonPanel, BorderLayout.SOUTH);
		
		setSize(300, 150);
		setVisible(true);
		
	}
	
	// TODO: This start action listener will be invoked when the start button is clicked.
	// It should take the values from the three text fields and try to convert them into integer values, and then check for NumberFormatExceptions 
	// and for the minute and second values between 0 and 59.
	public void actionListeners(){
		startButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		String pressed = e.getActionCommand();
		
		if(pressed.equalsIgnoreCase("START")){
			System.out.println("Start Pressed");
			
			try {
				hours = Integer.parseInt(hourField.getText());
				mins = Integer.parseInt(minField.getText());
				secs = Integer.parseInt(secField.getText());
			
			
			
				if (mins < 0 || mins > 59){
					JOptionPane.showMessageDialog(this, "Minute value must be between 0 and 59");
				} else if(secs > 59){
					JOptionPane.showMessageDialog(this, "Second value must be between 0 and 59");
				}
				
				else{
					int newHours = hours * (60*60);
					int newMins = mins * 60;
					int total = newHours + newMins + secs;
					setSeconds(total);
				}
				
				dispose();
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "Must be a number.", "Invalid input", JOptionPane.ERROR_MESSAGE);
			}	
			
			
		}
		
		
	}
	
	
	
	

	

}
