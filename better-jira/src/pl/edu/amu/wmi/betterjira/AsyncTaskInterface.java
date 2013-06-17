package pl.edu.amu.wmi.betterjira;

public interface AsyncTaskInterface {

    public void onPreExecute(AsyncTaskWorker asyncTaskWorker);

    /**
     * 
     * @param asyncTaskWorker
     *            async task on which you are working
     * @return resultCode
     */
    public int doInBackground(AsyncTaskWorker asyncTaskWorker);

    /**
     * 
     * @param resultCode
     *            in result is what return doInBackground
     */
    public void onPostExecute(int resultCode);

    public void onCancelled();

    public void onProgressUpdate();
}
