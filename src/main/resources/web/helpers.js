async function postHelper(path, json){
    const response = await fetch(path, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json)
    });
    // if (response.status === 403) {
    //     playerDeath();
    // }
    if (response.ok) {
        return await response.json();
    }
    throw new Error();
}

async function getHelper(path) {
    const response = await fetch(path, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    // if (response.status === 403) {
    //     playerDeath();
    // }
    if (response.ok) {
        return await response.json();
    }
    throw new Error();
}

function parseTextAsHTML(text) {
    return text.replaceAll("\n", "<br>");
}

// Why does it... return it?
function getFreshMainDiv() {
    const continueButton = document.getElementById("continue");
    mainDiv.innerHTML = "";
    mainDiv.append(continueButton);
    return mainDiv;
}

let errors = [];

function errorMessage(text) {
    const errorIndex = findFirstOpenError();
    const errorDiv = document.createElement("div");
    errorDiv.classList.add("error", "bordered");
    errorDiv.append(createParagraph(text));
    errorDiv.style.top = (errorIndex * 5) + "%";
    errorDiv.style.zIndex = errorIndex + "";
    document.body.append(errorDiv);
    // Stops left from updating immediately so it animates
    setTimeout(() => errorDiv.style.left = 0 + "px", 10);
    setTimeout(() => {
        errorDiv.style.left = -errorDiv.getBoundingClientRect().width + "px";
        errorDiv.addEventListener("transitionend", () => {
            errorDiv.remove();
            errors[errorIndex] = undefined;
        });
    }, 3000);
}

function findFirstOpenError() {
    let i = 0;
    while (errors[i] !== undefined) i++;
    errors[i] = 1;
    return i;
}

function capitalize(string) {
    return string.charAt(0).toUpperCase() + string.substring(1).toLowerCase();
}