package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.source.controllers.ClientPersonalPanelController;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class ClientPersonalPanel extends BasePanel {

	private JLabel m_topText;
	private JLabel m_secondNameLabel;
	private JLabel m_firstNameLabel;
	private JLabel m_middleNameLabel;
	private JLabel m_emailLabel;
	private JLabel m_loginLabel;
	private JPanel m_componentsPanel;
	private JButton m_ordersButton;
	private JButton m_backButton;
	
	public ClientPersonalPanel() {
		super();
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Личный кабинет", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(450, 30));
		m_topText.setFont(m_fontButtons);
		
		m_secondNameLabel = new JLabel("Фамилия: " + 
				CacheService.getInstance().getSecondName(), 
				SwingConstants.LEFT);
		m_secondNameLabel.setPreferredSize(new Dimension(300, 40));
		m_secondNameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_secondNameLabel.setFont(m_fontButtons);
		
		m_firstNameLabel = new JLabel("Имя: " + 
				CacheService.getInstance().getFirstName(), 
				SwingConstants.LEFT);
		m_firstNameLabel.setPreferredSize(new Dimension(300, 40));
		m_firstNameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_firstNameLabel.setFont(m_fontButtons);
		
		m_middleNameLabel = new JLabel("Отчество: " + 
				CacheService.getInstance().getMiddleName(), 
				SwingConstants.LEFT);
		m_middleNameLabel.setPreferredSize(new Dimension(300, 40));
		m_middleNameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_middleNameLabel.setFont(m_fontButtons);
		
		m_emailLabel = new JLabel("Эл. почта: " + 
				CacheService.getInstance().getEmail(), 
				SwingConstants.LEFT);
		m_emailLabel.setPreferredSize(new Dimension(300, 40));
		m_emailLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_emailLabel.setFont(m_fontButtons);
		
		m_loginLabel = new JLabel("Логин: " + 
				CacheService.getInstance().getLogin(), 
				SwingConstants.LEFT);
		m_loginLabel.setPreferredSize(new Dimension(300, 40));
		m_loginLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_loginLabel.setFont(m_fontButtons);
		
		m_ordersButton = new JButton("История заказов на поставку");
		m_ordersButton.setPreferredSize(new Dimension(300, 50));
		m_ordersButton.setFont(m_fontButtons);
		
		m_backButton = new JButton("Назад");
		m_backButton.setPreferredSize(new Dimension(300, 50));
		m_backButton.setFont(m_fontButtons);
		
		m_componentsPanel = new JPanel();
	}
	private void buildLayout() {
		m_view.setLayout(new BoxLayout(m_view, BoxLayout.Y_AXIS));
		m_view.add(m_componentsPanel);
		m_componentsPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		m_componentsPanel.add(m_topText);
		m_componentsPanel.add(m_secondNameLabel);
		m_componentsPanel.add(m_firstNameLabel);
		m_componentsPanel.add(m_middleNameLabel);
		m_componentsPanel.add(m_loginLabel);
		m_componentsPanel.add(m_emailLabel);
		m_componentsPanel.add(m_ordersButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_ordersButton.addActionListener(this);
		m_backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_ordersButton) {
			ClientPersonalPanelController.getInstance().openHistory();
		} else if(object == m_backButton) {
			ClientPersonalPanelController.getInstance().back();
		}
	}
}
