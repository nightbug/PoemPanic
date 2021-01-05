package PoemPanic.relics;

import PoemPanic.screens.PoemPanicScreen;
import PoemPanic.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.ArrayList;
import java.util.Iterator;

import static PoemPanic.PoemPanic.makeID;
import static PoemPanic.PoemPanic.poemPanicScreen;

public class PoemPanicRelic extends CustomRelic {
    public static String ID = makeID(PoemPanicRelic.class.getSimpleName());
    private static Texture IMG = TextureLoader.getTexture("PoemPanicResources/images/relics/PoemPanicRelic.png");
    private static Texture OUTLINE = TextureLoader.getTexture("PoemPanicResources/images/relics/outline/PoemPanicRelic.png");

    public PoemPanicRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        removeStrikeTip();
    }

    private void removeStrikeTip() {
        ArrayList<String> strikes = new ArrayList<>();
        for (String s : GameDictionary.STRIKE.NAMES) { strikes.add(s.toLowerCase()); }
        for (Iterator<PowerTip> t = this.tips.iterator(); t.hasNext(); ) {
            PowerTip derp = t.next();
            String s = derp.header.toLowerCase();
            if (strikes.contains(s)) {
                t.remove();
                break;
            }
        }
    }
    public void onEquip(){
        poemPanicScreen = new PoemPanicScreen();
        poemPanicScreen.init();
        poemPanicScreen.open();
    }
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
