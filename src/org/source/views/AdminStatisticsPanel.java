package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import org.source.controllers.AdminStatisticsPanelController;
import org.source.controllers.AllHistoryOrdersPanelController;
import org.source.models.Order;
import org.source.models.Product;
import org.source.models.User;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class AdminStatisticsPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JLabel m_numberItemLabel;
	private JTextField m_numberItemField;
	private JLabel m_beginLabel;
	private JDatePanelImpl m_beginDatePanel;
	private JDatePickerImpl m_beginDate;
	private JLabel m_endLabel;
	private JDatePanelImpl m_endDatePanel;
	private JDatePickerImpl m_endDate;
	private JScrollPane m_listOrdersScroll;
	private JButton m_proceedButton;
	private JButton m_backButton;
	private JList m_listStatistic;
	private ButtonGroup m_statiscticsGroup;
	private JRadioButton m_statisticProducts;
	private JRadioButton m_statisticOrders;
	private JRadioButton m_statisticUsers;
	
	public AdminStatisticsPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		
		m_topText = new JLabel("Статистика", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 40));
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
		
		m_statisticProducts = new JRadioButton("Определить самые популярные товары");
		m_statisticProducts.setPreferredSize(new Dimension(400, 30));
		m_statisticProducts.setSelected(true);
		m_statisticProducts.setFont(m_fontButtons);
		
		m_statisticOrders = new JRadioButton("Определить самые крупные заказы");
		m_statisticOrders.setPreferredSize(new Dimension(400, 30));
		m_statisticOrders.setFont(m_fontButtons);
		
		m_statisticUsers = new JRadioButton("Определить самых активных пользователей");
		m_statisticUsers.setPreferredSize(new Dimension(400, 30));
		m_statisticUsers.setFont(m_fontButtons);
		
		m_statiscticsGroup = new ButtonGroup();
		m_statiscticsGroup.add(m_statisticProducts);
		m_statiscticsGroup.add(m_statisticOrders);
		m_statiscticsGroup.add(m_statisticUsers);
		
		m_numberItemLabel = new JLabel("Количество: ");
		m_numberItemLabel.setPreferredSize(new Dimension(130, 30));
		m_numberItemLabel.setFont(m_fontButtons);
		
		m_numberItemField = new JTextField(10);
		m_numberItemField.setPreferredSize(new Dimension(200, 30));
		
		m_listStatistic = new JList();
		m_listStatistic.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_listStatistic.setLayoutOrientation(JList.VERTICAL);
		
		m_listOrdersScroll = new JScrollPane(m_listStatistic);
		m_listOrdersScroll.setPreferredSize(new Dimension(450, 130));
		
		m_proceedButton = new JButton("Посчитать");
		m_proceedButton.setPreferredSize(new Dimension(300, 50));
		m_proceedButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_statisticProducts);
		m_componentsPanel.add(m_statisticOrders);
		m_componentsPanel.add(m_statisticUsers);
		m_componentsPanel.add(m_beginLabel);
		m_componentsPanel.add(m_beginDate);
		m_componentsPanel.add(m_endLabel);
		m_componentsPanel.add(m_endDate);
		m_componentsPanel.add(m_numberItemLabel);
		m_componentsPanel.add(m_numberItemField);
		m_componentsPanel.add(m_listOrdersScroll);
		m_componentsPanel.add(m_proceedButton);
		m_componentsPanel.add(m_backButton);
	}
	
	private void addEvents() {
		m_proceedButton.addActionListener(this);
		m_backButton.addActionListener(this);
	}
	
	@Override 
	public void updatePanel() {
		DefaultListModel listModel = new DefaultListModel();
		if(m_statisticProducts.isSelected()) {
			for(Product product : CacheService.getInstance().getListProducts()) {
				listModel.addElement(" " + product.getName() + "; Кол-во: " + 
						product.getNumber() + "; Сумма: " + product.getPrice());
			}
			m_listStatistic.setModel(listModel);
		} else if(m_statisticOrders.isSelected()) {
			for(Order order : CacheService.getInstance().getListOrders()) {
				listModel.addElement(" Заказ: №" + order.getId() + "; Дата: " 
						+ order.getStringDate() + "; Статус: " + order.getStateString() 
						+ "; Сумма: " + order.getPrice());
			}
			m_listStatistic.setModel(listModel);
		} else if(m_statisticUsers.isSelected()) {
			for(User user : CacheService.getInstance().getListUsers()) {
				listModel.addElement(" Логин: " + user.getLogin() + "; Кол-во заказов: " + 
						user.getNumberOrders() + "; Сумма всех: " + user.getSummOrders() 
						+ " бел. руб.");
			}
			m_listStatistic.setModel(listModel);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_proceedButton) {
			CacheService.getInstance().setSearchString(
					m_numberItemField.getText());
			if(m_statisticProducts.isSelected()) {
				AdminStatisticsPanelController.getInstance().statisticProducts
				(this, (Date)m_beginDate.getModel().getValue(), 
						(Date)m_endDate.getModel().getValue());
			} else if(m_statisticOrders.isSelected()) {
				AdminStatisticsPanelController.getInstance().statisticOrders
				(this, (Date)m_beginDate.getModel().getValue(), 
						(Date)m_endDate.getModel().getValue());
			} else if(m_statisticUsers.isSelected()) {
				AdminStatisticsPanelController.getInstance().statisticUsers
				(this, (Date)m_beginDate.getModel().getValue(), 
						(Date)m_endDate.getModel().getValue());
			}
		} else if(object == m_backButton) {
			AllHistoryOrdersPanelController.getInstance().back();
		}
	}

}
