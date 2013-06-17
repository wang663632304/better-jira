package pl.edu.amu.wmi.betterjira.pages.filter;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import pl.edu.amu.wmi.betterjira.AsyncTaskInterface;
import pl.edu.amu.wmi.betterjira.AsyncTaskWorker;
import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.api.function.data.Filter;
import pl.edu.amu.wmi.betterjira.api.function.data.JQL;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.EmptyResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.NoStatusLine;
import pl.edu.amu.wmi.betterjira.api.function.filters.CreateFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EditFilterActivity extends SherlockFragmentActivity {

    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextJQL;
    private ImageButton imageButtonIsFavorite;
    private boolean isNewFilter = false;

    static public Filter filter;

    @Override
    protected void onCreate(Bundle arg0) {
	super.onCreate(arg0);

	setContentView(R.layout.activity_filter);

	editTextName = (EditText) findViewById(R.id.editTextFilterName);
	editTextDescription = (EditText) findViewById(R.id.editTextFilterDescription);
	editTextJQL = (EditText) findViewById(R.id.editTextFilterJQL);

	imageButtonIsFavorite = (ImageButton) findViewById(R.id.imageButtonFilterIsFavorite);

	if (filter == null) {
	    filter = new Filter();
	    filter.setJql(new JQL(""));
	    isNewFilter = true;
	}

	editTextName.setText(filter.getName());
	editTextDescription.setText(filter.getDescription());
	editTextJQL.setText(filter.getJql().getCommand());
	setFavorite(filter.isFavourite());

	imageButtonIsFavorite.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		setFavorite(!filter.isFavourite());
	    }
	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	menu.add("Save").setIcon(android.R.drawable.ic_menu_save)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

	return true;
    }

    private void setFavorite(boolean isFavorite) {
	filter.setFavourite(isFavorite);
	if (isFavorite) {
	    imageButtonIsFavorite.setImageResource(R.drawable.like);
	} else {
	    imageButtonIsFavorite.setImageResource(R.drawable.dislike);
	}
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

	filter.setName(editTextName.getText().toString());
	filter.setDescription(editTextDescription.getText().toString());
	filter.getJql().setCommand((editTextJQL.getText().toString()));

	// TODO Haha ugly code :D
	if (isNewFilter) {
	    // We create new filter
	    AsyncTaskWorker.createAndRun(getApplicationContext(),
		    new AsyncTaskInterface() {

			@Override
			public void onProgressUpdate() {
			}

			@Override
			public void onPreExecute(AsyncTaskWorker asyncTaskWorker) {
			}

			@Override
			public void onPostExecute(int resultCode) {
			    if (resultCode == -1) {
				Toast.makeText(getApplicationContext(),
					"Invalid input format",
					Toast.LENGTH_LONG).show();
			    } else {
				Toast.makeText(getApplicationContext(),
					"Saved succesfull", Toast.LENGTH_LONG)
					.show();
				finish();
			    }
			}

			@Override
			public void onCancelled() {
			}

			@Override
			public int doInBackground(
				AsyncTaskWorker asyncTaskWorker) {

			    CreateFilter createFilter = new CreateFilter(
				    BetterJiraApplication.getSession());
			    try {
				createFilter.createFilter(filter);
			    } catch (InvalidParameterException e) {
				e.printStackTrace();
				return -1;
			    } catch (ClientProtocolException e) {
				e.printStackTrace();
			    } catch (IllegalStateException e) {
				e.printStackTrace();
			    } catch (BadResponse e) {
				e.printStackTrace();
			    } catch (IOException e) {
				e.printStackTrace();
			    } catch (NoStatusLine e) {
				e.printStackTrace();
			    } catch (EmptyResponse e) {
				e.printStackTrace();
			    } catch (JSONException e) {
				e.printStackTrace();
			    }
			    return 0;
			}
		    });
	} else {
	    // We update already existing
	    // TODO
	}

	return true;
    }
}
