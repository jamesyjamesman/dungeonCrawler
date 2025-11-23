window.addEventListener("load", async () => {
    let rooms = await getHelper("/gameStart");
    createPopup("Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");
    $("#textInput").on("keypress", function(event) {
        if (event.key === "Enter") {
            userSelectOption();
        }
    })
    await printRooms(rooms);
    await render();
});

let numberInputOptions = [];

function userSelectOption() {
    //todo post request that sends input to debug command checker. if successful, rerender stuff (if possible) and return
    const input = $("#textInput");
    const error = $("#errorDiv");
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
    $("#popup").removeClass("invisible");
    $("#popupText").text(popupText);
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

async function printRooms(rooms) {
    let foresightRelicEquipped = await postHelper("/player/relicEquipped", {id: "FORESIGHT"});
    const mainDiv = $("#mainDiv");
    mainDiv.html("");

    numberInputOptions = [];
    for (let i = 0; i < rooms.length; i++) {
        const exitsString = `(${rooms[i].numExits} exits)`;
        const textParagraph = $(`<p>${rooms[i].appearance} ${((foresightRelicEquipped) ? exitsString : "")}</p>`);
        numberInputOptions[i] = async () => await goToRoom(rooms[i].id);
        const goButton = $(`<button>Go</button>`).click(() => goToRoom(rooms[i].id));
        mainDiv.append(goButton);
        mainDiv.append(textParagraph);
    }

    await render();
}

async function goToRoom(id) {
    let newRoom = await postHelper("rooms/change", { id: id });
    const descriptionDiv = $("#descriptionDiv").html("");
    descriptionDiv.append(`<p>${parseTextAsHTML(newRoom.description)}</p>`);
    const roomExits = await postHelper("/rooms/getExits", {id : newRoom.id });
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
        case "SHOP": //todo
        case "END": //todo
            await printRooms(roomExits);
    }
}

function getFreshMainDiv() {
    const mainDiv = $("#mainDiv");
    mainDiv.html("");
    return mainDiv;
}

async function appendContinue(id) {
    const mainDiv = $("#mainDiv");
    const exits = await postHelper("/rooms/getExits", { id: id });
    const continueButton = $("<button>Continue</button>").click(async () => await printRooms(exits));
    numberInputOptions = [async () => await printRooms(exits)];
    mainDiv.append(continueButton);
}

async function itemHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You picked up a ${room.item.name}!</p>`);
    await render();
    await appendContinue(room.id);
}

async function relicHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You picked up a ${room.relic.name}!</p>`);
    await render();
    await appendContinue(room.id);
}

async function trapHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You took ${room.damageDealt} damage!</p>`);
    await renderStats();
    await appendContinue(room.id);
}

async function enemyHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>${room.battleInitiationMessage}</p>`)
    await renderEnemies(room);
}

async function renderEnemies(room) {
    const enemies = await postHelper("/rooms/getEnemies", {id: room.id});
    const mainDiv = $("#mainDiv");

    numberInputOptions = [];
    for (let i = 0; i < enemies.length; i++) {
        const attackButton = $("<button>Attack</button>").click(async () => await battleSequence(room, enemies[i]));
        numberInputOptions[i] = async () => await battleSequence(room, enemies[i]);
        mainDiv.append(attackButton);
        mainDiv.append(`<p>${i}. ${enemies[i].species}</p>`);
    }
}

async function battleSequence(room, enemy) {
    const mainDiv = getFreshMainDiv();
    const enemyInfo = await postHelper("/enemy/takeDamage", {
        roomID: room.id,
        uuid: enemy.uuid
    });
    enemy = enemyInfo.enemy;
    const deathString = enemyInfo.deathString;
    if (enemy.currentHealth <= 0) {
        mainDiv.append(`<p>${deathString}</p>`);

        //this... is bad. basically checks if that was the last one.
        if (room.enemies.length === 1) {
            mainDiv.append("<p>You win!</p>");
            await appendContinue(room.id);
            //todo figure out how to not have this call
            await postHelper("/rooms/resetEnemies", {id: room.id});
            return;
        }
    }

    await enemiesAttackPlayer(room);
    await renderStats();
    await renderEnemies(room);
}

function appendStatusTicker(text) {
    const tickerDiv = $("#statusTicker");
    tickerDiv.append(text);
    const childParagraphs = tickerDiv.find("*");
    if (childParagraphs.length > 5) {
        childParagraphs[0].remove();
    }
}

