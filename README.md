

# Tema POO  - GwentStone

Made by Andrei Mărunțiș

<div align="center"><img src="https://media.tenor.com/u5dK9QpZc2QAAAAC/ciri-witcher.gif" width="500px"></div>

> Ciri din gif-ul pus de voi = eu inainte sa rezolv tema
> 
> Ciri din gif-ul asta = eu dupa ce am rezolvat tema


## Design-ul claselor

La baza implementarii se afla clasa abstracta `Card`, care contine cele mai basic campuri (`mana`, `description`, `colors`, `name`) si metoda abstracta `toJSON()` care va fi implementata de clasele mostenite si returneaza continutul cartii in format JSON.

Toate celelate carti mostenesc aceasta clasa de baza:

- clasa `Minion` (din care se instantiaza direct minionii simpli `Sentinel`, `Berserker`, `Goliath` si `Warden`)
  - adauga fata de clasa card campurile `health` si `attackDamage` 
  - contine si alte campuri necesare functionalitatilor jocului, cu nume sugestive
  - contine metoda `attack(...)` supraincarcata asa incat sa poata fi folosita atat pentru atacat minioni, cat si pentru eroi
  - implementeaza metoda `toJSON()`
  - toti minionii implementeaza si metoda `clone()` necesara pentru "deep copy"-ul pachetelor mai tarziu
- clasele din subpachetul `casterMinions` (`The Ripper`, `Miraj`, `The Cursed One`, `Disciple`) mostenesc `Minion` si extind interfata `Caster` care contine metoda `ability()`; ulterior, fiecare caster minion va implementa propria abilitate
- clasa `Hero` contine in plus campul `health` si metoda abstracta `ability()`; eroii din joc vor mosteni aceasta clasa
- clasa `Environment` contine in plus doar metoda abstracta `ability()` si va fi mostenita de cele 3 carti environment din joc

> Motivul pentru care numai clasa `Minion` contine metoda `clone()` este ca am nevoie de "deep copy" al acestor obiecte; altfel, in timpul jocului, modificari aduse asupra acestor carti (aflate pe tabla) se vor reflecta in pachetele inactive si se vor pastra pana la jocul urmator

In plus, mai am pachetul `game` care contine clasele `Game` si `Player` cu diverse campuri si metode cu denumiri sugestive, pe care nu le voi detalia aici pentru a ramane succint.

Pachetul `helpers` contine clase precum `Constants` care contine constante (**wow!**) si alte clase care se ocupa in mod special de prelucrarea input-urilor. Metodele din aceste clase au denumiri si comentarii sugestive, astfel ca nu voi intra in detalii aici.

## Desfasurarea jocului

1. Pentru initializarea jucatorilor inainte de meciuri folosesc metoda `initializePlayers` din clasa `Game`, care citeste din input pachetele si celelalte detalii ale player-ilor.
2. Pentru initializarea jocului folosesc metoda `initializeGame` din clasa `Game` care imi citeste din input detaliile despre player-i si joc, apoi incepe prima runda. FIecare runda se desfasoara astfel:
   1. playerii trag cate o carte si primesc mana
   2. procesez actiunile player-ilor date la input (in metoda `runAction` sin `Game`) si diverse actiuni de debug (in metoda `runActionDebug` din clasa Debug)
   3. dupa fiecare actiune fac verificarile necesare (ex. sterg de pe tabla minionii morti) si afisez rezultat la output, daca e nevoie
   4. daca un erou moare, termin runda, afisez mesajul de eroare si actualizez statisticile
   5. inainte de finalul rundei, dezghet toti minionii player-ului care si-a terminat tura si le resetez atacul (cu metoda `unfreezeMinions` din `Helpers`)
3. La finalul jocului, curat campuri precum pachetele celor doi jucatori si pregatesc urmatorul joc (metoda `cleanupGame` din `Game`).

## Posibile imbunatatiri ale implementarii

Implementarea mea nu respecta in totalitate principiile POO. Cateva dintre greselile pe care le fac si imbunatatirile pe care le pot face sunt urmatoarele:

- folosesc in anumite locuri `instanceof`; pot evita asta daca refactorizez codul si adaug in clasa `Card` si in copiii ei campuri `enum` cu care sa pot verifica tipurile cartilor, fara sa folosesc `instanceof`
- am metoda `ability` atat in interfata `Caster`, cat si in clasele abstracte `Hero` si `Environment`; ar trebui refactorizat codul asa incat clasele abstracte respective sa implementeze interfata `Caster`
- in mod similar, si metoda `toJSON` apare in mai multe clase; as putea face o interfata `JSONable` care contine acea metoda si sa fac clasele care au nevoie de metoda `toJSON` sa o implementeze
- o modificare posibila, dar care ar necesita mult rescris de cod (si cunostiinte din laboratorul 5 pe care nu le aveam cand am inceput sa scriu tema) ar fi sa nu fac 4 clase pentru `casterMinions`, ci sa folosesc numai una si echivalentul pointer-ilor catre functii (functii anonime) pentru a le implementa abilitatea

## Ce am invatat din tema

Lucrul hands-on cu principiile POO m-a ajutat mult sa aprofundez cunostiintele acestea. Am constientizat faptul ca atat limbajul Java, cat si IDE-ul InteliiJ sunt extrem de puternice si imi permit sa construiesc relativ usor o aplicatie extrem de complexa, datorita incapsularii inerente paradigmei de POO.

Am invatat si cum sa folosesc JSON-uri in limbajul Java. Cunosteam deja cum se folosesc aceste obiecte in JavaScript, unde este foarte usor caci clasa de baza Object are deja implementata metoda `toJSON`, fiind un limbaj destintat aplicatiilor web care folosesc JSON-uri pentru comuncarea server-client. In Java insa, lucrul cu JSON-uri este mult mai complicat si am inteles mai bine si cum functioneaza.

Nu in ultimul randuri, am invatat cum sa fac readme misto ca sa prezint tema asta.