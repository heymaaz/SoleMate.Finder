package org.example;

import static org.mockito.Mockito.*;

import org.example.entities.Comparison;
import org.example.entities.ShoeModel;
import org.example.entities.Shoes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Provides unit tests for the DatabaseUtility class using Mockito framework.
 * These tests are designed to ensure that the DatabaseUtility class functions correctly,
 * focusing on its interaction with Hibernate to perform various database operations
 * such as querying and persisting data related to shoes, shoe models, and comparisons.
 * The tests use mock objects to simulate the behavior of Hibernate's SessionFactory and Session,
 * allowing for isolated testing without the need for an actual database connection.
 */
class DatabaseUtilityTest {

    @Mock
    private SessionFactory mockedSessionFactory;
    @Mock
    private Session mockedSession;

    private DatabaseUtility databaseUtility;

    /**
     * Sets up the testing environment before each test.
     * Initializes mock and creates an instance of DatabaseUtility with a mocked SessionFactory.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockedSessionFactory.openSession()).thenReturn(mockedSession);

        databaseUtility = new DatabaseUtility(mockedSessionFactory);
    }

    /**
     * Tests whether the skuFullExists method correctly returns true when the SKU full exists.
     */
    @Test
    void skuFullExists_whenSkuFullExists_returnsTrue() {
        String skuFull = "testSkuFull";
        Shoes mockShoes = new Shoes();
        when(mockedSession.get(Shoes.class, skuFull)).thenReturn(mockShoes);

        boolean exists = databaseUtility.skuFullExists(skuFull);

        assertTrue(exists);
    }

    /**
     * Tests whether the skuFullExists method correctly returns false when the SKU full does not exist.
     */
    @Test
    void skuFullExists_whenSkuFullDoesNotExist_returnsFalse() {
        String skuFull = "testSkuFull";
        when(mockedSession.get(Shoes.class, skuFull)).thenReturn(null);

        boolean exists = databaseUtility.skuFullExists(skuFull);

        assertFalse(exists);
    }

    /**
     * Tests whether the skuExists method correctly returns true when the SKU base exists.
     */
    @Test
    void skuExists_whenSkuExists_returnsTrue() {
        String skuBase = "testSkuBase";
        ShoeModel mockShoeModel = new ShoeModel();
        when(mockedSession.get(ShoeModel.class, skuBase)).thenReturn(mockShoeModel);

        boolean exists = databaseUtility.skuExists(skuBase);

        assertTrue(exists);
    }

    /**
     * Tests whether the skuExists method correctly returns false when the SKU base does not exist.
     */
    @Test
    void skuExists_whenSkuDoesNotExist_returnsFalse() {
        String skuBase = "testSkuBase";
        when(mockedSession.get(ShoeModel.class, skuBase)).thenReturn(null);

        boolean exists = databaseUtility.skuExists(skuBase);

        assertFalse(exists);
    }

