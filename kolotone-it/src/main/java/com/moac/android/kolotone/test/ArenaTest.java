package com.moac.android.kolotone.test;

import android.graphics.Color;

import com.moac.android.kolotone.model.Arena;
import com.moac.android.kolotone.model.Ball;
import com.moac.android.kolotone.model.Speed;

import junit.framework.TestCase;

public class ArenaTest extends TestCase {
	
	public void testMoveLeft()
	{
		Arena a = new Arena(1.0f, 2, 100, 100);
		
		Ball b1 = new Ball(null, 10, Color.WHITE, 50f, 50f);
		
		// Test X rebound from LEFT surface
		
		// Only moving left.
		b1.getSpeed().setxDirection(Speed.DIRECTION_LEFT);
		b1.getSpeed().setYv(0f);
		b1.getSpeed().setXv(1f);
		
		a.manage(b1);
		
		a.update();
		
		assertEquals(49.0f, b1.getXPos());		
		assertEquals(50.0f, b1.getYPos());
		
	}
	
	public void testMoveRight()
	{
		Arena a = new Arena(1.0f, 2, 100, 100);
		
		Ball b1 = new Ball(null, 10, Color.WHITE, 50f, 50f);
				
		// Only moving right.
		b1.getSpeed().setxDirection(Speed.DIRECTION_RIGHT);
		b1.getSpeed().setYv(0f);
		b1.getSpeed().setXv(1f);
		
		a.manage(b1);		
		a.update();
		
		assertEquals(51.0f, b1.getXPos());		
		assertEquals(50.0f, b1.getYPos());
		
	}
	
	
	public void testMoveUp()
	{
		Arena a = new Arena(1.0f, 2, 100, 100);
		
		Ball b1 = new Ball(null, 10, Color.WHITE, 50f, 50f);
				
		// Only moving up.
		b1.getSpeed().setyDirection(Speed.DIRECTION_UP);
		b1.getSpeed().setYv(1f);
		b1.getSpeed().setXv(0f);
		
		a.manage(b1);		
		a.update();
		
		assertEquals(50.0f, b1.getXPos());		
		assertEquals(49.0f, b1.getYPos());
		
	}
	
	public void testMoveDown()
	{
		float radius = 10f;
		Arena a = new Arena(1.0f, 2, 100, 100);
		
		Ball b1 = new Ball(null, radius, Color.WHITE, 50f, 50f);
				
		// Only moving down.
		b1.getSpeed().setyDirection(Speed.DIRECTION_DOWN);
		b1.getSpeed().setYv(1f);
		b1.getSpeed().setXv(0f);
		
		a.manage(b1);		
		a.update();
		
		assertEquals(50.0f, b1.getXPos());		
		assertEquals(51.0f, b1.getYPos());
		
	}
	
	public void testReboundLeft()
	{
		float radius = 10f;
		Ball b1 = new Ball(null, radius, Color.WHITE, radius + 1f, 50f);

		Arena a = new Arena(1.0f, 2, 100, 100);
					
		// Only moving left.
		b1.getSpeed().setxDirection(Speed.DIRECTION_LEFT);
		b1.getSpeed().setYv(0f);
		b1.getSpeed().setXv(1f);
		
		a.manage(b1);		
		a.update();
		
		// Approach edge
		assertEquals(radius, b1.getXPos());		
		assertEquals(Speed.DIRECTION_LEFT, b1.getSpeed().getxDirection());
		
		a.update();
		
		// Detect at edge and sends back the other way
		assertEquals(radius + 1f, b1.getXPos());		
		assertEquals(Speed.DIRECTION_RIGHT, b1.getSpeed().getxDirection());
		
		for (int i =2; i < 20; i++)
		{
			
			a.update();

			// Continues back
			assertEquals(radius + (float)i, b1.getXPos());		
			assertEquals(Speed.DIRECTION_RIGHT, b1.getSpeed().getxDirection());
		}
				
	}

}
