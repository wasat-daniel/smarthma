/*
 * Copyright (c) 2016.  SmartHMA ESA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.wasat.smarthma.ui.frags.missions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.model.mission.MissionItemData;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link MissionsDetailsFragment.OnMissionsDetailNewFragmentListener} interface
 * to handle interaction events. Use the
 * {@link MissionsDetailsFragment#newInstance} factory method to create an
 * instance of this fragment.
 */
public class MissionsDetailsFragment extends Fragment {

    private static final String ARG_MISSION_DATA = "pl.wasat.smarthma.ARG_MISSION_DATA";

    private MissionItemData missionData;

    private OnMissionsDetailNewFragmentListener mListener;

    /**
     * Instantiates a new Missions details fragment.
     */
    public MissionsDetailsFragment() {
        setHasOptionsMenu(true);
    }

    /**
     * Use this factory method to create a new instance of this fragment using
     * the provided parameters.
     *
     * @param missionData the mission data
     * @return A new instance of fragment DataSeriesDetailFragment.
     */
    public static MissionsDetailsFragment newInstance(
            MissionItemData missionData) {
        MissionsDetailsFragment fragment = new MissionsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MISSION_DATA, missionData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            mListener = (OnMissionsDetailNewFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMissionsDetailFragmentListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            missionData = (MissionItemData) getArguments().getSerializable(
                    ARG_MISSION_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_missions_new_detail,
                container, false);

        TextView tvTitle = (TextView) rootView
                .findViewById(R.id.missions_detail_frag_title_tv);
        final String missionName = missionData.getName();
        tvTitle.setText(missionName);

        WebView detailWebView = (WebView) rootView
                .findViewById(R.id.missions_detail_frag_content_web);
        detailWebView.setBackgroundColor(Color.TRANSPARENT);
        detailWebView.loadData(missionData.getSummary(), "text/html", "UTF-8");

        Button btnSearchData = (Button) rootView
                .findViewById(R.id.missions_detail_frag_search_data_btn);
        btnSearchData.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mListener.onMissionsDetailNewFragmentSearchData(missionName);
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_twopane, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionbar_saveoffline) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "This metadata has been saved offline.", Toast.LENGTH_LONG)
                    .show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated to
     * the activity and potentially other fragments contained in that activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMissionsDetailNewFragmentListener {
        /**
         * On missions detail new fragment search data.
         *
         * @param missionName the mission name
         */
        void onMissionsDetailNewFragmentSearchData(String missionName);
    }

}
