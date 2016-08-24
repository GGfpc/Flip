package com.jtbgame;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by ggarc_000 on 10/08/2016.
 */
public class ColorScheme {

    Color bg;
    Color plat;
    int name;

    public ColorScheme(Color bg, Color plat) {
        this.bg = bg;
        this.plat = plat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorScheme color = (ColorScheme)o;
        return Math.abs(color.bg.toIntBits() - bg.toIntBits()) < 3;
    }


}
