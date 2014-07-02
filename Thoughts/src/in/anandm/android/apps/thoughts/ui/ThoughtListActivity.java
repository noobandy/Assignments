package in.anandm.android.apps.thoughts.ui;

import in.anandm.android.apps.thoughts.provider.UserThoughtsContract;
import in.anandm.thoughts.R;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleCursorAdapter;

public class ThoughtListActivity extends ListActivity implements
		LoaderCallbacks<Cursor>, OnQueryTextListener {
	private SimpleCursorAdapter adapter;
	private String mCurFilter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thought_list);
		fillData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thought_list, menu);

		getActionBar().setDisplayHomeAsUpEnabled(false);

		SearchView searchView = (SearchView) menu.findItem(
				R.id.menu_search_view).getActionView();

		searchView.setOnQueryTextListener(this);
		searchView.setIconifiedByDefault(true); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			Intent addThoughtIntent = new Intent(this, ThoughtActivity.class);
			startActivity(addThoughtIntent);
			break;
		case R.id.menu_refresh:
			getLoaderManager().restartLoader(0, null, this);
			break;
		case R.id.action_settings:
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			startActivity(settingsIntent);
			break;	

		default:
			break;
		}

		return true;
	}

	// Opens the second activity if a feedback is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent i = new Intent(this, ThoughtActivity.class);

		i.putExtra("thoughtId", id);

		startActivity(i);
	}

	private void fillData() {

		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] {
				UserThoughtsContract.Thought.COLUMN_THOUGHT,
				UserThoughtsContract.Thought.COLUMN_AUTHOR };
		// Fields on the UI to which we map
		int[] to = new int[] {R.id.textViewThoughtSummary, R.id.textViewThoughtAuthor};

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this,
				R.layout.thought_list_row, null, from, to, 0);
		setListAdapter(adapter);
	}

	// loader manager contract
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { UserThoughtsContract.Thought.COLUMN_ID,
				UserThoughtsContract.Thought.COLUMN_THOUGHT,
				UserThoughtsContract.Thought.COLUMN_AUTHOR };
		CursorLoader cursorLoader = new CursorLoader(this,
				UserThoughtsContract.Thought.CONTENT_URI, projection, mCurFilter,
				null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mCurFilter = UserThoughtsContract.Thought.COLUMN_THOUGHT + " like '%"
				+ newText + "%'";
		getLoaderManager().restartLoader(0, null, this);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return true;
	}

}
