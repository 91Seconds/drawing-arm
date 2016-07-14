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
  background(15,15,20);
  
  apparatus();
  gCursor();
}

void apparatus() {
  noStroke();
  fill(50);
  ellipse(o1X,o1Y,2*l,2*l);
  ellipse(o2X,o2Y,2*l,2*l);
}

void gCursor() {
  noStroke();
  fill(50);
  ellipse(mouseX,mouseY,10,10);
}