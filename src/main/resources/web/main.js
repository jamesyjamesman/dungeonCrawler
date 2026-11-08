const textInput = document.getElementById("prompt");
const popup = document.getElementById("popup");
const popupText = document.getElementById("popupText");
const armor = document.getElementById("armorItems");
const weapons = document.getElementById("weaponItems");
const inventory = document.getElementById("genericItems");
const relics = document.getElementById("relicItems");
const mainDiv = document.getElementById("navigation");
const descriptionDiv = document.getElementById("description");
const errorDiv = document.getElementById("error");
const inventoryButton = document.getElementById("genericButton");
const relicButton = document.getElementById("relicButton");
const armorButton = document.getElementById("armorButton");
const weaponButton = document.getElementById("weaponButton");

initiate().then();

async function initiate() {
    let rooms = await getHelper("/gameStart");

    textInput.addEventListener("keypress", (event) => {
        if (event.key === "Enter") {
            userSelectOption();
        }
    })
    popup.addEventListener("keypress", (event) => {
        if (event.key === "Enter") {
            hidePopup();
        }
    })
    await printRooms(rooms);
    await render();
}

let numberInputOptions = [];

function userSelectOption() {
    //todo post request that sends input to debug command checker. if successful, rerender stuff (if possible) and return
    if (textInput.value === "") {
        if (numberInputOptions.length <= 1) {
            numberInputOptions[0]();
            return;
        }
        errorMessage("You can only execute a default with one button available!");
        return;
    }
    const selectedOption = parseInt(textInput.value) - 1;
    if (selectedOption < 0 || selectedOption > numberInputOptions.length - 1) {
        errorMessage("Selection out of range!");
        return;
    }
    if (isNaN(selectedOption)) {
        errorMessage("Enter a number!");
        return;
    }
    numberInputOptions[selectedOption]();
    textInput.value = "";
}

function createPopup(textValue) {
    popup.classList.remove("invisible");
    popupText.textContent = textValue;
    popup.focus();
}

function hidePopup() {
    popup.classList.add("invisible");
    popupText.innerText = "";
    document.getElementById("textInput").focus();
}

function inventoryVisible() {
    inventoryButton.classList.add("buttonSelected");
    relicButton.classList.remove("buttonSelected");
    inventory.classList.remove("invisible");
    relics.classList.add("invisible");
}

function relicsVisible() {
    relicButton.classList.add("buttonSelected");
    inventoryButton.classList.remove("buttonSelected");
    inventory.classList.add("invisible");
    relics.classList.remove("invisible");
}