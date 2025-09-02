# Sustainability Board Game  

A Java-based board game focused on environmental sustainability and resource management, where players invest in eco-friendly properties and technologies to build a sustainable future. Similar to games such as Monoploy

---

## Overview  
This turn-based strategy game challenges players to make sustainable investment decisions while managing limited resources. Players navigate a board containing various eco-friendly properties, special squares, and face strategic choices that impact both their progress and the environment.  

---

## Game Mechanics  

### Core Gameplay  
- Turn-based movement using dice rolls  
- Resource management system with starting capital of **1000 units**  
- Property investment in sustainable technologies  
- Development system with multiple upgrade levels  
- Efficiency scoring based on investment strategy  
- Wasteland mechanic that introduces risk/reward decisions  

### Property Types  
The game features diverse sustainability-focused properties:  
- **Land Energy**: Solar Farm → Biomass Plant → Nuclear Power → Smart Grid  
- **Waste Management** (2 development levels)  
- **Marine Energy** (3 development levels)  
- **Eco City** (special high-value property)  

### Special Mechanics  
- **Wasteland Square**: Players face resource distribution choices when landing here  
- **Sustainability Grant**: Starting square providing resources  
- **Efficiency Calculator**: Evaluates player performance based on resource utilization  
- **Dynamic Rent System**: Rent increases with property development levels  

---

## Technical Implementation  

### Architecture  
Object-Oriented Design with clear separation of concerns:  
- **Model classes**: `Player`, `PropertySquare`, `BoardSquare`, `GameBoard`  
- **Game logic**: `PlayerManager`, `EfficiencyCalculator`, `PropertyUtils`  
- **Utility classes** for common operations  

### Key Features  
- Polymorphism with `BoardSquare` inheritance hierarchy  
- Enum-based property types for type safety  
- Resource tracking with transaction logging  
- Comprehensive validation for game rules  
- State management for complex game scenarios  

---

## Testing Coverage  
Comprehensive **JUnit 5** test suite covering:  
- Unit tests for all major classes (`Player`, `PropertySquare`, `GameBoard`)  
- Integration tests for game mechanics  
- Edge case testing for boundary conditions  
- Mock scenarios for complex interactions  

---

## Development Approach  

### Design Patterns  
- **Strategy Pattern** for different property types  
- **State Pattern** for player status management  
- **Factory concepts** for board initialization  

### Code Quality  
- 100+ test assertions across multiple test classes  
- Clear method documentation with **Javadoc**  
- Consistent naming conventions  
- Modular architecture for maintainability  

---

## Running the Game  

### Prerequisites  
- Java **11** or higher  
- JUnit 5 for running tests  
- Eclipse IDE (recommended)  

### Setup Instructions  
```bash
# Clone the repository
git clone https://github.com/your-username/sustainability-board-game.git
cd sustainability-board-game


