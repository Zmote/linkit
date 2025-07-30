function ImagePickerController(contextSelector) {
    if (!(this instanceof ImagePickerController)) {
        return new ImagePickerController(contextSelector);
    }
    this.context = contextSelector || "";
    this.contextPrefix = function (input) {
        return input? this.context + " " + input : this.context;
    };
    this.utils = new Utilities();
}

ImagePickerController.prototype.ajax = new AjaxController();

ImagePickerController.prototype.reloadImagePicker = function () {
    var that = this;
    that.ajax({
        url: "/imagepicker",
        method: "GET",
        success: function (data) {
            $(that.contextPrefix(".js-imagePickerModalBody")).replaceWith(data);
            that.init();
        }
    })
};

ImagePickerController.prototype.removeImage = function (imgSrc) {
    var that = this;
    that.ajax({
        url: "/files/delete-file?file=" + encodeURI(imgSrc),
        method: 'DELETE',
        success: function () {
            $(that.contextPrefix(".js-formMedia")).val("");
            portalApp.notifications.notifySuccess("Bild erfolgreich gelöscht!");
            that.reloadImagePicker();
        },
        error: function (err) {
            var errorMessage = err.status === 500 ? JSON.parse(err.responseText).message : err;
            portalApp.notifications.notifyDanger("Upsala, da ist was schiefgelaufen: " + errorMessage);
        }
    });
};

ImagePickerController.prototype.uploadImageForm = function () {
    var that = this;
    var formData = new FormData();
    formData.append('file', $(that.contextPrefix(".js-fileInputGroup"))[0].files[0]);

    that.ajax({
        url: "/files/upload-file",
        method: 'POST',
        data: formData,
        success: function () {
            portalApp.notifications.notifySuccess("Bild Upload war erfolgreich!");
            that.reloadImagePicker();
            $(that.contextPrefix(".js-fileInputGroupLabel")).html("Wähle Datei...");
        },
        error: function (err) {
            var errorMessage = JSON.parse(err.responseText).message;
            portalApp.notifications.notifyDanger("Upsala, da ist was schiefgelaufen: " + errorMessage);
        },
        processData: false,
        contentType: false
    });
};

ImagePickerController.prototype.selectMediaRef = function () {
    var that = this;
    $(that.contextPrefix(".js-imagePicker")).find("option").each(function () {
        var option = $(this);
        if (option.data("imgSrc") === $(that.contextPrefix(".js-formMedia")).val()) {
            option.attr("selected", true);
        }
    });
};

ImagePickerController.prototype.toggleImageUploadButton = function () {
    var that = this;
    var fileInput = $(that.contextPrefix(".js-fileInputGroup")).val();
    var uploadBtn = $(that.contextPrefix(".js-uploadImageBtn"));
    if (fileInput) {
        uploadBtn.addClass("btn-primary");
        uploadBtn.removeClass("btn-secondary");
        uploadBtn.removeAttr("disabled");
    } else {
        uploadBtn.attr("disabled", true);
        uploadBtn.removeClass("btn-primary");
        uploadBtn.addClass("btn-secondary");
    }
};

ImagePickerController.prototype.init = function () {
    var that = this;
    var imagePicker = $(that.contextPrefix(".js-imagePicker"));
    that.selectMediaRef();
    imagePicker.imagepicker({
        selected: function (select) {
            $(that.contextPrefix(".js-formMedia")).val(select.option.data('imgSrc'));
        },
        changed: function (select, newValues) {
            if (!newValues[0]) {
                $(that.contextPrefix(".js-formMedia")).val("");
            }
            $("#collapseExample").collapse("hide");
        },
        initialized: function () {
            var thumbnails = $(that.contextPrefix(".js-imagePickerModalBody li.img-square"));
            thumbnails.off();
            thumbnails.on("mouseover", function () {
                var elem = $(this);
                var removeBtn = $("<i class='fas fa-times shadow-sm btn btn-danger floating-pin-img-picker js-removeImageBtn'></i>");
                if (!elem.find("i.js-removeImageBtn").length) {
                    removeBtn.off().on("click", function () {
                        var elem = $(this);
                        var image = elem.parent().find(".thumbnail").find("img")[0];
                        that.removeImage($(image).attr("src"));
                    });
                    elem.prepend(removeBtn);
                }
            });
            thumbnails.on("mouseleave", function () {
                $(this).find("i.js-removeImageBtn").remove();
            });
            var uploadImageBtn = $(that.contextPrefix(".js-uploadImageBtn"));
            uploadImageBtn.off();
            uploadImageBtn.on("click", function () {
                that.uploadImageForm();
            });
            var fileInputGroup = $(that.contextPrefix(".js-fileInputGroup"));
            fileInputGroup.off();
            fileInputGroup.on("change", function () {
                var fileName = $(this).val();
                $(that.contextPrefix(".js-formFileInputGroupLabel")).html(that.utils.clearPath(fileName));
                that.toggleImageUploadButton();
            });
        }
    });
    that.toggleImageUploadButton();
};