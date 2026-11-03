const textInput = document.getElementById("textInput");
const popup = document.getElementById("popup");
const popupText = document.getElementById("popupText");
const inventory = document.getElementById("inventoryData");
const relics = document.getElementById("relicData");

window.addEventListener("load", async () => {
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
});

let numberInputOptions = [];

function userSelectOption() {
    //todo post request that sends input to debug command checker. if successful, rerender stuff (if possible) and return
    const input = document.getElementById("textInput");
    const error = getFreshErrorDiv();
    if (input.value === "") {
        if (numberInputOptions.length <= 1) {
            numberInputOptions[0]();
            return;
        }
        error.innerHTML = "";
        const response = document.createElement("p");
        response.textContent = "You can only execute a default with one button available!";
        error.append(response);
        return;
    }
    const selectedOption = parseInt(input.value) - 1;
    if (selectedOption < 0 || selectedOption > numberInputOptions.length - 1) {
        error.innerHTML = "";
        const errorText = document.createElement("p");
        errorText.textContent = "Out of range!";
        error.append(errorText);
        return;
    }
    if (isNaN(selectedOption)) {
        error.innerHTML = "";
        const errorText = document.createElement("p");
        errorText.textContent = "Enter a number!";
        error.append(errorText);
        return;
    }
    numberInputOptions[selectedOption]();
    input.value = "";
}

function createPopup(textValue) {
    popup.removeClass("invisible");
    popupText.textContent = textValue;
    popup.focus();
}

function hidePopup() {
    popup.classList.add("invisible");
    popupText.innerText = "";
}

function inventoryVisible() {
    inventory.classList.remove("invisible");
    relics.classList.add("invisible");
}

function relicsVisible() {
    inventory.classList.add("invisible");
    relics.classList.remove("invisible");
}

function playerDeath() {
    const mainDiv = getFreshMainDiv();
    inventory.innerHTML = "";
    relics.innerHTML = "";

    const deathParagraph = document.createElement("p");
    deathParagraph.textContent = "You died! Reset the game to try again.";
    mainDiv.append(deathParagraph);
    // Todo: actually implement resetting the game.
}

// todo: file split here ------ or maybe above playerDeath

const mainDiv = document.getElementById("mainDiv");
const descriptionDiv = document.getElementById("descriptionDiv");
const errorDiv = document.getElementById("errorDiv");

async function printRooms(rooms) {
    let foresightRelicEquipped = await postHelper("/player/relicEquipped", {id: "FORESIGHT"});
    mainDiv.innerHTML = "";

    numberInputOptions = [];
    for (let i = 0; i < rooms.length; i++) {
        const elementSpan = document.createElement("span");
        const exitsString = `(${rooms[i].numExits} exits)`;
        const textParagraph = document.createElement("p");
        textParagraph.classList.add("listParagraph");
        textParagraph.textContent = `${i+1}. ${rooms[i].appearance} ${((foresightRelicEquipped) ? exitsString : "")}`;
        numberInputOptions[i] = async () => await goToRoom(rooms[i].id);

        const goButton = document.createElement("button");
        goButton.classList.add("clickableButton", "inlineButton");
        goButton.textContent = "Go";
        goButton.addEventListener("click", () => goToRoom(rooms[i].id));
        elementSpan.append(goButton);
        elementSpan.append(textParagraph);
        mainDiv.append(elementSpan);
    }

    await render();
}

async function goToRoom(id) {
    const changeInfo = await postHelper("/rooms/change", { id: id });
    const newRoom = changeInfo.room;
    const roomExits = await postHelper("/rooms/getExits", {id : newRoom.id });
    const statusText = changeInfo.statusText;
    for (let i = 0; i < statusText.length; i++) {
        appendStatusTicker(statusText[i]);
    }
    const relicUseText = changeInfo.relicUseText;
    for (let i = 0; i < relicUseText.length; i++) {
        appendStatusTicker(relicUseText[i]);
    }
    const playerAlive = changeInfo.playerAlive;
    if (!playerAlive) {
        playerDeath();
        return;
    }
    document.body.style.backgroundImage = `url("textures/${newRoom.backgroundFileName}")`;
    descriptionDiv.innerHTML = "";
    const descriptionParagraph = document.createElement("p");
    descriptionParagraph.textContent = parseTextAsHTML(newRoom.description);
    descriptionDiv.append(descriptionParagraph);
    switch (newRoom.type) {
        case "NORMAL":
            await printRooms(roomExits);
            break;
        case "TRAP":
            await trapHandler(newRoom);
            break;
        case "BOSS":
        case "ENEMY":
            await enemyHandler(newRoom);
            break;
        case "RELIC":
            await relicHandler(newRoom);
            break;
        case "ITEM":
            await itemHandler(newRoom);
            break;
        case "FOUNTAIN":
            await fountainHandler(newRoom);
            break;
        case "SHOP":
            await shopHandler(newRoom);
            break;
        case "END":
            await endingHandler(newRoom);
            break;
    }
}

