package org.example;

import org.example.entities.Comparison;
import org.example.entities.ShoeModel;
import org.example.entities.Shoes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 * DatabaseUtility class for database operations using the Hibernate framework.
 * Provides methods for inserting and querying shoe models, shoes, and comparisons into/from the database.
 */

public class DatabaseUtility {
    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(ShoeModel.class)
            .addAnnotatedClass(Shoes.class)
            .addAnnotatedClass(Comparison.class)
            .buildSessionFactory();

    /**
     * This method is used to check whether the skuFull exists in the database
     * @param skuFull sku_full of the shoe
     * @return boolean value indicating whether the skuFull exists in the database
     */
    public boolean skuFullExists(String skuFull) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Shoes.class, skuFull) != null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * This method is used to check whether the skuBase exists in the database
     * @param skuBase sku_base of the shoe
     * @return boolean value indicating whether the skuBase exists in the database
     */
    public boolean skuExists(String skuBase) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ShoeModel.class, skuBase) != null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * This method is used to check whether the url exists in the database
     * @param url url of the shoe
     * @return boolean value indicating whether the url exists in the database
     */
    public boolean urlExists(String url) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT count(c.id) FROM Comparison c WHERE c.websiteUrl = :url";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("url", url);
            return query.uniqueResult() > 0;
        }
    }
    /**
     * This method is used to insert the shoe model into the database
     * @param sku sku of the shoe
     * @param brand brand of the shoe
     */
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
    /**
     * Inserts a new shoe into the database. If the shoe model does not exist, it creates a new model entry.
     * @param skuFull sku_full of the shoe
     * @param skuBase sku_base of the shoe
     * @param fullName full name of the shoe
     * @param imageUrl image url of the shoe
     */
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
    /**
     * Inserts a new comparison into the database. If the shoe does not exist, it creates a new shoe entry.
     * @param skuFull sku_full of the shoe
     * @param websiteUrl website url of the shoe
     * @param sellingPrice selling price of the shoe
     */
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
    /**
     * This method is used to close the session factory
     */
    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
