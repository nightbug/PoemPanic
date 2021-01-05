package PoemPanic.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PandorasBox;

public class this_box_empty {

    @SpirePatch(clz = PandorasBox.class, method = SpirePatch.CONSTRUCTOR)
    public static class here_goes {

        @SpirePostfixPatch
        public static void yeet(PandorasBox __instance) { __instance.tier = AbstractRelic.RelicTier.DEPRECATED; }
    }
}
