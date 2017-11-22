function saveName(org, orgPos) {
    document.getElementById(orgPos).value = document.getElementById(org).value;
}

function addField(parentId, section) {
    var currentDiv = document.createElement("div");
    var parentDiv = document.getElementById(parentId);
    currentDiv.innerHTML = "<input type=\"text\" name=\"" + section + "\" size=60 required> " +
        "<a onclick=\"return deleteField(this)\" href=\"#\"><img src=\"img/delete.png\"></a>";
    parentDiv.insertBefore(currentDiv, parentDiv.firstElementChild);
    return false;
}

function addPositionBlock(parentId, pos) {
    var currentDiv = document.createElement("div");
    var parentDiv = document.getElementById(parentId);
    var orgIndex = parseInt(parentId.split("Id")[1]);
    currentDiv.innerHTML = "     <table style=\"padding: 0 0 2ch 0\">" +
        "                        <tr>" +
        "                            <td width=\"19%\">" +
        "                                Позиция:<br/>" +
        "                                <input type=\"hidden\" id=\"orgPos" + orgIndex + "\" name=\"" + pos + "\">" +
        "                                <input type=\"text\" size=\"20\" name=\"" + pos + "\" required onkeyup=\"saveName('org" + orgIndex + "','orgPos" + orgIndex + "')\"><br/><br/>" +
        "                                Дата начала:<br/>" +
        "                                <input type=\"date\" name=\"" + pos + "\" required><br/><br/>" +
        "                                Дата окончания:<br/>" +
        "                                <input type=\"date\" name=\"" + pos + "\">" +
        "                            </td>" +
        "                            <td>" +
        "                                Деятельность:<br/>" +
        "                                <textarea name=\"" + pos + "\" rows=\"10\" cols=\"103\" style=\"resize: none\"" +
        "                                          required></textarea>" +
        "                            </td>" +
        "                            <td>" +
        "                                <a onclick=\"return deleteBlock(this)\" href=\"#\"><img src=\"img/delete.png\"></a>" +
        "                            </td>" +
        "                        </tr>" +
        "                    </table>";
    parentDiv.insertBefore(currentDiv, parentDiv.firstElementChild);
    return false;
}

var index = 100;

function addSectionBlock(parentId, type, pos) {
    var currentDiv = document.createElement("div");
    var parentDiv = document.getElementById(parentId);
    var divName = "posId" + index;
    currentDiv.innerHTML = "<b>Название организации:</b>" +
        "            <input id=\"org" + index + "\" type=\"text\" name=\"" + type + "\" size=40 required onkeyup=\"saveName('org" + index + "','orgPos" + index + "')\">" +
        "            &nbsp;<b>URL организации:</b>" +
        "            <input type=\"text\" name=\"" + type + "\" size=40>" +
        "                <a onclick=\"return deleteField(this)\" href=\"#\"><img src=\"img/delete.png\"></a>" +
        "                <a onclick=\"return addPositionBlock('" + divName + "','" + pos + "')\" href=\"#\"><img src=\"img/add.png\"></a>" +
        "                <br/><br/>" +
        "                <div id=\"" + divName + "\"></div>";
    parentDiv.insertBefore(currentDiv, parentDiv.firstElementChild);
    index++;
    return false;
}

function deleteField(box) {
    var contDiv = box.parentNode;
    contDiv.parentNode.removeChild(contDiv);
    return false;
}

function deleteBlock(box) {
    var contDiv = box.parentNode.parentNode.parentNode.parentNode;
    contDiv.parentNode.removeChild(contDiv);
    return false;
}