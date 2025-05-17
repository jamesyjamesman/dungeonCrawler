package Main.Initialization;

import Main.Item.CurseRelic;
import Main.Item.ForesightRelic;
import Main.Item.RegenerationRelic;
import Main.Item.Relic;

import java.util.ArrayList;

public class RelicInit {
    static ArrayList<Relic> relicList = new ArrayList<>();
    public static ArrayList<Relic> relicInit() {
        return relicObjectInit();
    }

    public static ArrayList<Relic> relicObjectInit() {
        Relic foresight = new ForesightRelic();
        relicList.add(foresight);

        Relic regeneration = new RegenerationRelic();
        relicList.add(regeneration);

        Relic cursed = new CurseRelic();
        relicList.add(cursed);

        return relicList;
    }
}