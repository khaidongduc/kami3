
Honor Code:
As a student at Union College, I am part of a community that values intellectual effort, curiosity and discovery. I understand that in order to truly claim my educational and academic achievements, I am obligated to act with academic integrity. Therefore, I affirm that I will carry out my academic endeavors with full academic honesty, and I rely on my fellow students to do the same.

Khai Dong, Zachary Dubinsky, Jack Collins

The app makes use of MVC architecture pattern

Design pattern used
- The model-view relationship is an observable-observer relationship.
- the LevelRepositoryManager is a mix of mediator and factory method pattern where the LevelRepositoryManager navigate
between multiple LevelRepository to give the Level object construction task to.
- LevelRepositoryManager also used strategy pattern, allowing loading multiple LevelRepository (File I/O strategy) in it 
for management.
- The levelBuilderFactory uses factory method pattern and prototype pattern to clone a registered levelBuilder object  everytime it is asked to make a new levelBuilder
- The LevelBuilders are cloneable (prototype pattern)
- All levels, levelBuilders, and the LevelRepositoryManager are observable.

The game is abstracted to a graph, and the Concrete Level realized the Graph into an explicit object (e.g. a grid)

The final diagrams are in [diagrams/final](/diagrams/final) folders
- The model_class_diagram.uxf described the relationship between the model and the service
- The model_view_controller_class_diagram.uxf described how service is used by the controller for construction of complex objects
  (Level and LevelBuilder objects)

To build the project
```agsl
gradle build
```
To run the project
```agsl
gradle run
```
