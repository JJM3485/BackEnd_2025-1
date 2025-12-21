package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository {

    @PersistenceContext
    private EntityManager em;

    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    public Article findById(Long id) {
        return em.find(Article.class, id);
    }

    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }

    public List<Article> findByBoardId(Long boardId) {
        return em.createQuery("select a from Article a where a.boardId = :boardId", Article.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    public void update(Long id, Article article) {
        article.setId(id);
        em.merge(article);
    }

    public void delete(Long id) {
        Article article = em.find(Article.class, id);
        if (article != null) {
            em.remove(article);
        }
    }
}

