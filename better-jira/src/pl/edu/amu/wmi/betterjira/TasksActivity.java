package pl.edu.amu.wmi.betterjira;

import pl.edu.amu.wmi.betterjira.api.function.SearchForIssues;
import pl.edu.amu.wmi.betterjira.api.function.data.Issue;
import pl.edu.amu.wmi.betterjira.api.function.data.IssueList;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.InvalidJQLCommand;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TasksActivity extends Activity implements OnItemClickListener {

    private ListView listView;
    private TasksAdapter adapter;
    private String jql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.page_tasks);

	jql = getIntent().getExtras().getString("JQL");

	listView = (ListView) findViewById(R.id.listView);

	adapter = new TasksAdapter(this);
	listView.setAdapter(adapter);

	LoadTasks loadTasks = new LoadTasks();
	loadTasks.execute();

	listView.setOnItemClickListener(this);
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

	Intent intent = new Intent(this, TaskComments.class);
	intent.putExtra("KEY", issue.getKey());

	startActivity(intent);
    }
}
