package com.james.chargescreen;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;

import com.alexandrepiveteau.library.tutorial.CustomAction;
import com.alexandrepiveteau.library.tutorial.TutorialActivity;
import com.alexandrepiveteau.library.tutorial.TutorialFragment;


public class IntroActivity extends TutorialActivity {

    private int[] BACKGROUND_COLORS = {
            Color.parseColor("#212121"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#607D8B")};
    @Override
    public String getIgnoreText() {
        return getResources().getString(R.string.tutorial_ignore);
    }

    @Override
    //change 1 more then last number
    public int getCount() {
        return 3;
    }

    @Override
    public int getBackgroundColor(int position) {
        return BACKGROUND_COLORS[position];
    }

    @Override
    public int getNavigationBarColor(int position) {
        return BACKGROUND_COLORS[position];
    }

    @Override
    public int getStatusBarColor(int position) {
        return BACKGROUND_COLORS[position];
    }

    @Override
    public TutorialFragment getTutorialFragmentFor(int position) {
        switch (position) {
            case 0:
                return new TutorialFragment.Builder()
                        .setTitle(getResources().getString(R.string.tutorial_screen_app_title))
                        .setDescription(getResources().getString(R.string.tutorial_screen_app_summary))
                        .setImageResource(R.mipmap.webpe)
                        .setImageResourceBackground(R.mipmap.ic_white_rect)
                        .build();
            case 1:
                return new TutorialFragment.Builder()
                        .setTitle(getResources().getString(R.string.tutorial_screen_hand_chosen_title))
                        .setDescription(getResources().getString(R.string.tutorial_screen_hand_chosen_summary))
                        .setImageResourceBackground(R.mipmap.prefs)
                        .build();
            case 2:
                return new TutorialFragment.Builder()
                        .setTitle(getResources().getString(R.string.tutorial_screen_search_title))
                        .setDescription(getResources().getString(R.string.tutorial_screen_search_summary))
                        .setImageResource(R.mipmap.ic_update)
                        .setImageResourceBackground(R.mipmap.ic_white_rect)
                        .setCustomAction(new CustomAction.Builder(Uri.parse("https://play.google.com/store/apps/details?id=com.james.chargescreen"))
                                .setIcon(R.mipmap.ic_play_store)
                                .build())
                        .build();
            default:
                return new TutorialFragment.Builder()
                        .setTitle("")
                        .setDescription("")
                        .setImageResource(R.mipmap.ic_launcher, false)
                        .build();
        }
    }

    @Override
    public boolean isNavigationBarColored() {
        return true;
    }

    @Override
    public boolean isStatusBarColored() {
        return true;
    }

    @Override
    public ViewPager.PageTransformer getPageTransformer() {
        return TutorialFragment.getParallaxPageTransformer(2.5f);
    }
}
