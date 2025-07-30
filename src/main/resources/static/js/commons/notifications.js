function Notifications() {
    if (!(this instanceof Notifications)) {
        return new Notifications();
    }
    this.loader = null;
}

var NOTIFICATION_TYPES = {
    INFO: 'info',
    WARN: 'warning',
    DANGER: 'danger',
    SUCCESS: 'success'
};

Notifications.prototype.bootstrapNotify = function (message, type) {
    return $.notify({
        message: message
    }, {
        type: type,
        placement: {
            from: 'bottom'
        },
        z_index: 2000,
        delay: 100,
        animate: {
            enter: "animated fadeInUp",
            exit: "animated fadeOutDown"
        },
        mouse_over: "pause"
    });
};

Notifications.prototype.bootstrapLoaderNotify = function () {
    this.hideLoading();
    return $.notify({
        message: '<span class="spinner-border text-primary" role="status"></span><span class="ml-3">Loading...</span>'
    }, {
        type: NOTIFICATION_TYPES.INFO,
        placement: {
            from: 'bottom',
            align: "left"
        },
        z_index: 2000,
        delay: 0,
        animate: {
            enter: "animated fadeInUp",
            exit: "animated fadeOutUp"
        },
        mouse_over: "pause"
    });
};

Notifications.prototype.notifySuccess = function (message) {
    this.bootstrapNotify(message, NOTIFICATION_TYPES.SUCCESS);
};

Notifications.prototype.notifyWarn = function (message) {
    this.bootstrapNotify(message, NOTIFICATION_TYPES.WARN);
};

Notifications.prototype.notifyDanger = function (message) {
    this.bootstrapNotify(message, NOTIFICATION_TYPES.DANGER);
};

Notifications.prototype.notifyInfo = function (message) {
    this.bootstrapNotify(message, NOTIFICATION_TYPES.INFO);
};

Notifications.prototype.showLoading = function () {
    if (this.loader == null) {
        this.loader = this.bootstrapLoaderNotify();
    }
};

Notifications.prototype.hideLoading = function () {
    if (this.loader != null) {
        this.loader.close();
        this.loader = null;
    }
};

