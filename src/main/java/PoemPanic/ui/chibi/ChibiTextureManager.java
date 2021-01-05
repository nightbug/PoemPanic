package PoemPanic.ui.chibi;

import PoemPanic.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;

import java.time.LocalDate;

import static PoemPanic.PoemPanic.getModID;

public class ChibiTextureManager {

    public static Texture getTexture(String INTERNAL_CHAR){ return TextureLoader.getTexture("PoemPanicResources/images/ui/chibi/" + INTERNAL_CHAR + "/sticker_1.png"); }
    public static Texture getTextureHop(String INTERNAL_CHAR){ return TextureLoader.getTexture("PoemPanicResources/images/ui/chibi/" + INTERNAL_CHAR + "/sticker_2.png"); }
}
