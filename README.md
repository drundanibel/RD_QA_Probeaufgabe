# RD_QA_Probeaufgabe

## Katalon Studio

Aufgaben 1 und 3 wurden mit *Katalon-Studio v6.3.3.11* im Projekt ***katalon_studio_relaxdays_shopping_cart_and_chrome_settings*** in folgenden Test-Suites umgesetzt:

### Aufgabe 1: 1_test_shopping_flow

#### Einzelheiten zum Test-Workflow

- Die in den Warenkorb zu legenden Produkte wurden mit ihren vorgegebenen Suchbegriffen und einer eventuellen Produktoption in der Excel-Tabelle products.xlsx hinterlegt und zur Testlaufzeit eingelesen, wodurch sich die Testdaten flexibel anpassen und erweitern lassen. Dabei wird unterstellt, dass die Suchbegriffe hinreichend scharf formuliert sind, dass ein eindeutiger Treffer (inkl. evtl. zusätzlich angegebener Produktoption) bereits auf der ersten Suchergebnisseite gefunden werden muss.
- Der hierfür notwendige Textabgleich wird anhand der einzelnen Suchbegriffe vorgenommen, die unabhängig von ihrer Reihenfolge allesamt im Link zum jeweiligen Produkt vorkommen müssen. Dazu werden jeweils beide zu vergleichende Texte normalisiert (Kleinschreibung, Konvertierung von Umlauten und sonstigen Diakritika sowie Aufspaltung in einzelne Wörter anhand von Nicht-Wort-Zeichen). Das erste Produkt, das auf der ersten Ergebnisseite entsprechend dieser Suchkriterien "passt", wird (ggf. nach Auswahl der zusätzlich erwarteten Produktoption) in den Warenkorb gelegt.
- Der Test toleriert es zwar, wenn ein Produkt (ggf. inkl. einer erwarteten Produktoption) nicht auf der ersten Ergebnisseite gefunden wird, geht in diesem Fall aber zum nächsten Produkt aus der Einkaufsliste über. D.h. in diesem Fall wird kein Produkt in den Warenkorb gelegt. Dies war vorliegend bereits beim ersten Produkt der Liste "Pavillon 6-eckig mit Seitenwänden" der Fall, das seit wenigen Tagen nicht mehr bei Relaxdays lieferbar ist.
- Während der Testdurchführung werden Screenshots und Videos erstellt. Zusätzlich werden für die Aufgabenstellung relevante Feststellungen durch begleitende Log-Ausgaben dokumentiert.

#### Mehrfache Testausführung

- Wird realisiert, indem innerhalb der Test-Suite die Test-Cases *01_reinit_browser*, *0_open_relaxdays_homepage* und *1_put_products_into_shopping_cart* in dieser Reihenfolge beliebig oft vor dem abschließenden Test-Case *99_close_browser* eingefügt werden können. Dabei wird durch *1_put_products_into_shopping_cart* implizit auch der Test-Case *2_check_subtotal* aufgerufen, der die Einzelpreise der zuvor in den Warenkorb gelegten Produkte als Eingabedaten übergeben erhält, um deren Summe mit dem Gesamtwert des Warenkorbs zu vergleichen.
- Durch das Löschen aller Cookies zwichen zwei Testdurchläufen wird der Warenkorb jeweils zurückgesetzt. Dadurch sind die Ergebnisse beliebig oft replizierbar, solange sich Verfügbarkeit, Inhalt, Reihenfolge und Preis der jeweils angezeigten Suchtreffer nicht maßgeblich ändern.

#### Erfolgreich getestet in folgenden Umgebungen

- *Windows 10* mit *Chrome v81.0.4044.138 (headful und headless)*
- *Windows 10* mit *Microsoft Edge v44.18362.449.0*

### Aufgabe 3: 2_change_chrome_search_provider

#### Allgemeines

