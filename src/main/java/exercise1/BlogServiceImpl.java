/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package exercise1;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import blog.model.BlogPost;

import blog.services.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public List<BlogPost> getBlogPostRepository() {
        return Collections.emptyList();
    }

    @Override
    public List<BlogPost> getBlogPostsByDate(Date beginDate, Date endDate) {
        return Collections.emptyList();
    }

    @Override
    public void addBlogPost(String title, String message) {
        BlogPost newPost = new BlogPost();
        newPost.setCreated(new Date());
        newPost.setTitle(title);
        newPost.setMessage(message);
    }

    @Override
    public boolean deleteBlogPost(int id) {
        return false;
    }
}
