
## Was tun bei Merchkonflikt?
### Im fall das der Konflikt behoben ist, sich Github Desktop aber trotzdem noch quer stellt:
 1. Im Ordner des Projekts über rechtsklick die Git-Bash oder die Shell öffnen
 2. Den Befehl 'git status' ausführen
 3. Es wird eine Liste gezeigt die in Kategorien geteilt ist, eine davon ist "Unresolved Conflicts", in dieser steht ein in rot markierter Dateipfad -> diesen kopieren.
 4. Den Befehl 'git add #' ausführen, an die Stelle des # den Dateipfad kopieren.
 5. Done!


## Was tun, beim login?
### welche Daten brauche ich, wo kommen die her?
Die login Seite ist von spring Security und erlaubt es, nur autorisierten Nutzern den Zugriff auf die Oberfläche.<br>
<del>Da wir noch nirgendwo Benutzer festgelegt haben, kommt der Standard (default) Nutzer zum Einsatz.</del>

<del>
**Nutzername:** user <br>
**Passwort:** steht in der (run)Console <br>
sieht z.B. so aus: <br>
Using generated security password: 931f7e1d-9ba1-4e66-9197-02de4072a6a8
</del>

↓

### Neuer Weg
**Nutzername:** admin <br>
**Passwort:** 123
