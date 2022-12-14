package org.source.views.statistics;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.Chart;
import org.knowm.xchart.ChartBuilder;
import org.knowm.xchart.StyleManager.ChartTheme;
import org.knowm.xchart.StyleManager.ChartType;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.source.service.CacheService;

public class DiagrammCreator implements ExampleChart {

	public void showGraphic() {
		Chart chart = getChart();
		JPanel panel = new XChartPanel(chart);
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	@Override
	public Chart getChart() {
		Chart chart = null;
		
		String months[] = new String[12];
		months[0] = "Январь";
		months[1] = "Февраль";
		months[2] = "Март";
		months[3] = "Апрель";
		months[4] = "Май";
		months[5] = "Июнь";
		months[6] = "Июль";
		months[7] = "Август";
		months[8] = "Сентабрь";
		months[9] = "Октябрь";
		months[10] = "Ноябрь";
		months[11] = "Декабрь";
	    
		Number yData[] = new Number[CacheService.getInstance().getStatisticOrders().size()];
	    int index = 0;
	    for(Number number : CacheService.getInstance().getStatisticOrders()) {
	    	yData[index] = number;
	    	++index;
	    }
	    
		if(CacheService.getInstance().getIsStatisticForYear()) {
		    String xTitle = "";
		    String titleGraphic = "Статистика за ";
		    xTitle = "Месяцы";
		    titleGraphic += CacheService.getInstance().getStatisticYear() + " год";
		    chart = new ChartBuilder().chartType(ChartType.Bar).width(800).
		    		height(600).title(titleGraphic).xAxisTitle(xTitle).
		    		yAxisTitle("Количество заказов").theme(ChartTheme.GGPlot2).build();
		    chart.addSeries("Диаграмма", new ArrayList<String>(Arrays.asList(months)), 
		    		new ArrayList<Number>(Arrays.asList(yData)));
		    chart.getStyleManager().setXAxisLabelRotation(15);
		} else {
			Number xData[] = new Number[CacheService.getInstance().getStatisticOrders().size()];
			for(int i = 0; i < CacheService.getInstance().getStatisticOrders().size(); ++i) {
				xData[i] = i + 1;
			}
		    String titleGraphic = "Статистика за " + months[CacheService.getInstance().
		                   getStatisticMonth() - 1] + " " + CacheService.getInstance().
		                   getStatisticYear() + " года";
		    chart = new ChartBuilder().chartType(ChartType.Bar).width(800).
		    		height(600).title(titleGraphic).xAxisTitle("Дни").
		    		yAxisTitle("Количество заказов").theme(ChartTheme.GGPlot2).build();
		    chart.addSeries("Диаграмма", new ArrayList<Number>(Arrays.asList(xData)), 
		    		new ArrayList<Number>(Arrays.asList(yData)));
		}
	    return chart;
	}

}
