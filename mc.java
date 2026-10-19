import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class mc {
    //global vars
    private static int size=700;
    private static int dim=5;
    private static int delay=16;
    private static Camera cam=new Camera(0,10,-10);
    private static ArrayList<Object> data=new ArrayList<>();

    public static void main(String[] args) {
        //local vars and math
        Random rand=new Random();
        for (int x=0;x<dim;x++){
            for (int y=0;y<dim;y++){
                for (int z=0;z<dim;z++){
                    Object tmp=shapes.objcube(x*2,y*2,z*2+10);
                    tmp.setscale(new Point(1,1,1));
                    tmp.mesh.wire=false;
                    int typ=rand.nextInt(2);
                    if (typ==0){
                        tmp.show=false;
                    }else if (typ==1){
                        tmp.mesh.color=new ArrayList<>();
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#5b8731");
                        tmp.mesh.color.add("#5b8731");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                        tmp.mesh.color.add("#573d26");
                    }
                    data.add(tmp);
                }
            }
        }
        System.out.println("Triangle:"+dim*dim*dim*12+"\nPoints:"+dim*dim*dim*8);
        draw3d.setup("Cube Animation", size, size, delay,mc::draw);
    }
    public static void draw(Graphics g) {
        //drawing and more
        for (int i=0;i<data.size();i++){
            draw3d.renderobj(size,data.get(i),cam,g);
        }
        //cam.moverot(0,0);
        //cam.movepos(0,0,-0.1);
        System.out.println(math3d.getfps());
    }
}
