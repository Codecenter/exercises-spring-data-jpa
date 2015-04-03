/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package solution1;

import java.util.Date;
import java.util.List;

import blog.model.BlogPost;

import blog.services.BlogService;

import org.springframework.beans.factory.annotation.Autowired;

public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    public List<BlogPost> getBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public List<BlogPost> getBlogPostsByDate(Date beginDate, Date endDate) {
        return blogPostRepository.findByCreatedBetween(beginDate, endDate);
    }

    @Override
    public void addBlogPost(String title, String message) {
        BlogPost newPost = new BlogPost();
        newPost.setCreated(new Date());
        newPost.setTitle(title);
        newPost.setMessage(message);
        blogPostRepository.save(newPost);
    }

    @Override
    public boolean deleteBlogPost(int id) {
        if (!blogPostRepository.exists(id)) {
            return false;
        }
        blogPostRepository.delete(id);
        return true;
    }
}
