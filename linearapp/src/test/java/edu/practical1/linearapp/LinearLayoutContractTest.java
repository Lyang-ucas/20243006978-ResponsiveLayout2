package edu.practical1.linearapp;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class LinearLayoutContractTest {
    private static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";

    @Test
    public void layoutUsesOnlyLinearContainersAndResponsiveWeights() throws Exception {
        Path layoutFile = moduleFile("src/main/res/layout/activity_main.xml");
        String xml = Files.readString(layoutFile);

        assertFalse(xml.contains("ConstraintLayout"));
        assertFalse(Pattern.compile("\\d+px\\b").matcher(xml).find());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document document = factory.newDocumentBuilder().parse(layoutFile.toFile());
        assertEquals("LinearLayout", document.getDocumentElement().getTagName());

        int weightedViews = 0;
        NodeList elements = document.getElementsByTagName("*");
        for (int index = 0; index < elements.getLength(); index++) {
            Element element = (Element) elements.item(index);
            String tag = element.getTagName();
            if (tag.endsWith("Layout")) {
                assertEquals("LinearLayout", tag);
            }

            String weight = element.getAttributeNS(ANDROID_NAMESPACE, "layout_weight");
            if (weight.isEmpty()) {
                continue;
            }
            weightedViews++;
            Node parentNode = element.getParentNode();
            assertTrue(parentNode instanceof Element);
            Element parent = (Element) parentNode;
            String orientation = parent.getAttributeNS(ANDROID_NAMESPACE, "orientation");
            if ("horizontal".equals(orientation)) {
                assertEquals("0dp", element.getAttributeNS(ANDROID_NAMESPACE, "layout_width"));
            } else {
                assertEquals("vertical", orientation);
                assertEquals("0dp", element.getAttributeNS(ANDROID_NAMESPACE, "layout_height"));
            }
        }
        assertTrue("Expected weights in both axes", weightedViews >= 12);
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
        Path fromRoot = workingDirectory.resolve("linearapp").resolve(relativePath);
        assertTrue("Cannot find linearapp/" + relativePath, Files.exists(fromRoot));
        return fromRoot;
    }
}

