// ============================================
// ATM BANKING SYSTEM - CORE FUNCTIONALITY
// ============================================

// Account Management (In-memory storage)
class ATMBase {
    static accounts = [];
    static currentAccount = null;

    // Generate unique account number
    static generateAccountNumber() {
        const base = 100000;
        const next = base + this.accounts.length + 1;
        return String(next);
    }

    // Find account by account number
    static findAccount(accountNumber) {
        return this.accounts.find(acc => acc.accountNumber === accountNumber) || null;
    }

    // Validate PIN (must be exactly 4 digits)
    static isValidPin(pin) {
        if (!pin || pin.length !== 4) return false;
        return /^\d{4}$/.test(pin);
    }

    // Validate amount (must be positive)
    static isValidAmount(amount) {
        return amount > 0;
    }
}

// Account Class
class Account {
    constructor(accountNumber, name = '') {
        this.accountNumber = accountNumber;
        this.name = name;
        this.pin = '';
        this.balance = 0;
    }

    setPin(pin) {
        this.pin = pin;
    }

    deposit(amount) {
        this.balance += amount;
    }

    withdraw(amount) {
        if (amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    getBalanceFormatted() {
        return `₹${this.balance.toFixed(2)}`;
    }
}

// Screen Manager
class ScreenManager {
    static showScreen(screenId) {
        // Hide all screens
        document.querySelectorAll('.screen').forEach(screen => {
            screen.classList.remove('active');
        });
        
        // Show target screen
        const targetScreen = document.getElementById(screenId);
        if (targetScreen) {
            targetScreen.classList.add('active');
        }
    }

    static showLoading(show = true) {
        const loadingOverlay = document.getElementById('loadingOverlay');
        if (show) {
            loadingOverlay.classList.add('active');
        } else {
            loadingOverlay.classList.remove('active');
        }
    }
}

// Alert/Notification System
class AlertSystem {
    static show(message, type = 'info', duration = 3000) {
        const alertContainer = document.getElementById('alertContainer');
        
        const alert = document.createElement('div');
        alert.className = `alert ${type}`;
        
        const icons = {
            success: '✅',
            error: '❌',
            info: 'ℹ️',
            warning: '⚠️'
        };
        
        alert.innerHTML = `
            <span class="alert-icon">${icons[type] || icons.info}</span>
            <span class="alert-message">${message}</span>
        `;
        
        alertContainer.appendChild(alert);
        
        // Auto remove after duration
        setTimeout(() => {
            alert.style.animation = 'alertFadeOut 0.4s ease forwards';
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 400);
        }, duration);
    }

    static success(message) {
        this.show(message, 'success');
    }

    static error(message) {
        this.show(message, 'error');
    }

    static info(message) {
        this.show(message, 'info');
    }

    static warning(message) {
        this.show(message, 'warning');
    }
}

// Simulate processing delay for realistic ATM experience
function simulateProcessing(callback, delay = 1500) {
    ScreenManager.showLoading(true);
    setTimeout(() => {
        ScreenManager.showLoading(false);
        if (callback) callback();
    }, delay);
}

// ============================================
// EVENT LISTENERS & INITIALIZATION
// ============================================

document.addEventListener('DOMContentLoaded', () => {
    initializeEventListeners();
    // Start on welcome screen
    ScreenManager.showScreen('welcomeScreen');
});

function initializeEventListeners() {
    // Welcome Screen
    document.getElementById('btnCreateAccount').addEventListener('click', () => {
        ScreenManager.showScreen('createAccountScreen');
        document.getElementById('fullName').value = '';
    });

    document.getElementById('btnLogin').addEventListener('click', () => {
        ScreenManager.showScreen('loginScreen');
        document.getElementById('loginAccount').value = '';
        document.getElementById('loginPin').value = '';
    });

    document.getElementById('btnExit').addEventListener('click', () => {
        if (confirm('Are you sure you want to exit?')) {
            AlertSystem.info('Thank you for using ATM Banking System!');
            setTimeout(() => {
                window.close();
            }, 1000);
        }
    });

    // Create Account Screen
    document.getElementById('btnGenerateAccount').addEventListener('click', handleCreateAccount);
    document.getElementById('btnBackFromCreate').addEventListener('click', () => {
        ScreenManager.showScreen('welcomeScreen');
    });

    // PIN Generation Screen
    document.getElementById('btnSetPin').addEventListener('click', handleSetPin);
    document.getElementById('btnCancelPin').addEventListener('click', () => {
        ScreenManager.showScreen('welcomeScreen');
    });

    // PIN Input - Only allow numbers
    document.getElementById('pinInput').addEventListener('input', (e) => {
        e.target.value = e.target.value.replace(/\D/g, '');
    });
    document.getElementById('pinConfirm').addEventListener('input', (e) => {
        e.target.value = e.target.value.replace(/\D/g, '');
    });
    document.getElementById('loginPin').addEventListener('input', (e) => {
        e.target.value = e.target.value.replace(/\D/g, '');
    });

    // Login Screen
    document.getElementById('btnLoginSubmit').addEventListener('click', handleLogin);
    document.getElementById('btnBackFromLogin').addEventListener('click', () => {
        ScreenManager.showScreen('welcomeScreen');
    });

    // Menu Screen
    document.getElementById('btnBalance').addEventListener('click', () => {
        showBalanceScreen();
    });
    document.getElementById('btnDeposit').addEventListener('click', () => {
        showDepositScreen();
    });
    document.getElementById('btnWithdraw').addEventListener('click', () => {
        showWithdrawScreen();
    });
    document.getElementById('btnLogout').addEventListener('click', handleLogout);

    // Fast Cash Buttons
    document.querySelectorAll('.fast-cash-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const amount = parseFloat(e.target.getAttribute('data-amount'));
            handleFastCash(amount);
        });
    });

    // Balance Screen
    document.getElementById('btnBackToMenu').addEventListener('click', () => {
        showMenuScreen();
    });

    // Deposit Screen
    document.getElementById('btnDepositSubmit').addEventListener('click', handleDeposit);
    document.getElementById('btnBackToMenuFromDeposit').addEventListener('click', () => {
        showMenuScreen();
    });

    // Withdraw Screen
    document.getElementById('btnWithdrawSubmit').addEventListener('click', handleWithdraw);
    document.getElementById('btnBackToMenuFromWithdraw').addEventListener('click', () => {
        showMenuScreen();
    });

    // Enter key support for forms
    document.getElementById('pinInput').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') document.getElementById('pinConfirm').focus();
    });
    document.getElementById('pinConfirm').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') document.getElementById('btnSetPin').click();
    });
    document.getElementById('loginAccount').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') document.getElementById('loginPin').focus();
    });
    document.getElementById('loginPin').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') document.getElementById('btnLoginSubmit').click();
    });
    document.getElementById('depositAmount').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') document.getElementById('btnDepositSubmit').click();
    });
    document.getElementById('withdrawAmount').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') document.getElementById('btnWithdrawSubmit').click();
    });
}

