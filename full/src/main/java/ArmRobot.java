/**
 * Created by surface on 16/07/2016.
 */
public class ArmRobot {

    //2*lengths ulnar and forearm
    float l=180;
    //2*distance between shoulders
    float d=236;

    //o is for shoulder, 1 means left, 2 meas right
    float o1X;
    float o1Y;
    float o2X;
    float o2Y;


    public ArmRobot(float shoulder1X, float shoulder1Y, float shoulder2X, float shoulder2Y) {
        o1X=shoulder1X;
        o1Y=shoulder1Y;
        o2X=shoulder2X;
        o2Y=shoulder2Y;
    }

    public ArmRobot(float shoulder1X, float shoulder1Y, float shoulder2X, float shoulder2Y, float armLength, float seperation) {
        o1X=shoulder1X;
        o1Y=shoulder1Y;
        o2X=shoulder2X;
        o2Y=shoulder2Y;
        l=armLength;
        d=seperation;
    }

    public float findTheta(float[] elbows, int shoulderNum, int leftRight) {
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
            System.out.println("shoulder number must be 1 or 2");
        }
        angle=(float)Math.atan2(X1-X2,Y1-Y2);
        return angle;
    }

    float[] findElbowPos(float targetX, float targetY) {
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
        float o1Angle = (float)Math.atan2((targetY-o1Y),(targetX-o1X));
        float o1NormalAngle = (float)Math.atan2(-(targetX-o1X),(targetY-o1Y));
        float o2Angle = (float)Math.atan2((targetY-o2Y),(targetX-o2X));
        float o2NormalAngle = (float)Math.atan2(-(targetX-o2X),(targetY-o2Y));

        //uses the length of the line as determined by the radius of the circle
        //and the slope of the line as determined by the inverse reciprocal of the angle
        //to determine the co-ordinates where the normal intersetcs the reach of the ulnar
        float o1NormalX1 = o1XC + o1R*(float)Math.cos(o1NormalAngle);
        float o1NormalY1 = o1YC + o1R*(float)Math.sin(o1NormalAngle);
        float o1NormalX2 = o1XC - o1R*(float)Math.cos(o1NormalAngle);
        float o1NormalY2 = o1YC - o1R*(float)Math.sin(o1NormalAngle);

        float o2NormalX1 = o2XC + o2R*(float)Math.cos(o2NormalAngle);
        float o2NormalY1 = o2YC + o2R*(float)Math.sin(o2NormalAngle);
        float o2NormalX2 = o2XC - o2R*(float)Math.cos(o2NormalAngle);
        float o2NormalY2 = o2YC - o2R*(float)Math.sin(o2NormalAngle);

        float[] points = {o1NormalX1,o1NormalY1,o1NormalX2,o1NormalY2,o2NormalX1,o2NormalY1,o2NormalX2,o2NormalY2};
        return points;
    }

    float[] findTCPPos(float theta1, float theta2) {

        //e is for elbow, 1 is for left, 2 is for right
        float e1X, e1Y, e2X, e2Y;
        e1X=(float)Math.cos((float)theta1);
        e1Y=(float)Math.sin((float)theta1);
        e2X=(float)Math.cos((float)theta2);
        e2Y=(float)Math.sin((float)theta2);

        //elbow center x, elbow center y; the half way point between the elbows
        float eCX = (e1X+e2X)/2;
        float eCY = (e1Y+e2Y)/2;

        float tcpRad = findOp(l,absLength(e1X,e2X,e1Y,e2Y));

        //angle of a line passing through both elbows
        float elbowLineAngle = (float)Math.atan2(e1Y-e2Y,e1X-e2X);
        //inverse reciprocal of that line's angle
        float invRepElbowLineAngle = (float)Math.atan2(e1X-e2X,-(e1Y-e2Y));

        float[] points = new float[4];

        points[0] = eCX + tcpRad*(float)Math.cos(invRepElbowLineAngle);
        points[1] = eCY + tcpRad*(float)Math.cos(invRepElbowLineAngle);
        points[2] = eCX - tcpRad*(float)Math.cos(invRepElbowLineAngle);
        points[3] = eCY - tcpRad*(float)Math.cos(invRepElbowLineAngle);

        return points;
    }

    private float absLength(float X1, float X2, float Y1, float Y2) {
        return (float)Math.sqrt(   (float)Math.pow(X1-X2,2)+(float)Math.pow(Y1-Y2,2)   );
    }

    private float findOp(float hyp, float adj) {
        return (float)Math.sqrt(   (float)Math.pow(hyp,2)-(float)Math.pow(adj,2)   );
    }

}
