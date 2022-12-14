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

import org.source.controllers.ProductDetailsPanelController;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class ProductDetailsPanel extends BasePanel {

	private JLabel m_topText;
	private JLabel m_nameLabel;
	private JLabel m_numberLabel;
	private JLabel m_priceLabel;
	private JLabel m_descriptionLabel;
	private JTextField m_nameField;
	private JTextField m_numberField;
	private JTextField m_priceField;
	private JTextArea m_descriptionField;
	private JScrollPane m_descriptionArea;
	private JPanel m_componentsPanel;
	private JButton m_updateButton;
	private JButton m_backButton;
	
	public ProductDetailsPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Данные о товаре", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 30));
		m_topText.setFont(m_fontButtons);
		
		m_nameLabel = new JLabel("Наименование: ", SwingConstants.LEFT);
		m_nameLabel.setPreferredSize(new Dimension(145, 30));
		m_nameLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_nameLabel.setFont(m_fontButtons);
		
		m_nameField = new JTextField();
		m_nameField.setPreferredSize(new Dimension(303, 30));
		m_nameField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_numberLabel = new JLabel("Количество: ", SwingConstants.LEFT);
		m_numberLabel.setPreferredSize(new Dimension(145, 30));
		m_numberLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_numberLabel.setFont(m_fontButtons);
		
		m_numberField = new JTextField();
		m_numberField.setPreferredSize(new Dimension(303, 30));
		m_numberField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_priceLabel = new JLabel("Цена(в бел. руб.): ", SwingConstants.LEFT);
		m_priceLabel.setPreferredSize(new Dimension(145, 30));
		m_priceLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_priceLabel.setFont(m_fontButtons);
		
		m_priceField = new JTextField();
		m_priceField.setPreferredSize(new Dimension(303, 30));
		m_priceField.setFont(new Font("Times Roman", Font.BOLD, 14));
		
		m_descriptionLabel = new JLabel("Описание: ", SwingConstants.LEFT);
		m_descriptionLabel.setPreferredSize(new Dimension(145, 30));
		m_descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
		m_descriptionLabel.setFont(m_fontButtons);
		
		m_descriptionField = new JTextArea(5, 25);
		m_descriptionField.setFont(new Font("Times Roman", Font.BOLD, 14));
		m_descriptionField.setWrapStyleWord(true);
		m_descriptionField.setLineWrap(true);
		
		m_descriptionArea = new JScrollPane(m_descriptionField);
		m_descriptionArea.setVerticalScrollBarPolicy(ScrollPaneConstants
				.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		m_updateButton = new JButton("Сохранить изменения");
		m_updateButton.setPreferredSize(new Dimension(300, 50));
		m_updateButton.setFont(m_fontButtons);
		
		m_backButton = new JButton("Назад");
		m_backButton.setPreferredSize(new Dimension(300, 50));
		m_backButton.setFont(m_fontButtons);
		
		m_componentsPanel = new JPanel();
		
		updatePanel();
	}
	
	private void buildLayout() {
		m_view.setLayout(new BoxLayout(m_view, BoxLayout.Y_AXIS));
		m_view.add(m_componentsPanel);
		m_componentsPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		m_componentsPanel.add(m_topText);
		m_componentsPanel.add(m_nameLabel);
		m_componentsPanel.add(m_nameField);
		m_componentsPanel.add(m_numberLabel);
		m_componentsPanel.add(m_numberField);
		m_componentsPanel.add(m_priceLabel);
		m_componentsPanel.add(m_priceField);
		m_componentsPanel.add(m_descriptionLabel);
		m_componentsPanel.add(m_descriptionArea);
		m_componentsPanel.add(m_updateButton);
		m_componentsPanel.add(m_backButton);
	}
	
	private void addEvents() {
		m_updateButton.addActionListener(this);
		m_backButton.addActionListener(this);
	}
	
	@Override
	public void updatePanel() {
		m_nameField.setText(CacheService.getInstance().getNameProduct());
		m_numberField.setText(CacheService.getInstance().getNumberProduct());
		m_priceField.setText(CacheService.getInstance().getPriceProduct());
		m_descriptionField.setText(CacheService.getInstance().getDescriptionProduct());
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_updateButton) {
			CacheService.getInstance().setNameProduct(m_nameField.getText());
			CacheService.getInstance().setNumberProduct(m_numberField.getText());
			CacheService.getInstance().setPriceProduct(m_priceField.getText());
			CacheService.getInstance().setDescriptionProduct(m_descriptionField.getText());
			ProductDetailsPanelController.getInstance().update();
		} else if(object == m_backButton) {
			ProductDetailsPanelController.getInstance().back();
		}
	}
}
