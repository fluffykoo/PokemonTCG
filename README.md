
# Routes API Pokémon TCG

## DresseurController

### `GET /dresseurs`
- **Description** : Récupérer la liste de tous les dresseurs.
- **Réponse** : Liste de tous les dresseurs avec leurs informations.

### `POST /dresseurs`
- **Description** : Créer un nouveau dresseur.
- **Corps de la requête** :
  ```json
  {
    "nom": "Du Bourg-Palette",
    "prenom": "Sacha"
  }
  ```
- **Réponse** : Dresseur créé avec les informations fournies.

### `GET /dresseurs/{uuid}`
- **Description** : Récupérer un dresseur avec l'ensemble de ses cartes par son UUID.
- **Réponse** : Dresseur avec les informations demandées ou 404 si non trouvé.

### `DELETE /dresseurs/{uuid}`
- **Description** : Supprimer un dresseur par son UUID.
- **Réponse** : 204 si la suppression est réussie, 404 si dresseur introuvable.

### `POST /dresseurs/{uuid}/claim`
- **Description** : Réclamer des cartes pour un dresseur (max 5 cartes).
- **Réponse** : 200 avec dresseur mis à jour ou 400 si le dresseur a déjà 5 cartes.

### `POST /dresseurs/battle`
- **Description** : Simuler un combat entre deux dresseurs en mode terminal.
- **Corps de la requête** :
  ```json
  {
    "dresseur1": "uuid-dresseur1",
    "dresseur2": "uuid-dresseur2"
  }
  ```
- **Réponse** : 200 si le combat est simulé avec succès, 404 si l'un des dresseurs n'est pas trouvé.

### `POST /dresseurs/exchange`
- **Description** : Échanger une carte entre deux dresseurs.
- **Corps de la requête** :
  ```json
  {
    "dresseurFromId": "uuid-dresseur1",
    "dresseurToId": "uuid-dresseur2",
    "carteId": "uuid-carte"
  }
  ```
- **Réponse** : 200 si l'échange a réussi, 400 si l'échange échoue.

---

## PokemonController

### `GET /pokemons`
- **Description** : Récupérer la liste de tous les pokémons ou filtrer par type.
- **Paramètres** : `type` (facultatif) - Filtrer par type de Pokémon.
- **Réponse** : Liste de tous les pokémons ou ceux correspondant au type.

### `POST /pokemons/batch`
- **Description** : Créer plusieurs pokémons à la fois.
- **Corps de la requête** :
  ```json
  [
    {
      "nom": "Pikachu",
      "type": "Electrique",
      "niveau": 5,
      "pv": 50,
      "rarete": 3
    },
    {
      "nom": "Bulbizarre",
      "type": "Plante",
      "niveau": 5,
      "pv": 45,
      "rarete": 2
    }
  ]
  ```
- **Réponse** : Liste des pokémons créés.

### `DELETE /pokemons/{uuid}`
- **Description** : Supprimer un Pokémon par son UUID.
- **Réponse** : 204 si la suppression est réussie, 404 si Pokémon introuvable.

### `GET /pokemons/{uuid}`
- **Description** : Récupérer un Pokémon par son UUID.
- **Réponse** : Pokémon avec les informations demandées ou 404 si non trouvé.
