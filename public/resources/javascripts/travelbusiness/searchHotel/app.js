'use strict';
var travelBusiness = angular.module('travel-business', ['ngDialog','ngCookies','jlareau.pnotify']);

travelBusiness.factory('MyHttpInterceptor', function ($q) {
    return {
      request: function (config) {
                  jQuery('#loading-id').show();
                  return config || $q.when(config);           
      },
 
      requestError: function (rejection) {
    	  jQuery('#loading-id').hide();
          return $q.reject(rejection);
      },
 
      // On response success
      response: function (response) {
    	  jQuery('#loading-id').hide();
          return response || $q.when(response);
      },
 
      // On response failture
      responseError: function (rejection) {
    	  jQuery('#loading-id').hide();
          return $q.reject(rejection);
      }
    };
});
travelBusiness.config(function ($httpProvider) {
   $httpProvider.interceptors.push('MyHttpInterceptor');  
})

/*travelBusiness.directive("starRating", function() {
  return {
    restrict : "A",
    template : "<ul class='rating'>" +
               "  <li ng-repeat='star in stars' style='float:left;' ng-class='star' ng-click='toggle($index)'>" +
               "    <i class='fa fa-star'></i>" + //&#9733
               "  </li>" +
               "</ul>",
    scope : {
      ratingValue : "=",
      max : "=",
      onRatingSelected : "&"
    },
    link : function(scope, elem, attrs) {
      var updateStars = function() {
        scope.stars = [];
        for ( var i = 0; i < scope.max; i++) {
          scope.stars.push({
            filled : i < scope.ratingValue
          });
        }
      };
      scope.toggle = function(index) {
        scope.ratingValue = index + 1;
        scope.onRatingSelected({
          rating : index + 1
        });
      };
      scope.$watch("ratingValue", function(oldVal, newVal) {
        if (newVal) { updateStars(); }
      });
    }
  };
});*/

travelBusiness.directive('ngSec',function(){
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


travelBusiness.directive('starRate', function($compile) {
	
	return {
		  link: function(scope, element, attrs) {
					  jQuery(element).rating({stars:attrs.value});
			  /*jQuery(element).rating({value:attrs.value});*/
	        }
	};
		 
	
	});


travelBusiness.directive('currencyRate', function($compile) {
	
	return {
		  replace: true,
		  require: 'ngModel',
		  link: function(scope, element, attrs) {
			  scope.$watch(attrs.ngModel, function(newValue) {
				  element.empty();
				  var calu = "";
				  var calu = parseFloat((1 / attrs.supplierCurr) * newValue);
				  calu = calu.toFixed(1);
				  jQuery(element).append("<b><span>"+calu+" <sup style='"+attrs.stylevalue+"'>"+attrs.agentCurr+"</sup></span></b>");
              }); 
	        }
	};
		 
	
	});