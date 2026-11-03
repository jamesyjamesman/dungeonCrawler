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

async function render() {
    await Promise.all([
        renderInventory(),
        renderRelics(),
        renderStats()
    ]);
}
