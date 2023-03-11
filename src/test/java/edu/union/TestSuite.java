package edu.union;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ColorTests.class,
                ColorRepositoryTests.class,
                RectangleGridLevelTests.class,
                RectangleGridLevelBuilderTests.class,
                MoveTests.class,
                ColoredGraphTests.class,
                ColoredGraphSolverTests.class,
                LevelRepositoryManagerTests.class,
                RectangleGridCellTests.class,
                LevelBuilderFactoryTests.class,
                RectangleHintInputLevelTests.class,
                CommandTest.class
        }
)

public class TestSuite {
}
