import java.awt.*;

public class test1 {
    //global vars
    private static Camera cam=new Camera(5,1,-20);
    private static Object obj=shapes.objcube();

    public static void main(String[] args) {
        //local vars and math
        obj.mesh.wire=true;
        obj.addpos(0,0,10);
        obj.setscale(5,5,5);
        draw3d.setup("Cube Animation", 500, 500, 0, test1::draw);
    }
    public static void draw(Graphics g) {
        //drawing and more
        obj.addrot(0.01,0.01,0.01);
        draw3d.renderobj(500,obj,cam,g);
        System.out.println(math3d.getfps());
    }
}
