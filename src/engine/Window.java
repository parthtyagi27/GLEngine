package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window
{
	private long window;
	private int width, height;
	private String title;
	
	public Window(int width, int height, String title)
	{
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void createCapabilities()
	{
		GL.createCapabilities();
	}
	
	public void render()
	{
		if(!GLFW.glfwInit())
		{
			System.err.println("Error: Could not init GLFW");
			System.exit(-1);
		}
		
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		
		if(window == 0)
		{
			System.err.println("Error: Could not init window");
			System.exit(-1);
		}
		
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (videoMode.width() - width)/2, (videoMode.height() - height)/2);
	}
	
	public boolean isClosed()
	{
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public void update()
	{
		GLFW.glfwPollEvents();
	}
	
	public void swapBuffers()
	{
		GLFW.glfwSwapBuffers(window);
	}
	
	public long getWindowID()
	{
		return window;
	}
	
	public void flush()
	{
		GLFW.glfwDestroyWindow(window);
	}
}
