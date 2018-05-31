package edu.cg.models;

import com.jogamp.opengl.GL2;
import java.io.PrintStream;



public class Empty
  implements IRenderable
{
  private boolean isLightSpheres;
  
  public Empty() {}
  
  public void render(GL2 gl) {}
  
  public String toString()
  {
    return "Empty";
  }
  


  public void control(int type, Object params)
  {
    switch (type)
    {
    case 0: 
      isLightSpheres = (!isLightSpheres);
      break;
    
    default: 
      System.out.println("Control type not supported: " + toString() + ", " + type);
    }
  }
  
  public boolean isAnimated()
  {
    return false;
  }
  
  public void init(GL2 gl) {}
  
  public void setCamera(GL2 gl) {}
}
