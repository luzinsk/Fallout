package com.luzinsk.fallout.weapons.type;

import com.luzinsk.fallout.weapons.Weapon;

public class None extends Weapon {
    public None() {
        super.setMagazineSize(0);
        super.setMagazine(0);
    }

    public String toString() {
        return "None";
    }
}
