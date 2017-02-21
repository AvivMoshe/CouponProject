var app = angular.module('myApp', []); 

//Bad login information error page.
app.controller('BadBackLogin', function($scope, $http){
	
	$scope.back= function() {
	window.history.back();
	};	
});


//Clears each form
function resetFunction() {
	document.getElementById("addForm").reset();
	document.getElementById("updateForm").reset();
	document.getElementById("getForm").reset();
	document.getElementById("delForm").reset();
	document.getElementById("addCust").reset();
	document.getElementById("updateCustForm").reset();
    document.getElementById("getCustForm").reset();
    document.getElementById("addCoupForm").reset();
    document.getElementById("updateCouponForm").reset();
    document.getElementById("getCoupForm").reset();
    document.getElementById("getTypeCouponForm").reset();
    document.getElementById("getPriceCouponForm").reset();
    document.getElementById("getDayCouponForm").reset();
    document.getElementById("delCoupForm").reset();
}


//Admin Company management.
app.controller('AdminCompanyMethods', function($scope, $http){
	
  $scope.createForm=false;
  
  $scope.createCompanyDetails = function(){
    $scope.deleteForm=false;
    $scope.updateCompanyForm=false;
    $scope.getCompanyForm=false;
    $scope.getAllCompDetails=false;
    $scope.getCompanyFormTable=false;
    if($scope.createForm==true){
      $scope.createForm=false;
    }else{
      $scope.createForm=true;
    }
  }
	$scope.addCompany = function() {
		$http.post("rest/admin/Company/Create/"+$scope.addIdComp+"/"+$scope.addNameComp+"/"+$scope.addPassComp+"/"+$scope.addEmailComp).then(function(response){
			$scope.createcomp = response.data;
		})				
	};
	
  
  $scope.deleteForm=false;
  
  $scope.delCompanyDetails = function(){
    $scope.createForm=false;
    $scope.updateCompanyForm=false;
    $scope.getCompanyForm=false;
    $scope.getAllCompDetails=false;
    $scope.getCompanyFormTable=false;
    if($scope.deleteForm==true){
      $scope.deleteForm=false;
    }else{
      $scope.deleteForm=true;
    }
  }
	$scope.delCompany = function() {
		$http.delete("rest/admin/Company/Delete/"+$scope.delID).then(function(response){
			$scope.del = response.data;
		})
	};
	
  
	$scope.updateCompanyForm=false;
  
  $scope.updateCompanyDetails = function(){
    $scope.createForm=false;
    $scope.deleteForm=false;
    $scope.getCompanyForm=false;
    $scope.getAllCompDetails=false;
    $scope.getCompanyFormTable=false;
    if($scope.updateCompanyForm==true){
      $scope.updateCompanyForm=false;
    }else{
      $scope.updateCompanyForm=true;
    }
  }
	$scope.updateCompany = function() {
		$http.put("rest/admin/Company/Update/"+$scope.updateID +"/"+ $scope.updatePass +"/"+ $scope.updateEmail).then(function(response){
			$scope.update = response.data;
		})
	};
	
	
  $scope.getCompanyForm=false;
  
  $scope.getCompanyFormTable=false;
  
  $scope.getCompanyDetails = function(){
    $scope.createForm=false;
    $scope.deleteForm=false;
    $scope.updateCompanyForm=false;
    $scope.getAllCompDetails=false;
    if($scope.getCompanyForm==true){
      $scope.getCompanyForm=false;
      $scope.getCompanyFormTable=false;
    }else{
      $scope.getCompanyForm=true;
    }
  }
	$scope.getId = function() {
		$http.get("rest/admin/Company/Get/"+$scope.InsertId).then(function(response){
			$scope.getCompanyFormTable=true;
			$scope.content = response.data;
		})				
	};
	
	
	$scope.getAllCompDetails=false;
	
	$scope.getAllComp = function() {
    $scope.createForm=false;
    $scope.deleteForm=false;
    $scope.updateCompanyForm=false;
    $scope.getCompanyForm=false;
    $scope.getCompanyFormTable=false;
		if($scope.getAllCompDetails==true){
			$scope.getAllCompDetails=false;
		}else{
			$scope.getAllCompDetails=true;
		}
		$http.get("rest/admin/Company/getAll").then(function(response){
			$scope.company = response.data.company;
		})				
	};
	
});



