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

import org.source.controllers.ControlAdminPanelController;
import org.source.views.base.BasePanel;

public class ControlAdminPanel extends BasePanel {

	private JPanel m_componentsPanel;
	private JButton m_productsButton;
	private JButton m_clientsButton;
	private JButton m_ordersButton;
	private JButton m_statisticButton;
	private JButton m_exitButton;
	private JButton m_exitAdminButton;
	private JLabel m_logo;
	
	public ControlAdminPanel() {
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
		
		m_productsButton = new JButton("Товары для заказа");
		m_productsButton.setPreferredSize(new Dimension(300, 50));
		m_productsButton.setFont(m_fontButtons);
		
		m_clientsButton = new JButton("Менеджеры");
		m_clientsButton.setPreferredSize(new Dimension(300, 50));
		m_clientsButton.setFont(m_fontButtons);
		
		m_ordersButton = new JButton("Заказы на поставку");
		m_ordersButton.setPreferredSize(new Dimension(300, 50));
		m_ordersButton.setFont(m_fontButtons);
		
		m_statisticButton = new JButton("Статистика");
		m_statisticButton.setPreferredSize(new Dimension(300, 50));
		m_statisticButton.setFont(m_fontButtons);
		
		m_exitAdminButton = new JButton("Выход из аккаунта");
		m_exitAdminButton.setPreferredSize(new Dimension(300, 50));
		m_exitAdminButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_ordersButton);
		m_componentsPanel.add(m_clientsButton);
		m_componentsPanel.add(m_statisticButton);
		m_componentsPanel.add(m_exitAdminButton);
		m_componentsPanel.add(m_exitButton);
	}
	
	private void addEvents() {
		m_productsButton.addActionListener(this);
		m_clientsButton.addActionListener(this);
		m_ordersButton.addActionListener(this);
		m_statisticButton.addActionListener(this);
		m_exitAdminButton.addActionListener(this);
		m_exitButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object object = event.getSource();
		if(object == m_productsButton) {
			ControlAdminPanelController.getInstance().products();
		} else if(object == m_clientsButton) {
			ControlAdminPanelController.getInstance().clients();
		} else if(object == m_ordersButton) {
			ControlAdminPanelController.getInstance().orders();
		} else if(object == m_statisticButton) {
			ControlAdminPanelController.getInstance().statistic();
		} else if(object == m_exitAdminButton) {
			ControlAdminPanelController.getInstance().close();
		} else if(object == m_exitButton) {
			ControlAdminPanelController.getInstance().exit();
		}
	}

}
