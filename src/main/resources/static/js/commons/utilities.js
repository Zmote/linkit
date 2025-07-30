function Utilities() {
}

Utilities.prototype.formToJson = function (form) {
    var passwordCollapse = $("#passwordCollapse");
    var data = form.serializeJSON();
    if (passwordCollapse.length > 0 && !passwordCollapse.hasClass("show")) {
        delete data.password;
    }
    return data;
};


Utilities.prototype.clearPath = function (fileName) {
    var roundOne = fileName.split("\\");
    var roundTwo = roundOne[roundOne.length - 1].split("/");
    return roundTwo[roundTwo.length - 1];
};