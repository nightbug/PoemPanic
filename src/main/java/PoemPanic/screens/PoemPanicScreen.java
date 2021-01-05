package PoemPanic.screens;

import PoemPanic.ui.buttons.poemWord;
import PoemPanic.ui.chibi.Monika;
import PoemPanic.ui.chibi.Natsuki;
import PoemPanic.ui.chibi.Sayori;
import PoemPanic.ui.chibi.Yuri;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrismaticShard;

import java.util.ArrayList;
import java.util.Collections;

import static PoemPanic.PoemPanic.getModID;
import static PoemPanic.PoemPanic.makeID;
import static PoemPanic.patches.PoemPanicPatches.POEM_PANIC;

public class PoemPanicScreen {

    private static final ArrayList<poemWord> poemWords = new ArrayList<>();
    private static ArrayList<AbstractCard> eligiblePoemCards = new ArrayList<>();
    private static ArrayList<AbstractCard> poemCards = new ArrayList<>();

    private static ArrayList<AbstractCard> starterCards = new ArrayList<>();
    private static ArrayList<AbstractCard> pickedCards = new ArrayList<>();
    private static final int CARD_RENDER_AMOUNT = 15;
    private static int CARDS_TO_PICK = 0;
    public boolean JustMonika;
    public Monika monika;
    public Yuri yuri;
    public Sayori sayori;
    public Natsuki natsuki;

    private boolean outputCards = false;
    private boolean justPickedACard = false;
    private UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID(PoemPanicScreen.class.getSimpleName()));
    public final String[] TEXT = uiStrings.TEXT;
    private final String DESCRIPTION = TEXT[0];

    public void init(){
        starterCards.clear();
        for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
            if(c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)){
                starterCards.add(c);
                CARDS_TO_PICK++;
            }
        }
        outputCards = false;
        justPickedACard = false;
        generatePoemCards();
        JustMonika = AbstractDungeon.miscRng.random(0, 2) == 0;
        monika = new Monika();
        yuri = new Yuri();
        natsuki = new Natsuki();
        sayori = new Sayori();
    }

    public void open(){
        if(CARDS_TO_PICK == 0){  }
        else {
            AbstractDungeon.player.releaseCard();
            AbstractDungeon.screen = POEM_PANIC;
            AbstractDungeon.overlayMenu.showBlackScreen();
            AbstractDungeon.overlayMenu.proceedButton.hide();

            CardCrawlGame.music.fadeOutTempBGM();
            CardCrawlGame.music.silenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBGM("4.ogg");
            CardCrawlGame.music.updateVolume();
        }
    }

    public void close(){
        if(!pickedCards.isEmpty()){
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c : pickedCards){
                for(AbstractRelic r: AbstractDungeon.player.relics){ r.onPreviewObtainCard(c); }
                group.addToBottom(c.makeCopy());
            }
            for(AbstractCard c: starterCards){ AbstractDungeon.player.masterDeck.removeCard(c); }
            CardCrawlGame.music.fadeOutTempBGM();
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, DESCRIPTION);
        }
    }

    public void render(SpriteBatch sb){
        //sb.draw(notebook, 0.0F, 0.0F, (float) Settings.WIDTH, (float)Settings.HEIGHT);
        for(poemWord p : poemWords){ p.render(sb);}
        if(JustMonika){ monika.render(sb); }
        else {
            yuri.render(sb);
            natsuki.render(sb);
            sayori.render(sb);
        }
    }

    public void update(){
        for(poemWord p : poemWords){ p.update(); }
        if (justPickedACard) {
            poemCards.clear();
            poemWords.clear();
            Collections.shuffle(eligiblePoemCards, AbstractDungeon.cardRandomRng.random);
            poemCards.addAll(new ArrayList<>(eligiblePoemCards.subList(0, CARD_RENDER_AMOUNT)));
            int index = 0;
            for (AbstractCard c : poemCards) { poemWords.add(new poemWord(c, index++, index % 2 == 0 ? 0 : 1)); }
            justPickedACard = false;
        }
        if(JustMonika){ monika.update(); }
        else {
            yuri.update();
            natsuki.update();
            sayori.update();
        }
        if (!this.outputCards && (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID && AbstractDungeon.screen != POEM_PANIC))  {
            this.outputCards = true;
            (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
        }
    }

    public void generatePoemCards(){
        pickedCards.clear();
        eligiblePoemCards.clear();
        poemCards.clear();
        poemWords.clear();
        if(AbstractDungeon.player.hasRelic(PrismaticShard.ID)){
            CardLibrary.getAllCards().stream().filter(c ->
                    (!c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND))
                    && (!c.color.equals(AbstractCard.CardColor.CURSE) && !c.color.equals(AbstractCard.CardColor.COLORLESS))
            ).forEach(c -> eligiblePoemCards.add(c.makeCopy()));
        }
        else {
            CardLibrary.getAllCards().stream().filter(c ->
                    (c.color.equals(AbstractDungeon.player.getCardColor())
                            && (!c.hasTag(AbstractCard.CardTags.STARTER_STRIKE) || !c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)))).forEach(c -> eligiblePoemCards.add(c.makeCopy()));
            CardLibrary.getAllCards().stream().filter(c -> (c.color.equals(AbstractDungeon.player.getCardColor()))).forEach(c -> eligiblePoemCards.add(c.makeCopy()));
        }
        Collections.shuffle(eligiblePoemCards, AbstractDungeon.cardRandomRng.random);
        poemCards.addAll(new ArrayList<>(eligiblePoemCards.subList(0, CARD_RENDER_AMOUNT)));
        int index = 0;
        for(AbstractCard c: poemCards){ poemWords.add(new poemWord(c, index++, index % 2 == 0 ? 0 : 1)); }
    }

    public void justPickedACard(AbstractCard pickedCard){
        pickedCards.add(pickedCard);
        CARDS_TO_PICK--;
        if(CARDS_TO_PICK <= 0){ close(); }
        else { justPickedACard = true; }
    }
}