async function enemiesAttackPlayer(room) {
    for (let i = 0; i < room.enemies.length; i++) {
        const statusInfo = await postHelper("/enemy/attack", {
            roomID: room.id,
            uuid: room.enemies[i].uuid
        });
        appendStatusTicker(`<p>${statusInfo}</p>`);
    }
}

async function render() {
    await renderInventory();
    await renderRelics();
    await renderStats();
}

async function renderStats() {
    const player = await getHelper("/player/getPlayer");
    const statDiv = $("#statusChecker").html("");
    statDiv.append(`<p>HP: ${player.currentHealth} ${(player.absorption > 0) ? `(+${player.absorption})` : ""} / ${player.maxHealth}</p>`);
    statDiv.append(`<p>Attack damage: ${await getHelper("/player/getTotalDamage")}</p>`);
    statDiv.append(`<p>Rooms traveled: ${player.roomsTraversed}</p>`);
    statDiv.append(`<p>Inventory: ${await getHelper("/player/getInventorySize")}/${player.inventoryCap}</p>`);
    statDiv.append(`<p>Relic pouch: ${player.equippedRelics.length}/${player.relicCap}</p>`)
    appendIfNonzero(player.currentStatuses.cursed, `Cursed: Level ${player.currentStatuses.cursed}`);
    appendIfNonzero(player.currentStatuses.weakened, `Weakened: Level ${player.currentStatuses.weakened}`);
    appendIfNonzero(player.currentStatuses.poison, `Poisoned: Level ${player.currentStatuses.poison}`);
    appendIfNonzero(player.currentStatuses.fire, `Fire: Level ${player.currentStatuses.fire}`);
}

function appendIfNonzero(value, string) {
    const statDiv = $("#statusChecker");
    if (value > 0)
        statDiv.append(`<p>${string}</p>`);
}

async function renderInventory() {
    const room = await getHelper("/player/getCurrentRoom");
    const inventoryDiv = $("#inventory").html("");
    const inventory = await getHelper("/player/getInventory");
    for (let i = 0; i < inventory.length; i++) {
        const item = inventory[i][0];

        if (item.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            $(`<button>Cleanse</button>`)
                .click(async () => {
                    const errorDiv = $("#errorDiv")
                    const itemCleansed = await postHelper("/player/cleanseItem", {uuid: item.uuid});
                    if (itemCleansed)
                        errorDiv.append(`<p>The ${item.name} was cleansed!</p>`);
                    else
                        errorDiv.append(`<p>The ${item.name} couldn't be cleansed!</p>`)
                    await render();
                })
                .appendTo(inventoryDiv);
        } else {
            let buttonText = "Use";
            if (item.type === "WEAPON") {
                if (item.equipped) {
                    buttonText = "Unequip";
                } else {
                    buttonText = "Equip";
                }
            }
            $(`<button>${buttonText}</button>`)
                .click(async () => {
                    const itemUseText = await postHelper("/player/useInventoryItem", { uuid: item.uuid });
                    const statusTicker = $("#statusTicker");
                    statusTicker.append(`<p>${itemUseText}</p>`);
                    await render();
                })
                .appendTo(inventoryDiv);
        }
        if (item.cursed && await postHelper("/player/relicEquipped", {id: "CURSE_DETECTION"})) {
            inventoryDiv.append($(`<p class="purple">${inventory[i].length}x ${item.name}: ${item.description}</p>`));
        } else {
            inventoryDiv.append($(`<p>${inventory[i].length}x ${item.name}: ${item.description}</p>`));
        }
    }
}

async function renderRelics() {
    const relicDiv = $("#relics").html("");
    const relics = await getHelper("/player/getRelics");
    for (let i = 0; i < relics.length; i++) {
        $(`<button>Use</button>`)
            .click(async () => {
                await postHelper("/player/unequipRelic", { uuid: relics[i].uuid })
                await render();
            })
            .appendTo(relicDiv);
        relicDiv.append($(`<p>${relics[i].name}: ${relics[i].description}`))
    }
}

async function fountainHandler(room) {
    await renderInventory();
    const mainDiv = getFreshMainDiv();
    mainDiv.append("<p>Place an item in the fountain, and/or continue!</p>");
    await appendContinue(room.id);
}

async function postHelper(path, json){
    return await (await fetch(path, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json)
    })).json();
}

async function getHelper(path) {
    return await (await fetch(path, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })).json();
}

function parseTextAsHTML(text) {
    return text.replaceAll("\n", "<br>");
}