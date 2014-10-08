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
		$scope.services = response;
		
	}); 
	
	$http.get("/amenities").success(function(response){
		$scope.amenities=response;
	});
	
	$http.get("/business").success(function(response){
		console.log("getting business");
		$scope.business=response;
	});
	
	$http.get("/leisureSport").success(function(response){
		console.log("getting leisureSport");
		$scope.leisureSport=response;
	});
	
	$scope.service_check = [];
	$scope.amenities_check = [];
	$scope.business_check=[];
	$scope.leisure_sport_check=[];
	
	
	
$scope.leisureClicked = function(e, leisure) {
		
		if($(e.target).is(":checked")) {
			$scope.leisure_sport_check.push(leisure);
		} else {
			//DeletebusinessItem(businessInfo);
		}
		console.log("//////////");
		console.log($scope.leisure_sport_check);
	}
	
/*DeletebusinessItem = function(leisure){

angular.forEach($scope.leisure_sport_check, function(obj, index){
	
	 if ((leisure. === obj)) {
    	$scope.leisure_sport_check.splice(index, 1);
    
       	return;
    };
  });
	  
}*/	

	
	
	$scope.businessClicked = function(e, businessInfo) {
		
		if($(e.target).is(":checked")) {
			$scope.business_check.push(businessInfo);
		} else {
			//DeletebusinessItem(businessInfo);
		}
		console.log("//////////");
		console.log($scope.business_check);
	}
	
/*DeletebusinessItem = function(businessInfo){

angular.forEach($scope.business_check, function(obj, index){
	
	 if ((businessInfo. === obj.)) {
    	$scope.business_check.splice(index, 1);
    
       	return;
    };
  });
	  
}*/	



	$scope.amenitiesClicked = function(e, amenitiesInfo) {
		
		if($(e.target).is(":checked")) {
			$scope.amenities_check.push(amenitiesInfo.amenitiesCode);
		} else {
			DeleteamenitiesItem(amenitiesInfo);
		}
	}
	
	DeleteamenitiesItem = function(amenitiesInfo){
		
		angular.forEach($scope.amenities_check, function(obj, index){
			
			 if ((amenitiesInfo.amenitiesCode == obj)) {
		    	$scope.amenities_check.splice(index, 1);
		    
		       	return;
		    };
		  });
			  
		}
	
	
	$scope.serviceClicked = function(e, generalInfo) {
				
		if($(e.target).is(":checked")) {
			$scope.service_check.push(generalInfo.serviceId);
		} else {
			DeleteItem(generalInfo);
		}
		
	}
	
	
	DeleteItem = function(generalInfo){
				    			    	
		angular.forEach($scope.service_check, function(obj, index){
			 if ((generalInfo.serviceId === obj.serviceId) || (generalInfo.serviceId === obj.serviceId)) {
		    	$scope.service_check.splice(index, 1);
		    
		       	return;
		    };
		  });
			  
		}
	
	
	
	
	$scope.radioValue;
	
	$scope.savegeneralinfo = function() {
		console.log($scope.generalInfo);
		
		$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
			console.log('success');
			$rootScope.supplierCode=data;
			console.log($scope.supplierCode);
			//$scope.saveDescription($scope.supp);
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	

	$scope.saveInternalInfo = function() {
			$scope.internalInfo.code = $rootScope.supplierCode ;
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
		
		$scope.contactInfo.code = $rootScope.supplierCode;
				
		$http.post('/updateContactInfo',$scope.contactInfo).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	
	$scope.Savebillinginfo=function()
	{
		$scope.bill.code=$rootScope.supplierCode;
	
		console.log($scope.bill);
		$http.post('/updatebillingInfo',$scope.bill).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	
	}
	
	
	
	$scope.savecommunciat=function()
	{
		$scope.comunicationhotel.code=$rootScope.supplierCode;
		console.log($scope.comunicationhotel);
		$http.post('/updateComunication',$scope.comunicationhotel).success(function(data){
			console.log('success');
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveDescription=function(){
		
			$scope.descrip.supplierCode = $rootScope.supplierCode;
		
				$scope.descrip.services = $scope.service_check;
			
			/*console.log($scope.service_check);
			console.log("//////////////////");
			console.log($scope.descrip.service);
			console.log("//////////////////");*/
			console.log($scope.descrip);
			console.log("//////////////////");
			$http.post('/updateDescription',$scope.descrip).success(function(data){
					console.log('success');
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
	}
	
	$scope.saveAmenities=function(){
		$scope.amenitiesInfo.code=$rootScope.supplierCode;
		$scope.amenitiesInfo.amenities=$scope.amenities_check;
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
