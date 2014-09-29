angular.module('travel_portal_hotel_profile').
	controller("generalInfoProfileController",['$scope', '$http',function($scope, $http){
	   $scope.countries = [];
	   $http.get("/countries").success(function(response) {
		   console.log("getting countries...");
		   $scope.countries = response;
	   });                             
    }]);
