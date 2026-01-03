window.addEventListener("load", async () => {
    let rooms = await getHelper("/gameStart");
    $("#textInput").on("keypress", function(event) {
        if (event.key === "Enter") {
            userSelectOption();
        }
    })
    $("#popup").on("keypress", function(event) {
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
    const input = $("#textInput");
    const error = getFreshErrorDiv();
    if (input.val() === "") {
        if (numberInputOptions.length <= 1) {
            numberInputOptions[0]();
            return;
        }
        error.html("");
        error.append(`<p>You can only execute a default with one button available!</p>`);
        return;
    }
    const selectedOption = parseInt(input.val()) - 1;
    if (selectedOption < 0 || selectedOption > numberInputOptions.length - 1) {
        error.html("");
        error.append(`<p>Out of range!</p>`);
        return;
    }
    if (isNaN(selectedOption)) {
        error.html("");
        error.append(`<p>Enter a number!</p>`)
        return;
    }
    numberInputOptions[selectedOption]();
    input.val("");
}

function createPopup(popupText) {
    const popup = $("#popup");
    popup.removeClass("invisible");
    $("#popupText").text(popupText);
    popup.focus();
}

function hidePopup() {
    $("#popup").addClass("invisible");
    $("#popupText").innerText = "";
}
function inventoryVisible() {
    $("#inventory").removeClass("invisible");
    $("#relics").addClass("invisible");
}

function relicsVisible() {
    $("#inventory").addClass("invisible");
    $("#relics").removeClass("invisible");
}

function playerDeath() {
    const mainDiv = getFreshMainDiv();
    $("#inventory").html("");
    $("#relics").html("");

    mainDiv.append(`<p>You died! Unfortunately, I am not a good programmer, so you must reset the game manually.</p>`);
}

async function printRooms(rooms) {
    let foresightRelicEquipped = await postHelper("/player/relicEquipped", {id: "FORESIGHT"});
    const mainDiv = $("#mainDiv");
    mainDiv.html("");

    numberInputOptions = [];
    for (let i = 0; i < rooms.length; i++) {
        const elementSpan = $("<span></span>")
        const exitsString = `(${rooms[i].numExits} exits)`;
        const textParagraph = $(`<p class="listParagraph">${i+1}. ${rooms[i].appearance} ${((foresightRelicEquipped) ? exitsString : "")}</p>`);
        numberInputOptions[i] = async () => await goToRoom(rooms[i].id);
        const goButton = $(`<button class="clickableButton inlineButton">Go</button>`).click(() => goToRoom(rooms[i].id));
        elementSpan.append(goButton);
        elementSpan.append(textParagraph);
        mainDiv.append(elementSpan);
    }

    await render();
}

async function goToRoom(id) {
    const changeInfo = await postHelper("rooms/change", { id: id });
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
    $("body").css("backgroundImage", `url("textures/${newRoom.backgroundFileName}")`);
    const descriptionDiv = $("#descriptionDiv").html("");
    descriptionDiv.append(`<p>${parseTextAsHTML(newRoom.description)}</p>`);
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

function getFreshMainDiv() {
    const mainDiv = $("#mainDiv");
    mainDiv.html("");
    return mainDiv;
}

function getFreshErrorDiv() {
    const errorDiv = $("#errorDiv");
    errorDiv.html("");
    return errorDiv;
}

async function appendContinue(id) {
    const exits = await postHelper("/rooms/getExits", { id: id });
    const mainDiv = $("#mainDiv");
    const continueButton = $("<button class='clickableButton'>Continue</button>").click(async () => await printRooms(exits));
    numberInputOptions = [async () => await printRooms(exits)];
    mainDiv.append(continueButton);
}

async function itemHandler(room) {
    const mainDiv = $("#mainDiv");
    let pickupSuccess = await itemPickup(room);
    if (!pickupSuccess) {
        $(`<button class="clickableButton">Try Again</button>`)
            .click(async () => {
                await itemHandler(room);
            })
            .appendTo(mainDiv);
    }
}

async function itemPickup(room) {
    const pickupSuccess = await postHelper("/rooms/itemPickup", { id: room.id });
    const mainDiv = getFreshMainDiv();
    if (pickupSuccess) {
        mainDiv.append(`<p>You picked up a ${room.item.name}!</p>`);
    } else {
        mainDiv.append(`<p>Your inventory is full! Drop an item to pick up the ${room.item.name}!</p>`);
    }
    await render();
    await appendContinue(room.id);
    return pickupSuccess;
}

async function relicHandler(room) {
    const mainDiv = $("#mainDiv");
    let pickupSuccess = await relicPickup(room);
    if (!pickupSuccess) {
        $(`<button class="clickableButton">Try Again</button>`)
            .click(async () => {
                await relicHandler(room);
            })
            .appendTo(mainDiv);
    }
}

async function relicPickup(room) {
    const pickupSuccess = await postHelper("/rooms/relicPickup", { id: room.id });
    const mainDiv = getFreshMainDiv();
    if (pickupSuccess) {
        mainDiv.append(`<p>You picked up a ${room.relic.name}!</p>`);
    } else {
        mainDiv.append(`<p>Your inventory is full! Drop an item to pick up the ${room.relic.name}!</p>`);
    }
    await render();
    await appendContinue(room.id);
    return pickupSuccess;
}

async function trapHandler(room) {
    const trapRoomDamageInfo = await postHelper("/rooms/trapDamage", { id: room.id });
    const playerDead = trapRoomDamageInfo.playerDead;
    const damageDealt = trapRoomDamageInfo.damageDealt;
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You took ${damageDealt} damage!</p>`);
    if (playerDead) {
        playerDeath();
        return;
    }
    await renderStats();
    await appendContinue(room.id);
}

async function enemyHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>${room.battleInitiationMessage}</p>`)
    await renderEnemies(room, false);
}

async function renderEnemies(room, reset = true) {
    const enemies = room.enemies;
    const displayEnemyInformation = await postHelper("/player/relicEquipped", {id: "ENEMY_INFORMATION"});
    const mainDiv = reset ? getFreshMainDiv() : $("#mainDiv");

    numberInputOptions = [];
    for (let i = 0; i < enemies.length; i++) {
        const speciesPascal = enemies[i].species.toString().charAt(0).toUpperCase() + enemies[i].species.toString().substring(1, enemies[i].species.toString().length).toLowerCase();
        const elementSpan = $("<span></span>")
        const attackButton = $("<button class='clickableButton inlineButton'>Attack</button>").click(async () => await battleSequence(room, enemies[i]));
        numberInputOptions[i] = async () => await battleSequence(room, enemies[i]);
        elementSpan.append(attackButton);
        if (displayEnemyInformation) {
            $(`<button class='clickableButton inlineButton'>Check</button>`)
                .click(() => {
                    checkEnemy(enemies[i]);
                })
                .appendTo(elementSpan);
        }
        elementSpan.append(`<p class="listParagraph">${i+1}. ${speciesPascal}${(displayEnemyInformation) ? ` (${enemies[i].currentHealth}/${enemies[i].maxHealth} HP)` : ""}</p>`);
        mainDiv.append(elementSpan);
    }
}

//needs work (might need to define this on the class itself for fancier output [like boss attacks and rates], but that DOES suck)
function checkEnemy(enemy) {
    const dDiv = $("#descriptionDiv").html("");
    appendElementText(dDiv, enemy.species);
    appendElementText(dDiv, `HP: ${enemy.currentHealth}/${enemy.maxHealth}`);
    appendElementText(dDiv, `EXP: ${enemy.experienceDropped}`);
    appendElementText(dDiv, `GOLD: ${enemy.loot.gold} G`);
}

function appendElementText(element, text) {
    element.append(`<p class="statusParagraph">${text}</p>`)
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

        mainDiv.append($(`<p>The ${enemyName} gave you ${enemy.experienceDropped} experience!</p>`));
        mainDiv.append($(`<p>The ${enemyName} dropped ${enemyInfo.gold} gold!</p>`));
        for (let i = 0; i < enemyInfo.droppedLoot.length; i++) {
            mainDiv.append($(`<p>The ${enemyName} dropped a ${enemyInfo.droppedLoot[i].name}!</p>`));
        }

        const levelUpString = await postHelper("/player/levelUp", {});
        if (levelUpString !== "") {
            createPopup(levelUpString);
        }

        if (enemyInfo.lastEnemy) {
            mainDiv.append("<p>You win!</p>");
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
    const tickerDiv = $("#statusTicker");
    tickerDiv.append(`<p class="statusParagraph">${text}</p>`);
    const childParagraphs = tickerDiv.find("*");
    if (childParagraphs.length > 5) {
        childParagraphs[0].remove();
    }
}

//refactor to just give room id and in the backend loop and attack the player? maybe (would make status rendering harder)
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
    const statDiv = $("#statusChecker").html("");
    statDiv.append(`<p class="statusParagraph">Level: ${player.level} ${(player.level < 10) ? `(${player.experience}/${player.expToNextLevel})` : ""}</p>`)
    statDiv.append(`<p class="statusParagraph">HP: ${player.currentHealth} ${(player.absorption > 0) ? `(+${player.absorption})` : ""} / ${player.maxHealth}</p>`);
    statDiv.append(`<p class="statusParagraph">Gold: ${player.gold} G</p>`);
    statDiv.append(`<p class="statusParagraph">Attack damage: ${totalDamage}</p>`);
    statDiv.append(`<p class="statusParagraph">Rooms traveled: ${player.roomsTraversed}</p>`);
    statDiv.append(`<p class="statusParagraph">Inventory: ${inventorySize}/${player.inventoryCap}</p>`);
    statDiv.append(`<p class="statusParagraph">Relic pouch: ${player.equippedRelics.length}/${player.relicCap}</p>`)
    appendIfNonzero(player.currentStatuses.cursed, `Cursed: Level ${player.currentStatuses.cursed}`);
    appendIfNonzero(player.currentStatuses.weakened, `Weakened: Level ${player.currentStatuses.weakened}`);
    appendIfNonzero(player.currentStatuses.poison, `Poisoned: Level ${player.currentStatuses.poison}`);
    appendIfNonzero(player.currentStatuses.fire, `Fire: Level ${player.currentStatuses.fire}`);
}

function appendIfNonzero(value, string) {
    const statDiv = $("#statusChecker");
    if (value > 0)
        statDiv.append(`<p class="statusParagraph">${string}</p>`);
}

async function renderInventory() {
    const room = await getHelper("/player/getCurrentRoom");
    const inventory = await getHelper("/player/getInventory");
    const curseDetectionEquipped = await postHelper("/player/relicEquipped", {id: "CURSE_DETECTION"})
    const inventoryDiv = $("#inventory").html("");
    for (let i = 0; i < inventory.length; i++) {
        const item = inventory[i][0];
        let defaultText = `${inventory[i].length}x ${item.name}: ${item.description}`;
        const elementSpan = $("<span></span>");
        elementSpan.appendTo(inventoryDiv);

        if (item.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            $(`<button class="clickableButton inlineButton">Cleanse</button>`)
                .click(async () => {
                    const mainDiv = $("#mainDiv");
                    const itemCleansed = await postHelper("/player/cleanseItem", {uuid: item.uuid});
                    if (itemCleansed)
                        mainDiv.append(`<p>The ${item.name} was cleansed!</p>`);
                    else
                        mainDiv.append(`<p>The ${item.name} couldn't be cleansed!</p>`)
                    mainDiv.append(`<p>The fountain dried up!</p>`);
                    await render();
                })
                .appendTo(elementSpan);
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
            $(`<button class="clickableButton inlineButton">${buttonText}</button>`)
                .click(async () => {
                    const response = await postHelper("/player/useInventoryItem", { uuid: item.uuid });
                    if (response.error) {
                        getFreshErrorDiv().append(`<p>${response.output}</p>`);
                    } else {
                        appendStatusTicker(response.output);
                    }
                    await render();
                })
                .appendTo(elementSpan);
        }

        if (room.type === "SHOP" && room.open) {
            defaultText = `(${item.value} G) ` + defaultText;
            $(`<button class="clickableButton inlineButton">Sell</button>`)
                .click(async () => {
                    const itemSold = await postHelper("/player/sellItem", {uuid: item.uuid});
                    if (itemSold) {
                        await render();
                    } else {
                        getFreshErrorDiv().append(`<p>The item couldn't be sold!</p>`)
                    }
                })
                .appendTo(elementSpan);
        } else {
            $(`<button class="clickableButton inlineButton">Drop</button>`)
                .click(async () => {
                    const itemDropped = await postHelper("/player/dropItem", {uuid: item.uuid});
                    if (itemDropped) {
                        await render();
                    } else {
                        getFreshErrorDiv().append(`<p>The item couldn't be dropped!</p>`)
                    }
                })
                .appendTo(elementSpan);
        }
        const outputParagraph = $(`<p class="listParagraph">${defaultText}</p>`);
        if (item.cursed && curseDetectionEquipped) {
            outputParagraph.addClass("purple");
        }
        elementSpan.append(outputParagraph);
    }
}

async function renderRelics() {
    const room = await getHelper("/player/getCurrentRoom");
    const relics = await getHelper("/player/getRelics");
    const relicDiv = $("#relics").html("");
    for (let i = 0; i < relics.length; i++) {
        const relic = relics[i];
        const elementSpan = $("<span></span>");
        elementSpan.appendTo(relicDiv);
        if (relic.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            $(`<button class="clickableButton inlineButton">Cleanse</button>`)
                .click(async () => {
                    const errorDiv = getFreshErrorDiv()
                    const relicCleansed = await postHelper("/player/cleanseRelic", {uuid: relic.uuid});
                    if (relicCleansed)
                        errorDiv.append(`<p>The ${relic.name} was cleansed!</p>`);
                    else
                        errorDiv.append(`<p>The ${relic.name} couldn't be cleansed!</p>`)
                    await render();
                })
                .appendTo(elementSpan);
        } else {
            $(`<button class="clickableButton inlineButton">Unequip</button>`)
                .click(async () => {
                    const response = await postHelper("/player/unequipRelic", {uuid: relics[i].uuid});
                    if (response.error) {
                        getFreshErrorDiv().append(`<p>${response.output}</p>`);
                    } else {
                        appendStatusTicker(response.output);
                    }
                    await render();
                })
                .appendTo(elementSpan);
        }
        if (relic.cursed) {
            elementSpan.append($(`<p class="purple listParagraph">${relic.name}: ${relic.description}</p>`));
        } else {
            elementSpan.append($(`<p class="listParagraph">${relic.name}: ${relic.description}</p>`));
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
        const elementSpan = $("<span></span>");
        mainDiv.append(elementSpan);
        $(`<button class="clickableButton inlineButton">Buy</button>`)
            .click(async () => {
                const success = await postHelper("/rooms/buyItem", {
                    roomID: room.id,
                    itemID: item.uuid
                });
                if (success) {
                    await renderInventory();
                    await shopRenderer(await getHelper("/player/getCurrentRoom"));
                } else {
                    // todo more advanced error message? e.g. inv full, can't afford
                    getFreshErrorDiv().append("<p>You can't buy that right now!</p>");
                }
            })
            .appendTo(elementSpan);
        elementSpan.append(`<p class="listParagraph">${item.name} (${item.value} G): ${item.description}</p>`);
    }
    await appendContinue(room.id);
    await render();
}

async function fountainHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append("<p>Place an item in the fountain, and/or continue!</p>");
    const selfCleanseButton = $("<button class='clickableButton'>Jump in</button>").click(async () => {
        const outputString = await postHelper("/rooms/cleansePlayer");
        mainDiv.append(`<p>${outputString}</p>`);
    });
    mainDiv.append(selfCleanseButton);
    await appendContinue(room.id);
    await render();
}

async function endingHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append("<p>You win! Good job! <br> But, if you'd like to return and continue... You may.</p>");
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