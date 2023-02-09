package edu.union;

import edu.union.model.LevelBuilderImpl;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ColorTests.class,
                ColorGridTests.class,
                ColorRepositoryTests.class,
                LevelImplTests.class,
                MoveTests.class,
                LevelBuilderImpl.class,
                ColoredGraphTests.class,
                LevelSolverTests.class,
                LevelRepositoryStrategyTests.class
        }
)

public class TestSuite {
}
