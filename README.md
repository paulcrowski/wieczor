# Wieczór ("Evening")

[🇵🇱 Polski](README.pl.md) · 🇬🇧 English

An Android app for evening questions. Each evening it draws a few questions from a pool of 239, for example:

- When did I first pick up my phone today?
- What did I believe today without checking?
- What did I do today because "that's how it's done"?

You answer in a few words, or skip. It takes a couple of minutes.

I built it for myself, to study how my feed shapes my day. By default every draw has one question about the feed, one about what went well, and one surprising one that is meant to stop you or shift your viewpoint.

**Try it now (iPhone, Android, desktop):** [https://paulcrowski.github.io/wieczor/](https://paulcrowski.github.io/wieczor/). No download, no account.

**Language:** the app and its questions are in Polish. The English README is here so you can see what it does and read the code. Translating the question pool is on the to-do list (see below). Pull requests are welcome.

## Features

- **Evening:** 4 questions a day by default: one about the feed, one surprising one (from another angle, scale, imagination, decisions, the algorithm, meaning, or tomorrow), one from the other groups, and one about what went well. Questions from the last three weeks do not come back. Each question can be swapped (↻) or skipped. "+ dolosuj pytanie" adds another one. The same day always shows the same questions.
- **Echo:** now and then, instead of the surprising question, one of your own answers or thoughts from a few days ago comes back ("A week ago I wrote: …. What do I think about it today?"). It runs on the device, on your own data.
- **Thought:** a quick note for a single thought during the day (an idea, something you read, something to add). The app icon has a "Złap myśl" (catch a thought) shortcut.
- **History:** every day with its answers. Tap a day to edit it.
- **Week:** the week's answers side by side, with "under the lens" and feed answers grouped. It shows words that came up at least twice. At the end there are three summary questions and a plan for next week in "when…, then…" form. The plan stays above your questions all next week, and in the next summary you mark whether it worked. On Sundays the app reminds you to do the summary.
- **My questions:** add your own questions under the "Więcej" (More) tab. They are drawn together with the rest.
- **Export** (under "Więcej"): Markdown to read, CSV to analyse (one row per answer), JSON as a backup. You can share the file or save it to Files.
- **Reminder:** one notification a day at a time you choose.

An entry made between midnight and 4:00 counts toward the previous evening.

## Privacy

The Android app has no internet permission, so Android will not let it send anything anywhere. The web version is served as static files from GitHub Pages and is locked down with a Content-Security-Policy that only lets the page talk to itself; after the first load it runs from the browser's cache. GitHub, as the host, sees the usual server logs when the page is loaded, but nothing you write is ever sent anywhere. There is no account, no ads and no analytics. Your answers stay in the phone's storage and leave it only when you export them yourself.

Side effect: if you uninstall the app, the data goes with it. Export a "JSON kopia" (JSON backup) from time to time and keep the file somewhere safe. "Importuj JSON" under "Więcej" loads it back.

Permissions: notifications (the reminder) and start after boot (so the reminder survives a restart).

## Install

### iPhone, iPad, or any browser (web app)

1. Open [https://paulcrowski.github.io/wieczor/](https://paulcrowski.github.io/wieczor/) in Safari (iPhone) or Chrome (Android, desktop).
2. iPhone: tap Share → **Add to Home Screen**. Android: menu ⋮ → **Install app**.
3. It now opens full screen and works offline.

No certificate, no App Store, no account. What the web version does **not** have: the daily reminder notification (a browser cannot schedule one without a server, which would break the privacy promise; set a normal alarm in your clock app instead) and the home-screen shortcut on iPhone. Data stays in the browser's storage on that device. Browsers can clear it when space runs low or if you delete the site's data, so export a JSON backup from time to time. Use Share → "Save to Files" to keep the export.

### Android (APK)

1. Download `Wieczor-v2.4.apk` from the [latest release](../../releases/latest).
2. Open the file on your phone. Android will ask for permission to install from this source.
3. If Play Protect shows a warning, choose "Install anyway". It appears because the app is not on Google Play.

Requires Android 8.0 or newer. The APK has the reminder notification and stores data in the app's own folder.

## What it is based on

This is not therapy and does not promise a transformation. A few findings shaped how it is built:

- **Writing about your day in the evening helps learning.** In a call-centre experiment, workers who spent 15 minutes at the end of the day writing about what they learned scored about 23% higher on the final training test than the control group (Di Stefano, Gino, Pisano, Staats, 2014, HBS working paper 14-093).
- **Ask "what", not "why".** "Why" invites guesses about causes you cannot see, and going in circles. Nearly all the questions ask about a fact from the day. Note: this advice comes mainly from Tasha Eurich's book *Insight*, and her data are correlational.
- **Repetition raises belief in a claim, even when we know it is false** (the illusory truth effect; Fazio et al., 2015, *Journal of Experimental Psychology: General*). This is why the "under the lens" questions exist, such as "Which opinion did I agree with today mainly because it was everywhere?"
- **"Consider the opposite" reduces biased judgement,** and works better than simply resolving to be objective (Lord, Lepper, Preston, 1984, *JPSP*). Hence the weekly question "Where could I be wrong?"
- **Looking from the outside reduces rumination.** People who analysed a painful event from an observer's perspective brooded on it less afterwards (Kross, Ayduk, Mischel, 2005). Hence describing the week in the third person.
- **Tracking raises awareness but rarely changes behaviour.** In Zimmermann's study (2021, N = 242), screen-time trackers improved people's knowledge of their phone use, yet their use rarely went down. Change came from concrete plans and friction.
- **"When X, then Y" plans work.** A meta-analysis of 94 tests found a medium-to-large effect on goal attainment (d = 0.65; Gollwitzer & Sheeran, 2006). That is why each week ends with one such plan, and you check its result a week later.

Writing about yourself has a small average effect on health and mood in studies (r = 0.075 in a meta-analysis of 146 studies; Frattaroli, 2006). Do not expect more from it than a few minutes of attention a day.

## Code

The whole UI and logic live in one file: [`app/src/main/assets/index.html`](app/src/main/assets/index.html). The question pool is the `POOL` array at the top of the script. The Java part (`app/src/main/java/`) writes data to a file, opens the share sheet and schedules the reminder.

Build (Android SDK and JDK 17+):

```bash
./gradlew assembleRelease
```

Without your own key in `../wieczor-signing/keystore.properties` the APK is signed with the debug key.

The web version lives in [`docs/`](docs). It is generated from the same `index.html`: after editing it, run `python3 tools/sync_web.py` (it adds the CSP and versions the offline cache). GitHub Pages serves `main` from `/docs`. In a browser the data is kept in `localStorage`.

## Questions

All 239 questions are listed in the [Polish README](README.pl.md#pytania), grouped into 18 sets: feed (20), good (20), under the lens (15), from another angle (16), scale (14), imagination (14), crossroads (12), algorithm (14), meaning (12), tomorrow (12), day (15), attention (13), the world around (15), people (10), mind (10), curiosity (10), body (9), small things (8). The questions are written in the masculine form, because I wrote them for myself. Feminine and neutral versions, and English ones, are on the to-do list.

A few examples, translated:

- *Feed:* What did I scroll longer than I wanted to today?
- *Good:* What came easily to me today?
- *Under the lens:* Who profits from what I watched most today?
- *The world around:* What would I notice here today if I were in this place for the first time?
- *Curiosity:* What did I check for myself today instead of taking it on trust?
- *From another angle:* What would someone from 1950 not understand about today?
- *Scale:* Which of today's news could really change something in my week?
- *Algorithm:* What did I see today only because masses of people clicked on it?
- *Imagination:* If I told today as a joke, what would the punchline be?
- *Tomorrow:* Which usual route will I walk differently tomorrow?

## Licence

Code and questions: MIT, see [LICENSE](LICENSE). You can copy and adapt them.
