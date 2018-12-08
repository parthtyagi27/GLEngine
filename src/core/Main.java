package core;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.Input;
import engine.Texture;
import engine.Window;

public class Main
{
	static Input windowInput;
	
	private static float x = 0, y = 0;
	private static Texture tex;
	
	public static void main(String[] args)
	{
		//Create Window Object and Display it
		Window window = new Window(600, 350, "PongGL");
		window.render();
		windowInput = new Input(window);
		
		//Tell GLFW to make the currentContext to the window object by referencing its ID
		GLFW.glfwMakeContextCurrent(window.getWindowID());
		//Get Ready to render
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		tex = new Texture("/res/paddle.jpg");
		
		while(window.isClosed() == false)
		{
			window.update();
			//rendering code here
			update();
			render();
			window.swapBuffers();
		}
		
		window.flush();
		GLFW.glfwTerminate();
		System.exit(0);
	}
	
	private static void render()
	{
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			//GL11.glColor4f(1, 0, 0, 0);
			GL11.glVertex2f(-0.5f, 0.5f);
			
			GL11.glTexCoord2f(1, 0);
			//GL11.glColor4f(0, 1, 0, 0);
			GL11.glVertex2f(0.5f, 0.5f);
			
			GL11.glTexCoord2f(1, 1);
			//GL11.glColor4f(0, 0, 1, 0);
			GL11.glVertex2f(0.5f, -0.5f);
			
			GL11.glTexCoord2f(0, 1);
			//GL11.glColor4f(1, 1, 1, 1);
			GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glEnd();
	}
	
	private static void update()
	{
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_RIGHT))
			x += 0.001f;
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_LEFT))
			x -= 0.0001f;
	}

}
