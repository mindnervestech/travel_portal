
angular.module('travel_portal').
controller("ApplicationController",function($scope,$http) {
	
	
	$scope.permission;
	$scope.init = function(data) {
		
		$scope.permission = data;
		console.log($scope.permission);
	};
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
});
angular.module('travel_portal').
controller("supplierAgreementController",['$scope','$rootScope','notificationService','$http','$filter','$upload',
                                         function($scope,$rootScope,notificationService, $http, $filter, $upload) {
	
	var navJs = 0;
	
	console.log(supplierCode);
	
	$scope.pdfFile = "/hotel_profile/getPdfPath/"+supplierCode;
	console.log($scope.pdfFile);
	console.log($rootScope.checkUser);
	if($rootScope.checkUser == "Admin"){
		$scope.uploadpdf = "true";
		$scope.showpdf = "false";
	}else{
		$scope.showpdf = "true";
		$scope.uploadpdf = "false";
	}
	
	$http.get('/getPdfPath1/'+supplierCode).success(function(response){
		console.log("***************");
		console.log(response.found);
		if(response.found == 1){
			$scope.imageShow = "true";
			$scope.labelShow = "false";
		}else{
			$scope.labelShow = "true";
			$scope.imageShow = "false";
		}
	});
	
	
	/*$http.get("/roomtypes/"+supplierCode).success(function(response){
		console.log('success');
		$scope.hotelRoomTypes = response;
	});*/
		
		
	 var Pdffile = null;
	   $scope.selectProfilePdf = function($files)
	   {		
		   Pdffile = $files[0]; 		
	   }
	   
	   $scope.savepdfData = {};
	   $scope.PdfUpload = function(){
		   
		   $scope.savepdfData.supplierCode = supplierCode;
		   $scope.upload = $upload.upload({
	           url: '/savepdf', 
	           method:'post',
	           data:$scope.savepdfData,
	           fileFormDataName: 'file1',
	           file:Pdffile,
	          
	   }).progress(function(evt) {
	           console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
	   }).success(function(data, status, headers, config) {
	          console.log(data);
	          notificationService.success("Upload Successfully");
	          $scope.showtext = false;
	          
	   }); 
	   }
	
}]
);


