

# Tema POO  - GwentStone

Made by Andrei Mărunțiș

<div align="center"><img src="https://media.tenor.com/u5dK9QpZc2QAAAAC/ciri-witcher.gif" width="500px"></div>

#### Assignment Link: [https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema](https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema)


## Design-ul claselor

Clasele pe care le-am folosit se impart in mai multe pachete:

- `cards`
  - `Card`
    - clasa abstracta, extinsa de toate cartile
    - contine campurile cele mai basic ale unei carti, respectiv setteri si getteri pentru acestea:
      - `mana`
      - `description`
      - `colors`
      - `name`
    - contine si metoda abstracta `toJSON()` care returneaza datele unei carti in format JSON pentru a scrie la output
  - Deck