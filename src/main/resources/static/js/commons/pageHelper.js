function PageHelper(context) {
    if (!(this instanceof PageHelper)) {
        return new PageHelper(context);
    }
    this.context = context;
    this.contextPrefix = function (input) {
        return this.context + " " + input;
    };
    this.DEFAULT_PAGE = 0;
    this.DEFAULT_COUNT = 1;
    this.DEFAULT_LIMIT = 10;
    this.DEFAULT_SORT = "id:ASC";
    this.SORT_DIRECTIONS = ["ASC", "DESC", "DEFAULT"];
}


PageHelper.prototype.nextDirection = function (currentDir) {
    var currIndex = this.SORT_DIRECTIONS.indexOf(currentDir);
    return this.SORT_DIRECTIONS[(currIndex + 1) % this.SORT_DIRECTIONS.length];
};

PageHelper.prototype.currentPage = function () {
    var that = this;
    var activePage = +$(that.contextPrefix(".js-pagination")).data("activepage");
    return isNaN(activePage) ? that.DEFAULT_PAGE : activePage;
};

PageHelper.prototype.currentSort = function () {
    var that = this;
    var activeSort = $(that.contextPrefix(".js-pagination")).data("activesort");
    return activeSort ? activeSort : that.DEFAULT_SORT;
};

PageHelper.prototype.currentSortDir = function () {
    var that = this;
    var activeSortDir = $(that.contextPrefix(".js-pagination")).data("activesortdir");
    activeSortDir = activeSortDir.trim();
    return activeSortDir ? activeSortDir : "DEFAULT";
};

PageHelper.prototype.totalPageCount = function () {
    var that = this;
    var pageCount = +$(that.contextPrefix(".js-pagination")).data("pagecount");
    return isNaN(pageCount) ? that.DEFAULT_COUNT : pageCount;
};

PageHelper.prototype.pageLimit = function () {
    var that = this;
    var pageLimit = +$(that.contextPrefix(".js-pageLimit option:selected")).val();
    return isNaN(pageLimit) ? that.DEFAULT_LIMIT : pageLimit;
};