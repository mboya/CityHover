package project.dkut.mobytrans;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	Button btn_login = null;
	private EditText mUsername;
	private EditText mPassword;
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Parse.initialize(this, "se6LySiRmGj08aDoO89JsxbuchKVaTv7wGhPSf5E", "TLBKkaNiJgp1F92QGHJcXfbbCwoIOs3FfAoCyNDp");
		//ParseAnalytics.trackAppOpened(getIntent());
		
		btn_login = (Button) findViewById (R.id.btn_login);
		mUsername = (EditText) findViewById (R.id.editTxtUsername);
		mPassword = (EditText) findViewById (R.id.editTxtPassword);
		
		btn_login.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				attemptLogin();
			}
		});
	}
	
	public void attemptLogin(){
		username = mUsername.getText().toString();
		password = mPassword.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		if(TextUtils.isEmpty(username)){
			//mUsername.setError(getString(R.string.error_field_required));
			Toast.makeText(getApplicationContext(), "Username Empty.", Toast.LENGTH_SHORT).show();
			//focusView = mUsername;
			cancel = true;
		}
		if (TextUtils.isEmpty(password)) {
			//mPassword.setError(getString(R.string.error_field_required));
			Toast.makeText(getApplicationContext(), "Password Empty.", Toast.LENGTH_SHORT).show();
			//focusView = mPassword;
			cancel = true;
		}else if (password.length() < 4) {
			mPassword.setError(getString(R.string.error_invalid_input));
			focusView =mPassword;
			cancel = true;
		}
		if(cancel){
			focusView.requestFocus();
		}else{
			Login(username.toLowerCase(Locale.getDefault()),password);
		}
	}

	public void Login(String lowerCase, String password){
		ParseUser.logInInBackground(lowerCase, password, new LogInCallback(){
			@Override
			public void done(ParseUser user, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if(e==null)
					loginSuccessful();
				else
					loginUnsuccessful();
			}
		});
	}
	
	protected void loginSuccessful(){
		Intent in=new Intent(LoginActivity.this, MainActivity.class);
		startActivity(in);
		LoginActivity.this.finish();
	}
	protected void loginUnsuccessful(){
		Toast.makeText(getApplicationContext(), "Username or Password is invalid.", Toast.LENGTH_SHORT).show();
		//showAlertDialog(LoginActivity.this,"Login", "Username or Password is invalid.", false);
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.login, menu);
//		return true;
//	}

}
