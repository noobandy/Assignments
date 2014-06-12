/**
 * 
 */
package in.anandm.android.apps.signupform;

import java.io.Serializable;

/**
 * @author anandm
 *
 */
public class SignUpForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public String confirmPassword;
	public String sex;
	public String country;
	
	public SignUpForm(String firstName, String lastName, String email,
			String password, String confirmPassword, String sex, String country) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.sex = sex;
		this.country = country;
	}
	
}