//Admin Customer management.
app.controller('AdminCustomerMethods', function($scope, $http){
	
  $scope.createForm=false;
  
  $scope.createCustomerDetails = function(){
    $scope.updateForm=false;
    $scope.getForm=false;
    $scope.deleteForm=false;
    $scope.getAllCustDetails=false;
    $scope.getAllCoupDetails=false;
    if($scope.createForm==true){
      $scope.createForm=false;
    }else{
      $scope.createForm=true;
    }
  }
	$scope.addCustomer = function() {
		$http.post("rest/admin/Customer/Create/" +$scope.addIdCust +"/"+ $scope.addNameCust +"/"+ $scope.addPassCust).then(function(response){
			$scope.createcust = response.data;
		})				
	};
	
  
	$scope.updateForm=false;
  
  $scope.updateCustomerDetails = function(){
    $scope.createForm=false;
    $scope.getForm=false;
    $scope.deleteForm=false;
    $scope.getAllCustDetails=false;
    $scope.getAllCoupDetails=false;
    if($scope.updateForm==true){
      $scope.updateForm=false;
    }else{
      $scope.updateForm=true;
    }
  }
	$scope.updateCustomer = function() {
		$http.put("rest/admin/Customer/Update/" +$scope.updateIdCust +"/"+ $scope.updatePassCust).then(function(response){
			$scope.updatecust = response.data;
		})				
	};
	
	
  $scope.getForm=false;
  
  $scope.getCustomerDetails = function(){
    $scope.createForm=false;
    $scope.updateForm=false;
    $scope.deleteForm=false;
    $scope.getAllCustDetails=false;
    $scope.getAllCoupDetails=false;
    if($scope.getForm==true){
      $scope.getForm=false;
    }else{
      $scope.getForm=true;
    }
  }
	$scope.getCust = function() {
		$http.get("rest/admin/Customer/Get/"+$scope.getCustId).then(function(response){
			$scope.getcust = response.data;
		})				
	};
	
  
	$scope.deleteForm=false;
  
  $scope.delCustomerDetails = function(){
    $scope.createForm=false;
    $scope.updateForm=false;
    $scope.getForm=false;
    $scope.getAllCustDetails=false;
    $scope.getAllCoupDetails=false;
    if($scope.deleteForm==true){
      $scope.deleteForm=false;
    }else{
      $scope.deleteForm=true;
    }
  }
	$scope.delCustomer = function() {
		$http.delete("rest/admin/Customer/Delete/"+$scope.delCustID).then(function(response){
			$scope.delcust = response.data;
		})
	};
	
	
	$scope.getAllCustDetails=false;
	
	$scope.getAllCust = function() {
    $scope.createForm=false;
    $scope.updateForm=false;
    $scope.getForm=false;
    $scope.deleteForm=false;
    $scope.getAllCoupDetails=false;
		if($scope.getAllCustDetails==true){
			$scope.getAllCustDetails=false;
		}else{
			$scope.getAllCustDetails=true;
		}
		$http.get("rest/admin/Customer/getAll").then(function(response){
			$scope.customer = response.data.customer;
		})				
	};

	
	$scope.getAllCoupDetails=false;
	
	$scope.getAllCoupons = function() {
    $scope.createForm=false;
    $scope.updateForm=false;
    $scope.getForm=false;
    $scope.deleteForm=false;
    $scope.getAllCustDetails=false;
		if($scope.getAllCoupDetails==true){
			$scope.getAllCoupDetails=false;
		}else{
			$scope.getAllCoupDetails=true;
		}
		$http.get("rest/admin/getAllCoupons").then(function(response){
			$scope.coupon = response.data.coupon;
		})				
	};
	
});



