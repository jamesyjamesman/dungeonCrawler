package main.initialization;

import main.item.relic.*;

import java.util.ArrayList;

public class RelicInit {
    static ArrayList<Relic> relicList = new ArrayList<>();
    public static ArrayList<Relic> relicInit() {
        Relic foresight = new ForesightRelic();
        relicList.add(foresight);

        Relic regeneration = new RegenerationRelic();
        relicList.add(regeneration);

        Relic cursed = new CurseDetectionRelic();
        relicList.add(cursed);

        Relic enemy = new EnemyRelic();
        relicList.add(enemy);

        Relic absorption = new ShieldingRelic();
        relicList.add(absorption);

        Relic holding = new HoldingRelic();
        relicList.add(holding);

        Relic relics = new RelicRelic();
        relicList.add(relics);

        return relicList;
    }
}