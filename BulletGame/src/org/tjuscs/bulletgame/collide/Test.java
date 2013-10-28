package org.tjuscs.bulletgame.collide;


public class Test {
	
	public static void main(String[] msg){
		GeometryEllipse mye1 = new GeometryEllipse(-1.5,1.5,2.0,40);
		GeometryRec mym1 = new GeometryRec(0,0,2,2,0);
		
		System.out.println(CollisionCheck.check(mym1,mye1));
	}
}
