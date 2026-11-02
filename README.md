# Wieczór

Aplikacja na Androida do wieczornych pytań. Co wieczór losuje kilka pytań ze 145, na przykład:

- O której pierwszy raz wziąłem dziś telefon?
- W co dziś uwierzyłem bez sprawdzania?
- Co dziś zrobiłem, bo „tak się robi”?

Odpowiadasz kilkoma słowami albo pomijasz. Zajmuje to parę minut.

Napisałem ją dla siebie, do prywatnego badania tego, jak feed wpływa na mój dzień. Pytanie o telefon i pytanie o to, co było dobre, są w każdym losowaniu (można to zmienić).

## Co umie

- **Wieczór**: domyślnie 4 pytania na dzień: jedno o feedzie, jedno o tym, co było dobre, i dwa z pozostałych grup, np. „pod lupą”, świat wokół, ciekawość, ludzie. Liczbę pytań i grupy stałe zmienisz w zakładce Więcej. Każde da się wymienić (↻) albo pominąć. Przycisk „+ dolosuj pytanie” dodaje kolejne. Ten sam dzień ma zawsze te same pytania.
- **Myśl**: szybki zapis jednej myśli w ciągu dnia (pomysł, coś przeczytanego, coś do dopisania). Na ikonie aplikacji jest skrót „Złap myśl”.
- **Historia**: wszystkie dni z odpowiedziami. Dotknięcie dnia otwiera go do poprawek.
- **Tydzień**: odpowiedzi z tygodnia obok siebie, osobno „pod lupą” i feed. Pokazuje słowa, które padły co najmniej dwa razy. Na końcu trzy pytania podsumowania i plan na kolejny tydzień w formie „kiedy…, to…”. Plan wisi potem nad pytaniami przez cały następny tydzień, a w kolejnym podsumowaniu zaznaczasz, czy wyszło. W niedzielę aplikacja przypomina o podsumowaniu.
- **Moje pytania**: w zakładce Więcej dopisujesz własne pytania. Losują się razem z resztą, a jedno z nich może być w każdym losowaniu.
- **Eksport** (zakładka Więcej): Markdown do czytania, CSV do analizy (jeden wiersz to jedna odpowiedź), JSON jako kopia zapasowa. Plik można udostępnić albo zapisać w Plikach.
- **Przypomnienie**: jedno powiadomienie dziennie o wybranej godzinie.

Wpis zrobiony między północą a 4:00 liczy się do poprzedniego wieczoru.

## Prywatność

Aplikacja nie ma uprawnienia do internetu, więc Android nie pozwoli jej niczego wysłać. Nie ma konta, reklam ani analityki. Odpowiedzi leżą w pamięci telefonu i wychodzą z niego tylko wtedy, gdy sam zrobisz eksport.

Skutek uboczny: po odinstalowaniu aplikacji dane znikają. Co jakiś czas zrób eksport „JSON kopia” i trzymaj plik w bezpiecznym miejscu. Import w zakładce Eksport wczyta go z powrotem.

Uprawnienia: powiadomienia (przypomnienie) i start po włączeniu telefonu (żeby przypomnienie przetrwało restart).

## Instalacja

1. Pobierz `Wieczor-v2.3.apk` z zakładki Releases.
2. Otwórz plik na telefonie. Android zapyta o zgodę na instalację z tego źródła.
3. Jeśli pojawi się ostrzeżenie Play Protect, wybierz „Zainstaluj mimo to”. Pojawia się, bo aplikacji nie ma w Sklepie Play.

Wymaga Androida 8.0 lub nowszego.

## Na czym to stoi

Aplikacja nie jest terapią i nie obiecuje przemiany. Kilka badań stoi za tym, jak jest zbudowana:

