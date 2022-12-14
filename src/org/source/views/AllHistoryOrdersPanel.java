package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Date;

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

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import org.source.controllers.AllHistoryOrdersPanelController;
import org.source.models.Order;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class AllHistoryOrdersPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JTextField m_searchField;
	private JButton m_searchButton;
	private JLabel m_beginLabel;
	private JDatePanelImpl m_beginDatePanel;
	private JDatePickerImpl m_beginDate;
	private JLabel m_endLabel;
	private JDatePanelImpl m_endDatePanel;
	private JDatePickerImpl m_endDate;
	private JScrollPane m_listOrdersScroll;
	private JButton m_statisticButton;
	private JButton m_descriptionButton;
	private JButton m_saveButton;
	private JButton m_backButton;
	private JList m_listOrders;
	
	public AllHistoryOrdersPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("История всех заказов на поставку", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 40));
		//m_topText.setVerticalAlignment(SwingConstants.TOP);
		m_topText.setFont(m_fontButtons);
		
		m_beginLabel = new JLabel("В период с ", SwingConstants.CENTER);
		m_beginLabel.setPreferredSize(new Dimension(110, 20));
		m_beginLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_beginLabel.setFont(m_fontButtons);
		
		m_endLabel = new JLabel(" по ", SwingConstants.CENTER);
		m_endLabel.setPreferredSize(new Dimension(40, 20));
		m_endLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_endLabel.setFont(m_fontButtons);
		
		UtilDateModel beginDateModel = new UtilDateModel();
		UtilDateModel endDateModel = new UtilDateModel();
		m_beginDatePanel = new JDatePanelImpl(beginDateModel);
		m_endDatePanel = new JDatePanelImpl(endDateModel);
		
		m_beginDate = new JDatePickerImpl(m_beginDatePanel);
		m_beginDate.setPreferredSize(new Dimension(120, 30));

		m_endDate = new JDatePickerImpl(m_endDatePanel);
		m_endDate.setPreferredSize(new Dimension(120, 30));
		
		m_searchField = new JTextField(27);
		m_searchField.setPreferredSize(new Dimension(200, 25));
		
		m_searchButton = new JButton("Поиск");
		m_searchButton.setPreferredSize(new Dimension(90, 25));
		m_searchButton.setFont(m_fontButtons);
		
		m_listOrders = new JList();
		m_listOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_listOrders.setLayoutOrientation(JList.VERTICAL);
		updatePanel();
		
		m_listOrdersScroll = new JScrollPane(m_listOrders);
		m_listOrdersScroll.setPreferredSize(new Dimension(450, 175));
		
		if(CacheService.getInstance().getIsAllOrders()) {
			m_statisticButton = new JButton("Статистика заказов");
			m_statisticButton.setPreferredSize(new Dimension(300, 50));
			m_statisticButton.setFont(m_fontButtons);
			
			m_listOrdersScroll.setPreferredSize(new Dimension(450, 115));
		}
		
		m_descriptionButton = new JButton("Информация о заказе");
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
		m_componentsPanel.add(m_beginLabel);
		m_componentsPanel.add(m_beginDate);
		m_componentsPanel.add(m_endLabel);
		m_componentsPanel.add(m_endDate);
		m_componentsPanel.add(m_searchField);
		m_componentsPanel.add(m_searchButton);
		m_componentsPanel.add(m_listOrdersScroll);
		if(CacheService.getInstance().getIsAllOrders()) 
			m_componentsPanel.add(m_statisticButton);
		m_componentsPanel.add(m_descriptionButton);
		m_componentsPanel.add(m_saveButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_searchButton.addActionListener(this);
		if(CacheService.getInstance().getIsAllOrders()) 
			m_statisticButton.addActionListener(this);
		m_descriptionButton.addActionListener(this);
		m_saveButton.addActionListener(this);
		m_backButton.addActionListener(this);
		m_listOrders.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (m_listOrders.getSelectedIndex() >= 0) {
                	m_descriptionButton.setEnabled(true);
                } else {
                	m_descriptionButton.setEnabled(false);
                }
            }
        });
	}
	
	@Override
	public void updatePanel() {
		m_listOrders.clearSelection();
		DefaultListModel listModel = new DefaultListModel();
		String stateOrder = "";
		for(Order order : CacheService.getInstance().getListOrders()) {
			if(order.getState() == Order.ORDER_IS_PREPARING) 
				stateOrder = "В ожидании";
			else if(order.getState() == Order.ORDER_IS_SENT)
				stateOrder = "Отправлен";
			else if(order.getState() == Order.ORDER_IS_COMPLETED)
				stateOrder = "Выполнен";
			else stateOrder = "";
			listModel.addElement(" Заказ № " + order.getId() + " Дата заказа: " + 
					order.getStringDate() + " Статус: " + stateOrder);
		}
		m_listOrders.setModel(listModel);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_searchButton) {
			CacheService.getInstance().setSearchString(m_searchField.getText());
			AllHistoryOrdersPanelController.getInstance().search(this, 
					(Date)m_beginDate.getModel().getValue(), 
					(Date)m_endDate.getModel().getValue());
		} else if(object == m_descriptionButton) {
			CacheService.getInstance().setIdSelectedOrder(
					CacheService.getInstance().getListOrders().
					get(m_listOrders.getSelectedIndex()).getId());
			AllHistoryOrdersPanelController.getInstance().description();
		} else if(object == m_saveButton) {
			AllHistoryOrdersPanelController.getInstance().save();
		} else if(object == m_backButton) {
			AllHistoryOrdersPanelController.getInstance().back();
		} else if(object == m_statisticButton) {
			AllHistoryOrdersPanelController.getInstance().statistics();
		}
	}

}
