
# INFOM124 Vérification et validation logicielle : Examen (janvier 2024)

- Nom : *Gaillard*
- Prénom : *Matthys*

## Consignes générales

- La durée maximale de l’examen est de **4 heures**.
- L'examen est à cours ouvert.
- L'examen est individuel, la communication avec autrui est interdite. En particulier l'utilisation de messagerie instantanée, de mails ou de tout autre système de partage d'information avec d'autres étudiant.e.s passant l'examen seront considérés comme une tricherie.
- Les réponses (aussi appelés rapports) doivent être indiquées dans les espaces prévus à cet effet dans le présent document, ainsi qu'éventuellement dans le code source remis au début de l'examen.

## Méthode de cotation

Le but de l'examen est de démontrer votre capacité à analyser la qualité globale d'une codebase aux moyens des outils et techniques vues en cours.

Pour vous aider à adopter une approche structurée, 9 "pistes guidées" vous sont fournies plus loin dans ce document. Ces pistes, comme leur nom l'indique, ont pour but de vous orienter vers des zones de la codebase dans lesquels des problèmes sont assurément présents. Elles sont également divisées en sous-questions vous ayant pour bu de vous aider à structurer votre manière de rapporter les soucis de qualité en question.

**Il n'est pas nécessaire de répondre à toutes les sous-questions afin d'engranger des points pour cette question.** Au plus vous allez loin, au plus la piste vous rapportera de points. Très concrètement, le rapport que vous faites sous chaque piste guidée sera évalué comme suit :

* *Insuffisant* : le rapport est vide ou totalement erroné (0 points).
* *Insuffisfaisant* : le rapport est parcellaire ou "à côté de la plaque", mais comporte des éléments factuels **et** originaux(*) (0.25 point).
* *Satisfaisant* : le diagnostic et la correction proposés dans le rapport sont corrects et étayés (0.5 point).
* *Bon* : le diagnostic, la correction et l'action préventive proposés dans le rapport sont corrects et étayés (0.75 point).
* *Très bon* : le diagnostic, la correction et l'action préventive proposés dans le rapport sont corrects et étayés et la correction a été appliquées dans le code source (1 point).

(*) Par "originaux", on entend un élément ayant fait l'objet d'une réflexion et/ou interprétation par l'étudiant ; recopier textuellement un morceau du rapport de SonarQube, Checkstyle ou autre ne vous accordera pas de points.

Les 9 pistes guidées sont réparties en 3 catégories liées au type de problème abordé (code smells, defects, bad development practices). Le seuil minimum de réussite (10/20) est atteint si vous répondez de manière satisfaisante à 5 questions, **avec au moins 1 question par catégorie**.

Votre score final sera alors :

* 4/20 (si vous avez répondu de manière satisfaisante à une seule catégorie).
* 7/20 (si vous avez répondu de manière satisfaisante à deux catégories)
* 10 + X(**) (si vous avez répondu de manière satisfaisante à trois catégories)

(**) X étant une "marge de réussite" comprise entre 0 et 10 et calculée sur base de la qualité des réponses fournies aux différentes pistes.

Veuillez noter qu'une 10e piste (*Analyse complémentaire*) est ouverte et vous demande de faire une analyse globale de la qualité du projet sur base des différents rapports d'analyse qui vous sont fournis. Celle-ci compte dans la marge de réussite ci-dessus.

## Projet à analyser

Le projet à tester est un jeu de Pacman où le joueur contrôle un personnage appelé Pac-Man, dont l'objectif est de manger toutes les pac-gommes présentes dans un labyrinthe tout en évitant d'être touché par les fantômes. Le jeu se déroule dans un labyrinthe composé de passages et de murs. Il y a également des pac-gommes dispersées dans le labyrinthe, ainsi que des bonus comme les fruits qui apparaissent à intervalles réguliers.

Pac-Man peut se déplacer vers le haut, le bas, la gauche et la droite à travers les passages du labyrinthe. Le but principal de Pac-Man est de manger toutes les pac-gommes présentes dans le labyrinthe. Chaque fois qu'il mange une pac-gomme, le score du joueur augmente.

