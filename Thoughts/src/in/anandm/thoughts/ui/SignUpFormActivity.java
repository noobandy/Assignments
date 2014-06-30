package in.anandm.thoughts.ui;

import in.anandm.android.utils.validator.BindingErrorRenderer;
import in.anandm.android.utils.validator.BindingResults;
import in.anandm.android.utils.validator.TextViewBindingErrorRenderer;
import in.anandm.android.utils.validator.ValidationUtils;
import in.anandm.thoughts.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
		ValidationUtils.rejectIfEmptyOrWhitespace(results, password,
				"Password is required");
		if (results.isValid()) {
			String sex = null;
			if (male.isChecked()) {
				sex = "Male";
			} else {
				sex = "Female";
			}
		}
	}
}
