package com.rybickim.javaquiz.data.impl;

import com.rybickim.javaquiz.data.QuizEntityRepository;
import com.rybickim.javaquiz.domain.QuizEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Qualifier("quizRepo")
public class QuizEntityRepositoryImpl implements QuizEntityRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(QuizEntity quizEntity) {
        return (Long) sessionFactory.getCurrentSession().save(quizEntity);
    }

    @Override
    public QuizEntity get(Long id) {
        return sessionFactory.getCurrentSession().get(QuizEntity.class, id);
    }

    @Override
    public List<QuizEntity> list() {
        return sessionFactory.getCurrentSession().createQuery("FROM QuizEntity").list();
//        return Collections.emptyList();
    }

    @Override
    public void update(QuizEntity quizEntity) {
        sessionFactory.getCurrentSession().update(quizEntity);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        QuizEntity quizEntity = session.byId(QuizEntity.class).load(id);
        session.delete(quizEntity);
    }
}
