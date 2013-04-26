package pl.edu.amu.wmi.betterjira;

import java.net.MalformedURLException;
import java.net.URL;

import pl.edu.amu.wmi.betterjira.api.ServerConnector;
import pl.edu.amu.wmi.betterjira.api.function.BasicAuthentication;
import pl.edu.amu.wmi.betterjira.api.function.data.Session;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.LoginException;
import pl.edu.amu.wmi.betterjira.pages.FiltersActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends SherlockActivity implements OnClickListener {

    private AutoCompleteTextView autoCompleteTextViewUrl;
    private AutoCompleteTextView autoCompleteTextViewLogin;
    private EditText editTextPassword;

    private Button buttonLogin;
    private ProgressDialogIndicator progressDialogIndicator;
    private LoginAsync loginAsync;

    // TODO Task BET-42 for now hardcoded
    private final String[] urls = new String[] {
	    "https://jira.wmi.amu.edu.pl/", "https://koalalab.atlassian.net" };

    // TODO Task BET-42 for now hardcoded
    private final String[] logins = new String[] { "s362617", "s362631",
	    "s369962" };

    private int MENU_ACTION_ADD_PAGE = 0x01;
    private int MENU_ACTION_SUBSTRACT_PAGE = 0x02;
    private int MENU_ACTION_OPTIONS = 0x03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);

	autoCompleteTextViewUrl = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewUrl);
	autoCompleteTextViewLogin = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewLogin);
	editTextPassword = (EditText) findViewById(R.id.editTextPassword);

	buttonLogin = (Button) findViewById(R.id.buttonLogin);
	buttonLogin.setOnClickListener(this);

	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_dropdown_item_1line, urls);
	autoCompleteTextViewUrl.setAdapter(adapter);

	adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_dropdown_item_1line, logins);
	autoCompleteTextViewLogin.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	menu.add("Add page").setIcon(android.R.drawable.ic_menu_add)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	menu.add("Remove page").setIcon(android.R.drawable.ic_menu_delete)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

	menu.add("Options").setIcon(android.R.drawable.ic_menu_preferences)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

	return true;
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
		    LoginActivity.this);
	    progressDialogIndicator.show();
	}

	@Override
	protected String doInBackground(Void... params) {
	    BasicAuthentication basicAuthentication = new BasicAuthentication();
	    try {
		Session session = basicAuthentication.login(
			autoCompleteTextViewLogin.getText().toString(),
			editTextPassword.getText().toString());

		BetterJiraApplication.setSession(session);

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

	    if (result == null) {
		result = new String("Login success");
	    }
	    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
		    .show();

	    progressDialogIndicator.dismiss();
	    progressDialogIndicator = null;

	    Intent intent = new Intent(LoginActivity.this,
		    PageIndicatorActivity.class);
	    LoginActivity.this.startActivity(intent);
	}
    }
}
