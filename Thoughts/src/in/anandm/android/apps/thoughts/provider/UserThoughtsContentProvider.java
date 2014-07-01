/**
 * 
 */
package in.anandm.android.apps.thoughts.provider;

import in.anandm.android.apps.thoughts.util.SelectionBuilder;
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
public class UserThoughtsContentProvider extends ContentProvider {

	private static final int ROUTE_USER = 1;
	private static final int ROUTE_USER_ID = 2;
	private static final int ROUTE_THOUGHT = 3;
	private static final int ROUTE_THOUGHT_ID = 4;

	private static final UriMatcher URI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		URI_MATCHER.addURI(UserThoughtsContract.CONTENT_AUTHORITY, "users",
				ROUTE_USER);
		URI_MATCHER.addURI(UserThoughtsContract.CONTENT_AUTHORITY,"users/#",
				ROUTE_USER_ID);
		URI_MATCHER.addURI(UserThoughtsContract.CONTENT_AUTHORITY, "thoughts",
				ROUTE_THOUGHT);
		URI_MATCHER.addURI(UserThoughtsContract.CONTENT_AUTHORITY, "thoughts/#",
				ROUTE_THOUGHT_ID);
	}

	private UserThoughtsDatabase databaseHelper;

	@Override
	public boolean onCreate() {
		databaseHelper = new UserThoughtsDatabase(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		final int match = URI_MATCHER.match(uri);
		switch (match) {
		case ROUTE_USER:
			return UserThoughtsContract.User.CONTENT_TYPE;

		case ROUTE_USER_ID:

			return UserThoughtsContract.User.CONTENT_ITEM_TYPE;
		case ROUTE_THOUGHT:

			return UserThoughtsContract.Thought.CONTENT_TYPE;
		case ROUTE_THOUGHT_ID:

			return UserThoughtsContract.Thought.CONTENT_ITEM_TYPE;

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final int match = URI_MATCHER.match(uri);

		SelectionBuilder builder = new SelectionBuilder();
		
		SQLiteDatabase db = databaseHelper.getReadableDatabase();

		Cursor c = null;
		Context ctx = null;

		switch (match) {
		case ROUTE_USER_ID:
			// Return a single user, by id.
			String id = uri.getLastPathSegment();
			builder.where(UserThoughtsContract.User.COLUMN_ID + "=?", id);

		case ROUTE_USER:
			// Return all known users.
			builder.table(UserThoughtsContract.User.TABLE).where(selection,
					selectionArgs);
			c = builder.query(db, projection, sortOrder);
			// Note: Notification URI must be manually set here for loaders to
			// correctly
			// register ContentObservers.
			ctx = getContext();
			assert ctx != null;
			c.setNotificationUri(ctx.getContentResolver(), uri);
			return c;

		case ROUTE_THOUGHT_ID:
			// Return a single thought, by ID.
			String thoughtId = uri.getLastPathSegment();
			builder.where(UserThoughtsContract.Thought.COLUMN_ID + "=?", thoughtId);
		case ROUTE_THOUGHT:

			// Return all known thoughts.
			builder.table(UserThoughtsContract.Thought.TABLE).where(selection,
					selectionArgs);
			c = builder.query(db, projection, sortOrder);
			// Note: Notification URI must be manually set here for loaders to
			// correctly
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
		case ROUTE_USER:
			id = db.insertOrThrow(UserThoughtsContract.User.TABLE, null, values);
			result = Uri.parse(UserThoughtsContract.User.CONTENT_URI + "/" + id);
			ctx = getContext();
			assert ctx != null;
			ctx.getContentResolver().notifyChange(uri, null, false);
			return result;

		case ROUTE_USER_ID:
			throw new UnsupportedOperationException("Unknown uri: " + uri);

		case ROUTE_THOUGHT:

			id = db.insertOrThrow(UserThoughtsContract.Thought.TABLE, null, values);
			result = Uri.parse(UserThoughtsContract.Thought.CONTENT_URI + "/" + id);
			ctx = getContext();
			assert ctx != null;
			ctx.getContentResolver().notifyChange(uri, null, false);
			return result;

		case ROUTE_THOUGHT_ID:
			throw new UnsupportedOperationException("Unknown uri: " + uri);

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SelectionBuilder builder = new SelectionBuilder();
		final SQLiteDatabase db = databaseHelper.getWritableDatabase();
		final int match = URI_MATCHER.match(uri);
		int count;
		String id = null;
		switch (match) {
		case ROUTE_USER:
			count = builder.table(UserThoughtsContract.User.TABLE)
					.where(selection, selectionArgs).update(db, values);
			break;

		case ROUTE_USER_ID:
			id = uri.getLastPathSegment();
			count = builder.table(UserThoughtsContract.User.TABLE)
					.where(UserThoughtsContract.User.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs).update(db, values);
			break;
		case ROUTE_THOUGHT:

			count = builder.table(UserThoughtsContract.Thought.TABLE)
					.where(selection, selectionArgs).update(db, values);
			break;
		case ROUTE_THOUGHT_ID:

			id = uri.getLastPathSegment();
			count = builder.table(UserThoughtsContract.Thought.TABLE)
					.where(UserThoughtsContract.Thought.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs).update(db, values);
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
		case ROUTE_USER:
			count = builder.table(UserThoughtsContract.User.TABLE)
					.where(selection, selectionArgs).delete(db);
			break;
		case ROUTE_USER_ID:

			id = uri.getLastPathSegment();
			count = builder.table(UserThoughtsContract.User.TABLE)
					.where(UserThoughtsContract.User.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs).delete(db);
			break;
		case ROUTE_THOUGHT:
			count = builder.table(UserThoughtsContract.Thought.TABLE)
					.where(selection, selectionArgs).delete(db);
			break;
		case ROUTE_THOUGHT_ID:

			id = uri.getLastPathSegment();
			count = builder.table(UserThoughtsContract.Thought.TABLE)
					.where(UserThoughtsContract.Thought.COLUMN_ID + "=?", id)
					.where(selection, selectionArgs).delete(db);
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

	static class UserThoughtsDatabase extends SQLiteOpenHelper {
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_NAME = "user_thoughts";

		public static final String CREATE_THOUGHT_TABLE = "CREATE TABLE "
				+ UserThoughtsContract.Thought.TABLE + " ("
				+ UserThoughtsContract.Thought.COLUMN_ID
				+ " INTEGER PRIMARY KEY , "
				+ UserThoughtsContract.Thought.COLUMN_THOUGHT + " TEXT, "
				+ UserThoughtsContract.Thought.COLUMN_AUTHOR + " TEXT, "
				+ UserThoughtsContract.Thought.COLUMN_MODIFIED_BY + " TEXT, "
				+ UserThoughtsContract.Thought.COLUMN_LAST_MODIFIED + " INTEGER );";

		private static final String CREATE_USER_TABLE = "CREATE TABLE "
				+ UserThoughtsContract.User.TABLE + " ("
				+ UserThoughtsContract.User.COLUMN_ID + " INTEGER PRIMARY KEY , "
				+ UserThoughtsContract.User.COLUMN_FIRST_NAME + " TEXT, "
				+ UserThoughtsContract.User.COLUMN_LAST_NAME + " TEXT, "
				+ UserThoughtsContract.User.COLUMN_EMAIL + " TEXT, "
				+ UserThoughtsContract.User.COLUMN_SEX + " TEXT, "
				+ UserThoughtsContract.User.COLUMN_COUNTRY + " TEXT, "
				+ UserThoughtsContract.User.COLUMN_USER_NAME + " TEXT, "
				+ UserThoughtsContract.User.COLUMN_PASSWORD + " TEXT );";

		private UserThoughtsDatabase(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_THOUGHT_TABLE);
			db.execSQL(CREATE_USER_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}
