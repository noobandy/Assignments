package in.anandm.android.apps.listviewsample;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {

	private Context context;

	public MyArrayAdapter(Context context,String[] values) {
		super(context, R.layout.list_row, values);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = ((Activity) context).getLayoutInflater().inflate(
					R.layout.list_row, parent);
			holder.personNameText = (TextView) convertView
					.findViewById(R.id.person_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.personNameText.setText(getItem(position));
		holder.personNameText.setTag(getItemId(position));

		return convertView;
	}

	private class ViewHolder {
		TextView personNameText;
	}

}
