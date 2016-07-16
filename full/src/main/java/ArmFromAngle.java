/**
 * Created by surface on 16/07/2016.
 */
public class ArmFromAngle {
    private double shoulderX,shoulderY,elbowX,elbowY,radius;

    public ArmFromAngle(double shoulderX,double shoulderY, double radius) {
        this.shoulderX=shoulderX;
        this.shoulderY=shoulderY;
        this.radius=radius;
    }

    public ArmFromAngle(double shoulderX,double shoulderY, double radius, double initialAngle) {
        this.shoulderX=shoulderX;
        this.shoulderY=shoulderY;
        this.radius=radius;
        this.elbowX=radius*Math.cos(initialAngle);
        this.elbowY=radius*Math.sin(initialAngle);
    }

    public float getAngle() {
        return (float)Math.atan2(shoulderY-elbowY,shoulderX-elbowX);
    }

    public void setMotor(int PWM) {
        double omega=PWM/255;
        //double angle=getAngle()+omega;
        this.elbowX=radius*Math.cos(getAngle()+omega);
        this.elbowY=radius*Math.sin(getAngle()+omega);
    }
}
