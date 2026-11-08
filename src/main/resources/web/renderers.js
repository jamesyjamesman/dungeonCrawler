async function renderStats() {
    const player = await getHelper("/player/getPlayer");
    updateBars(player);
    await updateItemCounts(player);
    await updateStatusEffects(player);
    document.getElementById("goldAmount").textContent = player.gold;
}

async function updateStatusEffects(player) {
    const totalDamage = await getHelper("/player/getTotalDamage");
    const poison = document.getElementById("poison");
    const curse = document.getElementById("curse");
    const weakness = document.getElementById("weakness");
    const fire = document.getElementById("fire");
    const attack = document.getElementById("attack");
    const defense = document.getElementById("defense");

    attack.textContent = `ATK: ${totalDamage}`;
    defense.textContent = `DEF: 0`;

    poison.querySelector("span").textContent = player.currentStatuses.poison + "";
    curse.querySelector("span").textContent = player.currentStatuses.cursed + "";
    weakness.querySelector("span").textContent = player.currentStatuses.weakened + "";
    fire.querySelector("span").textContent = player.currentStatuses.fire + "";

    disableIfZero(poison, curse, weakness, fire);

    function disableIfZero(...elements) {
        for (const element of elements) {
            if (parseInt(element.textContent) < 1) {
                element.classList.add("disabled");
            } else {
                element.classList.remove("disabled");
            }
        }
    }
}

function updateBars(player) {
    const healthBar = document.getElementById("healthBar");
    const absorptionBar = document.getElementById("absorptionBar");
    const experienceBar = document.getElementById("experienceBar");
    const healthText = document.getElementById("healthNumber");
    const experienceText = document.getElementById("experienceNumber");

    const healthString = `${player.currentHealth}/${player.maxHealth}`;
    const absorptionString = ` (+${player.absorption})`;
    const experienceString = ` (${player.experience}/${player.expToNextLevel})`;
    const levelString = `L${player.level}`;

    const oldHealth = parseInt(healthBar.style.width);
    const oldAbsorption = parseInt(absorptionBar.style.width);
    const oldExperience = parseInt(experienceBar.style.width);

    // If went to no absorption from something, animate health after absorption
    if (oldAbsorption > 0 && player.absorption <= 0)
        absorptionBar.addEventListener("transitionend", () =>
            healthBar.style.width = (player.currentHealth / player.maxHealth * 100) + "%",
            {once: true});
    // Else, just do health immediately
    else
        healthBar.style.width = (player.currentHealth / player.maxHealth * 100) + "%";

    absorptionBar.style.width = (player.absorption / player.maxHealth * 100) + "%";

    if (player.level < 10) { // Max level
        experienceBar.style.width = (player.experience / player.expToNextLevel * 100) + "%";
        experienceText.textContent = levelString + experienceString;
    } else {
        experienceBar.style.width = "100%";
        experienceText.textContent = levelString;
    }

    healthText.textContent = healthString + (player.absorption > 0 ? absorptionString : "");

    hideBarHandler(healthBar, player.currentHealth, oldHealth);
    hideBarHandler(absorptionBar, player.absorption, oldAbsorption);
    hideBarHandler(experienceBar, player.experience, oldExperience);

    function hideBarHandler(bar, newValue, oldValue) {
        // If it went to zero from something, hide after it finishes animating
        if (newValue < 1 && oldValue >= 1)
            bar.addEventListener("transitionend", () =>
                bar.classList.add("invisible"), {once: true});
        // Hide if its empty
        else if (newValue < 1)
            bar.classList.add("invisible");
        // Show if it's not empty
        else
            bar.classList.remove("invisible");
    }
}

async function updateItemCounts(player) {
    const inventorySize = await getHelper("/player/getInventorySize");
    const equippedRelics = await getHelper("/player/getEquippedRelics");
    const mainCapacity = document.getElementById("capacity");
    mainCapacity.textContent = `${inventorySize}/${player.inventoryCap}`;
    const relicCapacity = document.querySelector(".inventorySubContainer:has(#relicItems) .capacity");
    relicCapacity.textContent = `Equipped: ${equippedRelics.length}/${player.relicCap}`;
    const weaponCapacity = document.querySelector(".inventorySubContainer:has(#weaponItems) .capacity");
    weaponCapacity.textContent = `Equipped: ${+(player.equippedWeapon !== null)}/1`;
    const armorCapacity = document.querySelector(".inventorySubContainer:has(#armorItems) .capacity");
    armorCapacity.textContent = `Equipped: ${+(player.equippedArmor !== null)}/1`;
}

async function printRooms(rooms) {
    let foresightRelicEquipped = await postHelper("/player/relicEquipped", {id: "FORESIGHT"});
    const elementsToAppend = [];

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
        elementsToAppend.push(elementSpan);
    }
    getFreshMainDiv();
    mainDiv.append(...elementsToAppend);

    await render();
}

