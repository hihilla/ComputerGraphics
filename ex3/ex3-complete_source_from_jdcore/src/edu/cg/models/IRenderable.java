package edu.cg.models;

import com.jogamp.opengl.GL2;

public abstract interface IRenderable
{
  public static final int TOGGLE_LIGHT_SPHERES = 0;
  
  public abstract void render(GL2 paramGL2);
  
  public abstract void init(GL2 paramGL2);
  
  public abstract void control(int paramInt, Object paramObject);
  
  public abstract boolean isAnimated();
  
  public abstract void setCamera(GL2 paramGL2);
}
