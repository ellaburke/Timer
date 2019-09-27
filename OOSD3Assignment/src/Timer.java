import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Timer extends JFrame implements ActionListener {
	
	// Interface components
	
	// Fonts to be used
	Font countdownFont = new Font("Arial", Font.BOLD, 20);
	Font elapsedFont = new Font("Arial", Font.PLAIN, 14);
	
	// Labels and text fields
	JLabel countdownLabel = new JLabel("Seconds remaining:");
	JTextField countdownField = new JTextField(15);
	JLabel elapsedLabel = new JLabel("Time running:");
	JTextField elapsedField = new JTextField(15);
	JButton startButton = new JButton("START");
	JButton pauseButton = new JButton("PAUSE");
	JButton stopButton = new JButton("STOP");
	
	
	// JFile Chooser for save/open file
	File currentWorkingDirectory = new File(System.getProperty("user.dir"));
	JFileChooser jfc = new JFileChooser(currentWorkingDirectory);
	
	
	//input/output streams
	//FileOutputStream fileOut = null;
	//ObjectOutputStream objOut = null;
	//FileInputStream fileIn = null;
	//ObjectOutputStream objIn = null;
	
	// The text area and the scroll pane in which it resides
	JTextArea display;
	
	JScrollPane myPane;
	
	// These represent the menus
	JMenuItem saveData = new JMenuItem("Save data", KeyEvent.VK_S);
	JMenuItem displayData = new JMenuItem("Display data", KeyEvent.VK_D);
	
	JMenu options = new JMenu("Options");
	
	JMenuBar menuBar = new JMenuBar();
	
	// These booleans are used to indicate whether the START button has been clicked
	boolean started;
	
	// and the state of the timer (paused or running)
	boolean paused;
	
	// Number of seconds
	long totalSeconds = 0;
	long secondsToRun = 0;
	long secondsSinceStart = 0;
	
	String hours ;
	String mins;
	String secs;
	
	// This is the thread that performs the countdown and can be started, paused and stopped
	TimerThread countdownThread;
	TimerDialog td;

	// Interface constructed
	Timer() {
		
		setTitle("Timer Application");
		
		//Add actionListeners
		actionListeners();
		
		//Default JFileChooser to current location
		//jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		
    	MigLayout layout = new MigLayout("fillx");
    	JPanel panel = new JPanel(layout);
    	getContentPane().add(panel);
    	
    	options.add(saveData);
    	options.add(displayData);
    	menuBar.add(options);
    	
    	panel.add(menuBar, "spanx, north, wrap");
    	
    	MigLayout centralLayout = new MigLayout("fillx");
    	
    	JPanel centralPanel = new JPanel(centralLayout);
    	
    	GridLayout timeLayout = new GridLayout(2,2);
    	
    	JPanel timePanel = new JPanel(timeLayout);
    	
    	countdownField.setEditable(false);
    	countdownField.setHorizontalAlignment(JTextField.CENTER);
    	countdownField.setFont(countdownFont);
    	countdownField.setText("00:00:00");
    	
    	timePanel.add(countdownLabel);
    	timePanel.add(countdownField);

    	elapsedField.setEditable(false);
    	elapsedField.setHorizontalAlignment(JTextField.CENTER);
    	elapsedField.setFont(elapsedFont);
    	elapsedField.setText("00:00:00");
    	
    	timePanel.add(elapsedLabel);
    	timePanel.add(elapsedField);

    	centralPanel.add(timePanel, "wrap");
    	
    	GridLayout buttonLayout = new GridLayout(1, 3);
    	
    	JPanel buttonPanel = new JPanel(buttonLayout);
    	
    	buttonPanel.add(startButton);
    	buttonPanel.add(pauseButton, "");
    	buttonPanel.add(stopButton, "");
    	
    	centralPanel.add(buttonPanel, "spanx, growx, wrap");
    	
    	panel.add(centralPanel, "wrap");
    	
    	display = new JTextArea(100,150);
        display.setMargin(new Insets(5,5,5,5));
        display.setEditable(false);
        
        JScrollPane myPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(myPane, "alignybottom, h 100:320, wrap");
        
        
        // Initial state of system
        paused = false;
        started = false;
        
        // Allowing interface to be displayed
    	setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


	}
	
	public void actionListeners(){
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		pauseButton.addActionListener(this);
		saveData.addActionListener(this);
		displayData.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String pressed = e.getActionCommand();
		
		
        // TODO: START: This method should check to see if the application is already running, and if not, launch a TimerThread object.
		// If the application is running, you may wish to ask the user if the existing thread should be stopped and a new thread started.
        // It should begin by launching a TimerDialog to get the number of seconds to count down, and then pass that number of seconds along
		// with the seconds since the start (0) to the TimerThread constructor.
		// It can then display a message in the text area stating how long the countdown is for.
		if(pressed.equalsIgnoreCase("START")) {
			System.out.println("Start Pressed");
			
			Thread t1;
			display.setText("");
			if(!started) {
			
				td = new TimerDialog(Timer.this, secondsToRun, true);
				display.append("Countdown for:  " + td.getSeconds() + " seconds\n");
				started = true;
				
				//countdownField.setText(Long.toString(secondsToRun));
				secondsToRun = td.getSeconds();
				secondsSinceStart = td.getSeconds();
				countdownThread = new TimerThread(countdownField, elapsedField, secondsToRun,secondsSinceStart);
				t1 = new Thread(countdownThread);
				t1.start();
			}
			
			else {
					int input = JOptionPane.showConfirmDialog(this,"Do you want to restart the timer?", 
					"Warning",JOptionPane.YES_NO_CANCEL_OPTION);
					if (input == 0) {
						countdownThread.stop();
						td = new TimerDialog(Timer.this, secondsToRun, true);
						display.setText("");
						display.append("Countdown for:  " + td.getSeconds() + " seconds\n");

						
						//countdownField.setText(Long.toString(secondsToRun));
						secondsToRun = td.getSeconds();
						secondsSinceStart = td.getSeconds();
						countdownThread = new TimerThread(countdownField, elapsedField, secondsToRun,secondsSinceStart);
						t1 = new Thread(countdownThread);
						t1.start();
						
					}
			
			}
		}
			
			
			
			
	    // TODO: STOP: This method should stop the TimerThread object and use appropriate methods to display the stop time
	    // and the total amount of time remaining in the countdown (if any).
			else if(pressed.equalsIgnoreCase("STOP")){
			  System.out.println("Stop Pressed");
			  if(started)
			  {
				  started = false;
				  countdownThread.stop();
				  display.append("\nTimer stopped at:  " + countdownThread.getElapsedSeconds() + " seconds\n");
				  countdownField.setText("00:00:00");
				  elapsedField.setText("00:00:00");
				  display.append("\nTime Remaining:  " + (secondsToRun - countdownThread.getElapsedSeconds()) + " seconds\n");
				  
			  }
        	  
        	  
		}
		
        // TODO: PAUSE: This method should call the TimerThread object's pause method and display a message in the text area
        // indicating whether this represents pausing or restarting the timer.
			else if(pressed.equalsIgnoreCase("PAUSE")){
        	 System.out.println("Pause Pressed");
        	 if(started)
        	 {
        		 paused =true;
        		 if(paused == true) {
        			 pauseButton.setText("RESUME");
        			 countdownThread.pause();
        			 display.append("\nPaused at:  " + countdownThread.getElapsedSeconds() + " seconds\n");
        		 }
        	 }
        	 }else if(pressed.equalsIgnoreCase("RESUME")) {
        		 System.out.println("Resume Pressed");
                 
        			 if(paused == true) {
        				 pauseButton.setText("PAUSE");
        				 countdownThread.pause();
        				 display.append("\nResumed at:  " + countdownThread.getElapsedSeconds() + " seconds\n");
        			 }
        	 }
       	     
       	  
		
		//For displaying stopped/paused/resumed in the text area, I wasn't sure if you wanted the total seconds or time running,
		//so I based mine on time running as I think it made more sense.
       	     
         
		 
	     // TODO: SAVE: This method should allow the user to specify a file name to which to save the contents of the text area using a 
	     // JFileChooser. You should check to see that the file does not already exist in the system.
		 if(pressed.equalsIgnoreCase("Save data")){
			 System.out.println("save clicked");
			 if(started) {
				 JOptionPane.showMessageDialog(Timer.this, "Timer must be stopped to save data");
			 } else {
				 jfc.setCurrentDirectory(currentWorkingDirectory);
				 int result = jfc.showSaveDialog(this);
				 System.out.println(result);
				 if (result == jfc.APPROVE_OPTION) {
					 System.out.println("save was clicked");
					 File fileToBeSaved = jfc.getSelectedFile();
					 try {
						writeDataFile(fileToBeSaved);
					} catch (IOException e1) {
						
					}
					 //String filepath = jfc.getSelectedFile().getAbsolutePath();
					 //System.out.println(filepath);
				 }
			 }
			 
		 }
		 
         if(pressed.equalsIgnoreCase("Display data")){
        	 System.out.println("display was clicked");
        	 jfc.setCurrentDirectory(currentWorkingDirectory);
        	 int result = jfc.showOpenDialog(this);
        	
        	 System.out.println(result);
        	
        	 if (result == jfc.APPROVE_OPTION) {
        		 File file = jfc.getSelectedFile();
            	 System.out.println(file);
            	 System.out.println("Opening:  " + file.getName() + "." + "\n");
            	 try {
					String fileContents = readDataFile(file);
					System.out.println(fileContents);
					
					new TimerFileContentsDialog(this, true, fileContents);
					
				} catch (ClassNotFoundException | IOException e1) {
					// TODO display error dialog
				}
            	 
        	 }
		 }
	   }

	public synchronized void writeDataFile(File f) throws IOException, FileNotFoundException {
		System.out.println("writeDataFile");
		System.out.println(f.getAbsolutePath());
		String dataToWriteToFile = display.getText();
		System.out.println(dataToWriteToFile);
		
		PrintWriter output = new PrintWriter(f);
		output.println(dataToWriteToFile);
		output.close();
	}
	
	public synchronized String readDataFile(File f) throws IOException, ClassNotFoundException {
		
		// Open input stream with file
		InputStream is = new FileInputStream(f.getAbsolutePath());
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		String line = buf.readLine();
		String result = "";
		// Read file line by line, and store each line to reconstruct the original contents
		while(line != null){
			result += line + "\n";
			line = buf.readLine();
		}
		buf.close();
		return result;
	}

    public static void main(String[] args) {

        Timer timer = new Timer();

    }

	

}
