package pl.wasat.smarthma.kindle;

import android.content.Context;
import android.content.Intent;

import org.acra.ACRA;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import pl.wasat.smarthma.helper.Const;

class AmznTileProviderFactory {

    private static final String URL_BASE_OSM = "http://a.tile.openstreetmap.org/{z}/{x}/{y}.png";

    public static AmznWMSTileProvider getWmsTileProvider(final String WMS_name,
                                                         final Context context) {
        return new AmznWMSTileProvider(256, 256) {

            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, WMS_name, bbox[MINX],
                        bbox[MINY], bbox[MAXX], bbox[MAXY]);
                URL url;
                try {
                    url = new URL(s);
                    sendWmsLoadState(context);
                } catch (MalformedURLException e) {
                    ACRA.getErrorReporter().handleSilentException(e);
                    throw new AssertionError(e);
                }
                return url;
            }
        };
    }

    private static void sendWmsLoadState(Context con) {
        Intent intent = new Intent(
                Const.KEY_MAP_SPINNER_INTENTFILTER_NOTIFICATION);
        intent.putExtra(Const.KEY_MAP_WMS_LOAD_STATE, true);
        con.sendBroadcast(intent);
    }

    public static AmznWMSTileProvider getOsmWmsTileProvider(final String WMS_name) {

        return new AmznWMSTileProvider(256, 256) {

            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, WMS_name, bbox[MINX],
                        bbox[MINY], bbox[MAXX], bbox[MAXY]);
                URL url;
                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    ACRA.getErrorReporter().handleSilentException(e);
                    throw new AssertionError(e);
                }
                return url;
            }
        };
    }

    public static AmznWMSTileProvider getOSMTileProvider() {

        return new AmznWMSTileProvider(256, 256) {

            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                try {
                    return new URL(URL_BASE_OSM.replace("{z}", "" + zoom)
                            .replace("{x}", "" + x).replace("{y}", "" + y));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    ACRA.getErrorReporter().handleSilentException(e);
                }
                return null;
            }
        };

    }
}

