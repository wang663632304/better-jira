package pl.edu.amu.wmi.betterjira.pages.project;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.AsyncTaskWorker;
import pl.edu.amu.wmi.betterjira.BaseAdapterSnippet;
import pl.edu.amu.wmi.betterjira.LazyLoadBitmap;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.api.function.data.Project;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProjectsAdapter extends BaseAdapterSnippet {

    private ArrayList<Project> projects = new ArrayList<Project>();

    public ProjectsAdapter(Context context) {
	super(context);
    }

    @Override
    public int getCount() {
	return projects.size();
    }

    @Override
    public Object getItem(int position) {
	return projects.get(position);
    }

    @Override
    public View getInflatedRow(int position) {
	return getLayoutInflater().inflate(R.layout.row_project, null);
    }

    @Override
    protected Object getNewHolder() {
	return new Holder();
    }

    @Override
    protected void initializeHolder(Object yourHolder, View view, int position) {
	Holder holder = (Holder) yourHolder;
	Project project = projects.get(position);

	holder.textViewProjectName.setText(project.getName());
	holder.textViewKey.setText(project.getKey());
	holder.textViewLead.setText(project.getLead().getDisplayName());
	holder.textViewUrl.setText(project.getUrl());

	AsyncTaskWorker.createAndRun(view.getContext(),
		new LazyLoadBitmap(view.getContext(),
			holder.imageViewProjectIcon, null, project
				.getAvatarUrls().getBestQualityUrl(),
			android.R.drawable.progress_horizontal));

	AsyncTaskWorker.createAndRun(view.getContext(),
		new LazyLoadBitmap(view.getContext(),
			holder.imageViewLeadAvatar, null, project.getLead()
				.getAvatarUrls().getBestQualityUrl(),
			android.R.drawable.progress_horizontal));
    }

    @Override
    protected void inflateHolder(Object yourHolder, View view) {
	Holder holder = (Holder) yourHolder;

	holder.textViewProjectName = (TextView) view
		.findViewById(R.id.textViewProjectName);
	holder.textViewUrl = (TextView) view.findViewById(R.id.textViewUrl);
	holder.textViewLead = (TextView) view.findViewById(R.id.textViewLead);
	holder.textViewKey = (TextView) view.findViewById(R.id.textViewKey);
	holder.imageViewProjectIcon = (ImageView) view
		.findViewById(R.id.imageViewProjectIcon);
	holder.imageViewLeadAvatar = (ImageView) view
		.findViewById(R.id.imageViewLeadAvatar);
    }

    private static class Holder {
	public TextView textViewProjectName;
	public TextView textViewUrl;
	public TextView textViewLead;
	public TextView textViewKey;
	public ImageView imageViewProjectIcon;
	public ImageView imageViewLeadAvatar;
    }

    public void addAll(ArrayList<Project> projects) {
	if (projects == null) {
	    return;
	}

	for (Project pr : projects) {
	    System.out.println("I got project: " + pr.getName());
	}

	this.projects.addAll(projects);
	notifyDataSetChanged();
    }
}
