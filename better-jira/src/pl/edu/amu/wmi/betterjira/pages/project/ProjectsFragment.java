package pl.edu.amu.wmi.betterjira.pages.project;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.PageIndicatorActivity;
import pl.edu.amu.wmi.betterjira.api.function.data.Filter;
import pl.edu.amu.wmi.betterjira.api.function.data.Project;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.EmptyResponse;
import pl.edu.amu.wmi.betterjira.api.function.exception.NoStatusLine;
import pl.edu.amu.wmi.betterjira.api.function.exception.NotFoundException;
import pl.edu.amu.wmi.betterjira.api.function.project.GetAllProjects;
import pl.edu.amu.wmi.betterjira.api.function.project.GetProject;
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

public class ProjectsFragment extends Page implements OnItemClickListener {

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
	listView.setOnItemClickListener(this);

	LoadProjects loadProjects = new LoadProjects();
	loadProjects.execute();

	return listView;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {

	Project project = (Project) adapter.getItem(index);

	Intent intent = new Intent(getActivity(), PageIndicatorActivity.class);
	intent.putExtra(IssueListFragment.EXTRA_JQL,
		"project=" + project.getKey());
	intent.putExtra(PageIndicatorActivity.EXTRA_TREE_NODE, getClass()
		.getSimpleName());
	startActivity(intent);
    }

    private class LoadProjects extends AsyncTask<Void, Void, Void> {

	private ArrayList<Project> projects;

	@Override
	protected Void doInBackground(Void... params) {

	    GetAllProjects getAllProjects = new GetAllProjects(
		    BetterJiraApplication.getSession());
	    GetProject getProject = new GetProject(
		    BetterJiraApplication.getSession());

	    try {
		projects = getAllProjects.getAllVisibleProjects();

		for (Project project : projects) {
		    getProject.updateProject(project);
		}

	    } catch (BadResponse e) {
		e.printStackTrace();
	    } catch (ClientProtocolException e) {
		e.printStackTrace();
	    } catch (IllegalStateException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (NoStatusLine e) {
		e.printStackTrace();
	    } catch (EmptyResponse e) {
		e.printStackTrace();
	    } catch (JSONException e) {
		e.printStackTrace();
	    } catch (NotFoundException e) {
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
