package engine;

import java.io.*;
import java.util.Properties;
import static engine.GameSettings.*;
public class SettingLoader {
    private static final String CONFIG_PATH = "/res/system/config.properties";

    public static void Load(){
        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + CONFIG_PATH)) {

            Properties prop = new Properties();

            prop.load(input);
            try {
                SCREEN_WIDTH = Integer.parseInt(prop.getProperty("SCREEN_WIDTH"));
                SCREEN_HEIGHT = Integer.parseInt(prop.getProperty("SCREEN_HEIGHT"));
                SCREEN_SCALE = Float.parseFloat(prop.getProperty("SCREEN_SCALE"));
                SHOW_FPS = Boolean.parseBoolean(prop.getProperty("SHOW_FPS"));
                RENDER_COMPONENTS = Boolean.parseBoolean(prop.getProperty("RENDER_COMPONENTS"));
                USE_ROTATION_IMAGES_BUFFER = Boolean.parseBoolean(prop.getProperty("USE_ROTATION_IMAGES_BUFFER"));
                LOCK_FPS = Boolean.parseBoolean(prop.getProperty("LOCK_FPS"));
            }catch (Exception ex){
                ex.printStackTrace();
                System.err.println("Writing default configuration...");
                Save();
            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public static void Save(){
        try (OutputStream output = new FileOutputStream(System.getProperty("user.dir") + CONFIG_PATH)) {

            Properties prop = new Properties();

            prop.setProperty("SCREEN_WIDTH", String.valueOf(SCREEN_WIDTH));
            prop.setProperty("SCREEN_HEIGHT", String.valueOf(SCREEN_HEIGHT));
            prop.setProperty("SCREEN_SCALE", String.valueOf(SCREEN_SCALE));
            prop.setProperty("SHOW_FPS", String.valueOf(SHOW_FPS));
            prop.setProperty("RENDER_COMPONENTS", String.valueOf(RENDER_COMPONENTS));
            prop.setProperty("USE_ROTATION_IMAGES_BUFFER", String.valueOf(USE_ROTATION_IMAGES_BUFFER));
            prop.setProperty("LOCK_FPS", String.valueOf(LOCK_FPS));

            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
