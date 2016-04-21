package net.kerfuffle.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class Util {

	public static boolean onlyNumbers(String str)
	{
		int i;
		try
		{
			i = Integer.parseInt(str);
		}
		catch (NumberFormatException e)
		{
			str = "";
			return false;
		}
		
		return true;
	}
	
	/**
	 * Example
	 * Gets console input and returns it as a string.
	 * 
	 * String in = conIn();		<- Stores the input from the console in a string
	 */
	public static String conIn()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			return in.readLine();
		}
		catch (IOException e)
		{
			return "[ERROR] Could not read line.\n";
		}
	}
	
	/**
	 * Example
	 * This converts an array of strings into a vector of strings.
	 * 
	 * String blurb[] = {'H', 'I'};
	 * Vector <String> vecBlurb = stringArrayToVec(blurb);
	 * 
	 */
	public static Vector<String> stringArrayToVec(String str[])
	{
		Vector<String> vec = new Vector <String>();
		for (int i = 0; i < str.length; i++)
		{
			vec.add(str[i]);
		}
		return vec;
	}
	
	/**
	 * Example
	 * This converts a vector of strings into an array of strings.
	 * 
	 * Vector <String> blurb = new Vector<String>();
	 * blurb.add("poop");
	 * blurb.add("poopagain");
	 * 
	 * String arrayBlurb[] = vecToStringArray(blurb);
	 */
	public static String[] vecToStringArray(Vector <String> vec)
	{
		String str[] = new String[vec.size()];
		for (int i = 0; i < vec.size(); i++)
		{
			str[i] = vec.get(i);
		}
		return str;
	}
	
	/**
	 * Example
	 * This converts a vector of chars into a string.
	 * 
	 * Vector <Char> blurb = new Vector<Char>();
	 * blurb.add('h');
	 * blurb.add('i');
	 * 
	 * String strBlurb = vecToString(blurb);
	 */
	public static String vecToString(Vector vec)
	{
		String out = null;
		for (int i = 0; i < vec.size(); i++)
		{
			out += vec.get(i);
		}
		return out;
	}
	
	/**
	 * Example
	 * This is a function to output a string into a file.
	 * 
	 * outFile("Hello TextFile!", "out.txt");
	 */
	public static void outFile(String str, String name)
	{	
		PrintWriter out = null;
		try
		{
			out = new PrintWriter (new FileWriter(name));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) == '\n')
			{
				out.println();
			}
			else
			{
				out.print(str.charAt(i));
			}
		}
		out.close();
	}
	
	/**
	 * Example
	 * This is a function that reads a text file and exports it as a string.
	 * 
	 * String fileData = inFile("in.txt");
	 */
	public static String inFile(String name)
	{
		Vector<Character> c = new Vector<Character>();
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(name));
			int r;
			while((r = in.read()) != -1)
			{
				c.add((char) r);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String ret = vecToString(c);
		String fix[] = ret.split("null");
		
		return fix[1];
	}
	
	
	/****************************************************************************************************************************************
	 * OPENGL STUFF BELOW
	 */
	
	static boolean keyStates[] = new boolean [256];
	static boolean mouseStates[] = new boolean [2];
	
	public static void initGL()
	{
		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();		
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		//glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glEnable(GL_ALPHA);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glClearColor(1, 1, 1, 1);

		glDisable(GL_DEPTH_TEST);
	}
	
	public static void setDisplay(int width, int height, String name)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode (width, height));
			Display.setTitle(name);
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
		initGL();
	}
	
	public static void updateAndSync(int fps)
	{
		Display.update();
		Display.sync(fps);
	}
	
	public static void point(float x, float y)
	{
		glBegin(GL_POINTS);
		glVertex2f(x, y);
		glEnd();
	}
	
	public static void hollowCircle(float x, float y, float r)
	{
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < 100; i++)
		{
			float angle = (float) (2*Math.PI*i/100);
			float px = (float) ((r * Math.cos(angle)));
			float py = (float) ((r * Math.sin(angle)));
			glVertex2f(x + px, y + py);
		}
		glEnd();
	}
	
	public static void circle(float x, float y, float r)
	{
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y);
		for (float i = 1; i < 361; i += 0.2)
		{
			float x2 = (float) (x+Math.cos(i)*r);
			float y2 = (float) (y+Math.sin(i)*r);
			glVertex2f(x2, y2);
		}
		glEnd();
	}
	
	
	
	public static boolean hit(Quad pow, Quad p)
	{
		if ((p.x > pow.x && p.x < pow.x + pow.w) || (p.x + p.w > pow.x && p.x + p.w < pow.x + pow.w))	//check X
		{
			if ((p.y > pow.y && p.y < pow.y + pow.h) || (p.y + p.h > pow.y && p.y + p.h < pow.y + pow.h))	//check Y
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean hitRight(Quad p, Quad b)
	{
		/*if (b.x <= p.x+p.w && b.x >= p.x &&(b.y <= p.y+p.h && b.y+b.h >= p.y))
		{
			return true;
		}*/
		if (p.x <= b.x+b.w && p.x >= (b.x+b.w-10) && ((p.y >= b.y && p.y < b.y+b.h) || (p.y +p.h > b.y && p.y+p.h <=b.y+b.h)))
		{
			return true;
		}
		return false;
	}
	public static boolean hitLeft(Quad p, Quad b)
	{
		/*if (b.x+b.w >= p.x && b.x+b.w <= p.x+p.w &&(b.y <= p.y+p.h && b.y+b.h >= p.y))
		{
			return true;
		}*/
		if (p.x+p.w >= b.x && p.x+p.w <= (b.x+10) && ((p.y >= b.y && p.y < b.y+b.h) || (p.y +p.h > b.y && p.y+p.h <=b.y+b.h)))
		{
			return true;
		}
		return false;
	}
	public static boolean hitUp(Quad p, Quad b)
	{
		/*if (b.y+b.h >= p.y && b.y+b.h <= p.y+p.h && (b.x <= p.x+p.w && b.x >= p.x))
		{
			return true;
		}*/
		if (p.y <= b.y + b.h && p.y >= b.y + b.h - 10 && ((p.x <= b.x+b.w && p.x >= b.x) || (p.x+p.w >= b.x && p.x+p.w < b.x+b.w)))
		{
			return true;
		}
		return false;
	}
	public static boolean hitDown(Quad p, Quad b)
	{
		/*if (b.y <= p.y+p.h && b.y >= p.y &&((b.x <= p.x+p.w && b.x >= p.x)))
		{
			return true;
		}*/
		if (p.y+p.h >= b.y && p.y+p.h <= b.y + 10 && ((p.x <= b.x+b.w && p.x >= b.x) || (p.x+p.w >= b.x && p.x+p.w < b.x+b.w)))
		{
			return true;
		}
		return false;
	}
	
	public static boolean hitUpPong(Quad p, Quad b, float yVel)
	{
		/*if (b.y+b.h >= p.y && b.y+b.h <= p.y+p.h && (b.x <= p.x+p.w && b.x >= p.x))
		{
			return true;
		}*/
		if (p.y <= b.y + b.h && p.y > b.y + b.h - 10 && ((p.x <= b.x+b.w && p.x >= b.x) || (p.x+p.w >= b.x && p.x+p.w < b.x+b.w)))
		{
			return true;
		}
		return false;
	}
	public static boolean hitDownPong(Quad p, Quad b, float yVel)
	{
		/*if (b.y <= p.y+p.h && b.y >= p.y &&((b.x <= p.x+p.w && b.x >= p.x)))
		{
			return true;
		}*/
		System.out.println(p.y + ", " + b.y);
		if (p.y+p.h >= b.y && p.y+p.h <= b.y + 10 && ((p.x <= b.x+b.w && p.x >= b.x) || (p.x+p.w >= b.x && p.x+p.w < b.x+b.w)))
		{
			return true;
		}
		return false;
	}
	
	public static RGB randomColor()
	{
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new RGB(r, g, b);
	}
	
	public static void color(RGB color)
	{
		if (color.A() == -1)
		{
			glColor3f(color.R(), color.G(), color.B());
		}
		else
		{
			glColor4f(color.R(), color.G(), color.B(), color.A());
		}
		
	}
	
	public static String abbrev(String str, int limit)
	{
		String ret = "";
		if (str.length() > limit)
		{
			char cstr[] = str.toCharArray();
			char newStr[] = new char[limit];
			
			for (int i = str.length()-limit; i < str.length(); i++)
			{
				//System.out.println(cstr[i]);
				int j = i-(str.length()-limit);
				newStr[j] = cstr[i];
			}
			
			ret = new String(newStr);
		}
		else
		{
			ret=str;
		}
		return ret;
	}
	
	public static String lineInFile(String filename) throws IOException
	{
		String ret = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			ret = sb.toString();
		}
		finally
		{
			br.close();
		}
		return ret;
	}
	
	public static void lineH(float x, float y, float w)
	{
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(x + w, y);
		glEnd();
	}
	
	public static void lineV(float x, float y, float h)
	{
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(x, y+h);
		glEnd();
	}
	
	public static void line(float x, float y, float x2, float y2)
	{
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(x2, y2);
		glEnd();
	}
	
	public static void quad(float x, float y, float w, float h)
	{
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x, y + h);
		glVertex2f(x + w, y + h);
		glVertex2f(x + w, y);
		glEnd();
	}
	public static void quad(Coord c, Size s)
	{
		quad(c.getX(), c.getY(), s.getWidth(), s.getHeight());
	}
	
	public static void quadTex(float x, float y, float w, float h, Texture tex)
	{
		tex.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2f(1, 1);		
		glVertex2f(x, y);
		glTexCoord2f(1, 0); 	
		glVertex2f(x, y + h);
		glTexCoord2f(0, 0);		
		glVertex2f(x + w, y + h);
		glTexCoord2f(0, 1);		
		glVertex2f(x + w, y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		//tex.release();
	}
	
	public static void shiftX(int x)
	{
		glTranslatef(x, 0, 0);
	}
	
	public static void shiftY (int y)
	{
		glTranslatef(0, y, 0);
	}
	
	public static boolean checkKey(int i)
	{
		if (Keyboard.isKeyDown(i) != keyStates[i])
		{
			return keyStates[i] = !keyStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static boolean clickElseWhere(Quad q, int mx, int my)
	{
		if (checkMouse(0) && !onClick(q, mx, my))
		{
			return true;
		}
		return false;
	}
	
	public static String scanKeys(String blurb, int maxChars)
	{
		StringBuilder str = new StringBuilder(blurb);
		
		if (checkKey(Keyboard.KEY_BACK))
		{
			if (str.length() > 0)
			{
				str.deleteCharAt(str.length()-1);
			}
		}
		if (str.length() >= maxChars)
		{
			return str.toString();
		}
		
	
		if (checkKey(Keyboard.KEY_RETURN))
		{
			str.append("\n");
		}
		if (checkKey(Keyboard.KEY_A))
		{
			str.append("a");
		}
		if (checkKey(Keyboard.KEY_B))
		{
			str.append("b");
		}
		if (checkKey(Keyboard.KEY_C))
		{
			str.append("c");
		}
		if (checkKey(Keyboard.KEY_D))
		{
			str.append("d");
		}
		if (checkKey(Keyboard.KEY_E))
		{
			str.append("e");
		}
		if (checkKey(Keyboard.KEY_F))
		{
			str.append("f");
		}
		if (checkKey(Keyboard.KEY_G))
		{
			str.append("g");
		}
		if (checkKey(Keyboard.KEY_H))
		{
			str.append("h");
		}
		if (checkKey(Keyboard.KEY_I))
		{
			str.append("i");
		}
		if (checkKey(Keyboard.KEY_J))
		{
			str.append("j");
		}
		if (checkKey(Keyboard.KEY_K))
		{
			str.append("k");
		}
		if (checkKey(Keyboard.KEY_L))
		{
			str.append("l");
		}
		if (checkKey(Keyboard.KEY_M))
		{
			str.append("m");
		}
		if (checkKey(Keyboard.KEY_N))
		{
			str.append("n");
		}
		if (checkKey(Keyboard.KEY_O))
		{
			str.append("o");
		}
		if (checkKey(Keyboard.KEY_P))
		{
			str.append("p");
		}
		if (checkKey(Keyboard.KEY_Q))
		{
			str.append("q");
		}
		if (checkKey(Keyboard.KEY_R))
		{
			str.append("r");
		}
		if (checkKey(Keyboard.KEY_S))
		{
			str.append("s");
		}
		if (checkKey(Keyboard.KEY_T))
		{
			str.append("t");
		}
		if (checkKey(Keyboard.KEY_U))
		{
			str.append("u");
		}
		if (checkKey(Keyboard.KEY_V))
		{
			str.append("v");
		}
		if (checkKey(Keyboard.KEY_W))
		{
			str.append("w");
		}
		if (checkKey(Keyboard.KEY_X))
		{
			str.append("x");
		}
		if (checkKey(Keyboard.KEY_Y))
		{
			str.append("y");
		}
		if (checkKey(Keyboard.KEY_Z))
		{
			str.append("z");
		}
		
		if (checkKey(Keyboard.KEY_0))
		{
			str.append("0");
		}
		if (checkKey(Keyboard.KEY_1))
		{
			str.append("1");
		}
		if (checkKey(Keyboard.KEY_2))
		{
			str.append("2");
		}
		if (checkKey(Keyboard.KEY_3))
		{
			str.append("3");
		}
		if (checkKey(Keyboard.KEY_4))
		{
			str.append("4");
		}
		if (checkKey(Keyboard.KEY_5))
		{
			str.append("5");
		}
		if (checkKey(Keyboard.KEY_6))
		{
			str.append("6");
		}
		if (checkKey(Keyboard.KEY_7))
		{
			str.append("7");
		}
		if (checkKey(Keyboard.KEY_8))
		{
			str.append("8");
		}
		if (checkKey(Keyboard.KEY_9))
		{
			str.append("9");
		}
		
		if (checkKey(Keyboard.KEY_SPACE))
		{
			str.append(" ");
		}
		if (checkKey(Keyboard.KEY_PERIOD))
		{
			str.append(".");
		}
		if (checkKey(Keyboard.KEY_SLASH))
		{
			str.append("/");
		}
		if (checkKey(Keyboard.KEY_BACKSLASH))
		{
			str.append("\\");
		}
		if (checkKey(Keyboard.KEY_COLON) || checkKey(Keyboard.KEY_SEMICOLON))
		{
			str.append(":");
		}
		
		return str.toString();
	}
	
	public static boolean checkMouse(int i)
	{
		if (Mouse.isButtonDown(i) != mouseStates[i])
		{
			return mouseStates[i] = !mouseStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onHover(float x, float y, float w, float h, float mx, float my)
	{
		if ((mx >= x && mx <= x + w) && (my >= y && my <= y + h))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onHover(Quad q, float mx, float my)
	{
		if (onHover(q.x, q.y, q.w, q.h, mx, my))
		{
			return true;
		}
		return false;
	}
	
	public static boolean onRClick(int x, int y, int w, int h)
	{
		if ((Mouse.getX() >= x && Mouse.getX() <= x + w) && (Mouse.getY() >= y && Mouse.getY() <= y + h) && checkMouse(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onClickandDrag(int x, int y, int w, int h)
	{
		if ((Mouse.getX() >= x && Mouse.getX() <= x + w) && (Mouse.getY() >= y && Mouse.getY() <= y + h) && Mouse.isButtonDown(0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onClick(Quad q, float mx, float my)
	{
		if (onClick(q.x, q.y, q.w, q.h, mx, my))
		{
			return true;
		}
		return false;
	}
	
	public static boolean onClick(float x, float y, float w, float h, float mx, float my)
	{
		if (onHover(x, y, w, h, mx, my) && checkMouse(0))
		{
			return true;
		}
		return false;
		
		/*if ((Mouse.getX() >= x && Mouse.getX() <= x + w) && (Mouse.getY() >= y && Mouse.getY() <= y + h) && checkMouse(0))
		{
			return true;
		}
		else
		{
			return false;
		}*/
	}
	
	public static boolean keyDown(int key)
	{
		if (Keyboard.isKeyDown(key))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Texture loadTex(String name)
	{
		try
		{
			return TextureLoader.getTexture("PNG", new FileInputStream(new File(name + ".png")));
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("File not found!");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void drawString(String s, float x, float y) {
		float startX = x;
		GL11.glBegin(GL11.GL_POINTS);
		//glColor3f(0f, 0f, 0f);
		if (s.equals("!^"))
		{
			for (int i = 0; i<=10; i++)
			{
				glVertex2f(x - i, y + i);
				glVertex2f(x + i, y + i);
			}
		}
		else
		{
			for (char c : s.toLowerCase().toCharArray()) {

				if (c=='/')
				{
					for (int i = 0; i <= 8; i ++)
					{
						glVertex2f(x + i, y + i);
					}
					x += 8;
				}
				else if (c== '\\')
				{
					for (int i = 0; i <= 8; i++)
					{
						glVertex2f(8 + (x - i), y + i);
					}
					x += 8;
				}
				else if (c == '<')
				{
					for (int i =0; i <= 10; i++)
					{
						glVertex2f(x + i, y + i);
					}
					for (int i=0; i <=10; i++)
					{
						glVertex2f(x + i, y - i);
					}
					x += 8;
				}

				else if (c == '>')
				{
					for (int i=0; i <= 10; i++)
					{
						glVertex2f(x - i, y + i);
					}
					for (int i=0; i <= 10; i++)
					{
						glVertex2f(x - i, y - i);
					}
					x += 8;
				}

				else if (c== '^')
				{
					for (int i = 0; i <= 10; i++)
					{
						glVertex2f(x - i, y - i);
					}
					for (int i = 0; i <=10; i++)
					{
						glVertex2f(x + i, y - i);
					}
					x += 8;
				}

				else if (c == '-')
				{
					for (int i = 0; i <= 6; i ++)
					{
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				}

				else if (c == '=')
				{
					for (int i = 0; i <= 6; i++)
					{
						GL11.glVertex2f(x + i, y + 2);
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				}
				else if (c == ':')
				{
					GL11.glVertex2f(x + 1.5f, y);
					GL11.glVertex2f(x + 2.5f, y);
					GL11.glVertex2f(x + 1.5f, y + 1);
					GL11.glVertex2f(x + 2.5f, y + 1);

					GL11.glVertex2f(x + 1.5f, y + 7);
					GL11.glVertex2f(x + 2.5f, y + 7);
					GL11.glVertex2f(x + 2.5f, y + 6);
					GL11.glVertex2f(x + 1.5f, y + 6);
					
					x += 8;
				}
				else if (c == 'a') {
					for (int i = 0; i < 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'b') {
					for (int i = 0; i < 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 4);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 7, y + 5);
					GL11.glVertex2f(x + 7, y + 7);
					GL11.glVertex2f(x + 7, y + 6);
					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 3);
					x += 8;
				} else if (c == 'c') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 6, y + 2);

					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 7);

					x += 8;
				} else if (c == 'd') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 6, y + 2);
					GL11.glVertex2f(x + 6, y + 3);
					GL11.glVertex2f(x + 6, y + 4);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 7);

					x += 8;
				} else if (c == 'e') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 0);
						GL11.glVertex2f(x + i, y + 8);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'f') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'g') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 6, y + 2);
					GL11.glVertex2f(x + 6, y + 3);
					GL11.glVertex2f(x + 5, y + 3);
					GL11.glVertex2f(x + 7, y + 3);

					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 7);

					x += 8;
				} else if (c == 'h') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == 'i') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 3, y + i);
					}
					for (int i = 1; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 0);
						GL11.glVertex2f(x + i, y + 8);
					}
					x += 7;
				} else if (c == 'j') {
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 6, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 0);
					}
					GL11.glVertex2f(x + 1, y + 3);
					GL11.glVertex2f(x + 1, y + 2);
					GL11.glVertex2f(x + 1, y + 1);
					x += 8;
				} else if (c == 'k') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					GL11.glVertex2f(x + 6, y + 8);
					GL11.glVertex2f(x + 5, y + 7);
					GL11.glVertex2f(x + 4, y + 6);
					GL11.glVertex2f(x + 3, y + 5);
					GL11.glVertex2f(x + 2, y + 4);
					GL11.glVertex2f(x + 2, y + 3);
					GL11.glVertex2f(x + 3, y + 4);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 5, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 7, y);
					x += 8;
				} else if (c == 'l') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					x += 7;
				} else if (c == 'm') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					GL11.glVertex2f(x + 3, y + 6);
					GL11.glVertex2f(x + 2, y + 7);
					GL11.glVertex2f(x + 4, y + 5);

					GL11.glVertex2f(x + 5, y + 6);
					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 4, y + 5);
					x += 8;
				} else if (c == 'n') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					GL11.glVertex2f(x + 2, y + 7);
					GL11.glVertex2f(x + 2, y + 6);
					GL11.glVertex2f(x + 3, y + 5);
					GL11.glVertex2f(x + 4, y + 4);
					GL11.glVertex2f(x + 5, y + 3);
					GL11.glVertex2f(x + 6, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					x += 8;
				} else if (c == 'o' || c == '0') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 0);
					}
					x += 8;
				} else if (c == 'p') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 4);
					}
					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 6, y + 6);
					x += 8;
				} else if (c == 'q') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
						if (i != 1)
							GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						if (i != 6)
							GL11.glVertex2f(x + i, y + 0);
					}
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 5, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 7, y);
					x += 8;
				} else if (c == 'r') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 4);
					}
					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 6, y + 6);

					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 5, y + 2);
					GL11.glVertex2f(x + 6, y + 1);
					GL11.glVertex2f(x + 7, y);
					x += 8;
				} else if (c == 's') {
					for (int i = 2; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 1, y + 7);
					GL11.glVertex2f(x + 1, y + 6);
					GL11.glVertex2f(x + 1, y + 5);
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
						GL11.glVertex2f(x + i, y);
					}
					GL11.glVertex2f(x + 7, y + 3);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 1, y + 1);
					GL11.glVertex2f(x + 1, y + 2);
					x += 8;
				} else if (c == 't') {
					for (int i = 0; i <= 8; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					x += 7;
				} else if (c == 'u') {
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 0);
					}
					x += 8;
				} else if (c == 'v') {
					for (int i = 2; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 6, y + i);
					}
					GL11.glVertex2f(x + 2, y + 1);
					GL11.glVertex2f(x + 5, y + 1);
					GL11.glVertex2f(x + 3, y);
					GL11.glVertex2f(x + 4, y);
					x += 7;
				} else if (c == 'w') {
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					GL11.glVertex2f(x + 2, y);
					GL11.glVertex2f(x + 3, y);
					GL11.glVertex2f(x + 5, y);
					GL11.glVertex2f(x + 6, y);
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					x += 8;
				} else if (c == 'x') {
					for (int i = 1; i <= 7; i++)
						GL11.glVertex2f(x + i, y + i);
					for (int i = 7; i >= 1; i--)
						GL11.glVertex2f(x + i, y + 8 - i);
					x += 8;
				} else if (c == 'y') {
					GL11.glVertex2f(x + 4, y);
					GL11.glVertex2f(x + 4, y + 1);
					GL11.glVertex2f(x + 4, y + 2);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 4, y + 4);

					GL11.glVertex2f(x + 3, y + 5);
					GL11.glVertex2f(x + 2, y + 6);
					GL11.glVertex2f(x + 1, y + 7);
					GL11.glVertex2f(x + 1, y + 8);

					GL11.glVertex2f(x + 5, y + 5);
					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 7, y + 7);
					GL11.glVertex2f(x + 7, y + 8);
					x += 8;
				} else if (c == 'z') {
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + i);
					}
					GL11.glVertex2f(x + 6, y + 7);
					x += 8;
				} else if (c == '1') {
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 1; i <= 8; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					GL11.glVertex2f(x + 3, y + 7);
					x += 8;
				} else if (c == '2') {
					for (int i = 1; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 1, y + 7);
					GL11.glVertex2f(x + 1, y + 6);

					GL11.glVertex2f(x + 6, y + 7);
					GL11.glVertex2f(x + 6, y + 6);
					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 5, y + 4);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 3, y + 2);
					GL11.glVertex2f(x + 2, y + 1);
					x += 8;
				} else if (c == '3') {
					for (int i = 1; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 6, y + i);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == '4') {
					for (int i = 2; i <= 8; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 1);
					}
					for (int i = 0; i <= 4; i++) {
						GL11.glVertex2f(x + 4, y + i);
					}
					x += 8;
				} else if (c == '5') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + i, y + 8);
					}
					for (int i = 4; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					GL11.glVertex2f(x + 1, y + 1);
					GL11.glVertex2f(x + 2, y);
					GL11.glVertex2f(x + 3, y);
					GL11.glVertex2f(x + 4, y);
					GL11.glVertex2f(x + 5, y);
					GL11.glVertex2f(x + 6, y);

					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 3);

					GL11.glVertex2f(x + 6, y + 4);
					GL11.glVertex2f(x + 5, y + 4);
					GL11.glVertex2f(x + 4, y + 4);
					GL11.glVertex2f(x + 3, y + 4);
					GL11.glVertex2f(x + 2, y + 4);
					x += 8;
				} else if (c == '6') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y);
					}
					for (int i = 2; i <= 5; i++) {
						GL11.glVertex2f(x + i, y + 4);
						GL11.glVertex2f(x + i, y + 8);
					}
					GL11.glVertex2f(x + 7, y + 1);
					GL11.glVertex2f(x + 7, y + 2);
					GL11.glVertex2f(x + 7, y + 3);
					GL11.glVertex2f(x + 6, y + 4);
					x += 8;
				} else if (c == '7') {
					for (int i = 0; i <= 7; i++)
						GL11.glVertex2f(x + i, y + 8);
					GL11.glVertex2f(x + 7, y + 7);
					GL11.glVertex2f(x + 7, y + 6);

					GL11.glVertex2f(x + 6, y + 5);
					GL11.glVertex2f(x + 5, y + 4);
					GL11.glVertex2f(x + 4, y + 3);
					GL11.glVertex2f(x + 3, y + 2);
					GL11.glVertex2f(x + 2, y + 1);
					GL11.glVertex2f(x + 1, y);
					x += 8;
				} else if (c == '8') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 0);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					x += 8;
				} else if (c == '9') {
					for (int i = 1; i <= 7; i++) {
						GL11.glVertex2f(x + 7, y + i);
					}
					for (int i = 5; i <= 7; i++) {
						GL11.glVertex2f(x + 1, y + i);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 8);
						GL11.glVertex2f(x + i, y + 0);
					}
					for (int i = 2; i <= 6; i++) {
						GL11.glVertex2f(x + i, y + 4);
					}
					GL11.glVertex2f(x + 1, y + 0);
					x += 8;
				} else if (c == '.') {
					GL11.glVertex2f(x + 1, y);
					x += 2;
				} else if (c == ',') {
					GL11.glVertex2f(x + 1, y);
					GL11.glVertex2f(x + 1, y + 1);
					x += 2;
				} else if (c == '\n') {
					y -= 15;
					x = startX;
				} else if (c == ' ') {
					x += 8;
				}
			}
		}
		GL11.glEnd();
	}
	
}
