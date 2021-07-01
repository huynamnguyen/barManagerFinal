## barManager
+ Viet: 
  + Project Manager
  + Front-End Developer
  + (Head) Back-End Developer
  + UI Designer
+ Bach:
  + System Administrator
  + (Head) Front-End Developer
  + UI Designer
+ Hieu:
  + Back-End Developer
  + Front-End Developer
  + UI Designer
  + Salesman (1)
+ Nam:
  + Back-End Developer
  + Front-End Developer
  + UI Designer
+ Long:
  + Finanz & Recht
  + Sekretariat
  + Salesman (2)

## Deploy Back-End:

1. Eclipse installieren
2. MoogoDB Community installieren (https://docs.mongodb.com/manual/administration/install-community/)
3. Backend runterladen
4. Eclipse --> Import (Maven project)
5. Run as java application: src/main/java/bm/barManager/BarManagerApplication.java
6. Server kann unter localhost:8080 aufgerufen werden
7. Schnittstellen stehen in src/main/java/bm/webserver/RESTControllerInfra.java


Helpful tool to test POST functions: Postman

1. Install: https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en
2. Import barManager.postman_collection.json into Postman


## Zukunft:

Thermodrucker für 

+ Raspberry Pi: https://www.amazon.de/Thermo-Drucker-unterst%C3%BCtzt-Raspberry-BeagleBone-imx6-Board/dp/B01GA5GRJ6
+ oder Manager Client (Tablet)

### Für eine Verifizierung benötigen wir: 
+ zertifizierte technische Sicherheitseinrichtung (TSE) 
+ TSE-Einbindung über eine definierte Schnittstelle

## LANGE VERSION (https://www.bsi.bund.de/DE/Themen/DigitaleGesellschaft/Grundaufzeichnungen/FAQ/faq_node.html#faq12383954)

Der Hersteller einer TSE muss nachweisen, dass die TSE die Interoperabilitätsanforderungen der Technischen Richtlinien einhält. Der Nachweis ist durch eine Konformitätszertifizierung nach der Testspezifikation der TR-03153 zu erbringen.

BSI TR-03153 (https://www.bsi.bund.de/DE/Publikationen/TechnischeRichtlinien/tr03153/tr03153_node.html)

Weiterhin muss der Hersteller einer TSE nachweisen, dass die Sicherheitsanforderungen eingehalten werden. Der Nachweis ist durch Sicherheitszertifizierungen nach Common Critieria mit folgenden Schutzprofilen nachzuweisen:

BSI PP-SMAERS (https://www.bsi.bund.de/SharedDocs/Zertifikate_CC/PP/aktuell/PP_0105_0105_V2.html)

BSI PP-CSP (https://www.bsi.bund.de/SharedDocs/Zertifikate_CC/PP/aktuell/PP_0104.html)

in der Konfiguration entsprechend 

Protection Profile-Module CSP Time Stamp Service and Audit (PPM-TS-Au) (https://www.bsi.bund.de/SharedDocs/Downloads/DE/BSI/Zertifizierung/Reporte/ReportePP/pp0107b_pdf.pdf?__blob=publicationFile&v=4)

Der Hersteller kann eine vorläufige Freigabe einer Technischen Sicherheitseinrichtung beantragen, wenn die Konformitätszertifizierung nach der Testspezifikation der TR-03153 und eine Sicherheitszertifizierung nach PP-105 erfolgreich abgeschlossen wurde und die Prüfstelle bestätigt, dass die Evaluierung voraussichtlich erfolgreich abgeschlossen werden kann. Die vorläufige Freigabe ist auf ein Jahr befristet und kann auf Antrag des Herstellers bei Nachweis eines signifikanten Fortschritts der Evaluierung verlängert werden. Nach erfolgreichen Abschluss der Zertifizierung ist die TSE ggf. in Verbindung mit einem Softwareupdate als zertifizierte TSE einsetzbar.
