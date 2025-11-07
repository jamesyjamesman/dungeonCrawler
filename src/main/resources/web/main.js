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
        const goButton = $(`<button>Go</button>`).click(() => goToRoom(rooms[i].uuid));
        mainDiv.append(goButton);
        mainDiv.append(textParagraph);
    }
}

async function goToRoom(uuid) {
    let newRoom = await postHelper("rooms/change", { uuid: uuid });
    $("#descriptionDiv").text(newRoom.description);
    switch (newRoom.type) {
        case "NORMAL":
            await printRooms(newRoom.exits);
            break;
        case "TRAP": //todo
            await trapHandler(newRoom);
            break;
        case "BOSS":
        case "ENEMY": //todo
            await enemyHandler(newRoom);
            break;
        case "RELIC":
        case "ITEM": //todo
            break;
        case "FOUNTAIN": //todo
            break;
        case "SHOP": //todo
            break;
        case "END": //todo
    }
}

async function trapHandler(room) {
    const mainDiv = $("#mainDiv");
    mainDiv.html("");
    mainDiv.append(`<p>You took ${room.damageDealt} damage!</p>`);
    mainDiv.append(`<button onClick="printRooms()">Continue</button>`)
}

async function enemyHandler(room) {

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
