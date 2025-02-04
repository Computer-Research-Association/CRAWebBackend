package com.handong.cra.crawebbackend.util;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class BoardMDParser {
    private final AmazonS3 amazonS3;
    private final String bucket;

    public String updateImageUrls(String markdown, List<String> newUrls) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);

        List<String> copyUrls = new ArrayList<>(newUrls);
        replaceImageUrls(document, copyUrls);

        TextContentRenderer renderer = TextContentRenderer.builder().build();
        return renderer.render(document);
    }

    private void replaceImageUrls(Node node, List<String> newUrls) {
        if (node instanceof Image image && !newUrls.isEmpty()) {
            String s3Prefix = String.format("https://%s.s3.%s.amazonaws.com/", bucket, amazonS3.getRegionName());

            if (image.getDestination().startsWith(s3Prefix)) {
                log.info(image.getDestination());
                log.info(newUrls.get(0));
                image.setDestination(newUrls.remove(0));
            }
        }

        Node child = node.getFirstChild();
        while (child != null) {
            replaceImageUrls(child, newUrls);
            child = child.getNext();
        }
    }

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
