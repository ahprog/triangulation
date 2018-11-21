## Triangulation
Le devoir 3 du cours d'algorithmique

Le projet est crée avec Maven 3.3.9 et le Java SDK 11


# Tuto installation IntelliJ:

  + Ouvrir le repo avec IntelliJ via "New Project > From Version Control ..."
  + Importer Maven via le pom.xml
  + Setup le SDK en choisissan le path du SDK 11


# Tuto compilation et run :

  + soit utiliser IntelliJ
  
  + soit on utilise\
  
      + ```mvn clean install``` pour compiler avec les tests\
      + ```mvn clean install -DskipTests``` pour ne pas faire les tests\
      
    puis \
      ```java -jar target/triangulation-1.0.jar```
    
