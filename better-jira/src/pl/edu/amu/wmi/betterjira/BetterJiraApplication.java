package pl.edu.amu.wmi.betterjira;

import pl.edu.amu.wmi.betterjira.api.function.data.Session;
import android.app.Application;

public class BetterJiraApplication extends Application {

    private static BetterJiraApplication INSTANCE;

    private static Session session;

    public static BetterJiraApplication getInstance() {
	return INSTANCE;
    }

    @Override
    public void onCreate() {
	super.onCreate();
	INSTANCE = this;
    }

    public static Session getSession() {
	return session;
    }

    public static void setSession(Session session) {
	BetterJiraApplication.session = session;
    }
}
