package pl.wasat.smarthma.parser.missions.EsaEuemsat;

import android.content.Context;

import java.util.ArrayList;

import pl.wasat.smarthma.parser.Parser.BaseParser;
import pl.wasat.smarthma.parser.Parser.Pair;
import pl.wasat.smarthma.parser.missions.SimpleMissionInterface;
import pl.wasat.smarthma.parser.model.Mission;
import pl.wasat.smarthma.parser.model.Page;

/**
 * Created by marcel on 2015-08-13 00:09.
 * Part of the project  SmartHMA
 */
public class MetOp extends BaseParser implements SimpleMissionInterface {
    private final static int MISSION_ID = 59;
    private final static String TITLE = "MetOp";

    public MetOp(String pageUrl, Context context) {
        super(pageUrl, context);
        parserDb.addMission(new Mission(MISSION_ID, EsaEumetsat.CATEGORY_ID, TITLE));

    }

    @Override
    public void mainContent() {
        int ITEMS_COUNT = 2;
        super.getComplexPage(ITEMS_COUNT);
        ArrayList<Pair> list = super.getComplexPage(ITEMS_COUNT);
        for (Pair item : list) {
            parserDb.addPage(new Page(EsaEumetsat.CATEGORY_ID, MISSION_ID, (String) item.title, (String) item.content));
        }
    }
}
