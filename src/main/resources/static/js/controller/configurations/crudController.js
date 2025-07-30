function CrudController(contextSelector, basePath) {
    if (!(this instanceof CrudController)) {
        return new CrudController();
    }
    this.context = contextSelector;
    this.basePath = basePath;
    this.contextPrefix = function (input) {
        return this.context + " " + input;
    };
    this.viewHelper = new ViewHelper(this.context);
    this.utils = new Utilities();
    this.imagePickerCtrl = new ImagePickerController(this.context);
    this.pageHelper = new PageHelper(this.context);
}

CrudController.prototype.ajax = new AjaxController();

CrudController.prototype.delete = function (id) {
    var that = this;
    that.ajax({
        method: 'DELETE',
        url: that.basePath + "/" + id,
        success: function () {
            that.loadPage(that.pageHelper.currentPage());
            portalApp.notifications.notifySuccess("Löschen erfolgreich!");
        }
    })
};

CrudController.prototype.create = function () {
    var that = this;
    that.ajax({
        method: 'POST',
        url: that.basePath,
        data: JSON.stringify(that.utils.formToJson($(that.contextPrefix(".js-form")))),
        contentType: "application/json; charset=utf-8",
        success: function () {
            that.viewHelper.hidePortalModal();
            that.loadPage(that.pageHelper.currentPage());
            portalApp.notifications.notifySuccess("Erstellen erfolgreich!");
        }
    });
};

CrudController.prototype.update = function (id) {
    var that = this;
    that.ajax({
        method: 'PUT',
        url: that.basePath + "/" + id,
        data: JSON.stringify(that.utils.formToJson($(that.contextPrefix(".js-form")))),
        contentType: "application/json; charset=utf-8",
        success: function () {
            that.viewHelper.hidePortalModal();
            that.loadPage(that.pageHelper.currentPage());
            portalApp.notifications.notifySuccess("Aktualisierung erfolgreich!");
        }
    });
};

CrudController.prototype.editFormFor = function (id) {
    var that = this;
    that.ajax({
        method: 'GET',
        url: that.basePath + "/" + id + '/edit',
        success: function (data) {
            that.initializeForm(data);
            $(that.contextPrefix(".js-updateBtn")).on("click", function () {
                var elem = $(this);
                that.update(elem.data("id"));
            });
            var imageGalleryBtn = $(that.contextPrefix(".js-imageGalleryBtn"));
            if (imageGalleryBtn.length) {
                $(that.contextPrefix(".js-imageGalleryBtn")).on("click", function () {
                    that.imagePickerCtrl.init();
                });
            }
        }
    });
};

CrudController.prototype.newForm = function () {
    var that = this;
    that.ajax({
        method: 'GET',
        url: that.basePath + "/" + 'new',
        success: function (data) {
            that.initializeForm(data);
            $(that.contextPrefix(".js-createBtn")).on('click', function () {
                that.create();
            });
            $(that.contextPrefix(".js-imageGalleryBtn")).on("click", function () {
                that.imagePickerCtrl.init();
            });
        }
    });
};

CrudController.prototype.loadPage = function (pageNo, sort) {
    var that = this;
    pageNo = pageNo || 0;
    sort = sort || that.pageHelper.DEFAULT_SORT;
    that.ajax({
        method: 'GET',
        url: "/configurations" + that.basePath + '?pageNo=' + pageNo + "&size=" + that.pageHelper.pageLimit() + "&sort=" + sort
    });
};

CrudController.prototype.initializeForm = function (data) {
    var that = this;
    $(that.contextPrefix(".js-modalWrapper")).html(data);
    $(that.contextPrefix(".js-formModal")).modal("show");

    var multiSelect = $(that.contextPrefix(".js-multiSelect"));
    if (multiSelect.length) {
        $(that.contextPrefix(".js-multiSelect")).lwMultiSelect({
            addAllText: "Alle auswählen",
            removeAllText: "Alle entfernen",
            selectedLabel: "Ausgewählt"
        });
    }

    var categorySelect = $(that.contextPrefix(".js-formCategory"));
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

CrudController.prototype.init = function () {
    var that = this;
    var pageLimit = $(that.contextPrefix('.js-pageLimit'));
    var newBtn = $(that.contextPrefix(".js-newBtn"));
    var editBtn = $(that.contextPrefix(".js-editBtn"));
    var deleteBtn = $(that.contextPrefix(".js-deleteBtn"));
    var pageLink = $(that.contextPrefix(".js-page-link"));
    var sortBtn = $(that.contextPrefix(".js-sort"));

    pageLimit.on('change', function () {
        that.loadPage();
    });
    newBtn.on("click", function () {
        that.newForm();
    });
    editBtn.on("click", function () {
        var elem = $(this);
        that.editFormFor(elem.data("id"));
    });
    deleteBtn.on("click", function () {
        var elem = $(this);
        that.delete(elem.data("id"));
    });

    pageLink.on("click", function () {
        var elem = $(this);
        that.loadPage(+elem.data("pageno"), that.pageHelper.currentSortDir() === "DEFAULT" ?
            that.pageHelper.DEFAULT_SORT : that.pageHelper.currentSort() + ":" + that.pageHelper.currentSortDir());
    });

    sortBtn.on("click", function () {
        var elem = $(this);
        elem.data("dir", that.pageHelper.nextDirection(elem.data("dir")));
        var sortProperty = elem.data("col");
        var sortDirection = elem.data("dir");
        var sort = sortProperty + ":" + sortDirection;
        that.loadPage(that.pageHelper.currentPage(), sortDirection === "DEFAULT" ? that.pageHelper.DEFAULT_SORT : sort);
    });
};