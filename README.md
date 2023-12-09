# App Cheese Finder

Eva Pinlong - Frédéric Thomas

## Installation

1. Cloner le dépôt dans android studio
2. Ajouter votre clé API dans le fichier `local.properties` avec le nom `{MAPS_API_KEY}`

## Sommaire

1. Présentation de l'API
2. Présentation de l'application
3. License

## Présentation de l'API

[Dataset fromages français](https://data.opendatasoft.com/api/explore/v2.1/catalog/datasets/fromagescsv-fromagescsv@public/records?limit=20)  
[Typescript API](https://github.com/fredouric/cheese-finder-api)

## Présentation de l'application

Notre application se nomme Cheese Finder. Cette application permet d'afficher la liste des fromages
français dans une liste ainsi que sur une Google Map qui les regroupent par département.  
Une barre de recherche permet de filtrer les fromages par nom ou département.

Cette application propose d'ajouter des fromages en favoris et d'obtenir des détails supplémentaires
en cliquant sur un fromage de la liste.  
Le raffraichissement des données s'effectue avec un swipe vers le bas.  
L'application ne permet pas de rajouter des fromages dans l'API.  
L'application est compatible en mode nuit.

## License

[MIT License](License)
