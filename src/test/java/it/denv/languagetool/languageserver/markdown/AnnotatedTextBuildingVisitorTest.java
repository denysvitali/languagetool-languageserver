package it.denv.languagetool.languageserver.markdown;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.languagetool.markup.AnnotatedText;

import java.io.IOException;

public class AnnotatedTextBuildingVisitorTest {
    @Test
    void test() throws IOException {
        Parser p = Parser.builder().build();

        Document document = p.parse("# Heading\n" +
                "Paragraph with\n" +
                "multiple lines and [link](example.com)");

        AnnotatedTextBuildingVisitor visitor = new AnnotatedTextBuildingVisitor();
        visitor.visit(document);
        AnnotatedText text = visitor.getText();

        Assertions.assertEquals("Heading\nParagraph with multiple lines and link", text.getPlainText());
    }
}
