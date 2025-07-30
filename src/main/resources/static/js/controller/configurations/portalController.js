$(document).ready(function () {
    function PortalSettingsController() {
        if (!(this instanceof PortalSettingsController)) {
            return new PortalSettingsController();
        }
    }

    PortalSettingsController.prototype.activateAceEditor = function () {
        var aceEditor = $("#aceEditor");
        var customCss = $("#customCss");
        aceEditor.html(customCss.val());
        var editor = ace.edit("aceEditor");
        editor.setTheme("ace/theme/twilight");
        editor.session.setMode("ace/mode/css");
        editor.getSession().on('change', function () {
            customCss.val(editor.getSession().getValue());
        });
    };

    new PortalSettingsController().activateAceEditor();
});