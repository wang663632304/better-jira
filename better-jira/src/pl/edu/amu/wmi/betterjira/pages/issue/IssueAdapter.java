package pl.edu.amu.wmi.betterjira.pages.issue;

import pl.edu.amu.wmi.betterjira.AsyncTaskWorker;
import pl.edu.amu.wmi.betterjira.BaseAdapterSnippet;
import pl.edu.amu.wmi.betterjira.LazyLoadBitmap;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.api.function.data.IssueList;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IssueAdapter extends BaseAdapterSnippet {
    private IssueList issueList = new IssueList();

    public IssueAdapter(Context context) {
	super(context);
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
	return getLayoutInflater().inflate(R.layout.row_task, null);
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

	AsyncTaskWorker.createAndRun(view.getContext(),
		new LazyLoadBitmap(view.getContext(), holder.imageViewAssigne,
			null, issueList.getIssue(position).getAssignee()
				.getAvatarUrls().getBestQualityUrl(),
			android.R.drawable.progress_horizontal));

	AsyncTaskWorker.createAndRun(view.getContext(),
		new LazyLoadBitmap(view.getContext(), holder.imageViewReporter,
			null, issueList.getIssue(position).getReporter()
				.getAvatarUrls().getBestQualityUrl(),
			android.R.drawable.progress_horizontal));

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
	holder.imageViewAssigne = (ImageView) view
		.findViewById(R.id.imageViewAssigne);
	holder.imageViewReporter = (ImageView) view
		.findViewById(R.id.imageViewReporter);
    }

    private class Holder {
	TextView textViewIssueKey;
	TextView textViewAssigne;
	TextView textViewReporter;
	ImageView imageViewReporter;
	ImageView imageViewAssigne;
    }

    public IssueList getIssueList() {
	return issueList;
    }

    public void setIssueList(IssueList issueList) {
	this.issueList = issueList;
    }
}
