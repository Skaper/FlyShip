package engine;

import engine.gfx.IColor;

import static engine.GameSettings.*;


public class GameEngine implements Runnable{
	private Thread thread;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0/60.0;
	private int width = SCREEN_WIDTH, height = SCREEN_HEIGHT;
	private float scale = SCREEN_SCALE;
	private String title = "SKAPER ENGINE 0.0.1";
	
	private Window window;

	public Renderer getRenderer() {
		return renderer;
	}

	private Renderer renderer;
	private Input input;
	private ScenesManager scenesManager;
	private Scene currentScene;
	private String nextSceneTag;
	private Camera mainCamera;


	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
		if(mainCamera!=null){
			mainCamera.setupContent(currentScene);
			mainCamera.setup(this);
			mainCamera.update(this, (float)UPDATE_CAP);
			mainCamera.updateContent(this, (float)UPDATE_CAP);
		}
	}

	public Camera getMainCamera(){
		return mainCamera;
	}


	public GameEngine(ScenesManager scenesManager) {
		this.scenesManager = scenesManager;
	}
	
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		thread = new Thread(this);
		scenesManager.setup(this);
		currentScene = scenesManager.getMainScene();
		if(currentScene == null){
			System.err.println("Default Scene Not Set");
			return;
		}
		thread.run();
	}

	public void setScene(String tag){
		nextSceneTag = tag;
	}

	public void stop() {
		running = false;
	}
	
	public void run() {
		running = true;
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		currentScene.setupObjects(this);
		currentScene.setup(this);
		mainCamera.setupContent(currentScene);

		while(running) {
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			render = !LOCK_FPS;
			while(unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;

				currentScene.updateObjects(this, (float)UPDATE_CAP);;
				currentScene.update(this, (float)UPDATE_CAP);

				Physics.update();

				mainCamera.update(this, (float)UPDATE_CAP);
				mainCamera.updateContent(this, (float)UPDATE_CAP);
				if(frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
				input.update();
				render = true;
			}
			
			if(render) {
				renderer.clear();
				currentScene.render(this, renderer);
				currentScene.renderObjects(this, renderer);

				if(SHOW_FPS) {
					renderer.drawTextUI("FPS: " + fps, 0, 0, 2f, IColor.WHITE);
					renderer.drawTextUI("X: " + input.getMouseX() +
							" Y: " + input.getMouseY(), 80, 0, 2f,  IColor.WHITE);
					renderer.drawTextUI("WORLD X: " + renderer.getCamCenterX() +
							" Y: " + renderer.getCamCenterY(), 200, 0, 2f,  IColor.WHITE);
				}
				window.update();
				frames++;
			}else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(nextSceneTag!= null){
				if(!scenesManager.getCurrentSceneTag().equals(nextSceneTag)) {
					Scene scene = scenesManager.getNextScene(nextSceneTag);
					if (scene != null) {
						currentScene = scene;
						currentScene.destroy();
						mainCamera.reload(this);
						renderer.clear();
						currentScene.setup(this);

						mainCamera.setupContent(currentScene);
					} else {
						System.err.println("Cannot find scene <" + nextSceneTag + ">");
						nextSceneTag = null;
					}
				}
			}

		}
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
