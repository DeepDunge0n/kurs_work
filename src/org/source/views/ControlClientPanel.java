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

import org.source.controllers.ControlClientPanelController;
import org.source.views.base.BasePanel;

public class ControlClientPanel extends BasePanel {
	
	private JPanel m_componentsPanel;
	private JButton m_orderButton;
	private JButton m_productsButton;
	private JButton m_exitButton;
	private JButton m_personalButton;
	private JButton m_closeButton;
	private JLabel m_logo;
	
	
	public ControlClientPanel() {
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
		
		m_orderButton = new JButton("Оформить заказ");
		m_orderButton.setPreferredSize(new Dimension(300, 50));
		m_orderButton.setFont(m_fontButtons);
		
		m_personalButton = new JButton("Личный кабинет");
		m_personalButton.setPreferredSize(new Dimension(300, 50));
		m_personalButton.setFont(m_fontButtons);
		
		m_closeButton = new JButton("Выйти из аккаунта");
		m_closeButton.setPreferredSize(new Dimension(300, 50));
		m_closeButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_orderButton);
		m_componentsPanel.add(m_personalButton);
		m_componentsPanel.add(m_closeButton);
		m_componentsPanel.add(m_exitButton);
	}
	
	private void addEvents() {
		m_productsButton.addActionListener(this);
		m_orderButton.addActionListener(this);
		m_personalButton.addActionListener(this);
		m_closeButton.addActionListener(this);
		m_exitButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_productsButton) {
			ControlClientPanelController.getInstance().products();
		} else if(object == m_orderButton) {
			ControlClientPanelController.getInstance().order();
		} else if(object == m_personalButton) {
			ControlClientPanelController.getInstance().personal();
		} else if(object == m_closeButton) {
			ControlClientPanelController.getInstance().close();
		} else if(object == m_exitButton) {
			ControlClientPanelController.getInstance().exit();
		}
	}
	
}
