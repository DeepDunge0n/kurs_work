package org.source.views.dialogs;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.source.ApplicationUI;

public class DiscriptionProductDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel m_componentsPanel;
	private JPanel m_panelDialog;
	private JLabel m_nameLabel;
	private JLabel m_priceLabel;
	private JLabel m_discriptionLabel;
	private JLabel m_inStockLabel;
	private JButton m_closeButton;
	
	public DiscriptionProductDialog(String name, int price, 
			String description, boolean inStock) {
		super(ApplicationUI.getInstance(), "Описание товара", true);
		if (ApplicationUI.getInstance() != null) {
		      Dimension parentSize = ApplicationUI.getInstance().getSize(); 
		      Point p = ApplicationUI.getInstance().getLocation(); 
		      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}
		m_componentsPanel = new JPanel();
		m_panelDialog = new JPanel();
		getContentPane().add(m_panelDialog);
		
		m_nameLabel = new JLabel("<html>Наименование: " + name + "<html>");
		m_nameLabel.setVerticalAlignment(SwingConstants.TOP);
		m_nameLabel.setPreferredSize(new Dimension(450, 50));
		m_nameLabel.setFont(new Font("Times Roman", Font.PLAIN, 14));
		
		m_discriptionLabel = new JLabel("<html>Описание: " + description + "<html>");
		m_priceLabel = new JLabel("Цена: " + price + "бел. руб.");
		m_inStockLabel = new JLabel(inStock ? "Есть в наличии" : "Нету в наличии");
		m_closeButton = new JButton("Закрыть");
		
		setMaximumSize(new Dimension(400, 400));
		setMinimumSize(new Dimension(100, 100));
		
		buildLayout();
		addEvents();
		//pack();
		setVisible(true);
	}
	
	private void buildLayout() {
		m_panelDialog.setLayout(new BoxLayout(m_panelDialog, BoxLayout.Y_AXIS));
		m_panelDialog.add(m_componentsPanel);
		m_componentsPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		m_componentsPanel.add(m_nameLabel);
		m_componentsPanel.add(m_discriptionLabel);
		m_componentsPanel.add(m_priceLabel);
		m_componentsPanel.add(m_inStockLabel);
		m_componentsPanel.add(m_closeButton);
	}
	
	private void addEvents() {
		m_closeButton.addActionListener(this);
		addComponentListener(new ComponentAdapter()
		{
		    public void componentResized(ComponentEvent event)
		    {
		        setSize(400, 400);
		    }
		});
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == m_closeButton) {
			setVisible(false); 
			this.dispose();
		}
	}
	
}
