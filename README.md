# RD_QA_Probeaufgabe

## Katalon Studio

Aufgaben 1 und 3 wurden mit *Katalon-Studio v6.3.3.11* im Projekt ***katalon_studio_relaxdays_shopping_cart_and_chrome_settings*** in folgenden Test-Suites umgesetzt:

### Aufgabe 1: 1_test_shopping_flow

#### Einzelheiten zum Test-Workflow

- Die in den Warenkorb zu legenden Produkte wurden mit ihren vorgegebenen Suchbegriffen und einer eventuellen Produktoption in der Excel-Tabelle products.xlsx hinterlegt und zur Testlaufzeit eingelesen, wodurch sich die Testdaten flexibel anpassen und erweitern lassen. Dabei wird unterstellt, dass die Suchbegriffe hinreichend scharf formuliert sind, dass ein eindeutiger Treffer (inkl. evtl. zusätzlich angegebener Produktoption) bereits auf der ersten Suchergebnisseite gefunden werden muss.
- Der hierfür notwendige Textabgleich wird anhand der einzelnen Suchbegriffe vorgenommen, die unabhängig von ihrer Reihenfolge allesamt in der Produktbeschreibung vorkommen müssen. Dazu werden jeweils beide Seiten des Vergleichs normalisiert (Kleinschreibung, Konvertierung von Umlauten und sonstigen Diakritika sowie Aufspaltung anhand von Nicht-Wort-Zeichen). Der Test toleriert es zwar, wenn ein Produkt nicht auf der ersten Ergebnisseite gefunden wird, geht in diesem Fall aber zum nächsten Produkt aus der Einkaufsliste über. D.h. in diesem Fall wird kein Produkt in den Warenkorb gelegt. Dies war vorliegend bereits beim ersten Produkt der Liste "Pavillon 6-eckig mit Seitenwänden" der Fall, das seit wenigen Tagen nicht mehr lieferbar ist.
- Während der Testdurchführung werden Screenshots und Videos erstellt. Zusätzlich werden für die Aufgabenstellung relevante Feststellungen durch begleitende Log-Ausgaben dokumentiert.

#### Mehrfache Testausführung

- Wird realisiert, indem innerhalb der Test-Suite die Test-Cases *01_reinit_browser*, *0_open_relaxdays_homepage* und *1_put_products_into_shopping_cart* in dieser Reihenfolge beliebig oft vor dem abschließenden Test-Case *99_close_browser* eingefügt werden können. Dabei wird durch *1_put_products_into_shopping_cart* implizit auch der Test-Case *2_check_subtotal* aufgerufen, der die Einzelpreise der zuvor in den Warenkorb gelegten Produkte als Eingabedaten übergeben erhält, um deren Summe mit dem Gesamtwert des Warenkorbs zu vergleichen.

#### Erfolgreich getestet in folgenden Umgebungen

- **Windows 10**
- **Chrome v81.0.4044.138 (headful und headless)** sowie **Microsoft Edge v44.18362.449.0**

### Aufgabe 3: 2_change_chrome_search_provider

#### Allgemeines

Der praktische Nutzen dieser Aufgabenstellung ist aus meiner Sicht sehr begrenzt. Es sei denn, es sollen die Fähigkeiten des Chrome-Browsers als solchen überprüft werden. Die Umsetzung ist aufgrund der eingeschränkten Rahmenbedingungen nicht sonderlich robust. Allenfalls lassen sich damit die Grenzen des mit Katalon Studio prinzipiell Machbaren demonstrieren. Folgende Einschränkungen sind zu beachten:
- Die Lösung funktioniert nicht mit Chrome headless
- Es darf kein Chrome-Benutzerprofil mit einer vom derzeitigen Standard abweichenden Liste der Such-Anbieter geladen werden
- Die Wahrscheinlichkeit, dass die Lösung nach einem Chrome-Update nicht mehr funktioniert, ist sehr hoch
- Während der Testausführung ...
  - darf das Browser-Fenster nicht den Fokus verlieren
  - darf die Tastaur nicht benutzt werden

#### Mehrfache Testausführung

- Wird realisiert, indem die Test-Case-Variable *quantity* im Test-Case *change_chrome_search_provider* beliebig angepasst werden kann

#### Erfolgreich getestet in folgenden Umgebungen

- **Windows 10**
- **Chrome v81.0.4044.138 (headful)**

## Postman

Aufgabe 2 wurde mit **Postman v7.24.0** umgesetzt und das exportierte Projekt im **Collection-Format v2.1** unter **postman_reqres.in_rest_api/reqres.in.postman_collection.json** abgelegt.
