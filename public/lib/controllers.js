angular.module('travel_portal').
	controller("hoteRoomController",['$scope', '$http',function($scope, $http){
		
			$scope.counterArray = [1,2,3,4,5,6,7,8,9,10];
			$scope.supplierCode = 12345;
			console.log("hoteRoomController successfully initialized.");

			$scope.$watch("sel_room_type", function(selRoomType) {
				console.log("room type changed...  " + selRoomType);
				//call service to get the details of the selected room type..
			});
			
			$scope.initRoomTypePage = function() {
				//call the all room type service to get all the room types stored in db for supplier.
				$http.get("/hotel/roomtypes?supplierCode="+$scope.supplierCode).success(function(response) {
					$scope.hotelRoomTypes = response;
				});
				
				//call the all room type service to get all the room types stored in db for supplier.
				$http.get("/room/amenities").success(function(response) {
					$scope.roomAmenities = response;
				});
			}
			
		}]
);


angular.module('travel_portal').
	controller("hoteProfileController",['$scope', '$http',function($scope, $http){
	
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
	

	$scope.saveInternalInfo = function() {
			$scope.internalInfo.code = $scope.supp ;
			console.log($scope.internalInfo);
			$http.post('/updateInternalInfo',$scope.internalInfo).success(function(data){
				console.log('success');
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
	}
		
	/*$scope.value1=false;
	$scope.sameadd=function()
	{
		alert("hiikkkk");
	}*/
	
	$scope.SaveContactinfo = function() {
		alert("Hiiiiiii..");
		$scope.contactInfo.code = $scope.supp;
		console.log($scope.contactInfo);
		$http.post('/updateContactInfo',$scope.contactInfo).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	$scope.savecommunciat=function()
	{
		$scope.comunicationhotel.code=$scope.supp;
		console.log($scope.comunicationhotel);
		alert("Hiiiiiiiii.");
		$http.post('/updateComunication',$scope.comunicationhotel).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
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
