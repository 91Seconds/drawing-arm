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
        textSize(20);
    }

    public void draw() {
        background(30,35,40,0);
        int i = (frameCount/20)%coOrds.length;
        i/=2;
        i*=2;
        targetX = coOrds[i];
        targetY = yCoOrdCenter -200 + coOrds[i+1];
//        targetX=mouseX;
//        targetY=mouseY;
//        targetX=xCoOrdCenter+160*sin((float)frameCount/16);
//        targetY=yCoOrdCenter+180*cos((float)frameCount/19);

        float[] elbows = findElbowPos();

        float theta1 = findTheta(elbows,1,-1);
        float theta2 = findTheta(elbows,2,1);

        rect(xCoOrdCenter,20,theta1*150,20);
        rect(xCoOrdCenter,50,theta2*150,20);
        fill(100);
        text("left",80,40);
        text("right",80,70);
        apparatus();
        drawArms(elbows,-1,1);
        gCursor();
    }

    private float findTheta(float[] elbows, int shoulderNum, int leftRight) {
        float angle;
        float X1=0,Y1=0,X2=0,Y2=0;
        if(shoulderNum==1) {
            X1=o1X;
            Y1=o1Y;
            if(leftRight<=0) {
                X2=elbows[0];
                Y2=elbows[1];
            }
            if(leftRight>0) {
                X2=elbows[2];
                Y2=elbows[3];
            }
        }
        else if(shoulderNum==2) {
            X1=o2X;
            Y1=o2X;
            if(leftRight<=0) {
                X2=elbows[4];
                Y2=elbows[5];
            }
            if(leftRight>0) {
                X2=elbows[6];
                Y2=elbows[7];
            }
        }
        else {
            println("shoulder number must be 1 or 2");
        }
        angle=atan2(X1-X2,Y1-Y2);
        return angle;
    }

    private void drawArms(float[] elbowPos, int leftConfig, int rightConfig) {
        stroke(0xff,0xff,0xff,0x22);
        line(elbowPos[0], elbowPos[1], elbowPos[2], elbowPos[3]);
        line(elbowPos[4], elbowPos[5], elbowPos[6], elbowPos[7]);
        ellipse((elbowPos[0] + elbowPos[2]) / 2, (elbowPos[1] + elbowPos[3]) / 2, absLength(elbowPos[0], elbowPos[2], elbowPos[1], elbowPos[3]), absLength(elbowPos[0], elbowPos[2], elbowPos[1], elbowPos[3]));
        ellipse((elbowPos[4] + elbowPos[6]) / 2, (elbowPos[5] + elbowPos[7]) / 2, absLength(elbowPos[4], elbowPos[6], elbowPos[5], elbowPos[7]), absLength(elbowPos[4], elbowPos[6], elbowPos[5], elbowPos[7]));

        if(leftConfig<=0) {
            stroke(0xff,0x00,0x00,0x88);
            line(targetX, targetY, elbowPos[0], elbowPos[1]);
            stroke(0x00,0x00,0xff,0x88);
            line(o1X,o1Y,elbowPos[0],elbowPos[1]);
        }
        if(rightConfig<=0) {
            stroke(0xff,0x00,0x00,0x88);
            line(targetX, targetY, elbowPos[4], elbowPos[5]);
            stroke(0x00,0x00,0xff,0x88);
            line(o2X,o2Y,elbowPos[4],elbowPos[5]);
        }

        if(leftConfig>=0) {
            stroke(0xff,0x00,0x00,0xdd);
            line(targetX, targetY, elbowPos[2], elbowPos[3]);
            stroke(0x00,0x00,0xff,0xdd);
            line(o1X,o1Y,elbowPos[2],elbowPos[3]);
        }
        if(rightConfig>=0) {
            stroke(0xff,0x00,0x00,0xdd);
            line(targetX, targetY, elbowPos[6], elbowPos[7]);
            stroke(0x00,0x00,0xff,0xdd);
            line(o2X,o2Y,elbowPos[6],elbowPos[7]);
        }




    }

    float[] findElbowPos() {
        //co-ordinates of the center point of a line drawn from the shoulders to the mouse
        float o1XC = (targetX+o1X)/2;
        float o1YC = (targetY+o1Y)/2;
        float o2XC = (targetX+o2X)/2;
        float o2YC = (targetY+o2Y)/2;

        //length of the line between the shoulders and the mouse
        float abs1 = absLength(o1X,targetX,o1Y,targetY);
        float abs2 = absLength(o2X,targetX,o2Y,targetY);

        //radius of a circle centered halfway between the shoulders and the mouse so that the distance
        //between its intersection with the reach of the shoulders and the mouse is equal to l
        float o1R = findOp(l,abs1/2);
        float o2R = findOp(l,abs2/2);

        //finds the angle of the line from the shoulders to the mouse
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

        float[] points = {o1NormalX1,o1NormalY1,o1NormalX2,o1NormalY2,o2NormalX1,o2NormalY1,o2NormalX2,o2NormalY2};
        return points;
    }

    private float absLength(float X1, float X2, float Y1, float Y2) {
        return sqrt(   pow(X1-X2,2)+pow(Y1-Y2,2)   );
    }

    private float findOp(float hyp, float adj) {
        return sqrt(pow(hyp,2)-pow(adj,2));
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
        stroke(0xff,0xff,0xff,0x22);
        ellipse(o1X,o1Y,2*l,2*l);
        ellipse(o2X,o2Y,2*l,2*l);
    }

    //displays the mouse position
    void gCursor() {
        noStroke();
        fill(200);
        ellipse(targetX,targetY,10,10);
    }
}