    /**
     * Tests whether the urlExists method correctly returns true when the URL exists.
     */
    @Test
    void urlExists_whenUrlExists_returnsTrue() {
        String url = "http://testurl.com";
        Query mockQuery = mock(Query.class);
        when(mockedSession.createQuery(anyString(), eq(Long.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter("url", url)).thenReturn(mockQuery);
        when(mockQuery.uniqueResult()).thenReturn(1L);

        boolean exists = databaseUtility.urlExists(url);

        assertTrue(exists);
    }

    /**
     * Tests whether the urlExists method correctly returns false when the URL does not exist.
     */
    @Test
    void urlExists_whenUrlDoesNotExist_returnsFalse() {
        String url = "http://testurl.com";
        Query mockQuery = mock(Query.class);
        when(mockedSession.createQuery(anyString(), eq(Long.class))).thenReturn(mockQuery);
        when(mockQuery.setParameter("url", url)).thenReturn(mockQuery);
        when(mockQuery.uniqueResult()).thenReturn(0L);

        boolean exists = databaseUtility.urlExists(url);

        assertFalse(exists);
    }

    /**
     * Tests the insertShoeModel method to ensure that a new shoe model is successfully inserted into the database.
     */
    @Test
    void insertShoeModel_insertsShoeModelSuccessfully() {
        String sku = "testSku";
        String brand = "testBrand";
        Transaction mockTransaction = mock(Transaction.class);

        when(mockedSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockedSession.getTransaction()).thenReturn(mockTransaction);

        databaseUtility.insertShoeModel(sku, brand);

        verify(mockedSession).persist(any(ShoeModel.class));
        verify(mockTransaction).commit();
    }

    /**
     * Tests the insertShoes method to ensure that a new shoe is successfully inserted when the shoe model already exists.
     */
    @Test
    void insertShoes_whenShoeModelExists_insertsShoesSuccessfully() {
        String skuFull = "testSkuFull";
        String skuBase = "testSkuBase";
        String fullName = "testFullName";
        String imageUrl = "http://testimageurl.com";
        ShoeModel mockShoeModel = new ShoeModel(skuBase, "Nike");
        Transaction mockTransaction = mock(Transaction.class);

        when(mockedSession.get(ShoeModel.class, skuBase)).thenReturn(mockShoeModel);
        when(mockedSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockedSession.getTransaction()).thenReturn(mockTransaction);

        databaseUtility.insertShoes(skuFull, skuBase, fullName, imageUrl);

        verify(mockedSession).persist(any(Shoes.class));
        verify(mockTransaction).commit();
    }

    /**
     * Tests the insertShoes method to ensure that a new shoe and its model are successfully inserted when the shoe model does not exist.
     */
    @Test
    void insertShoes_whenShoeModelDoesNotExist_createsAndInsertsShoesSuccessfully() {
        String skuFull = "testSkuFull";
        String skuBase = "testSkuBase";
        String fullName = "testFullName";
        String imageUrl = "http://testimageurl.com";
        Transaction mockTransaction = mock(Transaction.class);

        when(mockedSession.get(ShoeModel.class, skuBase)).thenReturn(null);
        when(mockedSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockedSession.getTransaction()).thenReturn(mockTransaction);

        databaseUtility.insertShoes(skuFull, skuBase, fullName, imageUrl);

        verify(mockedSession).persist(any(ShoeModel.class));
        verify(mockedSession).persist(any(Shoes.class));
        verify(mockTransaction).commit();
    }

    /**
     * Tests the insertComparison method to ensure that a new comparison is successfully inserted when the corresponding shoes exist.
     */
    @Test
    void insertComparison_whenShoesExist_insertsComparisonSuccessfully() {
        String skuFull = "testSkuFull";
        String websiteUrl = "http://testwebsiteurl.com";
        String sellingPrice = "testSellingPrice";
        Shoes mockShoes = new Shoes();
        Transaction mockTransaction = mock(Transaction.class);

        when(mockedSession.get(Shoes.class, skuFull)).thenReturn(mockShoes);
        when(mockedSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockedSession.getTransaction()).thenReturn(mockTransaction);

        databaseUtility.insertComparison(skuFull, websiteUrl, sellingPrice);

        verify(mockedSession).persist(any(Comparison.class));
        verify(mockTransaction).commit();
    }

    /**
     * Tests the insertComparison method to ensure that a comparison is not inserted when the corresponding shoes do not exist.
     */
    @Test
    void insertComparison_whenShoesDoNotExist_doesNotInsertComparison() {
        String skuFull = "testSkuFull";
        String websiteUrl = "http://testwebsiteurl.com";
        String sellingPrice = "testSellingPrice";
        Transaction mockTransaction = mock(Transaction.class);

        when(mockedSession.get(Shoes.class, skuFull)).thenReturn(null);
        when(mockedSession.beginTransaction()).thenReturn(mockTransaction);
        when(mockedSession.getTransaction()).thenReturn(mockTransaction);

        databaseUtility.insertComparison(skuFull, websiteUrl, sellingPrice);

        verify(mockedSession, never()).persist(any(Comparison.class));
        verify(mockTransaction, never()).commit();
    }

    /**
     * Tests the closeSessionFactory method to ensure that the SessionFactory is properly closed.
     */
    @Test
    void closeSessionFactory_closesSessionFactory() {
        doNothing().when(mockedSessionFactory).close();

        assertDoesNotThrow(() -> databaseUtility.closeSessionFactory());
    }
}
