function AjaxController() {
    if (!(this instanceof AjaxController)) {
        return new AjaxController();
    }
    return function (options) {
        var that = this;
        portalApp.notifications.showLoading();
        var localOptions = {
            method: options.method,
            url: options.url,
            success: function (data) {
                try {
                    if (options.preSuccess && typeof options.preSuccess === "function") {
                        options.preSuccess(data);
                    }
                    if (options.success) {
                        options.success(data);
                    } else {
                        $(that.context).html(data);
                    }
                    if (options.postSuccess && typeof options.postSuccess === "function") {
                        options.postSuccess(data);
                    }
                } catch (ex) {
                    portalApp.notifications.notifyWarn("Ein Fehler ist aufgetreten.");
                    console.log("Fehler:", ex);
                }
            },
            error: function (data) {
                try {
                    if (options.error) {
                        options.error(data);
                    } else {
                        if (options.preError && typeof options.preError === "function") {
                            options.preError(data);
                        }
                        if (data && data.status) {
                            if (data.status === 401 || data.status === 403) {
                                location.reload();
                            } else if (data.status === 400) {
                                $(that.contextPrefix(".modal-body")).replaceWith(data.responseText);
                                $(that.contextPrefix(".js-multiSelect")).lwMultiSelect({
                                    addAllText: "Alle auswählen",
                                    removeAllText: "Alle entfernen",
                                    selectedLabel: "Ausgewählt"
                                });
                            } else {
                                try {
                                    var errorMessage = JSON.parse(data.responseText).message;
                                    portalApp.notifications.notifyDanger("Upsala, da ist was schiefgelaufen: " + errorMessage);
                                } catch (ex) {
                                    portalApp.notifications.notifyDanger("Upsala, da ist was schiefgelaufen: " + ex.message);
                                }
                            }
                        } else {
                            portalApp.notifications.notifyDanger("Up sala, etwas ist schief gelaufen!");
                        }
                        if (options.postError && typeof options.postError === "function") {
                            options.postError(data);
                        }
                    }
                } catch (ex) {
                    portalApp.notifications.notifyDanger("Up sala, etwas ist schief gelaufen!");
                }
            },
            complete: function () {
                portalApp.notifications.hideLoading();
            }
        };
        if (options.data !== undefined) {
            localOptions.data = options.data;
        }
        if (options.processData !== undefined) {
            localOptions.processData = options.processData;
        }
        if (options.contentType !== undefined) {
            localOptions.contentType = options.contentType;
        }
        $.ajax(localOptions);
    };
}