/*
 * $Id$
 * Copyright (c) Codecenter Oy. All rights reserved.
 *
 * This software is the proprietary information of Codecenter Oy.
 * Use is subject to license terms.
 */
package solution2;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Expression;

import blog.model.BlogPost;

import blog.services.BlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Override
    public List<BlogPost> getBlogPosts() {
        return blogPostRepository.findAll();
    }

    @Override
    public List<BlogPost> getBlogPostsByDate(Date beginDate, Date endDate) {
        return blogPostRepository.findAll(isCreatedBetween(beginDate, endDate));
    }

    private Specification<BlogPost> isCreatedBetween(final Date beginDate,
                                                     final Date endDate) {
        return
            (root, query, builder) -> {
            Expression<Date> created = root.get("created");
            return builder.between(created, beginDate, endDate);
        };
/*
        return new Specification<BlogPost>() {
            @Override
            public Predicate toPredicate(Root<BlogPost> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Expression<Date> created = root.get("created");
                return builder.between(created, beginDate, endDate);
            }
        };
*/
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
