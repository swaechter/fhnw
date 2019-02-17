package ch.fhnw.edu.rental.model;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StockTest {
    public static final int LISTENER_THRESHOLD = 2;
    Stock testStock;
    Movie mockMovie;
    LowStockListener testListener;

    @Before
    public void setUp() throws Exception {
        testStock = new Stock();
        mockMovie = mock(Movie.class);
        when(mockMovie.getTitle()).thenReturn("The Revenant");
        testListener = mock(LowStockListener.class);
        when(testListener.getThreshold()).thenReturn(LISTENER_THRESHOLD);
    }

    //test 1
    @Test
    public void testAddToStock() {
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);
        assertEquals(2, testStock.getInStock(mockMovie.getTitle()));
    }

    //test 2
    @Test(expected=MovieRentalException.class)
    public void testRemoveFromStock() throws Exception {
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);

        testStock.removeFromStock(mockMovie);
        assertEquals(testStock.getInStock(mockMovie.getTitle()), 3);
        testStock.removeFromStock(mockMovie);
        assertEquals(testStock.getInStock(mockMovie.getTitle()), 2);
        testStock.removeFromStock(mockMovie);
        assertEquals(testStock.getInStock(mockMovie.getTitle()), 1);
        testStock.removeFromStock(mockMovie);
        assertEquals(testStock.getInStock(mockMovie.getTitle()), 0);
        
        //exception expected after this call
        testStock.removeFromStock(mockMovie.getTitle());

    }
    
    //test 2,3 & 4
    @Test
    public void testLowStockListener() throws Exception {
        testStock.addLowStockListener(testListener);
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);

        testStock.removeFromStock(mockMovie);
        verify(testListener, times(0)).stockLow(mockMovie, 3);
        testStock.removeFromStock(mockMovie);
        testStock.removeFromStock(mockMovie);

        verify(testListener, times(1)).stockLow(mockMovie, 2);
        verify(testListener, times(1)).stockLow(mockMovie, 1);
        verify(testListener, times(1)).stockLow(mockMovie, 0);

        // Test remove StockListener
        testStock.removeLowStockListener(testListener);

        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);
        testStock.addToStock(mockMovie);

        testStock.removeFromStock(mockMovie);
        testStock.removeFromStock(mockMovie);
        testStock.removeFromStock(mockMovie);
    }

    @After
    public void tearDown() throws Exception {
        testStock = null;
    }

}
