package pl.wasat.smarthma.ui.frags.browse;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.model.FedeoRequest;
import pl.wasat.smarthma.model.feed.Feed;
import pl.wasat.smarthma.ui.frags.search.SearchListFragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link BrowseDataSeriesIntroFragment.OnBrowseDataSeriesIntroFragmentListener}
 * interface to handle interaction events. Use the
 * {@link BrowseDataSeriesIntroFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class BrowseDataSeriesIntroFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String KEY_DATA_SERIES_FEED = "pl.wasat.smarthma.KEY_DATA_SERIES_FEED";

	// TODO: Rename and change types of parameters
	private Feed searchFeed;
	private TextView tvTitle;
	private TextView tvSearchTerms;
	private TextView tvCollection;
	private TextView tvTimeStart;
	private TextView tvTimeEnd;
	private TextView tvUpdated;
	private TextView tvTotal;

	private Button btnFirst;
	private Button btnPrev;
	private Button btnReload;
	private Button btnNext;
	private Button btnLast;

	private OnBrowseDataSeriesIntroFragmentListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param paramSearchFeeds
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment SearchDataSeriesIntroFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static BrowseDataSeriesIntroFragment newInstance(Feed paramSearchFeed) {
		BrowseDataSeriesIntroFragment fragment = new BrowseDataSeriesIntroFragment();
		Bundle args = new Bundle();
		args.putSerializable(KEY_DATA_SERIES_FEED,
                paramSearchFeed);
		fragment.setArguments(args);
		return fragment;
	}

	public BrowseDataSeriesIntroFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			searchFeed = (Feed) getArguments().getSerializable(
					KEY_DATA_SERIES_FEED);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(
				R.layout.fragment_search_data_series_intro, container, false);

		tvTitle = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_title);
		tvSearchTerms = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_search_terms_value);
		tvCollection = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_collection_value);
		tvTimeStart = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_time_start_value);
		tvTimeEnd = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_time_end_value);
		tvUpdated = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_updated_value);
		tvTotal = (TextView) rootView
				.findViewById(R.id.search_frag_ds_intro_total_value);

		btnFirst = (Button) rootView
				.findViewById(R.id.search_frag_ds_intro_button_first);
		btnPrev = (Button) rootView
				.findViewById(R.id.search_frag_ds_intro_button_prev);
		btnReload = (Button) rootView
				.findViewById(R.id.search_frag_ds_intro_button_reload);
		btnNext = (Button) rootView
				.findViewById(R.id.search_frag_ds_intro_button_next);
		btnLast = (Button) rootView
				.findViewById(R.id.search_frag_ds_intro_button_last);

		initUITexts();
		initUIButtons();

		return rootView;
	}

	/**
	 * 
	 */
	private void initUITexts() {
		tvTitle.setText(searchFeed.getTitle());
		tvSearchTerms.setText(searchFeed.getQuery().get_searchTerms());
		tvCollection.setText(searchFeed.getQuery().get_dc_subject());
		tvTimeStart.setText(searchFeed.getQuery().get_time_start());
		tvTimeEnd.setText(searchFeed.getQuery().get_time_end());
		tvUpdated.setText(searchFeed.getUpdated());
		tvTotal.setText(searchFeed.getTotalResults().get__text());
	}

	private void initUIButtons() {
		int linkSize = searchFeed.getLink().size();

		for (int i = 0; i < linkSize; i++) {
			String linkRel = searchFeed.getLink().get(i).get_rel();
			final int incFinal = i;

			if (linkRel.equalsIgnoreCase("first")) {
				btnFirst.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String linkHref = searchFeed.getLink().get(incFinal)
								.get_href();
						loadNavSearch(linkHref);
					}
				});
			} else if (linkRel.equalsIgnoreCase("previous")) {
				btnPrev.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String linkHref = searchFeed.getLink().get(incFinal)
								.get_href();
						loadNavSearch(linkHref);
					}
				});
			} else if (linkRel.equalsIgnoreCase("self")) {
				btnReload.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String linkHref = searchFeed.getLink().get(incFinal)
								.get_href();
						loadNavSearch(linkHref);
					}
				});
			} else if (linkRel.equalsIgnoreCase("next")) {
				btnNext.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String linkHref = searchFeed.getLink().get(incFinal)
								.get_href();
						loadNavSearch(linkHref);
					}
				});
			} else if (linkRel.equalsIgnoreCase("last")) {
				btnLast.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						String linkHref = searchFeed.getLink().get(incFinal)
								.get_href();
						loadNavSearch(linkHref);
					}
				});
			}
		}
	}

	private void loadNavSearch(String linkHref) {
		FedeoRequest request = new FedeoRequest();
		request.setUrl(linkHref);
		
		SearchListFragment searchListFragment = SearchListFragment
				.newInstance(request);
		getActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.search_results_list_container, searchListFragment)
				.commit();
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed() {
		if (mListener != null) {
			mListener.onBrowseDataSeriesIntroFragmentInteraction();
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnBrowseDataSeriesIntroFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnBrowseDataSeriesIntroFragmentListener {
		// TODO: Update argument type and name
		public void onBrowseDataSeriesIntroFragmentInteraction();
	}

}
