//lengths ulnar and forearm
final float l=90;
//distance between shoulders
final float d=118;

//centerpoint between shoulders
float xCoOrdCenter;
float yCoOrdCenter;

//o is for shoulder, 1 means left, 2 meas right
float o1X;
float o1Y;
float o2X;
float o2Y;

//not used
//float h1X, h1Y, h2X, h2Y;

void setup() {
  size(800,800);
  xCoOrdCenter=width/2;
  yCoOrdCenter=height/2;
  o1X = xCoOrdCenter-d/2;
  o1Y = yCoOrdCenter;
  o2X = xCoOrdCenter+d/2;
  o2Y = xCoOrdCenter;
}

void draw() {
  background(30,35,40);
  
  apparatus();
  arms();
  gCursor();
}

void arms() {
  //co-ordinates of the center point of a line drawn from the shoulders to the mouse
  float o1XC = xCoOrdCenter+(mouseX-o1X)/2;
  float o1YC = yCoOrdCenter+(mouseY-o1Y)/2;
  float o2XC = xCoOrdCenter+(mouseX-o2X)/2;
  float o2YC = yCoOrdCenter+(mouseY-o2Y)/2;
  
  //length of the line btween the shoulders and the mouse
  float abs1 = sqrt(   pow(mouseX-o1X,2)+pow(mouseY-o1Y,2)   );
  float abs2 = sqrt(   pow(mouseX-o2X,2)+pow(mouseY-o2Y,2)   );
  
  //radius of a circle centered halfway between the shoulders and the mouse so that the distance 
  //between its intersection with the reach of the shoulders and the mouse is equal to l
  float o2R = sqrt(pow(l,2)-pow(abs1/2,2));
  float o1R = sqrt(pow(l,2)-pow(abs2/2,2));//(pow(((mouseX-o2X)/2),2)-pow(mouseY-o2Y,2)));
  
  //draws that circle
  stroke(80);
  ellipse(o1XC,o1YC,2*o1R,2*o1R);
  ellipse(o2XC,o2YC,2*o2R,2*o2R);
  
  //finds the angle of the line fromn the shoulders to the mouse
  //and the inverse reciprocal
  float o1Angle = atan2((mouseY-o1Y),(mouseX-o1X));
  float o1NormalAngle =atan2(-(mouseX-o1X),(mouseY-o1Y));
  float o2Angle = atan2((mouseY-o2Y),(mouseX-o2X));
  float o2NormalAngle =atan2(-(mouseX-o2X),(mouseY-o2Y));
  
  //uses the length of the line as determined by the radius of the circle
  //and the slope of the line as determined by the inverse reciprocal of the angle
  //to determine the co-ordinates where the normal intersetcs the reach of the ulnar
  float o1NormalX1 = o2XC + o2R*cos(o1NormalAngle);
  float o1NormalY1 = o2YC + o2R*sin(o1NormalAngle);
  float o1NormalX2 = o2XC - o2R*cos(o1NormalAngle);
  float o1NormalY2 = o2YC - o2R*sin(o1NormalAngle);
  
  float o2NormalX1 = o1XC + o1R*cos(o2NormalAngle);
  float o2NormalY1 = o1YC + o1R*sin(o2NormalAngle);
  float o2NormalX2 = o1XC - o1R*cos(o2NormalAngle);
  float o2NormalY2 = o1YC - o1R*sin(o2NormalAngle);
  
  //draws the normals
  stroke(60);
  
  line(o1NormalX2,o1NormalY2,o1NormalX1,o1NormalY1);
  line(o2NormalX2,o2NormalY2,o2NormalX1,o2NormalY1);
  
  //draws the one possiblility for the ulnars 
  stroke(255,0,0);
  
  line(o1X,o1Y,o1NormalX1,o1NormalY1);
  line(o2X,o2Y,o2NormalX1,o2NormalY1);
  
  //draws the other possiblility for the ulnars
  stroke(0,0,150);
  line(mouseX,mouseY,o1NormalX1,o1NormalY1);
  line(mouseX,mouseY,o1NormalX2,o1NormalY2);
  
  //draws one posssiblility for the foreamrs
  stroke(0,0,255);
  line(mouseX,mouseY,o2NormalX1,o2NormalY1);
  line(mouseX,mouseY,o2NormalX2,o2NormalY2);
  
  //draws the other possiblility for the forearms
  stroke(150,0,0);
  
  line(o1X,o1Y,o1NormalX2,o1NormalY2);
  line(o2X,o2Y,o2NormalX2,o2NormalY2);
  
  //draws a straight line from shoulder to mouse
  stroke(100);
  line(o1X,o1Y,mouseX,mouseY);
  line(o2X,o2Y,mouseX,mouseY);
}

//draws the reach of the ulnars pivoting from shoulders and the total working 
//area as the intersections of two ellipses centered at the shoulders
void apparatus() {
  fill(50);
  ellipse(o1X,o1Y,2*l,2*l);
  ellipse(o2X,o2Y,2*l,2*l);
  stroke(0);
  noFill();
  ellipse(o1X,o1Y,4*l,4*l);
  ellipse(o2X,o2Y,4*l,4*l);
}

//displays the mouse position
void gCursor() {
  noStroke();
  fill(50);
  ellipse(mouseX,mouseY,10,10);
}