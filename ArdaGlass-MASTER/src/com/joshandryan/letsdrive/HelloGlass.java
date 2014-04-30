
package com.joshandryan.letsdrive;

// Glass related setup 
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

import android.os.IBinder;
import android.app.Service;
import android.content.Intent;

public class HelloGlass extends Service {
	
	private static final String LIVE_CARD_ID = "helloglass";
	
	// Creates a Timeline manager needed to put the app on the timeline 
	private TimelineManager mTimelineManager;
	
	// lets you create a live card and put it on the timeline with the app information on it 
	@SuppressWarnings("unused")
	private LiveCard mLiveCard;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mTimelineManager = TimelineManager.from(this);
	} // onCreate

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	} 
	
	// used to start when a programmed voice trigger is made you can alter the voice method in the voice trigger.xml
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		//instantiates the card established above 
		mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_ID);
		Intent i = new Intent(this, Magic.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		return START_STICKY;
	} 

}
