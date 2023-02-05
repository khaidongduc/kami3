package edu.union;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ColorTests.class,
                ColorGridTests.class,
                ColorRepositoryTests.class,
                LevelImplTests.class
        }
)

public class TestSuite {
}
