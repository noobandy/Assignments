/**
 * 
 */
package in.anandm.thoughts.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author anandm
 *
 */
public class UserDetailsContract {

	/**
	 * Content provider authority.
	 */
	public static final String CONTENT_AUTHORITY = "in.anandm.android.apps.schemefeedback";

	/**
	 * Base URI. (content://in.anandm.android.apps.schemefeedback.scheme)
	 */
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	/**
	 * Path component for "scheme"-type resources..
	 */
	private static final String PATH_SCHEMES = "schemes";

	/**
	 * Path component for "scheme"-type resources..
	 */
	private static final String PATH_FEEDBACK = "feedbacks";

	/**
	 * Columns supported by "schemes" records.
	 */
	public static class Scheme implements BaseColumns {
		/**
		 * MIME type for lists of schemes.
		 */
		public static final String CONTENT_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.schemefeedback.schemes";
		/**
		 * MIME type for individual schemes.
		 */
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.schemefeedback.scheme";

		/**
		 * Fully qualified URI for "scheme" resources.
		 */
		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHEMES).build();

		public static final String TABLE = "scheme";
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_SCHEME_NAME = "schemeName";
		public static final String COLUMN_LAST_MODIFIED = "lastModified";
		public static final String COLUMN_MODIFIED_BY = "modifiedBy";
	}

	/*
	 * Columns supported by "userDetails" records.
	 */
	public static class UserDetails implements BaseColumns {
		/**
		 * MIME type for lists of userDetails.
		 */
		public static final String CONTENT_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.schemefeedback.userDetails";
		/**
		 * MIME type for individual userDetails.
		 */
		public static final String CONTENT_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.schemefeedback.userDetail";

		/**
		 * Fully qualified URI for "feedback" resources.
		 */
		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEEDBACK).build();
		
		public static final String TABLE = "user_details";
		public static final String COLUMN_ID = BaseColumns._ID;
		public static final String COLUMN_FIRST_NAME = "firstName";
		public static final String COLUMN_LAST_NAME = "lastName";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_PASSWORD = "password";
		public static final String COLUMN_SEX = "sex";
		public static final String COLUMN_COUNTRY = "country";

	}
}
