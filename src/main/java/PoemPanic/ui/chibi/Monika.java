package PoemPanic.ui.chibi;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class Monika extends AbstractChibi {

    public Monika(){
        super(
                ChibiTextureManager.getTexture(Monika.class.getSimpleName()),
                ChibiTextureManager.getTextureHop(Monika.class.getSimpleName()),
                Settings.WIDTH / 4 * 3.5F, 0F
                );
    }

    public Monika(int x, int y){
        super(
                ChibiTextureManager.getTexture(Monika.class.getSimpleName()),
                ChibiTextureManager.getTextureHop(Monika.class.getSimpleName()),
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
