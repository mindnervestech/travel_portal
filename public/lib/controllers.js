angular.module('travel_portal').
	controller("allotmentController",['$scope', '$http','$filter','$upload','ngDialog',
	                                         function($scope, $http, $filter, $upload, ngDialog) {
		
				
		$http.get("/currency").success(function(response) {
			$scope.currency = response;
		});
		console.log(supplierCode);
		$http.get("/roomtypes/"+supplierCode).success(function(response){
			console.log('success');
			$scope.hotelRoomTypes = response;
		});
		
		/*$http.get('/allotmentAllData/'+supplierCode).success(function(response) {
			
			console.log(response);
			
			
			$http.get('/getDates/'+response.roomId+"/"+response.currencyId)
			.success(function(data){
				if(data) {
					console.log(data);
					$scope.allotmentMarket1 =data;
					 angular.forEach($scope.allotmentMarket1, function(obj, index){
							$scope.allotmentMarket1[index].fromPeriod = $filter('date')(data[0].fromPeriod, "yyyy-MM-dd");
							$scope.allotmentMarket1[index].toPeriod = $filter('date')(data[0].toPeriod, "yyyy-MM-dd");
							return;
						});
							
					console.log($scope.allotmentMarket1);
				       
				} 
			});
			
			$http.get('/getRates/'+response.datePeriodId)
			.success(function(data){
				if(data) {
					console.log(data.rate);		
					$scope.rate = data.rate;
					angular.forEach($scope.rate,function(value,key) { 
						value.isSelected =0;
					});
					
					$scope.allotmentMarket = response;
					$scope.allotmentM = response.allotmentmarket;
					for(var i=0;i<$scope.allotmentM.length;i++) {
						angular.forEach($scope.allotmentM[i].rate,function(value1,key1) {
							angular.forEach($scope.rate,function(value,key) {
								console.log("rate id :"+value.id);
								console.log("allot id :"+value1.id);
								if(value1==value.id) {
									value.isSelected = i+1;
								}
							});
						});
					}
					$scope.allotmentM.allotmentId = response.allotmentId;
					
				    $scope.allotmentId = response.allotmentId;   
				} else {
					
				}
			});
			
		});*/
		
		$scope.showallotent =false;
		
		$scope.searchAllotment = function()
		{
		 //  $scope. = $scope.allotmentMarket.datePeriodId;	
			var arr = $scope.allotmentMarket.datePeriodId.split("@");
			$scope.allotmentMarket.formPeriod = arr[0];
			$scope.allotmentMarket.toPeriod = arr[1];
			$scope.allotmentMarket.supplierCode = supplierCode;
			
			console.log($scope.allotmentMarket);
			
			$http.post('/getallmentMarket', $scope.allotmentMarket).success(function(response){
				console.log('success');
				console.log(response);
				$scope.showallotent = true;
				
				$http.post('/getRates', $scope.allotmentMarket).success(function(data){
					console.log("-------");
					console.log(data);
					if(data.length>0) {
						console.log(data);		
						$scope.rate = data;
						angular.forEach($scope.rate,function(value,key) { 
							value.isSelected =0;
						});
						if(response!="") {
							$scope.allotmentMarket = response;
							$scope.allotmentM = response.allotmentmarket;
							for(var i=0;i<$scope.allotmentM.length;i++) {
								angular.forEach($scope.allotmentM[i].rate,function(value1,key1) {
									angular.forEach($scope.rate,function(value,key) {
										console.log("rate id :"+value.id);
										console.log("allot id :"+value1.id);
										if(value1==value.id) {
											value.isSelected = i+1;
										}
									});
								});
							}
							$scope.allotmentM.allotmentId = response.allotmentId;
						    $scope.allotmentId = response.allotmentId;
						}
						
					} else {
						
					}
				});
			}).error(function(response, status, headers, config) {
				console.log('ERROR');
			});
		}
		
		$scope.selectType = function()
		{
			

			if($scope.allotmentMarket.roomId != null && $scope.allotmentMarket.currencyName != null)
				{
			$http.get('/getDates/'+$scope.allotmentMarket.roomId+"/"+$scope.allotmentMarket.currencyName)
			.success(function(data){
				if(data) {
					console.log(data);
					$scope.allotmentMarket1 =data;
					 angular.forEach($scope.allotmentMarket1, function(obj, index){
							$scope.allotmentMarket1[index].fromPeriod = $filter('date')(data[0].fromDate, "yyyy-MM-dd");
							$scope.allotmentMarket1[index].toPeriod = $filter('date')(data[0].toDate, "yyyy-MM-dd");
							return;
						});
							
					console.log($scope.allotmentMarket1);
				       
				} 
			});
				}
		}
		
		$scope.onCurrencyChange = function(){
			
		console.log($scope.allotmentMarket.roomId); 
		console.log($scope.allotmentMarket.currencyName);
		
		if($scope.allotmentMarket.roomId != null && $scope.allotmentMarket.currencyName != null)
			{
			$http.get('/getDates/'+$scope.allotmentMarket.roomId+"/"+$scope.allotmentMarket.currencyName)
			.success(function(data){
				if(data) {
					console.log(data);
					$scope.allotmentMarket1 =data;
					 angular.forEach($scope.allotmentMarket1, function(obj, index){
							$scope.allotmentMarket1[index].fromPeriod = $filter('date')(data[0].fromDate, "yyyy-MM-dd");
							$scope.allotmentMarket1[index].toPeriod = $filter('date')(data[0].toDate, "yyyy-MM-dd");
							return;
						});
							
					console.log($scope.allotmentMarket1);
				       
				} 
			});
			}
				
		
		
		}
		
		/*$scope.onDateChoose = function(Id)
		{
			
			var arr = $scope.allotmentMarket.datePeriodId.split("@");
			$scope.allotmentMarket.toPeriod = arr[0];
			$scope.allotmentMarket.formPeriod = arr[1];
			console.log($scope.allotmentMarket);
			
			$http.post('/getRates', $scope.allotmentMarket)
			.success(function(data){
				
				if(data) {
					console.log(data);		
					$scope.rate = data;
				    angular.forEach($scope.rate,function(value,key) {
				    	value.isSelected = 0;
				    });   
				} else {
					
				}
			});
		};*/
		
		
		
		$scope.changeIsSelected = function(pIndex,index) {
			if($scope.rate[index].isSelected == 0) {
				$scope.rate[index].isSelected = pIndex+1;
			} else {
				$scope.rate[index].isSelected = 0;
			}
		};
		
		
		
		
		$scope.selectstopsell = function(allot)
		{
			
			allot.period = null;
			allot.choose = null;
			
		}
		
		$scope.selectFreesell = function(allot)
		{
			allot.choose = null;
			
		}
		
		$scope.allotmentMDeleteId = function(allot,index)
		{
			console.log(allot);
			
			$http.get('/deleteAllotmentMarket/'+allot.allotmentMarketId+'/'+ $scope.allotmentId)
			.success(function(){
				$scope.allotmentM.splice(index, 1);
				console.log('success');
			});
		}
		
		
		$scope.saveallotment = function()
		{
			//delete $scope.allotmentMarket
			
			/*if($scope.allotmentMarket.datePeriodId == null){
			var arr = $scope.allotmentMarket.datePeriodId.split("@");
			$scope.allotmentMarket.toPeriod = arr[0];
			$scope.allotmentMarket.formPeriod = arr[1];
			}*/
			
			$scope.allotmentMarket.allotmentmarket = $scope.allotmentM;
			$scope.allotmentMarket.supplierCode = supplierCode; 
			console.log($scope.allotmentMarket);
			 angular.forEach($scope.allotmentMarket.allotmentmarket, function(obj, index){
				 delete obj.allocatedCities;
			 });
			console.log($scope.allotmentMarket.allotmentmarket[0].allocatedCities);
			
			$http.post('/saveAllotment',$scope.allotmentMarket).success(function(data){
				console.log('success');
									
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
		
		}
	
		
		 $scope.allotmentM = [];
		  $scope.allotmentM.push( {  } );
		 
		 $scope.newallotmentM = function($event){
		        $scope.allotmentM.push( {  } );
		        $event.preventDefault();
		     
		       
		    };
		    
		    
		 		    
		    //////
		    //$scope.applyMarket = false;
		    $scope.selectedRatesId;
			console.log($scope.selectedRatesId);
			//$scope.webBrowsersGrouped =[];
			$scope.msClose;
			$scope.getSelectedCity = [];
			$scope.showAllotmentMarketTable = function(alloc) {
				Id = alloc.allotmentMarketId;
				console.log("-----------");	
				console.log(alloc);
				
				$scope.getSelectedCity.splice(0);
				$scope.selectedRatesId = Id;
				console.log($scope.selectedRatesId);
				$http.get('/getAllotmentMarketGroup/'+$scope.selectedRatesId)
				.success(function(data){
					if(data) {
						alloc.allocatedCities = [];
						
							for(var i = 0; i<data.length;i++){
								alloc.allocatedCities.push({
									name:'<strong>'+data[i].country.countryName+'</strong>',
									multiSelectGroup:true
								});
								for(var j =0; j<data[i].country.cityvm.length;j++){
									
									alloc.allocatedCities.push({
										name:data[i].country.cityvm[j].cityName,
										ticked:data[i].country.cityvm[j].tick
									});
									//allot.applyMarket = false;
								}
								alloc.allocatedCities.push({
									multiSelectGroup:false
								});
							}
							
							//console.log(alloc.allocatedCities);
							console.log(alloc.allocatedCities);
							//alloc.allocatedCities =  $scope.webBrowsersGrouped; //[{name:"pune",ticked:true}];	
					} 
				});
			}
			
			
			
			
			$scope.setSelection = function(allot) {
				
				$scope.getSelectedCity.splice(0);
				console.log(allot)
					for(var i = 0; i<allot.allocatedCities.length;i++){
						if(allot.allocatedCities[i].multiSelectGroup == undefined ){
							$scope.getSelectedCity.push({
                                name:allot.allocatedCities[i].name,
                                ticked:allot.allocatedCities[i].ticked,
                                countryCode : allot.allocatedCities[i].countryCode
							});
						}
					}
					$http.post('/setAllotmentCitySelection',{city:$scope.getSelectedCity,id:$scope.selectedRatesId})
					.success(function(data){
						$scope.flag = false;
						
					});
				
			};
	     
		}]
);


angular.module('travel_portal').
	controller("ManageHotelImageController",['$scope', '$http', '$rootScope','$filter','$upload','ngDialog',
	                                         function($scope, $http, $rootScope,  $filter, $upload, ngDialog) {
		
		 var generalPic =null;
		 $scope.opengeneralPic = false;
		 $scope.opengeneralPic1 = true;
		 
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
	            $scope.opengeneralPic = false;	
		    	 $scope.opengeneralPic1 = true;
	     }); 
	  	 
	     }
	     	    //
	     $scope.opengeneralpic1 = function()
	     {
	    	 $scope.opengeneralPic = false;	
	    	 $scope.opengeneralPic1 = true;
	     }
	     $scope.opengeneralImg = function()
	     {
	    	 $scope.opengeneralPic = true;	
	    	 $scope.opengeneralPic1 = false;
	     }
	     
	     /*------------------------------------------------------------*/
	     
	     var Lobbypic =null;
	     $scope.openLobbyPic = false;
		 $scope.openLobbyPic1 = true;
			 
	     $scope.selectHotelLobbyImage = function($Lobbypic)
	     {
	    	
	    	 Lobbypic = $Lobbypic[0]; 
	    	    	 
	     }
	     $scope.imgLobby = "getLobbyImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.saveLobbyImage = {};
	     $scope.saveHotelLobbyImage = function(){	     
	    	
	    	$scope.saveLobbyImage.supplierCode = supplierCode;
	  	   $scope.upload = $upload.upload({
	             url: '/saveLobbyImg', 
	             method:'post',
	             data:$scope.saveLobbyImage,
	             fileFormDataName: 'LobbyImage',
	             file:Lobbypic,
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	    	 console.log(data.hotel_Lobby);
	          $scope.imgLobby = "getLobbyImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            $scope.openLobbyPic = false;	
		    	 $scope.openLobbyPic1 = true;
	          
	     }); 
	     }
	     //
	     $scope.openLobbyspic1 = function()
	     {
	    	 $scope.openLobbyPic = false;	
	    	 $scope.openLobbyPic1 = true;
	     }
	     $scope.openLobbyImg = function()
	     {
	    	 $scope.openLobbyPic = true;	
	    	 $scope.openLobbyPic1 = false;
	     }
	     /*------------------------------------------------*/
	     var Roompic =null;
	     $scope.openRoomPic = false;
		 $scope.openRoomPic1 = true;
		 
		 $scope.selectHotelRoomImage = function($Roompic)
	     {
	    	
			 Roompic = $Roompic[0]; 
	    	    	 
	     }
		
		 $scope.imgRoom = "getRoomImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.saveRoomImage = {};
	     $scope.saveHotelRoomImage = function(){	     
	    	
	    	$scope.saveRoomImage.supplierCode = supplierCode;
	  	   $scope.upload = $upload.upload({
	             url: '/saveRoomImg', 
	             method:'post',
	             data:$scope.saveRoomImage,
	             fileFormDataName: 'RoomImage',
	             file:Roompic,
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	          $scope.imgRoom = "getRoomImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            $scope.openRoomPic = false;	
		    	 $scope.openRoomPic1 = true;
	          
	     }); 
	     }
		
		 //
	     $scope.openRoompic1 = function()
	     {
	    	 $scope.openRoomPic = false;	
	    	 $scope.openRoomPic1 = true;
	     }
		  $scope.openRoomImg = function()
	     {
	    	 $scope.openRoomPic = true;	
	    	 $scope.openRoomPic1 = false;
	     }
		 /*------------------------------------------------*/
	     var AmenitiesServicespic =null;
	     $scope.openAmenitiesServices = false;
		 $scope.openAmenitiesServices1 = true;
		 
		 $scope.selectAmenitiesServicesImage = function($AmenitiesServicespic)
	     {
	    	
			 AmenitiesServicespic = $AmenitiesServicespic[0]; 
	    	    	 
	     }
		
		 $scope.imgAmenitiesServices = "getAmenitiesServicesImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.saveAmenitiesServicesImage = {};
	     $scope.saveHotelAmenitiesServices = function(){	     
	    	
	    	$scope.saveAmenitiesServicesImage.supplierCode = supplierCode;
	  	   $scope.upload = $upload.upload({
	             url: '/saveAmenitiesServicesImg', 
	             method:'post',
	             data:$scope.saveAmenitiesServicesImage,
	             fileFormDataName: 'AmenitiesServicesImage',
	             file:AmenitiesServicespic,
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	          $scope.imgAmenitiesServices = "getAmenitiesServicesImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            $scope.openAmenitiesServices = false;	
		    	 $scope.openAmenitiesServices1 = true;
	          
	     }); 
	     }
		 
	     $scope.openAmenitiespic1 = function()
	     {
	    	 $scope.openAmenitiesServices = false;	
	    	 $scope.openAmenitiesServices1 = true;
	     }
		 $scope.openAmenitiesServicesImg = function()
	     {
	    	 $scope.openAmenitiesServices = true;	
	    	 $scope.openAmenitiesServices1 = false;
	     }
		 /*------------------------------------------------*/
	     var LeisureorSportspic =null;
	     $scope.openLeisureorSports = false;
		 $scope.openLeisureorSports1 = true;
		 
		 $scope.selectLeisureorSportsImage = function($LeisureorSportspic)
	     {
	    	
			 LeisureorSportspic = $LeisureorSportspic[0]; 
	    	    	 
	     }
		
		 $scope.imgLeisureorSports = "getLeisureorSportsImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.saveLeisureorSportsImage = {};
	     $scope.saveHotelLeisureorSports = function(){	     
	    	
	    	$scope.saveLeisureorSportsImage.supplierCode = supplierCode;
	  	   $scope.upload = $upload.upload({
	             url: '/saveLeisureorSportsImg', 
	             method:'post',
	             data:$scope.saveLeisureorSportsImage,
	             fileFormDataName: 'LeisureorSportsImage',
	             file:LeisureorSportspic,
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	          $scope.imgLeisureorSports = "getLeisureorSportsImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            $scope.openLeisureorSports = false;	
		    	 $scope.openLeisureorSports1 = true;
	          
	     }); 
	     }
	     //
	     $scope.openLeisurepic1 = function()
	     {
	    	 
	    	 $scope.openLeisureorSports = false;	
	    	 $scope.openLeisureorSports1 = true;
	     }
	     
		 $scope.openLeisureorSportsImg = function()
	     {
	    	 $scope.openLeisureorSports = true;	
	    	 $scope.openLeisureorSports1 = false;
	     }
		 /*------------------------------------------------*/
	     var Mappic =null;
	     $scope.openMapPic = false;
		 $scope.openMapPic1 = true;
		
		 $scope.selectHotelMapImage = function($selectHotelMapImage)
	     {
	    	
			 selectHotelMapImage = $selectHotelMapImage[0]; 
	    	    	 
	     }
		
		 $scope.imgMap = "getMapImagePath/"+supplierCode+"?d="+new Date().getTime();
	     $scope.saveMapImage = {};
	     $scope.saveHotelMapImage = function(){	     
	    	
	    	$scope.saveMapImage.supplierCode = supplierCode;
	  	   $scope.upload = $upload.upload({
	             url: '/saveMapImg', 
	             method:'post',
	             data:$scope.saveMapImage,
	             fileFormDataName: 'MapImage',
	             file:selectHotelMapImage,
	            
	     }).progress(function(evt) {
	             console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	     }).success(function(data, status, headers, config) {
	    	 console.log(data);   
	          $scope.imgMap = "getMapImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            $scope.openMapPic = false;	
		    	 $scope.openMapPic1 = true;
	          
	     }); 
	     }
	     
	     $scope.opeMappic1 = function()
	     {
	    	 $scope.openMapPic = false;	
	    	 $scope.openMapPic1 = true;
	     }
		 
		 $scope.opeMapImg = function()
	     {
	    	 $scope.openMapPic = true;	
	    	 $scope.openMapPic1 = false;
	     }
		 
		 
		 /*------------------------View Web Page------------------------------*/
		 $scope.amenities_check = [];
		 $scope.leisure_sport_check = [];
		 $scope.business_check = [];
		 
		 $http.get('/finddescripData/'+supplierCode).success(function(response) {
			
			 $scope.description = response.description; 
		 });
		
		 $http.get('/findAreaData/'+supplierCode).success(function(response) {
				console.log("!!!!!!!!!");
				console.log(response);
				$scope.areaAtt = response;					
		});	
		 
		 $http.get('/findAmenitiesData/'+supplierCode).success(function(response) {
				angular.forEach($scope.amenities, function(obj, index){
					angular.forEach(response, function(obj1, index){
						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.amenities_check.push(obj);
						};
					});
				});
			});
			
		 
				$http.get('/findAmenitiesData/'+supplierCode).success(function(response) {
				angular.forEach($scope.leisureSport, function(obj, index){
					angular.forEach(response, function(obj1, index){
						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.leisure_sport_check.push(obj);
						};
					});
				});
				});
			
			
				$http.get('/findAmenitiesData/'+supplierCode).success(function(response) {
				angular.forEach($scope.business, function(obj, index){
					angular.forEach(response, function(obj1, index){

						if ((obj.amenitiesCode == obj1.amenitiesCode)) {
							$scope.business_check.push(obj);
						};
					});
				});
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
						//$scope.childpolicy.push( {  } );
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
	controller("hoteProfileController",function($scope, $http, $location, $rootScope,$filter, $upload, ngDialog) {

		
		
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
	$scope.location = [];
	$http.get("/location").success(function(response) {
		for(var i =0 ; i<response.length; i++) {
			$scope.location.push({
				locationId:response[i].id,
				locationNm:response[i].name
			});
		}
		
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
				console.log("----");
				console.log(data);
				$scope.cities = [];
				for(var i = 0; i<data.length; i++){
					$scope.cities.push({
						cityCode:data[i].id,
						cityName:data[i].name
					});
				}
			} else {
				$scope.cities.splice(0);
			}
		});
	}
	
	$scope.getgeneralinfo = function(){
	
		$http.get('/findAllData/'+$rootScope.supplierCode).success(function(response) {
			$scope.getallData=response;
			console.log(response);
			
			
			$http.get('/cities/'+response.hotelgeneralinfo.countryCode)
			.success(function(data){
				if(data) {
					console.log("----");
					console.log(data);
					$scope.cities = [];
					for(var i = 0; i<data.length; i++){
						$scope.cities.push({
							cityCode:data[i].id,
							cityName:data[i].name
						});
					}
				} else {
					$scope.cities.splice(0);
				}
			});
			
			$scope.generalInfo=response.hotelgeneralinfo;
			    
			 
		});
		}
	
	$scope.getdescription = function()
	{
		$http.get('/finddescripData/'+$rootScope.supplierCode).success(function(response) {
			$scope.descrip = response;
			console.log("***********");
			console.log($scope.descrip);
			angular.forEach($scope.services, function(obj, index){
				angular.forEach(response.services, function(obj1, index){

					if ((obj.serviceId == obj1)) {
						$scope.service_check.push(obj.serviceId);
						obj.isSelected=true;
					};
				});
			});
		});
	}
	$scope.getInternalInfo = function()
	{
		$http.get('/findInternalData/'+$rootScope.supplierCode).success(function(response) {
			$scope.internalInfo = response;
		});
	}
	$scope.getContactInfo = function()
	{
	
		$http.get('/findContactData/'+$rootScope.supplierCode).success(function(response) {
			$scope.contactInfo =  response;
		});
	}
	$scope.getcommunicationInfo = function()
	{
		$http.get('/findCommunicationData/'+$rootScope.supplierCode).success(function(response) {
			$scope.comunicationhotel = response;
			$scope.comunicationhotel.Remail =response.primaryEmailAddr;
			$scope.comunicationhotel.Rccmail =response.ccEmailAddr;
		});
	}
	$scope.getBillInfo = function()
	{
		$http.get('/findBillData/'+$rootScope.supplierCode).success(function(response) {
			$scope.bill = response;
		});
	}
	$scope.getAmenitiesInfo = function()
	{
		$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
			angular.forEach($scope.amenities, function(obj, index){
				angular.forEach(response, function(obj1, index){
					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.amenities_check.push(obj.amenitiesCode);
						obj.isSelected=true;
					};
				});
			});
		});
		}
		$scope.getLeisureInfo = function()
		{
			$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
			angular.forEach($scope.leisureSport, function(obj, index){
				angular.forEach(response, function(obj1, index){
					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.leisure_sport_check.push(obj.amenitiesCode);
						obj.isSelected=true;
					};
				});
			});
			});
		}
		$scope.getBusinessInfo = function()
		{
			$http.get('/findAmenitiesData/'+$rootScope.supplierCode).success(function(response) {
			angular.forEach($scope.business, function(obj, index){
				angular.forEach(response, function(obj1, index){

					if ((obj.amenitiesCode == obj1.amenitiesCode)) {
						$scope.business_check.push(obj.amenitiesCode);
						obj.isSelected=true;
					};
				});
			});
		});
	}
		
		$scope.getAreaInfo = function(){
			$http.get('/findAreaData/'+$rootScope.supplierCode).success(function(response) {
				
				if (response != undefined) {
					for(var i=0;i<response.length;i++) {
						$scope.formArr[i] = response[i];	
					}
				}
			});
			
		}
		$scope.getTranAndDirInfo = function(){
			$http.get('/findTranDirData/'+$rootScope.supplierCode).success(function(response) {
			 $scope.locationsearch.findLocation = response;
			});
		}
		$scope.gethealthAndSafetyInfo = function(){
			$http.get('/findHealthAndSafData/'+$rootScope.supplierCode).success(function(response) {
				
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
			      
			       
			      
			       
			       angular.forEach(response.docInfo[0].imgpath, function(obj, index){
						response.docInfo[0].imgpath[index].datetime = $filter('date')(response.docInfo[0].imgpath[index].datetime, "yyyy-MM-dd");
						return;
					});
			       
			       $scope.document = response.docInfo[0].imgpath;
			       $scope.docId = response.docInfo[0].id;
			       console.log(response.docInfo);
			       
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
		}
	
	$rootScope.supplierCode = supplierCode;
	console.log("location :"+$location.path());
	if($location.path() == "/profile0")
	{
		$scope.getgeneralinfo();
	}
	if($location.path() == "/profile1")
	{
		$scope.getdescription();
	}
	if($location.path() == "/profile2")
	{
		$scope.getInternalInfo();
	}
	if($location.path() == "/profile3")
	{
		$scope.getContactInfo();
	}
	if($location.path() == "/profile4")
	{
		$scope.getcommunicationInfo();
	}
	if($location.path() == "/profile5")
	{
		$scope.getBillInfo();
	}
	if($location.path() == "/profile8")
	{
		$scope.getAmenitiesInfo();
	}
	if($location.path() == "/profile9")
	{
		$scope.getBusinessInfo();
	}
	if($location.path() == "/profile10")
	{
		$scope.getLeisureInfo();
	}
	if($location.path() == "/profile11")
	{
		$scope.getAreaInfo();
	}
	if($location.path() == "/profile12")
	{
		$scope.getTranAndDirInfo();
	}
	if($location.path() == "/profile13")
	{
		$scope.gethealthAndSafetyInfo();
	}
	
	
	

	$http.get("/MealTypeplan/"+$rootScope.supplierCode).success(function(response){
		$scope.MealType=response;
		console.log("-----------------");
		console.log($scope.MealType);
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
	$scope.mealpolicy= {};
	$scope.addnew = function(){
		$scope.mealpolicy= {};
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
	
	}

	$scope.closeNewmeal = function (){

		
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
	
	
	$scope.DeleteDocId = function(doc){
		console.log(doc.imgpathId);


		$http.get('/deleteDocument/'+$scope.docId+'/'+doc.imgpathId)
		.success(function(){
			
			console.log('success');
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
    
     
     $http.get('/findAllData/'+$rootScope.supplierCode).success(function(response) {
			$scope.getallData=response;
			console.log(response);
			
			
			$http.get('/cities/'+response.hotelgeneralinfo.countryCode)
			.success(function(data){
				if(data) {
					console.log("----");
					console.log(data);
					$scope.cities = [];
					for(var i = 0; i<data.length; i++){
						$scope.cities.push({
							cityCode:data[i].id,
							cityName:data[i].name
						});
					}
				} else {
					$scope.cities.splice(0);
				}
			});
			
			$scope.generalInfo=response.hotelgeneralinfo;
			    
			 
		});
     
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
			$http.get("/MealTypeplan/"+$rootScope.supplierCode).success(function(response){
				$scope.MealType=response;
				console.log("+++++++");
				console.log($scope.MealType);
				angular.forEach($scope.MealType, function(obj, index){
					$scope.MealType[index].fromPeriod = $filter('date')($scope.MealType[index].fromPeriod, "yyyy-MM-dd");
					$scope.MealType[index].toPeriod = $filter('date')($scope.MealType[index].toPeriod, "yyyy-MM-dd");
					return;
				});
			});	
			
			$scope.mealPlanSuccessMsg = true;
			//$scope.mealpolicy = [];
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

});

angular.module('travel_portal').
controller("manageContractsController",['$scope', '$rootScope','$http',function($scope,$rootScope, $http){
	
	$scope.showMeals = false;
	$scope.addMeal1 = 'no';
	$scope.showContract = true;
	$scope.showPeriod = false;
	$scope.diffRate = 'no';
	$scope.showRemoveRate = false;
	$scope.showSavedRates = false;
	$scope.rateObject = [];
	$scope.rateMeta = [];
	var showRateCount = 0;
	$scope.isUpdated = false;
	
	$http.get('/getRoomTypes').success(function(response){
		console.log(response);
		$scope.roomTypes = response;
	});
	
	$http.get('/getCurrency').success(function(response){
		console.log(response);
		$scope.currencies = response;
	});
	
	$http.get('/getMealTypes').success(function(response){
		console.log(response);
		$scope.mealTypes = response;
	});
	
	$scope.showData = function() {
		console.log($scope.formData.room);
		console.log($scope.formData.fromDate);
		console.log($scope.formData.toDate);
		console.log($scope.formData.currencyType);
		
		$http.get('/getRateData/'+$scope.formData.room+'/'+$scope.formData.fromDate+'/'+$scope.formData.toDate+'/'+$scope.formData.currencyType).success(function(response){
			console.log(response);
			$scope.rateMeta = response;
			console.log($scope.rateMeta);
			if(angular.isUndefined($scope.rateMeta) || $scope.rateMeta == "") {
				$scope.messageShow = "NO DATA FOUND";
				$scope.showRateUpdate = false;
				$scope.showPeriod = false;
			} else {
				$scope.messageShow = " ";
				$scope.showRateUpdate = true;
				$scope.showPeriod = false;
			}
			 
		});
		$scope.isUpdated = false;
		$scope.showSavedRates = true;
	};
	
	$scope.createNewRate = function() {
		
		for(var i=0;i<$scope.rateObject.length;i++) {
			$scope.rateObject.splice(i,1);
			console.log(i);
		}
		
		$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
			$scope.rateObject.push(response);
			console.log($scope.rateObject);
		});
		$scope.showPeriod = true;
	};
	
	$scope.showContractPage = function() {
		$scope.showContract = true;
		$scope.showPeriod = false;
	};
	
	$scope.isChecked = function(value,index) {
		if(value == 'yes') {
			$scope.rateObject.normalRate.rateDetails[index].includeMeals = true;
		} else {
			$scope.rateObject.normalRate.rateDetails[index].includeMeals = false;
		}
		
	};
	
	
	$scope.setWeekDay = function(value,flag,index) {
		if(flag == true) {
			$scope.rateObject[index].special.weekDays.push(value);
		} else {
			$scope.rateObject[index].special.weekDays.splice($scope.rateObject[index].special.weekDays.indexOf(value),1);
		}
		console.log($scope.rateObject[index].special.weekDays);
	};
	
	$scope.setRateMetaWeekDay = function(value,flag,index) {
		if(flag == true) {
			$scope.rateMeta[index].special.weekDays.push(value);
		} else {
			$scope.rateMeta[index].special.weekDays.splice($scope.rateMeta[index].special.weekDays.indexOf(value),1);
		}
		console.log($scope.rateMeta[index].special.weekDays);
	};
	
	$scope.addNewRuleforRate = function(index) {
		$scope.rateObject[index].cancellation.push({});
		
	};
	
	$scope.addNewRuleforSavedRate = function(index) {
		$scope.rateMeta[index].cancellation.push({});
		
	};
	
	$scope.removeRuleOfRate = function(index,parentIndex) {
		$scope.rateObject[parentIndex].cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSavedRate = function(index,parentIndex) {
		$scope.rateMeta[parentIndex].cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSpecialRate = function(index,parentIndex) {
		$scope.rateObject[parentIndex].special.cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSpecialRateMeta = function(index,parentIndex) {
		$scope.rateMeta[parentIndex].special.cancellation.splice(index,1);
	};
	
	$scope.addNewRuleforSpecialRate = function(index) {
		$scope.rateObject[index].special.cancellation.push({});
	};
	
	$scope.addNewRuleforSpecialRateMeta = function(index) {
		$scope.rateMeta[index].special.cancellation.push({});
	};
	
	$scope.addNewRate = function() {
		$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
			$scope.rateObject.push(response);
		});
		
			if($scope.rateObject.length > 0) {
				$scope.showRemoveRate = true;
			}
			
	};
	
	$scope.removeRate = function(index) {
		$scope.rateObject.splice(index,1);
		
		if($scope.rateObject.length == 0) {
			$scope.showRemoveRate = false;
		}
	};
	
	$scope.saveRate = function() {
		console.log($scope.rateObject.length);
		for(var i=0;i<$scope.rateObject.length;i++) {
			console.log(i);
			$scope.rateObject[i].roomType = $scope.formData.room;
			$scope.rateObject[i].fromDate = $scope.formData.fromDate;
			$scope.rateObject[i].toDate = $scope.formData.toDate;
			$scope.rateObject[i].currency = $scope.formData.currencyType;
		}
		
		$http.post('/saveRate', {"rateObject":$scope.rateObject}).success(function(data){
			console.log('success');
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	
	$scope.updateRateMeta = function() {
		
		$http.post('/updateRateMeta', {"rateObject":$scope.rateMeta}).success(function(data){
			console.log('success');
			$scope.isUpdated = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	
	$scope.deleteRate = function(id,index) {
		$http.get('/deleteRate/'+id).success(function(response){
			$scope.rateMeta.splice(index,1);
		});
	}
	
	
	////
    
    $scope.selectedRatesId;
	console.log($scope.selectedRatesId);
	//$scope.webBrowsersGrouped =[];
	$scope.msClose;
	$scope.getSelectedCity = [];
	
	$scope.showMarketTable = function(alloc) {
		 
			
		console.log(alloc.id);
		$scope.getSelectedCity.splice(0);
		if(angular.isUndefined(alloc.id)) {
			$scope.selectedRatesId = 0;
		} else {
			$scope.selectedRatesId = alloc.id;
		}
		console.log($scope.rateObject);
		console.log($scope.selectedRatesId);
		$http.get('/getMarketGroup/'+$scope.selectedRatesId)
		.success(function(data){
			if(data) {
				alloc.allocatedCities = [];
				
					for(var i = 0; i<data.length;i++){
						alloc.allocatedCities.push({
							name:'<strong>'+data[i].country.countryName+'</strong>',
							multiSelectGroup:true
						});
						for(var j =0; j<data[i].country.cityvm.length;j++){
							
							alloc.allocatedCities.push({
								name:data[i].country.cityvm[j].cityName,
								ticked:data[i].country.cityvm[j].tick
							});
							//allot.applyMarket = false;
						}
						alloc.allocatedCities.push({
							multiSelectGroup:false
						});
					}
					
					//console.log(alloc.allocatedCities);
					console.log(alloc.allocatedCities);
					//alloc.allocatedCities =  $scope.webBrowsersGrouped; //[{name:"pune",ticked:true}];	
			} 
		});
	}
	
	$scope.applyToAll = function(allot){
		for(var i = 0; i<allot.allocatedCities.length;i++){
			if(allot.allocatedCities[i].multiSelectGroup == undefined ){
				allot.allocatedCities[i].ticked = true;
			}
		}
		$scope.setSelection(allot);
	}
	
	
	$scope.setSelection = function(allot) {
		
		$scope.getSelectedCity.splice(0);
		console.log(allot.allocatedCities)
			for(var i = 0; i<allot.allocatedCities.length;i++){
				if(allot.allocatedCities[i].multiSelectGroup == undefined ){
					$scope.getSelectedCity.push({
                        name:allot.allocatedCities[i].name,
                        ticked:allot.allocatedCities[i].ticked,
                        countryCode : allot.allocatedCities[i].countryCode
					});
				}
			}
			$http.post('/setCitySelection',{city:$scope.getSelectedCity,id:$scope.selectedRatesId})
			.success(function(data){
				
			});
		
	};
	
	
	
	
	
	
}]);


angular.module('travel_portal').
controller("manageSuppliersController",['$scope', '$rootScope','$http',function($scope,$rootScope, $http){
		
			$scope.getData = function() {
				
				$http.get('/getPendingUsers').success(function(response){
					console.log(response);
					$scope.pendingUsers = response;
				});
				
				$http.get('/getApprovedUsers').success(function(response){
					console.log(response);
					$scope.approvedUsers = response;
				});
				
				$http.get('/getRejectedUsers').success(function(response){
					console.log(response);
					$scope.rejectedUsers = response;
				});
				
			}
		
		$scope.approvePending = function(userId,user) {
			$scope.userId = userId;
			$http.get('/approveUser/'+$scope.userId).success(function(response){
				$scope.pendingUsers.splice($scope.pendingUsers.indexOf(user),1);
				$scope.getData();
			});
		}
		
		$scope.rejectUser = function(userId,user) {
			$scope.userId = userId;
			$http.get('/rejectUser/'+$scope.userId).success(function(response){
				$scope.approvedUsers.splice($scope.approvedUsers.indexOf(user),1);
				$scope.getData();
			});
		}
		
		$scope.pendingUser = function(userId,user) {
			$scope.userId = userId;
			$http.get('/pendingUser/'+$scope.userId).success(function(response){
				$scope.rejectedUsers.splice($scope.rejectedUsers.indexOf(user),1);
				$scope.getData();
			});
		}
		
}]);