/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package blog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import blog.model.BlogPost;

import blog.services.BlogService;

public class ConsoleApplication {
    private static final SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd");

    private BlogService blogService;

    public ConsoleApplication(BlogService blogService) {
        this.blogService = blogService;
    }

    public void run() throws IOException {
        System.out.println("Welcome to the blog console!");

        BufferedReader in =
            new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println(
                "Please enter command: list, post, delete, exit");
            System.out.print("> ");

            try {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                else {
                    String command = line.trim();
                    if (command.equalsIgnoreCase("exit")) {
                        System.out.println("Bye!");
                        return;
                    }
                    else if (command.equalsIgnoreCase("list")) {
                        Date beginDate = readDate(in, "Begin date");
                        if (beginDate == null) {
                            printBlogPosts(blogService.getBlogPostRepository());
                        }
                        else {
                            Date endDate = readDate(in, "End date");
                            if (endDate == null) {
                                printBlogPosts(
                                    blogService.getBlogPostRepository());
                            }
                            else {
                                printBlogPosts(blogService.getBlogPostsByDate(
                                        beginDate, endDate));
                            }
                        }
                    }
                    else if (command.equalsIgnoreCase("post")) {
                        String title = readLine(in, "Title");
                        String message = readLine(in, "Message");
                        blogService.addBlogPost(title, message);
                    }
                    else if (command.equalsIgnoreCase("delete")) {
                        Integer id = readInt(in, "Id");
                        if (id != null && !blogService.deleteBlogPost(id)) {
                            System.out.println("Post not found");
                        }
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (true);
    }

    protected static String readLine(BufferedReader in, String prompt)
            throws IOException {
        System.out.print(prompt);
        System.out.print("? ");

        String line = in.readLine();
        return line != null ? line.trim() : null;
    }

    protected Date readDate(BufferedReader in, String prompt)
            throws IOException {
        prompt += " (" + dateFormat.toPattern() + ")";

        String value = readLine(in, prompt);
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        try {
            return dateFormat.parse(value);
        }
        catch (ParseException e) {
            System.out.println("Cannot parse date from '" + value + "'.");
            return null;
        }
    }

    protected Integer readInt(BufferedReader in, String prompt)
            throws IOException {
        String value = readLine(in, prompt);
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            System.out.println("Failed to parse integer from '" + value + "'.");
            return null;
        }
    }

    protected void printBlogPosts(List<BlogPost> list) {
        if (list.isEmpty()) {
            System.out.println("The blog is empty.");
        }
        else {
            for (BlogPost post : list) {
                System.out.println(
                    "========================================================");
                System.out.println("#" + post.getId() + " on "
                        + dateFormat.format(post.getCreated()) + ": "
                        + post.getTitle());
                System.out.println(post.getMessage());
                System.out.println(
                    "========================================================");
            }
        }
    }
}
