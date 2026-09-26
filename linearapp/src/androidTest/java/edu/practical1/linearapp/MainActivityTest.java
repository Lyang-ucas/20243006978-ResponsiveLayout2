package edu.practical1.linearapp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class MainActivityTest {
    @Test
    public void testRequiredViewsAreVisibleAndPanelsAreEqualWidth() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                View root = activity.findViewById(R.id.root);
                View[] panels = {
                    activity.findViewById(R.id.word_this),
                    activity.findViewById(R.id.word_is),
                    activity.findViewById(R.id.word_my),
                    activity.findViewById(R.id.word_first)
                };

                assertNotNull(root);
                for (View panel : panels) {
                    assertNotNull(panel);
                }
                int expectedWidth = panels[0].getWidth();
                assertTrue(expectedWidth > 0);
                for (int index = 0; index < panels.length; index++) {
                    assertTrue(Math.abs(expectedWidth - panels[index].getWidth()) <= 1);
                    if (index > 0) {
                        assertTrue(panels[index - 1].getRight() <= panels[index].getLeft());
                    }
                }

                assertNotNull(activity.findViewById(R.id.change_button));
                View cancel = activity.findViewById(R.id.cancel_button);
                assertNotNull(cancel);
                assertTrue(cancel.getBottom() <= root.getHeight());
            });
        }
    }
}
