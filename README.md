# 🎮 Emoji Match

**Emoji Match** is a turn-based strategy game built in **Java**, where players navigate a grid filled with emoji tiles. Each emoji triggers a unique effect that can alter the board, affect player stats, or influence the outcome of the match. The goal is to outmaneuver your opponent and accumulate points, gold, experience, and health.

---

## ✨ Features

- 🧭 Grid-based movement using directional input (W, A, S, D)  
- 🧠 Strategic interactions with emoji tiles  
- 🔄 Dynamic board transformations  
- 📊 Score tracking: life, gold, experience, and points  
- 🗂️ Battle history stored for replay and analysis

---

## 🧩 Emoji Effects

Each emoji on the board has a specific effect when activated:

| Emoji | Effect |
|-------|--------|
| ☠ (skull) | Removes 1 life from opponent |
| $ (gold) | Adds 1 gold to the player |
| ✚ (cross) | Adds 1 life to the player |
| ☺ (happy face) | Transforms all ☠ into ☺ on the board |
| ☻ (sad face) | Transforms all ☺ into ☠ on the board |
| ☀ (sun) | Resets opponent's gold to 0 |
| ✦ (star) | Adds 1 experience point to the player |

---

## 📂 Project Structure

```
Battle-Game/
├── src/
│   ├── Board.java            # Handles grid layout and emoji logic
│   ├── Player.java           # Represents a player with stats and actions
│   ├── Game.java             # Core game loop and mechanics
│   ├── GameData.java         # Stores and manages past games
│   ├── GameMenu.java         # Menu system for starting and managing the game
│   └── Main.java             # Entry point of the application
├── results/
│   └── match_logs.txt        # Records and history of previous battles
├── README.md                 # This file!
└── ...
```

## 🚀 Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/luanaguerisoli/Battle-Game.git
   ```
2. **Open with your favorite Java IDE.**
3. **Build and run the project!**

---
