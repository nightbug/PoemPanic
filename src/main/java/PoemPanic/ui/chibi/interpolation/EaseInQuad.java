package PoemPanic.ui.chibi.interpolation;

import com.badlogic.gdx.math.Interpolation;

public class EaseInQuad extends Interpolation {
    @Override
    public float apply(float v) {
        return 1 - (1 - v) * (1 - v);
    }
}
