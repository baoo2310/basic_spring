document.addEventListener('DOMContentLoaded', function() {
    // Get DOM elements
    const balanceDisplay = document.getElementById('balance');
    const depositAmount = document.getElementById('deposit-amount');
    const depositBtn = document.getElementById('deposit-btn');
    const withdrawAmount = document.getElementById('withdraw-amount');
    const withdrawBtn = document.getElementById('withdraw-btn');
    const currencySelect = document.getElementById('currency-select');
    const notificationSelect = document.getElementById('notification-select');
    const notificationList = document.getElementById('notification-list');
    
    // Current notification channel
    let currentNotificationChannel = 1; // Default: Email
    
    // Fetch initial balance
    fetchBalance();
    
    // Event listeners
    depositBtn.addEventListener('click', handleDeposit);
    withdrawBtn.addEventListener('click', handleWithdraw);
    currencySelect.addEventListener('change', handleCurrencyChange);
    notificationSelect.addEventListener('change', handleNotificationChange);
    
    // Functions
    function fetchBalance() {
        fetch('/api/bank/balance')
            .then(response => response.json())
            .then(data => {
                balanceDisplay.textContent = data.formattedBalance;
            })
            .catch(error => {
                console.error('Error fetching balance:', error);
                balanceDisplay.textContent = 'Error loading balance';
            });
    }
    
    function handleDeposit() {
        const amount = parseFloat(depositAmount.value);
        if (isNaN(amount) || amount <= 0) {
            addNotification('Please enter a valid amount to deposit', 'error');
            return;
        }
        
        fetch(`/api/bank/deposit?amount=${amount}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                balanceDisplay.textContent = data.formattedBalance;
                depositAmount.value = '';
                addNotification(`You deposited ${data.amount}`, currentNotificationChannel);
            })
            .catch(error => {
                console.error('Error during deposit:', error);
                addNotification('Error processing deposit', 'error');
            });
    }
    
    function handleWithdraw() {
        const amount = parseFloat(withdrawAmount.value);
        if (isNaN(amount) || amount <= 0) {
            addNotification('Please enter a valid amount to withdraw', 'error');
            return;
        }
        
        fetch(`/api/bank/withdraw?amount=${amount}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                balanceDisplay.textContent = data.formattedBalance;
                withdrawAmount.value = '';
                
                if (data.success) {
                    addNotification(`You withdrew ${data.amount}`, currentNotificationChannel);
                } else {
                    addNotification(`Insufficient funds for withdrawal of ${data.amount}`, currentNotificationChannel);
                }
            })
            .catch(error => {
                console.error('Error during withdrawal:', error);
                addNotification('Error processing withdrawal', 'error');
            });
    }
    
    function handleCurrencyChange() {
        const currencyCode = currencySelect.value;
        
        fetch(`/api/bank/currency?currencyCode=${currencyCode}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                balanceDisplay.textContent = data.formattedBalance;
                addNotification(`Currency changed to ${currencyCode}`, currentNotificationChannel);
            })
            .catch(error => {
                console.error('Error changing currency:', error);
                addNotification('Error changing currency', 'error');
            });
    }
    
    function handleNotificationChange() {
        const channel = parseInt(notificationSelect.value);
        currentNotificationChannel = channel;
        
        fetch(`/api/bank/notification?channel=${channel}`, {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                const channelName = channel === 1 ? 'Email' : 'SMS';
                addNotification(`Notification channel changed to ${channelName}`, channel);
            })
            .catch(error => {
                console.error('Error changing notification channel:', error);
                addNotification('Error changing notification channel', 'error');
            });
    }
    
    function addNotification(message, type) {
        const notification = document.createElement('div');
        notification.classList.add('notification');
        
        if (type === 1) {
            notification.classList.add('email');
            notification.textContent = `[EMAIL] ${message}`;
        } else if (type === 2) {
            notification.classList.add('sms');
            notification.textContent = `[SMS] ${message}`;
        } else {
            notification.classList.add('error');
            notification.textContent = message;
        }
        
        const timeSpan = document.createElement('span');
        timeSpan.classList.add('notification-time');
        timeSpan.textContent = new Date().toLocaleTimeString();
        notification.appendChild(timeSpan);
        
        notificationList.insertBefore(notification, notificationList.firstChild);
        
        // Limit notifications to 10
        if (notificationList.children.length > 10) {
            notificationList.removeChild(notificationList.lastChild);
        }
    }
});