package pl.edu.amu.wmi.betterjira.pages.project;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.api.function.GetAllProjects;
import pl.edu.amu.wmi.betterjira.api.function.data.Project;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.pages.Page;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProjectsFragment extends Page {

    private ProjectsAdapter adapter;
    private ListView listView;

    @Override
    public String getTitle() {
	return "Projects";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	listView = new ListView(getActivity());
	adapter = new ProjectsAdapter(getActivity());
	listView.setAdapter(adapter);

	LoadProjects loadProjects = new LoadProjects();
	loadProjects.execute();

	return listView;
    }

    private class LoadProjects extends AsyncTask<Void, Void, Void> {

	private ArrayList<Project> projects;

	@Override
	protected Void doInBackground(Void... params) {

	    GetAllProjects getAllProjects = new GetAllProjects(
		    BetterJiraApplication.getSession());
	    try {
		projects = getAllProjects.getAllVisibleProjects();
	    } catch (BadResponse e) {
		e.printStackTrace();
	    }

	    return null;
	}

	@Override
	protected void onPostExecute(Void result) {
	    adapter.addAll(projects);
	}
    }
}
