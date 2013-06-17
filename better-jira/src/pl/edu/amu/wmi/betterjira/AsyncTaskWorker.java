package pl.edu.amu.wmi.betterjira;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncTaskWorker extends AsyncTask<Void, Void, Void> {

    private static final AsyncTaskContainer asyncTaskContainer = new AsyncTaskContainer();
    private AsyncTaskInterface asyncTaskInterface;
    private int resultCode = -1;
    private TaskOnPostExecute taskOnPostExecute;

    private AsyncTaskWorker(AsyncTaskInterface asyncTaskInterface,
	    TaskOnPostExecute taskOnPostExecute) {
	this.asyncTaskInterface = asyncTaskInterface;
	Log.i(this.toString(), ">>> AsyncTaskWorker -> object creating <<<");
	this.taskOnPostExecute = taskOnPostExecute;
    }

    public synchronized static AsyncTaskWorker create(
	    AsyncTaskInterface asyncTaskInterface) {
	return create(asyncTaskInterface, null);
    }

    public synchronized static AsyncTaskWorker create(
	    AsyncTaskInterface asyncTaskInterface,
	    TaskOnPostExecute taskOnPostExecute) {
	return new AsyncTaskWorker(asyncTaskInterface, taskOnPostExecute);
    }

    /**
     * 
     * @param asyncTaskInterface
     * @return already runned task
     */
    public synchronized static AsyncTaskWorker createAndRun(Context context,
	    AsyncTaskInterface asyncTaskInterface) {
	return createAndRun(context, asyncTaskInterface, null);
    }

    /**
     * 
     * @param asyncTaskInterface
     * @param taskOnPostExecute
     * @return already runned task
     */
    public synchronized static AsyncTaskWorker createAndRun(Context context,
	    AsyncTaskInterface asyncTaskInterface,
	    TaskOnPostExecute taskOnPostExecute) {
	return new AsyncTaskWorker(asyncTaskInterface, taskOnPostExecute)
		.run(context);
    }

    @Override
    protected void onPreExecute() {
	super.onPreExecute();
	if (!asyncTaskContainer.contains(this))
	    throw new RuntimeException("Use run() method not execute!");
	asyncTaskInterface.onPreExecute(this);

    }

    @Override
    protected Void doInBackground(Void... params) {
	if (isCancelled()) {
	    Log.i(this.toString(),
		    ">>> AsyncTaskWorker -> doInBackground task is canceled <<<");
	    resultCode = -1;
	    return null;
	}
	Log.i(this.toString(), ">>> AsyncTaskWorker -> doInBackground <<<");
	resultCode = asyncTaskInterface.doInBackground(this);
	return null;
    }

    protected void onPostExecute(Void result) {
	Log.i(this.toString(), ">>> AsyncTaskWorker -> onPostExecute <<<");
	asyncTaskInterface.onPostExecute(resultCode);
	if (taskOnPostExecute != null)
	    taskOnPostExecute.onExecute();

	nextTesk();
    };

    private synchronized void nextTesk() {
	if (asyncTaskContainer.remove(this)) {
	    System.out.println("usunałem taska z kolejki: "
		    + asyncTaskInterface);
	}

	boolean isAnyRunning = asyncTaskContainer.isAnyRunning();

	if (asyncTaskContainer.isEmpty() == false && isAnyRunning == false) {
	    Log.d(this.toString(), "Starting another async");
	    asyncTaskContainer.get(0).execute();
	}
    }

    @Override
    protected void onCancelled() {
	super.onCancelled();
	Log.i(this.toString(), ">>> AsyncTaskWorker -> onCancel<<<");
	Log.i(this.toString(), ">>> canceling "
		+ asyncTaskInterface.getClass().getSimpleName());
	asyncTaskInterface.onCancelled();
	nextTesk();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
	super.onProgressUpdate(values);
	asyncTaskInterface.onProgressUpdate();
    }

    public synchronized AsyncTaskWorker run(Context context) {
	if (getStatus() != Status.RUNNING) {
	    asyncTaskContainer.add(this, context);

	    if (asyncTaskContainer.isAnyRunning())
		return this;
	    execute();
	} else {
	    Log.e(this.toString(), "Sorry you must wait untill it finished");
	}
	return this;
    }

    public static void showMessage(String message) {
	Toast.makeText(BetterJiraApplication.getInstance(), message,
		Toast.LENGTH_SHORT).show();
    }

    @Override
    public String toString() {
	return asyncTaskInterface.getClass().getSimpleName();
    }

    public static synchronized void cancelTaskForActivity(Context context) {
	asyncTaskContainer.cancelTaskForActivity(context);
    }
}
