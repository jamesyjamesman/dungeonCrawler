async function goToRoom(id) {
    const changeInfo = await postHelper("/rooms/change", { id: id });
    if (!changeInfo.playerAlive) {
        playerDeath();
        return;
    }
    const newRoom = changeInfo.room;
    const roomExits = await postHelper("/rooms/getExits", {id : newRoom.id });
    relicInfoParser(changeInfo.relicsUsed);
    statusInfoParser(changeInfo.statusInfo);
    document.body.style.backgroundImage = `url("textures/${newRoom.backgroundFileName}")`;
    const descriptionParagraph = document.createElement("p");
    descriptionParagraph.innerHTML = parseTextAsHTML(newRoom.description);
    await roomSwitch(roomExits, newRoom);
    descriptionDiv.innerHTML = "";
    descriptionDiv.append(descriptionParagraph);
}

async function roomSwitch(roomExits, newRoom) {
    switch (newRoom.type) {
        case "NORMAL": return await printRooms(roomExits);
        case "TRAP": return await trapHandler(newRoom);
        case "BOSS":
        case "ENEMY": return await enemyHandler(newRoom);
        case "RELIC": return await relicHandler(newRoom);
        case "ITEM": return await itemHandler(newRoom);
        case "FOUNTAIN": return await fountainHandler(newRoom);
        case "SHOP": return await shopHandler(newRoom);
        case "END": return await endingHandler(newRoom);
    }
}

function createParagraph(text = "") {
    const paragraph = document.createElement("p");
    paragraph.textContent = text;
    return paragraph;
}

async function appendRoomContinue(id) {
    const exits = await postHelper("/rooms/getExits", { id: id });
    appendContinue(async () => await printRooms(exits));
}

function appendContinue(func) {
    const continueButton = document.createElement("button");
    continueButton.classList.add("clickableButton");
    continueButton.textContent = "Continue";
    continueButton.addEventListener("click", func)
    numberInputOptions = [func];
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

async function relicHandler(room) {
    let pickupSuccess = await itemPickup(room, "relic");
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

async function itemPickup(room) {
    const pickupSuccess = await postHelper("/rooms/itemPickup", {id: room.id});
    const pickupParagraph = document.createElement('p');
    // todo: sometimes this fails
    // item name shows when picking up, but continue button does not get added
    // has no name or description in the inventory
    // can progress by entering 1
    // Does not fix on rerender, so might be an issue on the server side

    if (pickupSuccess) {
        pickupParagraph.textContent = `You picked up a ${room.item.name}!`;
    } else {
        pickupParagraph.textContent = `Your inventory is full! Drop an item to pick up the ${room.item.name}!`;
    }
    const mainDiv = getFreshMainDiv();
    mainDiv.append(pickupParagraph);
    await appendRoomContinue(room.id);
    await render();
    return pickupSuccess;
}

async function trapHandler(room) {
    const trapRoomDamageInfo = await postHelper("/rooms/trapDamage", { id: room.id });
    const playerDead = trapRoomDamageInfo.playerDead;
    const damageDealt = trapRoomDamageInfo.damageDealt;
    const damageParagraph = document.createElement("p");
    damageParagraph.textContent = `You took ${damageDealt} damage!`;
    const mainDiv = getFreshMainDiv();
    mainDiv.append(damageParagraph);
    if (playerDead) {
        playerDeath();
        return;
    }
    await renderStats();
    await appendRoomContinue(room.id);
}

async function enemyHandler(room) {
    const mainDiv = getFreshMainDiv();
    const battleMessageParagraph = document.createElement("p");
    battleMessageParagraph.textContent = room.battleInitiationMessage;
    mainDiv.append(battleMessageParagraph);
    await renderEnemies(room, false);
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
            await appendRoomContinue(room.id);
        } else {
            appendContinue(continueBattle);
        }
    } else {
        await continueBattle();
    }

    async function continueBattle() {
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

async function fountainHandler(room) {
    const selfCleanseButton = document.createElement("button");
    selfCleanseButton.classList.add("clickableButton");
    selfCleanseButton.textContent = "Jump in";
    selfCleanseButton.addEventListener("click", async () => {
        const extinguished = await postHelper("/rooms/cleansePlayer");
        const outputString = (extinguished) ?
            "You jumped in the fountain, and put out the fire on you!" :
            "...You're all wet now."
        mainDiv.append(createParagraph(outputString));
    });
    const mainDiv = getFreshMainDiv();
    mainDiv.append(createParagraph("Place an item in the fountain, and/or continue!"));
    mainDiv.append(selfCleanseButton);
    await appendRoomContinue(room.id);
    await render();
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

async function endingHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(createParagraph("You win! Good job! But, if you'd like to return and continue... You may."));
    await appendRoomContinue(room.id);
}