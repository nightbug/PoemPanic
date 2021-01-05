package PoemPanic.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static PoemPanic.PoemPanic.poemPanicScreen;

public class PoemPanicPatches {

    @SpireEnum
    public static AbstractDungeon.CurrentScreen POEM_PANIC;
    public static final String CLS = "com.megacrit.cardcrawl.dungeons.AbstractDungeon";

    @SpirePatch(cls = CLS, method="closeCurrentScreen")
    public static class CloseCurrentScreen {
        public static void Prefix() {
            if(AbstractDungeon.screen == POEM_PANIC) {
                try {
                    Method overlayReset = AbstractDungeon.class.getDeclaredMethod("genericScreenOverlayReset");
                    overlayReset.setAccessible(true);
                    overlayReset.invoke(AbstractDungeon.class);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                AbstractDungeon.overlayMenu.hideBlackScreen();
                poemPanicScreen.close();
            }
        }
    }

    //TODO: Move to postfix to allow rendering on top of other screens
    @SpirePatch(cls = CLS, method = "render")
    public static class Render {

        @SpireInsertPatch(locator = RenderLocator.class)
        public static void Insert(AbstractDungeon __instance, SpriteBatch sb) {
            if(AbstractDungeon.screen == POEM_PANIC) poemPanicScreen.render(sb);
        }

        private static class RenderLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(
                        "com.megacrit.cardcrawl.dungeons.AbstractDungeon", "screen");

                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(cls = CLS, method = "update")
    public static class Update {

        @SpireInsertPatch(locator = UpdateLocator.class)
        public static void Insert(AbstractDungeon __instance) {
            if(AbstractDungeon.screen == POEM_PANIC) poemPanicScreen.update();
        }

        private static class UpdateLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
                Matcher matcher = new Matcher.FieldAccessMatcher(
                        AbstractDungeon.class, "turnPhaseEffectActive"
                );
                int[] line = LineFinder.findInOrder(ctBehavior, matcher);
                return line;
            }
        }
    }

}

