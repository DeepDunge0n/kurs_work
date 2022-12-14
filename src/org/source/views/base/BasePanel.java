package org.source.views.base;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

abstract public class BasePanel implements ActionListener {
	
	protected JPanel m_view;
	protected Font m_fontButtons;
	
	public BasePanel() {
		this.m_view = new JPanel();
		this.m_fontButtons = new Font("Times Roman", Font.BOLD, 16);
	}
	
	public BasePanel(JPanel view) {
		this.m_view = view;
	}
	
	public JPanel getView() {
		return this.m_view;
	}
	
	public void setVisible(boolean visible) {
		this.m_view.setVisible(visible);
	}
	public void updatePanel() {}
}
