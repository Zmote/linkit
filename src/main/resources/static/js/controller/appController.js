function AppController() {
    if (!(this instanceof AppController)) {
        return new AppController();
    }
    this.urlBtnMap = {
        "configurations": "configurationsBtn",
        "publiclinks": "publicLinksBtn",
        "sharedwithme": "sharedLinksBtn",
        "mylinks": "myLinksBtn",
        "credentials": "credentialsBtn"
    };

    function swap(json) {
        var ret = {};
        for (var key in json) {
            ret[json[key]] = key;
        }
        return ret;
    }

    this.btnUrlMap = swap(this.urlBtnMap);

    this.btnTargetUrlMap = {
        "publicLinksBtn": "global-links",
        "myLinksBtn": "personal-links"
    };

    this.utils = new Utilities();
    this.viewHelper = new ViewHelper();
    this.imagePickerCtrl = new ImagePickerController();

    this.contextPrefix = function (input) {
        return input;
    };
}

AppController.prototype.ajax = new AjaxController();

AppController.prototype.loadContentFromUrl = function (url, successCallback, errorCallback) {
    var that = this;
    that.ajax({
        method: "GET",
        url: url,
        success: function (data) {
            try {
                $("#mainContent").html(data);
                that.activateLink(that.urlBtnMap[url.substring(1)]);
            } catch (ex) {
                portalApp.notifications.notifyWarn("Ein Fehler ist aufgetreten!");
            }
        },
        postSuccess: successCallback,
        postError: errorCallback
    });
};

AppController.prototype.activateLink = function (buttonId) {
    $("#navbarTogglerDemo03 .nav-item").removeClass("active");
    $("#" + buttonId).parent("li").addClass("active");
};

AppController.prototype.addNavClickEvent = function (callback, navUrl) {
    var that = this;
    $("#" + that.urlBtnMap[navUrl.substring(1)]).on("click", function () {
        callback();
        window.history.pushState(null, navUrl, navUrl);
    });
};

AppController.prototype.loadPublicLinks = function () {
    this.loadContentFromUrl("/publiclinks");
};

AppController.prototype.loadMyLinks = function () {
    this.loadContentFromUrl("/mylinks");
};


AppController.prototype.loadSharedLinks = function () {
    this.loadContentFromUrl("/sharedwithme");
};

AppController.prototype.loadCredentials = function () {
    this.loadContentFromUrl("/credentials");
};

AppController.prototype.loadConfigurations = function () {
    var that = this;
    that.loadContentFromUrl("/configurations");
};

AppController.prototype.filterLinks = function () {
    var searchTerm = $("#linkSearch").val().toLocaleLowerCase();
    var searchCategory = $("#categoryFilter").find("option:selected").val();
    var searchType = $("#typeFilter").find("option:selected").val();
    var searchShared = $("#onlySharedCheckbox").is(":checked");
    $(".js-card").each(function () {
        var elem = $(this);
        var title = elem.data("title").toLowerCase();
        var category = elem.data("category");
        var type = elem.data('type');
        var shared = elem.data("shared");
        if ((!searchTerm || title.includes(searchTerm))
            && (!searchCategory || category === searchCategory)
            && (!searchType || type === searchType)
            && (!searchShared || searchShared === shared)) {
            elem.parent().show();
        } else {
            elem.parent().hide();
        }
    });
};

AppController.prototype.showPassword = function (elem) {
    var that = this;
    var id = elem.data("id");
    var target = elem.parent().parent().find("td.js-credentialsPassword");
    that.ajax({
        method: "GET",
        url: "/credentials/" + id,
        success: function (data) {
            target.html(data);
            elem.attr("disabled", true);
            elem.off();
        }
    });
};

AppController.prototype.showLoginAndPassword = function (elem) {
    var that = this;
    var id = elem.data("id");
    that.ajax({
        method: "GET",
        url: "/credentials/extended/" + id,
        success: function (data) {
            var frontModalWrapper = $(".js-frontModalWrapper");
            frontModalWrapper.html(data);
            frontModalWrapper.find(".js-formModal").modal("show");
        }
    });
};

AppController.prototype.getActivePage = function () {
    return $("#navbarTogglerDemo03").find(".nav-item.active").find("a").attr("id");
};

AppController.prototype.apiUrl = function () {
    return this.btnTargetUrlMap[this.getActivePage()];
};

AppController.prototype.loadUrl = function () {
    return this.btnUrlMap[this.getActivePage()];
};

AppController.prototype.clearFrontModal = function () {
    $(".js-frontModalWrapper").html("");
}

AppController.prototype.deleteLink = function (id) {
    var that = this;
    that.ajax({
        method: "DELETE",
        url: that.apiUrl() + "/" + id,
        success: function () {
            that.viewHelper.hidePortalModal();
            that.clearFrontModal();
            portalApp.notifications.notifySuccess("Löschen erfolgreich!");
            that.loadContentFromUrl("/" + that.loadUrl());
        }
    });
};

AppController.prototype.createLink = function () {
    var that = this;
    that.ajax({
        method: 'POST',
        url: that.apiUrl(),
        data: JSON.stringify(that.utils.formToJson($(".js-form"))),
        contentType: "application/json; charset=utf-8",
        success: function () {
            that.viewHelper.hidePortalModal();
            that.clearFrontModal();
            portalApp.notifications.notifySuccess("Erstellen erfolgreich!");
            that.loadContentFromUrl("/" + that.loadUrl());
        }
    });
};

