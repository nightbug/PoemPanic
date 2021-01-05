package PoemPanic.patches;

import PoemPanic.relics.PoemPanicRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowReward;

import static PoemPanic.PoemPanic.makeID;

public class PoemPanicReward extends NeowReward {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(PoemPanicReward.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;
    private static final String DESCRIPTION = TEXT[0];
    public static String label;
    public PoemPanicReward() {
        super(0);
        this.optionLabel = DESCRIPTION;
        label = optionLabel;
    }

    public void activate(){
        if(!AbstractDungeon.player.hasRelic(PoemPanicRelic.ID)){ AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, RelicLibrary.getRelic(PoemPanicRelic.ID).makeCopy()); }
    }
}
