
# Routes API Pokémon TCG

ATTENTION UTILISER BATCH AVEC LA LISTE DE POKEMONS PUIS TESTER L'API

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
- **Description** : Simuler un combat entre deux dresseurs en mode terminal. (BIEN REGARDER VOTRE TERMINAL AFIN DE CONTINUER LE COMBAT), Il y a aussi une proposition d'échange, une fois la requête postman faite, retourner sur le terminal ! :)
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
        "niveau": 10,
        "pv": 50,
        "type": "ELECTRIQUE",
        "faiblesse": "SOL",
        "attaque1": "Éclair",
        "attaque2": "Tonnerre"
    },
    {
        "nom": "Salamèche",
        "niveau": 12,
        "pv": 55,
        "type": "FEU",
        "faiblesse": "EAU",
        "attaque1": "Flammèche",
        "attaque2": "Lance-Flamme"
    },
    {
        "nom": "Carapuce",
        "niveau": 15,
        "pv": 60,
        "type": "EAU",
        "faiblesse": "PLANTE",
        "attaque1": "Pistolet à O",
        "attaque2": "Hydrocanon"
    },
    {
        "nom": "Bulbizarre",
        "niveau": 13,
        "pv": 58,
        "type": "PLANTE",
        "faiblesse": "FEU",
        "attaque1": "Tranch’Herbe",
        "attaque2": "Lance-Soleil"
    },
    {
        "nom": "Rattata",
        "niveau": 8,
        "pv": 35,
        "type": "NORMAL",
        "faiblesse": "COMBAT",
        "attaque1": "Coup de Boule",
        "attaque2": "Écras’Face"
    },
    {
        "nom": "Dracaufeu",
        "niveau": 20,
        "pv": 80,
        "type": "FEU",
        "faiblesse": "EAU",
        "attaque1": "Draco-Griffe",
        "attaque2": "Draco-Rage"
    },
    {
        "nom": "Roucool",
        "niveau": 18,
        "pv": 40,
        "type": "VOL",
        "faiblesse": "ELECTRIQUE",
        "attaque1": "Lame d’Air",
        "attaque2": "Hurricane"
    },
    {
        "nom": "Alakazam",
        "niveau": 25,
        "pv": 70,
        "type": "PSY",
        "faiblesse": "TENEBRES",
        "attaque1": "Psyko",
        "attaque2": "Ball’Ombre"
    },
    {
        "nom": "Ectoplasma",
        "niveau": 20,
        "pv": 75,
        "type": "SPECTRE",
        "faiblesse": "TENEBRES",
        "attaque1": "Griffe Ombre",
        "attaque2": "Hantise"
    },
    {
        "nom": "Arcanin",
        "niveau": 14,
        "pv": 65,
        "type": "FEU",
        "faiblesse": "EAU",
        "attaque1": "Morsure",
        "attaque2": "Crocs Feu"
    },
    {
        "nom": "Rhinocorne",
        "niveau": 22,
        "pv": 85,
        "type": "SOL",
        "faiblesse": "EAU",
        "attaque1": "Séisme",
        "attaque2": "Tunnel"
    },
    {
        "nom": "Leviator",
        "niveau": 19,
        "pv": 90,
        "type": "EAU",
        "faiblesse": "ELECTRIQUE",
        "attaque1": "Cascade",
        "attaque2": "Surf"
    },
    {
        "nom": "Steelix",
        "niveau": 17,
        "pv": 88,
        "type": "ACIER",
        "faiblesse": "FEU",
        "attaque1": "Griffe Acier",
        "attaque2": "Tacle Lourd"
    },
    {
        "nom": "Tyranocif",
        "niveau": 21,
        "pv": 100,
        "type": "TENEBRES",
        "faiblesse": "COMBAT",
        "attaque1": "Coup Bas",
        "attaque2": "Ténèbres"
    },
    {
        "nom": "Mackogneur",
        "niveau": 20,
        "pv": 85,
        "type": "COMBAT",
        "faiblesse": "PSY",
        "attaque1": "Torgnoles",
        "attaque2": "Ultimapoing"
    },
    {
        "nom": "Golem",
        "niveau": 16,
        "pv": 78,
        "type": "ROCHE",
        "faiblesse": "EAU",
        "attaque1": "Coup d’Boule",
        "attaque2": "Roulade"
    },
    {
        "nom": "Dracolosse",
        "niveau": 23,
        "pv": 95,
        "type": "DRAGON",
        "faiblesse": "GLACE",
        "attaque1": "Aéropique",
        "attaque2": "Hurricane"
    },
    {
        "nom": "Sabelette",
        "niveau": 14,
        "pv": 50,
        "type": "SOL",
        "faiblesse": "EAU",
        "attaque1": "Jet de Sable",
        "attaque2": "Éboulement"
    },
    {
        "nom": "Mystherbe",
        "niveau": 10,
        "pv": 55,
        "type": "PLANTE",
        "faiblesse": "VOL",
        "attaque1": "Poudre Dodo",
        "attaque2": "Méga-Sangsue"
    },
    {
        "nom": "Raichu",
        "niveau": 20,
        "pv": 75,
        "type": "ELECTRIQUE",
        "faiblesse": "SOL",
        "attaque1": "Éclair Fou",
        "attaque2": "Tonnerre"
    },
    {
        "nom": "Florizarre",
        "niveau": 22,
        "pv": 100,
        "type": "PLANTE",
        "faiblesse": "FEU",
        "attaque1": "Lance-Soleil",
        "attaque2": "Feuille Garde"
    },
    {
        "nom": "Mewtwo",
        "niveau": 28,
        "pv": 110,
        "type": "PSY",
        "faiblesse": "TENEBRES",
        "attaque1": "Psyko",
        "attaque2": "Force Cosmique"
    },
    {
        "nom": "Regirock",
        "niveau": 25,
        "pv": 130,
        "type": "ROCHE",
        "faiblesse": "EAU",
        "attaque1": "Coud’Boule",
        "attaque2": "Lance-Roc"
    }
]
```
  ```
- **Réponse** : Liste des pokémons créés.

### `DELETE /pokemons/{uuid}`
- **Description** : Supprimer un Pokémon par son UUID.
- **Réponse** : 204 si la suppression est réussie, 404 si Pokémon introuvable.

### `GET /pokemons/{uuid}`
- **Description** : Récupérer un Pokémon par son UUID.
- **Réponse** : Pokémon avec les informations demandées ou 404 si non trouvé.
