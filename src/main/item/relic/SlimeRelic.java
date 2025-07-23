package main.item.relic;

//logic handled in Enemy.attack()
public class SlimeRelic extends Relic {
    public SlimeRelic() {
        setName("Relic of Bounciness");
        setType(RelicType.SLIME);
        setDescription("This relic covers you in viscous, bouncy slime. Some attacks just bounce right off!");
    }
}
