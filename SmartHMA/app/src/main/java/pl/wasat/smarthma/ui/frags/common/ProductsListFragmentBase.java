package pl.wasat.smarthma.ui.frags.common;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.model.entry.Entry;
import pl.wasat.smarthma.model.feed.Feed;
import pl.wasat.smarthma.ui.frags.base.BaseShowProductsListFragment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Use the
 * {@link ProductsListFragmentBase#newInstance} factory method to create an
 * instance of this fragment.
 */
public class ProductsListFragmentBase extends BaseShowProductsListFragment {
    //protected static final String KEY_PARAM_FEDEO_REQUEST = "pl.wasat.smarthma.KEY_PARAM_FEDEO_REQUEST";

    public ProductsListFragmentBase() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of this fragment using
     * the provided parameters.
     *
     * @return A new instance of fragment SearchProductsFeedsFragment.
     */
    public static ProductsListFragmentBase newInstance() {
        return new ProductsListFragmentBase();
    }

    /*
     * (non-Javadoc)
     *
     * @see pl.wasat.smarthma.ui.frags.base.BaseShowProductsListFragment#
     * loadSearchResultProductsIntroDetailsFrag
     * (pl.wasat.smarthma.model.feed.Feed)
     */
    @Override
    public void loadSearchResultProductsIntroDetailsFrag(Feed searchProductFeeds) {
        FeedSummaryProductsFragment feedSummaryProductsFragment = FeedSummaryProductsFragment
                .newInstance(searchProductFeeds);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.activity_base_details_container,
                        feedSummaryProductsFragment, FeedSummaryProductsFragment.class.getSimpleName())
                .addToBackStack(FeedSummaryProductsFragment.class.getSimpleName())
                .commit();
        super.loadSearchResultProductsIntroDetailsFrag(searchProductFeeds);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * pl.wasat.smarthma.ui.frags.base.BaseShowProductsListFragment#loadFailureFrag
     * ()
     */
    @Override
    protected void loadFailureFrag() {
        String searchFail = getActivity().getString(
                R.string.nothing_to_display_please_search_again_);
        FailureFragment failureFragment = FailureFragment
                .newInstance(searchFail);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_base_list_container, failureFragment)
                .commit();
        super.loadFailureFrag();
    }

    /*
     * (non-Javadoc)
     *
     * @see pl.wasat.smarthma.ui.frags.base.BaseShowProductsListFragment#
     * loadProductItemDetails(pl.wasat.smarthma.model.eo.Entry)
     */
    @Override
    protected void loadProductItemDetails(Entry entry) {
        ProductDetailsFragment productDetailsFragment = ProductDetailsFragment
                .newInstance(entry);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_base_details_container,
                        productDetailsFragment,
                        ProductDetailsFragment.class.getSimpleName())
                .addToBackStack(ProductDetailsFragment.class.getSimpleName())
                .commit();

        super.loadProductItemDetails(entry);
    }
}
