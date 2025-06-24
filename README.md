# ✨ Logium – Dein Logging-Upgrade für Minecraft

![Spigot](https://img.shields.io/badge/Spigot-1.8--1.20.x-orange)
![Java](https://img.shields.io/badge/Java-17%2B-blue)
![License](https://img.shields.io/badge/License-GNU%20GPLv3-blue)
![Version](https://img.shields.io/badge/Version-WIP-yellow)
![Status](https://img.shields.io/badge/Build-Active-brightgreen)

> Entwickelt von [**EministarVR**](https://github.com/EministarVR) mit ❤️ – voll modular, Discord-ready und mit Deluxe-UX.

---

## 📑 Inhaltsverzeichnis

1. [Features](#-features)
2. [Installation](#-installation)
3. [Konfiguration](#-konfiguration)
4. [Kommandos](#-kommandos)
5. [Roadmap](#-roadmap)
6. [Mitwirken](#-mitwirken)
7. [Support](#-support)
8. [Lizenz](#-lizenz)

---

## 🚀 Features

- **Umfangreiches Event-Logging** – Chat, Join/Leave, Tode, Teleports, Nachrichten, Bans, Mutes, Reports u. v. m.
- **Individuell konfigurierbar** – Jeder Log-Typ lässt sich separat aktivieren oder deaktivieren.
- **Discord-Webhooks** – Schicke Logs direkt in deinen Discord-Channel (mit Skin-Vorschau & Zeitstempel).
- **Optionales Datei-Logging** – Speichere alle Events zusätzlich in lokalen `.log`-Dateien.
- **Mehrsprachigkeit** – Alle Nachrichten lassen sich über die `lang.yml` übersetzen.
- **Ingame-Konfiguration** – Passe Einstellungen direkt im Spiel an.
- **Modernes Embed-Design** – Farbcodierte, aufgeräumte Discord-Nachrichten.

---

## 📂 Installation

1. Lade dir die neueste Logium-`jar` von GitHub Releases herunter.
2. Lege die Datei in das `plugins/`-Verzeichnis deines Servers.
3. Starte den Server einmal, damit die Konfigurationsdateien erzeugt werden.
4. Passe `config.yml` und `lang.yml` nach deinen Wünschen an.
5. Fertig – deine Logs landen nun übersichtlich auf Discord!

---

## 🛠 Konfiguration

In der `config.yml` stellst du detailliert ein, welche Log-Arten aktiv sein sollen und wo sie landen. Ein kleines Beispiel:

```yml
discord-log: true
file-log: true
chat-log:
  enabled: true
  webhook-url: "https://discord.com/api/webhooks/..."
simplified-message: false
```

Jeder Bereich (z. B. Chat, Join/Leave, Reports) besitzt eine eigene Sektion mit weiteren Optionen.

---

## ⚙️ Kommandos

| Befehl | Beschreibung |
|--------|-------------|
| `/logium reload` | Lädt alle Konfigurationsdateien neu |
| `/logium debug` | Erstellt eine `info.logium`-Datei mit Plugin-Daten |
| `/logium <logart> <webhook>` | Setzt die Webhook-URL direkt im Spiel |

Diese Befehle erfordern die Permission `logium.admin`.

---

## 🧪 Roadmap

- [x] Discord- und Datei-Logging
- [x] Vollständige Modularität aller Log-Arten
- [x] Report-System mit Discord-Integration
- [x] Mehrsprachigkeit per `lang.yml`
- [ ] Webinterface zur Log-Ansicht
- [ ] Analysefunktionen und Statistiken
- [ ] Premium-Features & API für Entwickler

---

## 👥 Mitwirken

Du möchtest Ideen einbringen oder selbst mitentwickeln?

1. Gib dem Projekt ein ⭐ auf GitHub.
2. Forke das Repository.
3. Erstelle deine Änderungen in einem Branch und öffne einen Pull Request.
4. Melde Bugs oder Feature-Wünsche unter [Issues](https://github.com/EministarVR/Logium/issues).

---

## 💬 Support

Bei Fragen oder Problemen bist du herzlich auf dem offiziellen YukiCraft-Discord willkommen: [**Discord beitreten**](https://dcs.lol/edg)

---

## 📜 Lizenz

Logium steht unter der **GNU General Public License v3.0**. Du darfst das Plugin frei verwenden, anpassen und weitergeben, solange der Quellcode offen bleibt und Änderungen dokumentiert werden. Mehr Informationen findest du in der [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.html).

