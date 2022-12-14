package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.source.controllers.BasketPanelController;
import org.source.models.Product;
import org.source.service.CacheService;
import org.source.service.DataService;
import org.source.views.base.BasePanel;

public class BasketPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JList m_listProducts;
	private JScrollPane m_listProductsScroll;
	private JButton m_changeButton;
	private JButton m_deleteButton;
	private JButton m_descriptionButton;
	private JButton m_makeOrderButton;
	private JButton m_backButton;
	
	public BasketPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Лист заказов", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(300, 30));
		m_topText.setFont(m_fontButtons);
		
		m_listProducts = new JList();
		m_listProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_listProducts.setLayoutOrientation(JList.VERTICAL);
		updateBasket();
		
		m_listProductsScroll = new JScrollPane(m_listProducts);
		m_listProductsScroll.setPreferredSize(new Dimension(450, 150));
		
		m_descriptionButton = new JButton("Описание товара");
		m_descriptionButton.setPreferredSize(new Dimension(300, 50));
		m_descriptionButton.setEnabled(false);
		m_descriptionButton.setFont(m_fontButtons);
		
		m_deleteButton = new JButton("Удалить из листа заказов");
		m_deleteButton.setPreferredSize(new Dimension(300, 50));
		m_deleteButton.setEnabled(false);
		m_deleteButton.setFont(m_fontButtons);
		
		m_changeButton = new JButton("Изменить колличество");
		m_changeButton.setPreferredSize(new Dimension(300, 50));
		m_changeButton.setEnabled(false);
		m_changeButton.setFont(m_fontButtons);
		
		m_makeOrderButton = new JButton("Отправить заказ");
		m_makeOrderButton.setPreferredSize(new Dimension(300, 50));
		m_makeOrderButton.setFont(m_fontButtons);
		
		m_backButton = new JButton("Назад");
		m_backButton.setPreferredSize(new Dimension(300, 50));
		m_backButton.setFont(m_fontButtons);
		
		m_componentsPanel = new JPanel();
	}
	
	public void updateBasket() {
		DefaultListModel listModel = new DefaultListModel();
		for(Entry<Integer, Integer> entry : DataService.getInstance().getBasket().getDataOrder().entrySet()) {
			int idProduct = entry.getKey();
			for(Product product : CacheService.getInstance().getListProducts())
				if(idProduct == product.getId())
					listModel.addElement(product.getName() + " Кол: " + entry.getValue()
							+ " Цена: " + product.getPrice() * entry.getValue() + " бел.руб. ");
		}
		m_listProducts.setModel(listModel);
	}
	
	private void buildLayout() {
		m_view.setLayout(new BoxLayout(m_view, BoxLayout.Y_AXIS));
		m_view.add(m_componentsPanel);
		m_componentsPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		m_componentsPanel.add(m_topText);
		m_componentsPanel.add(m_listProductsScroll);
		m_componentsPanel.add(m_changeButton);
		m_componentsPanel.add(m_deleteButton);
		m_componentsPanel.add(m_descriptionButton);
		m_componentsPanel.add(m_makeOrderButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_changeButton.addActionListener(this);
		m_deleteButton.addActionListener(this);
		m_descriptionButton.addActionListener(this);
		m_makeOrderButton.addActionListener(this);
		m_backButton.addActionListener(this);
		m_listProducts.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (m_listProducts.getSelectedIndex() >= 0) {
                	m_descriptionButton.setEnabled(true);
                	m_changeButton.setEnabled(true);
                	m_deleteButton.setEnabled(true);
                } else {
                	m_descriptionButton.setEnabled(false);
                	m_changeButton.setEnabled(false);
                	m_deleteButton.setEnabled(false);
                }
            }
        });
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		
		if(object == m_changeButton) {
			
			int iterator = 0;
			for(Entry<Integer, Integer> entry : DataService.getInstance().getBasket().getDataOrder().entrySet()) {
				if(m_listProducts.getSelectedIndex() == iterator) {
					CacheService.getInstance().setIdSelectedProduct(entry.getKey());
					break;
				} else ++iterator;
			}
			BasketPanelController.getInstance().change(this);
			
		} else if(object == m_deleteButton) {
			
			int iterator = 0;
			for(Entry<Integer, Integer> entry : DataService.getInstance().getBasket().getDataOrder().entrySet()) {
				if(m_listProducts.getSelectedIndex() == iterator) {
					CacheService.getInstance().setIdSelectedProduct(entry.getKey());
					break;
				} else ++iterator;
			}
			BasketPanelController.getInstance().delete(this);
			
		} else if(object == m_descriptionButton) {
			int iterator = 0;
			for(Entry<Integer, Integer> entry : DataService.getInstance().getBasket().getDataOrder().entrySet()) {
				if(m_listProducts.getSelectedIndex() == iterator) {
					CacheService.getInstance().setIdSelectedProduct(entry.getKey());
					break;
				} else ++iterator;
			}
			BasketPanelController.getInstance().description();
			
		} else if(object == m_makeOrderButton) {
			BasketPanelController.getInstance().makeOrder();
		} else if(object == m_backButton) {
			BasketPanelController.getInstance().back();
		}
	}
}
