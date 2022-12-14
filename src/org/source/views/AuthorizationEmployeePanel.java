package org.source.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.source.controllers.AuthorizationEmployeePanelController;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class AuthorizationEmployeePanel extends BasePanel {

	private JLabel m_topText;
	private JLabel m_infoText;
	private JPanel m_componentsPanel;
	private JLabel m_loginLabel;
	private JLabel m_passLabel;
	private JTextField m_loginField;
	private JPasswordField m_passField;
	private JButton m_authorizationButton;
	private JButton m_backButton;
	
	public AuthorizationEmployeePanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Для сотруднриков", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(450, 30));
		m_topText.setFont(m_fontButtons);
		
		m_infoText = new JLabel("<html>Если вы являетесь сотрудником " +
				"данной компании, то вы можете пройти авторизацию и" +
				" получить доступ к другим функциям данной программы.<html>");
		m_infoText.setVerticalAlignment(SwingConstants.TOP);
		m_infoText.setPreferredSize(new Dimension(450, 80));
		m_infoText.setFont(new Font("Times Roman", Font.PLAIN, 16));
		
		m_loginLabel = new JLabel("Логин:", SwingConstants.CENTER);
		m_loginLabel.setPreferredSize(new Dimension(120, 30));
		m_loginLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_loginLabel.setFont(m_fontButtons);
		
		m_loginField = new JTextField();
		m_loginField.setPreferredSize(new Dimension(250, 30));
		m_loginField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_passLabel = new JLabel("Пароль:", SwingConstants.CENTER);
		m_passLabel.setPreferredSize(new Dimension(120, 30));
		m_passLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_passLabel.setFont(m_fontButtons);
		
		m_passField = new JPasswordField();
		m_passField.setPreferredSize(new Dimension(250, 30));
		m_passField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_authorizationButton = new JButton("Авторизация");
		m_authorizationButton.setPreferredSize(new Dimension(300, 50));
		m_authorizationButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_infoText);
		m_componentsPanel.add(m_loginLabel);
		m_componentsPanel.add(m_loginField);
		m_componentsPanel.add(m_passLabel);
		m_componentsPanel.add(m_passField);
		m_componentsPanel.add(m_authorizationButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_authorizationButton.addActionListener(this);
		m_backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_authorizationButton) {
			CacheService.getInstance().setLogin(m_loginField.getText());
			CacheService.getInstance().setPassword(String.valueOf((m_passField.getPassword())));
			AuthorizationEmployeePanelController.getInstance().authorization();
		} else if(object == m_backButton) {
			AuthorizationEmployeePanelController.getInstance().back();
		}
	}
}
