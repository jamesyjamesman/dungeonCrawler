window.addEventListener("load", async () => {
    let rooms = await postHelper("/gameStart", {});
    createPopup("Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");
    await printRooms(rooms);
});

function createPopup(popupText) {
    $("#popup").removeClass("invisible");
    console.log(popupText);
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

    for (let i = 0; i < rooms.length; i++) {
        const exitsString = `(${rooms[i].numExits} exits)`;
        const textParagraph = $(`<p>${rooms[i].appearance} ${((foresightRelicEquipped) ? exitsString : "")}</p>`);
        const goButton = $(`<button>Go</button>`).click(() => goToRoom(rooms[i].id));
        mainDiv.append(goButton);
        mainDiv.append(textParagraph);
    }
}

async function goToRoom(id) {
    let newRoom = await postHelper("rooms/change", { id: id });
    $("#descriptionDiv").text(newRoom.description);
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
        case "FOUNTAIN": //todo
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
    mainDiv.append("<button>Continue</button>").click(() => printRooms(exits))
}

async function itemHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You picked up a ${room.item.name}!</p>`);
    await appendContinue(room.id);
}

async function relicHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You picked up a ${room.relic.name}!</p>`);
    await appendContinue(room.id);
}

async function trapHandler(room) {
    const mainDiv = getFreshMainDiv();
    mainDiv.append(`<p>You took ${room.damageDealt} damage!</p>`);
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

    if (enemies.length === 0) {
        console.log("enemies dead!");
        mainDiv.append("<p>You win!</p>");
        await appendContinue(room.id);
        //todo figure out how to not have this call
        await postHelper("/rooms/resetEnemies", {id: room.id});
        return;
    }
    for (let i = 0; i < enemies.length; i++) {
        mainDiv.append("<button>Attack</button>").click(() => battleSequence(room, enemies[i]));
        mainDiv.append(`<p>${i}. ${enemies[i].species}</p>`);
    }
}

async function battleSequence(room, enemy) {
    const mainDiv = getFreshMainDiv();
    enemy = await postHelper("/enemy/takeDamage", {
        roomID: room.id,
        uuid: enemy.uuid
    });
    if (enemy.currentHealth <= 0) {
        mainDiv.append(`<p>The ${enemy.species.toString().toLowerCase()} dropped ${enemy.gold} and ${enemy.experience} exp!</p>`);
    }
    await renderEnemies(room);
    //todo enemies attack
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
