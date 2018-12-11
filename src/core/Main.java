package core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import engine.Camera;
import engine.Input;
import engine.Model;
import engine.Shader;
import engine.Texture;
import engine.Timer;
import engine.Window;

public class Main
{
	static Input windowInput;
	
	public static final int WIDTH = 600, HEIGHT = 350;
	private static final double fpsCap = 1.0/60.0;
	private static double time, unprocessedTime = 0;
	
	private static float x = 0, y = 0;
	private static Texture tex;
	private static Model model;
	private static float[] textures;
	private static int[] indicies;
	private static Shader shader;
	private static Matrix4f scale;
	private static Matrix4f target;
	private static Camera camera;
	private static float[] verticies;
	
	public static void main(String[] args)
	{
		//Create Window Object and Display it
		Window window = new Window(WIDTH, HEIGHT, "PongGL");
		window.render();
		windowInput = new Input(window);
		
		//Tell GLFW to make the currentContext to the window object by referencing its ID
		GLFW.glfwMakeContextCurrent(window.getWindowID());
		//Get Ready to render
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));

		init();
		
		time = Timer.getTime();
		double frameTime = 0;
		unprocessedTime = 0;
		int frames = 0;
		
		while(window.isClosed() == false)
		{
			boolean canRender = false;
			double currentTime = Timer.getTime();
			double delta = currentTime - time;
			unprocessedTime += delta;
			frameTime += delta;
			time = currentTime;
			
			while(unprocessedTime >= fpsCap)
			{
				unprocessedTime -= fpsCap;
				
				canRender = true;
				
				//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				window.update();
				//rendering code here
				update();
				//render();
				//window.swapBuffers();
				//
				if(frameTime >= 1.0)
				{
					frameTime = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if(canRender)
			{
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				render();
				window.swapBuffers();
				frames++;
			}
		}
		
		window.flush();
		GL.destroy();
		GLFW.glfwTerminate();
		System.out.println("Disposing resources...");
		System.exit(0);
	}
	
	private static void init()
	{
		tex = new Texture("/res/badlogic.jpg");
		verticies = new float[]
				{
					-0.5f, 0.5f, 0, //Top left     1
					0.5f, 0.5f, 0,	//Top Right    2
					0.5f, -0.5f, 0, //Bottom Right 3
					-0.5f, -0.5f,0, //Bottom Left  4
					
				};
		
		textures = new float[]
		{
			0,0,
			1,0,
			1,1,
			0,1,
			
		};
		
		indicies = new int[]
		{
			0,1,2,
			2,3,0
		};
		
		scale = new Matrix4f().scale(128);
		target = new Matrix4f();
		camera = new Camera(WIDTH, HEIGHT);
		model = new Model(verticies, textures, indicies);
		shader = new Shader("/res/shader");
		
	}
	
	private static void render()
	{
		tex.bind();
		target = scale;
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection().mul(target));
		tex.bind(0);
		model.render();
		
	}
	
	private static void update()
	{
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_RIGHT))
			x += 0.005f;
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_LEFT))
			x -= 0.005f;
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_UP))
			y -= 0.005f;
		if(windowInput.isKeyDown(GLFW.GLFW_KEY_DOWN))
			y += 0.005f;

	}

}

