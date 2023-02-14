package edu.union;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ColorTests.class,
                ColorGridTests.class,
                ColorRepositoryTests.class,
                RectangleGridLevelTests.class,
                RectangleGridLevelBuilderTests.class,
                MoveTests.class,
                ColoredGraphTests.class,
                ColoredGraphSolverTests.class,
                LevelRepositoryManagerTests.class
        }
)

public class TestSuite {
}
