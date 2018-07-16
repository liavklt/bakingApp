package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by lianavklt on 16/07/2018.
 */

public class NetworkUtils {

  private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
  private final static int CONNECT_TIMEOUT = 5000;
  private final static int READ_TIMEOUT = 10000;

  public static boolean isConnected(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = null;
    if (cm != null) {
      activeNetwork = cm.getActiveNetworkInfo();
    }
    return activeNetwork != null &&
        activeNetwork.isConnectedOrConnecting();

  }

  public static URL buildUrl(String param) {
    Uri builtUri = Uri.parse(param);
    URL url = null;
    try {
      url = new URL(builtUri.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    Log.v(LOG_TAG, "Built URI " + url);

    return url;
  }

  public static String getResponseFromHttpUrl(URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
      urlConnection.setReadTimeout(READ_TIMEOUT);
      InputStream in = urlConnection.getInputStream();

      Scanner scanner = new Scanner(in);
      scanner.useDelimiter("\\A");

      boolean hasInput = scanner.hasNext();
      if (hasInput) {
        return scanner.next();
      } else {
        return null;
      }
    } finally {
      urlConnection.disconnect();
    }
  }
}
