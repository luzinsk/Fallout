package com.luzinsk.fallout.weapons.type;

import com.luzinsk.fallout.weapons.Weapon;

public class AWP extends Weapon {
    public AWP() {
        super.setMagazineSize(5);
        super.setMagazine(0);
    }

    public String toString() {
        return "AWP";
    }
}
