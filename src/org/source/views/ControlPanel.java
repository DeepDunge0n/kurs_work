package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.source.controllers.ControlPanelController;

import org.source.views.base.BasePanel;

public class ControlPanel extends BasePanel {
	
	private JPanel m_componentsPanel;
	private JButton m_exitButton;
	private JButton m_productsButton;
	private JButton m_employeeButton;
	private JButton m_authorizationButton;
	private JLabel m_logo;
	
	
	public ControlPanel() {
		super();
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("res/logo.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		m_logo = new JLabel(new ImageIcon(myPicture));
		
		m_productsButton = new JButton("Товары");
		m_productsButton.setPreferredSize(new Dimension(300, 50));
		m_productsButton.setFont(m_fontButtons);

		m_authorizationButton = new JButton("Для менеджеров компании");
		m_authorizationButton.setPreferredSize(new Dimension(300, 50));
		m_authorizationButton.setFont(m_fontButtons);
		
		m_employeeButton = new JButton("Для админов компании");
		m_employeeButton.setPreferredSize(new Dimension(300, 50));
		m_employeeButton.setFont(m_fontButtons);

		m_exitButton = new JButton("Выход");
		m_exitButton.setPreferredSize(new Dimension(300, 50));
		m_exitButton.setFont(m_fontButtons);
		
		m_componentsPanel = new JPanel();
	}

	private void buildLayout() {
		
		m_view.setLayout(new BoxLayout(m_view, BoxLayout.Y_AXIS));
		m_view.add(m_componentsPanel);
		m_componentsPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		m_componentsPanel.add(m_logo);
		m_componentsPanel.add(m_productsButton);
		m_componentsPanel.add(m_authorizationButton);
		m_componentsPanel.add(m_employeeButton);
		m_componentsPanel.add(m_exitButton);
	}
	
	private void addEvents() {
		m_productsButton.addActionListener(this);
		m_employeeButton.addActionListener(this);
		m_authorizationButton.addActionListener(this);
		m_exitButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_productsButton) {
			ControlPanelController.getInstance().products();
		} else if(object == m_authorizationButton) {
			ControlPanelController.getInstance().authorizationClients();
		} else if(object == m_employeeButton) {
			ControlPanelController.getInstance().authorizationEmployees();
		} else if(object == m_exitButton) {
			ControlPanelController.getInstance().exit();
		}
	}
}
