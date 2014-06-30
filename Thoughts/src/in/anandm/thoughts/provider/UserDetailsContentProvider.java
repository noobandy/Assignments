/**
 * 
 */
package in.anandm.thoughts.provider;

import in.anandm.thoughts.util.SelectionBuilder;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * @author anandm
 *
 */
public class UserDetailsContentProvider extends ContentProvider {

	private static final int ROUTE_SCHEME = 1;
	private static final int ROUTE_SCHEME_ID = 2;
	private static final int ROUTE_FEEDBACK = 3;
	private static final int ROUTE_FEEDBACK_ID = 4;

	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	static{
		URI_MATCHER.addURI(UserDetailsContract.CONTENT_AUTHORITY, "schemes", ROUTE_SCHEME);
		URI_MATCHER.addURI(UserDetailsContract.CONTENT_AUTHORITY, "schemes/#", ROUTE_SCHEME_ID);
		URI_MATCHER.addURI(UserDetailsContract.CONTENT_AUTHORITY, "feedbacks", ROUTE_FEEDBACK);
		URI_MATCHER.addURI(UserDetailsContract.CONTENT_AUTHORITY, "feedbacks/#", ROUTE_FEEDBACK_ID);
	}


	private SchemeFeedbackDatabase databaseHelper;

	@Override
	public boolean onCreate() {
		databaseHelper = new SchemeFeedbackDatabase(getContext());
		return true;
	}



