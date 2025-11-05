window.addEventListener("load", async () => {
    $.post("/gameStart");
    createPopup("Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");
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
    printRooms();
}

function relicsVisible() {
    $("#inventory").addClass("invisible");
    $("#relics").removeClass("invisible");
}

async function printRooms() {
    let roomAppearances = await $.getJSON("/rooms/getPrintableRooms");
    const mainDiv = $("#mainDiv");
    mainDiv.html("");

    for (let i = 0; i < roomAppearances.length; i++) {
        const textParagraph = $("<p>" + roomAppearances[i] + "</p>");
        const goButton = $(`<button onclick="goToRoom(${i})">Go</button>`);
        mainDiv.append(goButton);
        mainDiv.append(textParagraph);
    }

}

async function goToRoom(roomIndex) {

    await fetch("rooms/change", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ index: roomIndex })
    })

    // await $.post("/rooms/change", {
    //     contentType: "application/json",
    //     index: roomIndex
    // });

    printRooms();
}