- **Wieczorne pisanie o dniu pomaga się uczyć.** W eksperymencie w call center pracownicy, którzy pod koniec dnia przez 15 minut pisali o tym, czego się nauczyli, wypadli w końcowym teście szkolenia o ok. 23% lepiej niż grupa kontrolna (Di Stefano, Gino, Pisano, Staats, 2014, working paper HBS 14-093).
- **Pytania „co”, nie „dlaczego”.** „Dlaczego” zachęca do zgadywania przyczyn, których nie widać, i do kręcenia się w kółko. Prawie wszystkie pytania pytają o fakt z dnia. Uwaga: ta rada pochodzi głównie z książki Tashy Eurich „Insight”, a jej dane są korelacyjne.
- **Powtarzanie zwiększa wiarę w twierdzenie, nawet gdy wiemy, że jest fałszywe** (efekt iluzji prawdy; Fazio i in., 2015, *Journal of Experimental Psychology: General*). Stąd pytania „pod lupą”, takie jak „Z jaką opinią zgodziłem się dziś głównie dlatego, że była wszędzie?”.
- **„Rozważ przeciwieństwo” zmniejsza stronniczość ocen,** i to bardziej niż samo postanowienie, żeby być obiektywnym (Lord, Lepper, Preston, 1984, *JPSP*). Stąd pytanie tygodniowe „W czym mogę się mylić?”.
- **Spojrzenie z boku zmniejsza zamartwianie się.** Ludzie, którzy analizowali przykre wydarzenie z perspektywy obserwatora, mniej je potem przeżuwali (Kross, Ayduk, Mischel, 2005). Stąd opis tygodnia w trzeciej osobie.
- **Samo śledzenie zwiększa świadomość, ale rzadko zmienia zachowanie.** W badaniu Zimmermanna (2021, N = 242) liczniki czasu ekranowego poprawiały wiedzę o własnym używaniu telefonu, a jego czas spadał rzadko. Zmianę dają dopiero konkretne plany i utrudnienia.
- **Plan „kiedy X, to Y” działa.** Metaanaliza 94 testów pokazała średni do dużego efekt na realizację celów (d = 0,65; Gollwitzer i Sheeran, 2006). Dlatego tydzień kończy się jednym takim planem, a jego wynik sprawdzasz tydzień później.

Pisanie o sobie ma w badaniach mały efekt na zdrowie i samopoczucie (r = 0,075 w metaanalizie 146 badań; Frattaroli, 2006). Nie warto oczekiwać od niego więcej niż od kilku minut uwagi dziennie.

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

### Dobre (20)

- Co dziś poszło dobrze, choćby trochę?
- Z czego się dziś ucieszyłem?
- Co dobrego zrobiłem dziś dla kogoś?
- Co ktoś zrobił dziś dla mnie?
- Kiedy się dziś uśmiechnąłem?
- Co robię dziś lepiej niż miesiąc temu?
- Co mi dziś smakowało?
- Która rozmowa była dziś dobra?
- Co przyszło mi dziś łatwo?
- Czego dziś nie zepsułem, choć mogłem?
- Co mnie dziś uspokoiło?
- Co dziś zrobiłem, z czego jutro rano będę zadowolony?
- Kogo dziś dobrze wspominam?
- Co było dziś lepsze niż wczoraj?
- Co dziś dobrze zrobiło mojemu ciału?
- Po czym dziś widać, że daję radę?
- Za co pochwaliłbym kolegę, gdyby zrobił dziś to samo co ja?
- Na co cieszę się jutro?
- Co było dziś zabawne?
- Co dziś zrobiłem dobrze, choć nikt tego nie widział?

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

### Świat wokół (15)

- Co zauważyłem dziś po drodze, czego wcześniej nie widziałem?
- Jakie było dziś niebo?
- Co słyszałem, kiedy na chwilę się zatrzymałem?
- Kogo obcego dziś zapamiętałem? Po czym?
- Jak wyglądał mój dzień oczami osoby, która mieszka ze mną?
- Co się dziś zmieniło za oknem albo w okolicy?
- Jak pachniało dziś na dworze?
- Co obejrzałem dziś z bliska: roślinę, owada, fakturę czegoś?
- Jaki dzień mieli dziś ludzie wokół mnie?
- Czego dowiedziałem się dziś o kimś bliskim?
- Co bym tu dziś zauważył, gdybym był w tym miejscu pierwszy raz?
- Jakiej muzyki dziś słuchałem? Kto ją wybrał: ja czy algorytm?
- Co zrobiłem dziś wolniej niż zwykle?
- Z kim się dziś zgodziłem, choć zwykle się nie zgadzamy?
- Jaką pogodę dziś czułem na skórze?

### Ciekawość (10)

- Czego chciałem się dziś dowiedzieć?
- O co zapytałbym dziś kogoś, kto zna się na rzeczy?
- Co sprawdziłem dziś sam, zamiast przyjąć na słowo?
- Czego nie rozumiem, a chciałbym?
- Co zrobiłbym dziś, gdybym się nie bał wyjść na głupka?
- O czym chętnie posłuchałbym godzinnego wykładu?
- Czego mogę się nauczyć od kogoś, z kim się nie zgadzam?
- Co wydało mi się dziś dziwne?
- Czego jutro spróbuję pierwszy raz?
- Jakie miejsce, książkę albo osobę chcę poznać?

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
