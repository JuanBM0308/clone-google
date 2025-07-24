package com.juanba.clone_google.services;

import com.juanba.clone_google.entities.WebPage;
import com.juanba.clone_google.repositories.WebPageRepository;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpiderService {

    private final WebPageServiceImpl webPageService;

    public SpiderService(WebPageServiceImpl webPageService) {
        this.webPageService = webPageService;
    }

    public void indexWebPages() {
        List<WebPage> linksToIndex = webPageService.getLinksToIndex();
        linksToIndex.stream()
                .parallel()
                .forEach(webPage -> {
            try {
                System.out.println("Indexing");
                indexWebPage(webPage);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private void indexWebPage(WebPage webPage) throws Exception {
        String url = webPage.getUrl();
        String content = getWebContent(url);

        if (StringHelper.isBlank(content)) {
            return;
        }

        indexAndSaveWebPage(webPage, content);

        System.out.println("Domain: " + getDomain(url));
        saveLinks(getDomain(url), content);
    }

    private void indexAndSaveWebPage(WebPage webPage, String content) {
        String title = getTitle(content);
        String description = getDescription(content);

        webPage.setDescription(description);
        webPage.setTitle(title);

        System.out.println("Save: " + webPage);
        webPageService.save(webPage);
    }

    private void saveLinks(String domain, String content) {
        List<String> links = getLinks(domain, content);
        links.stream()
                .filter(link -> !webPageService.exist(link))
                .map(WebPage::new)
                .forEach(webPageService::save);
    }

    public List<String> getLinks(String domain, String content) {
        List<String> links = new ArrayList<>();

        String[] splitHref = content.split("href=\"");
        List<String> listHref = Arrays.asList(splitHref);

        listHref.forEach(strHref -> {
            String[] aux = strHref.split("\"");
            links.add(aux[0]);
        });
        return cleanLinks(domain, links);
    }

    private List<String> cleanLinks(String domain, List<String> links) {
        String[] excludedExtensions = new String[]{"css","js","json","jpg","png","woff2"};

        List<String> resultLinks = links.stream()
                .filter(link -> Arrays.stream(excludedExtensions).noneMatch(link::endsWith))
                .map(link -> link.startsWith("/") ? domain + link : link)
                .filter(link -> link.startsWith("http"))
                .collect(Collectors.toList());

        List<String> uniqueLinks = new ArrayList<>();
        uniqueLinks.addAll(new HashSet<>(resultLinks));

        return uniqueLinks;
    }

    private String getDomain(String url) {
        String[] aux = url.split("/");
        return aux[0] + "//" + aux[2];
    }

    public String getTitle(String content) {
        String[] aux = content.split("<title>");
        String[] aux2 = aux[1].split("</title>");
        return aux2[0];
    }

    public String getDescription(String content) {
        String[] aux = content.split("<meta name=\"description\" content=\"");
        String[] aux2 = aux[1].split("\">");
        return aux2[0];
    }

    private String getWebContent(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String encoding = conn.getContentEncoding();

            InputStream input = conn.getInputStream();

            Stream<String> lines = new BufferedReader(new InputStreamReader(input))
                    .lines();

            return lines.collect(Collectors.joining());
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        return "";
    }
}
