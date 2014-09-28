var App = angular.module('MAdmin', ['ngRoute', 'ui.bootstrap']);

App.config(function ($routeProvider) {
    $routeProvider
        .when('/', {  templateUrl: 'templates/states/main.html', controller: 'MainController'})
        .when('/email-inbox', { templateUrl: 'templates/states/email-inbox.html', controller: 'NoneController'})
        .when('/extra-signin', { templateUrl: 'templates/states/extra-signin.html', controller: 'NoneController'})
        .when('/layout-left-sidebar', { templateUrl: 'templates/states/layout-left-sidebar.html', controller: 'NoneController'})
        .when('/layout-left-sidebar-collapsed', { templateUrl: 'templates/states/layout-left-sidebar-collapsed.html', controller: 'NoneController'})
        .when('/layout-right-sidebar', { templateUrl: 'templates/states/layout-right-sidebar.html', controller: 'NoneController'})
        .when('/layout-right-sidebar-collapsed', { templateUrl: 'templates/states/layout-right-sidebar-collapsed.html', controller: 'NoneController'})
        .when('/layout-horizontal-menu', { templateUrl: 'templates/states/layout-horizontal-menu.html', controller: 'NoneController'})
        .when('/layout-horizontal-menu-sidebar', { templateUrl: 'templates/states/layout-horizontal-menu-sidebar.html', controller: 'NoneController'})
        .when('/layout-fixed-topbar', { templateUrl: 'templates/states/layout-fixed-topbar.html', controller: 'NoneController'})
        .when('/layout-boxed', { templateUrl: 'templates/states/layout-boxed.html', controller: 'NoneController'})
        .when('/layout-hidden-footer', { templateUrl: 'templates/states/layout-hidden-footer.html', controller: 'NoneController'})
        .when('/layout-header-topbar', { templateUrl: 'templates/states/layout-header-topbar.html', controller: 'LayoutHeaderTopbarController'})
        .when('/layout-title-breadcrumb', { templateUrl: 'templates/states/layout-title-breadcrumb.html', controller: 'LayoutTitleBreadcrumbController'})
        .when('/ui-generals', { templateUrl: 'templates/states/ui-generals.html', controller: 'UiGeneralsController'})
        .when('/ui-panels', { templateUrl: 'templates/states/ui-panels.html', controller: 'NoneController'})
        .when('/ui-buttons', { templateUrl: 'templates/states/ui-buttons.html', controller: 'NoneController'})
        .when('/ui-tabs', { templateUrl: 'templates/states/ui-tabs.html', controller: 'UiTabsController'})
        .when('/ui-progressbars', { templateUrl: 'templates/states/ui-progressbars.html', controller: 'UiProgressbarsController'})
        .when('/ui-editors', { templateUrl: 'templates/states/ui-editors.html', controller: 'UiEditorsController'})
        .when('/ui-typography', { templateUrl: 'templates/states/ui-typography.html', controller: 'NoneController'})
        .when('/ui-modals', { templateUrl: 'templates/states/ui-modals.html', controller: 'UIModalsController'})
        .when('/ui-sliders', { templateUrl: 'templates/states/ui-sliders.html', controller: 'UiSlidersController'})
        .when('/ui-nestable-list', { templateUrl: 'templates/states/ui-nestable-list.html', controller: 'UiNestableListController'})
        .when('/ui-dropdown-select', { templateUrl: 'templates/states/ui-dropdown-select.html', controller: 'UiDropdownSelectController'})
        .when('/ui-icons', { templateUrl: 'templates/states/ui-icons.html', controller: 'NoneController'})
        .when('/ui-notific8-notifications', { templateUrl: 'templates/states/ui-notific8-notifications.html', controller: 'UINotific8NotificationsController'})
        .when('/ui-toastr-notifications', { templateUrl: 'templates/states/ui-toastr-notifications.html', controller: 'UiToastrNotificationsController'})
        .when('/ui-checkbox-radio', { templateUrl: 'templates/states/ui-checkbox-radio.html', controller: 'CheckboxRadioController'})
        .when('/ui-treeview', { templateUrl: 'templates/states/ui-treeview.html', controller: 'UiTreeviewController'})
        .when('/ui-portlets', { templateUrl: 'templates/states/ui-portlets.html', controller: 'UiPortletsController'})
        .when('/form-layouts', { templateUrl: 'templates/states/form-layouts.html', controller: 'FormLayoutsController'})
        .when('/form-basic', { templateUrl: 'templates/states/form-basic.html', controller: 'FormBasicController'})
        .when('/form-components', { templateUrl: 'templates/states/form-components.html', controller: 'FormComponentsController'})
        .when('/form-wizard', { templateUrl: 'templates/states/form-wizard.html', controller: 'FormWizardController'})
        .when('/form-xeditable', { templateUrl: 'templates/states/form-xeditable.html', controller: 'FormXeditableController'})
        .when('/form-validation', { templateUrl: 'templates/states/form-validation.html', controller: 'FormValidationController'})
        .when('/form-multiple-file-upload', { templateUrl: 'templates/states/form-multiple-file-upload.html', controller: 'FormMultipleFileUploadController'})
        .when('/form-dropzone-file-upload', { templateUrl: 'templates/states/form-dropzone-file-upload.html', controller: 'FormDropzoneFileUploadController'})
        .when('/frontend-one-page', { templateUrl: 'templates/states/frontend-one-page.html', controller: 'NoneController'})
        .when('/table-basic', { templateUrl: 'templates/states/table-basic.html', controller: 'NoneController'})
        .when('/table-responsive', { templateUrl: 'templates/states/table-responsive.html', controller: 'NoneController'})
        .when('/table-action', { templateUrl: 'templates/states/table-action.html', controller: 'TableActionController'})
        .when('/table-filter', { templateUrl: 'templates/states/table-filter.html', controller: 'TableFilterController'})
        .when('/table-advanced', { templateUrl: 'templates/states/table-advanced.html', controller: 'TableAdvancedController'})
        .when('/table-editable', { templateUrl: 'templates/states/table-editable.html', controller: 'TableEditableController'})
        .when('/table-datatables', { templateUrl: 'templates/states/table-datatables.html', controller: 'TableDatatablesController'})
        .when('/table-sample', { templateUrl: 'templates/states/table-sample.html', controller: 'TableSampleController'})
        .when('/table-export', { templateUrl: 'templates/states/table-export.html', controller: 'TableExportController'})
        .when('/grid-layout-div', { templateUrl: 'templates/states/grid-layout-div.html', controller: 'GridLayoutDivController'})
        .when('/grid-layout-table-1', { templateUrl: 'templates/states/grid-layout-table-1.html', controller: 'GridLayoutTable1Controller'})
        .when('/grid-layout-table-2', { templateUrl: 'templates/states/grid-layout-table-2.html', controller: 'GridLayoutTable2Controller'})
        .when('/grid-layout-2-table', { templateUrl: 'templates/states/grid-layout-2-table.html', controller: 'GridLayout2TableController'})
        .when('/grid-layout-ul-li', { templateUrl: 'templates/states/grid-layout-ul-li.html', controller: 'GridLayoutUlLiController'})
        .when('/grid-filter-with-ul-li', { templateUrl: 'templates/states/grid-filter-with-ul-li.html', controller: 'GridFilterWithUiLiController'})
        .when('/grid-filter-with-select', { templateUrl: 'templates/states/grid-filter-with-select.html', controller: 'GridFilterWithSelectController'})
        .when('/grid-double-sort', { templateUrl: 'templates/states/grid-double-sort.html', controller: 'GridDoubleSortController'})
        .when('/grid-deep-linking', { templateUrl: 'templates/states/grid-deep-linking.html', controller: 'GridDeepLinkingController'})
        .when('/grid-pagination-only', { templateUrl: 'templates/states/grid-pagination-only.html', controller: 'GridPaginationOnlyController'})
        .when('/grid-without-item-per-page', { templateUrl: 'templates/states/grid-without-item-per-page.html', controller: 'GridWithoutItemPerPageController'})
        .when('/grid-hidden-sort', { templateUrl: 'templates/states/grid-hidden-sort.html', controller: 'GridHiddenSortController'})
        .when('/grid-range-slider', { templateUrl: 'templates/states/grid-range-slider.html', controller: 'GridRangeSliderController'})
        .when('/grid-datepicker', { templateUrl: 'templates/states/grid-datepicker.html', controller: 'GridDatepickerController'})
        .when('/page-gallery', { templateUrl: 'templates/states/page-gallery.html', controller: 'PageGalleryController'})
        .when('/page-timeline', { templateUrl: 'templates/states/page-timeline.html', controller: 'NoneController'})
        .when('/page-blog', { templateUrl: 'templates/states/page-blog.html', controller: 'NoneController'})
        .when('/page-blog-item', { templateUrl: 'templates/states/page-blog-item.html', controller: 'NoneController'})
        .when('/page-about', { templateUrl: 'templates/states/page-about.html', controller: 'NoneController'})
        .when('/page-contact', { templateUrl: 'templates/states/page-contact.html', controller: 'PageContactController'})
        .when('/page-calendar', { templateUrl: 'templates/states/page-calendar.html', controller: 'PageCalendarController'})
        .when('/extra-profile', { templateUrl: 'templates/states/extra-profile.html', controller: 'ExtraProfileController'})
        .when('/extra-signin', { templateUrl: 'templates/states/extra-signin.html', controller: 'ExtraSigninController'})
        .when('/extra-signup', { templateUrl: 'templates/states/extra-signup.html', controller: 'ExtraSignupController'})
        .when('/extra-lock-screen', { templateUrl: 'templates/states/extra-lock-screen.html', controller: 'ExtraLockScreenController'})
        .when('/extra-user-list', { templateUrl: 'templates/states/extra-user-list.html', controller: 'ExtraUserListController'})
        .when('/extra-invoice', { templateUrl: 'templates/states/extra-invoice.html', controller: 'NoneController'})
        .when('/extra-faq', { templateUrl: 'templates/states/extra-faq.html', controller: 'NoneController'})
        .when('/extra-pricing-table', { templateUrl: 'templates/states/extra-pricing-table.html', controller: 'NoneController'})
        .when('/extra-blank', { templateUrl: 'templates/states/extra-blank.html', controller: 'NoneController'})
        .when('/extra-404', { templateUrl: 'templates/states/extra-404.html', controller: 'Extra404Controller'})
        .when('/extra-500', { templateUrl: 'templates/states/extra-500.html', controller: 'Extra500Controller'})
        .when('/email-inbox', { templateUrl: 'templates/states/email-inbox.html', controller: 'EmailInboxController'})
        .when('/email-compose-mail', { templateUrl: 'templates/states/email-compose-mail.html', controller: 'EmailComposeMailController'})
        .when('/email-view-mail', { templateUrl: 'templates/states/email-view-mail.html', controller: 'NoneController'})
        .when('/charts-flotchart', { templateUrl: 'templates/states/charts-flotchart.html', controller: 'ChartsFlotChartController'})
        .when('/charts-chartjs', { templateUrl: 'templates/states/charts-chartjs.html', controller: 'ChartsChartJsController'})
        .when('/charts-highchart-line', { templateUrl: 'templates/states/charts-highchart-line.html', controller: 'ChartsHighchartLineController'})
        .when('/charts-highchart-area', { templateUrl: 'templates/states/charts-highchart-area.html', controller: 'ChartsHighchartAreaController'})
        .when('/charts-highchart-column-bar', { templateUrl: 'templates/states/charts-highchart-column-bar.html', controller: 'ChartsHighchartColumnBarController'})
        .when('/charts-highchart-pie', { templateUrl: 'templates/states/charts-highchart-pie.html', controller: 'ChartsHighchartPieController'})
        .when('/charts-highchart-scatter-bubble', { templateUrl: 'templates/states/charts-highchart-scatter-bubble.html', controller: 'ChartsHighchartScatterBubbleController'})
        .when('/charts-highchart-dynamic', { templateUrl: 'templates/states/charts-highchart-dynamic.html', controller: 'ChartsHighchartDynamicController'})
        .when('/charts-highchart-combinations', { templateUrl: 'templates/states/charts-highchart-combinations.html', controller: 'ChartsHighchartCombinationController'})
        .when('/charts-highchart-more', { templateUrl: 'templates/states/charts-highchart-more.html', controller: 'ChartsHighchartMoreController'})
        .when('/animations', { templateUrl: 'templates/states/animations.html', controller: 'NoneController'})
        .otherwise({
        redirectTo: '/'
      });
  });