package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.source.controllers.OrdersStatisticPanelController;
import org.source.views.base.BasePanel;

public class OrdersStatisticPanel extends BasePanel implements ActionListener, ItemListener {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JLabel m_yearLabel;
	private JLabel m_monthLabel;
	private JComboBox m_listYear;
	private JComboBox m_listMonth;
	private JRadioButton m_allYear;
	private JRadioButton m_monthOfYear;
	private JButton m_graphicButton;
	private JButton m_diagrammButton;
	private JButton m_backButton;
	
	public OrdersStatisticPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	
	private void initComponents() {
		m_topText = new JLabel("Статистика заказов", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 40));
		m_topText.setFont(m_fontButtons);
		
		m_yearLabel = new JLabel("За какой год нужно показать статистику: ", SwingConstants.CENTER);
		m_yearLabel.setPreferredSize(new Dimension(340, 30));
		m_yearLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_yearLabel.setFont(m_fontButtons);
		
		m_monthLabel = new JLabel("Выберите месяц: ", SwingConstants.CENTER);
		m_monthLabel.setPreferredSize(new Dimension(150, 30));
		m_monthLabel.setEnabled(false);
		m_monthLabel.setVerticalAlignment(SwingConstants.CENTER);
		m_monthLabel.setFont(m_fontButtons);
		
		m_allYear = new JRadioButton("За весь выбраный год, по месяцам");
		m_allYear.setPreferredSize(new Dimension(400, 30));
		m_allYear.setSelected(true);
		m_allYear.setFont(m_fontButtons);
		
		m_monthOfYear = new JRadioButton("Только за выбранный месяц, по дням");
		m_monthOfYear.setPreferredSize(new Dimension(400, 30));
		m_monthOfYear.setFont(m_fontButtons);
		
		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(m_allYear);
		radioGroup.add(m_monthOfYear);
		
		m_listYear = new JComboBox();
		m_listYear.setPreferredSize(new Dimension(80, 30));
		for(int year = 2015; year <= Calendar.getInstance().get(Calendar.YEAR); ++year) {
			m_listYear.addItem(String.valueOf(year));
		}
		m_listYear.setFont(m_fontButtons);
		
		m_listMonth = new JComboBox();
		m_listMonth.setEnabled(false);
		m_listMonth.setPreferredSize(new Dimension(130, 30));
		m_listMonth.addItem("Январь");
		m_listMonth.addItem("Февраль");
		m_listMonth.addItem("Март");
		m_listMonth.addItem("Апрель");
		m_listMonth.addItem("Май");
		m_listMonth.addItem("Июнь");
		m_listMonth.addItem("Июль");
		m_listMonth.addItem("Август");
		m_listMonth.addItem("Сентябрь");
		m_listMonth.addItem("Октябрь");
		m_listMonth.addItem("Ноябрь");
		m_listMonth.addItem("Декабрь");
		m_listMonth.setFont(m_fontButtons);
		
		m_graphicButton = new JButton("Построить график");
		m_graphicButton.setPreferredSize(new Dimension(300, 50));
		//m_graphicButton.setEnabled(false);
		m_graphicButton.setFont(m_fontButtons);
		
		m_diagrammButton = new JButton("Построить диаграмму");
		m_diagrammButton.setPreferredSize(new Dimension(300, 50));
		//m_diagrammButton.setEnabled(false);
		m_diagrammButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_yearLabel);
		m_componentsPanel.add(m_listYear);
		m_componentsPanel.add(m_allYear);
		m_componentsPanel.add(m_monthOfYear);
		m_componentsPanel.add(m_monthLabel);
		m_componentsPanel.add(m_listMonth);
		m_componentsPanel.add(m_graphicButton); 
		m_componentsPanel.add(m_diagrammButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_graphicButton.addActionListener(this);
		m_diagrammButton.addActionListener(this);
		m_backButton.addActionListener(this);
		m_monthOfYear.addItemListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_graphicButton) {
			OrdersStatisticPanelController.getInstance().showGraphic(m_allYear.isSelected(), 
					String.valueOf(m_listYear.getItemAt(m_listYear.getSelectedIndex())), 
							m_listMonth.getSelectedIndex() + 1);
		} else if(object == m_diagrammButton) {
			OrdersStatisticPanelController.getInstance().showDiagramm(m_allYear.isSelected(), 
					String.valueOf(m_listYear.getItemAt(m_listYear.getSelectedIndex())), 
							m_listMonth.getSelectedIndex() + 1);
		} else if(object == m_backButton) {
			OrdersStatisticPanelController.getInstance().back();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			m_monthLabel.setEnabled(true);
			m_listMonth.setEnabled(true);
	    }
	    else if (e.getStateChange() == ItemEvent.DESELECTED) {
	    	m_monthLabel.setEnabled(false);
	    	m_listMonth.setEnabled(false);
	    }
	}
	
}