async function renderInventory() {
    await Promise.all([
        renderGenericItems(),
        renderRelics(),
        renderWeapons(),
        renderArmor()
    ])
}

async function renderGenericItems() {
    await renderInventoryPane(inventory, await getHelper("/player/getGenericItems"));
}

async function renderRelics() {
    await renderInventoryPane(relics, await getHelper("/player/getRelicItems"));
}

async function renderWeapons() {
    await renderInventoryPane(weapons, await getHelper("/player/getWeaponItems"));
}

async function renderArmor() {
    await renderInventoryPane(armor, await getHelper("/player/getArmorItems"));
}

async function renderInventoryPane(pane, data) {
    const room = await getHelper("/player/getCurrentRoom");
    const dummyPane = document.createElement("div");
    const curseDetectionEquipped = await postHelper("/player/relicEquipped", {id: "CURSE_DETECTION"})

    for (let i = 0; i < data.length; i++) {
        const item = data[i][0];
        const outputParagraph = createItemText(item, data[i].length);
        const elementSpan = document.createElement("span");
        elementSpan.classList.add("listSpan");
        dummyPane.append(elementSpan);

        // Use/Equip/Unequip/Cleanse button
        if (room.type === "FOUNTAIN" && !room.fountainUsed) {
            appendInlineButton("Cleanse", elementSpan, async () => {
                const itemCleansed = await postHelper("/player/cleanseItem", {uuid: item.uuid});
                if (itemCleansed)
                    mainDiv.append(createParagraph(`You cleansed the ${item.name}!`));
                else
                    mainDiv.append(createParagraph(`You tried to cleanse the ${item.name}, but nothing happened.`));
                mainDiv.append(createParagraph(`The fountain dried up!`));
                await render();
            });
        } else if (["WEAPON", "RELIC", "ARMOR"].includes(item.type)) {
            if (item.equipped) {
                appendInlineButton("Unequip", elementSpan, async () => {
                    await inventoryButtonHelper(item, unequipParser);
                });
            } else {
                appendInlineButton("Equip", elementSpan, async () => {
                    await inventoryButtonHelper(item, equipParser);
                });
            }
        } else {
            appendInlineButton("Use", elementSpan,async () => {
                await inventoryButtonHelper(item, useItemParser);
            });
        }

        // Drop/Sell button
        if (!item.equipped) {
            if (room.type === "SHOP" && room.open) {
                const valueSpan = document.createElement("span");
                valueSpan.textContent = `(${item.value} G) `;
                outputParagraph.prepend(valueSpan);

                appendInlineButton("Sell", elementSpan, async () => {
                    const itemSold = await postHelper("/player/sellItem", {uuid: item.uuid});
                    if (itemSold) {
                        await render();
                    } else {
                        errorMessage(`The item couldn't be sold!`);
                    }
                });
            } else {
                appendInlineButton("Drop", elementSpan, async () => {
                    const itemDropped = await postHelper("/player/dropItem", {uuid: item.uuid});
                    if (itemDropped) {
                        await render();
                    } else {
                        errorMessage(`The item couldn't be dropped!`);
                    }
                });
            }
        }
        if (item.cursed && (item.equipped || curseDetectionEquipped)) {
            outputParagraph.classList.add("purple");
        }
        elementSpan.append(outputParagraph);
    }
    pane.innerHTML = "";
    pane.append(...dummyPane.children);
}

async function inventoryButtonHelper(item, func) {
    const response = await func(item);
    if (response.error) {
        errorMessage(response.output);
    } else {
        appendStatusTicker(response.output);
    }
    await render();
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

async function shopHandler(room) {
    await shopRenderer(room);
}

async function shopRenderer(room) {
    const wares = room.wares;
    const elements = [];
    for (let i = 0; i < wares.length; i++) {
        const item = wares[i];
        const elementSpan = document.createElement("span");
        elementSpan.classList.add("listSpan")
        elements.push(elementSpan);
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
                errorMessage("You can't buy that right now!");
            }
        });
        const itemParagraph = createParagraph(`${item.name} (${item.value} G): ${item.description}`);
        itemParagraph.classList.add("listParagraph");
        elementSpan.append(itemParagraph);
    }
    const mainDiv = getFreshMainDiv();
    mainDiv.append(...elements);
    await setRoomContinue(room.id);
    await render();
}

async function renderEnemies(room, reset = true) {
    const enemies = room.enemies;
    const displayEnemyInformation = await postHelper("/player/relicEquipped", {id: "ENEMY_INFORMATION"});

    numberInputOptions = [];
    const elementArray = [];

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
        elementArray.push(elementSpan);
    }
    if (reset) getFreshMainDiv();
    mainDiv.append(...elementArray);
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
        renderStats()
    ]);
}
