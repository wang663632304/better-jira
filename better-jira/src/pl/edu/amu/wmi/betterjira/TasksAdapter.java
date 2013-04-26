package pl.edu.amu.wmi.betterjira;

import pl.edu.amu.wmi.betterjira.api.function.data.IssueList;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TasksAdapter extends BaseAdapterSnippet {
    private IssueList issueList = new IssueList();
    private Context context;

    public TasksAdapter(Context context) {
	this.context = context;
    }

    @Override
    public int getCount() {
	return issueList.size();
    }

    @Override
    public Object getItem(int position) {
	return issueList.getIssue(position);
    }

    @Override
    public View getInflatedRow(int position) {
	// TODO refactor this create inflater in constructor
	return View.inflate(context, R.layout.row_task, null);
    }

    @Override
    protected Object getNewHolder() {
	return new Holder();
    }

    @Override
    protected void initializeHolder(Object yourHolder, View view, int position) {

	Holder holder = (Holder) yourHolder;
	holder.textViewIssueKey.setText(issueList.getIssue(position).getKey());
	holder.textViewAssigne.setText(issueList.getIssue(position)
		.getAssignee().getDisplayName());
	holder.textViewReporter.setText(issueList.getIssue(position)
		.getReporter().getDisplayName());

    }

    @Override
    protected void inflateHolder(Object yourHolder, View view) {

	Holder holder = (Holder) yourHolder;
	holder.textViewIssueKey = (TextView) view
		.findViewById(R.id.textViewIssueKey);
	holder.textViewAssigne = (TextView) view
		.findViewById(R.id.textViewAssigne);
	holder.textViewReporter = (TextView) view
		.findViewById(R.id.textViewReporter);
    }

    private class Holder {
	TextView textViewIssueKey;
	TextView textViewAssigne;
	TextView textViewReporter;
    }

    public IssueList getIssueList() {
	return issueList;
    }

    public void setIssueList(IssueList issueList) {
	this.issueList = issueList;
    }
}
