
Honor Code:
As a student at Union College, I am part of a community that values intellectual effort, curiosity and discovery. I understand that in order to truly claim my educational and academic achievements, I am obligated to act with academic integrity. Therefore, I affirm that I will carry out my academic endeavors with full academic honesty, and I rely on my fellow students to do the same.

Khai Dong, Zachary Dubinsky, Jack Collins

TODO:
- Saving builds.
- Adding new menu items.
- Dynamic size grid panes for Kami games that are not 5x5.
- Resizing functionality for build menu.
- Solver for Kami games
  - Integrated w/ builder for min number of moves.
  - Integrated w/ levels for hint (ie. best next move).
- Testing 
- Color palate size in builder.
- Address Level/LevelBuilder interface redundencies.

The app makes use of MVC architecture pattern

- The model-view relationship is an observable-observer relationship
<!---
- The controller mediates the action from the view to the model. This is a one-way mediator pattern from view to model with controller
  as the mediator. This is mediator pattern because there is no interaction from model to view. 
-->

- There are several iterator pattern embed into the data structure provided by java.
- There is a FactoryMethod to construct a Level object provided by LevelRepostiory.loadLevel(). All exact Level construction logic
  is here. A FactoryMethod to construct the save file for a Level is LevelRepository.saveLevel().
- The LevelRepository, ColorRepository are Singleton. These are singleton because they are used consistently by all other
  app's functionality, and should be shared across the app.
- The ColorGrid provides the TemplateMethod pattern. Since the color grid only differ by the adjacency relationship between the cells
  we can inherit ColorGrid and override the getNeighborPositions() method to obtain a different type of ColorGrid (triangle grid etc.,)
- The Composite structure is provided by JavaFX in constructing and styling the scene

Plan to implement
- Strategy pattern for LevelRepository to allow loading and saving more types of file other than raw text.