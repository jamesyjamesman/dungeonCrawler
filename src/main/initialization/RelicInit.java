package main.initialization;

import main.item.relic.*;

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

        Relic enemy = new EnemyRelic();
        relicList.add(enemy);

        Relic absorption = new ShieldingRelic();
        relicList.add(absorption);

        return relicList;
    }
}