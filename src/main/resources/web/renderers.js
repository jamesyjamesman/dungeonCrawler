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
        elementSpan.classList.add("listSpan");
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
        const outputParagraph = createItemText(item, inventoryData[i].length);
        const elementSpan = document.createElement("span");
        elementSpan.classList.add("listSpan");
        inventory.append(elementSpan);

        if (item.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            appendInlineButton("Cleanse", elementSpan, async () => {
                const itemCleansed = await postHelper("/player/cleanseItem", {uuid: item.uuid});
                if (itemCleansed)
                    mainDiv.append(createParagraph(`The ${item.name} was cleansed!`));
                else
                    mainDiv.append(createParagraph(`The ${item.name} couldn't be cleansed!`));
                mainDiv.append(createParagraph(`The fountain dried up!`));
                await render();
            });
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
            appendInlineButton(buttonText, elementSpan,async () => {
                const response = await postHelper("/player/useInventoryItem", { uuid: item.uuid });
                if (response.error) {
                    getFreshErrorDiv().append(createParagraph(`response.output`));
                } else {
                    appendStatusTicker(response.output);
                }
                await render();
                if (item.type === "RELIC") relicButton.click();
            });
        }

        if (room.type === "SHOP" && room.open) {
            const valueSpan = document.createElement("span");
            valueSpan.textContent = `(${item.value} G)`;
            outputParagraph.prepend(valueSpan);

            appendInlineButton("Sell", elementSpan,async () => {
                const itemSold = await postHelper("/player/sellItem", {uuid: item.uuid});
                if (itemSold) {
                    await render();
                } else {
                    getFreshErrorDiv().append(createParagraph(`The item couldn't be sold!`));
                }
            });
        } else {
            appendInlineButton("Drop", elementSpan, async () => {
                const itemDropped = await postHelper("/player/dropItem", {uuid: item.uuid});
                if (itemDropped) {
                    await render();
                } else {
                    getFreshErrorDiv().append(createParagraph(`The item couldn't be dropped!`));
                }
            });
        }
        if (item.cursed && curseDetectionEquipped) {
            outputParagraph.classList.add("purple");
        }
        elementSpan.append(outputParagraph);
    }
}

function createItemText(item, amount=1) {
    const outputParagraph = createParagraph();
    outputParagraph.classList.add("listParagraph");
    const nameStrong = document.createElement("strong");
    nameStrong.textContent = item.name;
    if (amount > 1) {
        const amountSpan = document.createElement("span");
        amountSpan.textContent = `${amount}x `;
        outputParagraph.append(amountSpan);
    }
    const descriptionSpan = document.createElement("span");
    descriptionSpan.textContent = `: ${item.description}`;
    outputParagraph.append(nameStrong);
    outputParagraph.append(descriptionSpan);
    return outputParagraph;
}

function appendInlineButton(text, appendTo, func) {
    const button = document.createElement("button");
    button.classList.add("clickableButton", "inlineButton");
    button.textContent = text;
    button.addEventListener("click", func);
    appendTo.append(button);
}

async function renderRelics() {
    const room = await getHelper("/player/getCurrentRoom");
    const relicData = await getHelper("/player/getRelics");
    relics.innerHTML = "";
    for (const relic of relicData) {
        const elementSpan = document.createElement("span");
        elementSpan.classList.add("listSpan");
        relics.append(elementSpan);
        if (relic.cleansable && room.type === "FOUNTAIN" && !room.fountainUsed) {
            appendInlineButton("Cleanse", elementSpan, async () => {
                const errorDiv = getFreshErrorDiv()
                const relicCleansed = await postHelper("/player/cleanseRelic", {uuid: relic.uuid});
                if (relicCleansed)
                    errorDiv.append(createParagraph(`The ${relic.name} was cleansed!`));
                else
                    errorDiv.append(createParagraph(`The ${relic.name} couldn't be cleansed!`));
                await render();
            });
        } else {
            appendInlineButton("Unequip", elementSpan, async () => {
                const response = await postHelper("/player/unequipRelic", {uuid: relic.uuid});
                if (response.error) {
                    getFreshErrorDiv().append(createParagraph(response.output));
                } else {
                    appendStatusTicker(response.output);
                }
                await render();
                inventoryButton.click();
            });
        }
        const relicParagraph = createItemText(relic);
        if (relic.cursed) {
            relicParagraph.classList.add("purple");
        }
        elementSpan.append(relicParagraph);
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
        elementSpan.classList.add("listSpan")
        mainDiv.append(elementSpan);
        appendInlineButton("Buy", elementSpan, async () => {
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
        const itemParagraph = createParagraph(`${item.name} (${item.value} G): ${item.description}`);
        itemParagraph.classList.add("listParagraph");
        elementSpan.append(itemParagraph);
    }
    await appendRoomContinue(room.id);
    await render();
}

async function renderEnemies(room, reset = true) {
    const enemies = room.enemies;
    const displayEnemyInformation = await postHelper("/player/relicEquipped", {id: "ENEMY_INFORMATION"});
    if (reset) getFreshMainDiv();

    // todo at some point the continue button stopped working (after killing 1 enemy) and did nothing and the remaining enemy just attacked each time??


    numberInputOptions = [];
    for (let i = 0; i < enemies.length; i++) {
        const speciesCapitalized = capitalize(enemies[i].species.toString());
        const elementSpan = document.createElement("span");
        elementSpan.classList.add("listSpan");
        const attackButton = document.createElement("button");
        attackButton.classList.add("clickableButton", "inlineButton");
        attackButton.textContent = "Attack";
        attackButton.addEventListener("click", async () =>
            await battleSequence(room, enemies[i]));
        numberInputOptions[i] = async () => await battleSequence(room, enemies[i]);
        elementSpan.append(attackButton);
        if (displayEnemyInformation) {
            appendInlineButton("Check", elementSpan, () => checkEnemy(enemies[i]));
        }
        const enemyParagraph = document.createElement("p");
        enemyParagraph.classList.add("listParagraph");
        // Holy moly
        enemyParagraph.textContent = `${i+1}. ${speciesCapitalized}${(displayEnemyInformation) ? ` (${enemies[i].currentHealth}/${enemies[i].maxHealth} HP)` : ""}`;
        elementSpan.append(enemyParagraph);
        mainDiv.append(elementSpan);
    }
}

function appendStatusTicker(text) {
    const tickerDiv = document.getElementById("statusTicker");
    const statusParagraph = createParagraph(text);
    statusParagraph.classList.add("statusParagraph");
    tickerDiv.prepend(statusParagraph);
    let childParagraphs = tickerDiv.children;

    // Remove clipping tickers
    for (let i = childParagraphs.length - 1; i >= 0; i--) {
        const paragraph = childParagraphs[i];
        const bounds = paragraph.getBoundingClientRect();
        const tickerBounds = tickerDiv.getBoundingClientRect();
        if (bounds.bottom > tickerBounds.bottom) {
            paragraph.remove();
        }
    }

    // Reset
    childParagraphs = tickerDiv.children;

    // Color tickers
    for (let i = 0; i < childParagraphs.length; i++) {
        const paragraph = childParagraphs[i];
        const ratio = (childParagraphs.length-i)/childParagraphs.length;
        if (ratio > 0.8) {
            paragraph.style.color = "white";
        } else if (ratio > 0.3) {
            paragraph.style.color = "darkgrey"; // Lighter than grey, don't ask
        } else {
            paragraph.style.color = "dimgrey"
        }
    }
}

async function render() {
    await Promise.all([
        renderInventory(),
        renderRelics(),
        renderStats()
    ]);
}
