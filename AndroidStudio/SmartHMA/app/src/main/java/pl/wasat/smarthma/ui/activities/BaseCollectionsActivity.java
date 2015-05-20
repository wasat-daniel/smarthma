package pl.wasat.smarthma.ui.activities;

import android.content.Intent;

import pl.wasat.smarthma.R;
import pl.wasat.smarthma.helper.Const;
import pl.wasat.smarthma.model.FedeoRequestParams;
import pl.wasat.smarthma.model.iso.EntryISO;
import pl.wasat.smarthma.ui.frags.common.MetadataISOFragment;

/**
 * Created by Daniel on 2015-04-27.
 * This file is a part of SmartHMA project.
 */
public class BaseCollectionsActivity extends BaseSmartHMActivity {

    void loadIsoMetadataFragment(EntryISO displayedEntry) {
        MetadataISOFragment metadataISOFragment = MetadataISOFragment
                .newInstance(displayedEntry);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_base_details_container,
                        metadataISOFragment, "MetadataISOFragment")
                .addToBackStack("MetadataISOFragment").commit();
    }

    void startSearchingProductsProcess(FedeoRequestParams fedeoSearchProductsParams) {
        Intent showProductsIntent = new Intent(this,
                ProductsBrowserActivity.class);
        //showProductsIntent.putExtra(Const.KEY_INTENT_PARENT_ID, parentID);
        showProductsIntent.putExtra(Const.KEY_INTENT_FEDEO_REQUEST_PARAMS, fedeoSearchProductsParams);
        startActivityForResult(showProductsIntent, REQUEST_NEW_SEARCH);
    }
}
