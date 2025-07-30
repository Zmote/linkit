$(document).ready(function () {
    window.portalApp = {};
    portalApp.notifications = portalApp.notifications || new Notifications();
    new AppController().init();
});