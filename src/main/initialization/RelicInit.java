package main.initialization;

import main.item.relic.*;

import java.util.ArrayList;

public class RelicInit {
    static ArrayList<Relic> relicList = new ArrayList<>();
    public static ArrayList<Relic> relicInit() {
        relicList.add(new ForesightRelic());
        relicList.add(new RegenerationRelic());
        relicList.add(new CurseDetectionRelic());
        relicList.add(new EnemyRelic());
        relicList.add(new ShieldingRelic());
        relicList.add(new HoldingRelic());
        relicList.add(new RelicRelic());

        return relicList;
    }
}