	@Override
	public String getType(Uri uri) {
		final int match = URI_MATCHER.match(uri);
		switch (match) {
		case ROUTE_SCHEME:
			return UserDetailsContract.Scheme.CONTENT_TYPE;

		case ROUTE_SCHEME_ID:

			return UserDetailsContract.Scheme.CONTENT_ITEM_TYPE;
		case ROUTE_FEEDBACK:

			return UserDetailsContract.UserDetails.CONTENT_TYPE;
		case ROUTE_FEEDBACK_ID:

			return UserDetailsContract.UserDetails.CONTENT_ITEM_TYPE;

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}




	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		final int match = URI_MATCHER.match(uri);

		SelectionBuilder builder = new SelectionBuilder();
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = null;
		Context ctx = null;

		switch (match) {
		case ROUTE_SCHEME_ID:
			// Return a single scheme, by ID.
			String schemeId = uri.getLastPathSegment();
			builder.where(UserDetailsContract.Scheme.COLUMN_ID + "=?", schemeId);

		case ROUTE_SCHEME:
			// Return all known schemes.
			builder.table(UserDetailsContract.Scheme.TABLE)
			.where(selection, selectionArgs);
			c = builder.query(db, projection, sortOrder);
			// Note: Notification URI must be manually set here for loaders to correctly
			// register ContentObservers.
			ctx = getContext();
			assert ctx != null;
			c.setNotificationUri(ctx.getContentResolver(), uri);
			return c;

		case ROUTE_FEEDBACK_ID:
			// Return a single feedback, by ID.
			String feedbackId = uri.getLastPathSegment();
			builder.where(UserDetailsContract.UserDetails.COLUMN_ID + "=?", feedbackId);
		case ROUTE_FEEDBACK:

			// Return all known feedbacks.
			builder.table(UserDetailsContract.UserDetails.TABLE)
			.where(selection, selectionArgs);
			c = builder.query(db, projection, sortOrder);
			// Note: Notification URI must be manually set here for loaders to correctly
			// register ContentObservers.
			ctx = getContext();
			assert ctx != null;
			c.setNotificationUri(ctx.getContentResolver(), uri);
			return c;

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}



	@Override
	public Uri insert(Uri uri, ContentValues values) {

		final int match = URI_MATCHER.match(uri);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Uri result;
		long id;
		Context ctx;
		switch (match) {
		case ROUTE_SCHEME:
			id = db.insertOrThrow(UserDetailsContract.Scheme.TABLE, null, values);
			result = Uri.parse(UserDetailsContract.Scheme.CONTENT_URI + "/" + id);
			ctx = getContext();
			assert ctx != null;
			ctx.getContentResolver().notifyChange(uri, null, false);
			return result;

		case ROUTE_SCHEME_ID:
			throw new UnsupportedOperationException("Unknown uri: " + uri);

		case ROUTE_FEEDBACK:

			id = db.insertOrThrow(UserDetailsContract.UserDetails.TABLE, null, values);
			result = Uri.parse(UserDetailsContract.UserDetails.CONTENT_URI + "/" + id);
			ctx = getContext();
			assert ctx != null;
			ctx.getContentResolver().notifyChange(uri, null, false);
			return result;

		case ROUTE_FEEDBACK_ID:
			throw new UnsupportedOperationException("Unknown uri: " + uri);


		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}



	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SelectionBuilder builder = new SelectionBuilder();
		final SQLiteDatabase db = databaseHelper.getWritableDatabase();
		final int match = URI_MATCHER.match(uri);
		int count;
		String id = null;
		switch (match) {
		case ROUTE_SCHEME:
			count = builder.table(UserDetailsContract.Scheme.TABLE)
			.where(selection, selectionArgs)
			.update(db, values);
			break;

		case ROUTE_SCHEME_ID:
			id = uri.getLastPathSegment();
			count = builder.table(UserDetailsContract.Scheme.TABLE)
					.where(UserDetailsContract.Scheme.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs)
					.update(db, values);
			break;
		case ROUTE_FEEDBACK:

			count = builder.table(UserDetailsContract.UserDetails.TABLE)
			.where(selection, selectionArgs)
			.update(db, values);
			break;
		case ROUTE_FEEDBACK_ID:

			id = uri.getLastPathSegment();
			count = builder.table(UserDetailsContract.UserDetails.TABLE)
					.where(UserDetailsContract.UserDetails.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs)
					.update(db, values);
			break;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		return count;
	}


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SelectionBuilder builder = new SelectionBuilder();
		final SQLiteDatabase db = databaseHelper.getWritableDatabase();
		final int match = URI_MATCHER.match(uri);
		int count;

		String id = null;
		switch (match) {
		case ROUTE_SCHEME:
			count = builder.table(UserDetailsContract.Scheme.TABLE)
			.where(selection, selectionArgs)
			.delete(db);
			break;
		case ROUTE_SCHEME_ID:

			id = uri.getLastPathSegment();
			count = builder.table(UserDetailsContract.Scheme.TABLE)
					.where(UserDetailsContract.Scheme.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs)
					.delete(db);
			break;
		case ROUTE_FEEDBACK:
			count = builder.table(UserDetailsContract.UserDetails.TABLE)
			.where(selection, selectionArgs)
			.delete(db);
			break;
		case ROUTE_FEEDBACK_ID:

			id = uri.getLastPathSegment();
			count = builder.table(UserDetailsContract.UserDetails.TABLE)
					.where(UserDetailsContract.UserDetails.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs)
					.delete(db);
			break;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		// Send broadcast to registered ContentObservers, to refresh UI.
		Context ctx = getContext();
		assert ctx != null;
		ctx.getContentResolver().notifyChange(uri, null, false);
		return count;
	}

	static class SchemeFeedbackDatabase extends SQLiteOpenHelper{
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_NAME = "scheme_feedback";


		public static final String CREATE_SCHEME_TABLE = "CREATE TABLE "
				+  UserDetailsContract.Scheme.TABLE + " (" + UserDetailsContract.Scheme.COLUMN_ID + " INTEGER PRIMARY KEY , "
				+ UserDetailsContract.Scheme.COLUMN_SCHEME_NAME + " TEXT, " + UserDetailsContract.Scheme.COLUMN_MODIFIED_BY
				+ " TEXT, " +UserDetailsContract.Scheme. COLUMN_LAST_MODIFIED + " INTEGER );";


		private static final String CREATE_FEEDBACK_TABLE = "CREATE TABLE "
				+ UserDetailsContract.UserDetails.TABLE + " (" + UserDetailsContract.UserDetails.COLUMN_ID + " INTEGER PRIMARY KEY , "
				+ UserDetailsContract.UserDetails.COLUMN_FIRST_NAME + " TEXT, " + UserDetailsContract.UserDetails.COLUMN_LAST_NAME
				+ " TEXT, " + UserDetailsContract.UserDetails.COLUMN_EMAIL + " TEXT, " + UserDetailsContract.UserDetails.COLUMN_SEX
				+ " TEXT, " + UserDetailsContract.UserDetails.COLUMN_PASSWORD + " INTEGER );";


		private SchemeFeedbackDatabase(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_SCHEME_TABLE);
			db.execSQL(CREATE_FEEDBACK_TABLE);
			populateSchemes(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

		private void populateSchemes(SQLiteDatabase db) {
			String schemeName = "Scheme ";
			for (int i = 0; i < 10; i++) {
				ContentValues values = new ContentValues();
				values.put(UserDetailsContract.Scheme.COLUMN_SCHEME_NAME, schemeName + i);
				values.put(UserDetailsContract.Scheme.COLUMN_LAST_MODIFIED, System.currentTimeMillis());
				values.put(UserDetailsContract.Scheme.COLUMN_MODIFIED_BY, "admin");
				db.insert(UserDetailsContract.Scheme.TABLE, UserDetailsContract.Scheme.COLUMN_ID, values);
			}
		}
	}
}
