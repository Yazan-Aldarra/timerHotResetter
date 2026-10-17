const btn = document.querySelector("#add_user");
const players_list = document.querySelector("#players_list");
const template_player_list_item = document.querySelector("#template_player_list_item");
btn.addEventListener("click", () => {

    const list_items = players_list.querySelectorAll("li");

    // The below sytax allow us to cast a type. (fake casting for the language server)
    const new_player = /** @type {HTMLElement} */ (template_player_list_item.cloneNode(true));

    const input = new_player.querySelector("input");
    const label = new_player.querySelector("label");

    const name = `name_${list_items.length}`;

    input.setAttribute("id", name);
    input.setAttribute("name", name);
    label.setAttribute("for", name);

    label.textContent = `Player ${length} name: `;

    new_player.removeAttribute("hidden");
    players_list.appendChild(new_player);
});
