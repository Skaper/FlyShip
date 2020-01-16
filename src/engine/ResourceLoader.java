package engine;

import engine.gfx.Animation;
import engine.gfx.Image;
import engine.gfx.Sprite;
import engine.sfx.SoundData;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceLoader {
    private static HashMap<String, ArrayList<Image>> animations;
    private static HashMap<String, Image> images;
    private static ArrayList<String> imagesPaths;
    private static HashMap<String, SoundData> sounds;

    private static String resPath;
    private static final String animationsPath = "/animations/";
    private static final String imagesPath = "/images/";
    private static final String soundsPath = "/audio/";
    private static final String fontsPath = "/fonts/";

    private static final String[] IMAGES_SUPPORT_FORMATS= {".jpg", ".png"};
    private static final String[] AUDIO_SUPPORT_FORMATS = {".wav"};

    public static void Load(){
        resPath = System.getProperty("user.dir") +  File.separator + "res";
        loadingImages();
        loadingAnimations();
        loadingSounds();

        loadingFonts();


    }

    private static void loadingSounds(){
        //LOADING SOUNDS
        System.out.println("\n\t> LOADING SOUNDS...");
        sounds = new HashMap<>();

        List<String> paths = null;
        Path start = Paths.get(resPath + soundsPath);
        try (Stream<Path> stream = Files.walk(start, 100)) {
            paths = stream.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }
        if(paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String currentPath = paths.get(i);

                if (currentPath.toLowerCase().endsWith(AUDIO_SUPPORT_FORMATS[0])) {
                    System.out.println("LOADING SOUND: " + currentPath.replace(resPath, ""));
                    sounds.put(currentPath.replace(resPath, ""), new SoundData(currentPath.replace(resPath, "")));
                } else {
                    paths.remove(i);
                    i--;
                    System.err.println("IGNORE FILE: " + currentPath.replace(resPath, ""));
                }
            }
        }
    }

    private static void loadingAnimations(){
        //LOADING ANIMATIONS:
        System.out.println("\n\t> LOADING ANIMATIONS...");
        animations = new HashMap<>();
        List<String> dirPaths = null;
        Path start = Paths.get(resPath + animationsPath);
        try (Stream<Path> stream = Files.walk(start, 100)) {
            dirPaths = stream.filter(Files::isDirectory)
                    .map(x -> x.toString()).collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }
        if(dirPaths != null) {
            for (int i = 0; i < dirPaths.size(); i++) {
                String currentPath = dirPaths.get(i);

                //check files in directory
                List<String> paths = null;
                try (Stream<Path> stream = Files.walk(Paths.get(currentPath), 1)) {
                    paths = stream.filter(Files::isRegularFile)
                            .map(x -> x.toString()).collect(Collectors.toList());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //make image list
                if(paths != null) {
                    sortFiles(paths);
                    ArrayList<Image> animationImgs = new ArrayList<>();

                    for (int j = 0; j < paths.size(); j++) {
                        String currentImagePath = paths.get(j);
                        if (currentImagePath.toLowerCase().endsWith(IMAGES_SUPPORT_FORMATS[0]) || currentImagePath.toLowerCase().endsWith(IMAGES_SUPPORT_FORMATS[1])) {
                            Image img = new Image(currentImagePath.replace(resPath, ""));
                            images.put(currentImagePath.replace(resPath, ""), img);
                            animationImgs.add(img);
                        } else {
                            paths.remove(j);
                            j--;
                        }

                    }
                    if(animationImgs.size() > 0){
                        System.out.println("LOADING ANIMATION DIR: "+currentPath.replace(resPath, ""));
                        animations.put(currentPath.replace(resPath, ""), animationImgs);
                    }
                }
            }
        }
    }

    private static void sortFiles(List<String> paths){
        Collections.sort(paths, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("[^0-9]", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }

    private static void loadingImages(){
        //LOADING IMAGES:
        System.out.println("\n\t> LOADING IMAGES...");
        images = new HashMap<>();
        imagesPaths = new ArrayList<>();
        List<String> paths = null;
        Path start = Paths.get(resPath + imagesPath);
        try (Stream<Path> stream = Files.walk(start, 100)) {
            paths = stream.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String currentPath = paths.get(i);

                if (currentPath.toLowerCase().endsWith(IMAGES_SUPPORT_FORMATS[0]) || currentPath.toLowerCase().endsWith(IMAGES_SUPPORT_FORMATS[1])) {
                    System.out.println("LOADING IMAGE: " + currentPath.replace(resPath, ""));
                    imagesPaths.add(currentPath.replace(resPath, ""));
                    images.put(currentPath.replace(resPath, ""), new Image(currentPath.replace(resPath, "")));
                } else {
                    paths.remove(i);
                    i--;
                    System.err.println("IGNORE FILE: " + currentPath.replace(resPath, ""));
                }
            }
        }
    }

    private static void loadingFonts(){
        //LOADING IMAGES:
        System.out.println("\n\t> LOADING FONTS...");
        List<String> paths = null;
        Path start = Paths.get(resPath + fontsPath);
        try (Stream<Path> stream = Files.walk(start, 100)) {
            paths = stream.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
        }
        if(paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String currentPath = paths.get(i);

                if (currentPath.toLowerCase().endsWith(IMAGES_SUPPORT_FORMATS[0]) || currentPath.toLowerCase().endsWith(IMAGES_SUPPORT_FORMATS[1])) {
                    System.out.println("LOADING FONT: " + currentPath.replace(resPath, ""));
                    imagesPaths.add(currentPath.replace(resPath, ""));
                    images.put(currentPath.replace(resPath, ""), new Image(currentPath.replace(resPath, "")));
                } else {
                    paths.remove(i);
                    i--;
                    System.err.println("IGNORE FILE: " + currentPath.replace(resPath, ""));
                }
            }
        }
    }

    public static Image getImage(String path){

        if(path == null) return null;
        path = path.replace("/", File.separator);
        if(images.containsKey(path)){
            return images.get(path);
        }
        return null;

    }

    public static SoundData getSound(String path){
        path = path.replace("/", File.separator);
        return sounds.get(path);
    }

    public static ArrayList<Image> getAnimation(String path){
        path = path.replace("/", File.separator);
        return animations.get(path);
    }

    public static Sprite getImageCopy(String path){
        return null;
    }

}
