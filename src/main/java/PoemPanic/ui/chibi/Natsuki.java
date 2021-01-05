package PoemPanic.ui.chibi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class Natsuki extends AbstractChibi {
    public Natsuki(){
        super(
                ChibiTextureManager.getTexture(Natsuki.class.getSimpleName()),
                ChibiTextureManager.getTextureHop(Natsuki.class.getSimpleName()),
                Settings.WIDTH / 4 * 3.35F, 0
        );
    }

    public Natsuki(int x, int y){
        super(
                ChibiTextureManager.getTexture(Natsuki.class.getSimpleName()),
                ChibiTextureManager.getTextureHop(Natsuki.class.getSimpleName()),
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
