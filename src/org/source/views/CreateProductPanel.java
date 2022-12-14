package org.source.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import org.source.controllers.CreatorProductPanelController;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class CreateProductPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JLabel m_nameLabel;
	private JLabel m_numberLabel;
	private JLabel m_priceLabel;
	private JLabel m_descriptionLabel;
	private JTextField m_nameText;
	private JTextField m_priceText;
	private JTextField m_numberText;
	private JTextArea m_descriptionText;
	private JScrollPane m_descriptionArea;
	private JButton m_addButton;
	private JButton m_backButton;
	
	public CreateProductPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Добавление нового товара", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 30));
		m_topText.setFont(m_fontButtons);
		
		m_nameLabel = new JLabel("Наименование: ", SwingConstants.LEFT);
		m_nameLabel.setPreferredSize(new Dimension(145, 30));
		m_nameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_nameLabel.setFont(m_fontButtons);
		
		m_nameText = new JTextField();
		m_nameText.setPreferredSize(new Dimension(303, 30));
		m_nameText.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_numberLabel = new JLabel("Количество: ", SwingConstants.LEFT);
		m_numberLabel.setPreferredSize(new Dimension(145, 30));
		m_numberLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_numberLabel.setFont(m_fontButtons);
		
		m_numberText = new JTextField();
		m_numberText.setPreferredSize(new Dimension(303, 30));
		m_numberText.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_priceLabel = new JLabel("Цена(в бел. руб.): ", SwingConstants.LEFT);
		m_priceLabel.setPreferredSize(new Dimension(145, 30));
		m_priceLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_priceLabel.setFont(m_fontButtons);
		
		m_priceText = new JTextField();
		m_priceText.setPreferredSize(new Dimension(303, 30));
		m_priceText.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_descriptionLabel = new JLabel("Описание: ", SwingConstants.LEFT);
		m_descriptionLabel.setPreferredSize(new Dimension(145, 30));
		m_descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
		m_descriptionLabel.setFont(m_fontButtons);
		
		m_descriptionText = new JTextArea(5, 25);
		m_descriptionText.setFont(new Font("Times Roman", Font.BOLD, 14));
		m_descriptionText.setWrapStyleWord(true);
		m_descriptionText.setLineWrap(true);
		
		m_descriptionArea = new JScrollPane(m_descriptionText);
		m_descriptionArea.setVerticalScrollBarPolicy(ScrollPaneConstants
				.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		m_addButton = new JButton("Добавить новый товар");
		m_addButton.setPreferredSize(new Dimension(300, 50));
		m_addButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_nameLabel);
		m_componentsPanel.add(m_nameText);
		m_componentsPanel.add(m_numberLabel);
		m_componentsPanel.add(m_numberText);
		m_componentsPanel.add(m_priceLabel);
		m_componentsPanel.add(m_priceText);
		m_componentsPanel.add(m_descriptionLabel);
		m_componentsPanel.add(m_descriptionArea);
		m_componentsPanel.add(m_addButton);
		m_componentsPanel.add(m_backButton);
	}
	
	private void addEvents() {
		m_addButton.addActionListener(this);
		m_backButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_addButton) {
			CacheService.getInstance().setNameProduct(m_nameText.getText());
			CacheService.getInstance().setNumberProduct(m_numberText.getText());
			CacheService.getInstance().setPriceProduct(m_priceText.getText());
			CacheService.getInstance().setDescriptionProduct(m_descriptionText.getText());
			CreatorProductPanelController.getInstance().add();
		} else if(object == m_backButton) {
			CreatorProductPanelController.getInstance().back();
		}
	}

}
