package pl.edu.amu.wmi.betterjira.pages;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.FiltersAdapter;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.TasksActivity;
import pl.edu.amu.wmi.betterjira.api.function.GetFavouriteFilters;
import pl.edu.amu.wmi.betterjira.api.function.data.Filter;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FiltersActivity extends Activity implements OnItemClickListener {

    private ListView listView;
    private FiltersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.page_filters);

	listView = (ListView) findViewById(R.id.listView);

	adapter = new FiltersAdapter(this);
	listView.setAdapter(adapter);

	LoadFilters loadFilters = new LoadFilters();
	loadFilters.execute();

	listView.setOnItemClickListener(this);
    }

    private class LoadFilters extends AsyncTask<Void, Void, String> {

	private ArrayList<Filter> filters;

	@Override
	protected String doInBackground(Void... params) {

	    GetFavouriteFilters getFavouriteFilters = new GetFavouriteFilters(
		    BetterJiraApplication.getSession());

	    try {
		filters = getFavouriteFilters.getFilters();

	    } catch (BadResponse e) {
		e.printStackTrace();
	    }

	    return null;
	}

	@Override
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    adapter.addAll(filters);
	}
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {

	Filter filter = (Filter) adapter.getItem(index);
	Intent intent = new Intent(this, TasksActivity.class);
	intent.putExtra("JQL", filter.getJql().getCommand());

	startActivity(intent);

    }
}
