package pl.edu.amu.wmi.betterjira;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask.Status;

final class AsyncTaskContainer {

    private static final ArrayList<AsyncTaskWorker> asyncs = new ArrayList<AsyncTaskWorker>(
	    8);
    private static final ArrayList<WeakReference<Context>> contexts = new ArrayList<WeakReference<Context>>(
	    8);

    public boolean contains(AsyncTaskWorker asyncTaskWorker) {
	return asyncs.contains(asyncTaskWorker);
    }

    public boolean remove(AsyncTaskWorker asyncTaskWorker) {

	for (int i = 0; i < asyncs.size(); ++i) {
	    if (asyncs.get(i) == asyncTaskWorker) {
		asyncs.remove(i);
		contexts.remove(i);
		return true;
	    }
	}

	return false;
    }

    public boolean isEmpty() {
	return asyncs.isEmpty();
    }

    public AsyncTaskWorker get(int index) {
	return asyncs.get(index);
    }

    public void add(AsyncTaskWorker asyncTaskWorker, Context context) {
	asyncs.add(asyncTaskWorker);
	contexts.add(new WeakReference<Context>(context));
    }

    private String getAllTasksNames() {
	StringBuilder stringBuilder = new StringBuilder(100);
	for (AsyncTaskWorker task : asyncs) {
	    stringBuilder.append(task.toString());
	    stringBuilder.append("  , ");
	}
	return stringBuilder.toString();
    }

    public boolean isAnyRunning() {
	for (AsyncTaskWorker task : asyncs) {
	    if (task.getStatus() == Status.RUNNING) {
		System.out.println("Jakiś task działa : " + task);
		return true;
	    }
	}
	return false;
    }

    public void cancelTaskForActivity(Context context) {

	Context cachedContext;
	for (int i = 0; i < contexts.size(); ++i) {
	    cachedContext = contexts.get(i).get();
	    if (cachedContext != null && cachedContext == context) {
		System.out.println("Stopuje taska " + asyncs.get(i));
		contexts.remove(i);
		asyncs.get(i).cancel(true);
		asyncs.remove(i);
		--i;
	    }
	}
    }
}