// ============================================
// HANDLER FUNCTIONS
// ============================================

function handleCreateAccount() {
    const name = document.getElementById('fullName').value.trim();
    
    simulateProcessing(() => {
        const accountNumber = ATMBase.generateAccountNumber();
        const account = new Account(accountNumber, name);
        ATMBase.accounts.push(account);
        
        // Show account number in PIN screen
        document.getElementById('pinAccountNumber').textContent = accountNumber;
        document.getElementById('pinInput').value = '';
        document.getElementById('pinConfirm').value = '';
        
        AlertSystem.success(`Account Created Successfully!\nYour Account Number is: ${accountNumber}\n\nNow set your 4-digit PIN.`);
        
        ScreenManager.showScreen('pinGenerationScreen');
        document.getElementById('pinInput').focus();
    });
}

function handleSetPin() {
    const pin = document.getElementById('pinInput').value;
    const confirmPin = document.getElementById('pinConfirm').value;
    const accountNumber = document.getElementById('pinAccountNumber').textContent;
    
    // Validation
    if (!ATMBase.isValidPin(pin)) {
        AlertSystem.error('PIN must be exactly 4 digits.');
        document.getElementById('pinInput').focus();
        return;
    }
    
    if (pin !== confirmPin) {
        AlertSystem.error('PIN and Confirm PIN do not match.');
        document.getElementById('pinConfirm').focus();
        return;
    }
    
    simulateProcessing(() => {
        const account = ATMBase.findAccount(accountNumber);
        if (account) {
            account.setPin(pin);
            AlertSystem.success('PIN set successfully!\n\nNow login using your Account Number and PIN.');
            ScreenManager.showScreen('loginScreen');
            document.getElementById('loginAccount').focus();
        }
    });
}