Der praktische Nutzen dieser Aufgabenstellung ist aus meiner Sicht sehr begrenzt. Es sei denn, es sollen die Fähigkeiten des Chrome-Browsers als solchen überprüft werden. Die Umsetzung ist aufgrund der eingeschränkten Rahmenbedingungen nicht sonderlich robust. Allenfalls lassen sich damit die Grenzen des mit Katalon Studio prinzipiell Machbaren demonstrieren. Folgende Einschränkungen sind zu beachten:
- Die Lösung funktioniert nicht mit Chrome headless
- Es darf kein Chrome-Benutzerprofil mit einer vom derzeitigen Standard abweichenden Liste der Such-Anbieter geladen werden
- Die Wahrscheinlichkeit, dass die Lösung nach einem Chrome-Update nicht mehr funktioniert, ist sehr hoch
- Während der Testausführung ...
  - darf das Browser-Fenster nicht den Fokus verlieren
  - darf die Tastaur nicht benutzt werden

#### Einzelheiten zum Test-Workflow

- Dieser Test ließ sich nicht allein mit Bordmitteln der WebUI-Befehlsbibliothek von Katalon Studio realisieren, sondern erforderte den zusätzlichen Einsatz von Robot Framework. Und zwar aus zwei Gründen:
 1. Die Methode WebUI.openBrowser erlaubt es nicht, das reale Verhalten von Chrome beim manuellen Öffnen eines neuen Browser-Fensters ohne Angabe einer URL nachzustellen. Anstatt die Eingabemaske des voreingestellten Suchanbieters wird nur eine leere weiße Seite angezeigt. Auch Selenium selbst erlaubt Interaktionen mit Browser-Fenstern nur im Kontext mit einer jeweils aufzurufenden Ressource. Der Zugriff auf Fenster-Funktionen, die durch den Browser selbst angeboten werden, bleibt hingegen verwehrt, weil sie außerhalb dieses Scopes liegen. Auf andere Weise lässt sich der voreingestellte Suchanbieter aber nicht ermitteln (siehe unten, 2.). Daher muss mit Hilfe von Robot Framework das Senden der Tastenkombination Strg+T an den Browser simuliert werden, um einen neuen Browser-Tab zu öffnen, in dem die gewünschte Eingabemaske erscheint. Ob es sich dabei um diejenige von Google oder von Bing handelt, kann anschließend wiederum mit Selenium- bzw. WebUI-Techniken ermittelt werden.
 2. Die Web-Elemente auf den Einstellungsseiten des Chrome-Browsers lassen nicht mit CSS- oer XPath-Selektoren adressieren. Aus diesem Grund kann mit WebUI- bzw. Selenium-Techniken das entsprechende Select-Feld nicht angesteuert werden, um den eingestellten Suchanbieter zu ermitteln oder gar zu ändern. Daher wird Robot Framework eingesetzt, um die entsprechend nötigen Tastenanschläge (21 x Tab-Taste und anschließend Cursor-Taste hoch oder runter) an den Browser zu senden.
- Während der Testdurchführung werden Screenshots und Videos erstellt. Zusätzlich werden für die Aufgabenstellung relevante Feststellungen durch begleitende Log-Ausgaben dokumentiert.

#### Mehrfache Testausführung

- Wird realisiert, indem die Test-Case-Variable *quantity* im Test-Case *change_chrome_search_provider* beliebig angepasst werden kann.
- Nach jeweils zwei Ausführungen wurde der im Chrome-Browser eingestellte Suchanbieter von Google auf Bing und wieder zurück auf Google gesetzt. 

#### Erfolgreich getestet in folgenden Umgebungen

- *Windows 10* mit *Chrome v81.0.4044.138 (headful)*

## Postman

Aufgabe 2 wurde mit **Postman v7.24.0** umgesetzt und das exportierte Projekt im **Collection-Format v2.1** unter **postman_reqres.in_rest_api/reqres.in.postman_collection.json** abgelegt.
