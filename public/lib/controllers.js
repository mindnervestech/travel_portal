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
	controller("hoteProfileController",['$scope', '$http', '$rootScope',function($scope, $http, $rootScope){
	
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
/*	$scope.carts = [];*/
	
	$scope.serviceClicked = function(e, generalInfo) {
		console.log(generalInfo);
		console.log("*************");
		console.log($scope.services);
		
		if($(e.target).is(":checked")) {
				newItem(generalInfo);
		} else {
			//DeleteItem(generalInfo);
		}
	}
	
	function newItem(generalInfo) {
		
		return cartItem = {
			id:generalInfo.serviceId	
						
	    }
	}
	
	/*$scope.PushItems = function(c) {
		$scope.carts.push(c);
		
	}*/
	
	
	
	/*DeleteItem = function(generalInfo){
				    			    	
		angular.forEach($scope.services, function(obj, index){
			 if ((generalInfo.$$id === obj.id) || (generalInfo.id === obj.id)) {
		    	$scope.services.splice(index, 1);
		    		    			    	
		    	
		       	return;
		    };
		  });
			  
		}*/
	
	
	
	
	$scope.radioValue;
	//$scope.supp;
	//$rootScope.supp="";
	
	$scope.savegeneralinfo = function() {
		console.log($scope.generalInfo);
		
		$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
			console.log('success');
			$rootScope.supp=data;
			console.log($scope.supp);
			//$scope.saveDescription($scope.supp);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	

	$scope.saveInternalInfo = function() {
			$scope.internalInfo.code = $rootScope.supp ;
			console.log($scope.internalInfo);
			$http.post('/updateInternalInfo',$scope.internalInfo).success(function(data){
				console.log('success');
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
	}
		
	//$scope.value1=false;
	$scope.sameadd=function()
	{
		
	if($scope.value1==true)
	{
		$scope.contactInfo.RcontactName=$scope.contactInfo.contactName;
		$scope.contactInfo.RemailAddr=$scope.contactInfo.emailAddr;
		$scope.contactInfo.Rtitle=$scope.contactInfo.title;
		$scope.contactInfo.RteleCode=$scope.contactInfo.teleCode;
		$scope.contactInfo.RteleNumber=$scope.contactInfo.teleNumber;
		$scope.contactInfo.RExtension=$scope.contactInfo.Extension;
	}
	else
		{
		$scope.contactInfo.RcontactName="";
		$scope.contactInfo.RemailAddr="";
		$scope.contactInfo.Rtitle="";
		$scope.contactInfo.RteleCode="";
		$scope.contactInfo.RteleNumber="";
		$scope.contactInfo.RExtension="";

		
		}
	}
	
	$scope.SaveContactinfo = function() {
		
		$scope.contactInfo.code = $rootScope.supp;
		console.log("/////////////");
		console.log($scope.supp);
		console.log("/////////////");
		console.log($scope.contactInfo);
		
		
		
		
		$http.post('/updateContactInfo',$scope.contactInfo).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	
	$scope.Savebillinginfo=function()
	{
		$scope.bill.code=$rootScope.supp;
	
		console.log($scope.bill);
		$http.post('/updatebillingInfo',$scope.bill).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	
	}
	
	
	
	$scope.savecommunciat=function()
	{
		$scope.comunicationhotel.code=$rootScope.supp;
		console.log($scope.comunicationhotel);
		$http.post('/updateComunication',$scope.comunicationhotel).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveDescription=function(){
		
			$scope.descrip.code = $rootScope.supp;
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