function handleLogin() {
    const accountNumber = document.getElementById('loginAccount').value.trim();
    const pin = document.getElementById('loginPin').value;
    
    // Validation
    if (!accountNumber || !pin) {
        AlertSystem.error('Please fill in all fields!');
        return;
    }
    
    if (!ATMBase.isValidPin(pin)) {
        AlertSystem.error('PIN must be exactly 4 digits.');
        return;
    }
    
    simulateProcessing(() => {
        const account = ATMBase.findAccount(accountNumber);
        
        if (!account) {
            AlertSystem.error('Account not found!');
            document.getElementById('loginPin').value = '';
            return;
        }
        
        if (account.pin !== pin) {
            AlertSystem.error('Invalid PIN!');
            document.getElementById('loginPin').value = '';
            document.getElementById('loginPin').focus();
            return;
        }
        
        // Login successful
        ATMBase.currentAccount = account;
        AlertSystem.success('Login successful!');
        showMenuScreen();
    });
}

function handleLogout() {
    if (confirm('Are you sure you want to logout?')) {
        ATMBase.currentAccount = null;
        AlertSystem.info('Logged out successfully.');
        ScreenManager.showScreen('welcomeScreen');
    }
}

function showMenuScreen() {
    ScreenManager.showScreen('menuScreen');
}

function showBalanceScreen() {
    if (!ATMBase.currentAccount) {
        AlertSystem.error('No account logged in.');
        return;
    }
    
    simulateProcessing(() => {
        document.getElementById('balanceAccountNumber').textContent = ATMBase.currentAccount.accountNumber;
        document.getElementById('balanceAmount').textContent = ATMBase.currentAccount.getBalanceFormatted();
        ScreenManager.showScreen('balanceScreen');
    });
}

function showDepositScreen() {
    if (!ATMBase.currentAccount) {
        AlertSystem.error('No account logged in.');
        return;
    }
    
    document.getElementById('depositCurrentBalance').textContent = ATMBase.currentAccount.getBalanceFormatted();
    document.getElementById('depositAmount').value = '';
    ScreenManager.showScreen('depositScreen');
    document.getElementById('depositAmount').focus();
}

function handleDeposit() {
    if (!ATMBase.currentAccount) {
        AlertSystem.error('No account logged in.');
        return;
    }
    
    const amountText = document.getElementById('depositAmount').value.trim();
    
    // Validation
    if (!amountText) {
        AlertSystem.error('Please enter an amount!');
        return;
    }
    
    const amount = parseFloat(amountText);
    
    if (isNaN(amount) || amount <= 0) {
        AlertSystem.error('Amount must be greater than zero!');
        return;
    }
    
    // Confirm deposit
    if (!confirm(`Deposit ₹${amount.toFixed(2)}?`)) {
        return;
    }
    
    simulateProcessing(() => {
        ATMBase.currentAccount.deposit(amount);
        AlertSystem.success(
            `Deposit successful!\n` +
            `Amount deposited: ₹${amount.toFixed(2)}\n` +
            `New balance: ${ATMBase.currentAccount.getBalanceFormatted()}`
        );
        showDepositScreen(); // Refresh screen with new balance
    });
}

