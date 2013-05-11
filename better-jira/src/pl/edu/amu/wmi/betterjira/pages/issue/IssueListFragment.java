package pl.edu.amu.wmi.betterjira.pages.issue;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.PageIndicatorActivity;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.R.id;
import pl.edu.amu.wmi.betterjira.R.layout;
import pl.edu.amu.wmi.betterjira.api.function.SearchForIssues;
import pl.edu.amu.wmi.betterjira.api.function.data.Issue;
import pl.edu.amu.wmi.betterjira.api.function.data.IssueList;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.InvalidJQLCommand;
import pl.edu.amu.wmi.betterjira.pages.Page;
import pl.edu.amu.wmi.betterjira.pages.issue.comment.CommentsFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class IssueListFragment extends Page implements OnItemClickListener {

    public static final String EXTRA_JQL = "jql";

    private ListView listView;
    private IssueAdapter adapter;
    private String jql;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	if (getActivity().getIntent().getExtras() != null) {
	    jql = getActivity().getIntent().getExtras().getString(EXTRA_JQL);
	    if (jql == null) {
		throw new RuntimeException("Give me JQL");
	    }
	} else {
	    throw new RuntimeException("Give me JQL");
	}

	listView = new ListView(getActivity());
	adapter = new IssueAdapter(getActivity());
	listView.setAdapter(adapter);

	LoadTasks loadTasks = new LoadTasks();
	loadTasks.execute();

	listView.setOnItemClickListener(this);

	return listView;
    }

    private class LoadTasks extends AsyncTask<Void, Void, String> {

	private IssueList search;

	@Override
	protected String doInBackground(Void... params) {

	    SearchForIssues searchForIssues = new SearchForIssues(
		    BetterJiraApplication.getSession());
	    try {
		search = searchForIssues.search(jql, 0, 50);

	    } catch (InvalidJQLCommand e) {
		e.printStackTrace();
	    } catch (BadResponse e) {
		e.printStackTrace();
	    }

	    return null;
	}

	@Override
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    adapter.setIssueList(search);
	    adapter.notifyDataSetChanged();
	}
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
	Issue issue = (Issue) adapter.getItem(index);

	Intent intent = new Intent(getActivity(), PageIndicatorActivity.class);
	intent.putExtra(IssueFullInfoFragment.EXTRA_ISSUE_KEY, issue.getKey());
	intent.putExtra(PageIndicatorActivity.EXTRA_TREE_NODE, getClass()
		.getSimpleName());
	startActivity(intent);
    }

    @Override
    public String getTitle() {
	return "Issue list";
    }
}