AppController.prototype.updateLink = function (id) {
    var that = this;
    that.ajax({
        method: 'PUT',
        url: that.apiUrl() + "/" + id,
        data: JSON.stringify(that.utils.formToJson($(".js-form"))),
        contentType: "application/json; charset=utf-8",
        success: function () {
            that.viewHelper.hidePortalModal();
            that.clearFrontModal();
            portalApp.notifications.notifySuccess("Aktualisierung erfolgreich!");
            that.loadContentFromUrl("/" + that.loadUrl());
        }
    });
};

AppController.prototype.updateProfile = function (id) {
    var that = this;
    that.ajax({
        method: 'PUT',
        url: "/profile",
        data: JSON.stringify(that.utils.formToJson($(".js-form"))),
        contentType: "application/json; charset=utf-8",
        success: function () {
            that.viewHelper.hidePortalModal();
            that.clearFrontModal();
            portalApp.notifications.notifySuccess("Aktualisierung erfolgreich!");
        }
    });
};

AppController.prototype.initializeForm = function () {
    $(".js-frontModalWrapper").find(".js-formModal").modal("show");
    var multiSelect = $(".js-multiSelect");
    if (multiSelect.length) {
        multiSelect.lwMultiSelect({
            addAllText: "Alle auswählen",
            removeAllText: "Alle entfernen",
            selectedLabel: "Ausgewählt"
        });
    }

    var categorySelect = $(".js-formCategory");
    if (categorySelect.length) {
        var selectedOption = categorySelect.find("option:selected");
        var selectizedCategory = categorySelect.selectize({
            create: true,
            sortField: 'text',
            render: {
                option_create: function (data, escape) {
                    return '<div class="create">Hinzufügen <strong>' + escape(data.input) + '</strong>&hellip;</div>';
                }
            }
        });
        selectizedCategory[0].selectize.setValue(selectedOption.val(), true);
    }
};

AppController.prototype.init = function () {
    var that = this;
    that.addNavClickEvent(function () {
        that.loadPublicLinks()
    }, "/publiclinks");
    that.addNavClickEvent(function () {
        that.loadMyLinks()
    }, "/mylinks");
    that.addNavClickEvent(function () {
        that.loadSharedLinks()
    }, "/sharedwithme");
    that.addNavClickEvent(function () {
        that.loadCredentials();
    }, "/credentials");
    that.addNavClickEvent(function () {
        that.loadConfigurations();
    }, "/configurations");
    var body = $("body");
    body.on("input", "#linkSearch", function () {
        that.filterLinks();
    });
    body.on("change", "#categoryFilter", function () {
        that.filterLinks();
    });
    body.on("change", "#typeFilter", function () {
        that.filterLinks();
    });
    body.on("click", "#onlySharedCheckbox", function () {
        that.filterLinks();
    });
    body.on("click", ".js-showPasswordBtn", function () {
        var elem = $(this);
        that.showPassword(elem);
    });

    body.on("click", ".js-frontAddPortalLink", function (event) {
        event.preventDefault();
        that.ajax({
            method: "GET",
            url: "/global-links/new",
            success: function (data) {
                $(".js-frontModalWrapper").html(data);
                that.initializeForm();
                $(".js-createBtn").on("click", function () {
                    var elem = $(this);
                    that.createLink(elem.data("id"));
                });
                var imageGalleryBtn = $(".js-imageGalleryBtn");
                if (imageGalleryBtn.length) {
                    imageGalleryBtn.on("click", function () {
                        that.imagePickerCtrl.init();
                    });
                }
            }
        });
    });

    body.on("click", ".js-frontEditPortalLink", function (event) {
        event.preventDefault();
        var elem = $(this);
        var id = elem.data("id");
        that.ajax({
            method: "GET",
            url: that.apiUrl() + "/" + id + "/edit",
            success: function (data) {
                $(".js-frontModalWrapper").html(data);
                that.initializeForm();
                $(".js-updateBtn").on("click", function () {
                    var elem = $(this);
                    that.updateLink(elem.data("id"));
                });
                var imageGalleryBtn = $(".js-imageGalleryBtn");
                if (imageGalleryBtn.length) {
                    imageGalleryBtn.on("click", function () {
                        that.imagePickerCtrl.init();
                    });
                }
            }
        });
    });


    body.on("click", ".js-frontDeletePortalLink", function (event) {
        event.preventDefault();
        var elem = $(this);
        var id = elem.data("id");
        that.ajax({
            method: "GET",
            url: "/warn",
            success: function (data) {
                $(".js-frontModalWrapper").html(data);
                $(".js-formModal").modal("show");
                $(".js-warnProceed").on("click", function () {
                    that.deleteLink(id);
                })
            }
        });
    });

    body.on("click", ".js-frontShowPassword", function (event) {
        event.preventDefault();
        var elem = $(this);
        that.showLoginAndPassword(elem);
    });

    body.on("click", ".js-frontEditProfile", function (event) {
        event.preventDefault();
        that.ajax({
            method: "GET",
            url: "/profile/edit",
            success: function (data) {
                $(".js-frontModalWrapper").html(data);
                that.initializeForm();
                $(".js-updateBtn").on("click", function () {
                    that.updateProfile();
                });
                var imageGalleryBtn = $(".js-imageGalleryBtn");
                if (imageGalleryBtn.length) {
                    imageGalleryBtn.on("click", function () {
                        that.imagePickerCtrl.init();
                    });
                }
            }
        });
    });

    window.onpopstate = function () {
        var path = document.location.pathname;
        if (path) {
            if (path === "/") {
                that.loadContentFromUrl("/publiclinks");
            } else {
                that.loadContentFromUrl(path);
            }
        }
    };
};