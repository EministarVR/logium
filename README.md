# 🌌 Logium – Das ultimative Minecraft-Logging-Plugin
![Spigot](https://img.shields.io/badge/Spigot-1.8--1.20.x-orange)  
![Java](https://img.shields.io/badge/Java-17%2B-blue)  
![License](https://img.shields.io/badge/License-GNU%20GPLv3-blue)  
![Version](https://img.shields.io/badge/Version-WIP-yellow)  
![Status](https://img.shields.io/badge/Build-Active-brightgreen)

> Entwickelt von [**EministarVR**](https://github.com/EministarVR) mit ❤️  
> Vollständig modular. Discord-ready. Deluxe-UX.

---

## 🚀 Features

- Protokolliert alle wichtigen Events:  
  Chat, Join/Leave, Deaths, Teleports, MSGs, Bans, Mutes, Reports etc.

- Jeder Log-Typ einzeln aktivierbar und konfigurierbar

- Unterstützung für Discord Webhooks (inkl. Skin-Vorschau & Uhrzeit)

- Optionales Datei-Logging für lokale `.log` Dateien

- Unterstützt mehrsprachige Ausgaben über eine `lang.yml`

- Ingame-Konfiguration per Befehl möglich

- Modernes, sauberes Embed-Design mit Farbcodierung

---

## 🛠 Konfiguration

In der `config.yml` kannst du jede Log-Art einzeln ein- oder ausschalten und eine eigene Webhook-URL definieren.

Beispiel:

- `discord-log`: Aktiviert das Logging auf Discord
- `file-log`: Aktiviert das Schreiben in lokale Logdateien
- `chat-log.enabled`: true/false
- `chat-log.webhook-url`: Deine Discord-Webhook-URL
- `simplified-message`: true für einfache Texte, false für hübsche Embeds

Jede Log-Art (z. B. Chat, Join/Leave, Reports) hat ihre eigene Sektion in der Config.

---

## ⚙️ Kommandos

- `/logium reload` – Lädt alle Konfigurationsdateien neu
- `/logium debug` – Erstellt eine `info.logium`-Datei mit Plugin-Daten
- `/logium <logart> <webhook>` – Setzt die Webhook-URL direkt ingame

Nur nutzbar mit der Permission `logium.admin`.

---

## 🧠 Anforderungen

- Minecraft-Server mit Spigot, Paper oder Forks (1.8 bis 1.20+)
- Java 17 oder höher
- Optional: MySQL für Highwarns & Reports

---

## 📂 Installation

1. Lade dir die neueste Logium `.jar` von GitHub Releases herunter
2. Platziere sie im `plugins/`-Verzeichnis deines Servers
3. Starte den Server einmal
4. Konfiguriere `config.yml` und `lang.yml` nach deinem Geschmack
5. Freu dich über saubere Logs direkt auf Discord!

---

## 🧪 Roadmap

- [x] Discord- und Datei-Logging
- [x] Volle Modularität aller Log-Arten
- [x] Report-System mit Discord-Integration
- [x] Mehrsprachigkeit per `lang.yml`
- [ ] Webinterface zur Log-Ansicht
- [ ] Analyse-Funktionen mit Statistiken
- [ ] Premium-Funktionen & API für Devs

---

## 👥 Mitwirken

Du willst mitentwickeln oder Ideen einbringen?
- Star das Projekt ⭐
- Forke das Repo
- Erstell Pull Requests
- Oder melde Bugs & Ideen unter [Issues](https://github.com/EministarVR/Logium/issues)

---

## 💬 Support

Fragen, Feedback oder Support?  
➡️ Tritt dem offiziellen YukiCraft Discord bei: [**Discord beitreten**](https://dcs.lol/edg)

---

## 📜 Lizenz

Dieses Projekt steht unter der **GNU General Public License v3.0**.  
Du darfst es verwenden, verändern und weiterverbreiten, solange du den Quellcode offenlegst und Änderungen dokumentierst.  
Weitere Infos: [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.html)
