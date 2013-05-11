package pl.edu.amu.wmi.betterjira.pages.filter;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.PageIndicatorActivity;
import pl.edu.amu.wmi.betterjira.api.function.GetFavouriteFilters;
import pl.edu.amu.wmi.betterjira.api.function.data.Filter;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.pages.Page;
import pl.edu.amu.wmi.betterjira.pages.issue.IssueListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FiltersFragment extends Page implements OnItemClickListener {

    private ListView listView;
    private FiltersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	listView = new ListView(getActivity());

	adapter = new FiltersAdapter(getActivity());
	listView.setAdapter(adapter);

	LoadFilters loadFilters = new LoadFilters();
	loadFilters.execute();

	listView.setOnItemClickListener(this);

	return listView;
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

	Intent intent = new Intent(getActivity(), PageIndicatorActivity.class);
	intent.putExtra(IssueListFragment.EXTRA_JQL, filter.getJql()
		.getCommand());
	intent.putExtra(PageIndicatorActivity.EXTRA_TREE_NODE, getClass()
		.getSimpleName());
	startActivity(intent);
    }

    @Override
    public String getTitle() {
	return "Filters";
    }
}
