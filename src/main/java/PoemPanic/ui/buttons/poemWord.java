package PoemPanic.ui.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static PoemPanic.PoemPanic.poemPanicScreen;

public class poemWord {
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.screens.mainMenu.MenuButton.class.getName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("MenuButton");
    public static final String[] TEXT = uiStrings.TEXT;
    public com.megacrit.cardcrawl.screens.mainMenu.MenuButton.ClickResult result;
    private String label;
    public Hitbox hb;
    private Color tint = Color.WHITE.cpy();
    private int index;
    private boolean isDisabled = false;
    private Color highlightColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    private boolean hidden = false;
    private float x = 0.0F;
    private float y = 0.0F;
    private float targetX = 0.0F;
    public static final float FONT_X = 120.0F * Settings.scale;
    public static final float START_Y = 120.0F * Settings.scale;
    public static final float SPACE_Y = 50.0F * Settings.scale;
    public static final float FONT_OFFSET_Y = 10.0F * Settings.scale;
    private boolean confirmation = false;
    private static Texture highlightImg = null;

    private AbstractCard storedCard;
    public enum ClickResult {PLAY, RESUME_GAME, ABANDON_RUN, INFO, STAT, SETTINGS, PATCH_NOTES, QUIT;}

    private int padding;
    public poemWord(AbstractCard c, int index, int padding) {
        this.padding = padding;
        storedCard = c;
        if (highlightImg == null) { highlightImg = ImageMaster.loadImage("images/ui/mainMenu/menu_option_highlight.png"); }
        this.index = index;
        setLabel();
        this.hb = new Hitbox(FontHelper.getSmartWidth(FontHelper.buttonLabelFont, this.label, 9999.0F, 1.0F) + 100.0F * Settings.scale, SPACE_Y);
        this.hb.move(
                padding == 0 ? this.hb.width / 2.0F + 1050.0F * Settings.scale : this.hb.width / 2F + 525F * Settings.scale,
                START_Y + index * SPACE_Y);
    }

    private void setLabel() { label = storedCard.name; }

    public void update() {
        this.hb.update();
        this.x = MathHelper.uiLerpSnap(this.x, this.targetX);
        if (this.hb.justHovered && !this.hidden) {
            CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
        }
        if (this.hb.hovered) {
            this.highlightColor.a = 0.9F;
            this.targetX = 25.0F * Settings.scale;
            if (InputHelper.justClickedLeft) {
                CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
                this.hb.clickStarted = true;
            }
            this.tint = Color.WHITE.cpy();
        }
        this.highlightColor.a = MathHelper.fadeLerpSnap(this.highlightColor.a, 0.0F);
        this.targetX = 0.0F;
        this.hidden = false;
        this.tint.r = MathHelper.fadeLerpSnap(this.tint.r, 0.3F);
        this.tint.g = this.tint.r;
        this.tint.b = this.tint.r;

        if (this.hb.hovered && CInputActionSet.select.isJustPressed()) {
            CInputActionSet.select.unpress();
            this.hb.clicked = true;
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
        }
        if (this.hb.clicked) {
            this.hb.clicked = false;
            buttonEffect();
            CardCrawlGame.mainMenuScreen.hideMenuButtons();
        }
    }

    public void hide() {
        this.hb.hovered = false;
        this.targetX = -1000.0F * Settings.scale + 30.0F * Settings.scale * this.index;
        this.hidden = true;
    }

    public void buttonEffect() {
        if(poemPanicScreen.JustMonika){ poemPanicScreen.monika.beginHopping(); }
        else {
            switch (AbstractDungeon.miscRng.random(0, 2)){
                case 0:
                    if(poemPanicScreen.yuri.hopping){
                        if(poemPanicScreen.natsuki.hopping){ poemPanicScreen.sayori.beginHopping(); }
                        else { poemPanicScreen.natsuki.beginHopping(); }
                    }
                    else { poemPanicScreen.yuri.beginHopping(); }
                    break;
                case 1:
                    if(poemPanicScreen.natsuki.hopping){
                        if(poemPanicScreen.yuri.hopping){ poemPanicScreen.sayori.beginHopping(); }
                        else { poemPanicScreen.yuri.beginHopping(); }
                    }
                    else { poemPanicScreen.natsuki.beginHopping(); }
                    break;
                case 2:
                    if(poemPanicScreen.sayori.hopping){
                        if(poemPanicScreen.natsuki.hopping){ poemPanicScreen.yuri.beginHopping(); }
                        else { poemPanicScreen.natsuki.beginHopping(); }
                    }
                    else { poemPanicScreen.sayori.beginHopping(); }
                    break;
            }
        }
        poemPanicScreen.justPickedACard(storedCard);
    }


    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.highlightColor);
        //sb.draw(highlightImg, this.x + FONT_X * Settings.scale, this.hb.cY - 52.0F, this.x, 52F, 358.0F, 104.0F, Settings.scale, Settings.scale * 0.8F, 0.0F, 0, 0, 358, 104, false, false);
        sb.draw(highlightImg, this.padding == 1 ? Settings.WIDTH / 3.5F - 179.0F + 120.0F * Settings.scale : Settings.WIDTH / 3.5F * 2 - 179.0F + 120F * Settings.scale , this.hb.cY - 52.0F, 179.0F, 52.0F, 358.0F, 104.0F, Settings.scale, Settings.scale * 0.8F, 0.0F, 0, 0, 358, 104, false, false);
        sb.setBlendFunction(770, 771);
        if (this.isDisabled) { FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, this.label, this.x + FONT_X , this.hb.cY + FONT_OFFSET_Y, 9999.0F, 1.0F, Settings.RED_TEXT_COLOR); }
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, this.label, padding == 1 ? Settings.WIDTH / 3.5F : Settings.WIDTH / 3.5F * 2, this.hb.cY + FONT_OFFSET_Y, 4500.0F, 1.0F, Settings.CREAM_COLOR);
        this.hb.render(sb);
        if (this.hb.hovered) {
            storedCard.current_x = Settings.WIDTH / 6F * Settings.scale;
            storedCard.current_y = Settings.HEIGHT / 2F * Settings.scale;
            storedCard.render(sb); }
    }
}