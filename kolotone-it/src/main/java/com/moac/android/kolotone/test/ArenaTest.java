package com.moac.android.kolotone.test;

import android.graphics.Color;
import com.moac.android.kolotone.model.Arena;
import com.moac.android.kolotone.model.Ball;
import com.moac.android.kolotone.model.Velocity;
import junit.framework.TestCase;

public class ArenaTest extends TestCase {

    public void testMoveLeft() {
        Arena a = new Arena(1.0f, 2, 100, 100);

        Ball b1 = new Ball(null, 10, Color.WHITE, 50f, 50f);

        // Test X rebound from LEFT surface

        // Only moving left.
        b1.getVelocity().setxDirection(Velocity.DIRECTION_LEFT);
        b1.getVelocity().setYv(0f);
        b1.getVelocity().setXv(1f);

        a.manage(b1);

        a.update();

        assertEquals(49.0d, b1.getXPos(), 0.0);
        assertEquals(50.0d, b1.getYPos(), 0.0);
    }

    public void testMoveRight() {
        Arena a = new Arena(1.0f, 2, 100, 100);

        Ball b1 = new Ball(null, 10, Color.WHITE, 50f, 50f);

        // Only moving right.
        b1.getVelocity().setxDirection(Velocity.DIRECTION_RIGHT);
        b1.getVelocity().setYv(0f);
        b1.getVelocity().setXv(1f);

        a.manage(b1);
        a.update();

        assertEquals(51.0f, b1.getXPos(), 0.0);
        assertEquals(50.0f, b1.getYPos(), 0.0);
    }

    public void testMoveUp() {
        Arena a = new Arena(1.0f, 2, 100, 100);

        Ball b1 = new Ball(null, 10, Color.WHITE, 50f, 50f);

        // Only moving up.
        b1.getVelocity().setyDirection(Velocity.DIRECTION_UP);
        b1.getVelocity().setYv(1f);
        b1.getVelocity().setXv(0f);

        a.manage(b1);
        a.update();

        assertEquals(50.0f, b1.getXPos(), 0.0);
        assertEquals(49.0f, b1.getYPos(), 0.0);
    }

    public void testMoveDown() {
        float radius = 10f;
        Arena a = new Arena(1.0f, 2, 100, 100);

        Ball b1 = new Ball(null, radius, Color.WHITE, 50f, 50f);

        // Only moving down.
        b1.getVelocity().setyDirection(Velocity.DIRECTION_DOWN);
        b1.getVelocity().setYv(1f);
        b1.getVelocity().setXv(0f);

        a.manage(b1);
        a.update();

        assertEquals(50.0f, b1.getXPos(), 0.0);
        assertEquals(51.0f, b1.getYPos(), 0.0);
    }

    public void testReboundLeft() {
        float radius = 10f;
        Ball b1 = new Ball(null, radius, Color.WHITE, radius + 1f, 50f);

        Arena a = new Arena(1.0f, 2, 100, 100);

        // Only moving left.
        b1.getVelocity().setxDirection(Velocity.DIRECTION_LEFT);
        b1.getVelocity().setYv(0f);
        b1.getVelocity().setXv(1f);

        a.manage(b1);
        a.update();

        // Approach edge
        assertEquals(radius, b1.getXPos(), 0.0);
        assertEquals(Velocity.DIRECTION_LEFT, b1.getVelocity().getxDirection());

        a.update();

        // Detect at edge and sends back the other way
        assertEquals(radius + 1f, b1.getXPos(), 0.0);
        assertEquals(Velocity.DIRECTION_RIGHT, b1.getVelocity().getxDirection());

        for(int i = 2; i < 20; i++) {

            a.update();

            // Continues back
            assertEquals(radius + (float) i, b1.getXPos(), 0.0);
            assertEquals(Velocity.DIRECTION_RIGHT, b1.getVelocity().getxDirection());
        }
    }
}
