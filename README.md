# Chess Game

[![Java CI with Maven](https://github.com/ec25p5e/ChessGame/actions/workflows/maven.yml/badge.svg)](https://github.com/ec25p5e/ChessGame/actions/workflows/maven.yml)
[![License](https://img.shields.io/github/license/ec25p5e/ChessGame)](https://github.com/ec25p5e/ChessGame/blob/main/LICENSE)
[![Release](https://img.shields.io/github/v/release/ec25p5e/ChessGame?include_prereleases)](https://github.com/ec25p5e/ChessGame/releases)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)]()
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/ec25p5e/ChessGame/issues)


Semplice gioco degli scacchi implementato a oggetti in Java-FX. Al suo interno si può trovare anche una parte automatizzata che gioca il ruolo delle pedine nere.
Anche se con una grafica molto semplice e poco strutturata è un gioco molto interessante e divertente.

Il progetto è nato nell'anno 2022 come progetto scolastico di fine corso. Dopo la v1.0.0 (consegna scolastica), a marzo 2023 ho ripreso in mano il progetto aggiungendo funzionalità e aggiungendo patch per correggere eventuali bug non rilevati precedentemente.

<br />

## Installazione
1. Estrarre i file dalla ZIP
2. Aprire la cartella all'interno di un IDE
3. Eseguire la build del progetto

<br />

## Interfaccia utente (GUI)


Classica | Personalizzata | Durante il gioco
--- | --- | --- |
![](https://github.com/ec25p5e/ChessGame/blob/master/art/CHESSGAME.png) | ![](https://github.com/ec25p5e/ChessGame/blob/master/art/CUSTOM1.png) | ![](https://github.com/ec25p5e/ChessGame/blob/master/art/CUSTOM2.png)

<br />

## Struttura delle cartelle (package)📦
    
    ChessGame               
    ├── src                 
    │   ├── main            
    |   │   |__ java        
    |   │     ├── core          # Logica della scacchiera
    |   |     |   |── board     # Logica + utiity della scacchiera virtuale
    |   |     |   |── move      # Mosse effettuabili
    |   |     |   |── pieces    # Pedine del gioco
    |   |     |   |   |__ piece # Pedina come entità singola (generica)
    |   |     |   |── player    # Giocatori (bianco/nero)
    |   |     |   |   |__ ai    # AI che controlla l'avversario
    |   |     |   |__ utils     # Utility, costanti comuni alla logica del core
    │   |     ├── gui           # Elementi che compongono la GUI Java-FX
    |   |     |__ util          # Costanti generali


<br />

## Librerie implementate

- [Guava (goole)](https://github.com/google/guava)
- [Lombok](https://projectlombok.org/)

<br />

## Volete contribuire? 🤝
Se vuoi contribuire a questa applicazione, sei sempre il benvenuto! Leggi [linee guida per contribuire](https://github.com/ec25p5e/ChessGame/blob/master/CONTRIBUTING.md). 

<br />

## Licenza 🔖
```
    Apache 2.0 License
    Copyright 2021 Spikey sanju
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
