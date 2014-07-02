package in.anandm.android.apps.thoughts.ui;

import in.anandm.android.apps.thoughts.provider.UserThoughtsContract;
import in.anandm.android.utils.validator.BindingErrorRenderer;
import in.anandm.android.utils.validator.BindingResults;
import in.anandm.android.utils.validator.TextViewBindingErrorRenderer;
import in.anandm.android.utils.validator.ValidationUtils;
import in.anandm.thoughts.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class SignUpFormActivity extends Activity {

	private EditText firstName = null;
	private EditText lastName = null;
	private EditText email = null;
	private EditText username = null;
	private EditText password = null;
	private EditText confirmPassword = null;
	private Spinner country = null;
	private RadioButton male = null;
	private Button submit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_form);
		firstName = (EditText) findViewById(R.id.firstName);
		lastName = (EditText) findViewById(R.id.lastName);
		email = (EditText) findViewById(R.id.email);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		confirmPassword = (EditText) findViewById(R.id.confirmPassword);
		male = (RadioButton) findViewById(R.id.gender_male);
		country = (Spinner) findViewById(R.id.country);
		submit = (Button) findViewById(R.id.button_sign_up);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submitSignUpForm();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			break;
		default:
			break;
		}
		return true;
	}
	public void submitSignUpForm() {
		BindingErrorRenderer bindingErrorRenderer = new TextViewBindingErrorRenderer();

		BindingResults results = new BindingResults(bindingErrorRenderer, this);

		ValidationUtils.rejectIfEmptyOrWhitespace(results, firstName,
				"First Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(results, lastName,
				"Last Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(results, email,
				"Email is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(results, username,
				"User Name is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(results, password,
				"Password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(results, confirmPassword,
				"Password is required");

		// check is user name is available
		String userName = username.getText().toString();

		Cursor cursor = getContentResolver().query(
				UserThoughtsContract.User.CONTENT_URI,
				new String[] { UserThoughtsContract.User.COLUMN_ID },
				UserThoughtsContract.User.COLUMN_USER_NAME + " = ?",
				new String[] { userName }, null);

		if (cursor.moveToFirst()) {
			results.rejectValue(username, "User Name already in use");
		}

		if (results.isValid()) {

			String sex = null;
			if (male.isChecked()) {
				sex = "Male";
			} else {
				sex = "Female";
			}

			ContentValues contentValues = new ContentValues();
			contentValues.put(UserThoughtsContract.User.COLUMN_COUNTRY,
					country.getSelectedItemId());
			contentValues.put(UserThoughtsContract.User.COLUMN_EMAIL, email
					.getText().toString());
			contentValues.put(UserThoughtsContract.User.COLUMN_FIRST_NAME,
					firstName.getText().toString());
			contentValues.put(UserThoughtsContract.User.COLUMN_LAST_NAME,
					lastName.getText().toString());
			contentValues.put(UserThoughtsContract.User.COLUMN_PASSWORD,
					password.getText().toString());
			contentValues.put(UserThoughtsContract.User.COLUMN_SEX, sex);
			contentValues.put(UserThoughtsContract.User.COLUMN_USER_NAME,
					username.getText().toString());

			//insert user details
			getContentResolver().insert(UserThoughtsContract.User.CONTENT_URI,
					contentValues);

			//return to main activity
			Intent loginIntent = new Intent(this, MainActivity.class);

			startActivity(loginIntent);
		}
	}
}