angular.module('travel_portal').
	controller("allotmentController",['$scope', '$http','notificationService','$rootScope','$filter','$upload','ngDialog',
	                                         function($scope, $http,notificationService,$rootScope, $filter, $upload, ngDialog) {
		
		//$scope.allot=({applyMarket = "true"});		
		
		$http.get("/currency").success(function(response) {
			$scope.currency = response;
		});
		console.log(supplierCode);
		$http.get("/roomtypes/"+supplierCode).success(function(response){
			console.log('success');
			$scope.hotelRoomTypes = response;
		});
		
		
		$scope.showallotent =false;
		
		$scope.searchAllotment = function()
		{
		
			var arr = $scope.allotmentMarket.datePeriodId.split("@");
			$scope.allotmentMarket.formPeriod = arr[0];
			$scope.allotmentMarket.toPeriod = arr[1];
			$scope.allotmentMarket.supplierCode = supplierCode;
			
			console.log($scope.allotmentMarket.allotmentmarket);
			console.log($scope.allotmentM);
			$scope.allotmentM = [];
			if($scope.allotmentMarket.allotmentmarket !=  undefined){
				delete $scope.allotmentMarket.allotmentmarket;
			}
			console.log($scope.allotmentMarket);
			$http.post('/getallmentMarket', $scope.allotmentMarket).success(function(response){
				console.log('success');
				console.log("**********");
				console.log(response);
				console.log(response.allotmentmarket);
				$scope.showallotent = true;
				if(response.allotmentmarket ==  undefined){
					$scope.allotmentM.push({applyMarket:"true"});
					$scope.showAllotmentMarketTable($scope.allotmentM);
				}else{
					for(var i=0;i<response.allotmentmarket.length;i++) {
						//if(response.allotmentmarket[i].applyMarket == "false"){
						$scope.showAllotmentMarketTable(response.allotmentmarket[i]);
						//}
					}
				}
							
				
				$http.post('/getRates', $scope.allotmentMarket).success(function(data){
					console.log("-------");
					//console.log(data);
					if(data.length>0) {
					
						$scope.rate = data;
					
						angular.forEach($scope.rate,function(value,key) { 
							value.isSelected =0;
						});
						if(response!="") {
							$scope.allotmentMarket = response;
							console.log($scope.allotmentM);
							$scope.allotmentM = response.allotmentmarket;
							console.log($scope.allotmentM);
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
			$scope.allotmentMarket.currencyName = $scope.currencyname;

			if($scope.allotmentMarket.roomId != null && $scope.allotmentMarket.currencyName != null)
				{
			$http.get('/getDates/'+$scope.allotmentMarket.roomId+"/"+$scope.allotmentMarket.currencyName)
			.success(function(data){
				if(data) {
					console.log(data);
					
					$scope.allotmentMarket1 =data;
					 angular.forEach($scope.allotmentMarket1, function(obj, index){
						 var i=0;
							$scope.allotmentMarket1[index].fromPeriod = $filter('date')(data[index][i], "yyyy-MM-dd");
							i++;
							$scope.allotmentMarket1[index].toPeriod = $filter('date')(data[index][i], "yyyy-MM-dd");
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
						 var i=0;
							$scope.allotmentMarket1[index].fromPeriod = $filter('date')(data[index][i], "yyyy-MM-dd");
							i++;
							$scope.allotmentMarket1[index].toPeriod = $filter('date')(data[index][i], "yyyy-MM-dd");
							return;
						});
							
					console.log($scope.allotmentMarket1);
				       
				} 
			});
			}
				
		
		
		}
		
		
		
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
			var r = confirm("Do You Want To Delete!");
		    if (r == true) {
			$http.get('/deleteAllotmentMarket/'+allot.allotmentMarketId+'/'+ $scope.allotmentId)
			.success(function(){
				angular.forEach($scope.allotmentM[index].rate,function(value1,key1) {
					angular.forEach($scope.rate,function(value,key) {
						console.log("rate id :"+value.id);
						console.log("allot id :"+value1.id);
						if(value1==value.id) {
							value.isSelected = 0;
						}
					});
				});
				$scope.allotmentM.splice(index, 1);
				console.log('success');
			});
		    }
		}
		
		
		$scope.saveallotment = function()
		{
			
			$scope.allotmentMarket.allotmentmarket = $scope.allotmentM;
			$scope.allotmentMarket.supplierCode = supplierCode; 
			console.log($scope.allotmentMarket);
		
			console.log($scope.allotmentMarket.allotmentmarket[0].allocatedCities);
			
			$http.post('/saveAllotment',$scope.allotmentMarket).success(function(data){
				console.log('success');
				 notificationService.success("Save Successfully");
									
			}).error(function(data, status, headers, config) {
				console.log('ERROR');
			});
		
		}
	
		
		 $scope.allotmentM = [];
		  $scope.allotmentM.push( {  } );
		 
		 $scope.newallotmentM = function($event){
		        $scope.allotmentM.push( {applyMarket:"true"} );
		        $scope.showAllotmentMarketTable($scope.allotmentM);
		        $event.preventDefault();
		     
		       
		    };
		    
		  		    
		    
		 ////////
		    
		    $scope.selectedRatesId;
			console.log($scope.selectedRatesId);
		
			$scope.msClose;
			$scope.getSelectedCity = [];
			
			$scope.showAllotmentMarketTable = function(allot) {
					
				console.log(allot);
			
				if(angular.isUndefined(allot.allotmentMarketId)) {
					$scope.selectedRatesId = 0;
				} else {
					$scope.selectedRatesId = allot.allotmentMarketId;
				}
				
				
				
				$scope.getSelectedCity.splice(0);
				
				console.log($scope.selectedRatesId);
				
				$http.get('/getAllotmentMarketGroup/'+$scope.selectedRatesId)
				.success(function(data){
					if(data) {
						allot.allocatedCities = [];
						
							for(var i = 0; i<data.length;i++){
								allot.allocatedCities.push({
									name:'<strong>'+data[i].country.countryName+'</strong>',
									multiSelectGroup:true
								});
								for(var j =0; j<data[i].country.cityvm.length;j++){
									if(allot.applyMarket == "false")
										{
									allot.allocatedCities.push({
										name:data[i].country.cityvm[j].cityName,
										ticked:data[i].country.cityvm[j].tick
									});
										}else{
											console.log("True all tick");
											allot.allocatedCities.push({
											name:data[i].country.cityvm[j].cityName,
											ticked:true
											});
											
											}
								
								}
								allot.allocatedCities.push({
									multiSelectGroup:false
								});
							}
												
							console.log(allot.allocatedCities);
								
					} 
				});
			}
			
			/*$scope.applyToAll = function(allot){
				console.log(allot);
				for(var i = 0; i<allot.allocatedCities.length;i++){
					if(allot.allocatedCities[i].multiSelectGroup == undefined ){
						allot.allocatedCities[i].ticked = true;
					}
				}
				console.log($scope.selectedRatesId);
				if($scope.selectedRatesId != 0) {
					$scope.setSelection(allot);
				}
				
			}*/
			
			
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
	controller("ManageHotelImageController",['$scope', '$http','notificationService','$rootScope','$filter','$upload','ngDialog',
	                                         function($scope, $http,notificationService, $rootScope,  $filter, $upload, ngDialog) {
		
		 var generalPic =null;
		 $scope.opengeneralPic = false;
		 $scope.opengeneralPic1 = true;
		 
		 console.log(supplierCode);
	     $scope.selectGeneralPicImage = function($generalPic)
	     {
	    	
	    	 generalPic = $generalPic[0]; 
	    	    	 
	     }
	     $scope.img = "/hotel_profile/getImagePath/"+supplierCode+"?d="+new Date().getTime();
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
	            $scope.img = "/hotel_profile/getImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	            notificationService.success("Replace Successfully");
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
	     $scope.imgLobby = "/hotel_profile/getLobbyImagePath/"+supplierCode+"?d="+new Date().getTime();
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
	          $scope.imgLobby = "/hotel_profile/getLobbyImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	          notificationService.success("Replace Successfully"); 
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
		
		 $scope.imgRoom = "/hotel_profile/getRoomImagePath/"+supplierCode+"?d="+new Date().getTime();
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
	          $scope.imgRoom = "/hotel_profile/getRoomImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	          notificationService.success("Replace Successfully");  
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
		
		 $scope.imgAmenitiesServices = "/hotel_profile/getAmenitiesServicesImagePath/"+supplierCode+"?d="+new Date().getTime();
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
	          $scope.imgAmenitiesServices = "/hotel_profile/getAmenitiesServicesImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	          notificationService.success("Replace Successfully"); 
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
		
		 $scope.imgLeisureorSports = "/hotel_profile/getLeisureorSportsImagePath/"+supplierCode+"?d="+new Date().getTime();
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
	          $scope.imgLeisureorSports = "/hotel_profile/getLeisureorSportsImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	          notificationService.success("Replace Successfully");  
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
		
		 $scope.imgMap = "/hotel_profile/getMapImagePath/"+supplierCode+"?d="+new Date().getTime();
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
	          $scope.imgMap = "/hotel_profile/getMapImagePath/"+data.supplierCode+"?d="+new Date().getTime();
	          notificationService.success("Replace Successfully"); 
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
	controller("hoteRoomController",['$scope','notificationService','$rootScope','$http',function($scope,notificationService,$rootScope, $http){
	    $scope.roomTypeIns = ( {chargesForChildren:"false"} );
	    
		$(".form-validate").validate({
	        errorPlacement: function(error, element){
	            error.insertAfter(element);
	        }
	    });
	    
			$scope.counterArray = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
		
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
			    	$scope.roomTypeIns.supplierCode = supplierCode; //$scope.roomTypeIns;
			    	$scope.roomTypeIns.roomchildPolicies = $scope.childpolicy;
			    	$scope.roomTypeIns.roomamenities = $scope.roomamenities;
			    	if($scope.roomTypeIns.chargesForChildren == "false")
			    		{
			    		$scope.roomTypeIns.roomchildPolicies = [];
			    		}
			    	
			    	console.log($scope.roomTypeIns);
			    				    	
			    	$http.post('/hotel/saveUpdateRoomType',$scope.roomTypeIns).success(function(data){
						console.log('success');
						$http.get("/roomtypes/"+supplierCode).success(function(response){
							console.log('success');
							notificationService.success("Save Successfully");
							$scope.hotelRoomTypes = response;
						});
						$scope.roomT = true;
											
					}).error(function(data, status, headers, config) {
						console.log('ERROR');
					});
			    	
			    	
			    }
			  
			    $scope.roomChildDeleteId = function(index){
					console.log($scope.childpolicy);
					console.log($scope.childpolicy[$scope.childpolicy.length-1].roomchildPolicyId);
					console.log($scope.roomTypeIns.roomchildPolicies);
					console.log("@#$%$%^&*(*&&^^#%%#%");
								
					if($scope.childpolicy[$scope.childpolicy.length-1].roomchildPolicyId != undefined){
					var r = confirm("Do You Want To Delete!");
				    if (r == true) {
					$http.get('/deleteRoomchild/'+$scope.childpolicy[$scope.childpolicy.length-1].roomchildPolicyId)
					.success(function(){
					//	$scope.roomTypeIns.roomchildPolicies = [];
					//	$scope.childpolicy = [];//$scope.roomTypeIns.roomchildPolicies = [];
						console.log('success');
						$scope.childpolicy.splice($scope.childpolicy.length-1,1);
						//$scope.childpolicy.push( {  } );
					});
				    }
					}else{
						$scope.childpolicy.splice($scope.childpolicy.length-1,1);
					}


				};
			   $scope.roomT = true;
				$scope.clearData = function(){
					$scope.roomT = false;
					$scope.roomTypeIns = null;
					$scope.childpolicy = [];
					angular.forEach($scope.roomAmenities, function(obj, index){
						 obj.isSelected=false;
					 });
					console.log($scope.roomTypeIns);
				}
			    
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
	controller("hoteProfileController",function($scope, $http,$routeParams,$location,notificationService,$rootScope,$filter, $upload, ngDialog) {

		
		
		$(".form-validate").validate({
		//console.log("Hiiiii");
	        errorPlacement: function(error, element){
	            error.insertAfter(element);
	        }
	    });
		
		console.log("$$$$$$&$$$$$");
		console.log(permissions);
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
	

	$scope.locationsearch.findLocation.push({});
	$scope.newLocation = function($event){
		console.log("newLocation");
		$scope.locationsearch.findLocation.push( {  } );
		$event.preventDefault();
	};

	$scope.submitSearch1 = function (set) {
		$scope.setText = set;
	};

	$scope.init = function () {
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
		
		
		  $http.get('/findAllData/'+$rootScope.supplierCode).success(function(response) {
				$scope.getallData=response;
				console.log(response);
				$rootScope.hotelName = response.hotelgeneralinfo.hotelNm;
					
				angular.forEach($scope.currency, function(obj, index){

					if ((response.hotelgeneralinfo.currencyCode == $scope.currency[index].currencyCode)) {
						$rootScope.currencyname = $scope.currency[index].currencyName ;					
					};
				});
							
				console.log(response.hotelgeneralinfo.isAdmin)
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
				   $rootScope.checkUser = response.hotelgeneralinfo.isAdmin; 
				 
			});
	}
	
	
	
	$scope.getgeneralinfo = function(){
	
		$http.get('/findAllData/'+$rootScope.supplierCode).success(function(response) {
			$scope.getallData=response;
			$rootScope.hotelName = response.hotelgeneralinfo.hotelNm;
		
			
			$http.get('/cities/'+response.hotelgeneralinfo.countryCode)
			.success(function(data){
				if(data) {
				
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
			  $rootScope.checkUser = response.hotelgeneralinfo.isAdmin; 
			 
		});
		}
	
	$scope.getdescription = function()
	{
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
		$http.get("/salutation").success(function(response){
			$scope.salutation=response;
		});
		
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
		$http.get("/salutation").success(function(response){
			$scope.salutation=response;
		});
		
		$http.get('/findBillData/'+$rootScope.supplierCode).success(function(response) {
			$scope.bill = response;
			console.log("<><><<><><><><><");
			console.log(response);
		});
	}
	
	$scope.getMealInfo = function(){
		
		$http.get("/MealType").success(function(response){
			$scope.MealTypes=response;			
			
		});
		
		
		$http.get("/MealTypeplan/"+$rootScope.supplierCode).success(function(response){
			$scope.MealType=response;
			console.log("-----------------");
			console.log($scope.MealType);
			if($scope.MealType != null )
				{
				$scope.mealRate = true;
				$scope.haveMeal = "true";
				
				}else
					{
					$scope.haveMeal = "false";
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
			$scope.contacts = [];
			$scope.mealpolicy= ({taxIncluded : "true"});
			//$scope.cont= ({chargeType:"true"});
			ngDialog.open({
				template: '/assets/html/hotel_profile/create_meal_plan.html',
				scope : $scope,
				//controller:'hoteProfileController',
				className: 'ngdialog-theme-default'
			});
			
			
		};
		
	}
	
	$scope.getAmenitiesInfo = function()
	{
		$http.get("/amenities").success(function(response){
			$scope.amenities=response;
		});
		
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
			$http.get("/business").success(function(response){
				$scope.business=response;
			});

			$http.get("/leisureSport").success(function(response){
				$scope.leisureSport=response;
			});
			
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
				console.log("getTranAndDirInfo :: "+response);
				if(response != '')
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
	
		if($routeParams.id == undefined || $routeParams.id == null )
			{
			$rootScope.supplierCode = supplierCode;
			}
		else{
			$rootScope.supplierCode =$routeParams.id;
			}
		
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
	if($location.path() == "/profile7")
	{
		$scope.getMealInfo();
	}
	
	if($location.path() == "/profile8")
	{
		$scope.getAmenitiesInfo();
	}
	if($location.path() == "/profile9")
	{
		$scope.getBusinessInfo();
	}
	/*if($location.path() == "/profile10")
	{
		$scope.getLeisureInfo();
	}*/
	if($location.path() == "/profile10")
	{
		$scope.getAreaInfo();
	}
	if($location.path() == "/profile11")
	{
		$scope.getTranAndDirInfo();
	}
	if($location.path() == "/profile12")
	{
		$scope.gethealthAndSafetyInfo();
	}
	
	
	

	
	
	$scope.findmap = function(find,scope){
		console.log(find);
		$scope.loactionAddr = find.locationAddr;
		ngDialog.open({
			template: '/assets/html/hotel_profile/showMap.html',
			scope : $scope,
			className: 'ngdialog-theme-default'				
		});
		$rootScope.$on('ngDialog.opened', function (e, $dialog) {
			$('#testDiv3').slimScroll({
		          color: '#00f'
		      });
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
			template: '/assets/html/hotel_profile/edit_meal_plan.html',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
	};
	
	
	$scope.DeleteDocId = function(doc){
		console.log(doc);

		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get('/deleteDocument/'+$scope.docId+'/'+doc.imgpathId)
		.success(function(){
			
			console.log('success');
			$http.get('/findHealthAndSafData/'+$rootScope.supplierCode).success(function(response) {
				console.log(response);
				
				 angular.forEach(response.docInfo[0].imgpath, function(obj, index){
						response.docInfo[0].imgpath[index].datetime = $filter('date')(response.docInfo[0].imgpath[index].datetime, "yyyy-MM-dd");
						return;
					});
				 $scope.document = response.docInfo[0].imgpath;
				  $scope.docId = response.docInfo[0].id;
			});
		});
	    }
		};

	$scope.setDeleteId = function(meal){
		console.log(meal.id);

		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get('/deletemealpolicy/'+meal.id)
		.success(function(){
			angular.forEach($scope.MealType, function(obj, index){
				if(obj.id == meal.id){
					$scope.MealType.splice(index,1);
				}

			});
			console.log('success');
		});
	    }
	};

	$scope.childDeleteId = function(){
		
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get('/deletechile/'+$rootScope.mealIdData.id)
		.success(function(){
			$scope.MealType.child = [];
			$scope.mealdata.child = $scope.MealType.child = [];

			console.log('success');
		});
	    }

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

		
	
	$scope.saveUpdateFirePrecaution = false;
	$scope.saveFirePrecaution = function(){
		
		$scope.FirePrecaution.supplierCode = $rootScope.supplierCode;
		console.log($scope.FirePrecaution);
		$http.post('/saveUpdateFirePrecaution', $scope.FirePrecaution).success(function(data){
			console.log('success');
			//$scope.saveUpdateFirePrecaution = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.saveUpdateDocumentation = false;
	$scope.saveDocumentation = function(){
		
		$scope.HealthSafety.supplierCode = $rootScope.supplierCode; 
		console.log($scope.HealthSafety);
		$http.post('/saveUpdateHealthSafety', $scope.HealthSafety).success(function(data){
			console.log('success');
			//$scope.saveUpdateDocumentation = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveUpExitsAndCorridors = false;
	$scope.saveExitsAndCorridors = function(){
		
		$scope.ExitsAndCorridor.supplierCode = $rootScope.supplierCode; 
		console.log($scope.ExitsAndCorridor);
		$http.post('/saveUpdateExitsAndCorridor', $scope.ExitsAndCorridor).success(function(data){
			console.log('success');
			//$scope.saveUpExitsAndCorridors = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveUpAirCondition = false;
	$scope.saveAirCondition = function(){
		
		$scope.AirCondition.supplierCode = $rootScope.supplierCode; 
		console.log($scope.AirCondition);
		$http.post('/saveUpdateAirCondition', $scope.AirCondition).success(function(data){
			console.log('success');
			//$scope.saveUpAirCondition = true;
			 notificationService.success("Save Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveUpLifts = false;
	$scope.saveLifts = function(){
		
		$scope.Lifts.supplierCode = $rootScope.supplierCode; 
		console.log($scope.Lifts);
		$http.post('/saveUpdateLifts', $scope.Lifts).success(function(data){
			console.log('success');
			//$scope.saveUpLifts = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveUpBedrooms = false;
	$scope.saveBedroomsAsndBalconies = function(){
		
		$scope.Bedrooms.supplierCode = $rootScope.supplierCode; 
		console.log($scope.Bedrooms);
		$http.post('/saveUpdateBedroomsAsndBalconies', $scope.Bedrooms).success(function(data){
			console.log('success');
			//$scope.saveUpBedrooms = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveUpKitchen = false;
	$scope.saveKitchenAndHygiene = function(){
		
		$scope.KitchenAndHygiene.supplierCode = $rootScope.supplierCode; 
		console.log($scope.KitchenAndHygiene);
		$http.post('/saveUpdateKitchenAndHygiene', $scope.KitchenAndHygiene).success(function(data){
			console.log('success');
			//$scope.saveUpKitchen = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
	
	$scope.saveUPChildren = false;
     $scope.saveChildrenFaciliti = function(){
		
		$scope.ChildrenFaciliti.supplierCode = $rootScope.supplierCode; 
		$scope.ChildrenFaciliti.monthkid = $scope.ChildrenkidClub;
		console.log($scope.ChildrenFaciliti);
		$http.post('/saveUpdateChildrenFaciliti', $scope.ChildrenFaciliti).success(function(data){
			console.log('success');
			//$scope.saveUPChildren = true;
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
		
	}
     
     $scope.saveUpSwiming = false;
     $scope.saveSwimmingPool = function(){
 		
 		$scope.SwimmingPool.supplierCode = $rootScope.supplierCode; 
 		$scope.SwimmingPool.monthOperational = $scope.swimmingpoolOperation;
 		console.log($scope.SwimmingPool);
 		$http.post('/saveUpdateSwimmingPool', $scope.SwimmingPool).success(function(data){
 			console.log('success');
 			//$scope.saveUpSwiming = true;
 			 notificationService.success("Save Successfully");
 			
 		}).error(function(data, status, headers, config) {
 			console.log('ERROR');
 		});
 		
 	}
     $scope.cctvSt = false;
     $scope.CCTVStatus={};
     $scope.saveCCTVstatus = function(){
  		
  		$scope.CCTVStatus.supplierCode = $rootScope.supplierCode; 
  		$scope.CCTVStatus.cctvArea = $scope.CCTVStatusArray;
  		console.log($scope.CCTVStatus);
  		$http.post('/saveUpdateCCTVstatus', $scope.CCTVStatus).success(function(data){
  			console.log('success');
  			//$scope.cctvSt = true;
  			 notificationService.success("Save Successfully");
  			
  		}).error(function(data, status, headers, config) {
  			console.log('ERROR');
  		});
  		
  	}
     $scope.saveAddition = false;
     $scope.saveAdditionalInfo = function(){
 		
 		$scope.additionalInfo.supplierCode = $rootScope.supplierCode; 
 		console.log($scope.additionalInfo);
 		$http.post('/saveUpdateAdditionalInfo', $scope.additionalInfo).success(function(data){
 			console.log('success');
 			//$scope.saveAddition = true;
 			 notificationService.success("Save Successfully");
 			
 		}).error(function(data, status, headers, config) {
 			console.log('ERROR');
 		});
 		
 	}
     $scope.saveGasHeaters = false;
     $scope.saveGaswaterHeaters = function(){
  		
  		$scope.GaswaterHeaters.supplierCode = $rootScope.supplierCode; 
  		console.log($scope.GaswaterHeaters);
  		$http.post('/saveUpdateGaswaterHeaters', $scope.GaswaterHeaters).success(function(data){
  			console.log('success');
  			//$scope.saveGasHeaters = true;
  			 notificationService.success("Save Successfully");
  			
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
			$rootScope.supplierCurrency=data.Currency;

			//$scope.saveDescription($scope.supp);
			notificationService.success("Save Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	$scope.mealpolicy={};

	$scope.mealPlanSuccessMsg = false;
	$scope.savemealplan = function(){

		$scope.mealpolicy.supplierCode = $rootScope.supplierCode ;

		$scope.mealpolicy.child=$scope.contacts;
		 if($scope.mealpolicy.taxIncluded == "true"){
			 delete $scope.mealpolicy.taxtype;
			 delete $scope.mealpolicy.taxvalue;
		 }
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
			notificationService.success("Save Successfully");
			//$scope.mealPlanSuccessMsg = true;
			//$scope.mealpolicy = [];
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});


	};
	
	
	

	$scope.mealPlanUpdateSuccessMsg = false;
	$scope.updatemealplan = function(){
		
		 if($scope.mealdata.taxIncluded == "true"){
			 delete $scope.mealdata.taxtype;
			 delete $scope.mealdata.taxvalue;
		 }
		 angular.forEach($scope.mealdata.child, function(obj, index){
			 if($scope.mealdata.child[index].chargeType == "true"){
				delete $scope.mealdata.child[index].childtaxtype;
				delete $scope.mealdata.child[index].childtaxvalue;
			 }
				 
		 });
		 
		
		console.log($scope.mealdata);
		$http.post('/updatemealpolicy',$scope.mealdata).success(function(data){
			console.log('success');
			//$scope.mealPlanUpdateSuccessMsg = true;
			 notificationService.success("Update Successfully");
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
			 notificationService.success("Save Successfully");
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
			 notificationService.success("Save Successfully");
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
			notificationService.success("Internal Info Save Successfully");
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
			notificationService.success("Save Successfully");
			//$scope.Contactinfosuccess = true;

		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});

	}

	$scope.BillinfoSucccess = false;
	$scope.Savebillinginfo=function()
	{
		$scope.bill.supplierCode=$rootScope.supplierCode;

		if($scope.bill.bankToBankTransfer == "false"){
			delete $scope.bill.bankName;
			delete $scope.bill.branchName;
			delete $scope.bill.swifiCode;
			delete $scope.bill.accountNo;
			delete $scope.bill.accountType;
			
		}
		console.log($scope.bill);
		$http.post('/updatebillingInfo',$scope.bill).success(function(data){
			console.log('success');
			notificationService.success("Save Successfully");
		//	$scope.BillinfoSucccess = true;

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
			notificationService.success("Save Successfully");
			//$scope.communciatsuccess = true;

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
			notificationService.success("Save Successfully");
			//$scope.Descriptinsuccess = true;
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
			notificationService.success("Save Successfully");
			//$scope.leisuresucess = true;
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
			notificationService.success(' Successfully');
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
			notificationService.success("Save Successfully");
			//$scope.amenitiesSuccess = true;
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
controller("manageContractsController",['$scope','notificationService','$rootScope','$http',function($scope,notificationService,$rootScope, $http){
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
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
	
	console.log($rootScope.hotelName);
	
	$http.get('/getRoomTypes').success(function(response){
		console.log(response);
		$scope.roomTypes = response;
		
		/*angular.forEach($scope.response, function(obj, index){
			$scope.roomTypes[index] = response[index];
		});
		console.log($scope.roomTypes);*/
	});
	
	$http.get('/getCurrency').success(function(response){
		console.log(response);
		$scope.currencies = response;
	});
	
	$http.get('/getMealTypes').success(function(response){
		console.log(response);
		$scope.mealTypes = response;
	});
	
//	   $scope.roomTypeIns = ( {chargesForChildren:"false"} );
	
	
	$scope.showData = function() {
		console.log($scope.formData.room);
		console.log($scope.formData.fromDate);
		console.log($scope.formData.toDate);
		console.log($scope.currencyname);
		
		if($scope.formData.fromDate < $scope.formData.toDate){
		
		$http.get('/getRateData/'+$scope.formData.room+'/'+$scope.formData.fromDate+'/'+$scope.formData.toDate+'/'+$scope.currencyname).success(function(response){
			console.log(response);
			$scope.rateMeta = response;
			
			for(var i=0;i<$scope.rateMeta.length;i++) {
				
				if($scope.rateMeta[i].applyToMarket == false){
					console.log($scope.rateMeta[i].id);
					$scope.showMarketTable($scope.rateMeta[i]);
				}
				
				}
			/*$scope.rateData = [{meals:'Lunch'}];*/
			console.log($scope.rateMeta);
			if(angular.isUndefined($scope.rateMeta) || $scope.rateMeta == "") {
				$scope.messageShow = "No Rate Found For This Period please";
				$scope.link = "Add New Rate";
				$scope.addNewButton = "false";
				$scope.showRateUpdate = false;
				$scope.showPeriod = false;
			} else {
				$scope.addNewButton = "true";
				$scope.messageShow = " ";
				$scope.link = " ";
				$scope.showRateUpdate = true;
				$scope.showPeriod = false;
			}
			 
		});
		$scope.isUpdated = false;
		$scope.showSavedRates = true;
		}else{
			$scope.messageShow = "Please Select 'To Date' Greater Then 'From Date'";
			$scope.link="";
			alert("Data max");
		}
	};
	
	$scope.createNewRate = function() {
		
		
		
		for(var i=0;i<$scope.rateObject.length;i++) {
			$scope.rateObject.splice(i,1);
			//$scope.rateObject.normalRate.rateDetails[i] = ( {meals:"Lunch"} );
			$scope.rateObject.normalRate.rateDetails[i].meals = "Lunch";
			//$scope.rateObject.normalRate.rateDetails[i].onlineMeals = "Lunch";
			console.log(i);
		}
		
		$http.get('/getRateObject/'+$scope.formData.room).success(function(response){
			$scope.rateObject.push(response);
			console.log($scope.rateObject);
			$scope.messageShow = " ";
			$scope.link = " ";
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
			/*$scope.rateObject.normalRate.rateDetails[index].meals = "Lunch";*/
		/*	$scope.rateObject.normalRate.rateDetails[index].onlineMeals = "Lunch";*/
		} else {
			$scope.rateObject.normalRate.rateDetails[index].includeMeals = false;
			$scope.rateObject.normalRate.rateDetails[index].meals = null;
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
		
		console.log($scope.rateMeta);
		console.log($scope.rateMeta[parentIndex].cancellation[index].id);
		$scope.id = $scope.rateMeta[parentIndex].cancellation[index].id;
		$scope.normal = false; 
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deletecansell/"+ $scope.id+'/'+$scope.normal).success(function(response){
			console.log('success');
			
			
			$scope.rateMeta[parentIndex].cancellation.splice(index,1);
			
		});
	    }
		//$scope.rateMeta[parentIndex].cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSpecialRate = function(index,parentIndex) {
		$scope.rateObject[parentIndex].special.cancellation.splice(index,1);
	};
	
	$scope.removeRuleOfSpecialRateMeta = function(index,parentIndex) {
		
		$scope.id = $scope.rateMeta[parentIndex].special.cancellation[index].id;
		$scope.spe = true;
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deleteSpecialcansell/"+ $scope.id+'/'+$scope.spe).success(function(response){
			console.log('success');
			
			
			$scope.rateMeta[parentIndex].special.cancellation.splice(index,1);
			
		});
	    }
		
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
		console.log($scope.rateObject);
		
		for(var i=0;i<$scope.rateObject.length;i++) {
			console.log(i);
			$scope.rateObject[i].roomId = $scope.formData.room;
			$scope.rateObject[i].fromDate = $scope.formData.fromDate;
			$scope.rateObject[i].toDate = $scope.formData.toDate;
			$scope.rateObject[i].currency = $scope.currencyname;
			$scope.rateObject[i].supplierCode = supplierCode;
		}
		
		$http.post('/saveRate', {"rateObject":$scope.rateObject}).success(function(data){
			console.log('success');
			 notificationService.success("Save Successfully");
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	
	$scope.updateRateMeta = function() {
		console.log($scope.rateMeta);
		$http.post('/updateRateMeta', {"rateObject":$scope.rateMeta}).success(function(data){
			console.log('success');
			 notificationService.success("Update Successfully");
			//$scope.isUpdated = true;
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	};
	
	$scope.deleteRate = function(id,index) {
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
	    	$http.get('/deleteRate/'+id).success(function(response){			
				$scope.rateMeta.splice(index,1);
			});
	        alert("Delete Successfully !");
	    }	
		
	}
	
	
	////
    
    $scope.selectedRatesId;
	console.log($scope.selectedRatesId);
	//$scope.webBrowsersGrouped =[];
	$scope.msClose;
	$scope.getSelectedCity = [];
	
	
	$scope.showMarketTable = function(alloc) {
		 
			
		console.log(alloc);
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
							if(alloc.applyToMarket == false)
								{
							alloc.allocatedCities.push({
								name:data[i].country.cityvm[j].cityName,
								ticked:data[i].country.cityvm[j].tick
							});
								}else{
									console.log("True all tick");
									alloc.allocatedCities.push({
									name:data[i].country.cityvm[j].cityName,
									ticked:true
									});
									
									}
						
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
		/*console.log($scope.selectedRatesId);
		if($scope.selectedRatesId != 0) {
			$scope.setSelection(allot);
		}*/
		
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
controller("manageSuppliersController",function($scope,notificationService,$rootScope,$location, $http,ngDialog){
		
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
		
	
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
		
		$scope.approvePending = function() {
			$scope.userId = $scope.generalInfo.id;
			$scope.email = $scope.generalInfo.email;
			$scope.supplierCode = $scope.generalInfo.code;
			$http.get('/approveUser/'+$scope.userId+'/'+$scope.email+'/'+$scope.supplierCode).success(function(response){
				$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
				$scope.getData();
			});
		}
		
		$scope.ApprovedUser = function(user) {
			//$scope.userId = userId;
			console.log(user);
			
			$scope.generalInfo = user;
			console.log($scope.generalInfo);
			$http.get("/marketrate").success(function(response) {
				$scope.marketrate = response;
			});
			
			ngDialog.open({
				template: 'Approved',
				scope : $scope,
				className: 'ngdialog-theme-default'
			});
			
		}
		
		$scope.rejectedUser = function(user) {
			//$scope.userId = userId;
			console.log(user);
			
			$scope.generalInfo = user;
			console.log($scope.generalInfo);
			$http.get("/marketrate").success(function(response) {
				$scope.marketrate = response;
			});
			
			ngDialog.open({
				template: 'Reject',
				scope : $scope,
				className: 'ngdialog-theme-default'
			});
			
		}
		
		
		$scope.pendingU = function(user) {
			
			console.log(user);
			
			$scope.generalInfo = user;
			console.log($scope.generalInfo);
			$http.get("/marketrate").success(function(response) {
				$scope.marketrate = response;
			});
			
			ngDialog.open({
				template: 'pending',
				scope : $scope,
				className: 'ngdialog-theme-default'
			});
			
		}
		
		$scope.ApprovReject = function(){
			//
			console.log($scope.generalInfo);
			$scope.userId = $scope.generalInfo.id;
			//console.log($scope.userId);
			$http.get('/rejectUser/'+$scope.userId).success(function(response){
				console.log("Success")
			$scope.approvedUsers.splice($scope.approvedUsers.indexOf($scope.generalInfo),1);
			$scope.getData();
		});
		}
		
		$scope.pendingUser = function() {
			
			$scope.userId = $scope.generalInfo.id;
			$http.get('/pendingUser/'+$scope.userId).success(function(response){
				$scope.rejectedUsers.splice($scope.rejectedUsers.indexOf($scope.generalInfo),1);
				$scope.getData();
			});
		}
		
		$scope.searchSupplier = function(find){
			
			console.log($scope.find);
			$scope.supplierCode = $scope.find.supplierCode;
			$scope.supplierName = $scope.find.hotelNm;
	
			$http.get('/supplierfind/'+$scope.supplierCode+'/'+$scope.supplierName).success(function(response){
				console.log(response);
				if(response == "true"){
					//window.open('http://localhost:9000/adminLogin#/findSupplier');
					
				}else{
					notificationService.error("Supplier Not Found");
				}
				
			}).error(function(data, status, headers, config){
				console.log("error");
				
			});
			
			
			
			//$http.post('/findSupplier',$scope.find).success(function(data){
		//		console.log("Success")
			//	console.log(data);
			//	$scope.flagAdmin = 1;
		//	$location.path("/supplier/206");
		//window.location.assign("hotel_profile/206");
				
				 //window.open('hotel_profile/'+$scope.supplierCode+"/"+$scope.flagAdmin);
				/*$http.get('/supplierfind/'+$scope.supplierCode+'/'+$scope.supplierName).success(function(response){
					console.log("Success");
				});*/
				/*$location.path('supplierfind/'+$scope.supplierCode+"/"+$scope.supplierName);*/
		
			
		//});
			
			
		}
		
});


angular.module('travel_portal').
controller("manageSpecialsController",['$scope','notificationService','$filter','$rootScope','$http',function($scope,notificationService,$filter,$rootScope, $http){
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$scope.specialsObject = [];
	$scope.specialsData = [];
	$scope.showRemoveMarket = false;
	$scope.showSavedRemoveMarket = false;
	$scope.showRemovePeriod = false;
	$scope.showSavedPeriods = false;
	$scope.isCreateNew = false;
	
	$http.get("/getPeriod/"+supplierCode).success(function(response){
		console.log('success');
		console.log(response);
		$scope.allperiod = response;
		
		
		 angular.forEach($scope.allperiod, function(obj, index){
				$scope.allperiod[index].fromDate = $filter('date')(response[index].fromDate, "yyyy-MM-dd");
				
				$scope.allperiod[index].toDate = $filter('date')(response[index].toDate, "yyyy-MM-dd");
				return;
			});
		
	});
	
	$http.get('/getRooms').success(function(response){
		console.log(response);
		$scope.rooms = response;
	});
	
	$http.get('/getfreeStay').success(function(response){
		console.log("response");
		console.log(response);
		$scope.freeStay = response;
	});
	
	
	$scope.createNewPeriod = function() {
		$http.get('/getSpecialsObject').success(function(response){
			$scope.specialsObject.push(response);
			$scope.isCreateNew = true;
			$scope.showSavedPeriods = false;
			$scope.formData.datePeriodId = null;
		});
		
		if($scope.specialsObject.length > 0) {
			$scope.showRemovePeriod = true;
		}
	}
	
	$scope.addNewMarket = function(index) {
		$scope.specialsObject[index].markets.push({});
		if($scope.specialsObject[index].markets.length > 0) {
			$scope.showRemoveMarket = true;
		}
	}
	
	$scope.addNewSavedMarket = function(index) {
		$scope.specialsData[index].markets.push({applyToMarket:"true"});
		 /*$scope.allotmentM.push( {applyMarket:"true"} );*/
		$scope.showMarketTable($scope.specialsData[index].markets);
		if($scope.specialsData[index].markets.length > 0) {
			$scope.showSavedRemoveMarket = true;
		}
	}
	
	$scope.deleteMarket = function(parentIndex,index) {
		$scope.specialsObject[parentIndex].markets.splice(index,1);
		if($scope.specialsObject[parentIndex].markets.length == 1) {
			$scope.showRemoveMarket = false;
		}
	}
	
	$scope.deleteSavedMarket = function(parentIndex,index) {
		
		console.log($scope.specialsData[parentIndex].markets[index].id);
		$scope.marketId = $scope.specialsData[parentIndex].markets[index].id;
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deleteMarket/"+$scope.marketId).success(function(response){
			console.log('success');
			
			$scope.specialsData[parentIndex].markets.splice(index,1);
			if($scope.specialsData[parentIndex].markets.length == 1) {
				$scope.showSavedRemoveMarket = false;
			}
			
		});
	    }
		
		
		
	}
	
	$scope.deletePeriod = function(index) {
		console.log($scope.specialsData);
		$scope.id = $scope.specialsData[0].id;
		
		
		var r = confirm("Do You Want To Delete!");
	    if (r == true) {
		$http.get("/deletePeriod/"+$scope.id).success(function(response){
			console.log('success');
			
			$scope.showSavedPeriods = false;
			console.log($scope.showRemovePeriod);
			$http.get("/getPeriod/"+supplierCode).success(function(response){
				console.log('success');
				console.log(response);
				$scope.allperiod = response;
				
				
				 angular.forEach($scope.allperiod, function(obj, index){
						$scope.allperiod[index].fromDate = $filter('date')(response[index].fromDate, "yyyy-MM-dd");
						
						$scope.allperiod[index].toDate = $filter('date')(response[index].toDate, "yyyy-MM-dd");
						return;
					});
				
			});
			
		});
		 alert("Delete Successfully !");
	    }
		
		/*$http.get('/deletePeriod', {"specialsData":$scope.specialsData}).success(function(data){
			console.log('success');
			
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});*/
		
	}
	
	$scope.savePeriod = function() {
		$scope.specialsObject[0].supplierCode = supplierCode;
		console.log($scope.specialsObject);
		$http.post('/saveSpecials', {"specialsObject":$scope.specialsObject}).success(function(data){
			console.log('success');
			 notificationService.success("Save Successfully");
			 $http.get("/getPeriod/"+supplierCode).success(function(response){
					console.log('success');
					console.log(response);
					$scope.allperiod = response;
					
					
					 angular.forEach($scope.allperiod, function(obj, index){
							$scope.allperiod[index].fromDate = $filter('date')(response[index].fromDate, "yyyy-MM-dd");
							
							$scope.allperiod[index].toDate = $filter('date')(response[index].toDate, "yyyy-MM-dd");
							return;
						});
					
				});
			
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.updatePeriod = function() {
		$scope.specialsData[0].supplierCode = supplierCode;
		$http.post('/updateSpecials', {"specialsObject":$scope.specialsData}).success(function(data){
			console.log('success');
			 notificationService.success("Update Successfully");
		}).error(function(data, status, headers, config) {
			console.log('ERROR');
		});
	}
	
	$scope.showPeriods = function() {
		console.log($scope.formData);
		
		var arr = $scope.formData.datePeriodId.split("@");
		$scope.formData.fromDate = arr[0];
		$scope.formData.toDate = arr[1];
		$scope.formData.promotionName = arr[2];
		
		$http.get('/getSpecialsData/'+$scope.formData.fromDate+'/'+$scope.formData.toDate+'/'+$scope.formData.promotionName).success(function(response){
			console.log(response);
			$scope.specialsData = response;
			console.log($scope.specialsData);
			if(angular.isUndefined($scope.specialsData) || $scope.specialsData == "") {
				$scope.messageShow = "No Period Found then";
				$scope.link = "Add New Rate";
				$scope.showRateUpdate = false;
				$scope.showSavedPeriods = false;
			} else {
			
				console.log("*(*(*((*(*(*");
				console.log($scope.specialsData[0].markets[0]);
			//	for(var i=0;i<$scope.specialsData.length;i++) {
					
					//if($scope.specialsData[0].markets[0].applyToMarket == "false"){
						//console.log($scope.specialsData[i].id);
						$scope.showMarketTable($scope.specialsData[0].markets[0]);
					//}
					
					//}
				
				$scope.isCreateNew =false;
				$scope.messageShow = " ";
				$scope.link = " ";
				$scope.showRateUpdate = true;
				$scope.showSavedPeriods = true;
				var i,j,k;
				for(i=0;i<$scope.specialsData.length;i++) {
					for(j=0;j<$scope.rooms.length;j++) {
						for(k=0;k<$scope.specialsData[i].roomTypes.length;k++) {
							if($scope.rooms[j].roomType == $scope.specialsData[i].roomTypes[k]) {
								$scope.rooms[j].isSelected = true;
							}
						};
					};
				};
				
			}
			
			console.log($scope.rooms);
		});
	}
	
	
	$scope.selectedRatesId;
	console.log($scope.selectedRatesId);
	//$scope.webBrowsersGrouped =[];
	$scope.msClose;
	$scope.getSelectedCity = [];
	
	
	$scope.showMarketTable = function(alloc) {
		 
		
		//console.log(alloc);
		//$scope.getSelectedCity.splice(0);
		if(angular.isUndefined(alloc.id)) {
			$scope.selectedRatesId = 0;
		} else {
			$scope.selectedRatesId = alloc.id;
		}
		console.log($scope.rateObject);
		console.log($scope.selectedRatesId);
		$http.get('/getSpecialMarketGroup/'+$scope.selectedRatesId)
		.success(function(data){
			if(data) {
				
					alloc.allocatedCities = [];
								
				
					for(var i = 0; i<data.length;i++){
						alloc.allocatedCities.push({
							name:'<strong>'+data[i].country.countryName+'</strong>',
							multiSelectGroup:true
						});
						
						for(var j =0; j<data[i].country.cityvm.length;j++){
							if(alloc.applyToMarket == "false")
								{
							alloc.allocatedCities.push({
								name:data[i].country.cityvm[j].cityName,
								ticked:data[i].country.cityvm[j].tick
							});
								}else{
									console.log("True all tick");
									alloc.allocatedCities.push({
									name:data[i].country.cityvm[j].cityName,
									ticked:true
									});
									
									}
						
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
		alert("HIIIII");
		for(var i = 0; i<allot.allocatedCities.length;i++){
			if(allot.allocatedCities[i].multiSelectGroup == undefined ){
				allot.allocatedCities[i].ticked = true;
				console.log("Hiiii");
			}
		}
		/*console.log($scope.selectedRatesId);
		if($scope.selectedRatesId != 0) {
			$scope.setSelection(allot);
		}*/
		
	}
	
}]);


angular.module('travel_portal').
controller("manageAgentController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){
	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$scope.getData = function() {
	
	   $http.get('/getApprovedAgent').success(function(response){
			
			console.log(response);
			$scope.approvedUsers = response;
		});
		
		$http.get('/getPendingAgent').success(function(response){
			console.log(response);
			$scope.pendingUsers = response;
		});
		
	
		
		$http.get('/getRejectedAgent').success(function(response){
			console.log(response);
			$scope.rejectedUsers = response;
		});
		
	}
	
	$scope.pendingU = function(user) {
		
		console.log(user);
		
		$scope.generalInfo = user;
		console.log($scope.generalInfo);
		/*$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});*/
		
		ngDialog.open({
			template: 'pending',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.rejectedUser = function(user) {
	
		console.log(user);
		
		$scope.generalInfo = user;
		console.log($scope.generalInfo);
		/*$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});*/
		
		ngDialog.open({
			template: 'Reject',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.ApprovedUser = function(user) {
		
		console.log(user);
		
		$scope.generalInfo = user;
		console.log($scope.generalInfo);
		/*$http.get("/marketrate").success(function(response) {
			$scope.marketrate = response;
		});*/
		
		ngDialog.open({
			template: 'Approved',
			scope : $scope,
			className: 'ngdialog-theme-default'
		});
		
	}
	
	$scope.approvePending = function() {
		$scope.userId = $scope.generalInfo.id;
		$scope.email = $scope.generalInfo.EmailAddr;
		$scope.agentCode = $scope.generalInfo.agentCode;
		$scope.loginId = $scope.generalInfo.loginId;
		$http.get('/approveAgent/'+$scope.userId+'/'+$scope.email+'/'+$scope.agentCode).success(function(response){
			 notificationService.success("Approved Successfully");
			$scope.pendingUsers.splice($scope.pendingUsers.indexOf($scope.generalInfo),1);
			$scope.getData();
		});
	}
	
	$scope.ApprovReject = function(){
		console.log($scope.generalInfo);
		$scope.userId = $scope.generalInfo.id;
		$http.get('/rejectAgent/'+$scope.userId).success(function(response){
			console.log("Success")
			 notificationService.success("Reject Successfully");
		$scope.approvedUsers.splice($scope.approvedUsers.indexOf($scope.generalInfo),1);
		$scope.getData();
	});
	}
	
	$scope.pendingUser = function() {
		$scope.userId = $scope.generalInfo.id;
		$http.get('/pendingAgent/'+$scope.userId).success(function(response){
			 notificationService.success("Pending Successfully");
			$scope.rejectedUsers.splice($scope.rejectedUsers.indexOf($scope.generalInfo),1);
			$scope.getData();
		});
	}
	
}]);

angular.module('travel_portal').
controller("markupController",['$scope','notificationService','$filter','$rootScope','$http','ngDialog',function($scope,notificationService,$filter,$rootScope, $http,ngDialog){

	
	$(".form-validate").validate({
        errorPlacement: function(error, element){
            error.insertAfter(element);
        }
    });
	
	$scope.supplier_check = [];
	$scope.agent_check = [];
	$scope.specificAgent_check = [];
	$scope.rate_check = [];
	$scope.specialrate_check = [];
	
	$http.get("/getSupplier").success(function(response){
		$scope.supplier=response;
		
		console.log($scope.supplier);
	});
	
	$scope.supplierClicked = function(e, supplierInfo) {

		if($(e.target).is(":checked")) {
			$scope.supplier_check.push(supplierInfo.code);
		} else {
			DeletesupplierItem(supplierInfo);
		}
		console.log("//////////");
		console.log($scope.supplier_check);
	}

	DeletesupplierItem = function(supplierInfo){
		angular.forEach($scope.supplier_check, function(obj, index){
			if ((supplierInfo.code == obj)) {
				$scope.supplier_check.splice(index, 1);
				return;
			};
		});

	}
	
	
	$scope.suppliercheckAll = function () {
        if ($scope.selectedAll) {
            $scope.selectedAll = false;
            angular.forEach($scope.supplier, function (supplierInfo) {
            	supplierInfo.isSelected = $scope.selectedAll;
            });
            $scope.supplier_check = [];
        } else {
        	
            $scope.selectedAll = true;
            $scope.supplier_check = [];
            angular.forEach($scope.supplier, function (supplierInfo,index) {
            	supplierInfo.isSelected = $scope.selectedAll;
                    	$scope.supplier_check.push($scope.supplier[index].code);
            });
        }
       
        	console.log($scope.supplier_check);
    };
	
	
	$http.get("/agentCountries").success(function(response) {
		$scope.countries = response;
		console.log($scope.countries);
	}); 
	
	$scope.init = function(){
		
		  $scope.agentselectedAll = false;
		  $scope.specificAgentselectedAll = false;
		   $scope.agent_check = [];
		   $scope.specificAgent_check = [];
		$http.get("/getAgent").success(function(response){
			$scope.agent=response;
			console.log("+++++++++++++++=");
			console.log($scope.agent);
			
		});
	}
	

	$scope.onCountryChange = function(){
		console.log($scope.countryCode);
		   $scope.agentselectedAll = false;
		   $scope.specificAgentselectedAll = false;
		   $scope.agent_check = [];
		   $scope.specificAgent_check = [];
		$http.get("/getAgentCountry/"+$scope.countryCode).success(function(response){
			$scope.agent=response;
			console.log("+++++++++++++++=");
			console.log($scope.agent);
			
		});
	
	}
	
	
	$scope.agentClicked = function(e, agentInfo) {

		if($(e.target).is(":checked")) {
			$scope.agent_check.push(agentInfo.agentCode);
		} else {
			DeleteagentItem(agentInfo);
		}
		console.log("//////////");
		console.log($scope.agent_check);
	}

	DeleteagentItem = function(agentInfo){
		angular.forEach($scope.agent_check, function(obj, index){
			if ((agentInfo.agentCode == obj)) {
				$scope.agent_check.splice(index, 1);
				return;
			};
		});

	}
	
	$scope.agentcheckAll = function () {
        if ($scope.agentselectedAll) {
            $scope.agentselectedAll = false;
            angular.forEach($scope.agent, function (agentInfo1) {
            angular.forEach(agentInfo1.agentDatavm, function (agentInfo) {
            	agentInfo.isSelected = $scope.agentselectedAll;
            });
            });
            $scope.agent_check = [];
        } else {
        	
            $scope.agentselectedAll = true;
            $scope.agent_check = [];
       
            angular.forEach($scope.agent, function (agentInfo1) {
            angular.forEach(agentInfo1.agentDatavm, function (agentInfo, index) {
            	agentInfo.isSelected = $scope.agentselectedAll;
            	console.log(agentInfo);
                    	$scope.agent_check.push(agentInfo1.agentDatavm[index].agentCode);
                    	
                    	
            	});
            });
        }
       
        	console.log($scope.agent_check);
    };
    
    $scope.saveBatchMarkup = function(){
    	$scope.batchMarkup.agent = $scope.agent_check;
    	$scope.batchMarkup.supplier = $scope.supplier_check;
    	if($scope.batchMarkup.selected == "1"){
    		delete $scope.batchMarkup.flat;
    	}else{
    		delete $scope.batchMarkup.percent;
    	}
    		
    	console.log($scope.batchMarkup);
    	
   
    	$http.post('/savebatchMarkup',$scope.batchMarkup).success(function(data){
    		console.log("Success");
    		 notificationService.success("BatchMarkup Save Successfully");
    	});
    }
	
	
    $scope.specificAgentClicked = function(e, specificAgentInfo) {

		if($(e.target).is(":checked")) {
			$scope.specificAgent_check.push(specificAgentInfo.agentCode);
		} else {
			DeletespecificAgentItem(specificAgentInfo);
		}
		console.log("//////////");
		console.log($scope.specificAgent_check);
	}

	DeletespecificAgentItem = function(specificAgentInfo){
		angular.forEach($scope.specificAgent_check, function(obj, index){
			if ((specificAgentInfo.agentCode == obj)) {
				$scope.specificAgent_check.splice(index, 1);
				return;
			};
		});

	}
	
	$scope.specificAgentcheckAll = function () {
        if ($scope.specificAgentselectedAll) {
            $scope.specificAgentselectedAll = false;
            angular.forEach($scope.agent, function (specificAgentInfo1) {
            angular.forEach(specificAgentInfo1.agentDatavm, function (specificAgentInfo) {
            	specificAgentInfo.isSelected = $scope.specificAgentselectedAll;
            });
            });
            $scope.specificAgent_check = [];
        } else {
        	
            $scope.specificAgentselectedAll = true;
            $scope.specificAgent_check = [];
            angular.forEach($scope.agent, function (specificAgentInfo1) {
            angular.forEach(specificAgentInfo1.agentDatavm, function (specificAgentInfo, index) {
            	specificAgentInfo.isSelected = $scope.specificAgentselectedAll;
            	console.log(specificAgentInfo);
                    	$scope.specificAgent_check.push(specificAgentInfo1.agentDatavm[index].agentCode);
                    	
                    	
            });
            });
        }
       
        	console.log($scope.specificAgent_check);
    };
    
    $scope.showRate = function(code){
    	console.log(code);
    	$scope.showAllRate = true;
    	$http.get("/getSupplierRate/"+code).success(function(response){
    		$scope.supplierRate = response;
    		console.log(response);
    	});
    	
    	/*$http.get("/getSpecialRate/"+code).success(function(response){
    		$scope.supplierSpecialRate = response;
    		console.log(response);
    	});*/
    }
    
    
    $scope.rateClicked = function(e, rate) {

		if($(e.target).is(":checked")) {
			$scope.rate_check.push(rate.id);
		} else {
			DeleterateItem(rate);
		}
		console.log("//////////");
		console.log($scope.rate_check);
	}

	DeleterateItem = function(rate){
		angular.forEach($scope.rate_check, function(obj, index){
			if ((rate.id == obj)) {
				$scope.rate_check.splice(index, 1);
				return;
			};
		});

	}
	
	$scope.ratecheckAll = function () {
        if ($scope.rateselectedAll) {
            $scope.rateselectedAll = false;
            angular.forEach($scope.supplierRate, function (rate) {
            	rate.isSelected = $scope.rateselectedAll;
            });
            $scope.rate_check = [];
        } else {
        	
            $scope.rateselectedAll = true;
            $scope.rate_check = [];
            $scope.i = 0;
            angular.forEach($scope.supplierRate, function (rate, index) {
            	rate.isSelected = $scope.rateselectedAll;
            	console.log(rate);
                    	$scope.rate_check.push($scope.supplierRate[index].id);
                    	
                    	
            });
        }
       
        	console.log($scope.rate_check);
    };
    
    
    
    
  /* $scope.specialrateClicked = function(e, specialrate) {

		if($(e.target).is(":checked")) {
			$scope.specialrate_check.push(specialrate.id);
		} else {
			DeletespecialrateItem(specialrate);
		}
		console.log("//////////");
		console.log($scope.specialrate_check);
	}

	DeletespecialrateItem = function(specialrate){
		angular.forEach($scope.specialrate_check, function(obj, index){
			if ((specialrate.id == obj)) {
				$scope.specialrate_check.splice(index, 1);
				return;
			};
		});

	}
	
	 	$scope.specialratecheckAll = function () {
        if ($scope.specialrateselectedAll) {
            $scope.specialrateselectedAll = false;
            angular.forEach($scope.supplierSpecialRate, function (specialrate) {
            	specialrate.isSelected = $scope.specialrateselectedAll;
            });
            $scope.specialrate_check = [];
        } else {
        	
            $scope.specialrateselectedAll = true;
            $scope.specialrate_check = [];
            $scope.i = 0;
            angular.forEach($scope.supplierSpecialRate, function (specialrate, index) {
            	specialrate.isSelected = $scope.specialrateselectedAll;
            	console.log(specialrate);
                    	$scope.specialrate_check.push($scope.supplierSpecialRate[index].id);
                    	
                    	
            });
        }
       
        	console.log($scope.specialrate_check);
    };
    */
    
    
    
    $scope.saveSpecificMarkup = function(){
    	$scope.specificMarkup.rateSelected = $scope.rate_check;
    	$scope.specificMarkup.agentSpecific = $scope.specificAgent_check;
    	//$scope.specificMarkup.specialRate =$scope.specialrate_check;
    	if($scope.specificMarkup.specificSelected == "1"){
    		delete $scope.specificMarkup.specificFlat;
    	}else{
    		delete $scope.specificMarkup.specificPercent;
    	}
    		
    	console.log($scope.specificMarkup);
    	
    	$http.post('/savespecificMarkup',$scope.specificMarkup).success(function(data){
    		console.log("Success");
    		 notificationService.success("SpecificMarkUp Save Successfully");
    	});
    }
    
    
    $scope.batchAgent = function(){
    	
    }
    $scope.onSupplierChoose = function(){
    	console.log($scope.code);
    	$http.get("/getSupplerWiseRate/"+$scope.code).success(function(response){
    		$scope.SupplerWiseRate = response;
    		console.log(response);
    	});
    	
    	$http.get("/getSupplerWiseSpecificRate/"+$scope.code).success(function(response){
    		$scope.SupplerWiseSpecificRate = response;
    		 angular.forEach($scope.SupplerWiseSpecificRate, function(obj, index){
    		 
    			 $scope.SupplerWiseSpecificRate[index].rateSelected.fromDate = $filter('date')(response[index].rateSelected.fromDate, "yyyy/MM/dd");
    			 $scope.SupplerWiseSpecificRate[index].rateSelected.toDate = $filter('date')(response[index].rateSelected.toDate, "yyyy/MM/dd");
    			 return;
    		 });
    		
    		console.log(response);
    	});
    }
    
    $scope.selectedSpecificData = {};
    $scope.updateSpecificId = function(supplerWiseSpecific){
    	console.log(supplerWiseSpecific);
    	if(supplerWiseSpecific.specificSelected == "1"){
    		supplerWiseSpecific.specificFlat = null;
    	}else{
    		supplerWiseSpecific.specificPercent =null;
    	}
    	console.log(supplerWiseSpecific);
    	$scope.selectedSpecificData.specificMarkupId = supplerWiseSpecific.specificMarkupId;
    	$scope.selectedSpecificData.specificFlat = supplerWiseSpecific.specificFlat;
    	$scope.selectedSpecificData.specificPercent = supplerWiseSpecific.specificPercent;
    	$scope.selectedSpecificData.specificSelected = supplerWiseSpecific.specificSelected;
    	console.log($scope.selectedSpecificData);
    	$http.post('/UpdateSpecificMarkup',$scope.selectedSpecificData).success(function(data){
    		console.log("Success");
    		 notificationService.success("Update Successfully");
    	});
    }
    
    $scope.selectedData = {};
    
    $scope.updateAgentId = function(supplerWise){
    	if(supplerWise.selected == "1"){
    		supplerWise.flat = null;
    	}else{
    		supplerWise.percent =null;
    	}
    	console.log(supplerWise);
    	$scope.selectedData.batchMarkupId = supplerWise.batchMarkupId;
    	$scope.selectedData.flat = supplerWise.flat;
    	$scope.selectedData.percent = supplerWise.percent;
    	$scope.selectedData.selected = supplerWise.selected;
    	console.log($scope.selectedData);
    	$http.post('/UpdateBatchMarkup', $scope.selectedData).success(function(data){
    		console.log("Success");
    		 notificationService.success("Update Successfully");
    	});
    	
    }
    
}]);
