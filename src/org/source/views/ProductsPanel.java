package org.source.views;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

import org.source.controllers.ProductPanelController;
import org.source.models.Product;
import org.source.service.CacheService;
import org.source.views.base.BasePanel;

public class ProductsPanel extends BasePanel {

	private JLabel m_topText;
	private JPanel m_componentsPanel;
	private JLabel m_sortLabel;
	private JComboBox m_sortListItem;
	private JTextField m_searchField;
	private JButton m_searchButton;
	private JScrollPane m_listProductsScroll;
	private JButton m_saveButton;
	private JButton m_descriptionButton;
	private JButton m_backButton;
	private JList m_listProducts;
	private JCheckBox m_typeSort;
	
	public ProductsPanel() {
		initComponents();
		buildLayout();
		addEvents();
	}
	private void initComponents() {
		
		m_topText = new JLabel("Список товаров", SwingConstants.CENTER);
		m_topText.setPreferredSize(new Dimension(400, 30));
		m_topText.setFont(m_fontButtons);
		
		m_sortLabel = new JLabel("Сортировать по ", SwingConstants.RIGHT);
		m_sortLabel.setPreferredSize(new Dimension(150, 30));
		m_sortLabel.setFont(m_fontButtons);
		
		m_sortListItem = new JComboBox();
		m_sortListItem.addItem("");
		m_sortListItem.addItem("Наименование");
		m_sortListItem.addItem("Цена");
		m_sortListItem.setPreferredSize(new Dimension(120, 25));
		
		m_typeSort = new JCheckBox("По убыванию");
		m_typeSort.setFont(m_fontButtons);
		
		m_searchField = new JTextField(27);
		
		m_searchButton = new JButton("Поиск");
		m_searchButton.setPreferredSize(new Dimension(90, 20));
		m_searchButton.setFont(m_fontButtons);
		
		m_listProducts = new JList();
		m_listProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m_listProducts.setLayoutOrientation(JList.VERTICAL);
		
		updatePanel();
		
		m_listProductsScroll = new JScrollPane(m_listProducts);
		m_listProductsScroll.setPreferredSize(new Dimension(450, 195));
		
		m_saveButton = new JButton("Сохранить список в файл");
		m_saveButton.setPreferredSize(new Dimension(300, 50));
		m_saveButton.setFont(m_fontButtons);
		
		m_descriptionButton = new JButton("Описание товара");
		m_descriptionButton.setPreferredSize(new Dimension(300, 50));
		m_descriptionButton.setEnabled(false);
		m_descriptionButton.setFont(m_fontButtons);
		
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
		m_componentsPanel.add(m_sortLabel);
		m_componentsPanel.add(m_sortListItem);
		m_componentsPanel.add(m_typeSort);
		m_componentsPanel.add(m_searchField);
		m_componentsPanel.add(m_searchButton);
		m_componentsPanel.add(m_listProductsScroll);
		m_componentsPanel.add(m_descriptionButton);
		m_componentsPanel.add(m_saveButton);
		m_componentsPanel.add(m_backButton);
	}
	private void addEvents() {
		m_searchButton.addActionListener(this);
		m_descriptionButton.addActionListener(this);
		m_saveButton.addActionListener(this);
		m_backButton.addActionListener(this);
		m_listProducts.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (m_listProducts.getSelectedIndex() >= 0) {
                	m_descriptionButton.setEnabled(true);
                } else {
                	m_descriptionButton.setEnabled(false);
                }
            }
        });
	}
	
	@Override
	public void updatePanel() {
		DefaultListModel listModel = new DefaultListModel();
		for(Product product : CacheService.getInstance().getListProducts())
			listModel.addElement(product.getName() + " Цена: " + 
					product.getPrice() + " бел.руб. ");
		m_listProducts.setModel(listModel);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();
		if(object == m_searchButton) {
			if(m_sortListItem.getSelectedIndex() == 1) 
				CacheService.getInstance().setSortColumn("name");
			else if(m_sortListItem.getSelectedIndex() == 2)
				CacheService.getInstance().setSortColumn("price");
			else 
				CacheService.getInstance().setSortColumn("");
			if(m_typeSort.isSelected()) 
				CacheService.getInstance().setTypeSort("DESC");
			else CacheService.getInstance().setTypeSort("");
			CacheService.getInstance().setSearchString(m_searchField.getText());
			ProductPanelController.getInstance().search(this);
		} else if(object == m_saveButton) {
			ProductPanelController.getInstance().save();
		} else if(object == m_descriptionButton) {
			CacheService.getInstance().setIdSelectedProduct(
					CacheService.getInstance().getListProducts().
					get(m_listProducts.getSelectedIndex()).getId());
			ProductPanelController.getInstance().description();
		} else if(object == m_backButton) {
			ProductPanelController.getInstance().back();
		}
	}
}
