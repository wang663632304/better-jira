package pl.edu.amu.wmi.betterjira.pages.issue;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.api.function.GetIssue;
import pl.edu.amu.wmi.betterjira.api.function.data.Issue;
import pl.edu.amu.wmi.betterjira.pages.Page;
import pl.edu.amu.wmi.betterjira.view.BJEditText;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IssueFullInfoFragment extends Page {

    public static final String EXTRA_STRING_ISSUE_KEY = "issue_key";
    private String issueKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	issueKey = getActivity().getIntent().getExtras()
		.getString(EXTRA_STRING_ISSUE_KEY);

	new GetIssueAsync().execute();// TODO refactor + fix bug

	return inflater.inflate(R.layout.fragment_task_full_info, null);
    }

    @Override
    public String getTitle() {
	return "Task";
    }

    private class GetIssueAsync extends AsyncTask<Void, Void, Void> {

	private Issue issue;

	@Override
	protected Void doInBackground(Void... params) {

	    GetIssue getIssue = new GetIssue(BetterJiraApplication.getSession());
	    try {
		issue = getIssue.getIssue(issueKey);
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	    return null;
	}

	@Override
	protected void onPostExecute(Void result) {
	    super.onPostExecute(result);
	    bindIssue(issue);
	}
    }

    private void bindIssue(Issue issue) {

	TextView textView = (TextView) getView()
		.findViewById(R.id.textViewType);
	textView.setText(issue.getIssuetype().getName());

	BJEditText editText = (BJEditText) getView().findViewById(
		R.id.bjeditTextDescription);
	editText.setText(issue.getDescription());

    }
}
