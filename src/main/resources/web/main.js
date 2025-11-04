window.addEventListener("load", async () => {
    $.ajax("/gameStart", {
        method: "POST"
    });
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
    $.ajax("/rooms/getCurrent");
}