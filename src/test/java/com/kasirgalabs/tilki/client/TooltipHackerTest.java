/*
 * Copyright (C) 2017 Kasirgalabs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.kasirgalabs.tilki.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import org.junit.Rule;
import org.junit.Test;

public class TooltipHackerTest {
    @Rule
    public JavaFXThread javaFXThread = new JavaFXThread();

    /**
     * Test of customTooltip method, of class TooltipHacker.
     */
    @Test
    public void testCustomTooltip() {
        final double durationToTest = 100.0;
        Tooltip tooltip = TooltipHacker.customTooltip("TEST", durationToTest);
        assertEquals("TEST", tooltip.getText());
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);
            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);
            double duration = objTimer.getCycleDuration().toMillis();
            // We expect duration difference less than or equal to 1.
            if(durationToTest - duration > 1) {
                fail();
            }
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(TooltipHacker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
