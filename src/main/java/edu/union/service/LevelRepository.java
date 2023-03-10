package edu.union.service;

import edu.union.model.*;

import java.util.List;
import java.util.concurrent.*;

/**
 * abstract class for levelRepository
 */
public abstract class LevelRepository {

    public static final int MAXBUILDTIME = 5; // seconds

    protected LevelRepository successor;
    protected int maxBuildTime;

    protected LevelRepository() {
        this(MAXBUILDTIME);
    }

    protected LevelRepository(int maxBuildTime) {
        this.maxBuildTime = maxBuildTime;
    }


    /**
     * set the successor if this levelRepository failed to work
     * @param levelRepository the successor levelRepository
     */
    public void setSuccessor(LevelRepository levelRepository){
        this.successor = levelRepository;
    }

    /**
     * load level from a levelInfo
     * will fail if the levelInfo of a wrong type is passed
     * @param levelInfo the levelInfo
     * @return the Level associated with levelInfo
     */
    public Level loadLevel(LevelInfo levelInfo){
        try {
            return _loadLevel(levelInfo);
        } catch (Exception ex){
            if (successor != null)
                return successor.loadLevel(levelInfo);
            else
                throw new RuntimeException(ex);
        }
    }

    /**
     * save a level created by a levelBuilder to a specific folder
     * will fail if the levelBuilder of a wrong type is passed
     * @param levelBuilder the levelBuilder
     * @param folderPath the folder path
     */
    public void saveLevel(LevelBuilder levelBuilder, String folderPath){
        ExecutorService executor = Executors.newSingleThreadExecutor();


        Callable <List<Move>> solverTask = ColoredGraphSolverTaskGenerator.getInstance()
                .getSolverTask(levelBuilder.getGraph());

        Future<List<Move>> future = executor.submit(
                () -> solverTask.call());
        try {
            List<Move> hints = future.get(this.maxBuildTime, TimeUnit.SECONDS);
            _saveLevel(levelBuilder, hints, folderPath);
        }
        catch(TimeoutException e){
            future.cancel(true);
            executor.shutdownNow();
            throw new RuntimeException("Puzzle is too complex, unable to save in reasonable time. " + e);
        } catch (Exception e){
            future.cancel(true);
            executor.shutdownNow();
            throw new RuntimeException(e);
        }
    }

    public void saveLevel(LevelHint levelHint, String folderPath){
        _saveLevel(levelHint, folderPath);
    }

    protected abstract Level _loadLevel(LevelInfo levelInfo);
    protected abstract void _saveLevel(LevelBuilder levelBuilder, List<Move> hints, String folderPath);
    protected abstract void _saveLevel(LevelHint levelHint, String folderPath);

}
