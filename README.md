# VaguesdeMonstres

**VaguesdeMonstres** est un plugin Spigot pour Minecraft 1.16.4 permettant d\'organiser des vagues d\'apparition de monstres autour des joueurs. Deux modes de jeu sont disponibles : un mode **Progressif** où la difficulté augmente avec le temps et un mode **Random** où les monstres surgissent de façon aléatoire.

## Fonctionnement

- Gestion d\'une liste de survivants et des monstres associés à chaque joueur.
- Affichage d\'un tableau de bord personnalisé (scoreboard) et d\'une barre d\'action pour suivre le temps et les vagues en cours.
- Possibilité de mettre en pause ou de reprendre les vagues (en mode Progressif).

## Fichiers de configuration

### `config.yml`

| Option | Type | Description |
| ------ | ---- | ----------- |
| `mode` | string | Mode de fonctionnement du plugin : `Random` (par défaut) ou `Progressif`. |
| `Random-Max-Delay` | int | Temps maximum (en secondes) avant l\'apparition d\'une vague aléatoire. |
| `Random-Max-Entity-Count` | int | Nombre maximal de monstres générés lors d\'une vague aléatoire. |
| `Auto-Aggro` | boolean | Si activé, les monstres ciblent automatiquement le joueur ayant provoqué la vague. |

## Commandes

Commande principale : `/vaguesdemonstres` (alias : `/vdm`, `/monsterwaves`, `/mw`). Les sous-commandes nécessitent la permission `vaguesdemonstres.admin`.

| Sous-commande | Description |
| ------------- | ----------- |
| `start` | Démarre les vagues de monstres. |
| `play` / `resume` | Reprend les vagues après une pause (mode Progressif). |
| `pause` | Informe qu\'il n\'est pas possible de mettre en pause (ou se moque du joueur en mode Random). |
| `esuap` | Met en pause les vagues en mode Progressif. |
| `stop` | Arrête complètement les vagues (mode Progressif). |
| `tab` / `scoreboard` | Active ou désactive l\'affichage du scoreboard personnel. |
| `actionbar` | Active ou désactive les informations dans la barre d\'action. |
| `set horde <secondes>` | Définit l\'intervalle entre les hordes de monstres basiques (mode Progressif). |
| `set terrifiant <secondes>` | Définit l\'intervalle d\'apparition des monstres terrifiants (mode Progressif). |

## Compilation / Installation

Le projet utilise **Maven**. Pour compiler le plugin :

```bash
mvn package
```

Le fichier JAR final se trouve dans le dossier `target/` : `VaguesdeMonstres-1.0-SNAPSHOT.jar`.

## Fichiers importants

- `VaguesdeMonstres.java` : classe principale du plugin et gestion des paramètres globaux.
- `CommandVaguesdeMonstres.java` : implémente la commande `/vaguesdemonstres` et ses sous-commandes.
- `Taches.java` : tâches planifiées pour faire apparaître les monstres selon le mode choisi.
- `MonstresEvenement.java` : événements liés aux connexions joueurs (initialisation des vagues, scoreboard, etc.).
- `Titles.java` et `scoreboard/ScoreboardSign.java` : utilitaires pour l\'affichage de titres, d\'action bar et du scoreboard.

## Utilisation rapide

1. Compilez le projet puis placez `VaguesdeMonstres-1.0-SNAPSHOT.jar` dans le dossier `plugins/` de votre serveur Spigot 1.16.4.
2. Démarrez le serveur.
3. Lancez les vagues avec :
   ```
   /vaguesdemonstres start
   ```
4. Gérez l\'affichage personnel via :
   ```
   /vaguesdemonstres tab
   /vaguesdemonstres actionbar
   ```

## Licence et contributions

Aucune licence n\'est fournie dans ce dépôt. Les contributions doivent être discutées avec l\'auteur.

