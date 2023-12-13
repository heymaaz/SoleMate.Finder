package org.example;

import org.example.entities.Comparison;
import org.example.entities.ShoeModel;
import org.example.entities.Shoes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class DatabaseUtility {
    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(ShoeModel.class)
            .addAnnotatedClass(Shoes.class)
            .addAnnotatedClass(Comparison.class)
            .buildSessionFactory();

    public boolean skuFullExists(String skuFull) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Shoes.class, skuFull) != null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean skuExists(String skuBase) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ShoeModel.class, skuBase) != null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean urlExists(String url) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT count(c.id) FROM Comparison c WHERE c.websiteUrl = :url";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("url", url);
            return query.uniqueResult() > 0;
        }
    }
    public void insertShoeModel(String sku, String brand){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ShoeModel shoeModel = new ShoeModel(sku, brand);
            session.persist(shoeModel);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertShoes(String skuFull, String skuBase, String fullName, String imageUrl){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ShoeModel shoeModel = session.get(ShoeModel.class, skuBase);
            if (shoeModel == null) {
                shoeModel = new ShoeModel(skuBase, "Nike");
                session.persist(shoeModel);
            }
            Shoes shoes = new Shoes(skuFull, shoeModel, fullName, imageUrl);
            session.persist(shoes);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertComparison(String skuFull, String websiteUrl, String sellingPrice){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Shoes shoes = session.get(Shoes.class, skuFull);
            if(shoes == null) {
                System.out.println("Shoes not found");
                return;
            }
            Comparison comparison = new Comparison(shoes, websiteUrl, sellingPrice);
            session.persist(comparison);
            session.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
