package org.source;

import java.awt.Dimension;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.source.views.base.BasePanel;

public class ApplicationUI extends JFrame {

    private static final long serialVersionUID = 1L;

    final public static int WIDTH_WINDOW = 500;
    final public static int HEIGHT_WINDOW = 500;

    private static ApplicationUI instance = new ApplicationUI();

    private Stack<BasePanel> m_navigationPane;

    public static ApplicationUI getInstance() {
        return instance;
    }

    private ApplicationUI() {
        super();
        m_navigationPane = new Stack<BasePanel>();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(WIDTH_WINDOW, HEIGHT_WINDOW));
        setTitle("ПС учета и регистрации поступления товаров на склад логистической компании");
    }
    public void push(BasePanel panel) {
        if(!m_navigationPane.isEmpty())
            m_navigationPane.peek().setVisible(false);
        m_navigationPane.push(panel);
        m_navigationPane.peek().setVisible(true);
        this.add(panel.getView());
    }
    public void pop() {
        if(!m_navigationPane.isEmpty()) {
            m_navigationPane.peek().setVisible(false);
            m_navigationPane.pop();
            if(!m_navigationPane.isEmpty()) {
                m_navigationPane.peek().updatePanel();
                m_navigationPane.peek().setVisible(true);
            }
        }
    }
}
