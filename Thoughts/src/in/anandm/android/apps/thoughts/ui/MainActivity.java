package in.anandm.android.apps.thoughts.ui;

import in.anandm.android.apps.thoughts.provider.UserThoughtsContract;
import in.anandm.android.apps.thoughts.util.SecurityContextHolder;
import in.anandm.android.utils.validator.BindingErrorRenderer;
import in.anandm.android.utils.validator.BindingResults;
import in.anandm.android.utils.validator.TextViewBindingErrorRenderer;
import in.anandm.android.utils.validator.ValidationUtils;
import in.anandm.thoughts.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button signUpButton;
	private Button signInButton;
	private EditText userNameInput;
	private EditText passwordInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private final class SignUpClickListener implements View.OnClickListener {

		private Context context;

		private SignUpClickListener(Context context) {
			super();
			this.context = context;
		}

		@Override
		public void onClick(View v) {

			Intent signUpIntent = new Intent(context, SignUpFormActivity.class);
			startActivity(signUpIntent);
		}

	}

	private final class SignInClickListener implements View.OnClickListener {

		private Context context;

		private SignInClickListener(Context context) {
			super();
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			BindingErrorRenderer bindingErrorRenderer = new TextViewBindingErrorRenderer();

			BindingResults results = new BindingResults(bindingErrorRenderer,
					(Activity) context);

			ValidationUtils.rejectIfEmptyOrWhitespace(results, userNameInput,
					"User Name is required");
			ValidationUtils.rejectIfEmptyOrWhitespace(results, passwordInput,
					"Password is required");

			if (results.isValid()) {
				String userName = userNameInput.getText().toString();
				String password = passwordInput.getText().toString();
				Cursor cursor = getContentResolver().query(
						UserThoughtsContract.User.CONTENT_URI,
						new String[] { UserThoughtsContract.User.COLUMN_ID,
								UserThoughtsContract.User.COLUMN_PASSWORD },
						UserThoughtsContract.User.COLUMN_USER_NAME + " = ? ",
						new String[] { userName }, null);

				Toast message = null;

				if (cursor.moveToFirst()) {
					String dbPassword = cursor
							.getString(cursor
									.getColumnIndex(UserThoughtsContract.User.COLUMN_PASSWORD));

					if (password.equals(dbPassword)) {
						// update security context
						SecurityContextHolder.setPrincipal(userName);

						Intent thoughtListIntent = new Intent(context,
								ThoughtListActivity.class);

						startActivity(thoughtListIntent);
						message = Toast.makeText(context, "Login Success",Toast.LENGTH_SHORT);
					} else {
						message = Toast.makeText(context,
								"Login failed wrong password",
								Toast.LENGTH_SHORT);
					}
				} else {
					message = Toast.makeText(context,
							"Login failed no user found", Toast.LENGTH_SHORT);
				}

				message.show();
			}
		}
	}

	private void initializeUI() {
		signUpButton = (Button) findViewById(R.id.buttonSignUp);
		signInButton = (Button) findViewById(R.id.buttonSignIn);

		userNameInput = (EditText) findViewById(R.id.loginId);
		passwordInput = (EditText) findViewById(R.id.loginPassword);

		signUpButton.setOnClickListener(new SignUpClickListener(this));

		signInButton.setOnClickListener(new SignInClickListener(this));
	}
}
