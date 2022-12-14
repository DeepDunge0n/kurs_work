package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.source.controllers.ControlUsersPanelController;
import org.source.models.User;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class ControlUsersPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JTextField m_searchField;
	private JButton m_searchButton;
	private JScrollPane m_listUsersScroll;
	private JButton m_descriptionButton;
	private JButton m_saveButton;
	private JButton m_backButton;
	private JList m_listUsers;
	
	public ControlUsersPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Список менеджеров", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 40));
		m_topText.setFont(m_fontButtons);
		
		m_searchField = new JTextField(27);
		m_searchField.setPreferredSize(new Dimension(200, 25));
		
		m_searchButton = new JButton("Поиск");
		m_searchButton.setPreferredSize(new Dimension(90, 25));
		m_searchButton.setFont(m_fontButtons);
		
		m_listUsers = new JList();
		m_listUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_listUsers.setLayoutOrientation(JList.VERTICAL);
		updatePanel();
		
		m_listUsersScroll = new JScrollPane(m_listUsers);
		m_listUsersScroll.setPreferredSize(new Dimension(450, 200));
		
		m_descriptionButton = new JButton("Информация о пользователе");
		m_descriptionButton.setPreferredSize(new Dimension(300, 50));
		m_descriptionButton.setEnabled(false);
		m_descriptionButton.setFont(m_fontButtons);
		
		m_saveButton = new JButton("Сохранить список в файл");
		m_saveButton.setPreferredSize(new Dimension(300, 50));
		m_saveButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_searchField);
		m_componentsPanel.add(m_searchButton);
		m_componentsPanel.add(m_listUsersScroll);
		m_componentsPanel.add(m_descriptionButton);
		m_componentsPanel.add(m_saveButton);
		m_componentsPanel.add(m_backButton);
	}
	
	private void addEvents() {
		m_searchButton.addActionListener(this);
		m_descriptionButton.addActionListener(this);
		m_saveButton.addActionListener(this);
		m_backButton.addActionListener(this);
		m_listUsers.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (m_listUsers.getSelectedIndex() >= 0) {
                	m_descriptionButton.setEnabled(true);
                } else {
                	m_descriptionButton.setEnabled(false);
                }
            }
        });
	}
	
	@Override
	public void updatePanel() {
		m_listUsers.clearSelection();
		DefaultListModel listModel = new DefaultListModel();
		for(User user : CacheService.getInstance().getListUsers()) {
			listModel.addElement(" Логин пользователя: " + user.getLogin());
		}
		m_listUsers.setModel(listModel);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_searchButton) {
			CacheService.getInstance().setSearchString(m_searchField.getText());
			ControlUsersPanelController.getInstance().search(this);
		} else if(object == m_descriptionButton) {
			CacheService.getInstance().setIdSelectedUser(
					CacheService.getInstance().getListUsers().
					get(m_listUsers.getSelectedIndex()).getId());
			ControlUsersPanelController.getInstance().description();
		} else if(object == m_saveButton) {
			ControlUsersPanelController.getInstance().save();
		} else if(object == m_backButton) {
			ControlUsersPanelController.getInstance().back();
		}
	}

}
