
package com.example.androidtest;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements TabContentFactory {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final TabHost tabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);

            tabHost.setup();

            tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1")
                    .setContent(PlaceholderFragment.this));
            tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2")
                    .setContent(PlaceholderFragment.this));
            tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3")
                    .setContent(PlaceholderFragment.this));

            Button btn1 = (Button) rootView.findViewById(R.id.add_tab);
            btn1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3")
                            .setContent(PlaceholderFragment.this));

                }
            });
            Button btn2 = (Button) rootView.findViewById(R.id.remove_tab);

            btn2.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    int position = tabHost.getCurrentTab();
                    tabHost.setCurrentTab(0);
                    int count = tabHost.getTabWidget().getChildCount();
                    if (count > 1) {
                        if (count - 1 == position) {
                            position = 0;
                        }
                        tabHost.getTabWidget().removeViewAt(count - 1);
                        int count1 = tabHost.getTabWidget().getTabCount();
                        int count2 = tabHost.getTabWidget().getChildCount();
                        int count3 = tabHost.getTabContentView().getChildCount();
                        tabHost.clearAllTabs();
                        tabHost.setCurrentTab(position);
                    }

                }
            });

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public View createTabContent(String tag) {
            final TextView tv = new TextView(getActivity());
            tv.setText("Content for tab with tag " + tag);
            return tv;
        }
    }

}
