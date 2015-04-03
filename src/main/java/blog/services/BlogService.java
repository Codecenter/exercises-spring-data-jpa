/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package blog.services;

import java.util.Date;
import java.util.List;

import blog.model.BlogPost;

//import org.springframework.transaction.annotation.Transactional;

//@Transactional
public interface BlogService {
    // @Transactional(readOnly = true)
    List<BlogPost> getBlogPosts();

    // @Transactional(readOnly = true)
    List<BlogPost> getBlogPostsByDate(Date beginDate, Date endDate);

    void addBlogPost(String title, String message);

    boolean deleteBlogPost(int id);

/* Service interfaces often contain other business logic as well:
    void registerBlogWatcher(String emailAddress);
    void stopWatchingBlog(String emailAddress);
*/
}
