/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package blog.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import blog.model.BlogPost;

import blog.services.BlogService;

public class ConsoleUI {
    private static final SimpleDateFormat dateFormat =
        new SimpleDateFormat("yyyy-MM-dd");

    private final BlogService blogService;
    private final PrintStream out;
    private final BufferedReader in;

    public ConsoleUI(BlogService blogService) {
        this.blogService = blogService;
        this.out = System.out;
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        out.println("Welcome to the blog console!");

        do {
            out.println("Please enter command: list, post, delete, exit");
            out.print("> ");

            try {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                else {
                    String command = line.trim();
                    if (command.equalsIgnoreCase("exit")) {
                        out.println("Bye!");
                        return;
                    }
                    else if (command.equalsIgnoreCase("list")) {
                        Date beginDate = readDate("Begin date");
                        if (beginDate == null) {
                            printBlogPosts(blogService.getBlogPosts());
                        }
                        else {
                            Date endDate = readDate("End date");
                            if (endDate == null) {
                                printBlogPosts(blogService.getBlogPosts());
                            }
                            else {
                                printBlogPosts(blogService.getBlogPostsByDate(
                                        beginDate, endDate));
                            }
                        }
                    }
                    else if (command.equalsIgnoreCase("post")) {
                        String title = readLine("Title");
                        String message = readLine("Message");
                        blogService.addBlogPost(title, message);
                    }
                    else if (command.equalsIgnoreCase("delete")) {
                        Integer id = readInt("Id");
                        if (id != null && !blogService.deleteBlogPost(id)) {
                            out.println("Post not found");
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

    protected String readLine(String prompt) throws IOException {
        out.print(prompt);
        out.print("? ");

        String line = in.readLine();
        return line != null ? line.trim() : null;
    }

    protected Date readDate(String prompt) throws IOException {
        prompt += " (" + dateFormat.toPattern() + ")";

        String value = readLine(prompt);
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        try {
            return dateFormat.parse(value);
        }
        catch (ParseException e) {
            out.println("Cannot parse date from '" + value + "'.");
            return null;
        }
    }

    protected Integer readInt(String prompt) throws IOException {
        String value = readLine(prompt);
        if (value == null || "".equals(value.trim())) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            out.println("Failed to parse integer from '" + value + "'.");
            return null;
        }
    }

    protected void printBlogPosts(List<BlogPost> list) {
        if (list.isEmpty()) {
            out.println("The blog is empty.");
        }
        else {
            for (BlogPost post : list) {
                out.println(
                    "========================================================");
                out.println("#" + post.getId() + " on "
                        + dateFormat.format(post.getCreated()) + ": "
                        + post.getTitle());
                out.println(post.getMessage());
                out.println(
                    "========================================================");
            }
        }
    }
}
