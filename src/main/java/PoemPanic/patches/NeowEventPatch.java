package PoemPanic.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowReward;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class NeowEventPatch {

    @SpirePatch(clz = NeowEvent.class, method = "buttonEffect")
    public static class buttonEffectPatch {
        @SpireInsertPatch(rloc = 0)
        public static void Insert(NeowEvent ___instanceance, int buttonPressed) throws NoSuchFieldException, IllegalAccessException {
            Field bossCount = ___instanceance.getClass().getDeclaredField("bossCount");
            bossCount.setAccessible(true);
            if (((Integer) bossCount.get(___instanceance)).intValue() < 1) {
                bossCount.set(___instanceance, Integer.valueOf(1));
            }
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "blessing")
    public static class neowPatches {

        @SpireInsertPatch(rloc = 12, localvars = {"rewards"})
        public static void Insert(NeowEvent ___instanceance, ArrayList<NeowReward> rewards) {
            rewards.add(new PoemPanicReward());
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "blessing")
    public static class blessingPatch {
        @SpireInsertPatch(rloc = 19, localvars = {"rewards"})
        public static void Insert(NeowEvent ___instanceance, ArrayList<NeowReward> rewards) {
            ___instanceance.roomEventText.addDialogOption(PoemPanicReward.label);
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "buttonEffect")
    public static class buttEffectPatch {
        @SpireInsertPatch(rloc = 63, localvars = {"rewards"})
        public static void Insert(NeowEvent __instance, int buttonPressed, ArrayList<NeowReward> rewards) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

            if (buttonPressed < 4) { return; }
            ((NeowReward) rewards.get(buttonPressed)).activate();
            Method m = NeowEvent.class.getDeclaredMethod("talk", new Class[]{String.class});
            m.setAccessible(true);
            m.invoke(__instance, new Object[]{"..."});
        }
    }
}