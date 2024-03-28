Das Test Projekt im Branch sollte sowohl mit Eclipse als auch Intellij(sowie mit VSC) startbar sein, da ich es außerhalb der IDE's erstellt habe. 
Desweiteren ist das ist das Projekt auch bereits mit Maven versehen und so mit quasi fast das Projekt was wir auf der Main nutzen werden.

zu Beachten:
 das Projekt sollte bei euch starten aber mit einem Error exiten, das liegt daran das für die korrekte Funktion eine aktuelle MySQL Instanz der Version 8.0.36 nötig ist die unteranderem auch die richtigen Root-Einstellungen besitzen muss.

Zu Beachten wärhend der Installation:
1. Server Only reicht
2. Netzwerkeinstellung(Port+TCP/IP) beim standart lassen!
3. Root einstellungen:
  * Benutzername: root
  * Password: IHaveGoodView11!

Nach der Installation zu machen:
1. MySQL 8.0 Comand Line Client starten
2. Folgende zeile Copy-Pasten: CREATE DATABASE dbtest;

fertig!
