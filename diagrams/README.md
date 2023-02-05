The app makes use of MVC architecture pattern

- The model-view relationship is an observable-observer relationship
- The controller mediates the action from the view to the model. This is a one-way mediator pattern from view to model with controller
as the mediator. This is mediator pattern because there is no interaction from model to view.
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