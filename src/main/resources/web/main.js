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
    let roomAppearances = await $.getJSON("/rooms/getPrintableRooms");
    const mainDiv = $("#mainDiv");
    mainDiv.html("");

    for (let i = 0; i < roomAppearances.length; i++) {
        const textParagraph = $("<p>" + roomAppearances[i] + "</p>");
        const goButton = $("<button>Go</button>");
        mainDiv.append(goButton);
        mainDiv.append(textParagraph);
    }

}