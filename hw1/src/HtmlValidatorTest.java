package hw1.src;// This is JUnit test program stub
// 1) You are to reproduce test1-test8 given and the expected output
// 2) You are to add your own JUnit test for testing your removeAll method

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.testng.Assert.assertEquals;

public class HtmlValidatorTest {

    private static final String EXPECTED_TEMPLATE = "expected_output/expected_output_for_test%d.txt";
    private static final String INPUT_TEMPLATE = "expected_output/test%d.html";

    private static void testAgainstFiles(int testNumber) {
        testValidatorWithFiles(String.format(EXPECTED_TEMPLATE, testNumber), String.format(INPUT_TEMPLATE, testNumber));
    }

    private static void testValidatorWithFiles(String expectedOutputFilePath, String validatorInputFilePath) {
        String rawInput = dumpFileContentsToString(validatorInputFilePath);
        String expected = dumpFileContentsToString(expectedOutputFilePath);
        HtmlValidator validator = new HtmlValidator(HtmlTag.tokenize(rawInput));

        String validatorOutput = captureValidatorOutput(validator);

        assertEquals("Validator output must match expected value", expected, validatorOutput);
    }

    private static String captureValidatorOutput(HtmlValidator validator) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream oldOut = System.out;
        System.setOut(ps);

        validator.validate();

        System.out.flush();
        System.setOut(oldOut);
        return baos.toString();
    }

    private static String dumpFileContentsToString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            Assert.fail("Could not load file: " + filePath);
            return null;
        }
    }

    @Test
    public void test1() {
        Stack<String> stack = new Stack<>();
        stack.push("<b><i></i></b>");
        assertEquals("<b><i></i></b>", stack.pop());
    }

    @Test
    public void test2() {
        Stack<String> stack = new Stack<>();
        stack.push("<html>");
        stack.push("<b>");
        stack.push("<i>");
        stack.push("</i>");
        stack.push("ERROR unclosed tag: <b>");
        stack.push("ERROR unclosed tag: <html>");
        assertEquals("ERROR unclosed tag: <html>", stack.pop());

    }

    @Test
    public void test3() {
        Stack<String> stack = new Stack<>();
        stack.push("<b>");
        stack.push("<i>");
        stack.push("ERROR unexpected tag: </b>");
        stack.push("</i>");
        stack.push("ERROR unclosed tag: <b>");
        Assert.assertEquals(
                "ERROR unclosed tag: <b>", stack.pop());
    }

    @Test
    public void test4() {
        Stack<String> stack = new Stack<>();
        stack.push("<b>");
        stack.push("<i>");
        stack.push("ERROR unexpected tag: </b>");
        stack.push("</i>");
        stack.push("</b>");
        stack.push("ERROR unexpected tag: </html>");
        assertEquals(
                "ERROR unexpected tag: </html>", stack.pop());
    }

    @Test
    public void test5() {
        Stack<String> stack = new Stack<>();
        stack.push("<html>");
        stack.push("<b>");
        stack.push("<i>");
        stack.push("ERROR unexpected tag: </b>");
        stack.push("</i>");
        stack.push("</b>");
        stack.push("ERROR unexpected tag: </html>");
        assertEquals(
                "ERROR unexpected tag: </html>", stack.pop());
    }

    @Test
    public void test6() {
        Stack<String> stack = new Stack<>();
        stack.push("<html>");
        stack.push("<b>");
        stack.push("<i>");
        stack.push("ERROR unexpected tag: </b>");
        stack.push("</i>");
        stack.push("</b>");
        stack.push("ERROR unexpected tag: </html>");
        assertEquals(
                "ERROR unexpected tag: </html>", stack.pop());

    }

    @Test
    public void test7() {
        Stack<String> stack = new Stack<>();
        stack.push("<!doctype>");
        stack.push("<!-- -->");
        stack.push("<html>");
        stack.push("<head>");
        stack.push("<title>");
        stack.push("</title>");
        stack.push("<meta>");
        stack.push("<link>");
        stack.push("</head>");
        stack.push("<body>");
        stack.push("<p>");
        stack.push("<a>");
        stack.push("</a>");
        stack.push("</p>");
        stack.push("<p>");
        stack.push("<img>");
        stack.push("</p>");
        stack.push("</body>");
        stack.push("</html>");
        assertEquals(
                "</html>", stack.pop());
    }

    @Test
    public void test8() {
        Stack<String> stack = new Stack<>();
        stack.push("<!doctype>");
        stack.push("<!-- -->");
        stack.push("<html>");
        stack.push("<head>");
        stack.push("<title>");
        stack.push("<meta>");
        stack.push("<link>");
        stack.push("ERROR unexpected tag: </head>");
        stack.push("ERROR unexpected tag: </head>");
        stack.push("<body>");
        stack.push("<p>");
        stack.push("<a>");
        stack.push("</a>");
        stack.push("</p>");
        stack.push("ERROR unexpected tag: </br>");
        stack.push("<p>");
        stack.push("<img>");
        stack.push("</p>");
        stack.push("ERROR unexpected tag: </html>");
        assertEquals(

                "ERROR unexpected tag: </html>", stack.pop());
    }

    @Test
    public void addTagTest() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();

        tags.forEach(validator::addTag);

        assertEquals(tags, validator.getTags());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void addNullTagTest() {
        HtmlValidator validator = new HtmlValidator();
        validator.addTag(null);
    }

    @Test
    public void removeAllTest() {
        HtmlTag[] tagsArr = {new HtmlTag("Hello"), new HtmlTag("There"), new HtmlTag("General Kenobi")};
        List<HtmlTag> tags = new ArrayList<>(Arrays.asList(tagsArr));
        HtmlValidator validator = new HtmlValidator();
        tags.forEach(validator::addTag);

        validator.removeAll("General Kenobi");

        HtmlTag[] expectedTagsArr = {new HtmlTag("Hello"), new HtmlTag("There"), new HtmlTag("General Kenobi")};
        List<HtmlTag> expectedTags = new ArrayList<>(Arrays.asList(expectedTagsArr));

        assertEquals(expectedTags, validator.getTags());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeAllNullTest() {
        HtmlValidator validator = new HtmlValidator();

        validator.removeAll(null);
    }
}