function showWithdrawScreen() {
    if (!ATMBase.currentAccount) {
        AlertSystem.error('No account logged in.');
        return;
    }
    
    document.getElementById('withdrawCurrentBalance').textContent = ATMBase.currentAccount.getBalanceFormatted();
    document.getElementById('withdrawAmount').value = '';
    ScreenManager.showScreen('withdrawScreen');
    document.getElementById('withdrawAmount').focus();
}

function handleWithdraw() {
    if (!ATMBase.currentAccount) {
        AlertSystem.error('No account logged in.');
        return;
    }
    
    const amountText = document.getElementById('withdrawAmount').value.trim();
    
    // Validation
    if (!amountText) {
        AlertSystem.error('Please enter an amount!');
        return;
    }
    
    const amount = parseFloat(amountText);
    
    if (isNaN(amount) || amount <= 0) {
        AlertSystem.error('Amount must be greater than zero!');
        return;
    }
    
    if (amount > ATMBase.currentAccount.balance) {
        AlertSystem.error(
            `Insufficient balance!\n` +
            `Available balance: ${ATMBase.currentAccount.getBalanceFormatted()}\n` +
            `Requested amount: ₹${amount.toFixed(2)}`
        );
        return;
    }
    
    // Confirm withdrawal
    if (!confirm(`Withdraw ₹${amount.toFixed(2)}?`)) {
        return;
    }
    
    simulateProcessing(() => {
        const success = ATMBase.currentAccount.withdraw(amount);
        
        if (success) {
            AlertSystem.success(
                `Withdrawal successful!\n` +
                `Amount withdrawn: ₹${amount.toFixed(2)}\n` +
                `Remaining balance: ${ATMBase.currentAccount.getBalanceFormatted()}`
            );
            showWithdrawScreen(); // Refresh screen with new balance
        } else {
            AlertSystem.error('Insufficient balance!');
        }
    });
}

function handleFastCash(amount) {
    if (!ATMBase.currentAccount) {
        AlertSystem.error('No account logged in.');
        return;
    }
    
    if (amount > ATMBase.currentAccount.balance) {
        AlertSystem.error(
            `Insufficient balance!\n` +
            `Available: ${ATMBase.currentAccount.getBalanceFormatted()}`
        );
        return;
    }
    
    // Confirm fast cash withdrawal
    if (!confirm(`Withdraw ₹${amount}?`)) {
        return;
    }
    
    simulateProcessing(() => {
        const success = ATMBase.currentAccount.withdraw(amount);
        
        if (success) {
            AlertSystem.success(
                `Transaction successful!\n` +
                `Amount withdrawn: ₹${amount}\n` +
                `Remaining balance: ${ATMBase.currentAccount.getBalanceFormatted()}`
            );
        } else {
            AlertSystem.error('Transaction failed!');
        }
    });
}

// ============================================
// UTILITY FUNCTIONS
// ============================================

// Format currency
function formatCurrency(amount) {
    return `₹${amount.toFixed(2)}`;
}

// Validate numeric input
function validateNumericInput(input) {
    return /^\d*\.?\d*$/.test(input);
}

// Add input validation for amount fields
document.addEventListener('DOMContentLoaded', () => {
    const amountInputs = ['depositAmount', 'withdrawAmount'];
    
    amountInputs.forEach(inputId => {
        const input = document.getElementById(inputId);
        if (input) {
            input.addEventListener('input', (e) => {
                let value = e.target.value;
                // Allow only numbers and one decimal point
                value = value.replace(/[^0-9.]/g, '');
                // Ensure only one decimal point
                const parts = value.split('.');
                if (parts.length > 2) {
                    value = parts[0] + '.' + parts.slice(1).join('');
                }
                // Limit to 2 decimal places
                if (parts[1] && parts[1].length > 2) {
                    value = parts[0] + '.' + parts[1].substring(0, 2);
                }
                e.target.value = value;
            });
        }
    });
});

// Prevent form submission on Enter key in text inputs
document.querySelectorAll('.atm-input').forEach(input => {
    input.addEventListener('keypress', (e) => {
        if (e.key === 'Enter' && input.type !== 'password') {
            e.preventDefault();
        }
    });
});
