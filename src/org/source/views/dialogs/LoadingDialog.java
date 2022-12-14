package org.source.views.dialogs;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.source.ApplicationUI;

public class LoadingDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	final private static int WIDTH_DIALOG = 400;
	final private static int HEIGHT_DIALOG = 150;
	final private static String NAME_WINDOW = "Загрузка...";
	
	private JProgressBar m_loadingIndicator;
	private JLabel m_stateLabel;
	
	public LoadingDialog(ApplicationUI parent) {
		
		super(parent, NAME_WINDOW, true);
		
		// init components
		m_loadingIndicator = new JProgressBar(0, 100);
		m_stateLabel = new JLabel("");
		
		//init window
		add(BorderLayout.CENTER, m_loadingIndicator);
	    add(BorderLayout.NORTH, m_stateLabel);
	    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	    setSize(WIDTH_DIALOG, HEIGHT_DIALOG);
	    setLocationRelativeTo(parent);
	}
	
	public void showDialog() {
		/*m_threadDialog = new Thread(new Runnable() {
			public void run() {
				JOptionPane.showOptionDialog(rootPane,
		                {m_text}, title, optionType, messageType, null,
		                options, optionSelection);
		    }
		});
		m_threadDialog.start();*/
	}
	public void closeDialog() {
		dispose();
		setVisible(false);
	}
	public void setState(String stateText, int progress) {
		m_stateLabel.setText(stateText);
		//m_loadingIndicator.setValue(progress);
	}
}
