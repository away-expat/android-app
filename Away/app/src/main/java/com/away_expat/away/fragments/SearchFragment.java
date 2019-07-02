package com.away_expat.away.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.away_expat.away.R;
import com.away_expat.away.adapters.CustomPageAdapter;

public class SearchFragment extends Fragment {

    private EditText searchET;
    private CustomPageAdapter customPagerAdapter;
    private int currentPos = 0;
    private String currentSearch = null;
    private boolean onPause = false;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.activity_main_viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.activity_main_tabs);
        searchET = (EditText) view.findViewById(R.id.search_input);

        setupElements();

        return view;
    }

    private void setupElements() {
        customPagerAdapter = new CustomPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(customPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            switch (i) {
                case 0:
                    tabLayout.getTabAt(i).setIcon(R.drawable.location);
                    int tabIconColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
                    tabLayout.getTabAt(i).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    break;
                case 1:
                    tabLayout.getTabAt(i).setIcon(R.drawable.event);
                    break;
                case 2:
                    tabLayout.getTabAt(i).setIcon(R.drawable.tag);
                    break;
                case 3:
                    tabLayout.getTabAt(i).setIcon(R.drawable.people);
                    break;
                default:
                    break;
            }
        }

        tabLayout.addOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                        currentPos = tab.getPosition();

                        if (currentSearch != null && !onPause) {
                            customPagerAdapter.updateList(currentPos, currentSearch, getActivity());
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getContext(), R.color.black);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

        searchET.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence c, int start, int before, int count) {
                if (c.toString().length() >= 3 && !onPause) {
                    currentSearch = c.toString();
                    customPagerAdapter.updateList(currentPos, c.toString(), getActivity());
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {}

            public void afterTextChanged(Editable c) {}
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        onPause = false;
    }
}
