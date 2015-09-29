package tr.org.mmo.asansor.util;

import java.io.IOException;

import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;

import org.primefaces.component.picklist.PickList;
import org.primefaces.component.picklist.PickListRenderer;
import org.primefaces.util.WidgetBuilder;


public class PickListBugfixRenderer extends PickListRenderer {

	@Override
    protected void encodeClientBehaviors(FacesContext context, ClientBehaviorHolder component) throws IOException {
        super.encodeClientBehaviors(context, component);
        PickList pickList = (PickList) component;
        WidgetBuilder wb = getWidgetBuilder(context);
        wb.attr("filterMatchMode", pickList.getFilterMatchMode(), null);
    }
}
