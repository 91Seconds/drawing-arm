final float l=90;
final float d=118;

float xCoOrdCenter;
float yCoOrdCenter;

float o1X;
float o1Y;
float o2X;
float o2Y;

float angle1, angle2;

float h1X, h1Y, h2X, h2Y;

void setup() {
  size(800,800);
  xCoOrdCenter=width/2;
  yCoOrdCenter=height/2;
  o1X = xCoOrdCenter-d/2;
  o1Y = yCoOrdCenter;
  o2X = xCoOrdCenter+d/2;
  o2Y = xCoOrdCenter;
  
  background(15,15,20);
}

void draw() {
  background(25,25,30);
  
  apparatus();
  arms();
  gCursor();
}

void arms() {
  float o1XC = xCoOrdCenter+(mouseX-o1X)/2;
  float o1YC = yCoOrdCenter+(mouseY-o1Y)/2;
  float o2XC = xCoOrdCenter+(mouseX-o2X)/2;
  float o2YC = yCoOrdCenter+(mouseY-o2Y)/2;
  
  float abs1 = sqrt(   pow(mouseX-o1X,2)+pow(mouseY-o1Y,2)   );
  float abs2 = sqrt(   pow(mouseX-o2X,2)+pow(mouseY-o2Y,2)   );
  
  //println(abs1);
  
  float o2R = sqrt(pow(l,2)-pow(abs1/2,2));
  float o1R = sqrt(pow(l,2)-pow(abs2/2,2));//(pow(((mouseX-o2X)/2),2)-pow(mouseY-o2Y,2)));
  
  stroke(80);
  ellipse(o1XC,o1YC,2*o1R,2*o1R);
  ellipse(o2XC,o2YC,2*o2R,2*o2R);
  
  float o1Angle = atan2((mouseY-o1Y),(mouseX-o1X));
  float o1NormalAngle =atan2(-(mouseX-o1X),(mouseY-o1Y));
  float o2Angle = atan2((mouseY-o2Y),(mouseX-o2X));
  float o2NormalAngle =atan2(-(mouseX-o2X),(mouseY-o2Y));
  
  float o1NormalX1 = o2XC + o2R*cos(o1NormalAngle);
  float o1NormalY1 = o2YC + o2R*sin(o1NormalAngle);
  float o1NormalX2 = o2XC - o2R*cos(o1NormalAngle);
  float o1NormalY2 = o2YC - o2R*sin(o1NormalAngle);
  
  float o2NormalX1 = o1XC + o1R*cos(o2NormalAngle);
  float o2NormalY1 = o1YC + o1R*sin(o2NormalAngle);
  float o2NormalX2 = o1XC - o1R*cos(o2NormalAngle);
  float o2NormalY2 = o1YC - o1R*sin(o2NormalAngle);
  
  stroke(60);
  
  line(o1NormalX2,o1NormalY2,o1NormalX1,o1NormalY1);
  line(o2NormalX2,o2NormalY2,o2NormalX1,o2NormalY1);
  
  stroke(255,0,0);
  
  line(o1X,o1Y,o1NormalX1,o1NormalY1);
  line(o2X,o2Y,o2NormalX1,o2NormalY1);
  
  stroke(0,0,150);
  line(mouseX,mouseY,o1NormalX1,o1NormalY1);
  line(mouseX,mouseY,o1NormalX2,o1NormalY2);
  
  stroke(0,0,255);
  line(mouseX,mouseY,o2NormalX1,o2NormalY1);
  line(mouseX,mouseY,o2NormalX2,o2NormalY2);
  
  stroke(150,0,0);
  
  line(o1X,o1Y,o1NormalX2,o1NormalY2);
  line(o2X,o2Y,o2NormalX2,o2NormalY2);
  
  //ellipse(o1XC,o1YC,2*sqrt(pow(l,2)-pow(mouseX-o1X,2)-pow(mouseY-o1Y,2)),2*sqrt(pow(l,2)-pow(mouseX-o1X,2)-pow(mouseY-o1Y,2)));
  stroke(100);
  line(o1X,o1Y,mouseX,mouseY);
  line(o2X,o2Y,mouseX,mouseY);
}

void apparatus() {
  fill(50);
  ellipse(o1X,o1Y,2*l,2*l);
  ellipse(o2X,o2Y,2*l,2*l);
  stroke(0);
  noFill();
  ellipse(o1X,o1Y,4*l,4*l);
  ellipse(o2X,o2Y,4*l,4*l);
}

void gCursor() {
  noStroke();
  fill(50);
  ellipse(mouseX,mouseY,10,10);
}