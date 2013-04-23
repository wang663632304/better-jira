package pl.edu.amu.wmi.betterjira;

import java.net.MalformedURLException;
import java.net.URL;

import pl.edu.amu.wmi.betterjira.api.ServerConnector;
import pl.edu.amu.wmi.betterjira.api.function.BasicAuthentication;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.LoginException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class MainScreenActivity extends Activity implements OnClickListener {

    private AutoCompleteTextView autoCompleteTextViewUrl;
    private AutoCompleteTextView autoCompleteTextViewLogin;
    private EditText editTextPassword;

    private Button buttonLogin;
    private ProgressDialogIndicator progressDialogIndicator;
    private LoginAsync loginAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main_screen);

	autoCompleteTextViewUrl = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewUrl);
	autoCompleteTextViewLogin = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewLogin);
	editTextPassword = (EditText) findViewById(R.id.editTextPassword);

	buttonLogin = (Button) findViewById(R.id.buttonLogin);
	buttonLogin.setOnClickListener(this);
    }

    // Login
    @Override
    public void onClick(View v) {

	try {
	    ServerConnector.setServerURL(new URL(autoCompleteTextViewUrl
		    .getText().toString()));
	} catch (MalformedURLException e1) {
	    e1.printStackTrace();
	    autoCompleteTextViewUrl.setError(getString(R.string.invalid_url));
	}

	if (autoCompleteTextViewLogin.getText().toString().length() < 1) {
	    autoCompleteTextViewLogin.setError(getString(R.string.enter_login));
	    return;
	}

	if (editTextPassword.getText().toString().length() < 1) {
	    editTextPassword.setError(getString(R.string.enter_password));
	    return;
	}

	loginAsync = new LoginAsync();
	loginAsync.execute();
    }

    @Override
    protected void onPause() {
	super.onPause();
    }

    class LoginAsync extends AsyncTask<Void, Void, String> {

	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
	    progressDialogIndicator = new ProgressDialogIndicator(
		    MainScreenActivity.this);
	    progressDialogIndicator.show();
	}

	@Override
	protected String doInBackground(Void... params) {
	    BasicAuthentication basicAuthentication = new BasicAuthentication();
	    try {
		basicAuthentication.login(autoCompleteTextViewLogin.getText()
			.toString(), editTextPassword.getText().toString());
	    } catch (LoginException e) {
		e.printStackTrace();
		return e.getMessage();
	    } catch (BadResponse e) {
		e.printStackTrace();
	    }
	    return null;
	}

	@Override
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
		    .show();

	    progressDialogIndicator.dismiss();
	    progressDialogIndicator = null;
	}

    }
}
