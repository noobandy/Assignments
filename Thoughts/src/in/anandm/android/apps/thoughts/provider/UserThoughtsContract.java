/**
 * 
 */
package in.anandm.android.apps.thoughts.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author anandm
 * 
 */
public class UserThoughtsContract {

	/**
	 * Content provider authority.
	 */
	public static final String CONTENT_AUTHORITY = "in.anandm.android.apps.userthoughts";

	/**
	 * Base URI. (content://in.anandm.android.apps.userthoughts)
	 */
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	/**
	 * Path component for "users"-type resources..
	 */
	public static final String PATH_USERS = "users";

	/**
	 * Path component for "thought"-type resources..
	 */
	public static final String PATH_THOUGHTS = "thoughts";

	/*
	 * Columns supported by "user" records.
	 */
	public static class User implements BaseColumns {
		/**
		 * MIME type for lists of user.
		 */
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
				+ "/vnd.userthoughts.users";
		/**
		 * MIME type for individual user.
		 */
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
				+ "/vnd.userthoughts.user";

		/**
		 * Fully qualified URI for "user" resources.
		 */
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_USERS).build();

		public static final String TABLE = "user";
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_FIRST_NAME = "firstName";
		public static final String COLUMN_LAST_NAME = "lastName";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_USER_NAME = "userName";
		public static final String COLUMN_PASSWORD = "password";
		public static final String COLUMN_SEX = "sex";
		public static final String COLUMN_COUNTRY = "country";

	}

	/**
	 * Columns supported by "thought" records.
	 */
	public static class Thought implements BaseColumns {
		/**
		 * MIME type for lists of thoughts.
		 */
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
				+ "/vnd.userthoughts.thoughts";
		/**
		 * MIME type for individual thoughts.
		 */
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
				+ "/vnd.userthoughts.thought";

		/**
		 * Fully qualified URI for "thought" resources.
		 */
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_THOUGHTS).build();

		public static final String TABLE = "thoughts";
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_THOUGHT = "thought";
		public static final String COLUMN_AUTHOR = "author";
		public static final String COLUMN_LAST_MODIFIED = "lastModified";
		public static final String COLUMN_MODIFIED_BY = "modifiedBy";
	}

}