//Company page:
app.controller('CompanyMethods', function($scope, $http){
	
	$scope.createCoupForm=false;
	
	$scope.createCoupDetails = function() {
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.createCoupForm==true){
			$scope.createCoupForm=false;
		}else{
			$scope.createCoupForm=true;
		}
	}
	$scope.createCoupon = function() {
		$http.post("rest/company/Create/"+ $scope.addIdCoup +"/"+ $scope.addTitleCoup +"/"+ $scope.addStartD +"/"+ $scope.addStartM +"/"+ $scope.addStartY +"/"+ $scope.addEndD +"/"+ $scope.addEndM +"/"+ $scope.addEndY +"/"+ $scope.addAmount +"/"+ $scope.addType +"/"+ $scope.addMessage +"/"+ $scope.addPrice +"/"+ $scope.addImage).then(function(response){
			$scope.createcoup = response.data;
		})				
	};
	
	
  $scope.updateCoupForm=false;
	
	$scope.updateCoupDetails = function() {
		$scope.createCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.updateCoupForm==true){
			$scope.updateCoupForm=false;
		}else{
			$scope.updateCoupForm=true;
		}
	}
	$scope.updateCoupon = function() {
		$http.put("rest/company/Update/" +$scope.updateIdCoup +"/"+ $scope.updatePriceCoup +"/"+ $scope.updateDayCoup +"/"+ $scope.updateMonthCoup +"/"+ $scope.updateYearCoup).then(function(response){
			$scope.updatecoup = response.data;
		})				
	};
	
	
  $scope.getCouponForm=false;
	
	$scope.getCoupDetails = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.getCouponForm==true){
			$scope.getCouponForm=false;
		}else{
			$scope.getCouponForm=true;
		}
	}
	$scope.getCoupon = function() {
		$http.get("rest/company/Get/"+$scope.getCoupId).then(function(response){
			$scope.getcoup = response.data;
		})				
	};
	
	
  $scope.delCouponForm=false;
	
	$scope.delCoupDetails = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.delCouponForm==true){
			$scope.delCouponForm=false;
		}else{
			$scope.delCouponForm=true;
		}
	}
	$scope.delCoupon = function() {
		$http.post("rest/company/Delete/"+$scope.delCoupID).then(function(response){
			$scope.delcoup = response.data;
		})
	};
	
	
	$scope.CouponDetailsDiv=false;
	
	$scope.getAllCoupons = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.CouponDetailsDiv==true){
			$scope.CouponDetailsDiv=false;
		}else{
			$scope.CouponDetailsDiv=true;
		}
		$http.get("rest/company/getAllCoupons").then(function(response){
			$scope.coupon = response.data.coupon;
		})				
	};
	
	
  $scope.getCoupTypeForm=false;
	
	$scope.getCoupTypeDetails = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.getCoupTypeForm==true){
			$scope.getCoupTypeForm=false;
		}else{
			$scope.getCoupTypeForm=true;
		}
	}
	$scope.getTypeCoupons = function() {
		$http.get("rest/company/Type/"+$scope.getCoupType).then(function(response){
			$scope.gettypecoup = response.data.coupon;
		})				
	};
	
  
  $scope.getCoupPriceForm=false;
	
	$scope.getCoupPriceDetails = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupDateForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.getCoupPriceForm==true){
			$scope.getCoupPriceForm=false;
		}else{
			$scope.getCoupPriceForm=true;
		}
	}
	$scope.getPriceCoupons = function() {
		$http.get("rest/company/Price/"+$scope.getCoupPrice).then(function(response){
			$scope.getpricecoup = response.data.coupon;
		})				
	};
	
	
  $scope.getCoupDateForm=false;
	
	$scope.getCoupDateDetails = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
	    $scope.getCompanyDetailsForm=false;
		if($scope.getCoupDateForm==true){
			$scope.getCoupDateForm=false;
		}else{
			$scope.getCoupDateForm=true;
		}
	}
	$scope.getDayCoupons = function() {
		$http.get("rest/company/Date/"+ $scope.getDayCoup +"/"+ $scope.getMonthCoup +"/"+ $scope.getYearCoup).then(function(response){
			$scope.getdaycoup = response.data.coupon;
		})				
	};
	
	
    $scope.getCompanyDetailsForm=false;

	$scope.getCompDetails = function() {
		$scope.createCoupForm=false;
		$scope.updateCoupForm=false;
		$scope.getCouponForm=false;
		$scope.delCouponForm=false;
		$scope.CouponDetailsDiv=false;
		$scope.getCoupTypeForm=false;
		$scope.getCoupPriceForm=false;
		$scope.getCoupDateForm=false;
		if($scope.getCompanyDetailsForm==true){
			$scope.getCompanyDetailsForm=false;
		}else{
			$scope.getCompanyDetailsForm=true;
		}
		$http.get("rest/company/getDetails").then(function(response){
			$scope.compdetails = response.data;
		})				
	};
	
});