function createParagraph(text = "") {
    const paragraph = document.createElement("p");
    paragraph.textContent = text;
    return paragraph;
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

async function appendContinue(id) {
    const exits = await postHelper("/rooms/getExits", { id: id });
    const continueButton = document.createElement("button");
    continueButton.classList.add("clickableButton");
    continueButton.textContent = "Continue";
    continueButton.addEventListener("click", async () => {
        await printRooms(exits);
    })
    numberInputOptions = [async () => await printRooms(exits)];
    mainDiv.append(continueButton);
}

async function itemHandler(room) {
    let pickupSuccess = await itemPickup(room);
    if (!pickupSuccess) {
        const tryButton = document.createElement("button");
        tryButton.classList.add("clickableButton");
        tryButton.textContent = "Try Again";
        tryButton.addEventListener("click", async () => {
            await itemHandler(room);
        });
        mainDiv.append(tryButton);
    }
}

async function itemPickup(room) {
    const pickupSuccess = await postHelper("/rooms/itemPickup", { id: room.id });
    const mainDiv = getFreshMainDiv();
    const pickupParagraph = document.createElement('p');
    if (pickupSuccess) {
        pickupParagraph.textContent = `You picked up a ${room.item.name}!`;
    } else {
        pickupParagraph.textContent = `Your inventory is full! Drop an item to pick up the ${room.item.name}!`;
    }
    mainDiv.append(pickupParagraph);
    await render();
    await appendContinue(room.id);
    return pickupSuccess;
}

async function relicHandler(room) {
    let pickupSuccess = await relicPickup(room);
    if (!pickupSuccess) {
        const tryButton = document.createElement("button");
        tryButton.classList.add("clickableButton");
        tryButton.textContent = "Try Again";
        tryButton.addEventListener("click", async () => {
            await relicHandler(room);
        });
        mainDiv.append(tryButton);
    }
}

async function relicPickup(room) {
    const pickupSuccess = await postHelper("/rooms/relicPickup", { id: room.id });
    const mainDiv = getFreshMainDiv();
    const pickupParagraph = document.createElement('p');
    if (pickupSuccess) {
        pickupParagraph.textContent = `You picked up a ${room.relic.name}!`;
    } else {
        pickupParagraph.textContent = `Your inventory is full! Drop an item to pick up the ${room.relic.name}!`;
    }
    mainDiv.append(pickupParagraph);
    await render();
    await appendContinue(room.id);
    return pickupSuccess;
}

async function trapHandler(room) {
    const trapRoomDamageInfo = await postHelper("/rooms/trapDamage", { id: room.id });
    const playerDead = trapRoomDamageInfo.playerDead;
    const damageDealt = trapRoomDamageInfo.damageDealt;
    const mainDiv = getFreshMainDiv();
    const damageParagraph = document.createElement("p");
    damageParagraph.textContent = `You took ${damageDealt} damage!`;
    mainDiv.append(damageParagraph);
    if (playerDead) {
        playerDeath();
        return;
    }
    await renderStats();
    await appendContinue(room.id);
}

async function enemyHandler(room) {
    const mainDiv = getFreshMainDiv();
    const battleMessageParagraph = document.createElement("p");
    battleMessageParagraph.textContent = room.battleInitiationMessage;
    mainDiv.append(battleMessageParagraph);
    await renderEnemies(room, false);
}

async function renderEnemies(room, reset = true) {
    const enemies = room.enemies;
    const displayEnemyInformation = await postHelper("/player/relicEquipped", {id: "ENEMY_INFORMATION"});
    if (reset) getFreshMainDiv();

    numberInputOptions = [];
    for (let i = 0; i < enemies.length; i++) {
        const speciesPascal = enemies[i].species.toString().charAt(0).toUpperCase() + enemies[i].species.toString().substring(1, enemies[i].species.toString().length).toLowerCase();
        const elementSpan = document.createElement("span");
        const attackButton = document.createElement("button");
        attackButton.classList.add("clickableButton", "inlineButton");
        attackButton.textContent = "Attack";
        attackButton.addEventListener("click", async () =>
            await battleSequence(room, enemies[i]));
        numberInputOptions[i] = async () => await battleSequence(room, enemies[i]);
        elementSpan.append(attackButton);
        if (displayEnemyInformation) {
            const checkButton = document.createElement("button");
            checkButton.classList.add("clickableButton", "inlineButton");
            checkButton.textContent = "Check";
            checkButton.addEventListener("click", () => {
                checkEnemy(enemies[i]);
            });
            elementSpan.append(checkButton);
        }
        const enemyParagraph = document.createElement("p");
        enemyParagraph.classList.add("listParagraph");
        // Holy moly
        enemyParagraph.textContent = `${i+1}. ${speciesPascal}${(displayEnemyInformation) ? ` (${enemies[i].currentHealth}/${enemies[i].maxHealth} HP)` : ""}`;
        elementSpan.append(enemyParagraph);
        mainDiv.append(elementSpan);
    }
}

//needs work (might need to define this on the class itself for fancier output [like boss attacks and rates], but that DOES suck)
function checkEnemy(enemy) {
    descriptionDiv.innerHTML = "";
    appendElementText(descriptionDiv, enemy.species);
    appendElementText(descriptionDiv, `HP: ${enemy.currentHealth}/${enemy.maxHealth}`);
    appendElementText(descriptionDiv, `EXP: ${enemy.experienceDropped}`);
    appendElementText(descriptionDiv, `GOLD: ${enemy.loot.gold} G`);
}

function appendElementText(element, text) {
    const statusParagraph = document.createElement("p");
    statusParagraph.classList.add("statusParagraph");
    statusParagraph.textContent = text;
    element.append(statusParagraph);
}

async function battleSequence(room, enemy) {
    const enemyInfo = await postHelper("/enemy/takeDamage", {
        roomID: room.id,
        uuid: enemy.uuid
    });
    enemy = enemyInfo.enemy;
    if (enemyInfo.dead) {
        const mainDiv = getFreshMainDiv();
        const enemyName = enemy.species.toString().toLowerCase();

        mainDiv.append(createParagraph(`The ${enemyName} gave you ${enemy.experienceDropped} experience!`));
        mainDiv.append(createParagraph(`The ${enemyName} dropped ${enemyInfo.gold} gold!`));
        for (let i = 0; i < enemyInfo.droppedLoot.length; i++) {
            mainDiv.append(createParagraph(`The ${enemyName} dropped a ${enemyInfo.droppedLoot[i].name}!`));
        }

        const levelUpString = await postHelper("/player/levelUp", {});
        if (levelUpString !== "") {
            createPopup(levelUpString);
        }

        if (enemyInfo.lastEnemy) {
            mainDiv.append(createParagraph("You win!"));
            await appendContinue(room.id);
            return;
        }
    }

    // Necessary so enemies aren't null
    room = await getHelper("/player/getCurrentRoom")

    const playerDied = await enemiesAttackPlayer(room);
    if (playerDied) {
        playerDeath();
        return;
    }
    await render();
    await renderEnemies(room);
}

function appendStatusTicker(text) {
    const tickerDiv = document.getElementById("statusTicker");
    const statusParagraph = createParagraph(text);
    statusParagraph.classList.add("statusParagraph");
    tickerDiv.append(statusParagraph);
    const childParagraphs = tickerDiv.children;
    if (childParagraphs.length > 5) {
        childParagraphs[0].remove();
    }
}

//todo refactor to just give room id and in the backend loop and attack the player? maybe (would make status rendering harder)
// could have /enemy/attack return an array of strings instead and loop through that and append?
// would decrease render time, too
async function enemiesAttackPlayer(room) {
    for (let i = 0; i < room.enemies.length; i++) {
        const statusInfo = await postHelper("/enemy/attack", {
            roomID: room.id,
            uuid: room.enemies[i].uuid
        });
        appendStatusTicker(statusInfo.attackString);
        if (statusInfo.playerDead) {
            return true;
        }
    }
    return false;
}

async function render() {
    await renderInventory();
    await renderRelics();
    await renderStats();
}

async function renderStats() {
    const player = await getHelper("/player/getPlayer");
    const totalDamage = await getHelper("/player/getTotalDamage");
    const inventorySize = await getHelper("/player/getInventorySize");
    const statDiv = document.getElementById("statusChecker");
    statDiv.innerHTML = "";
    statDiv.append(createStatusParagraph(`Level: ${player.level} ${(player.level < 10) ? `(${player.experience}/${player.expToNextLevel})` : ""}`))
    statDiv.append(createStatusParagraph(`HP: ${player.currentHealth} ${(player.absorption > 0) ? `(+${player.absorption})` : ""} / ${player.maxHealth}`));
    statDiv.append(createStatusParagraph(`Gold: ${player.gold} G`));
    statDiv.append(createStatusParagraph(`Attack damage: ${totalDamage}`));
    statDiv.append(createStatusParagraph(`Rooms traveled: ${player.roomsTraversed}`));
    statDiv.append(createStatusParagraph(`Inventory: ${inventorySize}/${player.inventoryCap}`));
    statDiv.append(createStatusParagraph(`Relic pouch: ${player.equippedRelics.length}/${player.relicCap}`));
    appendIfNonzero(player.currentStatuses.cursed, `Cursed: Level ${player.currentStatuses.cursed}`);
    appendIfNonzero(player.currentStatuses.weakened, `Weakened: Level ${player.currentStatuses.weakened}`);
    appendIfNonzero(player.currentStatuses.poison, `Poisoned: Level ${player.currentStatuses.poison}`);
    appendIfNonzero(player.currentStatuses.fire, `Fire: Level ${player.currentStatuses.fire}`);

    function createStatusParagraph(text) {
        const statusParagraph = createParagraph(text);
        statusParagraph.classList.add("statusParagraph");
        return statusParagraph;
    }

    function appendIfNonzero(value, string) {
        if (value > 0)
            statDiv.append(createStatusParagraph(string));
    }
}

async function renderInventory() {
    const room = await getHelper("/player/getCurrentRoom");
    inventory.innerHTML = "";
    const inventoryData = await getHelper("/player/getInventory");
    const curseDetectionEquipped = await postHelper("/player/relicEquipped", {id: "CURSE_DETECTION"})

    for (let i = 0; i < inventoryData.length; i++) {
        const item = inventoryData[i][0];
        let defaultText = `${inventoryData[i].length}x ${item.name}: ${item.description}`;
        const elementSpan = document.createElement("div");
        inventory.append(elementSpan);

        if (item.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            // make me a function?
            const cleanseButton = document.createElement("button");
            cleanseButton.classList.add("clickableButton", "inlineButton");
            cleanseButton.textContent = "Cleanse";
            cleanseButton.addEventListener("click", async () => {
                const itemCleansed = await postHelper("/player/cleanseItem", {uuid: item.uuid});
                if (itemCleansed)
                    mainDiv.append(createParagraph(`The ${item.name} was cleansed!`));
                else
                    mainDiv.append(createParagraph(`The ${item.name} couldn't be cleansed!`));
                mainDiv.append(createParagraph(`The fountain dried up!`));
                await render();
            });
            elementSpan.append(cleanseButton);
        } else {
            let buttonText = "Use";
            if (item.type === "WEAPON") {
                if (item.equipped) {
                    buttonText = "Unequip";
                } else {
                    buttonText = "Equip";
                }
            }
            if (item.type === "RELIC") {
                buttonText = "Equip";
            }
            const useButton = document.createElement("button");
            useButton.classList.add("clickableButton", "inlineButton");
            useButton.textContent = buttonText;
            useButton.addEventListener("click", async () => {
                const response = await postHelper("/player/useInventoryItem", { uuid: item.uuid });
                if (response.error) {
                    getFreshErrorDiv().append(createParagraph(`response.output`));
                } else {
                    appendStatusTicker(response.output);
                }
                await render();
            });
            elementSpan.append(useButton);
        }

        if (room.type === "SHOP" && room.open) {
            defaultText = `(${item.value} G) ` + defaultText;
            const sellButton = document.createElement("button");
            sellButton.classList.add("clickableButton", "inlineButton");
            sellButton.textContent = "Sell";
            sellButton.addEventListener("click", async () => {
                const itemSold = await postHelper("/player/sellItem", {uuid: item.uuid});
                if (itemSold) {
                    await render();
                } else {
                    getFreshErrorDiv().append(createParagraph(`The item couldn't be sold!`));
                }
            });
            elementSpan.append(sellButton);
        } else {
            const dropButton = document.createElement("Button");
            dropButton.classList.add("clickableButton", "inlineButton");
            dropButton.textContent = "Drop";
            dropButton.addEventListener("click", async () => {
                const itemDropped = await postHelper("/player/dropItem", {uuid: item.uuid});
                if (itemDropped) {
                    await render();
                } else {
                    getFreshErrorDiv().append(createParagraph(`The item couldn't be dropped!`));
                }
            });
            elementSpan.append(dropButton);
        }
        const outputParagraph = createParagraph(defaultText);
        outputParagraph.classList.add("listParagraph");
        if (item.cursed && curseDetectionEquipped) {
            outputParagraph.addClass("purple");
        }
        elementSpan.append(outputParagraph);
    }
}

async function renderRelics() {
    const room = await getHelper("/player/getCurrentRoom");
    const relicData = await getHelper("/player/getRelics");
    for (let i = 0; i < relicData.length; i++) {
        const relic = relicData[i];
        const elementSpan = document.createElement("span");
        relics.append(elementSpan);
        if (relic.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            const cleanseButton = document.createElement("button");
            cleanseButton.classList.add("clickableButton", "inlineButton");
            cleanseButton.textContent = "Cleanse";
            cleanseButton.addEventListener("click", async () => {
                const errorDiv = getFreshErrorDiv()
                const relicCleansed = await postHelper("/player/cleanseRelic", {uuid: relic.uuid});
                if (relicCleansed)
                    errorDiv.append(createParagraph(`The ${relic.name} was cleansed!`));
                else
                    errorDiv.append(createParagraph(`The ${relic.name} couldn't be cleansed!`));
                await render();
            });
            elementSpan.append(cleanseButton);
        } else {
            const unequipButton = document.createElement("button");
            unequipButton.classList.add("clickableButton", "inlineButton");
            unequipButton.textContent = "Unequip";
            unequipButton.addEventListener("click", async () => {
                const response = await postHelper("/player/unequipRelic", {uuid: relicData[i].uuid});
                if (response.error) {
                    getFreshErrorDiv().append(createParagraph(response.output));
                } else {
                    appendStatusTicker(response.output);
                }
                await render();
            });
            elementSpan.append(unequipButton);
        }
        const relicParagraph = createParagraph(`${relic.name}: ${relic.description}`);
        relicParagraph.classList.add("lightParagraph");
        elementSpan.append(relicParagraph);
        if (relic.cursed) {
            relicParagraph.classList.add("purple");
        }
    }
}

async function shopHandler(room) {
    await shopRenderer(room);
}

//todo can't buy items by text, only continue (maybe good thing?)
async function shopRenderer(room) {
    const wares = room.wares;
    const mainDiv = getFreshMainDiv();
    for (let i = 0; i < wares.length; i++) {
        const item = wares[i];
        const elementSpan = document.createElement("span");
        mainDiv.append(elementSpan);
        const buyButton = document.createElement("button");
        buyButton.classList.add("clickableButton", "inlineButton");
        buyButton.textContent = "Buy";
        buyButton.addEventListener("click", async () => {
            const success = await postHelper("/rooms/buyItem", {
                roomID: room.id,
                itemID: item.uuid
            });
            if (success) {
                await renderInventory();
                await shopRenderer(await getHelper("/player/getCurrentRoom"));
            } else {
                // todo more advanced error message? e.g. inv full, can't afford
                getFreshErrorDiv().append(createParagraph("You can't buy that right now!"));
            }
        });
        elementSpan.append(buyButton);
        const itemParagraph = createParagraph(`${item.name} (${item.value} G): ${item.description}`);
        itemParagraph.classList.add("listParagraph");
        elementSpan.append(itemParagraph);
    }
    await appendContinue(room.id);
    await render();
}

async function fountainHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(createParagraph("Place an item in the fountain, and/or continue!"));
    const selfCleanseButton = document.createElement("button");
    selfCleanseButton.classList.add("clickableButton");
    selfCleanseButton.textContent = "Jump in";
    selfCleanseButton.addEventListener("click", async () => {
        const outputString = await postHelper("/rooms/cleansePlayer");
        mainDiv.append(createParagraph(outputString));
    });
    mainDiv.append(selfCleanseButton);
    await appendContinue(room.id);
    await render();
}

async function endingHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(createParagraph("You win! Good job! <br> But, if you'd like to return and continue... You may."));
    await appendContinue(room.id);
}

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