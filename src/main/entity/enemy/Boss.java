package main.entity.enemy;

import main.entity.Species;

public abstract class Boss extends Enemy {
    public Boss(Species species, int health, int damage, int experienceDropped) {
        super(species, health, damage, experienceDropped);
    }
}