//Customer Page:
app.controller('CustomerMethods', function($scope, $http){
  
  $scope.buyCouponDetailsDiv=false;	
 
	$scope.buyCoupon = function() {
		$scope.buyCouponDetailsDiv=true;
		$http.post("rest/customer/Buy/"+$scope.chosenCoupon).then(function(response){
			$scope.buycoup = response.data;
		})				
	};
	
	
	$scope.getCouponID = function() {
		$http.get("rest/customer/getCouponID/"+$scope.chosenCoupon).then(function(response){
			$scope.getcoupid = response.data;
			$scope.buyCouponDetailsDiv=true;
		})				
	};
	
	
  $scope.getTypeCoupForm=false;
  
  $scope.getTypeCoupDetails = function(){
	  $scope.getPriceCoupForm=false;
	  $scope.getAllCouponDetailsDiv=false;
	  $scope.buyCouponDetailsDiv=false;
    if($scope.getTypeCoupForm==true){
      $scope.getTypeCoupForm=false;
    }else{
      $scope.getTypeCoupForm=true;
    }
  }
	$scope.getTypeCoupons = function() {
		$http.get("rest/customer/Type/"+$scope.getCoupType).then(function(response){
			$scope.gettypecoup = response.data.coupon;
		})				
	};
	
	
  $scope.getPriceCoupForm=false;
  
  $scope.getPriceCoupDetails = function(){
	  $scope.getTypeCoupForm=false;
	  $scope.getAllCouponDetailsDiv=false;
	  $scope.buyCouponDetailsDiv=false;
    if($scope.getPriceCoupForm==true){
      $scope.getPriceCoupForm=false;
    }else{
      $scope.getPriceCoupForm=true;
    }
  }
	$scope.getPriceCoupons = function() {
		$http.get("rest/customer/Price/"+$scope.getCoupPrice).then(function(response){
			$scope.getpricecoup = response.data.coupon;
		})				
	};
	
	
	$scope.getAllCouponDetailsDiv=false;
	
	$scope.getAllCoupons = function() {
		$scope.getTypeCoupForm=false;
		$scope.getPriceCoupForm=false;
		$scope.buyCouponDetailsDiv=false;
		if($scope.getAllCouponDetailsDiv==true){
			$scope.getAllCouponDetailsDiv=false;
		}else{
			$scope.getAllCouponDetailsDiv=true;
		}
		$http.get("rest/customer/MyCoupons").then(function(response){
			$scope.coupon = response.data.coupon;
		})				
	};
	
	
	$scope.getAllSystemCoupons = function() {
		$scope.getTypeCoupForm=false;
		$scope.getPriceCoupForm=false;
		$scope.getAllCouponDetailsDiv=false;
		if($scope.buyCouponDetailsDiv==true){
			$scope.buyCouponDetailsDiv=false;
		}else{
			$scope.buyCouponDetailsDiv=true;
		}
		$http.get("rest/customer/getAllCoupons").then(function(response){
			$scope.coupons = response.data.coupon;
		})				
	};
	
	
});
