# Integrationsanleitung CountryCantonMap

## Einleitung

Link: https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/INSTALL2.md

Die CountryCantonMap stellt eine grosse, aber auch stark komprimierbare, Übersicht aller Schweizer Kantone dar. Dabei können Kantone an- und abgewählt, auf Klicks auf Kantone reagiert und dynamisch Farben gesetzt werden. Das Styling erfolgt über Methoden. In einer Applikation sieht die CountryCantonMap per default wie folgt aus:

![Missing country canton map image](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/img/CountryCantonMap1.png?raw=true "CountryCantonMap")

Oder in einer stark komprimierten Form (Um beispielsweise den Kanton eines Kraftwerkes anzuzeigen):

![Missing country canton map image](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/img/CountryCantonMap2.png?raw=true "CountryCantonMap")

**Achtung**: Das Einstellungsmenü auf rechten Seite ist **NICHT** Bestandteil der Map. Es dient schliechtweg dazu, die Map zu Testzwecken beeinflussen zu können.
## Installation

Für die Installation müssen folgende Schritte getätigt werden:

1. Navigiere ins Repository unter [https://github.com/swaechter/fhnw/tree/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/countrycantonmap](https://github.com/swaechter/fhnw/tree/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/countrycantonmap)
2. Lade alle dortigen Klassen herunter und platziere diese in deinem Projekt
3. Öffne alle Dateien und passe den Namespace des Packages `package` in der ersten Zeile an

## Verwendung

Beispielapplikation: [https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/countrycantonmap/CountryCantonMapApplication.java](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/src/main/java/ch/fhnw/cuie/project/countrycantonmap/CountryCantonMapApplication.java)

Nach der Installation befindet sich die CountryCantonMap in deinem Projekt und kann in deine Applikation eingebunden werden. Zuerst müssen aber alle Kantone erstellt werden:

```java
ObservableList<Canton> observableCantonList = CantonUtils.getAllCantons();
```

Danach kann die CountryCantonMap erstellt werden:

```java
CountryCantonMap countryCantonMap = new CountryCantonMap(observableCantonList);
```

Die CountryCantonMap arbeitet vollständig auf der übergebenen Collection, sprich sie beobachtet die Werte der sich darin befindlichen Kantone. Um Beispiel den Kanton `Aargau` zu selektieren, holen wir uns das erste Element aus der Liste und setzen es aktiv (Der Kanton sollte nun aufleuchten):

```java
Canton canton = observableCantonList.get(0);
canton.setIsActive(true);
```

Analog dazu kann auch ein Klickhandler installiert werden. Dort kann man beispielsweise auf den Klick reagieren und den Zustand des geklickten Kantons ändern, was noch auskommentiert ist:

```java
countryCantonMap.setCantonClickCallback(canton -> {
    System.out.println("Canton clicked: " + canton);
    //canton.setIsActive(!canton.isIsActive()); // Einkommentieren, falls Status des Kantons geändert werden soll
});
```

Die einzelnen Farben lassen sich wie folgt verändern:

```java
Color color = ... 
countryCantonMap.setActiveCantonColor(color); // Farbe der angewählten/aktiven Kantone
countryCantonMap.setInactiveCantonColor(color); // Farbe der nicht angewählten/inaktiven Kantone
countryCantonMap.setBorderColor(color); // Ränderfarbe zwischen den Kantonen
```

## Kontakt

Bei Fragen oder Unklarheiten könnt ihr mich (Simon Wächter) gerne kontaktieren: simon.waechter@students.fhnw.ch

Quelle: [https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/INSTALL2.md](https://github.com/swaechter/fhnw/blob/master/Module/cuie/08_HydroPower/INSTALL2.md)
