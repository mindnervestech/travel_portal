'use strict';
/*angular.module('travel_portal',['ngRoute','rcWizard', 'rcForm', 'rcDisabledBootstrap']).config(function ($routeProvider) {*/
var app = angular.module('travel_portal',['ngRoute','ngDialog','jlareau.pnotify','angularFileUpload','multi-select']).config(function ($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: '/assets/html/hotel_profile/general_info.html',
		controller: 'hoteProfileController'
	})
	.when('/profile0', {
		templateUrl: '/assets/html/hotel_profile/general_info.html',
		controller: 'hoteProfileController'
	})
	.when('/supplier/:id', {
		templateUrl: '/assets/html/hotel_profile/general_info.html',
		controller: 'hoteProfileController'
	})
	.when('/profile1', {
		templateUrl: '/assets/html/hotel_profile/hotel_description.html',
		controller: 'hoteProfileController'
	})
	.when('/profile2', {
		templateUrl: '/assets/html/hotel_profile/internal_information.html',
		controller: 'hoteProfileController'
	})
	.when('/profile3', {
		templateUrl: '/assets/html/hotel_profile/contact_info.html',
		controller: 'hoteProfileController'
	})
	.when('/profile4', {
		templateUrl: '/assets/html/hotel_profile/hotel_communication.html',
		controller: 'hoteProfileController'
	})
	.when('/profile5', {
		templateUrl: '/assets/html/hotel_profile/billing_information.html',
		controller: 'hoteProfileController'
	})
	.when('/profile6', {
		templateUrl: '/assets/html/hotel_profile/addi_information.html',
		controller: 'hoteProfileController'
	})
	.when('/profile7', {
		templateUrl: '/assets/html/hotel_profile/meal_plan.html',
		controller: 'hoteProfileController'
	})
	.when('/profile8', {
		templateUrl: '/assets/html/hotel_profile/hotel_amenities.html',
		controller: 'hoteProfileController'
	})
	.when('/profile9', {
		templateUrl: '/assets/html/hotel_profile/business_amenities.html',
		controller: 'hoteProfileController'
	})
	.when('/profile10', {
		templateUrl: '/assets/html/hotel_profile/leisure_sport.html',
		controller: 'hoteProfileController'
	})
	.when('/profile11', {
		templateUrl: '/assets/html/hotel_profile/area_attraction.html',
		controller: 'hoteProfileController'
	})
	.when('/profile12', {
		templateUrl: '/assets/html/hotel_profile/transportation_directions.html',
		controller: 'hoteProfileController'
	})
	.when('/profile13', {
		templateUrl: '/assets/html/hotel_profile/health_and_safety.html',
		controller: 'hoteProfileController'
	})
	.when('/addHotelRoomType', {
		templateUrl: '/assets/html/room_profile/hotel_room_info.html',
		controller: 'hoteRoomController'
	})
	.when('/manageHotelImages', {
		templateUrl: '/assets/html/manage_hotel_images/manage_hotel_img.html',
		controller: 'ManageHotelImageController'
	})
	.when('/manageContracts', {
		templateUrl: '/assets/html/manage_contracts/manageContracts.html',
		controller: 'manageContractsController'
	})
		.when('/viewWebPage', {
		templateUrl: '/assets/html/view_your_web_page/view_web_page.html',
		controller: 'ManageHotelImageController'
	})
	.when('/Allotment', {
		templateUrl: '/assets/html/allotment_page/allotment.html',
		controller: 'allotmentController'
	})
	.when('/manageSuppliers', {
		templateUrl: '/assets/html/admin/manageSuppliers.html',
		controller: 'manageSuppliersController'
	})
	
	.when('/findSupplier', {
		templateUrl: '/assets/html/admin/find_supplier.html',
		controller: 'manageSuppliersController'
	})
		
	.when('/supplierAgreement', {
		templateUrl: '/assets/html/supplierAgreement/supplier_agreement.html',
		controller: 'supplierAgreementController'
	})
	
		.when('/manageSpecials', {
		templateUrl: '/assets/html/manage_specials/manageSpecials.html',
		controller: 'manageSpecialsController'
	})
	
	.when('/manageAgent', {
		templateUrl: '/assets/html/admin/manageAgent.html',
		controller: 'manageAgentController'
	})
	
	.when('/markupPage', {
		templateUrl: '/assets/html/admin/markup_page.html',
		controller: 'markupController'
	})
	
});

app.factory('MyHttpInterceptor', function ($q) {
    return {
      request: function (config) {
                  $('#loading-id').show();
                  return config || $q.when(config);           
      },
 
      requestError: function (rejection) {
                  $('#loading-id').hide();
          return $q.reject(rejection);
      },
 
      // On response success
      response: function (response) {
                  $('#loading-id').hide();
          return response || $q.when(response);
      },
 
      // On response failture
      responseError: function (rejection) {
                  $('#loading-id').hide();
          return $q.reject(rejection);
      }
    };
});
app.config(function ($httpProvider) {
   $httpProvider.interceptors.push('MyHttpInterceptor');  
})
app.directive('ngSec',function(){
	return {
		scope: { permission: '='},
	link: function(scope, element, attrs, ngModelCtrl) {
		
		console.log(scope.permission);
	var sec = scope.permission;  /* ${permissions};*/
	var obj = sec[attrs.ngSec];
	console.log(attrs.ngSec);
	if(typeof  obj === 'undefined') {

	}

	if(obj === 'false') {
	$(element).remove();
	}

	if(obj === 'R') {
	$(element).block(); //todo http://malsup.com/jquery/block
	$(element).children().off('click');
	}

	}
	}
	});

