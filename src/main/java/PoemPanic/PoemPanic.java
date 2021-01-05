package PoemPanic;

import PoemPanic.relics.PoemPanicRelic;
import PoemPanic.screens.PoemPanicScreen;
import PoemPanic.util.IDCheckDontTouchPls;
import PoemPanic.util.TextureLoader;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpireInitializer
public class PoemPanic implements
        EditStringsSubscriber,
        PostInitializeSubscriber,
        EditRelicsSubscriber
{

    public static final Logger logger = LogManager.getLogger(PoemPanic.class.getName());
    private static String modID;
    private static final String MODNAME = "Music Mod";
    private static final String AUTHOR = "squeeny";
    private static final String DESCRIPTION = "Just monikammmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm";
    public static final String BADGE_IMAGE = "PoemPanicResources/images/Badge.png";
    public static PoemPanicScreen poemPanicScreen;

    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/" + resourcePath;
    }

    public PoemPanic() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("PoemPanic");
        logger.info("Done subscribing");
        logger.info("Done adding mod settings");
    }

    public static void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = PoemPanic.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else { modID = ID; }
        logger.info("Success! ID is " + modID);
    }

    public static String getModID() {
        return modID;
    }

    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = PoemPanic.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = PoemPanic.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) { throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); }
            if (!resourcePathExists.exists()) { throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); }
        }
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing PoemPanic  =========================");
        PoemPanic poempanic = new PoemPanic();
        logger.info("========================= a fleeting dream =========================");
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        try { CreatePanel();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/relics.json");
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/ui.json");
        logger.info("Strings are done.");
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
    private void CreatePanel() throws IOException {
        ModPanel settingsPanel = new ModPanel();
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new PoemPanicRelic(), RelicType.SHARED);
    }
}
