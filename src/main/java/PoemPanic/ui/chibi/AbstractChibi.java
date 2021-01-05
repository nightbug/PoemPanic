package PoemPanic.ui.chibi;

import PoemPanic.ui.chibi.helpers.directionHelper;
import PoemPanic.ui.chibi.interpolation.EaseInQuad;
import PoemPanic.ui.chibi.interpolation.EaseOutQuad;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractChibi {

    private static final Logger logger = LogManager.getLogger(AbstractChibi.class.getName());

    protected Texture BASE_TEXTURE;
    protected Texture HOP_TEXTURE;
    protected float xPos;
    protected float yPos;
    protected float bufferXPos;
    protected float bufferYPos;

    protected float baseXPos;

    public boolean hopping = false;
    protected boolean flipped = false;
    protected boolean midMove = false;
    protected EaseInQuad easeInQuad = new EaseInQuad();
    protected EaseOutQuad easeOutQuad = new EaseOutQuad();

    private float RANDOM_PAUSE_DURATION;

    private float HOP_DURATION = 1F;
    private static float HOP_STARTING_DURATION = 1F;

    private float MOVE_DURATION = 0.5F;
    private static float MOVE_STARTING_DURATION = 0.5F;

    private Vector2 startPosition = null;

    private Vector2 hopPosition = null;
    private Vector2 moveLPosition = null;
    private Vector2 moveRPosition = null;
    private directionHelper helper = new directionHelper();

    private float X_MOVE_DISTANCE = 15 * Settings.scale;
    private float Y_SHORT_HOP_DISTANCE = 10 * Settings.scale;
    private float Y_LONG_HOP_DISTANCE = 50 * Settings.scale;

    public AbstractChibi(Texture BASE, Texture HOP, float x, float y){
        BASE_TEXTURE = BASE;
        HOP_TEXTURE = HOP;
        xPos = x;
        yPos = y;
        bufferXPos = xPos;
        baseXPos = xPos;
        if(this instanceof Monika || this instanceof Yuri){ RANDOM_PAUSE_DURATION = (AbstractDungeon.miscRng.random.nextFloat() + 1) * 4; }
        else if(this instanceof Natsuki){ RANDOM_PAUSE_DURATION = (AbstractDungeon.miscRng.random.nextFloat() + 1) * 3; }
        else { RANDOM_PAUSE_DURATION = (AbstractDungeon.miscRng.random.nextFloat() + 1) * 2; }
        startPosition = new Vector2(xPos, yPos);
        hopPosition = new Vector2(xPos, yPos + Y_LONG_HOP_DISTANCE);
        moveLPosition = new Vector2(xPos - X_MOVE_DISTANCE, yPos + Y_SHORT_HOP_DISTANCE);
        moveRPosition = new Vector2(xPos + X_MOVE_DISTANCE, yPos + Y_SHORT_HOP_DISTANCE);
    }

    public void render(SpriteBatch sb){ }

    public void update(){
        if(hopping){
            if(HOP_DURATION > HOP_STARTING_DURATION / 2f){
                yPos = easeInQuad.apply(bufferYPos, hopPosition.y, 1F - ((HOP_DURATION - (HOP_STARTING_DURATION / 2.0F)) / (HOP_STARTING_DURATION / 2.0F)));
                HOP_DURATION -= Gdx.graphics.getDeltaTime();
            }
            else {
                yPos = Interpolation.bounceOut.apply(hopPosition.y, startPosition.y,  1F - (HOP_DURATION / (HOP_STARTING_DURATION / 2.0F)));
                HOP_DURATION -= Gdx.graphics.getDeltaTime();
                if(HOP_DURATION < 0F){
                    HOP_DURATION = HOP_STARTING_DURATION;
                    hopping = false;
                }
            }
        }
        else if(midMove){
            if(MOVE_DURATION > MOVE_STARTING_DURATION / 2f){
                xPos = easeInQuad.apply(bufferXPos, flipped ? moveRPosition.x : moveLPosition.x, 1F - ((MOVE_DURATION - (MOVE_STARTING_DURATION / 2.0F)) / (MOVE_STARTING_DURATION / 2.0F)));
                yPos = easeInQuad.apply(startPosition.y, moveLPosition.y, 1F - ((MOVE_DURATION - (MOVE_STARTING_DURATION / 2.0F)) / (MOVE_STARTING_DURATION / 2.0F)));
                MOVE_DURATION -= Gdx.graphics.getDeltaTime();
            }
            else {
                yPos = easeOutQuad.apply(moveLPosition.y, startPosition.y, 1F - (MOVE_DURATION / (MOVE_STARTING_DURATION / 2.0F)));
                MOVE_DURATION -= Gdx.graphics.getDeltaTime();
                if(MOVE_DURATION < 0F){
                    MOVE_DURATION = MOVE_STARTING_DURATION;
                    midMove = false;
                }
            }
        }
        else {
            RANDOM_PAUSE_DURATION -= Gdx.graphics.getDeltaTime();
            if(RANDOM_PAUSE_DURATION < 0F) {
                randomMove();
                if (this instanceof Monika || this instanceof Yuri) { RANDOM_PAUSE_DURATION = (AbstractDungeon.miscRng.random.nextFloat() + 1) * 4;
                } else if (this instanceof Natsuki) { RANDOM_PAUSE_DURATION = (AbstractDungeon.miscRng.random.nextFloat() + 1) * 3;
                } else { RANDOM_PAUSE_DURATION = (AbstractDungeon.miscRng.random.nextFloat() + 1) * 2; }
            }
        }
    }

    public void beginHopping(){
        hopping = true;
        midMove = false;
        MOVE_DURATION = 0F;
        bufferYPos = yPos;
    }
    public void randomMove() {
        calculatemoveDirections();
        if(moveLPosition.x <= (baseXPos - (X_MOVE_DISTANCE * 3))){
            flipped = true;
            helper.addDirection(directionHelper.DIRECTION.RIGHT);
        }
        else{
            int result = AbstractDungeon.miscRng.random.nextInt(2);
            switch (result) {
                case 0:
                    if (helper.checkLastTwoDirectionsAreTheyBothEqual()) {
                        directionHelper.DIRECTION direction = helper.inverseOf(helper.checkLastDirection());
                        flipped = direction.equals(directionHelper.DIRECTION.RIGHT);
                        helper.addDirection(direction);
                    } else {
                        helper.addDirection(directionHelper.DIRECTION.LEFT);
                        flipped = false;
                    }
                    break;
                default:
                    if (helper.checkLastTwoDirectionsAreTheyBothEqual()) {
                        directionHelper.DIRECTION direction = helper.inverseOf(helper.checkLastDirection());
                        flipped = direction.equals(directionHelper.DIRECTION.RIGHT);
                        helper.addDirection(direction);
                    } else {
                        helper.addDirection(directionHelper.DIRECTION.RIGHT);
                        flipped = true;
                    }
                    break;
            }
        }
        MOVE_DURATION = MOVE_STARTING_DURATION;
        midMove = true;
    }

    public void calculatemoveDirections(){
        moveLPosition.x = xPos - X_MOVE_DISTANCE;
        moveRPosition.x = xPos + X_MOVE_DISTANCE;
        bufferXPos = xPos;
    }
}