Il y a plusieurs fantômes dans le jeu, chacun ayant son propre comportement. Les fantômes poursuivent Pac-Man dans le labyrinthe et tentent de le capturer. Si Pac-Man est touché par un fantôme, il perd une vie. En plus des pac-gommes normales, il y a des super pac-gommes spéciales. Lorsque Pac-Man mange une super pac-gomme, les fantômes deviennent vulnérables pendant un court laps de temps. Pendant cette période, Pac-Man peut les manger pour obtenir des points supplémentaires. Le jeu se termine lorsque toutes les pac-gommes ont été mangées ou lorsque le joueur perd toutes ses vies.

### Structure du projet

Le projet utilise Maven. Pour rappel, la structure d'un projet Maven est la suivante :
- `src/main/java` contient le code source de l'application.
- `src/test/java` contient les tests de l'application.
- `target/` contient les résultats du *build* (code compilé, résultats des tests, rapports d'analyse, etc.).

La classe `nl.tudelft.jpacman.Launcher` est la classe principale qui gère le lancement d'une partie.

### Configuration du build

La commande suivante permet de lancer le *build* du projet, à savoir la compilation et l'exécution des tests :
```
mvn clean package
``` 
Si Maven n'est pas installé sur votre machine, vous pouvez également exécuter depuis la racine du projet (sur Linux et MacOS) :
```
./mvnw clean package
``` 
Ou sur Windows :
```
mvnw.cmd clean package
``` 

La configuration du build inclus déjà toute une série d'outils d'analyse :
- Checkstyle, un outil d'analyse statique permettant de vérifier que les conventions d'écriture de code sont respectées. L'outil offre une configuration par défaut qui peut être adaptée et customisée selon les besoins.
- PMD, un outil d'analyse statique permettant de repérer les erreurs de programmation courantes.
- SpotBugs, un outil d'analyse statique permettant de repérer des bugs.
- JaCoCo, un outil d'analyse dynamique fournissant des indications sur la couverture structurelle des tests.
- PIT, un outil d'analyse dynamique permettant d'effectuer une analyse de mutation.

**Note :** L'archive .zip qui vous a été remise contient déjà une copie des différents rapports pour la version du projet dans le dossier `rapports/`. **Ces rapports sont suffisants pour répondre à la plus grande partie des pistes ci-après.** Si vous voulez aller plus loin, il est également possible de re-générer ces rapports via la commande suivante (ou `./mvnw` ou `mvnw.cmd`) :
```
./mvnw clean test org.pitest:pitest-maven:mutationCoverage site
``` 
Une fois la commande exécutée, les rapports générés sont disponibles dans `target/site/index.html` (sous le menu à gauche *Project Reports*).

****************************************************************************************

## Pistes guidées - Mauvaises pratiques de développement

### Classe ScorePanel

La classe `nl.tudelft.jpacman.ui.ScorePanel` présente un anti-pattern ou ne respecte pas une convention de codage. Essayez de résoudre le problème.

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour identifier le problème spécifique à cette classe de manière systématique ?*
Pour identifier les problèmes posés, j'ai utilisé 2 techniques. J'ai fait ici une analyse statique du code en utilisant un plugin de mon IDE, IntelliJ (SonarLint). Cet outil permet de regarder différents paramètres comme 
le respect des conventions de codage ou encore la complexité du code. J'ai également utilisé un outil d'analyse dynamique, JaCoCo, qui permet de voir la couverture de code des tests. 
La deuxième technique utilisée est une technique de révision du code, la pair review. C'est une technique qui demande formellement à une personne tierce d'examiner notre code. J'ai en effet pris du recul sur le code pour à partir de mon expérience de développeur, analyser le code et trouver les éventuels problèmes dans le code.




#### Diagnostic
*Indiquez ci-dessous le problème identifié et les impacts qu'il pourrait avoir.*
Dans cette partie, je distingue 2 types de mauvaises pratiques de programmation. On retrouve notamment : 
- **l'anti-pattern** lava flow : On le retrouve notamment à cause du code mis en commentaire dans les fonctions. On retrouve cela dans le constructeur de la classe, ou
encore dans la méthode refresh.
- **une mauvaise application du principe Solid** : En effet, une classe devrait n'avoir qu'une seule responsabilité. Or, on retrouve dedans une interface pour le String Formatter. De base,
elle ne gêne pas, car elle ne sert qu'à formatter un string pour le joueur et elle n'est utilisée dans d'autres classes et reste spécifique à la classe ScorePanel.
#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce problème en particulier ne se représente plus.*

- Pour ***l'anti-pattern***, la suppression du code en commentaire serait la meilleure solution. En effet, le code mis en commentaire
reflète l'utilisation d'une version antérieure de Java. Le code qui n'est pas en commentaire est simplement du code mis à jour.

- Pour la mauvaise application du principe Solid, il serait intéressant de séparer l'interface StringFormatter de la classe ScorePanel et de la placer dans un fichier à part. En effet, une interface ou comme toute autre classe en Java repose sur l'application des principes Solid
dans laquelle une classe ne doit avoir qu'une seule responsabilité. Cependant, cette modification peut être sujette à débat parce que seule la classe ScorePanel utilise le StringFormatter comme étant une constante symbolique pour tous les joueurs.
de la classe.



#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*
Pour éviter l'apparition de cet anti-pattern, je pense que la mise en place systématique d'une revue par les pairs suffirait à éviter l'apparition de cet anti-pattern qui ne repose ici, que sur l'oubli de l'effacement des commentaires.
L'avantage est qu'on a alors une deuxième personne avec un regard externe à qui on demande formellement de regarder à notre code et de le commenter.
Il ne reste alors plus qu'à vérifier et à corriger notre code en fonction.

### Classe Navigation

La classe `nl.tudelft.jpacman.npc.ghost.Navigation` présente un anti-pattern ou ne respecte pas une convention de codage. Essayez de résoudre le problème.

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour identifier le problème spécifique à cette classe de manière systématique ?*

Pour diagnostiquer le problème, j'ai utilisé un outil d'analyse statique de code, SonarLint, plus mon IDE. SonarLint m'a permis de dégager une métrique pour le code
de la méthode shortestPath. Cette métrique est la complexité cyclomatique. Cette métrique permet de voir la complexité du code. Plus la valeur est élevée, plus le code est complexe.µ
J'ai également pu mesurer la longueur de la fonction, grâce à mon IDE qui indique le numéro d'une ligne de code sur le côté.. En effet, plus une fonction est longue, plus elle est complexe à comprendre et à maintenir.


#### Diagnostic
*Indiquez ci-dessous le problème identifié et les impacts qu'il pourrait avoir.*

Ici, on retrouve l'anti-pattern *code spaghetti*. Cet anti-pattern est caractérisé par un code long et complexe à comprendre dans un contexte de programmation.
La méthode ici fait plus de 27 lignes et est imbriqué de conditions et de boucle rajoutant de la complexité au code.


#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce problème en particulier ne se représente plus.*
J'ai ici plusieurs suggestions pour améliorer le code. 
- Dans un premier temps, on pourrait commencer par utiliser des noms explicites pour les variables.
En effet, a, b ne sont pas des noms évoquant des carrés. Si la méthode était courte, on aurait pu se contenter, mais au fur et à mesure de la lecture du code, on se perd avec la signification.
- La deuxième serait de diviser le code de la fonction en plusieurs sous-méthodes avec des noms représentants ce qu'elles font. Cela aiderait grandement à leur compréhension.
- La troisème serait de placer qu'un seul return dans le code de la fonction. En effet, quand on regarde le code, seul f est possiblement renvoyé avec une valeur et à la fin, on voit qu'on retourne null.
On pourrait alors faire en sorte que d'effacer le return f et de le remplacer par une affectation et à la fin, à la place du return null, on retourne une variable peu importe si elle est nulle ou pas.
 

#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*

On pourrait instaurer une pratique de pair programming. De cette manière, on aurait un feedback continu sur le code qu'on utiliserait. De cette manière, notre compagnon 
pourrait nous aider dès que le code devient incompréhensible. Dès lors, l'équipe devrait être constituée d'un développeur senior et d'un développeur junior. De cette manière, le développeur senior pourrait aider le développeur junior à utiliser les bonnes pratiques de développement et à les appliquer dans le code.
### Classe CollisionMap

La classe `nl.tudelft.jpacman.level.CollisionMap` présente un anti-pattern ou ne respecte pas une convention de codage. Essayez de résoudre le problème.

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour identifier le problème spécifique à cette classe de manière systématique ?*
Ici, j'ai juste procèder une review informelle du code.


#### Diagnostic
*Indiquez ci-dessous le problème identifié et les impacts qu'il pourrait avoir.*
Les commentaires ne sont pas dans la même langue que dans les autres fichiers. Cela peut être déroutant pour un développeur qui ne parle pas la langue du commentaire de comprendre
à quoi sert l'interface.


#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce problème en particulier ne se représente plus.*
Dès lors, il serait utile de mettre les commentaires dans la même langue que le reste du code. 


#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*
Si on n'arrive pas écrire les commentaires dans la même langue, on peut alors utiliser un outil de traduction ou demander à un connaisseur de la langue de le traduire pour nous.

****************************************************************************************

## Pistes guidées - Code smells

### Packages ghost, level et sprite

Votre collègue Jean-Michel Àpeuprès vous fait remarquer que lorsqu'il implémente une fonctionnalité, il doit souvent modifier les packages `nl.tudelft.jpacman.npc.ghost`, `nl.tudelft.jpacman.level` et `nl.tudelft.jpacman.sprite` en même temps. Vous n'avez pas tout compris de ce qu'il marmonnait dans sa barbe, mais il semblerait qu'il y ait quelque chose à propos de couleurs. Quel est selon vous le problème ?

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour trouver ce(s) code smell(s) de manière systématique ?*



#### Diagnostic
*Indiquez ci-dessous le(s) code smell(s) identifié(s).*



#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce(s) code smell(s) en particulier ne se représente plus.*



#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*


### Classe Level

La classe `Level` du package `nl.tudelft.jpacman.level` contient un certain nombre de méthodes qu'il vous faut reviewer avant d'effectuer un pull request sur le repo du projet. Le code s'exécute correctement, mais Jean-Michel Àpeuprès, qui a implémenté cette classe, vous dit que lorsqu'il veut modifier une méthode, il doit parfois en modifier une autre. Quelles sont ces méthodes ? Quel est le problème et comment pouvez-vous le résoudre ? 
Il s'agit de la méthode move et de la méthode start

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour trouver ce(s) code smell(s) de manière systématique ?*
Ici, on pourrait utiliser des outils d'analyse statique comme sonarLint qui permet de relever des morceaux de codes semblables



#### Diagnostic
*Indiquez ci-dessous le(s) code smell(s) identifié(s).*

Je retrouve ici 2 types de code smells : 
- duplicate code : Il s'agit de codes identique ou en grande majorité à plusieurs endroits. Le changement d'un des morceaux implique le changement aux autres endroits. Le souci de la modification est que l'on peut oublier de modifier une partie et donc, provoquer des erreurs.
- shotGunSergery : il fait partie des codes smells de change preventers où quand on fait un changement on doit le faire à plusieurs endroits dans le code. 



#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce(s) code smell(s) en particulier ne se représente plus.*
On peut alors extraire le code semblable et le déplacer dans une méthode spécifique. De cette manière, on ne modifie le code qu'à un point spécifique du code.



#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*

On pourrait ici utiliser une technique d'inspection de manière à ce que plusieurs personnes puissent détecter les codes simailaires ave comme sujet le code répectée. On pourrait mettre en place l'inspection de Fagan qui se décompose en 6 étapes :
1. planification : On planifie ce qu'on va regarder. Des inspecteurs se chargeront de regarder le code.
2. L'overview (prévisualisation) : Rencontre entre auteurs et inspecteurs. Les auteurs donnent leur
impression. On va assigner à chacun ses tâches.
3. Préparation ou inspection individuelle : On analyse chacun le code en fonction de sa tâche. On
regarde aussi bien le code que les documents annexes.
4. Inspection : On rassemble les informations des différentes inspections. On repère les principaux
défauts.
5. Rework : Les auteurs répondent et corrigent les défauts.
6. Suivi : Un modérateur vérifiee les solutions et planifie éventuellement une future inspection.
Cette méthode a l'avantage d'impliquer plusieurs personnes dans la review.

### Classe MapParser

Jean-Michel Àpeuprès a implémenté la classe `MapParser`, du package `nl.tudelft.jpacman.level`. Plusieurs développeurs ont regardé son code et pointent un problème dans cette classe, et plus particulièrement dans la méthode `MakeGrid(char[][] map)`. Quel est ce problème ?
Elle possède trop de paramètres en entrée et c'est également une méthode assez longue.

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour trouver ce(s) code smell(s) de manière systématique ?*
Pour le premier code smell, on pourrait tout simplement faire un deskcheck où demande informellement
à un voisin de vérifier notre code.
Pour le second, on pourrait se reposer sur l'utilisation d'une métrique comme la longueur de ligne 
de code pour indiquer si la méthode est suffisamment longue. Cette métrique serait alors à combiner
avec la complexité cyclomatique pour obtenur une analyse plus probante.



#### Diagnostic
*Indiquez ci-dessous le(s) code smell(s) identifié(s).*
On distingue 2 types de codes smells
1. Long method : Ce code smell appartient à la famille des bloaters. Elle est caractérisée par du code extrêmement long ce qui le rend complexe à utiliser.
2. Long parameter List : Ce code smell appartient à la même famille que LongMethod. Il a tellement de paramètres en entrée qu'il devient difficile de les gérer correctement ajoutant du code supplémentaire pour leur traitement.



#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce(s) code smell(s) en particulier ne se représente plus.*

Pour la long method, je garderai le switch tel quel, mais je remplacerai l'intérieur des conditions. En effet, la méthode
est devenue longue et donc, on peut alors extraire chacun des bouts de code à l'intérieur et les placer à l'intérieur 
de méthode avec un nom explicite. Cela permettra de ne pas perdre en comprhénsion tout en la renforçant.

En ce qui concerne le code smell long parameter list, on pourrait effacer certains paramètres en entrée et créer les objets les correspondant à l'intérieur de la méthode. Cela permettrait d'alléger la compréhension de la méthode. On peut alors citer les paramètres grid, ghost et startPositions. De plus, vu grid est en fait un objet qui est utilisé par la classe appelante, mais qui est toujours vide lors de l'appel de la méthode, il suffirait alors de changer le type de paramètre renvoyer et d'envoyer grid mis à jour.


// regarder les différentes méthodes

#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*

Ici, je dirais qu'on pourrait automatiser le processus de détection en généralisant l'emploi des outils d'analyse statiques par tous les programmeurs impliqués. On peut aussi mettre en place une inspection de Fagan visant à respecter les conventions de l'orienté objet ou alors, on peut demander au développeur concerné de suivre une formation sur les codes smells afin qu'il puisse adopter de bonnes programmation


****************************************************************************************

## Pistes guidées - Defects

### Méthode BoardFactory.createBoard

Depuis la dernière mise à jour, un nombre croissant de rapports de bugs semblent pointer la méthode `nl.tudelft.jpacman.board.BoardFactory.createBoard`, dont la suite de tests dans la classe `BoardFactoryTest` ne semble pas très fournie. Essayez de résoudre le problème. 

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour trouver ce defect de manière systématique ?*

J'ai ici renforcer les tests. En effet, j'ai constaté qu'un grid est en fait, un tableau en 2 dimensions (longeur, largeur).
Donc, j'ai testé avec de la specification bases testing dans laquelle on teste les spécification. On essaie alors de tester les différents types de valeurs possibles pour un tableau.
J'ai ici testé la longeur de ceux-ci :
1. Pour un tableau : 
    - null
    - empty
    - longeur = 1
    - longeur > 1
2. Pour les 2 tableaux ensembles, je compare alors leur longeur :
 - au moins des 2 tableaux est null.
 - au moins des 2 tableaux est vide.
 - les 2 tableaux sont de longeur 1.
 - Les 2 tableaux sont de longeurs différentes, mais tous les 2 sont de longeur supérieurs à 1.

Sur base de mon expérience, je m'étais dit que la grille du jeux ne pouvait pas de hauteur ou de longeur vide ou null, c'est-à-dire qu'aucune des 2 dimensions ne peut être de longeur. Donc, j'ai directement supoposé que les dimensions devaient être de longeur supérieure à 0.



#### Diagnostic
*Indiquez ci-dessous le defect identifié, ainsi que le ou les tests permettant de le confirmer. Donnez au minimum : les valeurs d'entrées à utiliser pour le ou les tests, le résultat normalement attendu et la manière dont le defect se manifeste (la failure observable). Alternativement, modifiez le code du projet pour ajouter un ou plusieurs tests JUnit et indiquez-le ci-dessous, nous exécuterons alors les tests directement.*

Le deffect est ici une erreur d'indexation du tableau. On peut alors créer des test créer plusieurs carrés pour voir si la longeur des des dimensions correspondant à celles données en paramètre pour l'initialisation.

Pour le test, j'ai mis ici 2 aux valeurs pour chaque dimensions du square.

@Test
    void testCreateBoard() {
        Square[][] grid = new Square[2][2];
        grid[0][0] = factory.createGround();
        grid[0][1] = factory.createGround();
        grid[1][0] = factory.createGround();
        grid[1][1] = factory.createGround();

        Board board = factory.createBoard(grid);

        assertEquals(2, board.getWidth());
        assertEquals(2, board.getHeight());


    }




#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce(s) defect(s) en particulier ne se représente plus.*

Dans le code de la méthode `nl.tudelft.jpacman.board.BoardFactory.createBoard`, on remplace le premier diry par dirX dans la ligne suivante :  Square neighbour = grid[dirY][dirY];.



#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*

Ici, je pense qu'une bonne pratique serait d'élargir le nombre de tests pour correctement identifier d'éventuel bugs. Comme dit plus haut, on pourrait mettre en place la méthode de test s'appelant specification-based testing dans laquelle
on se base sur les valeurs limites.


### Méthode Level.registerPlayer

Jean-Michel Àpeuprès a récemment implémenté la méthode `nl.tudelft.jpacman.level.Level.registerPlayer` (et les tests correspondants dans la classe `LevelTest`) mais celle-ci semble ne pas fonctionner dans tous les cas. Essayez de résoudre le problème.

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour trouver ce defect de manière systématique ?*



#### Diagnostic
*Indiquez ci-dessous le defect identifié, ainsi que le ou les tests permettant de le confirmer. Donnez au minimum : les valeurs d'entrées à utiliser pour le ou les tests, le résultat normalement attendu et la manière dont le defect se manifeste (la failure observable). Alternativement, modifiez le code du projet pour ajouter le test JUnit et indiquez-le ci-dessous, nous exécuterons alors le test directement.*



#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce(s) defect(s) en particulier ne se représente plus.*



#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*


### Méthode Unit.occupy

Depuis sa dernière mise-à-jour la méthode `nl.tudelft.jpacman.board.Unit.occupy` pose quelques soucis. Pourtant, les tests correspondants de la classe `OccupantTest` passent tous. Essayez de résoudre le problème.

#### Technique(s) utilisée(s) pour poser le diagnostic
*Comment pourriez-vous vous y prendre pour trouver ce defect de manière systématique ?*



#### Diagnostic
*Indiquez ci-dessous le defect identifié, ainsi que le ou les tests permettant de le confirmer. Donnez au minimum : les valeurs d'entrées à utiliser pour le ou les tests, le résultat normalement attendu et la manière dont le defect se manifeste (la failure observable). Alternativement, modifiez le code du projet pour ajouter le test JUnit et indiquez-le ci-dessous, nous exécuterons alors le test directement.*

Elle ne vérifie pas si target est accessible. 

@Test
    void testOccupyInaccessibleSquare() {
        // Create a mock unit and a mock square
        Unit unit = mock(Unit.class);
        Square square = mock(Square.class);

        // Make the square inaccessible to the unit
        when(square.isAccessibleTo(unit)).thenReturn(false);

        // Try to occupy the square
        unit.occupy(square);

        // Verify that the unit did not occupy the square
        verify(square, never()).put(unit);
    }

#### Action(s) corrective(s)
*Indiquez ci-dessous la ou les actions(s) correctives(s) pour corriger le problème et s'assurer que ce(s) defect(s) en particulier ne se représente plus.*



#### Pratique(s) de développement à modifier
*Indiquez ci-dessous la ou les pratiques de développement à mettre en place pour empêcher qu'un problème similaire ne survienne à nouveau à l'avenir dans cette classe ou dans une autre classe.*


****************************************************************************************

## Analyse complémentaire

*Cette dernière question est ouverte et vous demande de faire une analyse globale de la qualité du projet sur base des différents rapports d'analyse qui vous sont fournis. Vous pouvez également indiquer des recommandations à mettre en place (pratiques de développement à modifier) afin d'améliorer et de maintenir le niveau de qualité du projet.*
