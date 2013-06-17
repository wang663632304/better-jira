package pl.edu.amu.wmi.betterjira;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class LazyLoadBitmap implements AsyncTaskInterface {

    private String bitmapName;
    private String alternativeDownloadLink;
    private ImageView imageView;
    private Bitmap downloadedBitmap;
    private Drawable defaultDrawable;
    private boolean isBitmapLoaded = false;
    private Context context;
    private int drawableLoadingId;

    public LazyLoadBitmap(Context context, ImageView imageView,
	    String bitmapName, String alternativeDownloadLink,
	    int drawableLoadingId) {
	this(context, imageView, bitmapName, alternativeDownloadLink,
		drawableLoadingId, -1);
    }

    public LazyLoadBitmap(Context context, ImageView imageView,
	    String bitmapName, String alternativeDownloadLink,
	    int drawableLoadingId, int defaultImageId) {

	this.context = context;
	this.drawableLoadingId = drawableLoadingId;
	this.imageView = imageView;
	this.bitmapName = bitmapName;
	this.alternativeDownloadLink = alternativeDownloadLink;

	if (defaultImageId > 0) {
	    defaultDrawable = context.getResources()
		    .getDrawable(defaultImageId);
	} else
	    defaultDrawable = imageView.getDrawable();
	imageView.setImageDrawable(defaultDrawable);
	imageView.setAnimation(null);
    }

    @Override
    public void onPreExecute(AsyncTaskWorker asyncTaskWorker) {

	if (isBitmapLoaded) {
	    asyncTaskWorker.cancel(true);
	    return;
	}

	imageView.setImageResource(drawableLoadingId);

	// Not working so good :(
	// RotateAnimation anim = new RotateAnimation(0f, 350f,
	// imageView.getWidth() * 0.5F, imageView.getHeight() * 0.5F);
	// anim.setInterpolator(new LinearInterpolator());
	// anim.setRepeatCount(Animation.INFINITE);
	// anim.setDuration(1200);
	// imageView.startAnimation(anim);
    }

    @Override
    public int doInBackground(AsyncTaskWorker asyncTaskWorker) {

	if (asyncTaskWorker.isCancelled() || isBitmapLoaded)
	    return -2;

	ImageDownloader downloader = new ImageDownloader();
	downloadedBitmap = downloader.download(alternativeDownloadLink);
	if (asyncTaskWorker.isCancelled() && downloadedBitmap != null) {
	    // downloadedBitmap.recycle();
	    downloadedBitmap = null;
	}
	return 0;
    }

    @Override
    public void onPostExecute(int resultCode) {

	if (resultCode == -2)
	    return;

	Log.d(this.toString(), "Finished downloading bitmap:"
		+ downloadedBitmap);
	imageView.setAnimation(null);
	if (downloadedBitmap == null) {
	    imageView.setImageDrawable(defaultDrawable);
	} else {
	    imageView.setImageBitmap(downloadedBitmap);
	}

    }

    @Override
    public void onCancelled() {
	Log.i(this.toString(), "Is canceled!");
	if (downloadedBitmap != null) {
	    // downloadedBitmap.recycle();
	    downloadedBitmap = null;
	}
    }

    @Override
    public void onProgressUpdate() {
    }

    public boolean isBitmapLoaded() {
	return isBitmapLoaded;
    }
}
