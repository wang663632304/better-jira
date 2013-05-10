package pl.edu.amu.wmi.betterjira;

import pl.edu.amu.wmi.betterjira.api.function.CommentFunction;
import pl.edu.amu.wmi.betterjira.api.function.data.Comment;
import pl.edu.amu.wmi.betterjira.api.function.data.CommentsList;
import pl.edu.amu.wmi.betterjira.api.function.exception.BadResponse;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TaskComments extends Activity implements OnItemClickListener,
	OnClickListener {
    private ListView listView;
    private CommentsAdapter adapter;
    private String string;
    private Button send;
    private EditText editText;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.page_comments);

	listView = (ListView) findViewById(R.id.listView);

	adapter = new CommentsAdapter(this);
	listView.setAdapter(adapter);

	string = getIntent().getExtras().getString("KEY");

	LoadComments loadTasks = new LoadComments();
	loadTasks.execute();

	listView.setOnItemClickListener(this);

	send = (Button) findViewById(R.id.buttonSend);
	send.setOnClickListener(this);

	editText = (EditText) findViewById(R.id.editTextComment);
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

}
