# ATM Banking System — Coding Samurai Task 1

## 🌐 Web Version (Recommended)

A modern, professional ATM Banking System web application with realistic ATM interface, smooth animations, and professional UI/UX.

### Features
- ✅ Realistic ATM machine interface with glass overlay effect
- ✅ Full-screen ATM background with professional styling
- ✅ Rectangular ATM-style buttons with 3D effects and hover animations
- ✅ Smooth screen transitions and loading animations
- ✅ Professional alert/notification system
- ✅ Responsive design for all devices
- ✅ Complete ATM functionality (Create Account, Login, Balance, Deposit, Withdraw, Fast Cash)
- ✅ Input validation and error handling
- ✅ Modern fonts (Roboto, Poppins)

### How to Run Web Version

1. **Simple Method:**
   - Open `index.html` in any modern web browser (Chrome, Firefox, Edge, Safari)
   - No server or installation required!

2. **Using VS Code Live Server (Recommended):**
   - Install "Live Server" extension in VS Code
   - Right-click on `index.html` → "Open with Live Server"
   - The app will open in your browser automatically

3. **Using Python (if installed):**
   ```bash
   # Python 3
   python -m http.server 8000
   
   # Then open: http://localhost:8000
   ```

### Web Version Files
- `index.html` - Main HTML structure
- `styles.css` - Professional ATM styling and animations
- `script.js` - Complete ATM functionality

### Usage
1. **Create Account:** Click "Create Account" → Enter name (optional) → Generate Account → Set 4-digit PIN
2. **Login:** Use your Account Number and PIN to login
3. **Transactions:** 
   - Check Balance
   - Deposit Money
   - Withdraw Money
   - Fast Cash (₹50, ₹100, ₹200, ₹500, ₹1000)

---

## 💻 Java Swing Version (Original)

### How to Run

#### Option A: VS Code / Eclipse
- Open the folder `ATMBankingSystem/`
- Run `Main.java`

#### Option B: Command line (Windows PowerShell)
From the `ATMBankingSystem` folder:
```bash
javac *.java
java Main
```

### Images (ATM Look)
Place these files inside `ATMBankingSystem/images/`:
- `welcome.jpg`
- `create_account.jpg`
- `pin.jpg`
- `login.jpg` (optional but recommended; code falls back if missing)
- `menu.jpg`
- `balance.jpg`
- `deposit.jpg`
- `withdraw.jpg`

If any image is missing, the app will still run (it will show a simple colored background instead).

---

## 📝 Notes

- **Web Version:** No images required - uses CSS gradients and effects for realistic ATM look
- **Java Version:** Requires background images for full ATM experience
- Both versions maintain the same core ATM logic and functionality
- All data is stored in-memory (no database)
- Account numbers are auto-generated starting from 100001

