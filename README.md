# Wieczór

Aplikacja na Androida do wieczornych pytań. Co wieczór losuje kilka pytań ze stu, na przykład:

- O której pierwszy raz wziąłem dziś telefon?
- W co dziś uwierzyłem bez sprawdzania?
- Co dziś zrobiłem, bo „tak się robi”?

Odpowiadasz kilkoma słowami albo pomijasz. Zajmuje to parę minut.

Napisałem ją dla siebie, do prywatnego badania tego, jak feed wpływa na mój dzień. Pytania o telefon i o rzeczy przyjęte bez sprawdzania okazały się najciekawsze, więc są w każdym losowaniu (można to wyłączyć).

## Co umie

- **Wieczór**: domyślnie 4 pytania na dzień, jedno o feedzie i jedno „pod lupą”. Każde da się wymienić (↻) albo pominąć. Przycisk „+ dolosuj pytanie” dodaje kolejne. Ten sam dzień ma zawsze te same pytania.
- **Myśl**: szybki zapis jednej myśli w ciągu dnia (pomysł, coś przeczytanego, coś do dopisania). Na ikonie aplikacji jest skrót „Złap myśl”.
- **Historia**: wszystkie dni z odpowiedziami. Dotknięcie dnia otwiera go do poprawek.
- **Eksport**: Markdown do czytania, CSV do analizy (jeden wiersz to jedna odpowiedź), JSON jako kopia zapasowa. Plik można udostępnić albo zapisać w Plikach.
- **Przypomnienie**: jedno powiadomienie dziennie o wybranej godzinie.

Wpis zrobiony między północą a 4:00 liczy się do poprzedniego wieczoru.

## Prywatność

Aplikacja nie ma uprawnienia do internetu, więc Android nie pozwoli jej niczego wysłać. Nie ma konta, reklam ani analityki. Odpowiedzi leżą w pamięci telefonu i wychodzą z niego tylko wtedy, gdy sam zrobisz eksport.

Skutek uboczny: po odinstalowaniu aplikacji dane znikają. Co jakiś czas zrób eksport „JSON kopia” i trzymaj plik w bezpiecznym miejscu. Import w zakładce Eksport wczyta go z powrotem.

Uprawnienia: powiadomienia (przypomnienie) i start po włączeniu telefonu (żeby przypomnienie przetrwało restart).

## Instalacja

1. Pobierz `Wieczor-v2.0.apk` z zakładki Releases.
2. Otwórz plik na telefonie. Android zapyta o zgodę na instalację z tego źródła.
3. Jeśli pojawi się ostrzeżenie Play Protect, wybierz „Zainstaluj mimo to”. Pojawia się, bo aplikacji nie ma w Sklepie Play.

Wymaga Androida 8.0 lub nowszego.

## Kod

Całe UI i logika są w jednym pliku: [`app/src/main/assets/index.html`](app/src/main/assets/index.html). Pytania leżą na górze skryptu w tablicy `POOL`. Część w Javie (`app/src/main/java/`) zapisuje dane do pliku, otwiera udostępnianie i planuje przypomnienie.

Budowanie (Android SDK i JDK 17+):

```bash
./gradlew assembleRelease
```

Bez własnego klucza w `../wieczor-signing/keystore.properties` APK podpisze się kluczem debug.

Plik `index.html` można też otworzyć w przeglądarce. Dane zapiszą się wtedy w `localStorage`.

## Pytania

Pytania są pisane w rodzaju męskim, bo pisałem je dla siebie. Wersja żeńska i neutralna jest na liście rzeczy do zrobienia.

### Feed (20)

- Jaki temat widziałem dziś najczęściej?
- Co z telefonu pamiętam z dzisiaj?
- Ile czasu spędziłem dziś w feedzie? Strzelam.
- W której aplikacji siedziałem dziś najdłużej?
- Co zrobiłem albo odpuściłem dziś przez zasięgi, feed lub powiadomienia?
- Które powiadomienie dziś mnie oderwało?
- Co mnie dziś wkurzyło w internecie?
- Co mnie dziś rozśmieszyło w internecie?
- O której pierwszy raz wziąłem dziś telefon?
- Czy coś z feedu zepsuło albo poprawiło mi dziś humor? Co?
- Czy feed dał mi dziś coś przydatnego? Co?
- Kiedy dziś odpaliłem feed odruchowo? Gdzie byłem?
- Czy coś dziś wrzuciłem? Czemu akurat to?
- Czy sprawdzałem dziś lajki, wyświetlenia albo zasięgi? Ile razy?
- Czy coś w feedzie straszyło mnie dziś? Czym?
- Co dziś przewijałem dłużej, niż chciałem?
- Kiedy dziś sam odłożyłem telefon?
- Jaki temat z feedu wraca do mnie od kilku dni?
- Co dziś robiłem bez telefonu w ręce?
- Co dziś kliknąłem, choć nie miałem zamiaru?

