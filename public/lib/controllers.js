angular.module('travel_portal_hotel_profile').
	controller("generalInfoProfileController",['$scope', '$http',function($scope, $http){
	
	$scope.generalInfo = {};
	$scope.countries = [];
	$scope.cities = [];
	
	
	$http.get("/hotelchains").success(function(response) {
		$scope.hotelchain = response;
		
	}); 
	
	$http.get("/currency").success(function(response) {
		$scope.currency = response;
		
	});
	
	$http.get("/hotelbrands").success(function(response) {
			$scope.hotelbrand = response;
				
	});
	
	
	$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
					
	});
	
		
	$http.get("/starrating").success(function(response) {
		console.log("getting rate...");
		$scope.starrating = response;
						
	});
	
	$http.get("/countries").success(function(response) {
		console.log("getting countries...");
		$scope.countries = response;
	}); 
	
	$http.get("/location").success(function(response) {
		console.log("getting Location...");
		$scope.location = response;
		
	}); 
	
	$http.get("/shoppingfacility").success(function(response) {
		$scope.shoppingfacility = response;
		
	}); 

	$http.get("/nightlife").success(function(response) {
		$scope.nightlife = response;
		
	}); 
	

	$http.get("/services").success(function(response) {
		console.log("getting services...");
		$scope.services = response;
		
	}); 
	
	$scope.radioValue;
	$scope.supp;
	
	$scope.savegeneralinfo = function() {
		console.log($scope.generalInfo);
		
		$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
			console.log('success');
			$scope.supp=data;
			console.log($scope.supp);
			//$scope.saveDescription($scope.supp);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	
	$scope.saveDescription=function(){
			$scope.descrip.code = $scope.supp;
			console.log($scope.descrip);
			$http.post('/updateDescription',$scope.descrip).success(function(data){
					console.log('success');
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
	}

	$scope.onCountryChange = function() {
		console.log($scope.generalInfo.countryCode);
		$http.get('/cities/'+$scope.generalInfo.countryCode)
		.success(function(data){
			if(data) {
				$scope.cities = data;
				console.log($scope.cities);
			} else {
				$scope.cities = [];
			}
		});

	}


}]);
