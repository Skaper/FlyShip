package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	private GameEngine gc;
	
	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];
	
	private final int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];
	
	private int mouseX, mouseY;
	private int scroll;
	
	private char lastKeyPressed;
	public Input(GameEngine gc) {
		this.gc = gc;
		scroll = 0;
		mouseX = 0;
		mouseY = 0;
		gc.getWindow().getCanvas().addKeyListener(this);
		gc.getWindow().getCanvas().addMouseListener(this);
		gc.getWindow().getCanvas().addMouseMotionListener(this);
		gc.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	public void update() {
		scroll = 0;
		for(int i=0; i <NUM_KEYS; i++) {
			keysLast[i] = keys[i];
		}
		for(int i=0; i <NUM_BUTTONS; i++) {
			buttons[i] = buttonsLast[i];
		}
	}
	
	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	
	public boolean isButton(int button) {
		return buttons[button];
	}
	
	public boolean isButtonUp(int button) {
		return !buttons[button] && buttonsLast[button];
	}
	
	public boolean isButtonDown(int button) {
		return buttons[button] && !buttonsLast[button];
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation()*-1; 
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX() / gc.getScale());
		mouseY = (int)(e.getY() / gc.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code != KeyEvent.VK_BACK_SPACE && 
				code != KeyEvent.VK_ENTER && 
				code != KeyEvent.VK_SHIFT &&
				code != KeyEvent.VK_DELETE) {
			lastKeyPressed = e.getKeyChar();
		}
		keys[e.getKeyCode()] = true;
	}
	
	public char getLastKey() {
		char tempKey = lastKeyPressed;
		lastKeyPressed = '\u0000';
		return tempKey;
	}
	
	

	@Override
	public void keyReleased(KeyEvent e) {
	
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public int getMouseX() {
		return mouseX;
	}
	public int getMouseWorldX() {
		return mouseX + (int)gc.getMainCamera().getOffX();
	}
	public int getMouseY() {
		return mouseY;
	}
	public int getMouseWorldY() {
		return mouseY + (int)gc.getMainCamera().getOffY();
	}

	public int getScroll() {
		return scroll;
	}

}
