# Integrationsanleitung ToolBar

## Einleitung

Link: https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/INSTALL1.md

Die ToolBar stellt eine kompakte ToolBar inklusive deaktivierbaren Buttons zur Verfügung. Das Styling erfolgt dabei über eine CSS Datei. In einer Applikation sieht die ToolBar per default wie folgt aus:

![Missing toolbar image](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/img/HydroToolBar1.png?raw=true "HydroToolBar")

Oder in der Nahansicht:

![Missing toolbar image](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/img/HydroToolBar2.png?raw=true "HydroToolBar")

![Missing toolbar image](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/img/HydroToolBar3.png?raw=true "HydroToolBar")

![Missing toolbar image](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/img/HydroToolBar4.png?raw=true "HydroToolBar")

## Installation

Für die Installation müssen folgende Schritte getätigt werden:

1. Navigiere ins Repository unter [https://github.com/swaechter/fhnw/tree/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/toolbar](https://github.com/swaechter/fhnw/tree/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/toolbar)
2. Lade die Dateien `HydroToolBar.java` und `toolbar-control.css` herunter und platziere beide Dateien in deinem Projekt (**Achtung**: Beide Dateien müssen sich im gleichen Verzeichnis befinden!)
3. Öffne nun die Datei `HydroToolBar.java` und passe den Namespace des Packages `package` in der ersten Zeile an
4. Kopiere den Inhalt aus `src/main/resources/fonts` in dein Projekt

**Hinweis**: Unsere cuie Templates verfügen über die Font `FontAwesome`, welche Du unter `src/main/resources/fonts/fontawesome-webfont.ttf` finden kannst. Solltest Du diese Datei nicht haben, kontaktiere bitte Herrn Holz.

## Verwendung

Beispielapplikation: [https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/toolbar/ToolBarApplication.java](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/toolbar/ToolBarApplication.java)

Nach der Installation befindet sich die ToolBar in deinem Projekt und kann in deine Applikation eingebunden werden. Am einfachsten ist es, die Toolbar als `Top` Element in ein `BorderPane` einzubinden:

```java
HydroToolBar toolBar = new HydroToolBar();

BorderPane borderPane = new BorderPane();
borderPane.setTop(toolBar);
borderPane.setCenter(new TextArea()); // Zur Veranschaulichung eine TextArea
```

Um auf alle Buttons und das Suchfeld zuzugreifen:

```java
Button saveButton = toolBar.getSaveButton();
Button createButton = toolBar.getCreateButton();
Button deleteButton = toolBar.getDeleteButton();
Button undoButton = toolBar.getUndoButton();
Button redoButton = toolBar.getRedoButton();
Button germanLanguageButton = toolBar.getGermanLanguageButton();
Button englishLanguageButton = toolBar.getEnglishLanguageButton();
TextField searchTextField = toolBar.getSearchTextField();
```

Um darauf Event Listeners zu installieren:

```java
saveButton.setOnAction(event -> System.out.println("Save button clicked!"));
createButton.setOnAction(event -> System.out.println("Create button clicked!"));
deleteButton.setOnAction(event -> System.out.println("Delete button clicked!"));
undoButton.setOnAction(event -> System.out.println("Undo button clicked!"));
redoButton.setOnAction(event -> System.out.println("Redo button clicked!"));
germanLanguageButton.setOnAction(event -> System.out.println("German language button clicked!"));
englishLanguageButton.setOnAction(event -> System.out.println("English language button clicked!"));
searchTextField.setOnAction(event -> System.out.println("Search text field submitted!"));
searchTextField.setOnKeyTyped(event -> System.out.println("Search text field typed!"));
```

Um einen Button zu aktivieren (Button ist dann weiss --> Standard) oder zu deaktivieren (Button ist dann ausgegraut):

```java
saveButton.setDisable(false); // Aktiv und weiss, default
saveButton.setDisable(true); // Inaktiv und ausgegraut
```

Um die Farben und Grössen der Toolbar und deren Elemente zu ändern, wird auf die CSS Datei `toolbar-control.css`zurückgegriffen:

```css
.toolbar-control { /* Eigentliche Toolbar */
    -fx-background-color: #1CA3EC; /* Hintergrundfarbe der Toolbar */
}

.toolbar-control .button-icon { /* Buttons mit einem Icon */
    -fx-font-family: FontAwesome; /* Font, welche via Unicode PUA die Icons zur Verfügung stellt */
    -fx-font-size: 18; /* Icongrösse */ 
    -fx-text-fill: white; /* Aktive Iconfarbe */
    -fx-background-color: transparent; /* Kein Buttonbackground */
}

.toolbar-control .button-text { /* Buttons mit einem Text */
    -fx-font-size: 16; /* Textgrösse - bewusst 2px kleiner wählen um nicht zu gross zu wirken */
    -fx-text-fill: white; /* Aktive Textfarbe */
    -fx-background-color: transparent; /* Kein Buttonbackground */
}

.toolbar-control .textfield { /* Textfeld */
    -fx-border-radius: 10 10 10 10; /* Abgerundete Ecken mit 10px Radius */
    -fx-background-radius: 10 10 10 10;
}
```

**Hinweis**: Die `HydroToolBar` agiert bewusst nur als Container für die Buttons und die Suche, um dir die grösstmöglichste Flexibilität zu geben, ohne dich einzuschränken.

## Kontakt

Bei Fragen oder Unklarheiten könnt ihr mich (Simon Wächter) gerne kontaktieren: simon.waechter@students.fhnw.ch

Quelle: [https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/INSTALL1.md](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/INSTALL1.md)
