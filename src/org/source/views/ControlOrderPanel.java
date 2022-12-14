package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.source.controllers.ControlOrderPanelController;
import org.source.models.Product;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class ControlOrderPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JLabel m_numberOrder;
	private JLabel m_summOrder;
	private JLabel m_dateOrder;
	private JLabel m_stateOrder;
	private JLabel m_userOrder;
	private JList m_listProducts;
	private JComboBox m_listStatuses;
	private JScrollPane m_listProductsScroll;
	private JButton m_descriptionButton;
	private JButton m_changeStatusButton;
	private JButton m_deleteButton;
	private JButton m_backButton;
	
	public ControlOrderPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Информация о заказе", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 30));
		m_topText.setFont(m_fontButtons);
		
		m_numberOrder = new JLabel("Номер заказа: " + 
				CacheService.getInstance().getCurrentOrder().getId(), 
				SwingConstants.LEFT);
		m_numberOrder.setPreferredSize(new Dimension(400, 25));
		m_numberOrder.setFont(m_fontButtons);
		
		m_dateOrder = new JLabel("Дата заказа: " + 
				CacheService.getInstance().getCurrentOrder().getStringDate(), 
				SwingConstants.LEFT);
		m_dateOrder.setPreferredSize(new Dimension(400, 25));
		m_dateOrder.setFont(m_fontButtons);
		
		m_userOrder = new JLabel("Логин пользователя: " + 
				CacheService.getInstance().getLogin(), 
				SwingConstants.LEFT);
		m_userOrder.setPreferredSize(new Dimension(400, 25));
		m_userOrder.setFont(m_fontButtons);
		
		m_summOrder = new JLabel("Сумма заказа: " + 
				CacheService.getInstance().getCurrentOrder().getPrice()
				+ " бел. руб.", SwingConstants.LEFT);
		m_summOrder.setPreferredSize(new Dimension(400, 25));
		m_summOrder.setFont(m_fontButtons);
		
		m_stateOrder = new JLabel("Статус заказа: ", SwingConstants.LEFT);
		m_stateOrder.setPreferredSize(new Dimension(120, 30));
		m_stateOrder.setFont(m_fontButtons);
		
		m_listStatuses = new JComboBox();
		m_listStatuses.addItem("В ожидании");
		m_listStatuses.addItem("Отправлен");
		m_listStatuses.addItem("Выполнен");
		m_listStatuses.setPreferredSize(new Dimension(130, 30));
		m_listStatuses.setSelectedIndex(CacheService.getInstance().getCurrentOrder().
				getState());
		
		DefaultListModel listModel = new DefaultListModel();
		for(Product product : CacheService.getInstance().getListProducts())
			listModel.addElement(product.getName() + " Кол-во: " + 
					product.getNumber() + " Цена: " + 
					product.getPrice() + " бел.руб. ");
		m_listProducts = new JList(listModel);
		m_listProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_listProducts.setLayoutOrientation(JList.VERTICAL);
		
		m_listProductsScroll = new JScrollPane(m_listProducts);
		m_listProductsScroll.setPreferredSize(new Dimension(450, 105));
		
		m_descriptionButton = new JButton("Описание товара");
		m_descriptionButton.setPreferredSize(new Dimension(300, 50));
		m_descriptionButton.setEnabled(false);
		m_descriptionButton.setFont(m_fontButtons);
		
		m_changeStatusButton = new JButton("Изменить");
		m_changeStatusButton.setPreferredSize(new Dimension(140, 30));
		m_changeStatusButton.setEnabled(false);
		m_changeStatusButton.setFont(m_fontButtons);
		
		m_deleteButton = new JButton("Отменить заказ");
		m_deleteButton.setPreferredSize(new Dimension(300, 50));
		m_deleteButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_numberOrder);
		m_componentsPanel.add(m_userOrder);
		m_componentsPanel.add(m_dateOrder);
		m_componentsPanel.add(m_summOrder);
		m_componentsPanel.add(m_stateOrder);
		m_componentsPanel.add(m_listStatuses);
		m_componentsPanel.add(m_changeStatusButton);
		m_componentsPanel.add(m_listProductsScroll);
		m_componentsPanel.add(m_descriptionButton);
		m_componentsPanel.add(m_deleteButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_backButton.addActionListener(this);
		m_descriptionButton.addActionListener(this);
		m_deleteButton.addActionListener(this);
		m_changeStatusButton.addActionListener(this);
		m_listProducts.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (m_listProducts.getSelectedIndex() >= 0) {
                	m_descriptionButton.setEnabled(true);
                } else {
                	m_descriptionButton.setEnabled(false);
                }
            }
        });
		m_listStatuses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(m_listStatuses.getSelectedIndex() == CacheService.getInstance().getCurrentOrder().
						getState()) {
					m_changeStatusButton.setEnabled(false);
				} else {
					m_changeStatusButton.setEnabled(true);
				}
			}
        });
	}
	
	@Override
	public void updatePanel() {
		m_listStatuses.setSelectedIndex(CacheService.getInstance().getCurrentOrder().
				getState());
		m_changeStatusButton.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_descriptionButton) {
			CacheService.getInstance().setIdSelectedProduct(
					CacheService.getInstance().getListProducts().
					get(m_listProducts.getSelectedIndex()).getId());
			ControlOrderPanelController.getInstance().description();
		} else if(object == m_changeStatusButton) {
			ControlOrderPanelController.getInstance().changeStatus(this, 
					m_listStatuses.getSelectedIndex());
		} else if(object == m_deleteButton) {
			ControlOrderPanelController.getInstance().delete();
		} else if(object == m_backButton) {
			ControlOrderPanelController.getInstance().back();
		}
	}

}
