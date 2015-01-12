package controllers.travelbusiness;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.travelbusiness.home;
import views.html.travelbusiness.searchHotel;

import com.mnt.travelbusiness.helper.PageScope;
public class Application extends Controller {

    public static Result index() {
    	PageScope.scope("number", 1);
    	return ok(home.render());
    }
    
    public static Result searchHotel() {
    	return ok(searchHotel.render());
    }

}
