/**
 * 
 */
package in.anandm.android.apps.notificationsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.util.JsonReader;
import android.util.Log;

/**
 * @author anandm
 * 
 */
public final class MusicStore {
	public static final String TAG = "MusicStore";
	private static final String DATA_URL = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";

	private MusicStore() {
		super();

	}

	public static final List<Track> loadTracks() {
		BufferedReader reader = null;
		JsonReader jsonReader = null;
		InputStream inputStream = null;
		try {
			inputStream = new URL(DATA_URL).openStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			jsonReader = new JsonReader(reader);
			jsonReader.beginObject();
			if(jsonReader.hasNext()){
				Log.d(TAG, jsonReader.nextName());

			}

		} catch (MalformedURLException e) {

			Log.e(TAG, "failed to load data from remote : " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, "failed to load data from remote : " + e.getMessage());
		} finally {
			if (jsonReader != null) {
				try {
					jsonReader.close();
				} catch (IOException e) {
					Log.e(TAG,
							"failed to load data from remote : "
									+ e.getMessage());
				}
			}
		}

		return null;
	}
}
