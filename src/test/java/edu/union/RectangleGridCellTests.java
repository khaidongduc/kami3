package edu.union;

import edu.union.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class RectangleGridCellTests {
    RectangleGridCell cell;
    @Before
    public void setUp(){
        cell = new RectangleGridCell(1,1);
    }

    @After
    public void tearDown(){cell = null;}
}