### Pod lupą (15)

- W co dziś uwierzyłem bez sprawdzania?
- Co dziś komuś powtórzyłem, nie wiedząc, skąd to wiem?
- Co dziś uznałem za oczywiste?
- Z jaką opinią zgodziłem się dziś głównie dlatego, że była wszędzie?
- Kto zarabia na tym, co dziś najczęściej oglądałem?
- Jak opisałby mój dzień ktoś, kto mnie nie lubi?
- Co dziś oceniłem po samym nagłówku albo miniaturce?
- Co dziś zrobiłem, bo „tak się robi”?
- Jaki temat zniknął mi z feedu w ostatnich tygodniach?
- Na co się dziś zgodziłem, choć wolałbym odmówić?
- Co mnie dziś przekonało: fakt, liczba czy emocja?
- Z kim pokłóciłbym się dziś w komentarzach? O co?
- Co chciałem dziś kupić? Skąd ten pomysł?
- Którą swoją opinię sprawdziłbym, gdybym miał na to godzinę?
- Co dziś zrobiłem inaczej niż zwykle?

### Dzień (15)

- Co chciałem dziś zrobić, a nie zrobiłem?
- Co zrobiłem dziś, choć mi się nie chciało?
- Która chwila dzisiaj była najlepsza?
- Co było dziś najtrudniejsze?
- Co dziś skończyłem?
- Co przełożyłem na jutro?
- Gdzie byłem dziś poza domem?
- Co zrobiłem dziś pierwszy raz?
- Co poszło dziś lepiej, niż myślałem?
- Co poszło dziś gorzej, niż myślałem?
- Z czego jestem dziś zadowolony?
- Co zajęło dziś więcej czasu, niż planowałem?
- Co zrobiłem dziś tylko dla siebie?
- Co mnie dziś zaskoczyło?
- Co jutro robię najpierw?

### Uwaga (13)

- Kiedy dziś najlepiej mi się pracowało?
- Co mnie dziś najczęściej wybijało z roboty?
- Czy dziś się nudziłem? Co wtedy zrobiłem?
- Nad czym siedziałem dziś bez przerwy dłużej niż pół godziny?
- Co dziś robiłem na autopilocie?
- O czym dziś najwięcej myślałem?
- Czy była dziś cisza? Gdzie?
- Co dziś zrobiłem powoli i porządnie?
- Co dziś przeczytałem do końca?
- Czy dziś trzymałem się planu? Gdzie się rozjechało?
- Kiedy dziś czas leciał najszybciej?
- Co dziś odkładałem, przewijając?
- Gdzie uciekałem, kiedy robiło się trudno?

### Ciało (9)

- Jak spałem? Od 1 do 5.
- Ile się dziś ruszałem?
- Kiedy dziś miałem najwięcej siły?
- Kiedy dziś siadła mi energia?
- Jak się czuje ciało? Jedno zdanie.
- Czy byłem dziś na dworze? Jak długo?
- Kiedy dziś odpocząłem?
- Co mnie dziś bolało?
- Co dziś zrobiłem dla zdrowia?

### Ludzie (10)

- Z kim dziś rozmawiałem najdłużej?
- Kto mnie dziś zaskoczył?
- Komu dziś pomogłem?
- Kto mi dziś pomógł?
- Do kogo chciałem się dziś odezwać i się nie odezwałem?
- Z kim rozmawiałem dziś na żywo?
- Z kim się dziś nie zgodziłem? O co?
- Kogo dziś słuchałem: podcast, film, rozmowa?
- Po rozmowie z kim miałem lepszy humor?
- Z kim się dziś porównywałem?

### Głowa (10)

- Jaka myśl wracała do mnie dziś kilka razy?
- Czego się dziś dowiedziałem?
- Jakie pytanie chodzi mi dziś po głowie?
- Co dziś choć trochę zmieniło moje zdanie?
- Z czym się dziś nie zgadzam?
- Jaki pomysł wpadł mi dziś do głowy?
- Co ciekawego dziś przeczytałem albo usłyszałem?
- Nad czym chcę jutro dłużej pomyśleć?
- Co dziś zrozumiałem lepiej niż wczoraj?
- Jakie zdanie z dzisiaj chcę zapisać?

### Drobiazgi (8)

- Kiedy dziś było spokojnie?
- Co dziś było przyjemne?
- Co ładnego dziś widziałem?
- Co dobrego dziś dostałem od kogoś?
- Co mnie dziś rozśmieszyło bez ekranu?
- Co dziś zrobiłem rękami?
- Co dziś sprawdziłem z ciekawości?
- Jaka drobna rzecz dziś wyszła?

## Licencja

Kod i pytania: MIT, zobacz [LICENSE](LICENSE). Możesz je kopiować i przerabiać.
