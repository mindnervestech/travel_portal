
angular.module('travel_portal').
	controller("ManageHotelImageController",['$scope', '$http', '$rootScope','$filter','$upload','ngDialog',
	                                         function($scope, $http, $rootScope,  $filter, $upload, ngDialog) {
		
		 var generalPic =null;
		 console.log(supplierCode);
	     $scope.selectGeneralPicImage = function($generalPic)
	     {
	    	
	    	 generalPic = $generalPic[0]; 
	    	    	 
	     }
	     $scope.img = "getImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.savegeneral = {};
	     $scope.savegeneralPic = function(){
	     
	    	
	    	$scope.savegeneral.supplierCode = supplierCode;
	  	   $scope.upload = $upload.upload({
	             url: '/savegeneralImg', 
	             method:'post',
	             data:$scope.savegeneral,
	             fileFormDataName: 'generalImg',
	             file:generalPic,
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	    	 console.log(data.generalPicture);
	            $scope.img = "getImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            //$scope.document = data[0].imgpath;
	     }); 
	     }
	     
		}]
);

angular.module('travel_portal').
	controller("hoteRoomController",['$scope', '$rootScope','$http',function($scope,$rootScope, $http){
	
			$scope.counterArray = [1,2,3,4,5,6,7,8,9,10];
		//	$scope.supplierCode = 3;
			console.log("hoteRoomController successfully initialized."+supplierCode);

			$scope.$watch("sel_room_type", function(selRoomType) {
				console.log("room type changed...  " + selRoomType);
				//call service to get the details of the selected room type..
			});
			
			$http.get("/roomtypes/"+supplierCode).success(function(response){
				console.log('success');
				$scope.hotelRoomTypes = response;
			});
			
			$scope.selectType = function(){
				alert($scope.roomTypeIns.roomId);
				$http.get('/roomtypesInfo/'+$scope.roomTypeIns.roomId).success(function(response){
					console.log(response);
					$scope.roomTypeIns.childAllowedFreeWithAdults = response.childAllowedFreeWithAdults; 
					$scope.roomTypeIns.roomname = response.roomType;
					$scope.roomTypeIns.extraBedAllowed = response.extraBedAllowed; 
					$scope.roomTypeIns.maxAdultOccSharingWithChildren = response.maxAdultOccSharingWithChildren;
					$scope.roomTypeIns.maxAdultOccupancy = response.maxAdultOccupancy; 
					$scope.roomTypeIns.roomType = response.roomType;
					$scope.roomTypeIns.maxOccupancy = response.maxOccupancy; 
					$scope.roomTypeIns.roomSuiteType = response.roomSuiteType;
					$scope.roomTypeIns.chargesForChildren = response.chargesForChildren;
					 $scope.childpolicy = response.roomchildPolicies;
					 
					 $scope.roomamenities =[];
					 
					 angular.forEach($scope.roomAmenities, function(obj, index){
						 obj.isSelected=false;
							angular.forEach(response.amenities, function(obj1, index){
								if ((obj.amenityId == obj1.amenityId)) {				 
									
									 obj.isSelected=true;
							    };
							});
							
						});
					 
					 for(var i=0;i<response.amenities.length;i++)
						 {
					 $scope.roomamenities.push(response.amenities[i].amenityId);
						 }
					 console.log($scope.roomamenities);
				});
				
			}
			
			
			$scope.initRoomTypePage = function() {
				//call the all room type service to get all the room types stored in db for supplier.
				
				
				/*$http.get("/hotel/roomtypes?supplierCode="+$scope.supplierCode).success(function(response) {
					$scope.hotelRoomTypes = response;
					console.log("###########");
					console.log(response);
				});*/
				
				//call the all room type service to get all the room types stored in db for supplier.
				$http.get("/room/amenities").success(function(response) {
					$scope.roomAmenities = response;
				});
			}
			
			$scope.roomamenities = [];
			 $scope.childpolicy = [];
			  $scope.childpolicy.push( {  } );
			 
			 $scope.newchildpolicy = function($event){
			        $scope.childpolicy.push( {  } );
			        $event.preventDefault();
			       // console.log($scope.childpolicy);
			       
			    };
			    
			    $scope.createroomtypeMsg = false;
			    
			    $scope.CreateRoomType =function(){
			    	$scope.roomTypeIns.supplierCode = $scope.supplierCode; //$scope.roomTypeIns;
			    	$scope.roomTypeIns.roomchildPolicies = $scope.childpolicy;
			    	$scope.roomTypeIns.roomamenities = $scope.roomamenities;
			    	
			    	console.log($scope.roomTypeIns);
			    				    	
			    	$http.post('/hotel/saveUpdateRoomType',$scope.roomTypeIns).success(function(data){
						console.log('success');
											
					}).error(function(data, status, headers, config) {
						console.log('ERROR');
					});
			    	
			    	
			    }
			  
			    $scope.roomChildDeleteId = function(){
					console.log("@#$%$%^&*(*&&^^#%%#%");
					console.log($scope.roomTypeIns.roomId);
					console.log($scope.roomTypeIns.roomchildPolicies);
					console.log("@#$%$%^&*(*&&^^#%%#%");
					$http.get('/deleteRoomchild/'+$scope.roomTypeIns.roomId)
					.success(function(){
						$scope.roomTypeIns.roomchildPolicies = [];
						$scope.childpolicy = [];//$scope.roomTypeIns.roomchildPolicies = [];
						console.log('success');
					});


				};
			   
				
			    
			    $scope.serviceClicked = function(e, roomTypeIns) {
					
					if($(e.target).is(":checked")) {
						$scope.roomamenities.push(roomTypeIns.amenityId);
					} else {
						DeleteItem(roomTypeIns);
					}
					
				}
			    /*|| (generalInfo.serviceId === obj.serviceId))*/
				
			    DeleteItem = function(roomTypeIns){
			    	
					angular.forEach($scope.roomamenities, function(obj, index){
						 if ((roomTypeIns.amenityId === obj)) {
					    	$scope.roomamenities.splice(index, 1);
					    
					       	return;
					    };
					  });
						  
					}
				
			
		}]
);


