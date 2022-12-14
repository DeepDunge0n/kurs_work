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

import org.source.controllers.RegistrationClientPanelController;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class RegistrationClientPanel extends BasePanel {

	private JLabel m_topText;
	private JLabel m_infoText;
	private JPanel m_componentsPanel;
	private JLabel m_loginLabel;
	private JLabel m_passLabel;
	private JLabel m_repPassLabel;
	private JLabel m_emailLabel;
	private JLabel m_snameLabel;
	private JLabel m_fnameLabel;
	private JLabel m_mnameLabel;
	private JTextField m_loginField;
	private JPasswordField m_passField;
	private JPasswordField m_repPassField;
	private JTextField m_emailField;
	private JTextField m_snameField;
	private JTextField m_fnameField;
	private JTextField m_mnameField;
	private JButton m_registrationButton;
	private JButton m_backButton;
	
	public RegistrationClientPanel() {
		super();
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Регистрация аккаунта", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(450, 30));
		m_topText.setFont(m_fontButtons);
		
		m_infoText = new JLabel("<html>Для регистрации заполните " +
				"следуюшие поля:<html>");
		m_infoText.setVerticalAlignment(SwingConstants.TOP);
		m_infoText.setPreferredSize(new Dimension(450, 25));
		m_infoText.setFont(new Font("Times Roman", Font.PLAIN, 16));
		
		m_snameLabel = new JLabel("Фамилия:", SwingConstants.CENTER);
		m_snameLabel.setPreferredSize(new Dimension(130, 30));
		m_snameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_snameLabel.setFont(m_fontButtons);
		
		m_snameField = new JTextField();
		m_snameField.setPreferredSize(new Dimension(250, 30));
		m_snameField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_fnameLabel = new JLabel("Имя:", SwingConstants.CENTER);
		m_fnameLabel.setPreferredSize(new Dimension(130, 30));
		m_fnameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_fnameLabel.setFont(m_fontButtons);
		
		m_fnameField = new JTextField();
		m_fnameField.setPreferredSize(new Dimension(250, 30));
		m_fnameField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_mnameLabel = new JLabel("Отчество:", SwingConstants.CENTER);
		m_mnameLabel.setPreferredSize(new Dimension(130, 30));
		m_mnameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_mnameLabel.setFont(m_fontButtons);
		
		m_mnameField = new JTextField();
		m_mnameField.setPreferredSize(new Dimension(250, 30));
		m_mnameField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_emailLabel = new JLabel("Эл. почта:", SwingConstants.CENTER);
		m_emailLabel.setPreferredSize(new Dimension(130, 30));
		m_emailLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_emailLabel.setFont(m_fontButtons);
		
		m_emailField = new JTextField();
		m_emailField.setPreferredSize(new Dimension(250, 30));
		m_emailField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_loginLabel = new JLabel("Логин:", SwingConstants.CENTER);
		m_loginLabel.setPreferredSize(new Dimension(130, 30));
		m_loginLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_loginLabel.setFont(m_fontButtons);
		
		m_loginField = new JTextField();
		m_loginField.setPreferredSize(new Dimension(250, 30));
		m_loginField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_passLabel = new JLabel("Пароль:", SwingConstants.CENTER);
		m_passLabel.setPreferredSize(new Dimension(130, 30));
		m_passLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_passLabel.setFont(m_fontButtons);
		
		m_passField = new JPasswordField();
		m_passField.setPreferredSize(new Dimension(250, 30));
		m_passField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_repPassLabel = new JLabel("Повтор пароля:", SwingConstants.CENTER);
		m_repPassLabel.setPreferredSize(new Dimension(130, 30));
		m_repPassLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_repPassLabel.setFont(m_fontButtons);
		
		m_repPassField = new JPasswordField();
		m_repPassField.setPreferredSize(new Dimension(250, 30));
		m_repPassField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_registrationButton = new JButton("Регистрация");
		m_registrationButton.setPreferredSize(new Dimension(300, 50));
		m_registrationButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_snameLabel);
		m_componentsPanel.add(m_snameField);
		m_componentsPanel.add(m_fnameLabel);
		m_componentsPanel.add(m_fnameField);
		m_componentsPanel.add(m_mnameLabel);
		m_componentsPanel.add(m_mnameField);
		m_componentsPanel.add(m_emailLabel);
		m_componentsPanel.add(m_emailField);
		m_componentsPanel.add(m_loginLabel);
		m_componentsPanel.add(m_loginField);
		m_componentsPanel.add(m_passLabel);
		m_componentsPanel.add(m_passField);
		m_componentsPanel.add(m_repPassLabel);
		m_componentsPanel.add(m_repPassField);
		m_componentsPanel.add(m_registrationButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_registrationButton.addActionListener(this);
		m_backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		Object object = event.getSource();
		if(object == m_registrationButton) {
			
			CacheService.getInstance().setSecondName(m_snameField.getText());
			CacheService.getInstance().setFirstName(m_fnameField.getText());
			CacheService.getInstance().setMiddleName(m_mnameField.getText());
			CacheService.getInstance().setEmail(m_emailField.getText());
			CacheService.getInstance().setLogin(m_loginField.getText());
			CacheService.getInstance().setPassword(
					String.valueOf(m_passField.getPassword()));
			CacheService.getInstance().setRepetitionPass(
					String.valueOf(m_repPassField.getPassword()));
			
			RegistrationClientPanelController.getInstance().registration();
		} else if(object == m_backButton) {
			RegistrationClientPanelController.getInstance().back();
		}
	}

}
