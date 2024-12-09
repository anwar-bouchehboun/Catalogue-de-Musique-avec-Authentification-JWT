# 🎵 API REST - Gestion du Catalogue Musical 🎵

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7-green?style=flat&logo=spring)
![MongoDB](https://img.shields.io/badge/MongoDB-5.0-brightgreen?style=flat&logo=mongodb)
![JWT](https://img.shields.io/badge/JWT-Auth%20Stateless-blue?style=flat&logo=jsonwebtokens)
![Docker](https://img.shields.io/badge/Docker-Containerization-blue?style=flat&logo=docker)
![JUnit](https://img.shields.io/badge/Testing-JUnit%20%7C%20Mockito-critical?style=flat&logo=junit5)

Une API REST sécurisée pour gérer un catalogue musical, intégrant les meilleures pratiques DevOps et développée avec Spring Boot.

## 📑 **Table des matières**

- [📝 Contexte](#-contexte)
- [⚙️ Fonctionnalités](#️-fonctionnalités)
- [📂 Architecture](#-architecture)
- [🔒 Sécurité](#-sécurité)
- [🚀 Installation et Utilisation](#-installation-et-utilisation)

---

## 📝 **Contexte**

Cette API gère les entités suivantes :

- **Album** : `titre`, `artiste`, `année`.
- **Chanson** : `titre`, `durée`, `trackNumber`.
- **Utilisateur** : `login`, `password`, `active`, `roles`.

Relations principales :

- Un album peut contenir plusieurs chansons.
- Une chanson appartient à un seul album.

## ⚙️ **Fonctionnalités**

### Gestion des Albums

- Lister, rechercher et filtrer les albums avec pagination et tri (USER/ADMIN).
- Ajouter, modifier ou supprimer un album (ADMIN uniquement).

| **Endpoint**             | **Méthode** | **Description**               |
| ------------------------ | ----------- | ----------------------------- |
| `/api/user/albums`       | `GET`       | Lister/rechercher les albums. |
| `/api/admin/albums`      | `POST`      | Ajouter un album.             |
| `/api/admin/albums/{id}` | `PUT`       | Modifier un album existant.   |
| `/api/admin/albums/{id}` | `DELETE`    | Supprimer un album.           |

### Gestion des Chansons

- Lister, rechercher et filtrer les chansons (USER/ADMIN).
- Ajouter, modifier ou supprimer une chanson (ADMIN uniquement).

| **Endpoint**            | **Méthode** | **Description**                 |
| ----------------------- | ----------- | ------------------------------- |
| `/api/user/songs`       | `GET`       | Lister/rechercher les chansons. |
| `/api/admin/songs`      | `POST`      | Ajouter une chanson.            |
| `/api/admin/songs/{id}` | `PUT`       | Modifier une chanson existante. |
| `/api/admin/songs/{id}` | `DELETE`    | Supprimer une chanson.          |

### Gestion des Utilisateurs

- Authentification avec JWT (stateless).
- Gestion des rôles et utilisateurs (ADMIN uniquement).

| **Endpoint**                  | **Méthode** | **Description**                      |
| ----------------------------- | ----------- | ------------------------------------ |
| `/api/auth/login`             | `POST`      | Authentification utilisateur.        |
| `/api/auth/register`          | `POST`      | Création de compte utilisateur.      |
| `/api/admin/users`            | `GET`       | Lister les utilisateurs (ADMIN).     |
| `/api/admin/users/{id}/roles` | `PUT`       | Modifier les rôles d'un utilisateur. |

---

## 📂 **Architecture**

L'API suit une architecture modulaire avec les couches suivantes :

- **Controller** : Gestion des endpoints.
- **Service** : Logique métier.
- **Repository** : Interaction avec la base de données.
- **DTO** : Modèles de transfert de données.
- **Mapper** : Conversion entre entités et DTO.
- **Validation** : Validation des entrées utilisateur.
- **Exception** : Gestion centralisée des exceptions.

## 🔒 **Sécurité**

- **Authentification** : Stateless avec JWT.
- **Rôles** :
  - ADMIN pour `/api/admin/*`
  - USER pour `/api/user/*`
- **Cryptage des mots de passe** : `BCryptPasswordEncoder`.

### Exigences du Token JWT :

- Émetteur identifiable : `.withIssuer()`.
- Identité du client : `.withSubject()`.
- Permissions via : `.withArrayClaim()`.
- Durée de validité : `.withExpiresAt()`.
- Signature sécurisée avec : `.sign()`.

---

## 🚀 **Installation et Utilisation**

### Prérequis

- Java 1.8
- Maven 2.7+
- Docker & Docker Compose
- MongoDB installé ou conteneurisé

### Étapes

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/anwar-bouchehboun/Catalogue-de-Musique-avec-Authentification-JWT.git
   cd musique
   ```
