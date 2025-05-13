import java.util.ArrayList;

public class RelicInit {
    static ArrayList<Relic> relicList = new ArrayList<>();
    public static ArrayList<Relic> relicInit() {
        return relicObjectInit();
    }

    public static ArrayList<Relic> relicObjectInit() {
        Relic foresight = new ForesightRelic();
        relicList.add(foresight);

        return relicList;
    }
}