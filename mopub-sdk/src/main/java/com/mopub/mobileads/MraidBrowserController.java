package com.mopub.mobileads;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

class MraidBrowserController extends MraidAbstractController {
    private static final String LOGTAG = "MraidBrowserController";
    
    MraidBrowserController(MraidView view) {
        super(view);
    }
    
    protected void open(String url) {
        Log.d(LOGTAG, "Opening in-app browser: " + url);
        
        MraidView view = getMraidView();
        if (view.getOnOpenListener() != null) {
            view.getOnOpenListener().onOpen(view);
        }
        
        Context context = getMraidView().getContext();
        Intent i = new Intent(context, MraidBrowser.class);
        i.putExtra(MraidBrowser.URL_EXTRA, url);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(i);
        }
        catch (ActivityNotFoundException e) {
            try {
                i = new Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
            catch (ActivityNotFoundException e2) {
                Log.w("MoPub", "Could not handle intent action: " + i.getAction()
                        + ". URL: " + url
                        + " Could it be that there is no browser installed?");
            }
        }
    }
}
