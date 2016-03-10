package pl.wasat.smarthma.ui.activities.base;

import android.support.v4.app.FragmentManager;

import pl.wasat.smarthma.kindle.AmznAreaPickerMapFragment.OnAmznAreaPickerMapFragmentListener;
import pl.wasat.smarthma.model.FedeoRequestParams;
import pl.wasat.smarthma.model.iso.EntryISO;
import pl.wasat.smarthma.ui.frags.browse.DataSeriesListFragment;
import pl.wasat.smarthma.ui.frags.common.AreaPickerMapFragment.OnAreaPickerMapFragmentListener;
import pl.wasat.smarthma.ui.frags.common.CollectionDetailsFragment;
import pl.wasat.smarthma.ui.frags.common.CollectionDetailsFragment.OnCollectionDetailsFragmentListener;
import pl.wasat.smarthma.ui.frags.search.SearchListFragmentBase;
import pl.wasat.smarthma.utils.obj.LatLngBoundsExt;
import pl.wasat.smarthma.utils.obj.LatLngExt;

/**
 * Activity used to browse and manage saved collections.
 */
public abstract class ExtendedBaseCollectionsActivity extends BaseCollectionsActivity implements
        DataSeriesListFragment.OnDataSeriesListFragmentListener,
        OnAreaPickerMapFragmentListener, OnAmznAreaPickerMapFragmentListener, SearchListFragmentBase.OnSearchListFragmentListener,
        OnCollectionDetailsFragmentListener {

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        if (dismissMenuOnBackPressed()) return;
        FragmentManager fm = getSupportFragmentManager();
        int bsec = fm.getBackStackEntryCount();
        if (bsec > 1) {
            while (bsec > 1) {
                fm.popBackStackImmediate();
                bsec = fm.getBackStackEntryCount();
            }
        } else {
            finish();
            super.onBackPressed();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see pl.wasat.smarthma.ui.fragments.SearchListFragment.
     * OnSearchListFragmentListener
     * #onSearchListFragmentItemSelected(java.lang.String)
     */
    @Override
    public void onSearchListFragmentItemSelected(String id) {
    }

    @Override
    public void onMapFragmentBoundsChange(LatLngBoundsExt bounds) {
        callUpdateDetailFrag(bounds);
    }

    @Override
    public void onAmznMapFragmentBoundsChange(LatLngBoundsExt bounds) {
        callUpdateDetailFrag(bounds);
    }

    @Override
    public void onAmznMapFragmentAreaInputChange(int areaType) {
        changeAreaInputView(areaType);
    }

    @Override
    public void onAmznMapFragmentPointAndRadiusSend(LatLngExt center, float radius) {
        updatePointAndRadiusValues(center, radius);
    }

    @Override
    public void onMapFragmentAreaInputChange(int areaType) {
        changeAreaInputView(areaType);
    }

    @Override
    public void onMapFragmentPointAndRadiusSend(LatLngExt center, float radius) {
        updatePointAndRadiusValues(center, radius);
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * pl.wasat.smarthma.ui.frags.search.SearchResultCollectionDetailsFragment
     * .OnSearchResultCollectionDetailsFragmentListener
     * #onSearchResultCollectionDetailsFragmentShowProducts
     * (pl.wasat.smarthma.model.FedeoRequest)
     */
    @Override
    public void onCollectionDetailsFragmentShowProducts(FedeoRequestParams fedeoSearchProductsParams) {
        startSearchingProductsProcess(fedeoSearchProductsParams);
    }

    @Override
    public void onCollectionDetailsFragmentShowMetadata(EntryISO displayedEntry) {
        loadIsoMetadataFragment(displayedEntry);
    }

    @Override
    public void onDataSeriesFragmentItemSelected(String id) {
    }

    private void callUpdateDetailFrag(LatLngBoundsExt bounds) {
        CollectionDetailsFragment collectionDetailsFragment = (CollectionDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(CollectionDetailsFragment.class.getSimpleName());

        if (collectionDetailsFragment != null) {
            collectionDetailsFragment.updateAreaBounds(bounds);
        }
    }

    private void changeAreaInputView(int areaType) {
        CollectionDetailsFragment collectionDetailsFragment = (CollectionDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(CollectionDetailsFragment.class.getSimpleName());
        if (collectionDetailsFragment != null) {
            collectionDetailsFragment.loadProperAreaView(areaType);
        }
    }


    private void updatePointAndRadiusValues(LatLngExt center, float radius) {
        CollectionDetailsFragment collectionDetailsFragment = (CollectionDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(CollectionDetailsFragment.class.getSimpleName());
        if (collectionDetailsFragment != null) {
            collectionDetailsFragment.updateAreaPtAndRadius(center, radius);
        }
    }
}
