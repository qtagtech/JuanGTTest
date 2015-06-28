package com.juandavidarroyave.android.juangttest;

import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;

import com.juandavidarroyave.android.juangttest.MainActivity;
import com.robotium.solo.Solo;


public class AppBasicTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public AppBasicTest() {
        super(MainActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testActivity() {
        // robotium assert
        solo.assertCurrentActivity("Main Screen", MainActivity.class);
        solo.searchButton("Play Now!", true);
        solo.clickOnButton("Play Now!");
        View image = solo.getView(R.id.img_thumbnail);
        solo.clickOnView(image);
        solo.waitForFragmentByTag("pop_up_window", 1000);
        solo.searchButton("Close", true);
        solo.clickOnButton("Close");
        solo.assertMemoryNotLow();


        // junit assert
        assertEquals(true, true);
    }
}
