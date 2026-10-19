import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.ArrayList;

public class draw3d{
    public static void setup(String title,int sizex,int sizey,int delay,Consumer<Graphics> action){
        JFrame frame=new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizex, sizey);
        Panel panel=new Panel();
        panel.setDrawAction(action);
        frame.add(panel);
        frame.setVisible(true);
        panel.startTimer(()->{}, delay);
    }
    public static void draw2dpoint(int size,int x,int y,Color color,Graphics g) {
        Screenpoint p=math3d.bindto(size,x,y);
        g.setColor(color);
        g.fillRect((int)p.x-2,(int)p.y-2,4,4);
    }
    public static void draw3dpoint(int size,Point point,Camera cam,Color color,Graphics g) {
        Point a=math3d.make3d2d(cam, point);
        draw2dpoint(size,(int)a.x,(int)a.y,color,g);
    }
    public static void rendermesh(int size,Mesh mesh,Camera cam,Graphics g){
        ArrayList<Point> p=new ArrayList<>();
        ArrayList<Screenpoint> p2=new ArrayList<>();
        Boolean err=true;
        for (Point i:mesh.vert){
            if (i.x!=-10000){
                p.add(math3d.make3d2d(cam,i));
            }else{
                err=false;
            }
        }
        if (err){
            for (int i=0;i<p.size();i++){
                p2.add(i,math3d.bindto(size,p.get(i).x,p.get(i).y));
            }
            if (mesh.wire){
                for (Indextri i:mesh.tri){
                    g.drawPolygon(new int[]{(int)p2.get(i.i1).x,(int)p2.get(i.i2).x,(int)p2.get(i.i3).x},new int[]{(int)p2.get(i.i1).y,(int)p2.get(i.i2).y,(int)p2.get(i.i3).y},3);
                }
            }else{
                for (Indextri i:mesh.tri){
                    g.fillPolygon(new int[]{(int)p2.get(i.i1).x,(int)p2.get(i.i2).x,(int)p2.get(i.i3).x},new int[]{(int)p2.get(i.i1).y,(int)p2.get(i.i2).y,(int)p2.get(i.i3).y},3);
                }
            }
        }
    }
    public static void renderobj(int size,Object obj,Camera cam,Graphics g){
        if (obj.show){
            ArrayList<Point>vert=math3d.transformobjvert(obj);
            ArrayList<Triangle3d>tri=new ArrayList<>();
            for (int i=0;i<obj.mesh.tri.size();i++){
                Indextri e=obj.mesh.tri.get(i);
                ArrayList<Point> points=new ArrayList<>();
                points.add(math3d.make3d2d(cam,vert.get(e.i1)));
                points.add(math3d.make3d2d(cam,vert.get(e.i2)));
                points.add(math3d.make3d2d(cam,vert.get(e.i3)));
                if ((points.get(0).x==-10000)||(points.get(1).x==-10000)||(points.get(2).x==-10000)){
                    continue;
                }
                double z=(points.get(0).i3+points.get(1).i3+points.get(2).i3)/3;
                Triangle3d tmp=new Triangle3d();
                tmp.depth=z;
                tmp.index=i;
                tmp.points=points;
                tri.add(tmp);
            }
            tri.sort((a,b)->Double.compare(b.depth,a.depth));
            for (int ind=0;ind<tri.size();ind++){
                int i=tri.get(ind).index;
                ArrayList<Point> p=tri.get(ind).points;
                Screenpoint p1=math3d.bindto(size,p.get(0).x,p.get(0).y);
                Screenpoint p2=math3d.bindto(size,p.get(1).x,p.get(1).y);
                Screenpoint p3=math3d.bindto(size,p.get(2).x,p.get(2).y);
                if (obj.mesh.color.size()==1){
                    g.setColor(Color.decode(obj.mesh.color.get(0)));
                }else{
                    g.setColor(Color.decode(obj.mesh.color.get(i)));
                }
                if (obj.mesh.wire){
                    g.drawPolygon(new int[]{(int)p1.x,(int)p2.x,(int)p3.x},new int[]{(int)p1.y,(int)p2.y,(int)p3.y},3);
                }else{
                    g.fillPolygon(new int[]{(int)p1.x,(int)p2.x,(int)p3.x},new int[]{(int)p1.y,(int)p2.y,(int)p3.y},3);
                }
            }
        }
    }
}