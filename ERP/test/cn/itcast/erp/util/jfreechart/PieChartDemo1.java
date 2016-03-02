package cn.itcast.erp.util.jfreechart;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
//500, 370
public class PieChartDemo1{
	public static void main(String[] paramArrayOfString) throws IOException {
		JFreeChart jfc = createChart();
		//jfc->图片
		BufferedImage bi = jfc.createBufferedImage(500, 370);
		ImageIO.write(bi, "PNG", new File("1.PNG"));
	}
	
	private static JFreeChart createChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("笔记本", new Double(100D));
		dataset.setValue("bbb", new Double(111D));
		dataset.setValue("ccc", new Double(123D));
		dataset.setValue("ddd", new Double(135D));

		JFreeChart jfc = ChartFactory.createPieChart("采购报表", dataset, true, false, false);
		
		PiePlot plot = (PiePlot) jfc.getPlot();
		plot.setLabelFont(new Font("SansSerif", 0, 12));
		plot.setNoDataMessage("对不起，没有对应报表数据！");
		plot.setCircular(true);
		plot.setLabelGap(0.02D);
		return jfc;
	}
	
}