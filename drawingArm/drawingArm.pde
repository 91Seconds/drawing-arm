void setup() {
  size(800,800);
  background(0);
  stroke(255,0,0);
}

void draw() {
  float leftElbowX, rightElbowX, leftShoulderX, rightShoulderX;
  float leftElbowY, rightElbowY, leftShoulderY, rightShoulderY;
  leftShoulderX=200;
  leftShoulderY=600;
  rightShoulderX=600;
  rightShoulderY=600;
  
  float ulnarLength=200;
  
  background(0);
  fill(30,30,30);
  noStroke();
  ellipse(leftShoulderX,leftShoulderY,2*ulnarLength,2*ulnarLength);
  ellipse(rightShoulderX,rightShoulderY,2*ulnarLength,2*ulnarLength);
  
  float leftUlnarAngle=atan((mouseX-leftShoulderX)/(mouseY-leftShoulderY));
  float rightUlnarAngle=atan((mouseX-rightShoulderX)/(mouseY-rightShoulderY));
  
  //leftElbowX=leftShoulderX+sqrt(pow(ulnarLength,2)/(pow((mouseY-leftShoulderX)/(mouseX-leftShoulderY),2)+1));
  
  leftElbowX=leftShoulderX-ulnarLength*sin(leftUlnarAngle);
  leftElbowY=leftShoulderY-ulnarLength*cos(leftUlnarAngle);
  rightElbowX=rightShoulderX-ulnarLength*sin(rightUlnarAngle);
  rightElbowY=rightShoulderY-ulnarLength*cos(rightUlnarAngle);
  
  float dY = mouseY-leftShoulderY;
  float dX = mouseX-leftShoulderX;
  
  println("dY= " + dY + ", dX= " + dX + "  angle= " + leftUlnarAngle/PI*180);
  
  fill(50,50,50);
  
  //leftElbow
  ellipse(leftElbowX,leftElbowY,ulnarLength*2,ulnarLength*2);
  
  //rightElbow
  ellipse(rightElbowX,rightElbowY,ulnarLength*2,ulnarLength*2);
  
  stroke(200,0,0);
  //left ulnar
  line(leftShoulderX,leftShoulderY,leftElbowX,leftElbowY);
  
  //right ulnar
  line(rightShoulderX,rightShoulderY,rightElbowX,rightElbowY);
}