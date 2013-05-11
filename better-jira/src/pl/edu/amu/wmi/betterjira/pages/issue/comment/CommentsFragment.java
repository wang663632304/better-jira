package pl.edu.amu.wmi.betterjira.pages.issue.comment;

import pl.edu.amu.wmi.betterjira.BetterJiraApplication;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.api.function.CommentFunction;
import pl.edu.amu.wmi.betterjira.api.function.data.Comment;
import pl.edu.amu.wmi.betterjira.api.function.data.CommentsList;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import pl.edu.amu.wmi.betterjira.pages.Page;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CommentsFragment extends Page implements OnItemClickListener,
	OnClickListener {
    private ListView listView;
    private CommentsAdapter adapter;
    private String string;
    private Button send;
    private EditText editText;
    private String comment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_comments, null);

	listView = (ListView) view.findViewById(R.id.listView);

	adapter = new CommentsAdapter(getActivity());
	listView.setAdapter(adapter);

	string = getActivity().getIntent().getExtras().getString("KEY");

	LoadComments loadTasks = new LoadComments();
	loadTasks.execute();

	listView.setOnItemClickListener(this);

	send = (Button) view.findViewById(R.id.buttonSend);
	send.setOnClickListener(this);

	editText = (EditText) view.findViewById(R.id.editTextComment);

	return view;
    }

    private class LoadComments extends AsyncTask<Void, Void, String> {

	private CommentsList allComments;

	@Override
	protected String doInBackground(Void... params) {

	    CommentFunction commentFunction = new CommentFunction(
		    BetterJiraApplication.getSession(), string);

	    try {
		allComments = commentFunction.getAllComments();
	    } catch (BadResponse e) {
		e.printStackTrace();
	    }

	    return null;
	}

	@Override
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    adapter.addAll(allComments.getComments());
	}
    }

    private class SendComment extends AsyncTask<Void, Void, String> {

	private Comment sendComment;

	@Override
	protected String doInBackground(Void... params) {

	    CommentFunction commentFunction = new CommentFunction(
		    BetterJiraApplication.getSession(), string);

	    try {
		sendComment = commentFunction.sendComment(comment);
	    } catch (BadResponse e) {
		e.printStackTrace();
	    }

	    return null;
	}

	@Override
	protected void onPostExecute(String result) {
	    super.onPostExecute(result);
	    adapter.add(sendComment);
	    editText.clearFocus();
	    editText.getText().clear();
	}
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
	adapter.getItem(index);
    }

    @Override
    public void onClick(View v) {

	comment = editText.getText().toString();
	SendComment sendComment = new SendComment();
	sendComment.execute();
    }

    @Override
    public String getTitle() {
	return "Comments";
    }

}
