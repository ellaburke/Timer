
import java.awt.*;

import javax.swing.*;

public class TimerFileContentsDialog extends JDialog {
	
	JTextArea display = new JTextArea(5, 10);
	
	public TimerFileContentsDialog(Frame owner, boolean modality, String textToDisplay) {
		super(owner, modality);

		setTitle("File contents");	
		setLayout(new BorderLayout());
		
		display = new JTextArea(10,15);
        display.setMargin(new Insets(5,5,5,5));
        display.setEditable(false);
		display.setText(textToDisplay);
		JPanel container = new JPanel();
        JScrollPane myPane = new JScrollPane(display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(myPane);
		
		add(container);
		setLocationRelativeTo(owner);
		
		setSize(200, 400);
		setVisible(true);
	}
}
