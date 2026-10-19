import java.awt.*;
import java.util.ArrayList;

public class test2 {
    //global vars
    private static int size=700;
    private static int delay=0;
    private static Camera cam=new Camera(0,0,-10);
    private static ArrayList<Object> obj=new ArrayList<>();

    public static void main(String[] args) {
        //local vars and math
        int dimx=16;
        int dimy=16;
        int dimz=16;
        for (int x=0;x<dimx;x++){
            for (int y=0;y<dimy;y++){
                for (int z=0;z<dimz;z++){
                    Object tmp=shapes.objcube(x*3,y*3,z*3+10);
                    tmp.setscale(new Point(1,1,1));
                    tmp.mesh.wire=true;
                    obj.add(tmp);
                }
            }
        }
        System.out.println("Triangle:"+dimx*dimy*dimz*12+"\nPoints:"+dimx*dimy*dimz*8);
        draw3d.setup("Cube Animation", size, size, delay,test2::draw);
    }
    public static void draw(Graphics g) {
        //drawing and more
        for (int i=0;i<obj.size();i++){
            obj.get(i).addrot(0.1,0.1,0.1);
            draw3d.renderobj(size,obj.get(i),cam,g);
        }
        //cam.moverot(0,0);
        //cam.movepos(0,0,-0.1);
        System.out.println(math3d.getfps());
    }
}
