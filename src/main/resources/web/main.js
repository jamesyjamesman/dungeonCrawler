const textInput = document.getElementById("prompt");
const popup = document.getElementById("popup");
const popupText = document.getElementById("popupText");
const armor = document.getElementById("armorItems");
const weapons = document.getElementById("weaponItems");
const inventory = document.getElementById("genericItems");
const relics = document.getElementById("relicItems");
const mainDiv = document.getElementById("navigation");
const descriptionDiv = document.getElementById("description");
const inventoryButton = document.getElementById("genericButton");
inventoryButton.parentElement.addEventListener("click", showInventory);
const relicButton = document.getElementById("relicButton");
relicButton.parentElement.addEventListener("click", showRelics);
const armorButton = document.getElementById("armorButton");
armorButton.parentElement.addEventListener("click", showArmor);
const weaponButton = document.getElementById("weaponButton");
weaponButton.parentElement.addEventListener("click", showWeapons);

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
    textInput.focus();
}

function showInventory() {
    inventoryTabToggle(inventoryButton, inventory);
}

function showRelics() {
    inventoryTabToggle(relicButton, relics);
}

function showWeapons() {
    inventoryTabToggle(weaponButton, weapons);
}

function showArmor() {
    inventoryTabToggle(armorButton, armor);
}

function inventoryTabToggle(button, element) {
    const buttons = [inventoryButton, relicButton, weaponButton, armorButton];
    const divs = [inventory, relics, weapons, armor];
    buttons.splice(buttons.indexOf(button), 1);
    divs.splice(divs.indexOf(element), 1);
    disable(buttons, divs);
    button.parentElement.classList.add("buttonSelected");
    element.parentElement.classList.remove("disabled");
}

function disable(buttons, elements) {
    for (const button of buttons) {
        button.parentElement.classList.remove("buttonSelected");
    }
    for (const element of elements) {
        element.parentElement.classList.add("disabled");
    }
}