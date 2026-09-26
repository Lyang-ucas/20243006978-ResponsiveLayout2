package edu.practical1.constraintapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class ConstraintLayoutContractTest {
    private static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";
    private static final String APP_NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private static final String CONSTRAINT_LAYOUT =
            "androidx.constraintlayout.widget.ConstraintLayout";

    @Test
    public void layoutUsesOnlyConstraintContainersChainsAndWeights() throws Exception {
        Path layoutFile = moduleFile("src/main/res/layout/activity_main.xml");
        String xml = Files.readString(layoutFile);

        assertFalse(xml.contains("LinearLayout"));
        assertFalse(Pattern.compile("\\d+px\\b").matcher(xml).find());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document document = factory.newDocumentBuilder().parse(layoutFile.toFile());
        assertEquals(CONSTRAINT_LAYOUT, document.getDocumentElement().getTagName());

        int horizontalWeights = 0;
        int verticalWeights = 0;
        NodeList elements = document.getElementsByTagName("*");
        for (int index = 0; index < elements.getLength(); index++) {
            Element element = (Element) elements.item(index);
            if (element.getTagName().endsWith("Layout")) {
                assertEquals(CONSTRAINT_LAYOUT, element.getTagName());
            }
            if (element.hasAttributeNS(APP_NAMESPACE, "layout_constraintHorizontal_weight")) {
                horizontalWeights++;
                assertEquals("0dp", element.getAttributeNS(ANDROID_NAMESPACE, "layout_width"));
            }
            if (element.hasAttributeNS(APP_NAMESPACE, "layout_constraintVertical_weight")) {
                verticalWeights++;
                assertEquals("0dp", element.getAttributeNS(ANDROID_NAMESPACE, "layout_height"));
            }
        }

        assertTrue("Expected weighted horizontal chains", horizontalWeights >= 6);
        assertTrue("Expected weighted vertical chains", verticalWeights >= 6);
        assertTrue(xml.contains("layout_constraintHorizontal_chainStyle"));
        assertTrue(xml.contains("layout_constraintVertical_chainStyle"));
    }

    @Test
    public void allRequiredTextComesFromStringResources() throws Exception {
        String layout = Files.readString(moduleFile("src/main/res/layout/activity_main.xml"));
        String strings = Files.readString(moduleFile("src/main/res/values/strings.xml"));
        String[] names = {
            "lab_title", "layout_title", "word_this", "word_is", "word_my",
            "word_first", "application_message", "change", "cancel"
        };

        for (String name : names) {
            assertTrue(layout.contains("@string/" + name));
            assertTrue(strings.contains("name=\"" + name + "\""));
        }
    }

    private static Path moduleFile(String relativePath) {
        Path workingDirectory = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
        Path direct = workingDirectory.resolve(relativePath);
        if (Files.exists(direct)) {
            return direct;
        }
        Path fromRoot = workingDirectory.resolve("constraintapp").resolve(relativePath);
        assertTrue("Cannot find constraintapp/" + relativePath, Files.exists(fromRoot));
        return fromRoot;
    }
}