angular.module('travel_portal').
	controller("hoteProfileController",['$scope', '$http', '$rootScope','$filter','$upload','ngDialog',
    function($scope, $http, $rootScope,  $filter, $upload, ngDialog) {

	$rootScope.supplierCode = supplierCode;
		
	$scope.generalInfo = {};
	$scope.descrip = {};
	$scope.countries = [];
	$scope.cities = [];

	$scope.service_check = [];
	$scope.amenities_check = [];
	$scope.business_check = [];
	$scope.leisure_sport_check = [];

	$scope.name = '';

	$scope.contacts = [];
	$scope.mealdata = [];

	$scope.area= {
		areaInfo:[],
		supplierCode:''
	};

	$scope.formArr = [];
	for(var i=0;i<10;i++) {
		var j = {name:'',distance:'',km:'',minutes:''};
		$scope.formArr[i] = j;
	}

	$scope.area1={
		name:'',
		distance:'',
		km:'',
		minutes:''
	};

	for(var i=0;i<10;i++) {
		$scope.area.areaInfo.push($scope.area1);
	}

	$scope.newContact = function($event) {
		$scope.contacts.push( {  } );
		$scope.mealdata.child.push( { } );
		$event.preventDefault();
	};

	$scope.locationsearch= {
		findLocation:[],
		supplierCode:''
	};
	
	$scope.locationsearch.findLocation = [];

	$scope.locationsearch.findLocation.push( {  } );
	$scope.newLocation = function($event){
		$scope.locationsearch.findLocation.push( {  } );
		$event.preventDefault();
	};

	$scope.submitSearch1 = function (set) {
		$scope.setText = set;
	};

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
		$scope.starrating = response;
	});

	$http.get("/countries").success(function(response) {
		$scope.countries = response;
	}); 

	$http.get("/location").success(function(response) {
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
		$scope.business=response;
	});

	$http.get("/leisureSport").success(function(response){
		$scope.leisureSport=response;
	});

	$http.get("/salutation").success(function(response){
		$scope.salutation=response;
	});

	$http.get("/MealType").success(function(response){
		$scope.MealTypes=response;
	});

	$scope.onCountryChange = function() {
		$http.get('/cities/'+$scope.generalInfo.countryCode)
		.success(function(data){
			if(data) {
				$scope.cities = data;
			} else {
				$scope.cities = [];
			}
		});
	}
	
	$http.get('/findAllData/'+$rootScope.supplierCode).success(function(response) {
		$scope.getallData=response;
		console.log(response);
		
		
		$http.get('/cities/'+response.hotelgeneralinfo.countryCode)
		.success(function(data){
			if(data) {
				$scope.cities = data;
			} else {
				$scope.cities = [];
			}
		});
		
		$scope.generalInfo=response.hotelgeneralinfo;
		if (response.areaattractionsVM != undefined) {
			for(var i=0;i<response.areaattractionsVM.length;i++) {
				$scope.formArr[i] = response.areaattractionsVM[i];	
			}
		}
       $scope.HealthSafety = response.healthAndSafetyVM;
       $scope.HealthSafety.expiryDate = $filter('date')(response.healthAndSafetyVM.expiryDate, "yyyy-MM-dd");
       $scope.HealthSafety.expiryDate1 = $filter('date')(response.healthAndSafetyVM.expiryDate1, "yyyy-MM-dd");
       $scope.FirePrecaution = response.healthAndSafetyVM;
       $scope.ExitsAndCorridor = response.healthAndSafetyVM;
       $scope.AirCondition = response.healthAndSafetyVM;
       $scope.Lifts = response.healthAndSafetyVM;
       $scope.Bedrooms = response.healthAndSafetyVM;
       $scope.KitchenAndHygiene = response.healthAndSafetyVM;
       $scope.additionalInfo = response.healthAndSafetyVM;
       $scope.GaswaterHeaters = response.healthAndSafetyVM;
       $scope.ChildrenFaciliti = response.healthAndSafetyVM;
       $scope.SwimmingPool = response.healthAndSafetyVM;    
       
       $scope.locationsearch.findLocation = response.transportationdirectionsVM;
		$scope.internalInfo = response.hotelinternalinformation;
		$scope.contactInfo =  response.hotelcontactinformation;
		
		
		$scope.comunicationhotel = response.hotelcommuniction;
		$scope.comunicationhotel.Remail =response.hotelcommuniction.primaryEmailAddr;
		$scope.comunicationhotel.Rccmail =response.hotelcommuniction.ccEmailAddr;
		
		$scope.descrip = response.hoteldescription;

		$scope.bill = response.hotelbillinginformation;
		
		angular.forEach($scope.amenities, function(obj, index){
			angular.forEach(response.hotelamenities, function(obj1, index){
				if ((obj.amenitiesCode == obj1.amenitiesCode)) {
					$scope.amenities_check.push(obj.amenitiesCode);
					obj.isSelected=true;
				};
			});
		});

		angular.forEach($scope.leisureSport, function(obj, index){
			angular.forEach(response.hotelamenities, function(obj1, index){
				if ((obj.amenitiesCode == obj1.amenitiesCode)) {
					$scope.leisure_sport_check.push(obj.amenitiesCode);
					obj.isSelected=true;
				};
			});
		});


		angular.forEach($scope.business, function(obj, index){
			angular.forEach(response.hotelamenities, function(obj1, index){

				if ((obj.amenitiesCode == obj1.amenitiesCode)) {
					$scope.business_check.push(obj.amenitiesCode);
					obj.isSelected=true;
				};
			});
		});

		angular.forEach($scope.services, function(obj, index){
			angular.forEach(response.hoteldescription.services, function(obj1, index){

				if ((obj.serviceId == obj1)) {
					$scope.service_check.push(obj.serviceId);
					obj.isSelected=true;
				};
			});
		});
		
		 
	       angular.forEach($scope.kidClub, function(obj, index){
				angular.forEach(response.healthAndSafetyVM.monthkid, function(obj1, index){

					if ((obj.name == obj1)) {
						$scope.ChildrenkidClub.push(obj.name);
						obj.isSelected=true;
					};
				});
			});
	       
	       angular.forEach($scope.operational, function(obj, index){
				angular.forEach(response.healthAndSafetyVM.monthOperational, function(obj1, index){

					if ((obj.name == obj1)) {
						$scope.swimmingpoolOperation.push(obj.name);
						obj.isSelected=true;
					};
				});
			});
	       
	       angular.forEach($scope.cctv, function(obj, index){
				angular.forEach(response.healthAndSafetyVM.cctvArea, function(obj1, index){

					if ((obj.name == obj1)) {
						$scope.CCTVStatusArray.push(obj.name);
						obj.isSelected=true;
					};
				});
			});
	});

	$http.get("/MealTypeplan/"+$rootScope.supplierCode).success(function(response){
		$scope.MealType=response;
		if($scope.MealType != null )
			{
			$scope.mealRate = true;
			}
		
		angular.forEach($scope.MealType, function(obj, index){
			$scope.MealType[index].fromPeriod = $filter('date')($scope.MealType[index].fromPeriod, "yyyy-MM-dd");
			$scope.MealType[index].toPeriod = $filter('date')($scope.MealType[index].toPeriod, "yyyy-MM-dd");
			return;
		});
	});	

	$scope.addnew = function(){
		ngDialog.open({
			template: 'manage',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
	};
	
	$scope.findmap = function(find,scope){
		console.log(find);
		$scope.loactionAddr = find.locationAddr;
		ngDialog.open({
			template: '/assets/html/hotel_profile/showMap.html',
			scope : $scope,
			className: 'ngdialog-theme-default'				
		});
		
	}

	$scope.ShowTax = function(tax){
		$scope.taxQTY=false;
		if(tax==true) {
			$scope.taxQTY= false;
		}
		else {
			$scope.taxQTY= true;
		}
	}

	$scope.showMeal = function(mealRate) {
		$scope.mealRate=mealRate;
		// alert($scope.mealRate);	
	}

	$scope.closeNewmeal = function (){

		alert("llllllllllllll");
	}


	$scope.editdata = function(meal){

		$rootScope.mealIdData=meal;
		$scope.mealdata = meal;
		console.log($scope.mealdata);

		ngDialog.open({
			template: 'editing',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
	};


	$scope.setDeleteId = function(meal){
		console.log(meal.id);


		$http.get('/deletemealpolicy/'+meal.id)
		.success(function(){
			angular.forEach($scope.MealType, function(obj, index){
				if(obj.id == meal.id){
					$scope.MealType.splice(index,1);
				}

			});
			console.log('success');
		});

	};

	$scope.childDeleteId = function(){
		console.log("@#$%$%^&*(*&&^^#%%#%");
		console.log($rootScope.mealIdData.id);
		console.log("@#$%$%^&*(*&&^^#%%#%");
		$http.get('/deletechile/'+$rootScope.mealIdData.id)
		.success(function(){
			$scope.MealType.child = [];
			$scope.mealdata.child = $scope.MealType.child = [];

			console.log('success');
		});


	};
	
	$scope.cctv =[{
		name:'Entrance',
		isSelected:false
	},
	{name:'Lobby',isSelected:false},
	{name:'Corridors',isSelected:false},
	{name:'Fitness Centre',isSelected:false},
	{name:'Indoor Pools',isSelected:false},
	{name:'Indoor Pools',isSelected:false}];
	
	$scope.CCTVStatusArray=[];
	//cctvClicked($event,TVstatus)
	 $scope.cctvClicked = function(e, TVstatus){
			
			if($(e.target).is(":checked")) {
				$scope.CCTVStatusArray.push(TVstatus.name);
			} else {
				DeleteCCTVStatus(TVstatus);
			}
			console.log($scope.CCTVStatusArray);
		}
		
	 DeleteCCTVStatus = function(TVstatus){

			angular.forEach($scope.CCTVStatusArray, function(obj, index){
				if ((TVstatus.name === obj)) {
					$scope.CCTVStatusArray.splice(index, 1);

					return;
				};
			});

		}
	
	
	
	
	$scope.operational = [{
		name:'January',
		isSelected:false
	},
	{name:'February',isSelected:false},
	{name:'March',isSelected:false},
	{name:'April',isSelected:false},
	{name:'May',isSelected:false},
	{name:'June',isSelected:false},
	{name:'July',isSelected:false},
	{name:'August',isSelected:false},
	{name:'September',isSelected:false},
	{name:'October',isSelected:false},
	{name:'November',isSelected:false},
	{name:'December',isSelected:false}];
	
		
	$scope.swimmingpoolOperation=[];
    $scope.operationalClicked = function(e, SwimmingP){
		
		if($(e.target).is(":checked")) {
			$scope.swimmingpoolOperation.push(SwimmingP.name);
		} else {
			DeleteswimmingpoolOperation(SwimmingP);
		}
		console.log($scope.swimmingpoolOperation);
	}
	
    DeleteswimmingpoolOperation = function(SwimmingP){

		angular.forEach($scope.swimmingpoolOperation, function(obj, index){
			if ((SwimmingP.name === obj)) {
				$scope.swimmingpoolOperation.splice(index, 1);

				return;
			};
		});

	}
    
    
    $scope.kidClub = [{
		name:'January',
		isSelected:false
	},
	{name:'February',isSelected:false},
	{name:'March',isSelected:false},
	{name:'April',isSelected:false},
	{name:'May',isSelected:false},
	{name:'June',isSelected:false},
	{name:'July',isSelected:false},
	{name:'August',isSelected:false},
	{name:'September',isSelected:false},
	{name:'October',isSelected:false},
	{name:'November',isSelected:false},
	{name:'December',isSelected:false}];
    
	$scope.ChildrenkidClub=[];
		
	$scope.kidClubClicked = function(e, ChildrenF){
		
		if($(e.target).is(":checked")) {
			$scope.ChildrenkidClub.push(ChildrenF.name);
		} else {
			DeleteChildrenkidClub(ChildrenF);
		}
		console.log($scope.ChildrenkidClub);
	}
	
	DeleteChildrenkidClub = function(ChildrenF){

		angular.forEach($scope.ChildrenkidClub, function(obj, index){
			if ((ChildrenF.name === obj)) {
				$scope.ChildrenkidClub.splice(index, 1);

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
		console.log($scope.service_check);
	}

	DeleteItem = function(generalInfo){

		angular.forEach($scope.service_check, function(obj, index){
			if ((generalInfo.serviceId === obj)) {
				$scope.service_check.splice(index, 1);

				return;
			};
		});

	}

		
	
	
	$scope.saveFirePrecaution = function(){
		
		$scope.FirePrecaution.supplierCode = $rootScope.supplierCode;
		console.log($scope.FirePrecaution);
		$http.post('/saveUpdateFirePrecaution', $scope.FirePrecaution).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.saveDocumentation = function(){
		
		$scope.HealthSafety.supplierCode = $rootScope.supplierCode; 
		console.log($scope.HealthSafety);
		$http.post('/saveUpdateHealthSafety', $scope.HealthSafety).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveExitsAndCorridors = function(){
		
		$scope.ExitsAndCorridor.supplierCode = $rootScope.supplierCode; 
		console.log($scope.ExitsAndCorridor);
		$http.post('/saveUpdateExitsAndCorridor', $scope.ExitsAndCorridor).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveAirCondition = function(){
		
		$scope.AirCondition.supplierCode = $rootScope.supplierCode; 
		console.log($scope.AirCondition);
		$http.post('/saveUpdateAirCondition', $scope.AirCondition).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveLifts = function(){
		
		$scope.Lifts.supplierCode = $rootScope.supplierCode; 
		console.log($scope.Lifts);
		$http.post('/saveUpdateLifts', $scope.Lifts).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveBedroomsAsndBalconies = function(){
		
		$scope.Bedrooms.supplierCode = $rootScope.supplierCode; 
		console.log($scope.Bedrooms);
		$http.post('/saveUpdateBedroomsAsndBalconies', $scope.Bedrooms).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	
	$scope.saveKitchenAndHygiene = function(){
		
		$scope.KitchenAndHygiene.supplierCode = $rootScope.supplierCode; 
		console.log($scope.KitchenAndHygiene);
		$http.post('/saveUpdateKitchenAndHygiene', $scope.KitchenAndHygiene).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
     $scope.saveChildrenFaciliti = function(){
		
		$scope.ChildrenFaciliti.supplierCode = $rootScope.supplierCode; 
		$scope.ChildrenFaciliti.monthkid = $scope.ChildrenkidClub;
		console.log($scope.ChildrenFaciliti);
		$http.post('/saveUpdateChildrenFaciliti', $scope.ChildrenFaciliti).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
     
     $scope.saveSwimmingPool = function(){
 		
 		$scope.SwimmingPool.supplierCode = $rootScope.supplierCode; 
 		$scope.SwimmingPool.monthOperational = $scope.swimmingpoolOperation;
 		console.log($scope.SwimmingPool);
 		$http.post('/saveUpdateSwimmingPool', $scope.SwimmingPool).success(function(data){
 			console.log('success');
 			
 		}).error(function(data, status, headers, config) {
 			console.log('ERROR');
 		});
 		
 	}
     $scope.CCTVStatus={};
     $scope.saveCCTVstatus = function(){
  		
  		$scope.CCTVStatus.supplierCode = $rootScope.supplierCode; 
  		$scope.CCTVStatus.cctvArea = $scope.CCTVStatusArray;
  		console.log($scope.CCTVStatus);
  		$http.post('/saveUpdateCCTVstatus', $scope.CCTVStatus).success(function(data){
  			console.log('success');
  			
  		}).error(function(data, status, headers, config) {
  			console.log('ERROR');
  		});
  		
  	}
     
     $scope.saveAdditionalInfo = function(){
 		
 		$scope.additionalInfo.supplierCode = $rootScope.supplierCode; 
 		console.log($scope.additionalInfo);
 		$http.post('/saveUpdateAdditionalInfo', $scope.additionalInfo).success(function(data){
 			console.log('success');
 			
 		}).error(function(data, status, headers, config) {
 			console.log('ERROR');
 		});
 		
 	}
     
     $scope.saveGaswaterHeaters = function(){
  		
  		$scope.GaswaterHeaters.supplierCode = $rootScope.supplierCode; 
  		console.log($scope.GaswaterHeaters);
  		$http.post('/saveUpdateGaswaterHeaters', $scope.GaswaterHeaters).success(function(data){
  			console.log('success');
  			
  		}).error(function(data, status, headers, config) {
  			console.log('ERROR');
  		});
  		
  	}
    
     
    var files = null;
   $scope.selectProfileImage = function($files)
   {		
		files = $files[0]; 		/**/
   }
   
   $scope.savedoc = {};
   $scope.saveDocumens = function(){
	   $scope.savedoc.supplierCode = $rootScope.supplierCode;
	   $scope.upload = $upload.upload({
           url: '/savefiles', 
           method:'post',
           data:$scope.savedoc,
           fileFormDataName: 'file1',
           file:files,
          
   }).progress(function(evt) {
           console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
   }).success(function(data, status, headers, config) {
          console.log(data[0].imgpath);
          /*$scope.HealthSafety.expiryDate = $filter('date')(response.healthAndSafetyVM.expiryDate, "yyyy-MM-dd");*/
          $scope.document = data[0].imgpath;
   }); 
   }
	
	$scope.radioValue;
	$scope.generalInfoMsg = false;
	$scope.savegeneralinfo = function() {
		//$scope.generalInfo.supplierCode = "1234";
		console.log($scope.generalInfo.supplierCode);
		console.log($scope.generalInfo);

		$http.post('/saveGeneralInfo', $scope.generalInfo).success(function(data){
			console.log('success');
			console.log(data);
			$scope.generalInfoMsg = true;
			//$scope.MealType = data;		
			$scope.generalInfo.supplierCode=data.ID;
			$rootScope.supplierCode=data.ID;
			$rootScope.supplierName=data.NAME;
			$rootScope.supplierAddr=data.ADDR;

			//$scope.saveDescription($scope.supp);

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	$scope.mealpolicy={};

	$scope.mealPlanSuccessMsg = false;
	$scope.savemealplan = function(){

		$scope.mealpolicy.supplierCode = $rootScope.supplierCode ;

		$scope.mealpolicy.child=$scope.contacts;
		console.log($scope.mealpolicy);
		$http.post('/savemealpolicy',$scope.mealpolicy).success(function(data){
			console.log('success');
			$scope.mealPlanSuccessMsg = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});


	};

	$scope.mealPlanUpdateSuccessMsg = false;
	$scope.updatemealplan = function(){

		
		console.log($scope.mealdata);
		$http.post('/updatemealpolicy',$scope.mealdata).success(function(data){
			console.log('success');
			$scope.mealPlanUpdateSuccessMsg = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});


	};


	$scope.saveAreainfo = function() {


		$scope.area.supplierCode =$rootScope.supplierCode ;
		console.log($scope.formArr);
		console.log("//////////");
		$scope.area.areaInfo = $scope.formArr;
		console.log($scope.area);
		$http.post('/saveattraction',$scope.area).success(function(data){
			console.log('success');
			if(data == "yes")
			{
				$scope.attractionfind = "true";
				$scope.attractionnotfind = "false"
			}
			else
			{
				$scope.attractionfind = "false";
				$scope.attractionnotfind = "true";
			}
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}


	$scope.savetranspotDire = function() {

		$scope.locationfind=false;	
		$scope.locationnotfind = false;

		$scope.locationsearch.supplierCode = $rootScope.supplierCode ;
		console.log($scope.locationsearch);

		$http.post('/savetransportDir',$scope.locationsearch).success(function(data){
			console.log('success');
			if(data == "yes")
			{
				$scope.locationfind = "true";
				$scope.locationnotfind = "false";
			}
			else
			{
				$scope.locationnotfind = "true";
				$scope.locationfind = "false";
			}
			//$scope.locationfind; 
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}

	$scope.InternalInfoSuccess = false;

	$scope.saveInternalInfo = function() {
		$scope.internalInfo.supplierCode = $rootScope.supplierCode ;
		console.log($scope.internalInfo);
		$http.post('/updateInternalInfo',$scope.internalInfo).success(function(data){
			console.log('success');
			$scope.InternalInfoSuccess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}

	//$scope.value1=false;
	$scope.sameadd=function()
	{
     
		if($scope.contactInfo.reservationDetailSame == true)
		{
			$scope.contactInfo.rContactName=$scope.contactInfo.cPersonName;
			$scope.contactInfo.rEmailAddr=$scope.contactInfo.dEmailAddr;
			$scope.contactInfo.rTitle=$scope.contactInfo.cTitle;
			$scope.contactInfo.rDirectTelCode=$scope.contactInfo.dTelCode;
			$scope.contactInfo.rDirectTelNo=$scope.contactInfo.dTelNo;
			$scope.contactInfo.rExtNo=$scope.contactInfo.dExtNo;
		}
		else
		{
			$scope.contactInfo.rContactName="";
			$scope.contactInfo.rEmailAddr="";
			$scope.contactInfo.rTitle="";
			$scope.contactInfo.rDirectTelCode="";
			$scope.contactInfo.rDirectTelNo="";
			$scope.contactInfo.rExtNo="";


		}
	}

	$scope.Contactinfosuccess = false;
	$scope.SaveContactinfo = function() {

		$scope.contactInfo.supplierCode = $rootScope.supplierCode;
		console.log($scope.contactInfo);
		$http.post('/updateContactInfo',$scope.contactInfo).success(function(data){
			console.log('success');
			$scope.Contactinfosuccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});

	}

	$scope.BillinfoSucccess = false;
	$scope.Savebillinginfo=function()
	{
		$scope.bill.supplierCode=$rootScope.supplierCode;

		console.log($scope.bill);
		$http.post('/updatebillingInfo',$scope.bill).success(function(data){
			console.log('success');
			$scope.BillinfoSucccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});

	}


	$scope.communciatsuccess = false;
	$scope.savecommunciat=function()
	{
		$scope.comunicationhotel.supplierCode=$rootScope.supplierCode;
		console.log($scope.comunicationhotel);
		$http.post('/updateComunication',$scope.comunicationhotel).success(function(data){
			console.log('success');
			$scope.communciatsuccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});

	}
	$scope.Descriptinsuccess = false;
	$scope.saveDescription=function(){

		$scope.descrip.supplierCode = $rootScope.supplierCode;

		$scope.descrip.services = $scope.service_check;

		console.log($scope.descrip);
		console.log($scope.descrip.services);
		console.log("//////////////////");
		$http.post('/updateDescription',$scope.descrip).success(function(data){
			console.log('success');
			$scope.Descriptinsuccess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}

	$scope.leisuresucess = false;
	$scope.saveleisure_sport = function(){
		$scope.leisure.supplierCode= $rootScope.supplierCode;
		$scope.leisure.amenities=$scope.leisure_sport_check;
		console.log($scope.leisure);
		$http.post('/saveamenities',$scope.leisure).success(function(data){
			console.log('success');
			$scope.leisuresucess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.leisureClicked = function(e, leisure) {
		if($(e.target).is(":checked")) {
			$scope.leisure_sport_check.push(leisure.amenitiesCode);
		} else {
			DeleteleisureItem(leisure);
		}
		console.log($scope.leisure_sport_check);
	}


	DeleteleisureItem = function(leisure){
		angular.forEach($scope.leisure_sport_check, function(obj, index){
			if ((leisure.amenitiesCode == obj)) {
				$scope.leisure_sport_check.splice(index, 1);
				return;
			};
		});
	}

	$scope.businessSucess = false;
	$scope.savebusiness = function(){
		$scope.businessInfo.supplierCode=$rootScope.supplierCode;
		$scope.businessInfo.amenities=$scope.business_check;
		console.log($scope.businessInfo);
		$http.post('/saveamenities',$scope.businessInfo).success(function(data){
			console.log('success');
			$scope.businessSucess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.businessClicked = function(e, businessInfo) {

		if($(e.target).is(":checked")) {
			$scope.business_check.push(businessInfo.amenitiesCode);
		} else {
			DeletebusinessItem(businessInfo);
		}

	}

	DeletebusinessItem = function(businessInfo){

		angular.forEach($scope.business_check, function(obj, index){

			if ((businessInfo.amenitiesCode == obj)) {
				$scope.business_check.splice(index, 1);

				return;
			};
		});

	}

	$scope.amenitiesSuccess = false;
	$scope.saveAmenities = function(){
		//console.log($scope.amenitiesInfo.supplierCode);
		//$scope.amenitiesInfo.supplierCode=$scope.amenitiesInfo.supplierCode;
		$scope.amenitiesInfo.supplierCode= $rootScope.supplierCode;

		$scope.amenitiesInfo.amenities =$scope.amenities_check;
		console.log($scope.amenitiesInfo);
		$http.post('/saveamenities',$scope.amenitiesInfo).success(function(data){
			console.log('success');
			$scope.amenitiesSuccess = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.amenitiesClicked = function(e, amenitiesInfo) {

		if($(e.target).is(":checked")) {
			$scope.amenities_check.push(amenitiesInfo.amenitiesCode);
		} else {
			DeleteamenitiesItem(amenitiesInfo);
		}
		console.log("//////////");
		console.log($scope.amenities_check);
	}

	DeleteamenitiesItem = function(amenitiesInfo){
		angular.forEach($scope.amenities_check, function(obj, index){
			if ((amenitiesInfo.amenitiesCode == obj)) {
				$scope.amenities_check.splice(index, 1);
				return;
			};
		});

	}

}]);
