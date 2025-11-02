// function toggleInventory() {
//     $("#inventory").toggleClass("invisible");
//     $("#relics").toggleClass("invisible");
// }

function inventoryVisible() {
    $("#inventory").removeClass("invisible");
    $("#relics").addClass("invisible");
}

function relicsVisible() {
    $("#inventory").addClass("invisible");
    $("#relics").removeClass("invisible");
}