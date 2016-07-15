import processing.core.PApplet;

import java.lang.invoke.MethodHandles;

/**
 * Created by surface on 15/07/2016.
 */
public class Main extends PApplet {

    public static Main instance;

    public static void main(String[] args) {
        main(MethodHandles.lookup().lookupClass().getName());
    }

    public Main() {
        instance = this;
    }

    //2*lengths ulnar and forearm
    final float l=180;
    //2*distance between shoulders
    final float d=236;

    //centerpoint between shoulders
    float xCoOrdCenter;
    float yCoOrdCenter;

    //o is for shoulder, 1 means left, 2 meas right
    float o1X;
    float o1Y;
    float o2X;
    float o2Y;

    float targetX, targetY;
    //not used
    //float h1X, h1Y, h2X, h2Y;

    float[] coOrds;

    public void settings() {
        size(1000,1000);
    }

    public void setup() {
        coOrds = new XMLer().pointsFromXML("file.svg");
        xCoOrdCenter=width/2;
        yCoOrdCenter=height/2;
        o1X = xCoOrdCenter-d/2;
        o1Y = yCoOrdCenter;
        o2X = xCoOrdCenter+d/2;
        o2Y = xCoOrdCenter;
    }

    public void draw() {
        background(30,35,40,0);
        int i = (frameCount/20)%coOrds.length;
        i/=2;
        i*=2;
        //targetX = coOrds[i];
        //targetY = xCoOrdCenter + coOrds[i+1];
        targetX=mouseX;
        targetY=mouseY;
        ////targetX=xCoOrdCenter+160*sin((float)frameCount/16);
        //targetY=yCoOrdCenter+180*cos((float)frameCount/19);

        apparatus();
        arms();
        gCursor();
    }

    void arms() {
        rect(o1X,o1Y,10,10);
        //co-ordinates of the center point of a line drawn from the shoulders to the mouse
        float o1XC = (targetX+o1X)/2;
        float o1YC = (targetY+o1Y)/2;
        float o2XC = (targetX+o2X)/2;
        float o2YC = (targetY+o2Y)/2;

        //length of the line between the shoulders and the mouse
        float abs1 = sqrt(   pow(o1X-targetX,2)+pow(o1Y-targetY,2)   );
        float abs2 = sqrt(   pow(o2X-targetX,2)+pow(o2Y-targetY,2)   );

        //radius of a circle centered halfway between the shoulders and the mouse so that the distance
        //between its intersection with the reach of the shoulders and the mouse is equal to l
        float o1R = findRadius(l,abs1);
        float o2R = findRadius(l,abs2); //(pow(((mouseX-o2X)/2),2)-pow(mouseY-o2Y,2)));

        //draws that circle
        stroke(80);
        ellipse(o1XC,o1YC,2*o1R,2*o1R);
        ellipse(o2XC,o2YC,2*o2R,2*o2R);

        //finds the angle of the line fromn the shoulders to the mouse
        //and the inverse reciprocal
        float o1Angle = atan2((targetY-o1Y),(targetX-o1X));
        float o1NormalAngle =atan2(-(targetX-o1X),(targetY-o1Y));
        float o2Angle = atan2((targetY-o2Y),(targetX-o2X));
        float o2NormalAngle =atan2(-(targetX-o2X),(targetY-o2Y));

        //uses the length of the line as determined by the radius of the circle
        //and the slope of the line as determined by the inverse reciprocal of the angle
        //to determine the co-ordinates where the normal intersetcs the reach of the ulnar
        float o1NormalX1 = o1XC + o1R*cos(o1NormalAngle);
        float o1NormalY1 = o1YC + o1R*sin(o1NormalAngle);
        float o1NormalX2 = o1XC - o1R*cos(o1NormalAngle);
        float o1NormalY2 = o1YC - o1R*sin(o1NormalAngle);

        float o2NormalX1 = o2XC + o2R*cos(o2NormalAngle);
        float o2NormalY1 = o2YC + o2R*sin(o2NormalAngle);
        float o2NormalX2 = o2XC - o2R*cos(o2NormalAngle);
        float o2NormalY2 = o2YC - o2R*sin(o2NormalAngle);

        //draws the normals
        stroke(60);
        line(o1NormalX2,o1NormalY2,o1NormalX1,o1NormalY1);
        line(o2NormalX2,o2NormalY2,o2NormalX1,o2NormalY1);

        //draws the one possiblility for the ulnars
        stroke(255,0,0);
        line(o1X,o1Y,o1NormalX1,o1NormalY1);
        line(o2X,o2Y,o2NormalX1,o2NormalY1);

        //draws the other possiblility for the ulnars
        stroke(150,0,0);
        line(o1X,o1Y,o1NormalX2,o1NormalY2);
        line(o2X,o2Y,o2NormalX2,o2NormalY2);

        //draws one posssiblility for the foreamrs
        stroke(0,0,255);
        line(targetX,targetY,o1NormalX1,o1NormalY1);
        line(targetX,targetY,o2NormalX1,o2NormalY1);

        //draws the other possiblility for the forearms
        stroke(0,0,150);
        line(targetX,targetY,o1NormalX2,o1NormalY2);
        line(targetX,targetY,o2NormalX2,o2NormalY2);






        //draws a straight line from shoulder to mouse
        stroke(100);
        line(o1X,o1Y,targetX,targetY);
        line(o2X,o2Y,targetX,targetY);
    }

    private float findRadius(float l, float length) {
        return sqrt(pow(l,2)-pow(length/2,2));
    }

    //draws the reach of the ulnars pivoting from shoulders and the total working
    //area as the intersections of two ellipses centered at the shoulders
    void apparatus() {
        fill(50,50,60);
        ellipse(o1X,o1Y,2*l,2*l);
        ellipse(o2X,o2Y,2*l,2*l);
        stroke(200);
        noFill();
        ellipse(o1X,o1Y,4*l,4*l);
        ellipse(o2X,o2Y,4*l,4*l);
    }

    //displays the mouse position
    void gCursor() {
        noStroke();
        fill(200);
        ellipse(targetX,targetY,10,10);
    }
}
