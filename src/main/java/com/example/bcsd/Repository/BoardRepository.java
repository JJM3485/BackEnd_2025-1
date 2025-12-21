package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    public void delete(Long id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }
}

