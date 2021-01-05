package PoemPanic.ui.chibi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class Yuri extends AbstractChibi {
    public Yuri(){
        super(
                ChibiTextureManager.getTexture(Yuri.class.getSimpleName()),
                ChibiTextureManager.getTextureHop(Yuri.class.getSimpleName()),
                Settings.WIDTH / 4 * 3.75F, 0
        );
    }

    public Yuri(int x, int y){
        super(
                ChibiTextureManager.getTexture(Yuri.class.getSimpleName()),
                ChibiTextureManager.getTextureHop(Yuri.class.getSimpleName()),
                x,
                y
        );
    }

    public void render(SpriteBatch sb){
        super.render(sb);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(hopping ? HOP_TEXTURE : BASE_TEXTURE, xPos, yPos, BASE_TEXTURE.getWidth(), BASE_TEXTURE.getHeight(), 0, 0, BASE_TEXTURE.getWidth(), BASE_TEXTURE.getHeight(), flipped, false);
    }
}