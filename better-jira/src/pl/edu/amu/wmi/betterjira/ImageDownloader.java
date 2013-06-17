package pl.edu.amu.wmi.betterjira;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import pl.edu.amu.wmi.betterjira.api.ServerConnector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

public class ImageDownloader {

    private Options options = new Options();

    public ImageDownloader() {
	options.inScaled = false;
    }

    public Bitmap download(String imageUrl, int sampleSize) {
	options.inSampleSize = sampleSize;
	try {
	    URL url = new URL(imageUrl);

	    Log.d(this.toString(), "Downloading image: " + imageUrl);

	    InputStream inputStream = ServerConnector.getImageFromServer(
		    imageUrl, BetterJiraApplication.getSession());

	    Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
		    options);

	    if (bitmap == null)
		Log.e(this.toString(), "I can't download image?!");
	    else
		Log.d(this.toString(), "I have image!");
	    return bitmap;

	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (IllegalStateException e) {
	    e.printStackTrace();
	} catch (URISyntaxException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public Bitmap download(String imageUrl) {
	return download(imageUrl, 1);
    }
}
