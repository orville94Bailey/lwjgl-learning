package Rendering;

import Interfaces.IDrawable;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class Renderer {
    public static void DrawQuad(Pair<Integer,Integer> position, Pair<Float, Float> dimensions, Triplet<Float,Float,Float> color) {
        GL11.glColor3f(color.getValue0(),color.getValue1(),color.getValue2());
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(position.getValue0()+(-.5f*dimensions.getValue0()), position.getValue1()+(-.5f*dimensions.getValue1()));
        GL11.glVertex2f(position.getValue0()+(.5f*dimensions.getValue0()), position.getValue1()+(-.5f*dimensions.getValue1()));
        GL11.glVertex2f(position.getValue0()+(.5f*dimensions.getValue0()), position.getValue1()+(.5f*dimensions.getValue1()));
        GL11.glVertex2f(position.getValue0()+(-.5f*dimensions.getValue0()), position.getValue1()+(.5f*dimensions.getValue1()));
        GL11.glEnd();
    }

    public static void DrawThings()
    {
        for (IDrawable drawable:
             drawables) {
            drawable.DrawMe();
        }
    }

    private static ArrayList<IDrawable> drawables = new ArrayList<>();

    public static void RegisterDrawable(IDrawable drawable)
    {
        if (! drawables.contains(drawable))
        {
            drawables.add(drawable);
        }
        System.out.println("added drawable");
    }

    public static boolean UnregisterDrawable(IDrawable drawable)
    {
        return drawables.remove(drawable);
    }

    public static final Pair<Float,Float> TileConstant = new Pair<>(10f,10f);
}
