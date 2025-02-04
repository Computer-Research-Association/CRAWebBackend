package com.handong.cra.crawebbackend.util;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.commonmark.renderer.markdown.MarkdownRenderer;
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

        MarkdownRenderer renderer = MarkdownRenderer.builder().build();
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
        markdown = markdown.replaceAll("(?i)<br\\s*/?>", " ");

        Node document = parser.parse(markdown);

        StringBuilder sb = new StringBuilder();
        extractText(document, sb);

        return sb.toString().trim();
    }

    private static void extractText(Node node, StringBuilder sb) {
        if (node instanceof Text) {
            sb.append(((Text) node).getLiteral()).append(" ");
        } else if (node instanceof SoftLineBreak || node instanceof HardLineBreak) {
            sb.append(" "); // 줄바꿈 유지
        } else if (node instanceof HtmlInline htmlInline) {
            // `<br>` 태그가 포함된 경우 줄바꿈 추가
            if (htmlInline.getLiteral().equalsIgnoreCase("<br>") || htmlInline.getLiteral().equalsIgnoreCase("<br/>")) {
                sb.append(" ");
            }
        } else if (node instanceof HtmlBlock htmlBlock) {
            // `<br>` 태그만 포함된 HTML 블록 처리
            if (htmlBlock.getLiteral().trim().equalsIgnoreCase("<br>") || htmlBlock.getLiteral().trim().equalsIgnoreCase("<br/>")) {
                sb.append(" ");
            }
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
