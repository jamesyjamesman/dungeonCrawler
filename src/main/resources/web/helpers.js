async function postHelper(path, json){
    const response = await fetch(path, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json)
    });
    // if (response.status === 403) {
    //     playerDeath();
    // }
    if (response.ok) {
        return await response.json();
    }
    throw new Error();
}

async function getHelper(path) {
    const response = await fetch(path, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    // if (response.status === 403) {
    //     playerDeath();
    // }
    if (response.ok) {
        return await response.json();
    }
    throw new Error();
}

function parseTextAsHTML(text) {
    return text.replaceAll("\n", "<br>");
}

// Why does it... return it?
function getFreshMainDiv() {
    mainDiv.innerHTML = "";
    return mainDiv;
}

function getFreshErrorDiv() {
    errorDiv.innerHTML = "";
    return errorDiv;
}

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.substring(1).toLowerCase();
}

async function useItemHelper(item) {
    const response = await postHelper("/player/useInventoryItem", { uuid: item.uuid });
    item = response.item;
    const result = {
        error: false,
        output: ""
    };

    switch (response.useCase) {
        case "HEALTH": {
            result.output = `The ${item.name} healed you for ${item.healthRestored} health!`;
            break;
        } case "NO_HEALTH": {
            result.output = `You ate the ${item.name}, but nothing happened.`;
            break;
        } case "NEGATIVE_HEALTH": {
            result.output = `Yuck! That ${item.name} was gross... You lost ${item.healthRestored} health!`;
            break;
        } case "BUFF": {
            if (item.statName === "poison") {
                if (item.amountChanged > 0)
                    result.output = `Gross! That... was not worth it. You gained ${item.amountChanged} levels of poison!`;
                else
                    result.output = `That was disgusting!`;
            } else {
                if (item.amountChanged > 0)
                    result.output = `You force the contents of the can down your throat... Somehow, you gained ${item.amountChanged} ${item.statName}!`;
                else
                    result.output = "Drinking that... Wasn't worth it.";
            }
            break;
        } default: {
            result.output = `An error occurred while using the ${item.name} with a use case of ${response.useCase}!`;
            result.error = true;
        }
    }

    // This is still bad but better than doing it thrice I guess
    if (item.addedAbsorption > 0)
        result.output += ` It also gave you ${item.addedAbsorption} absorption!`;

    return result;
}

async function equipHelper(item) {
    const response = await postHelper("/player/equip", {uuid: item.uuid});
    item = response.item;
    const result = {
        error: false,
        output: ""
    };

    switch (response.useCase) {
        case "ALREADY_EQUIPPED": {
            result.output = `The ${item.name} could not be equipped, because there is already a ${item.type.toLowerCase()} equipped!`;
            result.error = true;
            break;
        } case "POUCH_FULL": {
            result.output = "Your relic pouch is full!";
            result.error = true;
            break;
        } case "EQUIPPED": {
            if (item.cursed)
                result.output = `The ${item.name} was equipped, but it was cursed!`;
            else
                result.output = `The ${item.name} was equipped!`;
            break;
        } default:
            result.output = `An error occurred while using the ${item.name} with a use case of ${response.useCase}!`;
            result.error = true;
    }
    return result;
}

async function unequipHelper(item) {
    const response = await postHelper("/player/unequip", {uuid: item.uuid});
    item = response.item;
    const result = {
        error: false,
        output: ""
    };

    switch (response.useCase) {
        case "UNEQUIPPED": {
            result.output = `The ${item.name} was unequipped!`;
            break;
        } case "INVENTORY_FULL": {
            result.output = "Your inventory is full!";
            result.error = true;
            break;
        } case "INVENTORY_OVERFLOW": {
            result.output = `You have too many items to unequip your ${item.name}!`;
            result.error = true;
            break;
        } case "POUCH_OVERFLOW": {
            result.output = `You have too many relics equipped to unequip your ${item.name}!`;
            result.error = true;
            break;
        } case "RELIC_CURSED": {
            result.output = "You cannot unequip a cursed relic!";
            result.error = true;
            break;
        } default: {
            result.output = `An error occurred while using the ${item.name} with a use case of ${response.useCase}!`;
            result.error = true;
        }
    }
    return result;
}