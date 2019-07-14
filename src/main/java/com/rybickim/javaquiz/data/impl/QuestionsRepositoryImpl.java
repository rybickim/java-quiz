package com.rybickim.javaquiz.data.impl;

import com.rybickim.javaquiz.data.QuestionsRepository;
import com.rybickim.javaquiz.domain.Questions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("questionsRepo")
public class QuestionsRepositoryImpl implements QuestionsRepository {

    private SessionFactory sessionFactory;

//    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long save(Questions question) {
//        return (Long) sessionFactory.getCurrentSession().save(question);
        sessionFactory.getCurrentSession().saveOrUpdate(question);
        return 1L;
    }

    @Override
    public Questions get(Long id) {
        return sessionFactory.getCurrentSession().get(Questions.class, id);
    }

    @Override
    public List<Questions> list() {
        return sessionFactory.getCurrentSession().createQuery("FROM Questions").list();
    }

    @Override
    public void update(Questions question) {
        sessionFactory.getCurrentSession().update(question);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Questions question = session.byId(Questions.class).load(id);
        session.delete(question);
    }
}
