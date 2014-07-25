package in.anandm.chartsample;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

public class BarChartActivity extends Activity {

	private GraphicalView mChart;
	private static final XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private static final String[] CATEGORIES = new String[] { "AAP", "BJP",
			"CONGRESS", "OTHERS" };
	private static final int[] VALUES = new int[] { 2, 31, 12, 55 };

	static {
		for (int i = 0; i < CATEGORIES.length; i++) {
			mDataset.addSeries(new XYSeries(CATEGORIES[i], VALUES[i]));
		}
	}
	private static final int[] colors = new int[] { Color.GREEN, Color.RED,
			Color.BLUE, Color.YELLOW };
	private static final XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	static {
		mRenderer.setLabelsTextSize(15);
		mRenderer.setLegendTextSize(15);
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
		for (int color : colors) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(color);
			mRenderer.addSeriesRenderer(r);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_chart);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LinearLayout layout = (LinearLayout) findViewById(R.id.barChart);
		if (mChart == null) {
			mChart = ChartFactory.getBarChartView(this, mDataset, mRenderer,
					BarChart.Type.DEFAULT);
			layout.addView(mChart);
		} else {
			mChart.repaint();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bar_chart, menu);
		return true;
	}

}
