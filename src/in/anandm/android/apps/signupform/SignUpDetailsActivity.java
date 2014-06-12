package in.anandm.android.apps.signupform;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class SignUpDetailsActivity extends Activity {

	private TextView firstName = null;
	private TextView lastName = null;
	private TextView email = null;
	private TextView password = null;
	private TextView confirmPassword = null;
	private TextView country = null;
	private TextView gender = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_details);

		firstName =  (TextView)findViewById(R.id.firstName);
		lastName =  (TextView)findViewById(R.id.lastName);
		email =  (TextView)findViewById(R.id.email);
		password =  (TextView)findViewById(R.id.password);
		confirmPassword =  (TextView)findViewById(R.id.confirmPassword);
		gender = (TextView) findViewById(R.id.gender);
		country = (TextView) findViewById(R.id.country);




		SignUpForm signUpForm = (SignUpForm) getIntent().getExtras().get("signUpForm");

		firstName.setText(signUpForm.firstName);
		lastName.setText(signUpForm.lastName);
		email.setText(signUpForm.email);

		password.setText(signUpForm.password);
		confirmPassword.setText(signUpForm.confirmPassword);
		gender.setText(signUpForm.sex);
		country.setText(signUpForm.country);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up_details, menu);
		return true;
	}

}
