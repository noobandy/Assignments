package in.anandm.chartsample;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

public class PieChartActivity extends Activity {

	private GraphicalView mChart;
	private static final CategorySeries mDataset = new CategorySeries("Political Party");
	private static final String[] CATEGORIES = new String[]{"AAP","BJP","CONGRESS","OTHERS"};
	private static final double[] VALUES = new double[]{2,31,12,55};
	static{
		for(int i = 0; i < CATEGORIES.length; i++){
			mDataset.add(CATEGORIES[i], VALUES[i]);
		}
	}
	private static final  int[] colors = new int[] { Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW};
	private static final DefaultRenderer mRenderer = new DefaultRenderer();
    static{
    	mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        for (int color : colors) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(color);
          mRenderer.addSeriesRenderer(r);
        }
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		  LinearLayout layout = (LinearLayout) findViewById(R.id.pieChart);
	        if (mChart == null) {
	            mChart = ChartFactory.getPieChartView(this, mDataset, mRenderer);
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
