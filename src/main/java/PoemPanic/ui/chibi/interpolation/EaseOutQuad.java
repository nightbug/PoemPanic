package PoemPanic.ui.chibi.interpolation;

import com.badlogic.gdx.math.Interpolation;

public class EaseOutQuad extends Interpolation {
    @Override
    public float apply(float v) { return v * v; }
}
