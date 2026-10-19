const namesUl = document.querySelector("ul#users");
const timerInSecDiv = /** @type {htmlElement} */ (document.querySelector("time#timer"));
let timerInSec = /** @type {string} */ (timerInSecDiv.innerText);

let current_timer = parseInt(timerInSec);
const currentUserDiv = document.querySelector("main div#current_user");

const namesLi = namesUl.querySelectorAll("li");

const userNames = [...namesLi].map(el => el.innerText);
currentUserDiv.innerText = userNames[0];

const isTimerActive = true;

let interval;

function startTimer() {
    interval = setInterval(() => {
        current_timer--;

        timerInSecDiv.innerText = current_timer + "s";

        if (current_timer < 0) {
            nextUser();
            current_timer = timerInSec;
        }

    }, 1000);
}

function toggleTimer() {
    isTimerActive = !isTimerActive;

    if (isTimerActive) {
        startTimer();
    } else {
        clearInterval(interval);
    }
}

function nextUser() {
    currentUserDiv.innerText = userNames[userNames.indexOf(currentUserDiv.innerText) + 1] ?? userNames[0];
}
