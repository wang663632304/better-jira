package pl.edu.amu.wmi.betterjira;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.api.function.data.Comment;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class CommentsAdapter extends BaseAdapterSnippet {

    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private Context context;

    public CommentsAdapter(Context context) {
	this.context = context;
    }

    @Override
    public int getCount() {
	return comments.size();
    }

    @Override
    public Object getItem(int position) {
	return comments.get(position);
    }

    @Override
    public View getInflatedRow(int position) {
	// TODO refactor this create inflater in constructor
	return View.inflate(context, R.layout.row_comment, null);
    }

    @Override
    protected Object getNewHolder() {
	return new Holder();
    }

    @Override
    protected void initializeHolder(Object yourHolder, View view, int position) {

	Holder holder = (Holder) yourHolder;
	holder.textViewAuthor.setText(comments.get(position).getUpdateAuthor()
		.getDisplayName());
	holder.textViewBody.setText(comments.get(position).getBody());
	holder.textViewDate.setText(comments.get(position).getUpdated()
		.toLocaleString());

    }

    @Override
    protected void inflateHolder(Object yourHolder, View view) {

	Holder holder = (Holder) yourHolder;
	holder.textViewAuthor = (TextView) view
		.findViewById(R.id.textViewAuthor);
	holder.textViewBody = (TextView) view.findViewById(R.id.textViewBody);
	holder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
    }

    private class Holder {
	TextView textViewAuthor;
	TextView textViewBody;
	TextView textViewDate;
    }

    public boolean addAll(ArrayList<Comment> comments) {
	boolean addAll = this.comments.addAll(comments);
	notifyDataSetChanged();
	return addAll;
    }

    public boolean add(Comment comment) {
	boolean add = comments.add(comment);
	notifyDataSetChanged();
	return add;
    }
}