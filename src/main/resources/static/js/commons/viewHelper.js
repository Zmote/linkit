function ViewHelper(context) {
    if (!(this instanceof ViewHelper)) {
        return new ViewHelper();
    }
    this.context = context;
    this.contextPrefix = function (input) {
        return this.context + " " + input;
    }
}

ViewHelper.prototype.hidePortalModal = function () {
    var formModal = $(".js-formModal");
    formModal.removeClass("fade");
    formModal.modal("hide");
    $('body').removeClass('modal-open');
    $('.modal-backdrop').remove();
};