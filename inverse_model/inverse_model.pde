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
  
 println(abs1);
  float o2R = sqrt(pow(l,2)-pow(abs1/2,2));
  float o1R = sqrt(pow(l,2)-pow(abs2/2,2));//(pow(((mouseX-o2X)/2),2)-pow(mouseY-o2Y,2)));
  
  stroke(80);
  ellipse(o1XC,o1YC,2*o1R,2*o1R);
  ellipse(o2XC,o2YC,2*o2R,2*o2R);
  
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