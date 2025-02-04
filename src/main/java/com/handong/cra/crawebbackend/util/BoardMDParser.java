package com.handong.cra.crawebbackend.util;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;

public class BoardMDParser {
    public static String extractPlainText(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);

        StringBuilder sb = new StringBuilder();
        extractText(document, sb);

        return sb.toString().trim();
    }

    private static void extractText(Node node, StringBuilder sb) {
        if (node instanceof Text) {
            sb.append(((Text) node).getLiteral());
        } else if (node instanceof SoftLineBreak || node instanceof HardLineBreak) {
            sb.append("\n"); // 줄바꿈 유지
        } else if (!(node instanceof Link || node instanceof Image)) {
            // Link, Image 노드는 무시하고 다른 노드는 계속 탐색
            Node child = node.getFirstChild();
            while (child != null) {
                extractText(child, sb);
                child = child.getNext();
            }
        }
    }
}
