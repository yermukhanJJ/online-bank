package kz.silk.onlinebank.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Email templates enum
 *
 * @author YerukhanJJ
 */
@Getter
@RequiredArgsConstructor
public enum EmailTemplates {

    EMAIL_CONFIRMATION("email_confirmation.html", 3),
    EMAIL_CONFIRMATION_RESEND("email_confirmation_resend.html", 3);


    private final String fileName;
    private final int argsCount;
}
