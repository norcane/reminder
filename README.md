# reminder
**Say goodbye to forgotten TODOs in your code!**

Were you ever forced to do some _quick&dirty_ changes or hot fixes to your codebase telling yourself you will review this part of code later? Are code comments like `//TODO make this better` or `//FIXME bring some sanity to this` familiar to you? Once you put these comment in your codebase, it's easy to forget about them. Time to say goodbye to them and introduce the `@reminder` annotation! Simply put this annotation to the code you want to revisit in future and after selected date, this code won't compile anymore, forcing you (or your colleagues) to review it.

## Make it a part of your project
Add following definitions to `build.sbt`:
```scala
  // enable macro paradise plugin
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  
  // add the Bintray resolver
  resolvers += Resolver.bintrayRepo("norcane", "reminder")
  
  // add reminder as dependency
  libraryDependencies += "com.norcane" %% "reminder" % "0.1.0"
```
At this moment, *reminder* is cross compiled for Scala 2.11 and 2.12.

## Show me the code!
```scala
  import com.norcane.reminder
  
  @reminder("2200-02-02")       // <-- will compile, you have plenty of time to review this
  def someDirtyImplementation() = { ... }
  
  @reminder("2000-02-02")       // <-- will cause compile error
  def someDirtyImplementation() = { ... }
  
  @reminder("dsfwefioh")        // <-- invalid date format will also cause compile error
  def someDirtyImplementation() = { ... }
  
  @reminder("2200/02/02", dateFormat = "yyyy/MM/dd")       // <-- using custom date format
  def someDirtyImplementation() = { ... }
  
  @reminder("2200/02/02", "I really need to fix this")     // <-- using custom message
  class ReallyUglyStuff() { ... }
```

## Ok, show me more!
Below is the definition of the `@reminder` annotation, with corresponding default values:
```scala
  class reminder(date: String,
                 note: String = "Please make sure that the annotated part of your codebase is still valid",
                 dateFormat: String = "yyyy-MM-dd")
```
Feel free to combine parameters as required.

## What kind of sorcery is this?
The `@reminder` annotation is implemented as [Scala Macro Annotation](https://docs.scala-lang.org/overviews/macros/annotations.html), using the [Macro Paradise](https://github.com/scalamacros/paradise) compiler plugin. In fact, it does nothing more than that it compares the current date and the date you set as the annotation parameter and if the set date is in past, it will cause compiler error.
