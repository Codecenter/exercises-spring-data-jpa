/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package solution2;

import java.io.IOException;

import blog.ConsoleApplication;

import blog.services.BlogService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BlogConsole {
    public static void main(String[] args) throws IOException {
        BlogService blogService = createBlogService();
        ConsoleApplication application = new ConsoleApplication(blogService);
        application.run();
    }

    protected static BlogService createBlogService() {
        // Use ApplicationContext to enable container-managed
        // transactions.
        ApplicationContext context =
            new ClassPathXmlApplicationContext(
                "solution2/applicationContext.xml");
        return context.getBean("blogService", BlogService.class);
    }
}
