package com.exercise.zkspringboot.validator;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import java.util.Date;

public class AddViewValidator extends AbstractValidator {

    public void validate(ValidationContext ctx) {
        String abbr = (String) ctx.getProperties("abbr")[0].getValue();
        String controller = (String) ctx.getProperties("controller")[0].getValue();
        Date createDate = (Date) ctx.getProperties("createDate")[0].getValue();
        String renewOption = (String) ctx.getProperties("renewOption")[0].getValue();
        String displayLevel = (String) ctx.getProperties("displayLevel")[0].getValue();
        Integer displaySequence = (Integer) ctx.getProperties("displaySequence")[0].getValue();
        String maintainByKbFlg = (String) ctx.getProperties("maintainByKbFlg")[0].getValue();

        if (abbr == null || abbr.isEmpty() || abbr.length() > 100) {
            this.addInvalidMessage(ctx, "v_abbr", "**Form ABBR must not be blank and character length <= 100");
        }

        if (controller == null || controller.isEmpty()) {
            this.addInvalidMessage(ctx, "v_controller", "**Must choose Controller");
        }

        if (createDate == null) {
            this.addInvalidMessage(ctx, "v_createDate", "**Must choose Create Date");
        }

        if (renewOption == null || renewOption.isEmpty()) {
            this.addInvalidMessage(ctx, "v_renewOption", "**Must choose Renew Option");
        }

        if (displayLevel == null || displayLevel.isEmpty()) {
            this.addInvalidMessage(ctx, "v_displayLevel", "**Must choose Receiver Display Level");
        }

        if (displaySequence == null || displaySequence > 999 || displaySequence < 0) {
            this.addInvalidMessage(ctx, "v_displaySequence", "**Display Sequence must be 0-999 and not be blank");
        }

        if (maintainByKbFlg == null || maintainByKbFlg.isEmpty()) {
            this.addInvalidMessage(ctx, "v_maintainByKbFlg", "**Must choose Maintain by KBank Flag");
        }
    }

}
